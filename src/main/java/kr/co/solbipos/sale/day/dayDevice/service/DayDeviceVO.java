package kr.co.solbipos.sale.day.dayDevice.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : DayDeviceVO.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class DayDeviceVO extends PageVO {

    private static final long serialVersionUID = 4567094906301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사브랜드코드 */
    private String hqBrandCd;

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    public String getHqOfficeCd() { return hqOfficeCd; }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getHqBrandCd() { return hqBrandCd; }

    public void setHqBrandCd(String hqBrandCd) { this.hqBrandCd = hqBrandCd; }

    public String[] getStoreCdList() {
        return storeCdList;
    }

    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

}