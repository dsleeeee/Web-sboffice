package kr.co.solbipos.sys.admin.saleChk.service;

import kr.co.solbipos.application.common.service.PageVO;

public class SaleChkVO extends PageVO {

    private static final long serialVersionUID = 3457905152351659714L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 매출구분 */
    private String saleYn;
    /** 체크항목 */
    private String checkNm;
    /** 오류타입 */
    private String remark;
    /** 처리메시지 */
    private String resultMsg;
    /** 처리구분 */
    private String procYn;
    /** 체크항목코드 */
    private String checkCd;
    /** 매장코드 */
    private String storeCd2;
    /** 매출일자 */
    private String saleDate2;
    /** 포스번호 */
    private String posNo2;
    /** 영수증번호 */
    private String billNo2;
    /** 체크항목코드 */
    private String checkCd2;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getSaleYn() {
        return saleYn;
    }

    public void setSaleYn(String saleYn) {
        this.saleYn = saleYn;
    }

    public String getCheckNm() {
        return checkNm;
    }

    public void setCheckNm(String checkNm) {
        this.checkNm = checkNm;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getProcYn() {
        return procYn;
    }

    public void setProcYn(String procYn) {
        this.procYn = procYn;
    }

    public String getCheckCd() {
        return checkCd;
    }

    public void setCheckCd(String checkCd) {
        this.checkCd = checkCd;
    }

    public String getStoreCd2() {
        return storeCd2;
    }

    public void setStoreCd2(String storeCd2) {
        this.storeCd2 = storeCd2;
    }

    public String getSaleDate2() {
        return saleDate2;
    }

    public void setSaleDate2(String saleDate2) {
        this.saleDate2 = saleDate2;
    }

    public String getPosNo2() {
        return posNo2;
    }

    public void setPosNo2(String posNo2) {
        this.posNo2 = posNo2;
    }

    public String getBillNo2() {
        return billNo2;
    }

    public void setBillNo2(String billNo2) {
        this.billNo2 = billNo2;
    }

    public String getCheckCd2() {
        return checkCd2;
    }

    public void setCheckCd2(String checkCd2) {
        this.checkCd2 = checkCd2;
    }
}
