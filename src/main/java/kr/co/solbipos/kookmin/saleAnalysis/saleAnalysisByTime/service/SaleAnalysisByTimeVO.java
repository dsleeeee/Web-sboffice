package kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByTime.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : SaleAnalysisByTimeController.java
 * @Description : 국민대 > 매출분석 > 시간대별 매출분석(요일별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.01  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.01
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class SaleAnalysisByTimeVO extends PageVO {
    private static final long serialVersionUID = -8346496803389509753L;

    private String hqOfficeCd;
    private String storeCd;
    private String branchCd;
    private String branchNm;
    private String storeCds;
    private String storeCdQuery;
    private String yoil;
    private String yoilList[];

    private String guestCnt;
    private String guestAmt;
    private String saleQty;
    private String totSaleAmt;

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

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
    }

    public String getBranchNm() {
        return branchNm;
    }

    public void setBranchNm(String branchNm) {
        this.branchNm = branchNm;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getYoil() {
        return yoil;
    }

    public void setYoil(String yoil) {
        this.yoil = yoil;
    }

    public String[] getYoilList() {
        return yoilList;
    }

    public void setYoilList(String[] yoilList) {
        this.yoilList = yoilList;
    }

    public String getGuestCnt() {
        return guestCnt;
    }

    public void setGuestCnt(String guestCnt) {
        this.guestCnt = guestCnt;
    }

    public String getGuestAmt() {
        return guestAmt;
    }

    public void setGuestAmt(String guestAmt) {
        this.guestAmt = guestAmt;
    }

    public String getSaleQty() {
        return saleQty;
    }

    public void setSaleQty(String saleQty) {
        this.saleQty = saleQty;
    }

    public String getTotSaleAmt() {
        return totSaleAmt;
    }

    public void setTotSaleAmt(String totSaleAmt) {
        this.totSaleAmt = totSaleAmt;
    }
}
