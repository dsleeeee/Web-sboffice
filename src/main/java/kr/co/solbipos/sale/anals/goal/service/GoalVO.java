package kr.co.solbipos.sale.anals.goal.service;

import kr.co.solbipos.application.common.service.PageVO;

public class GoalVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 조회월 */
    private String saleDate;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 판매자별, 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;
    /** 본사,매장 구분 */
    private String orgnFg;
    
    //매출목표등록
    /** 매출목표월 */
    private String saleGoalYm;
    /** 매출목표금액 */
    private String saleGoalAmt;
    /** 매출목표요일별금액 */
    private String saleGoalWeight;
    private String saleGoalWeight1;
    private String saleGoalWeight2;
    private String saleGoalWeight3;
    private String saleGoalWeight4;
    private String saleGoalWeight5;
    private String saleGoalWeight6;
    private String saleGoalWeight7;
    /** 매출목표일련번호 */
    private String saleGoalWeightNo;
    /** 매출목표월별가중치total */
    private String saleGoalWeightNoTot;
    /** 매출목표날자 */
    private String saleGoalDate;
    /** 마지막날자여부 */
    private String saleEndDateYn;
    /** 마지막날자 */
    private String saleEndDate;
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
   
	public String getsQuery1() {
        return sQuery1;
    }

    public void setsQuery1(String sQuery1) {
        this.sQuery1 = sQuery1;
    }

    public String getsQuery2() {
        return sQuery2;
    }

    public void setsQuery2(String sQuery2) {
        this.sQuery2 = sQuery2;
    }
    
	public String getOrgnFg() {
		return orgnFg;
	}

	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}

	public String[] getArrStoreCd() {
		return arrStoreCd;
	}

	public void setArrStoreCd(String[] arrStoreCd) {
		this.arrStoreCd = arrStoreCd;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getSaleGoalYm() {
		return saleGoalYm;
	}

	public void setSaleGoalYm(String saleGoalYm) {
		this.saleGoalYm = saleGoalYm;
	}
	
	public String getSaleGoalWeight() {
		return saleGoalWeight;
	}

	public void setSaleGoalWeight(String saleGoalWeight) {
		this.saleGoalWeight = saleGoalWeight;
	}

	public String getSaleGoalWeight1() {
		return saleGoalWeight1;
	}

	public void setSaleGoalWeight1(String saleGoalWeight1) {
		this.saleGoalWeight1 = saleGoalWeight1;
	}

	public String getSaleGoalWeight2() {
		return saleGoalWeight2;
	}

	public void setSaleGoalWeight2(String saleGoalWeight2) {
		this.saleGoalWeight2 = saleGoalWeight2;
	}

	public String getSaleGoalWeight3() {
		return saleGoalWeight3;
	}

	public void setSaleGoalWeight3(String saleGoalWeight3) {
		this.saleGoalWeight3 = saleGoalWeight3;
	}

	public String getSaleGoalWeight4() {
		return saleGoalWeight4;
	}

	public void setSaleGoalWeight4(String saleGoalWeight4) {
		this.saleGoalWeight4 = saleGoalWeight4;
	}

	public String getSaleGoalWeight5() {
		return saleGoalWeight5;
	}

	public void setSaleGoalWeight5(String saleGoalWeight5) {
		this.saleGoalWeight5 = saleGoalWeight5;
	}

	public String getSaleGoalWeight6() {
		return saleGoalWeight6;
	}

	public void setSaleGoalWeight6(String saleGoalWeight6) {
		this.saleGoalWeight6 = saleGoalWeight6;
	}

	public String getSaleGoalWeight7() {
		return saleGoalWeight7;
	}

	public void setSaleGoalWeight7(String saleGoalWeight7) {
		this.saleGoalWeight7 = saleGoalWeight7;
	}

	public String getSaleGoalAmt() {
		return saleGoalAmt;
	}

	public void setSaleGoalAmt(String saleGoalAmt) {
		this.saleGoalAmt = saleGoalAmt;
	}

	public String getSaleGoalWeightNo() {
		return saleGoalWeightNo;
	}

	public void setSaleGoalWeightNo(String saleGoalWeightNo) {
		this.saleGoalWeightNo = saleGoalWeightNo;
	}

	public String getSaleGoalWeightNoTot() {
		return saleGoalWeightNoTot;
	}

	public void setSaleGoalWeightNoTot(String saleGoalWeightNoTot) {
		this.saleGoalWeightNoTot = saleGoalWeightNoTot;
	}

	public String getSaleGoalDate() {
		return saleGoalDate;
	}

	public void setSaleGoalDate(String saleGoalDate) {
		this.saleGoalDate = saleGoalDate;
	}

	public String getSaleEndDate() {
		return saleEndDate;
	}

	public void setSaleEndDate(String saleEndDate) {
		this.saleEndDate = saleEndDate;
	}

	public String getSaleEndDateYn() {
		return saleEndDateYn;
	}

	public void setSaleEndDateYn(String saleEndDateYn) {
		this.saleEndDateYn = saleEndDateYn;
	}
	
}
