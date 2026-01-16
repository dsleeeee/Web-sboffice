package kr.co.solbipos.sale.anals.onlineOrder.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name  : OnlineOrderVO.java
 * @Description : 매출관리 > 매출분석 > 온라인주문확인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.16  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.16
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class OnlineOrderVO extends PageVO {

    private static final long serialVersionUID = 7192483552168014591L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 일자 */
    private String saleDate;

    /** 영수증번호 */
    private String billNo;

    /** 오더주문번호 */
    private String vorderNo;

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

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getVorderNo() {
        return vorderNo;
    }

    public void setVorderNo(String vorderNo) {
        this.vorderNo = vorderNo;
    }
}
