package kr.co.solbipos.application.pos.posPostpaid.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.membr.anals.enums.StatusFg;

/**
 * @Class Name : PosPostpaidStoreVO.java
 * @Description : 포스 > 세금계산서 발행 요청
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.03  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.12.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PosPostpaidStoreVO extends CmmVO {

    /** 세금계산서 발행일자 */
    private String billDate;
    /** 세금계산서 일련번호 */
    private String billSeq;
    /** 회원소속코드 */
    private String hqOfficeCd;
    /** 후불회원 등록 매장 코드 */
    private String storeCd;
    /** 후불회원 등록 매장 명 */
    private String storeNm;
    /** 회원번호 */
    private String membrNo;
    /** 회원명 */
    private String membrNm;
    /** 세금계산서 발행 요청 일시 */
    private String requestDt;
    /** 세금계산서 발행 요청 금액 */
    private int requestAmt;
    /** 세금계산서 발행 요청 상태 */
    private StatusFg statusFg;
    /** 비고 */
    private String remark;


    /**
     * @return the billDate
     */

    public String getBillDate() {
        return billDate;
    }

    /**
     * @param billDate the billDate to set
     */
    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    /**
     * @return the billSeq
     */

    public String getBillSeq() {
        return billSeq;
    }

    /**
     * @param billSeq the billSeq to set
     */
    public void setBillSeq(String billSeq) {
        this.billSeq = billSeq;
    }

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
     * @return the storeCd
     */

    public String getStoreCd() {
        return storeCd;
    }

    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    /**
     * @return the storeNm
     */

    public String getStoreNm() {
        return storeNm;
    }

    /**
     * @param storeNm the storeNm to set
     */
    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    /**
     * @return the membrNo
     */

    public String getMembrNo() {
        return membrNo;
    }

    /**
     * @param membrNo the membrNo to set
     */
    public void setMembrNo(String membrNo) {
        this.membrNo = membrNo;
    }

    /**
     * @return the membrNm
     */

    public String getMembrNm() {
        return membrNm;
    }

    /**
     * @param membrNm the membrNm to set
     */
    public void setMembrNm(String membrNm) {
        this.membrNm = membrNm;
    }

    /**
     * @return the requestDt
     */

    public String getRequestDt() {
        return requestDt;
    }

    /**
     * @param requestDt the requestDt to set
     */
    public void setRequestDt(String requestDt) {
        this.requestDt = requestDt;
    }

    /**
     * @return the requestAmt
     */

    public int getRequestAmt() {
        return requestAmt;
    }

    /**
     * @param requestAmt the requestAmt to set
     */
    public void setRequestAmt(int requestAmt) {
        this.requestAmt = requestAmt;
    }

    /**
     * @return the statusFg
     */

    public StatusFg getStatusFg() {
        return statusFg;
    }

    /**
     * @param statusFg the statusFg to set
     */
    public void setStatusFg(StatusFg statusFg) {
        this.statusFg = statusFg;
    }

    /**
     * @return the remark
     */

    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
