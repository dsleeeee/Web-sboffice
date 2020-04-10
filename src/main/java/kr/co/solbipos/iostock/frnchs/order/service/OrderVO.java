package kr.co.solbipos.iostock.frnchs.order.service;

import kr.co.solbipos.application.common.service.PageVO;

public class OrderVO extends PageVO {

    private static final long serialVersionUID = 2847554288440574225L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    private String arrStoreCd[];
    /** 전표번호 YYMM(4)+SEQ(4) */
    private String slipNo;
    /** 판매구분 */
    private String slipFg;
    /** 입고전표번호 YYMM(4)+SEQ(4) */
    private String inSlipNo;
    /** 상품코드 */
    private String prodCd;
    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    
    /** 출고일자, 출고예약일자 구분 */
    private String outDateFg;
    /** 진행상태 구분 */
    private String procFg;
    
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
     * @return the slipNo
     */
    public String getSlipNo() {
        return slipNo;
    }

    /**
     * @param slipNo the slipNo to set
     */
    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    /**
     * @return the inSlipNo
     */
    public String getInSlipNo() {
        return inSlipNo;
    }

    /**
     * @param inSlipNo the inSlipNo to set
     */
    public void setInSlipNo(String inSlipNo) {
        this.inSlipNo = inSlipNo;
    }

    /**
     * @return the prodCd
     */
    public String getProdCd() {
        return prodCd;
    }

    /**
     * @param prodCd the prodCd to set
     */
    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    /**
     * @return the orgnFg
     */
    public String getOrgnFg() {
        return orgnFg;
    }

    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

	public String getOutDateFg() {
		return outDateFg;
	}

	public void setOutDateFg(String outDateFg) {
		this.outDateFg = outDateFg;
	}

	public String getSlipFg() {
		return slipFg;
	}

	public void setSlipFg(String slipFg) {
		this.slipFg = slipFg;
	}

	public String[] getArrStoreCd() {
		return arrStoreCd;
	}

	public void setArrStoreCd(String arrStoreCd[]) {
		this.arrStoreCd = arrStoreCd;
	}

	public String getProcFg() {
		return procFg;
	}

	public void setProcFg(String procFg) {
		this.procFg = procFg;
	}
}
