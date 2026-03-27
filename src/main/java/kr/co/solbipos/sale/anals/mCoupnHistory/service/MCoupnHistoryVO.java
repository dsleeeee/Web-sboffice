package kr.co.solbipos.sale.anals.mCoupnHistory.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MCoupnHistoryVO.java
 * @Description :  매출관리 > 매출분석 > 모바일쿠폰이력조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.24  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.03.24
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class MCoupnHistoryVO extends PageVO {

    private static final long serialVersionUID = -1170798160239311828L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 시작일자 */
    private String startDate;
    /** 종료일자 */
    private String endDate;
    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;
    /** 모바일쿠폰바코드번호 */
    private String mCoupnBarcdNo;
    /** 승인번호 */
    private String apprNo;
    /** 모바일쿠폰사코드 */
    private String mCoupnCd;
    /** 영수타입 */
    private String billType;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getmCoupnBarcdNo() {
        return mCoupnBarcdNo;
    }

    public void setmCoupnBarcdNo(String mCoupnBarcdNo) {
        this.mCoupnBarcdNo = mCoupnBarcdNo;
    }

    public String getApprNo() {
        return apprNo;
    }

    public void setApprNo(String apprNo) {
        this.apprNo = apprNo;
    }

    public String getmCoupnCd() {
        return mCoupnCd;
    }

    public void setmCoupnCd(String mCoupnCd) {
        this.mCoupnCd = mCoupnCd;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }
}
