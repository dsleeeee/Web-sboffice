package kr.co.solbipos.sale.status.item.item.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name : ItemItemVO.java
 * @Description : 기초관리 > 매장관리 > 매장사원관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.16  hblee      최초생성
 * @ 2018.11.26  김지은     UseYn 수정
 *
 * @author NHN한국사이버결제 이한빈
 * @since 2018.08.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ItemItemVO extends PageVO {

	private static final long serialVersionUID = 1L;

	/** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    private String orgnFg;

	/** 업무구분코드 */
    private String busiFg;

    /** 표시항목코드 */
	private String showHdCd;

	/** 표시여부 */
    private Boolean showYn;

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

    /**
     * @return the busiFg
     */
    public String getBusiFg() {
		return busiFg;
	}

    /**
     * @param hqOfficeCd the busiFg to set
     */
	public void setBusiFg(String busiFg) {
		this.busiFg = busiFg;
	}

	/**
     * @return the showHdCd
     */
	public String getShowHdCd() {
		return showHdCd;
	}

	/**
     * @param hqOfficeCd the showHdCd to set
     */
	public void setShowHdCd(String showHdCd) {
		this.showHdCd = showHdCd;
	}

	/**
     * @return the showYn
     */
	public Boolean getShowYn() {
		return showYn;
	}

	/**
     * @param hqOfficeCd the showYn to set
     */
	public void setShowYn(Boolean showYn) {
		this.showYn = showYn;
	}

}
