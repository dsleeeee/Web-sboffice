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
import java.util.regex.Pattern;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : BadwordFilterServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > SMS전송(탭) > 금칙어 필터링
 *
 *  contains 키워드 : Aho-Corasick 트리로 O(n) 탐색
 *  exact    키워드 : HashSet 으로 O(1) 조회
 *  regex    키워드 : 정규식 리스트 순차 탐색
 *  조합(&&) 키워드 : keyword 를 && 로 분리한 모든 파트가 본문에 포함되면 성립 (매칭방식 무관, && 포함 건)
 *
 *  이력은 걸린 "규칙당 1건" (동일 금칙어 중복 제거, 단독/조합은 독립 판정)
 *  금칙어 목록은 DB에서 1시간 단위로 캐시 갱신
 *
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.06.23  이다솜      최초생성
 * @ 2026.09.21  yskim       조합(&&) 키워드 추가(매칭방식 무관), 이력 기준 규칙당 1건으로 변경, regex 여러 줄 메시지 미탐지 수정
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
     *
     *  2026.09.21 조합(&&) 키워드 추가 + 이력 기준 "규칙당 1건"으로 통일
     *   - 조합 키워드: keyword 를 && 로 분리한 모든 파트가 본문에 포함되면 성립 (성립한 조합당 1건)
     *   - 동일 금칙어가 여러 번 등장해도 1건만 수집 (badwordId 기준 중복 제거, 발견 순서 유지)
     *   - 단독 금칙어와 조합은 독립 판정 — 걸린 규칙은 전부 각각 이력에 남는다
     */
    @Override
    public FilterResult check(String msgContent) {
        if (msgContent == null || msgContent.isEmpty()) {
            return FilterResult.pass();
        }

        CacheHolder cache = getCache();
        String normalized = TextNormalizer.normalize(msgContent);

        // 탐지 목록 (발견 순서 유지)
        List<BadwordVO> hits = new ArrayList<>();

        // 1) Aho-Corasick: contains 키워드 (O(n)) — 등장 위치마다 수집 (아래에서 규칙당 1건으로 중복 제거)
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

        // 4) 조합(&&) 키워드 — 모든 파트가 본문에 포함되어야 성립, 성립한 조합당 1건 (파트별 등장 횟수는 안 셈)
        for (ComboBadword combo : cache.comboList) {
            boolean allFound = true;
            for (String part : combo.normalizedParts) {
                if (!normalized.contains(part)) {
                    allFound = false;
                    break;
                }
            }
            if (allFound) {
                hits.add(combo.badword);
            }
        }

        if (hits.isEmpty()) {
            return FilterResult.pass();
        }

        // 걸린 "규칙당 1건"으로 중복 제거 (contains 는 등장 위치마다 수집되므로 badwordId 기준 최초 1건만 유지)
        Map<Long, BadwordVO> distinct = new LinkedHashMap<>();
        for (BadwordVO bw : hits) {
            distinct.putIfAbsent(bw.getBadwordId(), bw);
        }
        return FilterResult.detectedAll(new ArrayList<>(distinct.values()), "keyword");
    }

    /**
     * MESSAGE_BLOCK_LOG 검사 이력 저장 (탐지 여부와 관계없이 항상 호출)
     *
     *  2026.08.26 다건 이력 저장으로 변경
     *   - 탐지된 금칙어가 여러 개면 금칙어마다 이력을 1건씩 저장
     *   - msgStatus 는 각 금칙어의 severity 기준으로 개별 판정
     *  2026.09.21 detectedList 가 규칙당 1건으로 중복 제거되어 넘어오므로 이력도 규칙당 1건
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
        List<ComboBadword> comboList = new ArrayList<>();

        /*
         * 매칭방식(MATCH_TYPE)별 분류 규칙
         *  1) keyword 에 && 포함 → 조합 (매칭방식 값과 무관하게 최우선. 모든 파트가 본문에 포함되면 성립)
         *  2) exact              → 전문일치 맵 (정규화된 메시지 "전체"가 키워드와 같을 때만 탐지)
         *  3) regex              → 정규식 리스트 (원본 텍스트 부분탐색, 정규화 안 거침)
         *  4) 그 외(contains)    → Aho-Corasick 트리 (본문 어디든 포함되면 탐지, 정규화로 특수문자 우회 방어)
         */
        for (BadwordVO bw : all) {
            // (2026.09.21) && 포함 키워드는 매칭방식과 무관하게 조합으로 처리
            //  - exact 로 두면: 정규화 때 && 가 지워져 "급전당일" 같은 붙은 전문일치로 변질 → 사실상 못 잡음
            //  - regex 로 두면: && 가 리터럴 문자로 해석돼 본문에 && 가 그대로 있어야 매칭 → 죽은 규칙
            //  → 등록자가 매칭방식을 잘못 골라도 조합 의도대로 동작하도록 여기서 최우선 분류한다
            ComboBadword combo = ComboBadword.of(bw);
            if (combo != null) {
                comboList.add(combo);
                continue;
            }

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
                comboList,
                System.currentTimeMillis()
        );
        cacheRef.set(next);
        LOGGER.info("BADWORD_FILTER >>> 캐시 갱신 완료 >>> contains={} exact={} regex={} combo={}",
                containsList.size(), exactMap.size(), regexList.size(), comboList.size());
    }

    // ------------------------------------------------------------------ //
    // 정규식 매칭 (regex 타입 전용)
    // ------------------------------------------------------------------ //

    /**
     * (2026.09.21) matches(".*kw.*") → find() 로 변경
     *  - 기존 방식은 .* 가 줄바꿈을 못 넘어 여러 줄 메시지에서 패턴이 있어도 미탐지
     *  - find() 는 부분 탐색이라 .* 포장이 불필요하고, DOTALL 로 패턴 내 . 도 줄바꿈을 넘는다
     */
    private boolean matchesRegex(String msgContent, BadwordVO bw) {
        try {
            return Pattern.compile(bw.getKeyword(), Pattern.DOTALL).matcher(msgContent).find();
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
        final List<ComboBadword>  comboList;
        final long                loadedAt;

        /** 초기 빈 홀더 */
        CacheHolder() {
            this.acMatcher = new AhoCorasickMatcher(Collections.<BadwordVO>emptyList());
            this.exactMap  = Collections.emptyMap();
            this.regexList = Collections.emptyList();
            this.comboList = Collections.emptyList();
            this.loadedAt  = 0L;
        }

        CacheHolder(AhoCorasickMatcher acMatcher,
                    Map<String, BadwordVO> exactMap,
                    List<BadwordVO> regexList,
                    List<ComboBadword> comboList,
                    long loadedAt) {
            this.acMatcher = acMatcher;
            this.exactMap  = exactMap;
            this.regexList = regexList;
            this.comboList = comboList;
            this.loadedAt  = loadedAt;
        }
    }

    // ------------------------------------------------------------------ //
    // 조합(&&) 금칙어
    // ------------------------------------------------------------------ //

    /** 조합(&&) 금칙어 — 파트별 정규화 결과를 캐시에 보관 (check() 마다 다시 분리하지 않도록) */
    private static class ComboBadword {
        final BadwordVO badword;
        final List<String> normalizedParts;

        private ComboBadword(BadwordVO badword, List<String> normalizedParts) {
            this.badword = badword;
            this.normalizedParts = normalizedParts;
        }

        /**
         * keyword 에 && 가 있으면 파트별로 정규화해서 조합 금칙어로 생성. 조합이 아니면 null.
         *  - 정규화하면 && 자체가 제거되므로 keywordNormalized 는 쓰지 않고 원문 keyword 를 분리한다
         *  - 정규화 후 빈 파트는 제거 (빈 파트를 남기면 contains("") == true 라 모든 메시지가 차단됨)
         *  - 유효 파트가 2개 미만이면 조합이 아니므로 null 반환 → 기존 contains 로 처리
         */
        static ComboBadword of(BadwordVO bw) {
            String keyword = bw.getKeyword();
            if (keyword == null || !keyword.contains("&&")) {
                return null;
            }
            List<String> parts = new ArrayList<>();
            for (String raw : keyword.split("&&")) {
                String part = TextNormalizer.normalize(raw);
                if (!part.isEmpty()) {
                    parts.add(part);
                }
            }
            if (parts.size() < 2) {
                return null;
            }
            return new ComboBadword(bw, parts);
        }
    }
}
