package kr.co.solbipos.membr.anals.taxBill.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.membr.anals.enums.StatusFg;

/**
 * @Class Name : TaxBillVO.java
 * @Description : 회원관리 > 회원분석 > 세금계산서 발행 목록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.13  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.12.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class TaxBillVO extends PageVO {

    /** 세금계산서 발행일자 */
    private String billDate;

    /** 세금계산서 일련번호 */
    private String billSeq;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 회원번호 */
    private String membrNo;

    /** 회원명 */
    private String membrNm;

    /** 매장 코드 */
    private String storeCd;

    /** 등록 매장 명 */
    private String storeNm;

    /** 발행 요청 날짜 */
    private String requestDt;

    /** 발행 요청 금액 */
    private Double requestAmt;

    /** 발행 상태 */
    private StatusFg statusFg;

    /** 전체기간 체크 */
    private boolean chkDt;

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
     * @return the membrOrgnCd
     */

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    /**
     * @param membrOrgnCd the membrOrgnCd to set
     */
    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
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

    public Double getRequestAmt() {
        return requestAmt;
    }

    /**
     * @param requestAmt the requestAmt to set
     */
    public void setRequestAmt(Double requestAmt) {
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
     * @return the chkDt
     */

    public boolean isChkDt() {
        return chkDt;
    }

    /**
     * @param chkDt the chkDt to set
     */
    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
    }
}
