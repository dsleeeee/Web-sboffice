package kr.co.solbipos.kookmin.generalInfo.saleAnalysisByMonthly.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name  : SaleAnalysisByMonthlyVO.java
 * @Description : 국민대 > 매출분석 > 월별매출분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.23  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.10.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class SaleAnalysisByMonthlyVO extends PageVO {

    private static final long serialVersionUID = 8643115199971428320L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 시작일자 */
    private String startDate;
    /** 종료일자 */
    private String endDate;
    /** 지난해 시작일자 */
    private String lastStartDate;
    /** 지난해 종료일자 */
    private String lastEndDate;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
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

    public String getLastStartDate() {
        return lastStartDate;
    }

    public void setLastStartDate(String lastStartDate) {
        this.lastStartDate = lastStartDate;
    }

    public String getLastEndDate() {
        return lastEndDate;
    }

    public void setLastEndDate(String lastEndDate) {
        this.lastEndDate = lastEndDate;
    }
}
