package kr.co.solbipos.iostock.order.storeClose.service;

import kr.co.solbipos.application.common.service.CmmVO;

public class StoreCloseVO extends CmmVO {

    private static final long serialVersionUID = -3952103756990956723L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 마감월 */
    private String closeMonth;
    /** 마감일자 */
    private String closeDate;
    /** 마감일자 display용 */
    private String closeDateNm;
    /** 이전주문구분 0:주문허용 1:주문마감 */
    private Boolean prevOrderCloseFg;
    /** 주문구분 0:주문허용 1:주문마감 */
    private Boolean orderCloseFg;
    /** 주문구분 등록일시 */
    private String orderCloseDt;
    /** 주문구분 등록자아이디 */
    private String orderCloseId;
    /** 주문구분 등록자명 */
    private String orderCloseNm;
    /** 마감월 전체일수 */
    private String dateCnt;
    /** 마감월 마감일수 */
    private int closeDateCnt;

    /**
     * @return the hqOfficeCd
     */
    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    /**
     * @return the closeMonth
     */
    public String getCloseMonth() {
        return closeMonth;
    }

    /**
     * @param closeMonth the closeMonth to set
     */
    public void setCloseMonth(String closeMonth) {
        this.closeMonth = closeMonth;
    }

    /**
     * @return the closeDate
     */
    public String getCloseDate() {
        return closeDate;
    }

    /**
     * @param closeDate the closeDate to set
     */
    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    /**
     * @return the closeDateNm
     */
    public String getCloseDateNm() {
        return closeDateNm;
    }

    /**
     * @param closeDateNm the closeDateNm to set
     */
    public void setCloseDateNm(String closeDateNm) {
        this.closeDateNm = closeDateNm;
    }

    /**
     * @return the prevOrderCloseFg
     */
    public Boolean getPrevOrderCloseFg() {
        return prevOrderCloseFg;
    }

    /**
     * @param prevOrderCloseFg the prevOrderCloseFg to set
     */
    public void setPrevOrderCloseFg(Boolean prevOrderCloseFg) {
        this.prevOrderCloseFg = prevOrderCloseFg;
    }

    /**
     * @return the orderCloseFg
     */
    public Boolean getOrderCloseFg() {
        return orderCloseFg;
    }

    /**
     * @param orderCloseFg the orderCloseFg to set
     */
    public void setOrderCloseFg(Boolean orderCloseFg) {
        this.orderCloseFg = orderCloseFg;
    }

    /**
     * @return the orderCloseDt
     */
    public String getOrderCloseDt() {
        return orderCloseDt;
    }

    /**
     * @param orderCloseDt the orderCloseDt to set
     */
    public void setOrderCloseDt(String orderCloseDt) {
        this.orderCloseDt = orderCloseDt;
    }

    /**
     * @return the orderCloseId
     */
    public String getOrderCloseId() {
        return orderCloseId;
    }

    /**
     * @param orderCloseId the orderCloseId to set
     */
    public void setOrderCloseId(String orderCloseId) {
        this.orderCloseId = orderCloseId;
    }

    /**
     * @return the orderCloseNm
     */
    public String getOrderCloseNm() {
        return orderCloseNm;
    }

    /**
     * @param orderCloseNm the orderCloseNm to set
     */
    public void setOrderCloseNm(String orderCloseNm) {
        this.orderCloseNm = orderCloseNm;
    }

    /**
     * @return the dateCnt
     */
    public String getDateCnt() {
        return dateCnt;
    }

    /**
     * @param dateCnt the dateCnt to set
     */
    public void setDateCnt(String dateCnt) {
        this.dateCnt = dateCnt;
    }

    /**
     * @return the closeDateCnt
     */
    public int getCloseDateCnt() {
        return closeDateCnt;
    }

    /**
     * @param closeDateCnt the closeDateCnt to set
     */
    public void setCloseDateCnt(int closeDateCnt) {
        this.closeDateCnt = closeDateCnt;
    }
}
