package kr.co.solbipos.base.price.salePriceResve.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;


/**
 * @Class Name : SalePriceResveVO.java
 * @Description : 기초관리 - 가격관리 - 가격예약
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.05  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.04.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SalePriceResveVO extends PageVO {

    private static final long serialVersionUID = 7702867811980377424L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 변경전 매장 판매가 */
    private String saleUprcP;

    /** 매장판매가 */
    private String saleUprc;

    /** 매장공급가 */
    private String storeSplyUprc;

    /** 본사마진금액 */
    private String hqMarginAmt;

    /** 본사마진율 */
    private String hqMarginRate;

    /** 현재판매금액 */
    private String saleUprcSmt;

    /** 매장마진금액 */
    private String storeMarginAmt;

    /** 매장마진율 */
    private String storeMarginRate;

    /** 주문단위수량 */
    private String poUnitQty;

    /** 주문단위수량 */
    private String prcCtrlFg;

    /** 조회 매장 */
    private String arrStoreCd[];

    /** 변경 판매가 선택 옵션 (H:본사 , S:매장) */
    private String saleAmtOption;

    /** 판매가 적용 시작일 */
    private String startDate;

    /** 판매가 적용 마지막일 */
    private String endDate;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 내점가 */
    private String stinSaleUprc;

    /** 배달가 */
    private String dlvrSaleUprc;

    /** 포장가 */
    private String packSaleUprc;

    /** 전매장적용 구분 */
    private String applyFg;

    /** 가격예약구분 - 0:일반, 1:예약 */
    private String saleResveFg;

    /**
     * workMode<br>
     * 1 : 상품정보수정<br>
     * 2 : 신규상품등록<br>
     * 3 : 매장등록<br>
     */
    private WorkModeFg workMode;

    /** 프로시져 실행 결과 */
    private String result;

    /** 사용자 아이디 */
    private String userId;

    /** 변경전 시작일자 */
    private String orgStartDate;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 세션ID */
    private String sessionId;

    /** 순번 */
    private int seq;

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

    public String getSaleUprcP() {
        return saleUprcP;
    }

    public void setSaleUprcP(String saleUprcP) {
        this.saleUprcP = saleUprcP;
    }

    public String getSaleUprc() {
        return saleUprc;
    }

    public void setSaleUprc(String saleUprc) {
        this.saleUprc = saleUprc;
    }

    public String getStoreSplyUprc() {
        return storeSplyUprc;
    }

    public void setStoreSplyUprc(String storeSplyUprc) {
        this.storeSplyUprc = storeSplyUprc;
    }

    public String getHqMarginAmt() {
        return hqMarginAmt;
    }

    public void setHqMarginAmt(String hqMarginAmt) {
        this.hqMarginAmt = hqMarginAmt;
    }

    public String getHqMarginRate() {
        return hqMarginRate;
    }

    public void setHqMarginRate(String hqMarginRate) {
        this.hqMarginRate = hqMarginRate;
    }

    public String getSaleUprcSmt() {
        return saleUprcSmt;
    }

    public void setSaleUprcSmt(String saleUprcSmt) {
        this.saleUprcSmt = saleUprcSmt;
    }

    public String getStoreMarginAmt() {
        return storeMarginAmt;
    }

    public void setStoreMarginAmt(String storeMarginAmt) {
        this.storeMarginAmt = storeMarginAmt;
    }

    public String getStoreMarginRate() {
        return storeMarginRate;
    }

    public void setStoreMarginRate(String storeMarginRate) {
        this.storeMarginRate = storeMarginRate;
    }

    public String getPoUnitQty() {
        return poUnitQty;
    }

    public void setPoUnitQty(String poUnitQty) {
        this.poUnitQty = poUnitQty;
    }

    public String getPrcCtrlFg() {
        return prcCtrlFg;
    }

    public void setPrcCtrlFg(String prcCtrlFg) {
        this.prcCtrlFg = prcCtrlFg;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    public String getSaleAmtOption() {
        return saleAmtOption;
    }

    public void setSaleAmtOption(String saleAmtOption) {
        this.saleAmtOption = saleAmtOption;
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

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getStinSaleUprc() {
        return stinSaleUprc;
    }

    public void setStinSaleUprc(String stinSaleUprc) {
        this.stinSaleUprc = stinSaleUprc;
    }

    public String getDlvrSaleUprc() {
        return dlvrSaleUprc;
    }

    public void setDlvrSaleUprc(String dlvrSaleUprc) {
        this.dlvrSaleUprc = dlvrSaleUprc;
    }

    public String getPackSaleUprc() {
        return packSaleUprc;
    }

    public void setPackSaleUprc(String packSaleUprc) {
        this.packSaleUprc = packSaleUprc;
    }

    public String getApplyFg() {
        return applyFg;
    }

    public void setApplyFg(String applyFg) {
        this.applyFg = applyFg;
    }

    public WorkModeFg getWorkMode() {
        return workMode;
    }

    public void setWorkMode(WorkModeFg workMode) {
        this.workMode = workMode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSaleResveFg() {
        return saleResveFg;
    }

    public void setSaleResveFg(String saleResveFg) {
        this.saleResveFg = saleResveFg;
    }

    public String getOrgStartDate() {
        return orgStartDate;
    }

    public void setOrgStartDate(String orgStartDate) {
        this.orgStartDate = orgStartDate;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getSessionId() { return sessionId; }

    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public int getSeq() { return seq; }

    public void setSeq(int seq) { this.seq = seq; }
}
