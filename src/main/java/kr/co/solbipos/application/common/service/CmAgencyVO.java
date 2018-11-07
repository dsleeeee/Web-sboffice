package kr.co.solbipos.application.common.service;

/**
 * @Class Name : CmAgencyVO.java
 * @Description : [공통] 대리점 정보 조회용
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.08  김영근      최초생성
 *
 * @author nhn kcp 김영근
 * @since 2018. 08.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CmAgencyVO extends CmmVO {

	private static final long serialVersionUID = -4697297982932829979L;

	/** 대리점코드 */
	private String agencyCd;
	/** 대리점명 */
	private String agencyNm;
	/**
	 * @return the agencyCd
	 */
	public String getAgencyCd() {
		return agencyCd;
	}
	/**
	 * @param agencyCd the agencyCd to set
	 */
	public void setAgencyCd(String agencyCd) {
		this.agencyCd = agencyCd;
	}
	/**
	 * @return the agencyNm
	 */
	public String getAgencyNm() {
		return agencyNm;
	}
	/**
	 * @param agencyNm the agencyNm to set
	 */
	public void setAgencyNm(String agencyNm) {
		this.agencyNm = agencyNm;
	}
}
