package kr.co.solbipos.sys.admin2.saleBillDataChk.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SaleBillDataChkVO.java
 * @Description : 시스템관리 > 관리자기능2 > 매출자료검증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.11.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.11.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SaleBillDataChkVO extends PageVO {

    private static final long serialVersionUID = -2846219318417524713L;

    /** 매장코드 */
    private String storeCd;
    /** 영업일자 */
    private String saleDate;
    /** 포스번호 */
    private String posNo;
    /** 영수번호 */
    private String billNo;
    /** 결과코드 */
    private String resultCode;
    /** 결과메시지 */
    private String resultMsg;

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

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
