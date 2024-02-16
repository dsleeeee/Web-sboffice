package kr.co.solbipos.base.prod.dlvrProdMulti.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : DlvrProdMultiVO.java
 * @Description : 기초관리 - 상품관리 - 배달시스템 상품 명칭 매핑2
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.25  김유승       최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.25
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class DlvrProdMultiVO extends PageVO {

    private static final long serialVersionUID = 6506896944142701351L;


    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 전체기간 여부 */
    private boolean chkDt;
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
    /** 배달앱 - 명칭코드 */
    private String dlvrNameCd;
    /** 배달앱 - 상품명 */
    private String dlvrProdNm;
    /** 사용자 아이디 */
    private String userId;
    /** 배달앱 구분코드 컬럼 */
    private String dlvrCol;
    /** 배달앱 구분코드 array */
    private String arrDlvrCol[];
    /** 쿼리문의 PIVOT IN에 사용할 배달앱 구분코드 컬럼 문자열 */
    private String pivotDlvrCol;
    /** 기준매장 구분 Flag (본사 : H / 매장 : S) */
    private String originalStoreFg;
    /** 기준매장코드 */
    private String originalStoreCd;
    /** 적용대상매장코드 */
    private String targetStoreCd;
    /** 업로드 상품코드 컬럼  */
    private String prodCdCol;
    /** 업로드 상품코드 array */
    private String arrProdCdCol[];
    /** 등록일자 검색기준 */
    private String regDtType;

    /* *//** 페이코앱 *//*
    private String dlvrProdNm2;
    *//** 배달의민족앱 *//*
    private String dlvrProdNm3;
    *//** 요기요앱 *//*
    private String dlvrProdNm4;
    *//** 쿠팡이츠앱 *//*
    private String dlvrProdNm5;
    *//** 위메프앱 *//*
    private String dlvrProdNm6;*/

    /** 매장명 */
    private String storeNm;

    /** 매장상태 */
    private String sysStatFg;

    /** 상품코드 */
    private String selectProdCd;

    /** 배달앱(배달채널) - 상품명(검색용) */
    private String channelProdNm;

    /** 세션ID */
    private String sessionId;

    /** 순번 */
    private Integer seq;

    /** 엑셀업로드 구분 */
    private String mappFg;

    private String seqCol;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public boolean isChkDt() {
        return chkDt;
    }

    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
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

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getBarCd() {
        return barCd;
    }

    public void setBarCd(String barCd) {
        this.barCd = barCd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getDlvrNameCd() {
        return dlvrNameCd;
    }

    public void setDlvrNameCd(String dlvrNameCd) {
        this.dlvrNameCd = dlvrNameCd;
    }

    public String getDlvrProdNm() {
        return dlvrProdNm;
    }

    public void setDlvrProdNm(String dlvrProdNm) {
        this.dlvrProdNm = dlvrProdNm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDlvrCol() {
        return dlvrCol;
    }

    public void setDlvrCol(String dlvrCol) {
        this.dlvrCol = dlvrCol;
    }

    public String[] getArrDlvrCol() {
        return arrDlvrCol;
    }

    public void setArrDlvrCol(String[] arrDlvrCol) {
        this.arrDlvrCol = arrDlvrCol;
    }

    public String getPivotDlvrCol() {
        return pivotDlvrCol;
    }

    public void setPivotDlvrCol(String pivotDlvrCol) {
        this.pivotDlvrCol = pivotDlvrCol;
    }

    public String getOriginalStoreFg() {
        return originalStoreFg;
    }

    public void setOriginalStoreFg(String originalStoreFg) {
        this.originalStoreFg = originalStoreFg;
    }

    public String getOriginalStoreCd() {
        return originalStoreCd;
    }

    public void setOriginalStoreCd(String originalStoreCd) {
        this.originalStoreCd = originalStoreCd;
    }

    public String getTargetStoreCd() {
        return targetStoreCd;
    }

    public void setTargetStoreCd(String targetStoreCd) {
        this.targetStoreCd = targetStoreCd;
    }

    public String getProdCdCol() {
        return prodCdCol;
    }

    public void setProdCdCol(String prodCdCol) {
        this.prodCdCol = prodCdCol;
    }

    public String[] getArrProdCdCol() {
        return arrProdCdCol;
    }

    public void setArrProdCdCol(String[] arrProdCdCol) {
        this.arrProdCdCol = arrProdCdCol;
    }

    public String getRegDtType() {
        return regDtType;
    }

    public void setRegDtType(String regDtType) {
        this.regDtType = regDtType;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String getSelectProdCd() {
        return selectProdCd;
    }

    public void setSelectProdCd(String selectProdCd) {
        this.selectProdCd = selectProdCd;
    }

    public String getChannelProdNm() {
        return channelProdNm;
    }

    public void setChannelProdNm(String channelProdNm) {
        this.channelProdNm = channelProdNm;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getMappFg() {
        return mappFg;
    }

    public void setMappFg(String mappFg) {
        this.mappFg = mappFg;
    }

    public String getSeqCol() {
        return seqCol;
    }

    public void setSeqCol(String seqCol) {
        this.seqCol = seqCol;
    }
}
