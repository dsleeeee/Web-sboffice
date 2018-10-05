package kr.co.solbipos.membr.anals.credit.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.membr.anals.credit.service.enums.CreditInFg;
import kr.co.solbipos.membr.anals.credit.service.enums.CreditPayFg;

/**
 * @Class Name : CreditStoreVO.java
 * @Description : 기초관리 > 결제수단 > 후불회원등록매장, 후불등록 공통 사용
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.20  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.09.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CreditStoreVO extends CmmVO {

    /** 본사코드 */
    private String hqOfficeCd;
    /** 회원번호 */
    private String memberNo;
    /** 회원명 */
    private String memberNm;
    /** 후불회원 등록 매장 코드 */
    private String creditStoreCd;
    /** 후불회원 등록 매장 명 */
    private String creditStoreNm;
    /** 외상 입금 날짜 */
    private String saleDate;
    /** 외상 입금 일시 */
    private String creditDt;
    /** 외상 입금 구분 */
    private CreditInFg creditInFg;
    /** 외상 입금 구분 */
    private CreditPayFg creditPayFg;
    /** 외상 입금 금액 */
    private String creditAmt;
    /** 비매출 영수증번호 */
    private String nonsaleBillNo;
    /** 원거래 충전번호 */
    private String orgPrepaidNo;
    /** 전송여부 */
    private UseYn sendYn;
    /** 전송일시 */
    private String sendDt;
    /** 정렬방법 */
    private String array;
    /** 조회매장 */
    private String storeCds;
    /** 조회매장 */
    private String[] storeCdList;
    /** 기본매장  */
    private String defaultStoreCd;


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
     * @return the memberNo
     */

    public String getMemberNo() {
        return memberNo;
    }

    /**
     * @param memberNo the memberNo to set
     */
    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    /**
     * @return the memberNm
     */

    public String getMemberNm() {
        return memberNm;
    }

    /**
     * @param memberNm the memberNm to set
     */
    public void setMemberNm(String memberNm) {
        this.memberNm = memberNm;
    }

    /**
     * @return the creditStoreCd
     */

    public String getCreditStoreCd() {
        return creditStoreCd;
    }

    /**
     * @param creditStoreCd the creditStoreCd to set
     */
    public void setCreditStoreCd(String creditStoreCd) {
        this.creditStoreCd = creditStoreCd;
    }

    /**
     * @return the creditStoreNm
     */

    public String getCreditStoreNm() {
        return creditStoreNm;
    }

    /**
     * @param creditStoreNm the creditStoreNm to set
     */
    public void setCreditStoreNm(String creditStoreNm) {
        this.creditStoreNm = creditStoreNm;
    }

    /**
     * @return the saleDate
     */

    public String getSaleDate() {
        return saleDate;
    }

    /**
     * @param saleDate the saleDate to set
     */
    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    /**
     * @return the creditDt
     */

    public String getCreditDt() {
        return creditDt;
    }

    /**
     * @param creditDt the creditDt to set
     */
    public void setCreditDt(String creditDt) {
        this.creditDt = creditDt;
    }

    /**
     * @return the creditInFg
     */

    public CreditInFg getCreditInFg() {
        return creditInFg;
    }

    /**
     * @param creditInFg the creditInFg to set
     */
    public void setCreditInFg(CreditInFg creditInFg) {
        this.creditInFg = creditInFg;
    }

    /**
     * @return the creditPayFg
     */

    public CreditPayFg getCreditPayFg() {
        return creditPayFg;
    }

    /**
     * @param creditPayFg the creditPayFg to set
     */
    public void setCreditPayFg(CreditPayFg creditPayFg) {
        this.creditPayFg = creditPayFg;
    }

    /**
     * @return the creditAmt
     */

    public String getCreditAmt() {
        return creditAmt;
    }

    /**
     * @param creditAmt the creditAmt to set
     */
    public void setCreditAmt(String creditAmt) {
        this.creditAmt = creditAmt;
    }

    /**
     * @return the nonsaleBillNo
     */

    public String getNonsaleBillNo() {
        return nonsaleBillNo;
    }

    /**
     * @param nonsaleBillNo the nonsaleBillNo to set
     */
    public void setNonsaleBillNo(String nonsaleBillNo) {
        this.nonsaleBillNo = nonsaleBillNo;
    }

    /**
     * @return the orgPrepaidNo
     */

    public String getOrgPrepaidNo() {
        return orgPrepaidNo;
    }

    /**
     * @param orgPrepaidNo the orgPrepaidNo to set
     */
    public void setOrgPrepaidNo(String orgPrepaidNo) {
        this.orgPrepaidNo = orgPrepaidNo;
    }

    /**
     * @return the sendYn
     */

    public UseYn getSendYn() {
        return sendYn;
    }

    /**
     * @param sendYn the sendYn to set
     */
    public void setSendYn(UseYn sendYn) {
        this.sendYn = sendYn;
    }

    /**
     * @return the sendDt
     */

    public String getSendDt() {
        return sendDt;
    }

    /**
     * @param sendDt the sendDt to set
     */
    public void setSendDt(String sendDt) {
        this.sendDt = sendDt;
    }

    /**
     * @return the array
     */

    public String getArray() {
        return array;
    }

    /**
     * @param array the array to set
     */
    public void setArray(String array) {
        this.array = array;
    }

    /**
     * @return the storeCds
     */

    public String getStoreCds() {
        return storeCds;
    }

    /**
     * @param storeCds the storeCds to set
     */
    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    /**
     * @return the storeCdList
     */

    public String[] getStoreCdList() {
        return storeCdList;
    }

    /**
     * @param storeCdList the storeCdList to set
     */
    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }

    /**
     * @return the defaultStoreCd
     */

    public String getDefaultStoreCd() {
        return defaultStoreCd;
    }

    /**
     * @param defaultStoreCd the defaultStoreCd to set
     */
    public void setDefaultStoreCd(String defaultStoreCd) {
        this.defaultStoreCd = defaultStoreCd;
    }
}
