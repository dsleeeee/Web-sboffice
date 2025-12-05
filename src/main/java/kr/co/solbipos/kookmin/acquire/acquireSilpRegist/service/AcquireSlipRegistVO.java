package kr.co.solbipos.kookmin.acquire.acquireSilpRegist.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name  : AcquireSlipRegistVO.java
 * @Description : 국민대 > 매입관리 > 매입전표등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.21  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.11.21
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class AcquireSlipRegistVO extends PageVO {
    private static final long serialVersionUID = 3907011249582566409L;

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
    /** 전표번호 YYMM(4)+SEQ(4) */
    private String slipNo;
    /** 전표구분 1:입고 -1:출고 */
    private Integer slipFg;
    /** 거래처코드 */
    private String vendrCd;
    /** 처리구분 0:등록 1:확정 */
    private String procFg;
    /** 발주/반출 전표번호 YYMM(4)+SEQ(4) */
    private String orderSlipNo;
    /** 입고일자 */
    private String instockDate;
    /** 상세건수 */
    private Integer dtlCnt;
    /** 입고/출고수량합계 낱개 */
    private Integer inTotQty;
    /** 입고/출고금액 */
    private Long inAmt;
    /** 입고/출고VAT */
    private Long inVat;
    /** 입고/출고합계금액 */
    private Long inTot;
    /** 비고 */
    private String remark;
    /** 등록일시 */
    private String inRegDt;
    /** 등록자 */
    private String inRegId;
    /** 확정일시 */
    private String confmDt;
    /** 확정자 */
    private String confmId;
    /** 창고코드 */
    private String storageCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 바코드 */
    private String barcdCd;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 원가단가 */
    private Float costUprc;
    /** 발주단위구분 */
    private String poUnitFg;
    /** 발주단위수량 */
    private Integer poUnitQty;
    /** 입고/출고수량 주문단위 */
    private Integer inUnitQty;
    /** 입고/출고수량 나머지 */
    private Integer inEtcQty;
    /** 발주/반출수량 주문단위 */
    private Integer prevInUnitQty;
    /** 발주/반출수량 나머지 */
    private Integer prevInEtcQty;
    /** 발주/반출수량합계 낱개 */
    private Integer prevInTotQty;
    /** 기 입고/출고금액 */
    private Long prevInAmt;
    /** 기 입고/출고VAT */
    private Long prevInVat;
    /** 기 입고/출고합계금액 */
    private Long prevInTot;

    /** 년월 */
    private String yymm;
    /** 조회일자구분 */
    private String dateFg;
    /** 발주/무발주 구분 */
    private String instockType;
    /** 매장공급가 동시저장 구분 */
    private String storeSplyFg;
    /** 공급가 */
    private Long splyUprc;
    /** 발생구분 */
    private String occrFg;
    /** 창고 갯수 */
    //private int	storageCnt;

    /** 창고별 배열값-창고코드 */
    private String  arrStorageCd;

    /** 창고별 배열값-창고명 */
    private String  arrStorageNm;

    /** 창고별 배열값-입고수량 주문단위 */
    private String  arrInUnitQty;

    /** 창고별 배열값-입고수량 나머지 */
    private String  arrInEtcQty;

    /** 창고별 배열값-입고수량 합계 */
    private String  arrInTotQty;

    /** 창고별 배열값-입고금액 */
    private String  arrInAmt;

    /** 창고별 배열값-입고금액 부가세 */
    private String  arrInVat;

    /** 창고별 배열값-입고금액 합계 */
    private String  arrInTot;

    /** 권역별 창고 코드 */
    private String  areaFg;

    /** 창고코드 */
    private String outStorageCd;

    private String delFg;

    /** 처리구분 0:확정취소 1:확정 */
    private String confmYn;

    /** 입고상품 등록화면 구분 1:입고상품탭 2:상품추가/변경 3: 발주내역으로등록 */
    private String prodRegFg;


    /** 매입구분 */
    private String tradeFg;

    /** 구분 */
    private String tradeForm;

    /** 판매날짜 */
    private String today;

    /** 순번 */
    private String seq;

    /** 매입가 */
    private String acquireUprc;

    /** 매입율 */
    private String acquireRate;

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

    public String getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    public Integer getSlipFg() {
        return slipFg;
    }

    public void setSlipFg(Integer slipFg) {
        this.slipFg = slipFg;
    }

    public String getVendrCd() {
        return vendrCd;
    }

    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }

    public String getProcFg() {
        return procFg;
    }

    public void setProcFg(String procFg) {
        this.procFg = procFg;
    }

    public String getOrderSlipNo() {
        return orderSlipNo;
    }

    public void setOrderSlipNo(String orderSlipNo) {
        this.orderSlipNo = orderSlipNo;
    }

    public String getInstockDate() {
        return instockDate;
    }

    public void setInstockDate(String instockDate) {
        this.instockDate = instockDate;
    }

    public Integer getDtlCnt() {
        return dtlCnt;
    }

    public void setDtlCnt(Integer dtlCnt) {
        this.dtlCnt = dtlCnt;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInRegDt() {
        return inRegDt;
    }

    public void setInRegDt(String inRegDt) {
        this.inRegDt = inRegDt;
    }

    public String getInRegId() {
        return inRegId;
    }

    public void setInRegId(String inRegId) {
        this.inRegId = inRegId;
    }

    public String getConfmDt() {
        return confmDt;
    }

    public void setConfmDt(String confmDt) {
        this.confmDt = confmDt;
    }

    public String getConfmId() {
        return confmId;
    }

    public void setConfmId(String confmId) {
        this.confmId = confmId;
    }

    public String getStorageCd() {
        return storageCd;
    }

    public void setStorageCd(String storageCd) {
        this.storageCd = storageCd;
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

    public Float getCostUprc() {
        return costUprc;
    }

    public void setCostUprc(Float costUprc) {
        this.costUprc = costUprc;
    }

    public String getPoUnitFg() {
        return poUnitFg;
    }

    public void setPoUnitFg(String poUnitFg) {
        this.poUnitFg = poUnitFg;
    }

    public Integer getPoUnitQty() {
        return poUnitQty;
    }

    public void setPoUnitQty(Integer poUnitQty) {
        this.poUnitQty = poUnitQty;
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

    public Integer getPrevInUnitQty() {
        return prevInUnitQty;
    }

    public void setPrevInUnitQty(Integer prevInUnitQty) {
        this.prevInUnitQty = prevInUnitQty;
    }

    public Integer getPrevInEtcQty() {
        return prevInEtcQty;
    }

    public void setPrevInEtcQty(Integer prevInEtcQty) {
        this.prevInEtcQty = prevInEtcQty;
    }

    public Integer getPrevInTotQty() {
        return prevInTotQty;
    }

    public void setPrevInTotQty(Integer prevInTotQty) {
        this.prevInTotQty = prevInTotQty;
    }

    public Long getPrevInAmt() {
        return prevInAmt;
    }

    public void setPrevInAmt(Long prevInAmt) {
        this.prevInAmt = prevInAmt;
    }

    public Long getPrevInVat() {
        return prevInVat;
    }

    public void setPrevInVat(Long prevInVat) {
        this.prevInVat = prevInVat;
    }

    public Long getPrevInTot() {
        return prevInTot;
    }

    public void setPrevInTot(Long prevInTot) {
        this.prevInTot = prevInTot;
    }

    public String getYymm() {
        return yymm;
    }

    public void setYymm(String yymm) {
        this.yymm = yymm;
    }

    public String getDateFg() {
        return dateFg;
    }

    public void setDateFg(String dateFg) {
        this.dateFg = dateFg;
    }

    public String getInstockType() {
        return instockType;
    }

    public void setInstockType(String instockType) {
        this.instockType = instockType;
    }

    public String getStoreSplyFg() {
        return storeSplyFg;
    }

    public void setStoreSplyFg(String storeSplyFg) {
        this.storeSplyFg = storeSplyFg;
    }

    public Long getSplyUprc() {
        return splyUprc;
    }

    public void setSplyUprc(Long splyUprc) {
        this.splyUprc = splyUprc;
    }

    public String getOccrFg() {
        return occrFg;
    }

    public void setOccrFg(String occrFg) {
        this.occrFg = occrFg;
    }

    public String getArrStorageCd() {
        return arrStorageCd;
    }

    public void setArrStorageCd(String arrStorageCd) {
        this.arrStorageCd = arrStorageCd;
    }

    public String getArrStorageNm() {
        return arrStorageNm;
    }

    public void setArrStorageNm(String arrStorageNm) {
        this.arrStorageNm = arrStorageNm;
    }

    public String getArrInUnitQty() {
        return arrInUnitQty;
    }

    public void setArrInUnitQty(String arrInUnitQty) {
        this.arrInUnitQty = arrInUnitQty;
    }

    public String getArrInEtcQty() {
        return arrInEtcQty;
    }

    public void setArrInEtcQty(String arrInEtcQty) {
        this.arrInEtcQty = arrInEtcQty;
    }

    public String getArrInTotQty() {
        return arrInTotQty;
    }

    public void setArrInTotQty(String arrInTotQty) {
        this.arrInTotQty = arrInTotQty;
    }

    public String getArrInAmt() {
        return arrInAmt;
    }

    public void setArrInAmt(String arrInAmt) {
        this.arrInAmt = arrInAmt;
    }

    public String getArrInVat() {
        return arrInVat;
    }

    public void setArrInVat(String arrInVat) {
        this.arrInVat = arrInVat;
    }

    public String getArrInTot() {
        return arrInTot;
    }

    public void setArrInTot(String arrInTot) {
        this.arrInTot = arrInTot;
    }

    public String getAreaFg() {
        return areaFg;
    }

    public void setAreaFg(String areaFg) {
        this.areaFg = areaFg;
    }

    public String getOutStorageCd() {
        return outStorageCd;
    }

    public void setOutStorageCd(String outStorageCd) {
        this.outStorageCd = outStorageCd;
    }

    public String getDelFg() {
        return delFg;
    }

    public void setDelFg(String delFg) {
        this.delFg = delFg;
    }

    public String getConfmYn() {
        return confmYn;
    }

    public void setConfmYn(String confmYn) {
        this.confmYn = confmYn;
    }

    public String getProdRegFg() {
        return prodRegFg;
    }

    public void setProdRegFg(String prodRegFg) {
        this.prodRegFg = prodRegFg;
    }

    public String getTradeFg() {
        return tradeFg;
    }

    public void setTradeFg(String tradeFg) {
        this.tradeFg = tradeFg;
    }

    public String getTradeForm() {
        return tradeForm;
    }

    public void setTradeForm(String tradeForm) {
        this.tradeForm = tradeForm;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getAcquireUprc() {
        return acquireUprc;
    }

    public void setAcquireUprc(String acquireUprc) {
        this.acquireUprc = acquireUprc;
    }

    public String getAcquireRate() {
        return acquireRate;
    }

    public void setAcquireRate(String acquireRate) {
        this.acquireRate = acquireRate;
    }
}
