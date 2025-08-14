package kr.co.solbipos.sale.marketing.salePerfCompare.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name : SalePerfCompareVO.java
 * @Description : 미스터피자 > 마케팅조회 > 매출실적비교
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.08  김유승      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 김유승
 * @since 2025.08.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SalePerfCompareVO extends PageVO {
    private static final long serialVersionUID = 68589420525995833L;

    private String hqOfficeCd;

    private String storeCd;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    private String compStartDate;

    private String compEndDate;

    private String dateFg;

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

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getCompStartDate() {
        return compStartDate;
    }

    public void setCompStartDate(String compStartDate) {
        this.compStartDate = compStartDate;
    }

    public String getCompEndDate() {
        return compEndDate;
    }

    public void setCompEndDate(String compEndDate) {
        this.compEndDate = compEndDate;
    }

    public String getDateFg() {
        return dateFg;
    }

    public void setDateFg(String dateFg) {
        this.dateFg = dateFg;
    }
}
