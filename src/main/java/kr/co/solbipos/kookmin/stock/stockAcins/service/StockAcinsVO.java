package kr.co.solbipos.kookmin.stock.stockAcins.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : StockAcinsVO.java
 * @Description : 국민대 > 재고관리 > 재고실사
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class StockAcinsVO extends PageVO {

    private static final long serialVersionUID = 6030024078436304111L;

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

    /** 실사등록건수 */
    private Integer dtlCnt;

    /** 실사조사 일자 */
    private String acinsDate;

    /** 헤드 인덱스(DAY SEQUENCE) */
    private Integer seqNo;

    /** 제목 */
    private String acinsTitle;

    /** 처리구분 (0:입력, 1:확정) */
    private String procFg;

    /** 사유코드 */
    private String acinsReason;

    /** 사용자의 권역코드 */
    private String areaFg;

    /** 창고코드 */
    private String storageCd;

    /** 바코드 */
    private String barcdCd;

    /** 상품분류 */
    private String prodClassCd;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 거래처 */
    private String vendrCd;

    /** 상품코드 or 바코드 */
    private String prodBarcdCd;

    /** 실사구분 */
    private String acinsFg;

    /** 선택창고 */
    private String adjStorageCd;

    /** 실사 상품 STATUS */
    private String acinsProdStatus;

    /** 확정여부 */
    private String confirmFg;

    /** 브랜드코드 */
    private String hqBrandCd;

    /** 원가 */
    private Integer costUprc;

    /** 조정금액 */
    private Integer adjAmt;

    /** 전산 재고수량 */
    private Integer cmptCurrQty;

    /** 실사 재고수량 */
    private Integer acinsQty;

    /** 조정 재고수량 */
    private Integer adjQty;

    /** 실사금액 */
    private Integer acinsAmt;

    /** 주문단위-입수량 */
    private Integer poUnitQty;

    /** 실사비고 */
    private String remark;

    /** 상품 순번 */
    private Integer seq;

    /** 세션 ID */
    private String sessionId;

    // 엑셀업로드 관련
    /** 업로드 구분 */
    private String uploadFg;

    /** 단가 */
    private Float uprc;

    /** 단위수량 */
    private Long unitQty;

    /** 낱개수량 */
    private Long etcQty;

    /** 수량 */
    private Long qty;

    /** 수량적용/추가여부 */
    private String addQtyFg;

    /** 일자 */
    private String date;

    /** 제목 */
    private String title;

    /** 이유 */
    private String reason;
    // 엑셀업로드 관련

    /** 폐기 창고 */
    private String disuseStorageCd;

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

    public Integer getDtlCnt() {
        return dtlCnt;
    }

    public void setDtlCnt(Integer dtlCnt) {
        this.dtlCnt = dtlCnt;
    }

    public String getAcinsDate() {
        return acinsDate;
    }

    public void setAcinsDate(String acinsDate) {
        this.acinsDate = acinsDate;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getAcinsTitle() {
        return acinsTitle;
    }

    public void setAcinsTitle(String acinsTitle) {
        this.acinsTitle = acinsTitle;
    }

    public String getProcFg() {
        return procFg;
    }

    public void setProcFg(String procFg) {
        this.procFg = procFg;
    }

    public String getAcinsReason() {
        return acinsReason;
    }

    public void setAcinsReason(String acinsReason) {
        this.acinsReason = acinsReason;
    }

    public String getAreaFg() {
        return areaFg;
    }

    public void setAreaFg(String areaFg) {
        this.areaFg = areaFg;
    }

    public String getStorageCd() {
        return storageCd;
    }

    public void setStorageCd(String storageCd) {
        this.storageCd = storageCd;
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

    public String getVendrCd() {
        return vendrCd;
    }

    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }

    public String getProdBarcdCd() {
        return prodBarcdCd;
    }

    public void setProdBarcdCd(String prodBarcdCd) {
        this.prodBarcdCd = prodBarcdCd;
    }

    public String getAcinsFg() {
        return acinsFg;
    }

    public void setAcinsFg(String acinsFg) {
        this.acinsFg = acinsFg;
    }

    public String getAdjStorageCd() {
        return adjStorageCd;
    }

    public void setAdjStorageCd(String adjStorageCd) {
        this.adjStorageCd = adjStorageCd;
    }

    public String getAcinsProdStatus() {
        return acinsProdStatus;
    }

    public void setAcinsProdStatus(String acinsProdStatus) {
        this.acinsProdStatus = acinsProdStatus;
    }

    public String getConfirmFg() {
        return confirmFg;
    }

    public void setConfirmFg(String confirmFg) {
        this.confirmFg = confirmFg;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    public Integer getCostUprc() {
        return costUprc;
    }

    public void setCostUprc(Integer costUprc) {
        this.costUprc = costUprc;
    }

    public Integer getAdjAmt() {
        return adjAmt;
    }

    public void setAdjAmt(Integer adjAmt) {
        this.adjAmt = adjAmt;
    }

    public Integer getCmptCurrQty() {
        return cmptCurrQty;
    }

    public void setCmptCurrQty(Integer cmptCurrQty) {
        this.cmptCurrQty = cmptCurrQty;
    }

    public Integer getAcinsQty() {
        return acinsQty;
    }

    public void setAcinsQty(Integer acinsQty) {
        this.acinsQty = acinsQty;
    }

    public Integer getAdjQty() {
        return adjQty;
    }

    public void setAdjQty(Integer adjQty) {
        this.adjQty = adjQty;
    }

    public Integer getAcinsAmt() {
        return acinsAmt;
    }

    public void setAcinsAmt(Integer acinsAmt) {
        this.acinsAmt = acinsAmt;
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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUploadFg() {
        return uploadFg;
    }

    public void setUploadFg(String uploadFg) {
        this.uploadFg = uploadFg;
    }

    public Float getUprc() {
        return uprc;
    }

    public void setUprc(Float uprc) {
        this.uprc = uprc;
    }

    public Long getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(Long unitQty) {
        this.unitQty = unitQty;
    }

    public Long getEtcQty() {
        return etcQty;
    }

    public void setEtcQty(Long etcQty) {
        this.etcQty = etcQty;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getAddQtyFg() {
        return addQtyFg;
    }

    public void setAddQtyFg(String addQtyFg) {
        this.addQtyFg = addQtyFg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDisuseStorageCd() {
        return disuseStorageCd;
    }

    public void setDisuseStorageCd(String disuseStorageCd) {
        this.disuseStorageCd = disuseStorageCd;
    }
}
