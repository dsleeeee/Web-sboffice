package kr.co.solbipos.kookmin.saleAnalysis.saleTotalAnalysisByTime.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name  : SaleTotalAnalysisByTimeVO.java
 * @Description : 국민대 > 매출분석 > 시간대 매출합계분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.13  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.13
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class SaleTotalAnalysisByTimeVO extends PageVO {
    private static final long serialVersionUID = -1636203327906577546L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 지점코드 */
    private String branchCd;

    /** 지점명 */
    private String branchNm;

    /** 매장코드 */
    private String storeCds;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    /** 요일구분 */
    private String yoil;

    /** 요일 리스트 */
    private String yoilList[];

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

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
    }

    public String getBranchNm() {
        return branchNm;
    }

    public void setBranchNm(String branchNm) {
        this.branchNm = branchNm;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getYoil() {
        return yoil;
    }

    public void setYoil(String yoil) {
        this.yoil = yoil;
    }

    public String[] getYoilList() {
        return yoilList;
    }

    public void setYoilList(String[] yoilList) {
        this.yoilList = yoilList;
    }
}
