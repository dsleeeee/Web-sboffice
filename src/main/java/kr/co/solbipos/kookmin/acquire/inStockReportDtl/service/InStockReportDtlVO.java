package kr.co.solbipos.kookmin.acquire.inStockReportDtl.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : InStockReportDtlVO.java
 * @Description : 국민대 > 매입관리 > 매입처별 상세매입내역(상품별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.05  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class InStockReportDtlVO extends PageVO {

    private static final long serialVersionUID = 66342536628520657L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장 코드 */
    private String storeCd;

    /** 거래처 코드 */
    private String vendrCd;

    /** 상품 분류 코드 */
    private String prodClassCd;

    /** 판매일자 */
    private String today;

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

    public String getVendrCd() {
        return vendrCd;
    }

    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }
}
