package kr.co.solbipos.sale.status.saleAnalysisReport.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SaleAnalysisReportVO.java
 * @Description : 매출관리 > 매출현황2 > 중분류(매출분석) 다운로드(정직유부)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.12.14  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.12.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SaleAnalysisReportVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회매장 */
    private String storeCd;

    /** 조회월 */
    private String startMonth;

    /** 조회월 */
    private String endMonth;

    /** 매장 컬럼 */
    private String storeCol;
    /** 매장 array */
    private String arrStoreCol[];
    /** 쿼리문의 PIVOT IN에 사용할 매장 컬럼 문자열 */
    private String pivotStoreCol;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getStartMonth() { return startMonth; }

    public void setStartMonth(String startMonth) { this.startMonth = startMonth; }

    public String getEndMonth() { return endMonth; }

    public void setEndMonth(String endMonth) { this.endMonth = endMonth; }

    public String getStoreCol() { return storeCol; }

    public void setStoreCol(String storeCol) { this.storeCol = storeCol; }

    public String[] getArrStoreCol() {
        return arrStoreCol;
    }

    public void setArrStoreCol(String[] arrStoreCol) {
        this.arrStoreCol = arrStoreCol;
    }

    public String getPivotStoreCol() { return pivotStoreCol; }

    public void setPivotStoreCol(String pivotStoreCol) { this.pivotStoreCol = pivotStoreCol; }
}