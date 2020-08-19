package kr.co.solbipos.iostock.frnchs.store.service;

import kr.co.solbipos.application.common.service.PageVO;

public class FrnchsStoreVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 매장명 */
    private String storeNm;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 본사,매장 구분 */
    private String orgnFg;
    /** 전표구분 1:출고 -1:반품 */
    private Integer slipFg;
    /** 전표수 */
    private Integer slipCnt;
    /** 주문금액 */
    private Long orderTot;
    /** 분배금액 */
    private Long mdTot;
    /** 입고금액 */
    private Long inTot;
    /** 출고금액*/
    private Long outTot;
    /** 출고가액 */
    private Long outAmt;
    /** 출고부가세 */
    private Long outVat;
    /** 사업자번호 */
    private String bizNo;
    /** 사업자매장명 */
    private String bizStoreNm;
    /** 우편번호 */
    private String postNo;
    /** 주소 */
    private String addr;
    /** 종목 */
    private String storeTypeNm;
    /** 업태 */
    private String clsFgNm;
    /** Email */
    private String email;
    /** 원산지코드 */
    private String orplceCd;
    /** 원산지명 */
    private String orplceNm;
    /** 주문단위 */
    private String poUnitFgNm;
    /** 입수 */
    private Integer poUnitQty;
    /** 공급단가 */
    private Integer splyUprc;
    /** 수량 */
    private Integer totQty;
    /** 금액 */
    private Long totAmt;
    /** VAT */
    private Long totVat;
    /** 합계 */
    private Long totTot;
    
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
     * @return the prodNm
     */
    public String getProdNm() {
        return prodNm;
    }

    /**
     * @param prodNm the prodNm to set
     */
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }
    
    /**
     * @return the prodClassCd
     */
    public String getProdClassCd() {
        return prodClassCd;
    }

    /**
     * @param prodClassCd the prodClassCd to set
     */
    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }
    
    /**
     * @return the arrStoreCd
     */
    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    /**
     * @param arrStoreCd the arrStoreCd to set
     */
    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }
    
    /**
     * @return the storeNm
     */
    public String getStoreNm() {
		return storeNm;
	}

    /**
     * @param storeNm the storeNm to set
     */
	public void setStoreNm(String storeNm) {
		this.storeNm = storeNm;
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

	/**
     * @return the slipFg
     */
	public Integer getSlipFg() {
		return slipFg;
	}

	/**
     * @param slipFg the slipFg to set
     */
	public void setSlipFg(Integer slipFg) {
		this.slipFg = slipFg;
	}

	/**
     * @return the slipCnt
     */
	public Integer getSlipCnt() {
		return slipCnt;
	}

	/**
     * @param slipCnt the slipCnt to set
     */
	public void setSlipCnt(Integer slipCnt) {
		this.slipCnt = slipCnt;
	}

	/**
     * @return the orderTot
     */
	public Long getOrderTot() {
		return orderTot;
	}

	/**
     * @param orderTot the orderTot to set
     */
	public void setOrderTot(Long orderTot) {
		this.orderTot = orderTot;
	}

	/**
     * @return the mdTot
     */
	public Long getMdTot() {
		return mdTot;
	}

	/**
     * @param mdTot the mdTot to set
     */
	public void setMdTot(Long mdTot) {
		this.mdTot = mdTot;
	}

	/**
     * @return the inTot
     */
	public Long getInTot() {
		return inTot;
	}

	/**
     * @param inTot the inTot to set
     */
	public void setInTot(Long inTot) {
		this.inTot = inTot;
	}

	/**
     * @return the outAmt
     */
	public Long getOutAmt() {
		return outAmt;
	}

	/**
     * @param outAmt the outAmt to set
     */
	public void setOutAmt(Long outAmt) {
		this.outAmt = outAmt;
	}

	/**
     * @return the outVat
     */
	public Long getOutVat() {
		return outVat;
	}

	/**
     * @param outVat the outVat to set
     */
	public void setOutVat(Long outVat) {
		this.outVat = outVat;
	}

	/**
     * @return the bizNo
     */
	public String getBizNo() {
		return bizNo;
	}

	/**
     * @param bizNo the bizNo to set
     */
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	/**
     * @return the bizStoreNm
     */
	public String getBizStoreNm() {
		return bizStoreNm;
	}

	/**
     * @param bizStoreNm the bizStoreNm to set
     */
	public void setBizStoreNm(String bizStoreNm) {
		this.bizStoreNm = bizStoreNm;
	}

	/**
     * @return the postNo
     */
	public String getPostNo() {
		return postNo;
	}

	/**
     * @param postNo the postNo to set
     */
	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	/**
     * @return the addr
     */
	public String getAddr() {
		return addr;
	}

	/**
     * @param addr the addr to set
     */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
     * @return the storeTypeNm
     */
	public String getStoreTypeNm() {
		return storeTypeNm;
	}

	/**
     * @param storeTypeNm the storeTypeNm to set
     */
	public void setStoreTypeNm(String storeTypeNm) {
		this.storeTypeNm = storeTypeNm;
	}

	/**
     * @return the clsFgNm
     */
	public String getClsFgNm() {
		return clsFgNm;
	}

	/**
     * @param clsFgNm the clsFgNm to set
     */
	public void setClsFgNm(String clsFgNm) {
		this.clsFgNm = clsFgNm;
	}

	/**
     * @return the email
     */
	public String getEmail() {
		return email;
	}

	/**
     * @param email the email to set
     */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
     * @return the outTot
     */
	public Long getOutTot() {
		return outTot;
	}

	/**
     * @param outTot the outTot to set
     */
	public void setOutTot(Long outTot) {
		this.outTot = outTot;
	}

	/**
     * @return the orplceCd
     */
	public String getOrplceCd() {
		return orplceCd;
	}

	/**
     * @param orplceCd the orplceCd to set
     */
	public void setOrplceCd(String orplceCd) {
		this.orplceCd = orplceCd;
	}
	
	/**
     * @return the orplceNm
     */
	public String getOrplceNm() {
		return orplceNm;
	}

	/**
     * @param orplceNm the orplceNm to set
     */
	public void setOrplceNm(String orplceNm) {
		this.orplceNm = orplceNm;
	}  

	/**
     * @return the poUnitFgNm
     */
	public String getPoUnitFgNm() {
		return poUnitFgNm;
	}

	/**
     * @param poUnitFgNm the poUnitFgNm to set
     */
	public void setPoUnitFgNm(String poUnitFgNm) {
		this.poUnitFgNm = poUnitFgNm;
	}

	/**
     * @return the poUnitQty
     */
	public Integer getPoUnitQty() {
		return poUnitQty;
	}

	/**
     * @param poUnitQty the poUnitQty to set
     */
	public void setPoUnitQty(Integer poUnitQty) {
		this.poUnitQty = poUnitQty;
	}

	/**
     * @return the splyUprc
     */
	public Integer getSplyUprc() {
		return splyUprc;
	}

	/**
     * @param splyUprc the splyUprc to set
     */
	public void setSplyUprc(Integer splyUprc) {
		this.splyUprc = splyUprc;
	}

	/**
     * @return the totQty
     */
	public Integer getTotQty() {
		return totQty;
	}

	/**
     * @param totQty the totQty to set
     */
	public void setTotQty(Integer totQty) {
		this.totQty = totQty;
	}

	/**
     * @return the totAmt
     */
	public Long getTotAmt() {
		return totAmt;
	}

	/**
     * @param totAmt the totAmt to set
     */
	public void setTotAmt(Long totAmt) {
		this.totAmt = totAmt;
	}

	/**
     * @return the totVat
     */
	public Long getTotVat() {
		return totVat;
	}

	/**
     * @param totVat the totVat to set
     */
	public void setTotVat(Long totVat) {
		this.totVat = totVat;
	}

	/**
     * @return the totTot
     */
	public Long getTotTot() {
		return totTot;
	}

	/**
     * @param totTot the totTot to set
     */
	public void setTotTot(Long totTot) {
		this.totTot = totTot;
	}  
	
}
