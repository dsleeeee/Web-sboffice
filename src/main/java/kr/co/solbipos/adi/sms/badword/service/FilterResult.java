package kr.co.solbipos.adi.sms.badword.service;

/**
 * @Class Name : FilterResult.java
 * @Description : 금칙어 필터링 결과 VO
 */
public class FilterResult {

    /** 탐지 여부 */
    private boolean detected;

    /**
     * 탐지 유형
     * keyword / url / combined
     */
    private String blockType;

    /** 탐지된 금칙어 ID (BADWORD.ID) */
    private Long badwordId;

    /** 탐지된 금칙어 원문 */
    private String keyword;

    /**
     * 처리 방식
     * block / hold / warn
     */
    private String severity;

    /** 정상 통과 결과 */
    public static FilterResult pass() {
        FilterResult result = new FilterResult();
        result.detected = false;
        return result;
    }

    /** 탐지 결과 */
    public static FilterResult detected(BadwordVO badword, String blockType) {
        FilterResult result = new FilterResult();
        result.detected     = true;
        result.blockType    = blockType;
        result.badwordId    = badword.getBadwordId();
        result.keyword      = badword.getKeyword();
        result.severity     = badword.getSeverity();
        return result;
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
