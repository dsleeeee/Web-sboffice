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
    private String id;

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

    /** 활성여부 (1:활성, 0:비활성) */
    private String isActive;

    /** 차단로그 ID */
    private String blockLogId;

    /** 계정 ID */
    private String accountId;

    /** 메시지 ID */
    private String messageId;

    /** 차단 유형 */
    private String blockType;

    /** 트리거된 키워드 ID */
    private String triggeredKeywordId;

    /** 트리거된 URL */
    private String triggeredUrl;

    /** 상태 (blocked/held/warned/allowed) */
    private String msgStatus;

    /** 메시지 미리보기 */
    private String messageSnippet;

    /** 소속코드 */
    private String sOgnCd;

    /** 소속구분 */
    private String sOgnFg;

    /** 사용자 ID */
    private String sUserId;

    /** 검색 시작일 */
    private String startDt;

    /** 검색 종료일 */
    private String endDt;

    /** 검색 시작일 (msgBlockLog) */
    private String startDate;

    /** 검색 종료일 (msgBlockLog) */
    private String endDate;

    /** 탐지 금칙어 텍스트 검색 */
    private String triggeredKeyword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getBlockLogId() {
        return blockLogId;
    }

    public void setBlockLogId(String blockLogId) {
        this.blockLogId = blockLogId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getTriggeredKeywordId() {
        return triggeredKeywordId;
    }

    public void setTriggeredKeywordId(String triggeredKeywordId) {
        this.triggeredKeywordId = triggeredKeywordId;
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

    public String getMessageSnippet() {
        return messageSnippet;
    }

    public void setMessageSnippet(String messageSnippet) {
        this.messageSnippet = messageSnippet;
    }

    public String getsOgnCd() {
        return sOgnCd;
    }

    public void setsOgnCd(String sOgnCd) {
        this.sOgnCd = sOgnCd;
    }

    public String getsOgnFg() {
        return sOgnFg;
    }

    public void setsOgnFg(String sOgnFg) {
        this.sOgnFg = sOgnFg;
    }

    public String getsUserId() {
        return sUserId;
    }

    public void setsUserId(String sUserId) {
        this.sUserId = sUserId;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTriggeredKeyword() {
        return triggeredKeyword;
    }

    public void setTriggeredKeyword(String triggeredKeyword) {
        this.triggeredKeyword = triggeredKeyword;
    }
}
