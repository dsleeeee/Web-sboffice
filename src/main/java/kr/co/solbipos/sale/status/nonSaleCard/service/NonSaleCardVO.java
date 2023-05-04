package kr.co.solbipos.sale.status.nonSaleCard.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : NonSaleCardVO.java
 * @Description : 매출관리 > 승인현황 > 비매출카드상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.03  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.05.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class NonSaleCardVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;
    private String arrStoreCd[];

    /** POS번호 */
    private String posNo;
    private String arrPosNo[];

    /** 승인구분 */
    private String saleYn;

    /** 승인처리 */
    private String apprProcFg;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String[] getArrStoreCd() { return arrStoreCd; }

    public void setArrStoreCd(String[] arrStoreCd) { this.arrStoreCd = arrStoreCd; }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String[] getArrPosNo() {
        return arrPosNo;
    }

    public void setArrPosNo(String[] arrPosNo) {
        this.arrPosNo = arrPosNo;
    }

    public String getSaleYn() {
        return saleYn;
    }

    public void setSaleYn(String saleYn) {
        this.saleYn = saleYn;
    }

    public String getApprProcFg() {
        return apprProcFg;
    }

    public void setApprProcFg(String apprProcFg) {
        this.apprProcFg = apprProcFg;
    }
}