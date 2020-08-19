package kr.co.solbipos.iostock.order.instockConfm.service;

import kr.co.solbipos.application.common.service.CmmVO;

public class InstockConfmProdVO extends CmmVO {
	private static final long serialVersionUID = -3729942813464987001L;

    /*
		TB_PO_HQ_STORE_OUTSTOCK_PROD - PRIMARY KEY ("HQ_OFFICE_CD", "SLIP_NO", "PROD_CD", "STORAGE_CD")
		변경 -->
		TB_PO_HQ_STORE_OUTSTOCK_PROD - PRIMARY KEY ("HQ_OFFICE_CD", "SLIP_NO", "PROD_CD", "STORAGE_CD", "OCCR_FG")

		HQ_OFFICE_CD
		SLIP_NO
		PROD_CD
		STORAGE_CD
		OCCR_FG
		STORE_CD
		SLIP_FG
		CONFM_YN
		IN_UNIT_QTY
		IN_ETC_QTY
		IN_TOT_QTY
		IN_AMT
		IN_VAT
		IN_TOT
		REG_DT
		REG_ID
		MOD_DT
		MOD_ID
    */

	/** 본사코드 */
    private String hqOfficeCd;

    /** 전표번호 YYMM(4)+SEQ(6) */
    private String slipNo;

    /** 상품코드 */
    private String prodCd;

    /** 창고코드 */
    private String storageCd;

    /** 발생구분 */
    private String occrFg;


    /** 매장코드 */
    private String storeCd;

    /** 전표구분 1:주문 -1:반품 */
    private Integer slipFg;

    /** 확정여부(Y/N) - Trigger가  'Y'인것만 읽어서 처리하는데 사용 */
    private String confmYn;

    /** 입고수량 주문단위 */
    private Integer inUnitQty;

    /** 입고수량 나머지 */
    private Integer inEtcQty;

    /** 입고수량합계 낱개 */
    private Integer inTotQty;

    /** 입고금액 */
    private Long inAmt;

    /** 입고금액VAT */
    private Long inVat;

    /** 입고금액합계 */
    private Long inTot;
    //----------------------------------------------------------------------------------------------

	public String getHqOfficeCd() {
		return hqOfficeCd;
	}

	public void setHqOfficeCd(String hqOfficeCd) {
		this.hqOfficeCd = hqOfficeCd;
	}

	public String getSlipNo() {
		return slipNo;
	}

	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getStorageCd() {
		return storageCd;
	}

	public void setStorageCd(String storageCd) {
		this.storageCd = storageCd;
	}

	public String getOccrFg() {
		return occrFg;
	}

	public void setOccrFg(String occrFg) {
		this.occrFg = occrFg;
	}

	public String getStoreCd() {
		return storeCd;
	}

	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}

	public Integer getSlipFg() {
		return slipFg;
	}

	public void setSlipFg(Integer slipFg) {
		this.slipFg = slipFg;
	}

	public String getConfmYn() {
		return confmYn;
	}

	public void setConfmYn(String confmYn) {
		this.confmYn = confmYn;
	}

	public Integer getInUnitQty() {
		return inUnitQty;
	}

	public void setInUnitQty(Integer inUnitQty) {
		this.inUnitQty = inUnitQty;
	}

	public Integer getInEtcQty() {
		return inEtcQty;
	}

	public void setInEtcQty(Integer inEtcQty) {
		this.inEtcQty = inEtcQty;
	}

	public Integer getInTotQty() {
		return inTotQty;
	}

	public void setInTotQty(Integer inTotQty) {
		this.inTotQty = inTotQty;
	}

	public Long getInAmt() {
		return inAmt;
	}

	public void setInAmt(Long inAmt) {
		this.inAmt = inAmt;
	}

	public Long getInVat() {
		return inVat;
	}

	public void setInVat(Long inVat) {
		this.inVat = inVat;
	}

	public Long getInTot() {
		return inTot;
	}

	public void setInTot(Long inTot) {
		this.inTot = inTot;
	}


}
