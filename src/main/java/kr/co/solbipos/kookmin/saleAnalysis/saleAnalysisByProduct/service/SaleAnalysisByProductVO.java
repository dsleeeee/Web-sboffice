package kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByProduct.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : SaleAnalysisByProductVO.java
 * @Description : 국민대 > 매출분석 > 상품별 매출분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.30  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class SaleAnalysisByProductVO extends PageVO {
    private static final long serialVersionUID = 2991969061187665767L;

    private String hqOfficeCd;
    private String storeCd;
    private String acquireCd;
    private String acquireNm;
    private String branchCd;
    private String branchNm;
    private String prodCd;
    private String prodNm;
    private String storeCds;
    private String storeCdQuery;



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

    public String getAcquireCd() {
        return acquireCd;
    }

    public void setAcquireCd(String acquireCd) {
        this.acquireCd = acquireCd;
    }

    public String getAcquireNm() {
        return acquireNm;
    }

    public void setAcquireNm(String acquireNm) {
        this.acquireNm = acquireNm;
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
}
