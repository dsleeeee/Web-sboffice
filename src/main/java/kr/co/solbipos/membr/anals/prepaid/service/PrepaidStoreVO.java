package kr.co.solbipos.membr.anals.prepaid.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.membr.anals.prepaid.enums.PrepaidFg;
import kr.co.solbipos.membr.anals.prepaid.enums.PrepaidPayFg;

/**
 * @Class Name : PrepaidStoreVO.java
 * @Description : 기초관리 > 결제수단 > 선불회원등록매장, 선불등록 공통 사용
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.01  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.10.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PrepaidStoreVO extends PageVO {

    /** 본사코드 */
    private String hqOfficeCd;
    /** 회원번호 */
    private String memberNo;
    /** 회원명 */
    private String memberNm;
    /** 후불회원 등록 매장 코드 */
    private String prepaidStoreCd;
    /** 후불회원 등록 매장 명 */
    private String prepaidStoreNm;
    /** 외상 입금 날짜 */
    private String saleDate;
    /** 외상 입금 일시 */
    private String prepaidDt;
    /** 외상 입금 구분 */
    private PrepaidFg prepaidFg;
    /** 외상 입금 구분 */
    private PrepaidPayFg prepaidPayFg;
    /** 외상 입금 금액 */
    private String prepaidAmt;/** 비매출 승인번호 */
    private String nonsaleTypeApprNo;
    /** 원거래비매출승인번호 */
    private String orgNonsaleTypeApprNo;
    /** 비매출 영수증 번호 */
    private String nonsaleBillNo;
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
     * @return the prepaidStoreCd
     */

    public String getPrepaidStoreCd() {
        return prepaidStoreCd;
    }

    /**
     * @param prepaidStoreCd the prepaidStoreCd to set
     */
    public void setPrepaidStoreCd(String prepaidStoreCd) {
        this.prepaidStoreCd = prepaidStoreCd;
    }

    /**
     * @return the prepaidStoreNm
     */

    public String getPrepaidStoreNm() {
        return prepaidStoreNm;
    }

    /**
     * @param prepaidStoreNm the prepaidStoreNm to set
     */
    public void setPrepaidStoreNm(String prepaidStoreNm) {
        this.prepaidStoreNm = prepaidStoreNm;
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
     * @return the prepaidDt
     */

    public String getPrepaidDt() {
        return prepaidDt;
    }

    /**
     * @param prepaidDt the prepaidDt to set
     */
    public void setPrepaidDt(String prepaidDt) {
        this.prepaidDt = prepaidDt;
    }

    /**
     * @return the prepaidInFg
     */

    public PrepaidFg getPrepaidFg() {
        return prepaidFg;
    }

    /**
     * @param prepaidInFg the prepaidInFg to set
     */
    public void setPrepaidFg(PrepaidFg prepaidInFg) {
        this.prepaidFg = prepaidInFg;
    }

    /**
     * @return the prepaidPayFg
     */

    public PrepaidPayFg getPrepaidPayFg() {
        return prepaidPayFg;
    }

    /**
     * @param prepaidPayFg the prepaidPayFg to set
     */
    public void setPrepaidPayFg(PrepaidPayFg prepaidPayFg) {
        this.prepaidPayFg = prepaidPayFg;
    }

    /**
     * @return the prepaidAmt
     */

    public String getPrepaidAmt() {
        return prepaidAmt;
    }

    /**
     * @param prepaidAmt the prepaidAmt to set
     */
    public void setPrepaidAmt(String prepaidAmt) {
        this.prepaidAmt = prepaidAmt;
    }


    /**
     * @return the nonsaleTypeApprNo
     */

    public String getNonsaleTypeApprNo() {
        return nonsaleTypeApprNo;
    }

    /**
     * @param nonsaleTypeApprNo the nonsaleTypeApprNo to set
     */
    public void setNonsaleTypeApprNo(String nonsaleTypeApprNo) {
        this.nonsaleTypeApprNo = nonsaleTypeApprNo;
    }

    /**
     * @return the orgNonsaleTypeApprNo
     */

    public String getOrgNonsaleTypeApprNo() {
        return orgNonsaleTypeApprNo;
    }

    /**
     * @param orgNonsaleTypeApprNo the orgNonsaleTypeApprNo to set
     */
    public void setOrgNonsaleTypeApprNo(String orgNonsaleTypeApprNo) {
        this.orgNonsaleTypeApprNo = orgNonsaleTypeApprNo;
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
