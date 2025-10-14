package kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByVendr.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : SaleAnalysisByVendrVO.java
 * @Description : 국민대 > 매출분석 > 매입처별 매출분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class SaleAnalysisByVendrVO extends PageVO {
    private static final long serialVersionUID = 5373113006502964118L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 지점코드 */
    private String branchCd;

    /** 지점명 */
    private String branchNm;

    /** 매입처코드 */
    private String acquireCd;

    /** 매입처명 */
    private String acquireNm;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 상품분류코드 */
    private String arrProdClassCd[];

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

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String[] getArrProdClassCd() {
        return arrProdClassCd;
    }

    public void setArrProdClassCd(String[] arrProdClassCd) {
        this.arrProdClassCd = arrProdClassCd;
    }
}
