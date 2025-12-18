package kr.co.solbipos.kookmin.stock.stockDisuse.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : StockDisuseVO.java
 * @Description : 국민대 > 재고관리 > 재고폐기
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.17  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.17
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class StockDisuseVO extends PageVO {
    private static final long serialVersionUID = -5307571047022427818L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 창고코드 */
    private String storageCd;
    /** 조정조사 일자 */
    private String disuseDate;
    /** 헤드 인덱스(DAY SEQUENCE) */
    private Integer seqNo;
    /** 제목 */
    private String disuseTitle;
    /** 처리구분 (0:입력, 1:확정) */
    private String procFg;
    /** 조정등록건수 */
    private Integer dtlCnt;
    /** 총조정금액 */
    private Integer totAdjAmt;
    /** 확정일자 */
    private String confmDate;
    /** 확정자 */
    private String confmId;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 원가 */
    private Integer costUprc;
    /** 조정 재고수량 */
    private Integer disuseQty;
    /** 조정금액 */
    private Integer disuseAmt;
    /** 전산 재고수량 */
    private Integer currQty;
    /** 주문단위-입수량 */
    private Integer poUnitQty;
    /** 조정비고 */
    private String remark;
    /** 바코드 */
    private String barcdCd;
    /** 상품분류 */
    private String prodClassCd;
    /** 거래처코드 */
    private String vendrCd;
    /** 거래처코드 array */
    private String arrVendrCd[];
    /** 조정 상품 STATUS */
    private String disuseProdStatus;
    /** 확정여부 */
    private String confirmFg;
    /** 상품코드 or 바코드 */
    private String prodBarcdCd;
    /** 조정구분 */
    private String disuseFg;
    /** 권역별 창고 코드 */
    private String  areaFg;
    /** 선택창고 */
    private String disuseStorageCd;
    /** 사유코드 */
    private String disuseReason;

    /** 순번 */
    private String seq;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

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

    public String getStorageCd() {
        return storageCd;
    }

    public void setStorageCd(String storageCd) {
        this.storageCd = storageCd;
    }

    public String getDisuseDate() {
        return disuseDate;
    }

    public void setDisuseDate(String disuseDate) {
        this.disuseDate = disuseDate;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getDisuseTitle() {
        return disuseTitle;
    }

    public void setDisuseTitle(String disuseTitle) {
        this.disuseTitle = disuseTitle;
    }

    public String getProcFg() {
        return procFg;
    }

    public void setProcFg(String procFg) {
        this.procFg = procFg;
    }

    public Integer getDtlCnt() {
        return dtlCnt;
    }

    public void setDtlCnt(Integer dtlCnt) {
        this.dtlCnt = dtlCnt;
    }

    public Integer getTotAdjAmt() {
        return totAdjAmt;
    }

    public void setTotAdjAmt(Integer totAdjAmt) {
        this.totAdjAmt = totAdjAmt;
    }

    public String getConfmDate() {
        return confmDate;
    }

    public void setConfmDate(String confmDate) {
        this.confmDate = confmDate;
    }

    public String getConfmId() {
        return confmId;
    }

    public void setConfmId(String confmId) {
        this.confmId = confmId;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
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

    public Integer getCostUprc() {
        return costUprc;
    }

    public void setCostUprc(Integer costUprc) {
        this.costUprc = costUprc;
    }

    public Integer getDisuseQty() {
        return disuseQty;
    }

    public void setDisuseQty(Integer disuseQty) {
        this.disuseQty = disuseQty;
    }

    public Integer getDisuseAmt() {
        return disuseAmt;
    }

    public void setDisuseAmt(Integer disuseAmt) {
        this.disuseAmt = disuseAmt;
    }

    public Integer getCurrQty() {
        return currQty;
    }

    public void setCurrQty(Integer currQty) {
        this.currQty = currQty;
    }

    public Integer getPoUnitQty() {
        return poUnitQty;
    }

    public void setPoUnitQty(Integer poUnitQty) {
        this.poUnitQty = poUnitQty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBarcdCd() {
        return barcdCd;
    }

    public void setBarcdCd(String barcdCd) {
        this.barcdCd = barcdCd;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getVendrCd() {
        return vendrCd;
    }

    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }

    public String[] getArrVendrCd() {
        return arrVendrCd;
    }

    public void setArrVendrCd(String[] arrVendrCd) {
        this.arrVendrCd = arrVendrCd;
    }

    public String getDisuseProdStatus() {
        return disuseProdStatus;
    }

    public void setDisuseProdStatus(String disuseProdStatus) {
        this.disuseProdStatus = disuseProdStatus;
    }

    public String getConfirmFg() {
        return confirmFg;
    }

    public void setConfirmFg(String confirmFg) {
        this.confirmFg = confirmFg;
    }

    public String getProdBarcdCd() {
        return prodBarcdCd;
    }

    public void setProdBarcdCd(String prodBarcdCd) {
        this.prodBarcdCd = prodBarcdCd;
    }

    public String getDisuseFg() {
        return disuseFg;
    }

    public void setDisuseFg(String disuseFg) {
        this.disuseFg = disuseFg;
    }

    public String getAreaFg() {
        return areaFg;
    }

    public void setAreaFg(String areaFg) {
        this.areaFg = areaFg;
    }

    public String getDisuseStorageCd() {
        return disuseStorageCd;
    }

    public void setDisuseStorageCd(String disuseStorageCd) {
        this.disuseStorageCd = disuseStorageCd;
    }

    public String getDisuseReason() {
        return disuseReason;
    }

    public void setDisuseReason(String disuseReason) {
        this.disuseReason = disuseReason;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}
