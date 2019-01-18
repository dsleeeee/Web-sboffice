package kr.co.solbipos.membr.anals.postpaid.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.postpaid.enums.PostpaidFg;
import kr.co.solbipos.membr.anals.postpaid.enums.PostpaidPayFg;

/**
 * @Class Name : PostpaidStoreVO.java
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
public class PostpaidStoreVO extends PageVO {

    /** 회원소속구분 */
    private OrgnFg membrOrgnFg;
    /** 회원소속코드 */
    private String membrOrgnCd;
    /** 회원번호 */
    private String membrNo;
    /** 회원명 */
    private String membrNm;
    /** 후불회원 등록 매장 코드 */
    private String storeCd;
    /** 후불회원 등록 매장 명 */
    private String storeNm;
    /** 사용여부 */
    private UseYn useYn;
    /** 후불 입금 날짜 */
    private String saleDate;
    /** 후불 입금 일시 */
    private String postpaidDt;
    /** 후불 구분 */
    private PostpaidFg postpaidFg;
    /** 후불 입금 구분 */
    private PostpaidPayFg postpaidPayFg;
    /** 후불 입금 금액 */
    private Double postpaidAmt;
    /** 비매출 승인번호 */
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
    /** 등록여부  */
    private UseYn regYn;

    /**
     * @return the membrOrgnFg
     */

    public OrgnFg getMembrOrgnFg() {
        return membrOrgnFg;
    }

    /**
     * @param membrOrgnFg the membrOrgnFg to set
     */
    public void setMembrOrgnFg(OrgnFg membrOrgnFg) {
        this.membrOrgnFg = membrOrgnFg;
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
     * @return the useYn
     */

    public UseYn getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(UseYn useYn) {
        this.useYn = useYn;
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
     * @return the postpaidDt
     */

    public String getPostpaidDt() {
        return postpaidDt;
    }

    /**
     * @param postpaidDt the postpaidDt to set
     */
    public void setPostpaidDt(String postpaidDt) {
        this.postpaidDt = postpaidDt;
    }

    /**
     * @return the postpaidFg
     */

    public PostpaidFg getPostpaidFg() {
        return postpaidFg;
    }

    /**
     * @param postpaidFg the postpaidFg to set
     */
    public void setPostpaidFg(PostpaidFg postpaidFg) {
        this.postpaidFg = postpaidFg;
    }

    /**
     * @return the postpaidPayFg
     */

    public PostpaidPayFg getPostpaidPayFg() {
        return postpaidPayFg;
    }

    /**
     * @param postpaidPayFg the postpaidPayFg to set
     */
    public void setPostpaidPayFg(PostpaidPayFg postpaidPayFg) {
        this.postpaidPayFg = postpaidPayFg;
    }


    /**
     * @return the postpaidAmt
     */

    public Double getPostpaidAmt() {
        return postpaidAmt;
    }

    /**
     * @param postpaidAmt the postpaidAmt to set
     */
    public void setPostpaidAmt(Double postpaidAmt) {
        this.postpaidAmt = postpaidAmt;
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

    /**
     * @return the regYn
     */

    public UseYn getRegYn() {
        return regYn;
    }

    /**
     * @param regYn the regYn to set
     */
    public void setRegYn(UseYn regYn) {
        this.regYn = regYn;
    }
}
