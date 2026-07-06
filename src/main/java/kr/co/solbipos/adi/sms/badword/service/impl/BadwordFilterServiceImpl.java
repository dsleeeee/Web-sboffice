package kr.co.solbipos.adi.sms.badword.service.impl;

import kr.co.solbipos.adi.sms.badword.service.BadwordFilterService;
import kr.co.solbipos.adi.sms.badword.service.BadwordVO;
import kr.co.solbipos.adi.sms.badword.service.FilterResult;
import kr.co.solbipos.adi.sms.badword.util.AhoCorasickMatcher;
import kr.co.solbipos.adi.sms.badword.util.TextNormalizer;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : BadwordFilterServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > SMS전송(탭) > 금칙어 필터링
 *
 *  contains 키워드 : Aho-Corasick 트리로 O(n) 탐색
 *  exact    키워드 : HashSet 으로 O(1) 조회
 *  regex    키워드 : 정규식 리스트 순차 탐색
 *
 *  금칙어 목록은 DB에서 1시간 단위로 캐시 갱신
 *
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.06.23  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.06.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("badwordFilterService")
@Transactional
public class BadwordFilterServiceImpl implements BadwordFilterService {

    private static final long CACHE_TTL_MS = 60 * 60 * 1000L; // 1시간

    private final BadwordFilterMapper badwordFilterMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** 스레드-세이프 캐시 홀더 */
    private final AtomicReference<CacheHolder> cacheRef = new AtomicReference<>(new CacheHolder());

    @Autowired
    public BadwordFilterServiceImpl(BadwordFilterMapper badwordFilterMapper) {
        this.badwordFilterMapper = badwordFilterMapper;
    }

    // ------------------------------------------------------------------ //
    // public API
    // ------------------------------------------------------------------ //

    /** 금칙어 탐지 — 캐시가 만료됐으면 자동 갱신 */
    @Override
    public FilterResult check(String msgContent) {
        if (msgContent == null || msgContent.isEmpty()) {
            return FilterResult.pass();
        }

        CacheHolder cache = getCache();
        String normalized = TextNormalizer.normalize(msgContent);

        // 1) Aho-Corasick: contains 키워드 (O(n))
        BadwordVO hit = cache.acMatcher.findFirst(normalized);
        if (hit != null) {
            return FilterResult.detected(hit, "keyword");
        }

        // 2) HashSet: exact 키워드 (O(1))
        BadwordVO exactHit = cache.exactMap.get(normalized);
        if (exactHit != null) {
            return FilterResult.detected(exactHit, "keyword");
        }

        // 3) 정규식 키워드 (리스트 순차 — 통상 건수 적음, 원본 텍스트로 비교)
        for (BadwordVO bw : cache.regexList) {
            if (matchesRegex(msgContent, bw)) {
                return FilterResult.detected(bw, "keyword");
            }
        }

        return FilterResult.pass();
    }

    /** MESSAGE_BLOCK_LOG 검사 이력 저장 (탐지 여부와 관계없이 항상 호출) */
    @Override
    public void saveBlockLog(SmsSendVO smsSendVO, FilterResult result, SessionInfoVO sessionInfoVO) {

        smsSendVO.setBlockType(result.isDetected() ? result.getBlockType() : "");
        smsSendVO.setTriggeredKeywordId(result.getTriggeredKeywordId());
        smsSendVO.setTriggeredStatus(result.getStatus());

        // 개인정보 최소화 원칙: 본문 앞 100자만 저장
        String content = smsSendVO.getContent();
        smsSendVO.setMessageSnippet(content != null && content.length() > 100 ? content.substring(0, 100) : content);

        // MESSAGE_BLOCK_LOG INSERT (selectKey로 blockLogId 채번 포함)
        badwordFilterMapper.insertMessageBlockLog(smsSendVO);

        // '보류' 이면 관리자 검토 테이블 등록
        /*if ("hold".equals(result.getStatus())) {
            badwordFilterMapper.insertAdminReviewQueue(smsSendVO);
        }*/
    }

    /** 캐시 강제 갱신 */
    @Override
    public void refreshCache() {
        loadCache();
    }

    // ------------------------------------------------------------------ //
    // 캐시 관리
    // ------------------------------------------------------------------ //

    private CacheHolder getCache() {
        CacheHolder holder = cacheRef.get();
        if (holder.loadedAt == 0 || System.currentTimeMillis() - holder.loadedAt > CACHE_TTL_MS) {
            loadCache();
        }
        return cacheRef.get();
    }

    private synchronized void loadCache() {
        CacheHolder current = cacheRef.get();
        if (current.loadedAt != 0 && System.currentTimeMillis() - current.loadedAt <= CACHE_TTL_MS) {
            return;
        }
        List<BadwordVO> all = badwordFilterMapper.selectActiveBadwordList();

        List<BadwordVO> containsList = new ArrayList<>();
        Map<String, BadwordVO> exactMap  = new LinkedHashMap<>();
        List<BadwordVO> regexList  = new ArrayList<>();

        for (BadwordVO bw : all) {
            String mt = bw.getMatchType();
            if ("exact".equals(mt)) {
                String key = bw.getKeywordNormalized() != null && !bw.getKeywordNormalized().isEmpty()
                        ? bw.getKeywordNormalized()
                        : TextNormalizer.normalize(bw.getKeyword());
                exactMap.put(key, bw);
            } else if ("regex".equals(mt)) {
                regexList.add(bw);
            } else {
                containsList.add(bw);
            }
        }

        CacheHolder next = new CacheHolder(
                new AhoCorasickMatcher(containsList),
                exactMap,
                regexList,
                System.currentTimeMillis()
        );
        cacheRef.set(next);
        LOGGER.info("BADWORD_FILTER >>> 캐시 갱신 완료 >>> contains={} exact={} regex={}",
                containsList.size(), exactMap.size(), regexList.size());
    }

    // ------------------------------------------------------------------ //
    // 정규식 매칭 (regex 타입 전용)
    // ------------------------------------------------------------------ //

    private boolean matchesRegex(String normalizedContent, BadwordVO bw) {
        try {
            return normalizedContent.matches(".*" + bw.getKeyword() + ".*");
        } catch (Exception e) {
            LOGGER.warn("BADWORD_FILTER >>> regex 오류 >>> keyword={}", bw.getKeyword());
            return false;
        }
    }

    // ------------------------------------------------------------------ //
    // 캐시 홀더
    // ------------------------------------------------------------------ //

    private static class CacheHolder {
        final AhoCorasickMatcher  acMatcher;
        final Map<String, BadwordVO> exactMap;
        final List<BadwordVO>     regexList;
        final long                loadedAt;

        /** 초기 빈 홀더 */
        CacheHolder() {
            this.acMatcher = new AhoCorasickMatcher(Collections.<BadwordVO>emptyList());
            this.exactMap  = Collections.emptyMap();
            this.regexList = Collections.emptyList();
            this.loadedAt  = 0L;
        }

        CacheHolder(AhoCorasickMatcher acMatcher,
                    Map<String, BadwordVO> exactMap,
                    List<BadwordVO> regexList,
                    long loadedAt) {
            this.acMatcher = acMatcher;
            this.exactMap  = exactMap;
            this.regexList = regexList;
            this.loadedAt  = loadedAt;
        }
    }
}
