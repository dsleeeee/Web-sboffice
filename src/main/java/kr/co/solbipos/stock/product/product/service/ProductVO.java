package kr.co.solbipos.stock.product.product.service;

import kr.co.solbipos.application.common.service.PageVO;

public class ProductVO extends PageVO {

    private static final long serialVersionUID = 1551306308508498798L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 생산구분 (0:생산, 1:폐기) */
    private String productFg;
    /** 처리구분 (0:입력, 1:확정) */
    private String procFg;
    /** 상품코드 */
    private String prodCd;
    /** 상품명  */
    private String prodNm;
    /** 바코드 */
    private String barcdCd;
    /** 상품분류 */
    private String prodClassCd;
    /** 생산일자 */
    private String productDate;
    /** 창고코드 */
    private String storageCd;
    /** 차수 */
    private String seqNo;
    /** 원가 */
    private Integer costUprc;
    /** 입수량 */
    private Integer poUnitQty;
    /** 현재고수량 */
    private Integer currQty;
    /** 생산 반영수량 */
    private Integer productQty;
    /** 반영금액 */
    private Integer productAmt;
    /** 비고 */
    private String remark;
    /** 생산 상품 STATUS */
    private String productProdStatus;
    /** 생산 중량 */
    private String productWeight;
    /** 생산 판매가 */
    private String productSaleUprc;
    /** 제목 */
    private String productTitle;
    /** 생산 창고코드 */
    private String productStorageCd;
    /** 확정여부 */
    private String confirmFg;
    /** 세션ID */
    private String sessionId;
    /** 리더기자료 상품코드(바코드)+중량+가격 */
    private String prodWtUprc;
    /** 순번 */
    private int seq;
    /** 수량적용/추가여부 */
    private String addQtyFg;
    /** 상품코드/바코드 */
    private String prodBarcdCd;

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

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    public String getProductFg() {
        return productFg;
    }

    public void setProductFg(String productFg) {
        this.productFg = productFg;
    }

    public String getProcFg() {
        return procFg;
    }

    public void setProcFg(String procFg) {
        this.procFg = procFg;
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

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getStorageCd() {
        return storageCd;
    }

    public void setStorageCd(String storageCd) {
        this.storageCd = storageCd;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public Integer getCostUprc() {
        return costUprc;
    }

    public void setCostUprc(Integer costUprc) {
        this.costUprc = costUprc;
    }

    public Integer getPoUnitQty() {
        return poUnitQty;
    }

    public void setPoUnitQty(Integer poUnitQty) {
        this.poUnitQty = poUnitQty;
    }

    public Integer getCurrQty() {
        return currQty;
    }

    public void setCurrQty(Integer currQty) {
        this.currQty = currQty;
    }

    public Integer getProductQty() {
        return productQty;
    }

    public void setProductQty(Integer productQty) {
        this.productQty = productQty;
    }

    public Integer getProductAmt() {
        return productAmt;
    }

    public void setProductAmt(Integer productAmt) {
        this.productAmt = productAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductProdStatus() {
        return productProdStatus;
    }

    public void setProductProdStatus(String productProdStatus) {
        this.productProdStatus = productProdStatus;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public String getProductSaleUprc() {
        return productSaleUprc;
    }

    public void setProductSaleUprc(String productSaleUprc) {
        this.productSaleUprc = productSaleUprc;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductStorageCd() {
        return productStorageCd;
    }

    public void setProductStorageCd(String productStorageCd) {
        this.productStorageCd = productStorageCd;
    }

    public String getConfirmFg() {
        return confirmFg;
    }

    public void setConfirmFg(String confirmFg) {
        this.confirmFg = confirmFg;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getProdWtUprc() {
        return prodWtUprc;
    }

    public void setProdWtUprc(String prodWtUprc) {
        this.prodWtUprc = prodWtUprc;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getAddQtyFg() {
        return addQtyFg;
    }

    public void setAddQtyFg(String addQtyFg) {
        this.addQtyFg = addQtyFg;
    }

    public String getProdBarcdCd() {
        return prodBarcdCd;
    }

    public void setProdBarcdCd(String prodBarcdCd) {
        this.prodBarcdCd = prodBarcdCd;
    }
}
