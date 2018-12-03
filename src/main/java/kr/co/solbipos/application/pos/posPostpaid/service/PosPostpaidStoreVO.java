package kr.co.solbipos.application.pos.posPostpaid.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.membr.anals.postpaid.service.enums.PostpaidFg;
import kr.co.solbipos.membr.anals.postpaid.service.enums.PostpaidPayFg;

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
    private String requestAmt;


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

    public String getRequestAmt() {
        return requestAmt;
    }

    /**
     * @param requestAmt the requestAmt to set
     */
    public void setRequestAmt(String requestAmt) {
        this.requestAmt = requestAmt;
    }
}
