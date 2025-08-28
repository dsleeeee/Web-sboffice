package kr.co.solbipos.base.pay.mCoupnProdMapping.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MCoupnProdMappingVO.java
 * @Description : 기초관리 > 결제수단 > 모바일쿠폰상품매핑
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.08.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MCoupnProdMappingVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 전체기간 여부 */
    private boolean chkDt;

    /** 등록일자 검색기준 */
    private String regDtType;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 바코드 */
    private String barCd;

    /** 사용여부 */
    private String useYn;

    /** 사용자 아이디 */
    private String userId;

    /** 모바일쿠폰사-상품코드 최대수 */
    private int mCoupnProdCnt;

    /** 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;

    /** 조회구분 (A:가로, B:세로) */
    private String searchGubun;

    /** 세션ID */
    private String sessionId;

    /** 순번 */
    private int seq;

    /** 모바일쿠폰코드 */
    private String mcoupnCd;

    /** 모바일쿠폰명 */
    private String mcoupnNm;

    /** 모바일쿠폰사-상품코드 */
    private String mcoupnProdCd;

    /** 비고 */
    private String remark;

    /** 결과 구분 */
    private String resultGubun;

    public String getHqOfficeCd() { return hqOfficeCd; }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public boolean isChkDt() { return chkDt; }

    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
    }

    public String getRegDtType() {
        return regDtType;
    }

    public void setRegDtType(String regDtType) {
        this.regDtType = regDtType;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getProdClassCd() { return prodClassCd; }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getBarCd() {
        return barCd;
    }

    public void setBarCd(String barCd) {
        this.barCd = barCd;
    }

    public String getUseYn() { return useYn; }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getMCoupnProdCnt() { return mCoupnProdCnt; }

    public void setMCoupnProdCnt(int mCoupnProdCnt) {
        this.mCoupnProdCnt = mCoupnProdCnt;
    }

    public String getsQuery1() { return sQuery1; }

    public void setsQuery1(String sQuery1) { this.sQuery1 = sQuery1; }

    public String getsQuery2() { return sQuery2; }

    public void setsQuery2(String sQuery2) { this.sQuery2 = sQuery2; }

    public String getSearchGubun() { return searchGubun; }

    public void setSearchGubun(String searchGubun) { this.searchGubun = searchGubun; }

    public String getSessionId() { return sessionId; }

    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public int getSeq() { return seq; }

    public void setSeq(int seq) { this.seq = seq; }

    public String getMcoupnCd() { return mcoupnCd; }

    public void setMcoupnCd(String mcoupnCd) {
        this.mcoupnCd = mcoupnCd;
    }

    public String getMcoupnNm() { return mcoupnNm; }

    public void setMcoupnNm(String mcoupnNm) {
        this.mcoupnNm = mcoupnNm;
    }

    public String getMcoupnProdCd() { return mcoupnProdCd; }

    public void setMcoupnProdCd(String mcoupnProdCd) {
        this.mcoupnProdCd = mcoupnProdCd;
    }

    public String getRemark() { return remark; }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResultGubun() { return resultGubun; }

    public void setResultGubun(String resultGubun) {
        this.resultGubun = resultGubun;
    }
}