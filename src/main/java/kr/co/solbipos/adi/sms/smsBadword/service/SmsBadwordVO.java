package kr.co.solbipos.adi.sms.smsBadword.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SmsBadwordVO.java
 * @Description : 부가서비스 > SMS관리 > SMS금칙어(탭)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.01  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.01
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class SmsBadwordVO extends PageVO {

    private static final long serialVersionUID = 692647285725286776L;

    /** 금칙어 ID */
    private String badwordId;

    /** 금칙어 키워드 */
    private String keyword;

    /** 정규화 키워드 */
    private String keywordNormalized;

    /** 카테고리 */
    private String category;

    /** 매칭 타입 (exact/regex/aho_corasick) */
    private String matchType;

    /** 위험도 (block/hold/warn) */
    private String severity;

    /** 출처 */
    private String source;

    /** 사용여부 */
    private String useYn;

    /** 조회 시작 일자 */
    private String startDate;

    /** 조회 종료 일자 */
    private String endDate;

    /** 차단 유형 */
    private String blockType;

    /** 탐지 키워드 */
    private String triggeredKeyword;

    /** 탐지 URL */
    private String triggeredUrl;

    /** 메시지 상태 (blocked/held/warned/allowed) */
    private String msgStatus;

    public String getBadwordId() {
        return badwordId;
    }

    public void setBadwordId(String badwordId) {
        this.badwordId = badwordId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeywordNormalized() {
        return keywordNormalized;
    }

    public void setKeywordNormalized(String keywordNormalized) {
        this.keywordNormalized = keywordNormalized;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getTriggeredKeyword() {
        return triggeredKeyword;
    }

    public void setTriggeredKeyword(String triggeredKeyword) {
        this.triggeredKeyword = triggeredKeyword;
    }

    public String getTriggeredUrl() {
        return triggeredUrl;
    }

    public void setTriggeredUrl(String triggeredUrl) {
        this.triggeredUrl = triggeredUrl;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }
}
