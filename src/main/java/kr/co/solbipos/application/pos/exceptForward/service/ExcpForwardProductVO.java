package kr.co.solbipos.application.pos.exceptForward.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.pos.exceptForward.service.enums.StatusFg;
import kr.co.solbipos.application.pos.exceptForward.service.enums.TpioFg;

/**
 * @Class Name : MsProductVO.java
 * @Description : 예외출고 대상상품 VO
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.14  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.09.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ExcpForwardProductVO extends PageVO {

    /** 영업일자 */
    private String saleDate;

    /** 매장코드 */
    private String storeCd;

    /** 입고수량 */
    private Integer inQty;

    /** 출고수량 (판매+기타출고_ */
    private Integer outQty;

    /** 현재수량 */
    private Integer stockQty;

    /** 예외출고 수량 */
    private Integer forwardQty;

    /** 품절여부 */
    private UseYn soldOutYn;

    /** 회사코드 */
    private String cdCompany;

    /** 출고번호 */
    private String noEgr;

    /** 출고항번 */
    private String noErgline;

    /** 입고번호 */
    private String noIo;

    /** 입고항번 */
    private String noIoline;

    /** 입고일자 */
    private String dtIo;

    /** 점포코드 */
    private String cdPjt;

    /** 거래처코드 */
    private String cdPartner;

    /** 공장코드 */
    private String cdPlant;

    /** 대분류 */
    private String clsL;

    /** 중분류 */
    private String clsM;

    /** 소분류 */
    private String clsS;

    /** 품목코드 */
    private String cdItem;

    /** POS메뉴명 */
    private String nmItem;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 입고수량 */
    private String otIo;

    /** 출고수량 */
    private Integer qtIo;

    /** 기존 예외출고 수량 */
    private Integer prevQtIo;

    /** 단가 */
    private String um;

    /** 금액 */
    private String am;

    /** 부가세액 */
    private String vat;

    /** 대체유형 */
    private TpioFg fgTpio;

    /** 수불유형 */
    private String cdQtiotp;

    /** 상태 */
    private StatusFg fgStatus;

    /** 입력일자 */
    private String dtsInsert;

    /** 입력자 */
    private String idInsert;

    /** 수정일자 */
    private String dtsUpdate;

    /** 수정자 */
    private String idUpdate;


    /**
     * @return the saleDate
     */

    public String getSaleDate() {
        return saleDate;
    }

    /**
     * @param saleDate the saleDate to set
     */
    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
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
     * @return the inQty
     */

    public Integer getInQty() {
        return inQty;
    }

    /**
     * @param inQty the inQty to set
     */
    public void setInQty(Integer inQty) {
        this.inQty = inQty;
    }

    /**
     * @return the outQty
     */

    public Integer getOutQty() {
        return outQty;
    }

    /**
     * @param outQty the outQty to set
     */
    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }

    /**
     * @return the stockQty
     */

    public Integer getStockQty() {
        return stockQty;
    }

    /**
     * @param stockQty the stockQty to set
     */
    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    /**
     * @return the forwardQty
     */

    public Integer getForwardQty() {
        return forwardQty;
    }

    /**
     * @param forwardQty the forwardQty to set
     */
    public void setForwardQty(Integer forwardQty) {
        this.forwardQty = forwardQty;
    }

    /**
     * @return the soldOutYn
     */

    public UseYn getSoldOutYn() {
        return soldOutYn;
    }

    /**
     * @param soldOutYn the soldOutYn to set
     */
    public void setSoldOutYn(UseYn soldOutYn) {
        this.soldOutYn = soldOutYn;
    }

    /**
     * @return the cdCompany
     */

    public String getCdCompany() {
        return cdCompany;
    }

    /**
     * @param cdCompany the cdCompany to set
     */
    public void setCdCompany(String cdCompany) {
        this.cdCompany = cdCompany;
    }

    /**
     * @return the noEgr
     */

    public String getNoEgr() {
        return noEgr;
    }

    /**
     * @param noEgr the noEgr to set
     */
    public void setNoEgr(String noEgr) {
        this.noEgr = noEgr;
    }

    /**
     * @return the noErgline
     */

    public String getNoErgline() {
        return noErgline;
    }

    /**
     * @param noErgline the noErgline to set
     */
    public void setNoErgline(String noErgline) {
        this.noErgline = noErgline;
    }

    /**
     * @return the noIo
     */

    public String getNoIo() {
        return noIo;
    }

    /**
     * @param noIo the noIo to set
     */
    public void setNoIo(String noIo) {
        this.noIo = noIo;
    }

    /**
     * @return the noIoline
     */

    public String getNoIoline() {
        return noIoline;
    }

    /**
     * @param noIoline the noIoline to set
     */
    public void setNoIoline(String noIoline) {
        this.noIoline = noIoline;
    }

    /**
     * @return the dtIo
     */

    public String getDtIo() {
        return dtIo;
    }

    /**
     * @param dtIo the dtIo to set
     */
    public void setDtIo(String dtIo) {
        this.dtIo = dtIo;
    }

    /**
     * @return the cdPjt
     */

    public String getCdPjt() {
        return cdPjt;
    }

    /**
     * @param cdPjt the cdPjt to set
     */
    public void setCdPjt(String cdPjt) {
        this.cdPjt = cdPjt;
    }

    /**
     * @return the cdPartner
     */

    public String getCdPartner() {
        return cdPartner;
    }

    /**
     * @param cdPartner the cdPartner to set
     */
    public void setCdPartner(String cdPartner) {
        this.cdPartner = cdPartner;
    }

    /**
     * @return the cdPlant
     */

    public String getCdPlant() {
        return cdPlant;
    }

    /**
     * @param cdPlant the cdPlant to set
     */
    public void setCdPlant(String cdPlant) {
        this.cdPlant = cdPlant;
    }

    /**
     * @return the clsL
     */

    public String getClsL() {
        return clsL;
    }

    /**
     * @param clsL the clsL to set
     */
    public void setClsL(String clsL) {
        this.clsL = clsL;
    }

    /**
     * @return the clsM
     */

    public String getClsM() {
        return clsM;
    }

    /**
     * @param clsM the clsM to set
     */
    public void setClsM(String clsM) {
        this.clsM = clsM;
    }

    /**
     * @return the clsS
     */

    public String getClsS() {
        return clsS;
    }

    /**
     * @param clsS the clsS to set
     */
    public void setClsS(String clsS) {
        this.clsS = clsS;
    }

    /**
     * @return the cdItem
     */

    public String getCdItem() {
        return cdItem;
    }

    /**
     * @param cdItem the cdItem to set
     */
    public void setCdItem(String cdItem) {
        this.cdItem = cdItem;
    }

    /**
     * @return the nmItem
     */

    public String getNmItem() {
        return nmItem;
    }

    /**
     * @param nmItem the nmItem to set
     */
    public void setNmItem(String nmItem) {
        this.nmItem = nmItem;
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
     * @return the otIo
     */

    public String getOtIo() {
        return otIo;
    }

    /**
     * @param otIo the otIo to set
     */
    public void setOtIo(String otIo) {
        this.otIo = otIo;
    }

    /**
     * @return the qtIo
     */

    public Integer getQtIo() {
        return qtIo;
    }

    /**
     * @param qtIo the qtIo to set
     */
    public void setQtIo(Integer qtIo) {
        this.qtIo = qtIo;
    }

    /**
     * @return the prevQtIo
     */

    public Integer getPrevQtIo() {
        return prevQtIo;
    }

    /**
     * @param prevQtIo the prevQtIo to set
     */
    public void setPrevQtIo(Integer prevQtIo) {
        this.prevQtIo = prevQtIo;
    }

    /**
     * @return the um
     */

    public String getUm() {
        return um;
    }

    /**
     * @param um the um to set
     */
    public void setUm(String um) {
        this.um = um;
    }

    /**
     * @return the am
     */

    public String getAm() {
        return am;
    }

    /**
     * @param am the am to set
     */
    public void setAm(String am) {
        this.am = am;
    }

    /**
     * @return the vat
     */

    public String getVat() {
        return vat;
    }

    /**
     * @param vat the vat to set
     */
    public void setVat(String vat) {
        this.vat = vat;
    }

    /**
     * @return the fgTpio
     */

    public TpioFg getFgTpio() {
        return fgTpio;
    }

    /**
     * @param fgTpio the fgTpio to set
     */
    public void setFgTpio(TpioFg fgTpio) {
        this.fgTpio = fgTpio;
    }

    /**
     * @return the cdQtiotp
     */

    public String getCdQtiotp() {
        return cdQtiotp;
    }

    /**
     * @param cdQtiotp the cdQtiotp to set
     */
    public void setCdQtiotp(String cdQtiotp) {
        this.cdQtiotp = cdQtiotp;
    }

    /**
     * @return the fgStatus
     */

    public StatusFg getFgStatus() {
        return fgStatus;
    }

    /**
     * @param fgStatus the fgStatus to set
     */
    public void setFgStatus(StatusFg fgStatus) {
        this.fgStatus = fgStatus;
    }

    /**
     * @return the dtsInsert
     */

    public String getDtsInsert() {
        return dtsInsert;
    }

    /**
     * @param dtsInsert the dtsInsert to set
     */
    public void setDtsInsert(String dtsInsert) {
        this.dtsInsert = dtsInsert;
    }

    /**
     * @return the idInsert
     */

    public String getIdInsert() {
        return idInsert;
    }

    /**
     * @param idInsert the idInsert to set
     */
    public void setIdInsert(String idInsert) {
        this.idInsert = idInsert;
    }

    /**
     * @return the dtsUpdate
     */

    public String getDtsUpdate() {
        return dtsUpdate;
    }

    /**
     * @param dtsUpdate the dtsUpdate to set
     */
    public void setDtsUpdate(String dtsUpdate) {
        this.dtsUpdate = dtsUpdate;
    }

    /**
     * @return the idUpdate
     */

    public String getIdUpdate() {
        return idUpdate;
    }

    /**
     * @param idUpdate the idUpdate to set
     */
    public void setIdUpdate(String idUpdate) {
        this.idUpdate = idUpdate;
    }
}
