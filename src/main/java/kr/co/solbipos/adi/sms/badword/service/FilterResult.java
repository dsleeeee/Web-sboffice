package kr.co.solbipos.adi.sms.badword.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Class Name : FilterResult.java
 * @Description : 금칙어 필터링 결과 VO
 *
 *  2026.08.26 다건 탐지 대응
 *   - 메시지 안에 금칙어가 여러 개면 detectedList 에 전부 담는다. (이력도 전부 저장)
 *   - badwordId/severity 는 첫 번째 탐지 건 기준(기존 호환), keyword 는 전체를 콤마로 연결해 반환
 */
public class FilterResult {

    /** 탐지 여부 */
    private boolean detected;

    /**
     * 탐지 유형
     * keyword / url / combined
     */
    private String blockType;

    /** 탐지된 금칙어 ID (BADWORD.ID) - 첫 번째 탐지 건 */
    private Long badwordId;

    /** 탐지된 금칙어 원문 - 다건이면 콤마(, )로 전체 연결 */
    private String keyword;

    /**
     * 처리 방식 - 첫 번째 탐지 건
     * block / hold / warn
     */
    private String severity;

    /** 탐지된 금칙어 전체 목록 (본문 발견 순서, 동일 금칙어가 여러 번 등장하면 등장 횟수만큼 포함) */
    private List<BadwordVO> detectedList = Collections.emptyList();

    /** 정상 통과 결과 */
    public static FilterResult pass() {
        FilterResult result = new FilterResult();
        result.detected = false;
        return result;
    }

    /** 탐지 결과 (단건) */
    public static FilterResult detected(BadwordVO badword, String blockType) {
        List<BadwordVO> one = new ArrayList<>();
        one.add(badword);
        return detectedAll(one, blockType);
    }

    /** 탐지 결과 (다건) — 탐지된 금칙어 전부를 담는다 */
    public static FilterResult detectedAll(List<BadwordVO> badwords, String blockType) {
        FilterResult result = new FilterResult();
        result.detected     = true;
        result.blockType    = blockType;
        result.detectedList = badwords;

        // 단건 필드는 첫 번째 탐지 건 기준으로 채움 (기존 호출부 호환)
        BadwordVO first = badwords.get(0);
        result.badwordId = first.getBadwordId();
        result.severity  = first.getSeverity();

        // keyword 는 전체를 콤마로 연결 (표시용 문자열이므로 동일 키워드는 한 번만)
        java.util.LinkedHashSet<String> keywordSet = new java.util.LinkedHashSet<>();
        for (BadwordVO bw : badwords) {
            keywordSet.add(bw.getKeyword());
        }
        StringBuilder sb = new StringBuilder();
        for (String kw : keywordSet) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(kw);
        }
        result.keyword = sb.toString();

        return result;
    }

    public List<BadwordVO> getDetectedList() {
        return detectedList;
    }

    public boolean isDetected() { return detected; }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public Long getBadwordId() {
        return badwordId;
    }

    public void setBadwordId(Long badwordId) {
        this.badwordId = badwordId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
