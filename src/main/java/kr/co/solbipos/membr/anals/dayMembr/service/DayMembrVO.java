package kr.co.solbipos.membr.anals.dayMembr.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : DayMembrVO.java
 * @Description : 회원관리 > 회원분석 > 일자별회원 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.08.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class DayMembrVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 조회매장 */
    private String storeCd;

    /** PIVOT PAY_CD */
    private String pivotPayCol;

    /** 영업일자 */
    private String saleDate;

    /** 포스번호 */
    private String posNo;

    /** 영수증번호 */
    private String billNo;

    /** 회원번호 */
    private String membrNo;

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getPivotPayCol() {
        return pivotPayCol;
    }

    public void setPivotPayCol(String pivotPayCol) {
        this.pivotPayCol = pivotPayCol;
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

    public String getMembrNo() {
        return membrNo;
    }

    public void setMembrNo(String membrNo) {
        this.membrNo = membrNo;
    }
}
