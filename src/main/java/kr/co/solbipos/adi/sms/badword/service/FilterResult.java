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
    private Long triggeredKeywordId;

    /** 탐지된 금칙어 원문 */
    private String triggeredKeyword;

    /**
     * 처리 방식
     * block / hold / warn
     */
    private String status;

    /** 정상 통과 결과 */
    public static FilterResult pass() {
        FilterResult result = new FilterResult();
        result.detected = false;
        return result;
    }

    /** 탐지 결과 */
    public static FilterResult detected(BadwordVO badword, String blockType) {
        FilterResult result = new FilterResult();
        result.detected           = true;
        result.blockType          = blockType;
        result.triggeredKeywordId = badword.getId();
        result.triggeredKeyword   = badword.getKeyword();
        result.status             = badword.getSeverity();
        return result;
    }

    public boolean isDetected() { return detected; }

    public Long getTriggeredKeywordId() { return triggeredKeywordId; }

    public String getTriggeredKeyword() { return triggeredKeyword; }

    public String getStatus() { return status; }

    public String getBlockType() { return blockType; }
}
