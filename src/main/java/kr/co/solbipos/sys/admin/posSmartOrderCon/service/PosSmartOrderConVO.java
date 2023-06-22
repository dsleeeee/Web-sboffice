package kr.co.solbipos.sys.admin.posSmartOrderCon.service;

import kr.co.solbipos.application.common.service.PageVO;

public class PosSmartOrderConVO extends PageVO {

    private static final long serialVersionUID = 4937100336516724986L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 포스번호 */
    private String posNo;
    /** 시스템일자 */
    private String sysDate;
    /** 주문 접속 타입 */
    private String connectType;
    /** 스마이오더 접속시간 */
    private String connectDt;
    /** 스마이오더 응답시간 */
    private String responseDt;
    /** 전송응답코드 */
    private String resultCode;
    /** 전송응답메시지 */
    private String resultMsg;

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

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public String getConnectType() {
        return connectType;
    }

    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }

    public String getConnectDt() {
        return connectDt;
    }

    public void setConnectDt(String connectDt) {
        this.connectDt = connectDt;
    }

    public String getResponseDt() {
        return responseDt;
    }

    public void setResponseDt(String responseDt) {
        this.responseDt = responseDt;
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
