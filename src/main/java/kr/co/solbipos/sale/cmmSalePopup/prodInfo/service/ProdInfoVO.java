package kr.co.solbipos.sale.cmmSalePopup.prodInfo.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : ProdInfoVO.java
 * @Description : 매출공통팝업 - 상품매출 상세내역 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.12.11  김설아      수정
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.12.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ProdInfoVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    /** 조회매장 */
    private String storeCd;

    /** 매출일자 */
    private String saleDate;

    /** 년월 */
    private String yearMonth;

    /** 팝업호출시 구분 */
    private String gubun;

    /** POS 번호 */
    private String posNo;

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    public String[] getStoreCdList() {
        return storeCdList;
    }

    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }

    public String getStoreCds() { return storeCds; }

    public void setStoreCds(String storeCds) { this.storeCds = storeCds; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getSaleDate() { return saleDate; }

    public void setSaleDate(String saleDate) { this.saleDate = saleDate; }

    public String getYearMonth() { return yearMonth; }

    public void setYearMonth(String yearMonth) { this.yearMonth = yearMonth; }

    public String getGubun() { return gubun; }

    public void setGubun(String gubun) { this.gubun = gubun; }

    public String getPosNo() { return posNo; }

    public void setPosNo(String posNo) { this.posNo = posNo; }
}