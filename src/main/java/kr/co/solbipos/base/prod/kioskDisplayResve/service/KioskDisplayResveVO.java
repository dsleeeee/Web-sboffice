package kr.co.solbipos.base.prod.kioskDisplayResve.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;

/**
 * @Class Name : KioskDisplayResveVO.java
 * @Description : 기초관리 - 상품관리2 - 비노출관리(예약)
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.24  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.05.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class KioskDisplayResveVO extends PageVO {

    private static final long serialVersionUID = 2507060678407090632L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 브랜드명 */
    private String hqBrandNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 코너코드 */
    private String cornrCd;
    /** 원산지코드 */
    private String orgplceCd;
    /** 사이드속성분류코드 */
    private String sdattrClassCd;
    /** 사이드선택그룹코드 */
    private String sdselGrpCd;
    /** 사이드선택그룹명 */
    private String sdselGrpNm;
    /** 포인트적립여부 */
    private String pointSaveYn;
    /** 상품할인구분 */
    private String prodDcFg;
    /** 부가세구분 */
    private String vatFg;
    /** 상품봉사료여부*/
    private String prodTipYn;
    /** 상품포장금액 */
    private Double prodPackAmt;
    /** 상품배달금액 */
    private Double prodDlvrAmt;
    /** 상품유형구분 */
    private String prodTypeFg;
    /** 판매상품여부 */
    private String saleProdYn;
    /** 재고상품여부 */
    private String stockProdYn;
    /** 사이드상품여부 */
    private String sideProdYn;
    /** 세트상품구분 */
    private String setProdFg;
    /** 가격관리구분 */
    private String prcCtrlFg;
    /** 원가단가 */
    private Double costUprc;
    /** 최종원가단가 */
    private Double lastCostUprc;
    /** 공급단가 */
    private Double splyUprc;
    /** 공급단가사용여부 */
    private String splyUprcUseYn;
    /** 발주상품구분 */
    private String poProdFg;
    /** 발주단위구분 */
    private String poUnitFg;
    /** 발주단위수량 */
    private Integer poUnitQty;
    /** 발주단위허용구분 */
    private String poUnitAllowFg;
    /** 발주최소수량 */
    private Integer poMinQty;
    /** 안전재고수량 */
    private Integer safeStockQty;
    /** 재고단위여부 */
    private String stockUnitFg;
    /** 사용여부 */
    private String useYn;
    private String kioskUseYn;
    /** 비고 */
    private String remark;

    /** 바코드 */
    private String barCd;
    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 전체기간 여부 */
    private boolean chkDt;

    /** 프로시져 실행 결과 */
    private String result;

    /** 매장적용여부 */
    private UseYn storeRegFg;

    /** 판매가격구분 (본사판매가 :1, 매장판매가:2) */
    private String salePrcFg;

    /** 판매가 */
    private String saleUprc;

    /** 변경전 판매가 */
    private String saleUprcB;

    /** 상품 등록 주체 구분 */
    private OrgnFg regOrgnFg;

    /**
     * workMode<br>
     * 1 : 상품정보수정<br>
     * 2 : 신규상품등록<br>
     * 3 : 매장등록<br>
     */
    private WorkModeFg workMode;

    /** */
    private ProdNoEnvFg prodNoEnv;

    /** */
    private String saveMode;

    /** 상품적용여부 */
    private UseYn prodRegFg;

    /** 파일 경로 */
    private String filePath;

    /** 파일 명 */
    private String fileNm;

    /** 파일확장자 */
    private String fileExt;

    /** 상품이미지파일 삭제여부 */
    private String prodImageDelFg;

    /** 초기재고 */
    private Integer startStockQty;

    /** 거래처코드 */
    private String vendrCd;

    /** 거래처명 */
    private String vendrNm;

    /** 거래처코드 */
    private String chkVendrCd;

    /** 거래처명 */
    private String chkVendrNm;

    /** 거래처코드 */
    private String[] vendrCdList;

    /** 매장상태 */
    private String sysStatFg;

    /** 상품등록구분 */
    private String regFg;

    /** 엑셀 다운로드 구분 */
    private String excelGubun;

    /** 매핑상품코드 */
    private String mapProdCd;

    /** 사이드메뉴생성구분 */
    private String sideEnvstVal;

    /** 내점가 */
    private String stinSaleUprc;

    /** 배달가 */
    private String dlvrSaleUprc;

    /** 포장가 */
    private String packSaleUprc;

    /** 변경전 내점가 */
    private String stinSaleUprcB;

    /** 변경전 배달가 */
    private String dlvrSaleUprcB;

    /** 변경전 포장가 */
    private String packSaleUprcB;

    /** 프린터코드 */
    private String prterNo;

    /** 매장상품 prefix */
    private String prefix;

    /** 구성상품코드*/
    private String unitProdCd;

    /** 구성상품수량*/
    private String unitProdQty;

    /** 표기순서*/
    private String dispSeq;

    /** 매장상품제한구분 여부 */
    private String storeProdUseFg;

    /** 사용자 아이디 */
    private String userId;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;

    /** 품절여부 */
    private String soldOutYn;

    /** 품절여부 */
    private String KioskDisplayYn;

    /** 사이드선택그룹코드 */
    private String sdselClassCd;

    /** 사용자별 브랜드코드 */
    private String[] userBrandList;

    /** 사용자별 브랜드코드 */
    private String userBrands;

    /** 매장브랜드코드 */
    private String storeHqBrandCd;

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    /** 상품코드 */
    private String[] prodCdList;

    /** 상품코드 */
    private String prodCds;

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    /** 팀별 */
    private String momsTeam;

    /** AC점포별 */
    private String momsAcShop;

    /** 지역구분 */
    private String momsAreaFg;

    /** 상권 */
    private String momsCommercial;

    /** 점포유형 */
    private String momsShopType;

    /** 매장관리타입 */
    private String momsStoreManageType;

    /** 그룹코드 */
    private String branchCd;

    private String orgStartDate;
    private String orgStartTime;
    private String startTime;
    private String orgKioskDisplayYn;

    /** 매장그룹 */
    private String momsStoreFg01;

    /** 매장그룹2 */
    private String momsStoreFg02;

    /** 매장그룹3 */
    private String momsStoreFg03;

    /** 매장그룹4 */
    private String momsStoreFg04;

    /** 매장그룹5 */
    private String momsStoreFg05;

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
     * @return the hqBrandCd
     */

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    /**
     * @param hqBrandCd the hqBrandCd to set
     */
    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    /**
     * @return the hqBrandNm
     */

    public String getHqBrandNm() {
        return hqBrandNm;
    }

    /**
     * @param hqBrandNm the hqBrandNm to set
     */
    public void setHqBrandNm(String hqBrandNm) {
        this.hqBrandNm = hqBrandNm;
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
     * @return the cornrCd
     */
    public String getCornrCd() {
        return cornrCd;
    }

    /**
     * @param cornrCd the cornrCd to set
     */
    public void setCornrCd(String cornrCd) {
        this.cornrCd = cornrCd;
    }

    /**
     * @return the orgplceCd
     */
    public String getOrgplceCd() {
        return orgplceCd;
    }

    /**
     * @param orgplceCd the orgplceCd to set
     */
    public void setOrgplceCd(String orgplceCd) {
        this.orgplceCd = orgplceCd;
    }

    /**
     * @return the sdattrClassCd
     */
    public String getSdattrClassCd() {
        return sdattrClassCd;
    }

    /**
     * @param sdattrClassCd the sdattrClassCd to set
     */
    public void setSdattrClassCd(String sdattrClassCd) {
        this.sdattrClassCd = sdattrClassCd;
    }

    /**
     * @return the sdselGrpCd
     */
    public String getSdselGrpCd() {
        return sdselGrpCd;
    }

    /**
     * @param sdselGrpCd the sdselGrpCd to set
     */
    public void setSdselGrpCd(String sdselGrpCd) {
        this.sdselGrpCd = sdselGrpCd;
    }

    public String getSdselGrpNm() {
        return sdselGrpNm;
    }

    public void setSdselGrpNm(String sdselGrpNm) {
        this.sdselGrpNm = sdselGrpNm;
    }

    /**
     * @return the pointSaveYn
     */
    public String getPointSaveYn() {
        return pointSaveYn;
    }

    /**
     * @param pointSaveYn the pointSaveYn to set
     */
    public void setPointSaveYn(String pointSaveYn) {
        this.pointSaveYn = pointSaveYn;
    }

    /**
     * @return the prodDcFg
     */
    public String getProdDcFg() {
        return prodDcFg;
    }

    /**
     * @param prodDcFg the prodDcFg to set
     */
    public void setProdDcFg(String prodDcFg) {
        this.prodDcFg = prodDcFg;
    }

    /**
     * @return the vatFg
     */
    public String getVatFg() {
        return vatFg;
    }

    /**
     * @param vatFg the vatFg to set
     */
    public void setVatFg(String vatFg) {
        this.vatFg = vatFg;
    }

    /**
     * @return the prodTipYn
     */
    public String getProdTipYn() {
        return prodTipYn;
    }

    /**
     * @param prodTipYn the prodTipYn to set
     */
    public void setProdTipYn(String prodTipYn) {
        this.prodTipYn = prodTipYn;
    }

    /**
     * @return the prodPackAmt
     */
    public Double getProdPackAmt() {
        return prodPackAmt;
    }

    /**
     * @param prodPackAmt the prodPackAmt to set
     */
    public void setProdPackAmt(Double prodPackAmt) {
        this.prodPackAmt = prodPackAmt;
    }

    /**
     * @return the prodDlvrAmt
     */
    public Double getProdDlvrAmt() {
        return prodDlvrAmt;
    }

    /**
     * @param prodDlvrAmt the prodDlvrAmt to set
     */
    public void setProdDlvrAmt(Double prodDlvrAmt) {
        this.prodDlvrAmt = prodDlvrAmt;
    }

    /**
     * @return the prodTypeFg
     */
    public String getProdTypeFg() {
        return prodTypeFg;
    }

    /**
     * @param prodTypeFg the prodTypeFg to set
     */
    public void setProdTypeFg(String prodTypeFg) {
        this.prodTypeFg = prodTypeFg;
    }

    /**
     * @return the saleProdYn
     */
    public String getSaleProdYn() {
        return saleProdYn;
    }

    /**
     * @param saleProdYn the saleProdYn to set
     */
    public void setSaleProdYn(String saleProdYn) {
        this.saleProdYn = saleProdYn;
    }

    /**
     * @return the stockProdYn
     */
    public String getStockProdYn() {
        return stockProdYn;
    }

    /**
     * @param stockProdYn the stockProdYn to set
     */
    public void setStockProdYn(String stockProdYn) {
        this.stockProdYn = stockProdYn;
    }

    /**
     * @return the sideProdYn
     */
    public String getSideProdYn() {
        return sideProdYn;
    }

    /**
     * @param sideProdYn the sideProdYn to set
     */
    public void setSideProdYn(String sideProdYn) {
        this.sideProdYn = sideProdYn;
    }

    /**
     * @return the setProdFg
     */
    public String getSetProdFg() {
        return setProdFg;
    }

    /**
     * @param setProdFg the setProdFg to set
     */
    public void setSetProdFg(String setProdFg) {
        this.setProdFg = setProdFg;
    }


    /**
     * @return the prcCtrlFg
     */

    public String getPrcCtrlFg() {
        return prcCtrlFg;
    }

    /**
     * @param prcCtrlFg the prcCtrlFg to set
     */
    public void setPrcCtrlFg(String prcCtrlFg) {
        this.prcCtrlFg = prcCtrlFg;
    }

    /**
     * @return the costUprc
     */
    public Double getCostUprc() {
        return costUprc;
    }

    /**
     * @param costUprc the costUprc to set
     */
    public void setCostUprc(Double costUprc) {
        this.costUprc = costUprc;
    }

    /**
     * @return the lastCostUprc
     */
    public Double getLastCostUprc() {
        return lastCostUprc;
    }

    /**
     * @param lastCostUprc the lastCostUprc to set
     */
    public void setLastCostUprc(Double lastCostUprc) {
        this.lastCostUprc = lastCostUprc;
    }

    /**
     * @return the splyUprc
     */
    public Double getSplyUprc() {
        return splyUprc;
    }

    /**
     * @param splyUprc the splyUprc to set
     */
    public void setSplyUprc(Double splyUprc) {
        this.splyUprc = splyUprc;
    }

    /**
     * @return the splyUprcUseYn
     */
    public String getSplyUprcUseYn() {
        return splyUprcUseYn;
    }

    /**
     * @param splyUprcUseYn the splyUprcUseYn to set
     */
    public void setSplyUprcUseYn(String splyUprcUseYn) {
        this.splyUprcUseYn = splyUprcUseYn;
    }

    /**
     * @return the poProdFg
     */
    public String getPoProdFg() {
        return poProdFg;
    }

    /**
     * @param poProdFg the poProdFg to set
     */
    public void setPoProdFg(String poProdFg) {
        this.poProdFg = poProdFg;
    }

    /**
     * @return the poUnitFg
     */
    public String getPoUnitFg() {
        return poUnitFg;
    }

    /**
     * @param poUnitFg the poUnitFg to set
     */
    public void setPoUnitFg(String poUnitFg) {
        this.poUnitFg = poUnitFg;
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
     * @return the poUnitAllowFg
     */
    public String getPoUnitAllowFg() {
        return poUnitAllowFg;
    }

    /**
     * @param poUnitAllowFg the poUnitAllowFg to set
     */
    public void setPoUnitAllowFg(String poUnitAllowFg) {
        this.poUnitAllowFg = poUnitAllowFg;
    }

    /**
     * @return the poMinQty
     */
    public Integer getPoMinQty() {
        return poMinQty;
    }

    /**
     * @param poMinQty the poMinQty to set
     */
    public void setPoMinQty(Integer poMinQty) {
        this.poMinQty = poMinQty;
    }

    /**
     * @return the safeStockQty
     */
    public Integer getSafeStockQty() {
        return safeStockQty;
    }

    /**
     * @param safeStockQty the safeStockQty to set
     */
    public void setSafeStockQty(Integer safeStockQty) {
        this.safeStockQty = safeStockQty;
    }

    /**
     * @return the stockUnitFg
     */
    public String getStockUnitFg() {
        return stockUnitFg;
    }

    /**
     * @param stockUnitFg the stockUnitFg to set
     */
    public void setStockUnitFg(String stockUnitFg) {
        this.stockUnitFg = stockUnitFg;
    }

    /**
     * @return the useYn
     */
    public String getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the barCd
     */
    public String getBarCd() {
        return barCd;
    }

    /**
     * @param barCd the barCd to set
     */
    public void setBarCd(String barCd) {
        this.barCd = barCd;
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
     * @return the chkDt
     */
    public boolean isChkDt() {
        return chkDt;
    }

    /**
     * @param chkDt the chkDt to set
     */
    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
    }

    /**
     * @return the result
     */

    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return the storeRegFg
     */

    public UseYn getStoreRegFg() {
        return storeRegFg;
    }

    /**
     * @param storeRegFg the storeRegFg to set
     */
    public void setStoreRegFg(UseYn storeRegFg) {
        this.storeRegFg = storeRegFg;
    }

    /**
     * @return the salePrcFg
     */

    public String getSalePrcFg() {
        return salePrcFg;
    }

    /**
     * @param salePrcFg the salePrcFg to set
     */
    public void setSalePrcFg(String salePrcFg) {
        this.salePrcFg = salePrcFg;
    }


    /**
     * @return the saleUprc
     */

    public String getSaleUprc() {
        return saleUprc;
    }

    /**
     * @param saleUprc the saleUprc to set
     */
    public void setSaleUprc(String saleUprc) {
        this.saleUprc = saleUprc;
    }

    /**
     * @return the saleUprcB
     */

    public String getSaleUprcB() {
        return saleUprcB;
    }

    /**
     * @param saleUprcB the saleUprcB to set
     */
    public void setSaleUprcB(String saleUprcB) {
        this.saleUprcB = saleUprcB;
    }

    /**
     * @return the regOrgnFg
     */

    public OrgnFg getRegOrgnFg() {
        return regOrgnFg;
    }

    /**
     * @param regOrgnFg the regOrgnFg to set
     */
    public void setRegOrgnFg(OrgnFg regOrgnFg) {
        this.regOrgnFg = regOrgnFg;
    }

    public WorkModeFg getWorkMode() {
        return workMode;
    }

    public void setWorkMode(WorkModeFg workMode) {
        this.workMode = workMode;
    }

    public ProdNoEnvFg getProdNoEnv() {
        return prodNoEnv;
    }

    public void setProdNoEnv(ProdNoEnvFg prodNoEnv) {
        this.prodNoEnv = prodNoEnv;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public UseYn getProdRegFg() {
        return prodRegFg;
    }

    public void setProdRegFg(UseYn prodRegFg) {
        this.prodRegFg = prodRegFg;
    }

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getFileNm() { return fileNm; }

    public void setFileNm(String fileNm) { this.fileNm = fileNm; }

    public String getFileExt() { return fileExt; }

    public void setFileExt(String fileExt) { this.fileExt = fileExt; }

    public String getProdImageDelFg() { return prodImageDelFg; }

    public void setProdImageDelFg(String prodImageDelFg) { this.prodImageDelFg = prodImageDelFg; }

    public Integer getStartStockQty() { return startStockQty; }

    public void setStartStockQty(Integer startStockQty) { this.startStockQty = startStockQty; }

    public String getVendrCd() { return vendrCd; }

    public void setVendrCd(String vendrCd) { this.vendrCd = vendrCd; }

    public String getVendrNm() { return vendrNm; }

    public void setVendrNm(String vendrNm) { this.vendrNm = vendrNm; }

    public String getChkVendrCd() { return chkVendrCd; }

    public void setChkVendrCd(String chkVendrCd) { this.chkVendrCd = chkVendrCd; }

    public String getChkVendrNm() { return chkVendrNm; }

    public void setChkVendrNm(String chkVendrNm) { this.chkVendrNm = chkVendrNm; }

    public String[] getVendrCdList() {
        return vendrCdList;
    }

    public void setVendrCdList(String[] vendrCdList) { this.vendrCdList = vendrCdList; }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String getRegFg() {
        return regFg;
    }

    public void setRegFg(String regFg) {
        this.regFg = regFg;
    }

    public String getExcelGubun() {
        return excelGubun;
    }

    public void setExcelGubun(String excelGubun) {
        this.excelGubun = excelGubun;
    }

    public String getMapProdCd() {
        return mapProdCd;
    }

    public void setMapProdCd(String mapProdCd) {
        this.mapProdCd = mapProdCd;
    }

    public String getSideEnvstVal() {
        return sideEnvstVal;
    }

    public void setSideEnvstVal(String sideEnvstVal) {
        this.sideEnvstVal = sideEnvstVal;
    }

    public String getStinSaleUprc() {
        return stinSaleUprc;
    }

    public void setStinSaleUprc(String stinSaleUprc) {
        this.stinSaleUprc = stinSaleUprc;
    }

    public String getDlvrSaleUprc() {
        return dlvrSaleUprc;
    }

    public void setDlvrSaleUprc(String dlvrSaleUprc) {
        this.dlvrSaleUprc = dlvrSaleUprc;
    }

    public String getPackSaleUprc() {
        return packSaleUprc;
    }

    public void setPackSaleUprc(String packSaleUprc) {
        this.packSaleUprc = packSaleUprc;
    }

    public String getStinSaleUprcB() {
        return stinSaleUprcB;
    }

    public void setStinSaleUprcB(String stinSaleUprcB) {
        this.stinSaleUprcB = stinSaleUprcB;
    }

    public String getDlvrSaleUprcB() {
        return dlvrSaleUprcB;
    }

    public void setDlvrSaleUprcB(String dlvrSaleUprcB) {
        this.dlvrSaleUprcB = dlvrSaleUprcB;
    }

    public String getPackSaleUprcB() {
        return packSaleUprcB;
    }

    public void setPackSaleUprcB(String packSaleUprcB) {
        this.packSaleUprcB = packSaleUprcB;
    }

    public String getPrterNo() {
        return prterNo;
    }

    public void setPrterNo(String prterNo) {
        this.prterNo = prterNo;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getUnitProdCd() {
        return unitProdCd;
    }

    public void setUnitProdCd(String unitProdCd) {
        this.unitProdCd = unitProdCd;
    }

    public String getUnitProdQty() {
        return unitProdQty;
    }

    public void setUnitProdQty(String unitProdQty) {
        this.unitProdQty = unitProdQty;
    }

    public String getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(String dispSeq) {
        this.dispSeq = dispSeq;
    }

    public String getStoreProdUseFg() {
        return storeProdUseFg;
    }

    public void setStoreProdUseFg(String storeProdUseFg) {
        this.storeProdUseFg = storeProdUseFg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    public String getSoldOutYn() {
        return soldOutYn;
    }

    public void setSoldOutYn(String soldOutYn) {
        this.soldOutYn = soldOutYn;
    }

    public String getKioskDisplayYn() {
        return KioskDisplayYn;
    }

    public void setKioskDisplayYn(String kioskDisplayYn) {
        KioskDisplayYn = kioskDisplayYn;
    }

    public String getSdselClassCd() {
        return sdselClassCd;
    }

    public void setSdselClassCd(String sdselClassCd) {
        this.sdselClassCd = sdselClassCd;
    }

    public String[] getUserBrandList() {
        return userBrandList;
    }

    public void setUserBrandList(String[] userBrandList) {
        this.userBrandList = userBrandList;
    }

    public String getUserBrands() {
        return userBrands;
    }

    public void setUserBrands(String userBrands) {
        this.userBrands = userBrands;
    }

    public String getStoreHqBrandCd() {
        return storeHqBrandCd;
    }

    public void setStoreHqBrandCd(String storeHqBrandCd) {
        this.storeHqBrandCd = storeHqBrandCd;
    }

    public String getProdHqBrandCd() {
        return prodHqBrandCd;
    }

    public void setProdHqBrandCd(String prodHqBrandCd) {
        this.prodHqBrandCd = prodHqBrandCd;
    }

    public String[] getProdCdList() {
        return prodCdList;
    }

    public void setProdCdList(String[] prodCdList) {
        this.prodCdList = prodCdList;
    }

    public String getProdCds() {
        return prodCds;
    }

    public void setProdCds(String prodCds) {
        this.prodCds = prodCds;
    }

    public String[] getStoreCdList() {
        return storeCdList;
    }

    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String getMomsTeam() {
        return momsTeam;
    }

    public void setMomsTeam(String momsTeam) {
        this.momsTeam = momsTeam;
    }

    public String getMomsAcShop() {
        return momsAcShop;
    }

    public void setMomsAcShop(String momsAcShop) {
        this.momsAcShop = momsAcShop;
    }

    public String getMomsAreaFg() {
        return momsAreaFg;
    }

    public void setMomsAreaFg(String momsAreaFg) {
        this.momsAreaFg = momsAreaFg;
    }

    public String getMomsCommercial() {
        return momsCommercial;
    }

    public void setMomsCommercial(String momsCommercial) {
        this.momsCommercial = momsCommercial;
    }

    public String getMomsShopType() {
        return momsShopType;
    }

    public void setMomsShopType(String momsShopType) {
        this.momsShopType = momsShopType;
    }

    public String getMomsStoreManageType() {
        return momsStoreManageType;
    }

    public void setMomsStoreManageType(String momsStoreManageType) {
        this.momsStoreManageType = momsStoreManageType;
    }

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
    }

    public String getKioskUseYn() {
        return kioskUseYn;
    }

    public void setKioskUseYn(String kioskUseYn) {
        this.kioskUseYn = kioskUseYn;
    }

    public String getOrgStartDate() {
        return orgStartDate;
    }

    public void setOrgStartDate(String orgStartDate) {
        this.orgStartDate = orgStartDate;
    }

    public String getOrgStartTime() {
        return orgStartTime;
    }

    public void setOrgStartTime(String orgStartTime) {
        this.orgStartTime = orgStartTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getOrgKioskDisplayYn() {
        return orgKioskDisplayYn;
    }

    public void setOrgKioskDisplayYn(String orgKioskDisplayYn) {
        this.orgKioskDisplayYn = orgKioskDisplayYn;
    }

    public String getMomsStoreFg01() { return momsStoreFg01; }

    public void setMomsStoreFg01(String momsStoreFg01) { this.momsStoreFg01 = momsStoreFg01; }

    public String getMomsStoreFg02() { return momsStoreFg02; }

    public void setMomsStoreFg02(String momsStoreFg02) { this.momsStoreFg02 = momsStoreFg02; }

    public String getMomsStoreFg03() { return momsStoreFg03; }

    public void setMomsStoreFg03(String momsStoreFg03) { this.momsStoreFg03 = momsStoreFg03; }

    public String getMomsStoreFg04() { return momsStoreFg04; }

    public void setMomsStoreFg04(String momsStoreFg04) { this.momsStoreFg04 = momsStoreFg04; }

    public String getMomsStoreFg05() { return momsStoreFg05; }

    public void setMomsStoreFg05(String momsStoreFg05) { this.momsStoreFg05 = momsStoreFg05; }
}