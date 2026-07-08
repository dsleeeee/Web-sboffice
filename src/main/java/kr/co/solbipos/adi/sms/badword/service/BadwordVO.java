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
    private Long badwordId;

    /** 금칙어 원문 (정규식 패턴 허용) */
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

    /** 사용 여부 (Y:사용 / N:미사용) */
    private String useYn;

    /** 등록 일시 */
    private String regDt;

    /** 등록 ID */
    private String regId;

    /** 수정 일시 */
    private String modDt;

    /** 수정 ID */
    private String modId;

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

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }
}
