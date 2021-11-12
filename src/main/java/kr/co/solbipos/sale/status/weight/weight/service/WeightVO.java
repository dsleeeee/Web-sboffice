package kr.co.solbipos.sale.status.weight.weight.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : WeightVO.java
 * @Description : 매출관리 > 매출현황2 > 중량별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.11.08  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.11.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class WeightVO extends PageVO {

	private static final long serialVersionUID = 1L;

	/** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 검색 시작일 */
    private String startDate;

    /** 검색 종료일 */
    private String endDate;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public String getOrgnFg() {
		return orgnFg;
	}

	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}

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

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }
}
