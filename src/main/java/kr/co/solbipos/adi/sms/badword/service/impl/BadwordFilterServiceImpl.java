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

    /**
     * 금칙어 탐지 — 캐시가 만료됐으면 자동 갱신
     *
     *  2026.08.26 다건 탐지로 변경
     *   - 기존: 첫 탐지에서 즉시 반환 → 금칙어가 여러 개여도 이력이 1건만 남음
     *   - 변경: 3단계(contains/exact/regex)를 전부 수행해 탐지된 금칙어를 모두 수집
     *           (URL 체크가 URL마다 이력을 남기는 것과 동일하게 금칙어도 전부 이력 저장)
     *   - 동일 금칙어가 여러 번 등장하면 "등장 횟수만큼" 수집 → 이력도 등장 횟수만큼 저장
     *     (contains 는 등장 위치마다 1건, exact 는 전문 일치라 1건, regex 는 키워드당 1건)
     */
    @Override
    public FilterResult check(String msgContent) {
        if (msgContent == null || msgContent.isEmpty()) {
            return FilterResult.pass();
        }

        CacheHolder cache = getCache();
        String normalized = TextNormalizer.normalize(msgContent);

        // 탐지 목록 (발견 순서 유지, 동일 금칙어도 등장 횟수만큼 담김)
        List<BadwordVO> hits = new ArrayList<>();

        // 1) Aho-Corasick: contains 키워드 (O(n)) — 등장 위치마다 전부 수집
        hits.addAll(cache.acMatcher.findAll(normalized));

        // 2) HashSet: exact 키워드 (O(1)) — 메시지 전문 일치이므로 1건
        BadwordVO exactHit = cache.exactMap.get(normalized);
        if (exactHit != null) {
            hits.add(exactHit);
        }

        // 3) 정규식 키워드 (리스트 순차 — 통상 건수 적음, 원본 텍스트로 비교) — 매치된 키워드당 1건
        for (BadwordVO bw : cache.regexList) {
            if (matchesRegex(msgContent, bw)) {
                hits.add(bw);
            }
        }

        if (hits.isEmpty()) {
            return FilterResult.pass();
        }
        return FilterResult.detectedAll(hits, "keyword");
    }

    /**
     * MESSAGE_BLOCK_LOG 검사 이력 저장 (탐지 여부와 관계없이 항상 호출)
     *
     *  2026.08.26 다건 이력 저장으로 변경
     *   - 탐지된 금칙어가 여러 개면 금칙어마다 이력을 1건씩 저장 (URL 체크 이력과 동일한 방식)
     *   - msgStatus 는 각 금칙어의 severity 기준으로 개별 판정
     */
    @Override
    public void saveBlockLog(SmsSendVO smsSendVO, FilterResult result, SessionInfoVO sessionInfoVO) {

        // 미탐지 이력(allowed) — 기존 단건 저장 유지
        if (!result.isDetected() || result.getDetectedList().isEmpty()) {
            smsSendVO.setBlockType("");
            smsSendVO.setBadwordId(result.getBadwordId());
            smsSendVO.setMsgStatus(toMsgStatus(result.getSeverity()));
            badwordFilterMapper.insertMessageBlock(smsSendVO);
            return;
        }

        // 탐지된 금칙어마다 이력 1건씩 저장
        for (BadwordVO bw : result.getDetectedList()) {
            smsSendVO.setBlockType(result.getBlockType());
            smsSendVO.setBadwordId(bw.getBadwordId());
            smsSendVO.setMsgStatus(toMsgStatus(bw.getSeverity()));
            badwordFilterMapper.insertMessageBlock(smsSendVO);
        }
    }

    /** severity → 메시지 상태 변환 */
    private String toMsgStatus(String severity) {
        if (severity == null) {
            return "allowed";
        }
        switch (severity) {
            case "block": return "blocked";
            case "hold":  return "held";
            case "warn":  return "warned";
            default:      return "allowed";
        }
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
