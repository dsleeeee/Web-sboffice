package kr.co.solbipos.sale.anals.salePerfCompareBenson.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name : SalePerfCompareBensonVO.java
 * @Description : 벤슨 > 매출분석 > 대비기간 매출실적
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SalePerfCompareBensonVO extends PageVO {
    private static final long serialVersionUID = 68589420525995833L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    /** 시작일자 */
    private String compStartDate;

    /** 종료일자 */
    private String compEndDate;

    /** 날짜구분 */
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
