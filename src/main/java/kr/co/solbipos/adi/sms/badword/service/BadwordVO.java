package kr.co.solbipos.adi.sms.badword.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : BadwordVO.java
 * @Description : 부가서비스 > SMS관리 > SMS전송(탭) > 금칙어 필터링
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
public class BadwordVO extends PageVO {

    private static final long serialVersionUID = 629368046159174716L;

    /** 고유 ID */
    private Long id;

    /** 금칙어 원문 */
    private String keyword;

    /** 정규화된 키워드 (특수문자 제거 버전) */
    private String keywordNormalized;

    /**
     * 카테고리
     * loan / gambling / adult / phishing / illegal_drug / scam / other
     */
    private String category;

    /**
     * 매칭 방식
     * exact / contains / regex
     */
    private String matchType;

    /**
     * 처리 방식
     * block / hold / warn
     */
    private String severity;

    /** 출처 (kisa_report / manual / feedback 등) */
    private String source;

    /** 활성 여부 (1:활성 / 0:비활성) */
    private String isActive;

    /** 등록 관리자 ID */
    private String createdBy;

    /** 등록 일시 */
    private String createdAt;

    /** 수정 관리자 ID */
    private String updatedBy;

    /** 수정 일시 */
    private String updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
