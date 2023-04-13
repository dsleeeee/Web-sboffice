package kr.co.solbipos.base.prod.prod.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;

/**
 * @Class Name : ProdVO.java
 * @Description : 기초관리 - 상품관리 - 상품조회
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  장혁수       최초생성
 * @ 2018.10.19  노현수       소속구분 타입 변경
 * @ 2019.06.03  이다솜       workMode 추가 (PKG_HQ_PRODUCT_SALE_PRICE.SP_HQ_PRODUCT_SALE_PRICE_I 호출 시 조회 조건 구분을 위함)
 *
 * @author NHN한국사이버결제 KCP 장혁수
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ProdVO extends PageVO {

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
    /** 상품코드 */
    private String searchProdCd;
    /** 상품명 */
    private String searchProdNm;
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
    /** 단품상품여부 */
    private String singleYn;
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
    /** 비고 */
    private String remark;
    /** 상품 상세 설명 */
    private String prodInfo;

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

    /** 세션ID */
    private String sessionId;

    /** 순번 */
    private int seq;

    /** 상품삭제구분 */
    private String delTypeFg;

    /** 보증컵유형 */
    private String depositCupFg;
    /** 포인트사용여부 */
    private String pointUseYn;
    /** 할인여부 */
    private String dcYn;
    /** 매장상품 */
    private String originalStore;

    /** KIOSK 판매시간 사용여부 */
    private String saleTimeFg;
    /** KIOSK 판매시간 시간설정 */
    private String saleTime;
    /** KIOSK 판매시간 등록순번 */
    private int timeSeq;
    /** KIOSK 판매시간 시간설정구분 [1:시간내적용 2:시간내제외] */
    private String includeFg;
    /** KIOSK 판매시간 시작시간*/
    private String sSaleTime;
    /** KIOSK 판매시간 종료시간 */
    private String eSaleTime;

    /** 총중량 */
    private String nuTotWt;
    /** 총열량 */
    private String nuKcal;
    /** 단백질 */
    private String nuProtein;
    /** 나트륨 */
    private String nuSodium;
    /** 당류 */
    private String nuSugars;
    /** 포화지방 */
    private String nuSatFat;
    /** 카페인 */
    private String nuCaffeine;

    /** KIOSK 엣지 */
    private String momsKioskEdge;

    /** 가격예약구분 - 0:일반, 1:예약 */
    private String saleResveFg;

    /** 상품옵션그룹코드 */
    private String optionGrpCd;

    /** 상품옵션그룹명 */
    private String optionGrpNm;

    /** 단품/세트선택설정 */
    private String groupProdCd;

    /** 보증금상품코드 */
    private String depositProdCd;

    /** 출시일 */
    private String releaseDate;

    /** 단종일 */
    private String disconDate;

    /** 단종여부 */
    private String disconYn;

    /** 판매방식 - 내점 */
    private String saleTypeYnSin;

    /** 판매방식 - 배달 */
    private String saleTypeYnDlv;

    /** 판매방식 - 포장 */
    private String saleTypeYnPkg;

    /** 판매채널 - POS */
    private String saleChnYnPos;

    /** 판매채널 - KIOSK */
    private String saleChnYnKsk;

    /** 판매채널 - 자사앱 */
    private String saleChnYnCmp;

    /** 판매채널 - 배민 */
    private String saleChnYnBae;

    /** 판매채널 - 배민1 */
    private String saleChnYnBao;

    /** 판매채널 - 요기요 */
    private String saleChnYnYgy;

    /** 판매채널 - 요기요익스프레스 */
    private String saleChnYnYge;

    /** 판매채널 - 쿠팡 */
    private String saleChnYnCpn;

    /** 판매채널 - 배달통 */
    private String saleChnYnTng;

    /** 판매채널 - 땡겨요 */
    private String saleChnYnDdn;

    /** 사용자별 브랜드코드(상품) */
    private String[] userProdBrandList;

    /** 사용자별 브랜드코드(상품) */
    private String userProdBrands;

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    /** 사용자별 브랜드코드(매장) */
        private String[] userBrandList;

    /** 사용자별 브랜드코드(매장) */
    private String userBrands;

    /** 매장브랜드코드 */
    private String storeHqBrandCd;

    /** 사원번호 */
    private String empNo;

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

    public String getSearchProdCd() {
        return searchProdCd;
    }

    public void setSearchProdCd(String searchProdCd) {
        this.searchProdCd = searchProdCd;
    }

    public String getSearchProdNm() {
        return searchProdNm;
    }

    public void setSearchProdNm(String searchProdNm) {
        this.searchProdNm = searchProdNm;
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

    public String getSingleYn() {
        return singleYn;
    }

    public void setSingleYn(String singleYn) {
        this.singleYn = singleYn;
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

    public String getProdInfo() {
        return prodInfo;
    }

    public void setProdInfo(String prodInfo) {
        this.prodInfo = prodInfo;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getDelTypeFg() {
        return delTypeFg;
    }

    public void setDelTypeFg(String delTypeFg) {
        this.delTypeFg = delTypeFg;
    }

    public String getDepositCupFg() {
        return depositCupFg;
    }

    public void setDepositCupFg(String depositCupFg) {
        this.depositCupFg = depositCupFg;
    }


    public String getPointUseYn() {
        return pointUseYn;
    }

    public void setPointUseYn(String pointUseYn) {
        this.pointUseYn = pointUseYn;
    }

    public String getDcYn() {
        return dcYn;
    }

    public void setDcYn(String dcYn) {
        this.dcYn = dcYn;
    }

    public String getOriginalStore() {
        return originalStore;
    }

    public void setOriginalStore(String originalStore) {
        this.originalStore = originalStore;
    }

    public String getSaleTimeFg() {
        return saleTimeFg;
    }

    public void setSaleTimeFg(String saleTimeFg) {
        this.saleTimeFg = saleTimeFg;
    }

    public String getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
    }

    public int getTimeSeq() {
        return timeSeq;
    }

    public void setTimeSeq(int timeSeq) {
        this.timeSeq = timeSeq;
    }

    public String getIncludeFg() {
        return includeFg;
    }

    public void setIncludeFg(String includeFg) {
        this.includeFg = includeFg;
    }

    public String getsSaleTime() {
        return sSaleTime;
    }

    public void setsSaleTime(String sSaleTime) {
        this.sSaleTime = sSaleTime;
    }

    public String geteSaleTime() {
        return eSaleTime;
    }

    public void seteSaleTime(String eSaleTime) {
        this.eSaleTime = eSaleTime;
    }

    public String getNuTotWt() { return nuTotWt; }

    public void setNuTotWt(String nuTotWt) { this.nuTotWt = nuTotWt; }

    public String getNuKcal() { return nuKcal; }

    public void setNuKcal(String nuKcal) { this.nuKcal = nuKcal; }

    public String getNuProtein() { return nuProtein; }

    public void setNuProtein(String nuProtein) { this.nuProtein = nuProtein; }

    public String getNuSodium() { return nuSodium; }

    public void setNuSodium(String nuSodium) { this.nuSodium = nuSodium; }

    public String getNuSugars() { return nuSugars; }

    public void setNuSugars(String nuSugars) { this.nuSugars = nuSugars; }

    public String getNuSatFat() { return nuSatFat; }

    public void setNuSatFat(String nuSatFat) { this.nuSatFat = nuSatFat; }

    public String getNuCaffeine() { return nuCaffeine; }

    public void setNuCaffeine(String nuCaffeine) { this.nuCaffeine = nuCaffeine; }

    public String getMomsKioskEdge() { return momsKioskEdge; }

    public void setMomsKioskEdge(String momsKioskEdge) { this.momsKioskEdge = momsKioskEdge; }

    public String getSaleResveFg() {
        return saleResveFg;
    }

    public void setSaleResveFg(String saleResveFg) {
        this.saleResveFg = saleResveFg;
    }

    public String getOptionGrpCd() {
        return optionGrpCd;
    }

    public void setOptionGrpCd(String optionGrpCd) {
        this.optionGrpCd = optionGrpCd;
    }

    public String getOptionGrpNm() {
        return optionGrpNm;
    }

    public void setOptionGrpNm(String optionGrpNm) {
        this.optionGrpNm = optionGrpNm;
    }

    public String getGroupProdCd() {
        return groupProdCd;
    }

    public void setGroupProdCd(String groupProdCd) {
        this.groupProdCd = groupProdCd;
    }

    public String getDepositProdCd() {
        return depositProdCd;
    }

    public void setDepositProdCd(String depositProdCd) {
        this.depositProdCd = depositProdCd;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDisconDate() {
        return disconDate;
    }

    public void setDisconDate(String disconDate) {
        this.disconDate = disconDate;
    }

    public String getDisconYn() {
        return disconYn;
    }

    public void setDisconYn(String disconYn) {
        this.disconYn = disconYn;
    }

    public String getSaleTypeYnSin() {
        return saleTypeYnSin;
    }

    public void setSaleTypeYnSin(String saleTypeYnSin) {
        this.saleTypeYnSin = saleTypeYnSin;
    }

    public String getSaleTypeYnDlv() {
        return saleTypeYnDlv;
    }

    public void setSaleTypeYnDlv(String saleTypeYnDlv) {
        this.saleTypeYnDlv = saleTypeYnDlv;
    }

    public String getSaleTypeYnPkg() {
        return saleTypeYnPkg;
    }

    public void setSaleTypeYnPkg(String saleTypeYnPkg) {
        this.saleTypeYnPkg = saleTypeYnPkg;
    }

    public String getSaleChnYnPos() {
        return saleChnYnPos;
    }

    public void setSaleChnYnPos(String saleChnYnPos) {
        this.saleChnYnPos = saleChnYnPos;
    }

    public String getSaleChnYnKsk() {
        return saleChnYnKsk;
    }

    public void setSaleChnYnKsk(String saleChnYnKsk) {
        this.saleChnYnKsk = saleChnYnKsk;
    }

    public String getSaleChnYnCmp() {
        return saleChnYnCmp;
    }

    public void setSaleChnYnCmp(String saleChnYnCmp) {
        this.saleChnYnCmp = saleChnYnCmp;
    }

    public String getSaleChnYnBae() {
        return saleChnYnBae;
    }

    public void setSaleChnYnBae(String saleChnYnBae) {
        this.saleChnYnBae = saleChnYnBae;
    }

    public String getSaleChnYnBao() {
        return saleChnYnBao;
    }

    public void setSaleChnYnBao(String saleChnYnBao) {
        this.saleChnYnBao = saleChnYnBao;
    }

    public String getSaleChnYnYgy() {
        return saleChnYnYgy;
    }

    public void setSaleChnYnYgy(String saleChnYnYgy) {
        this.saleChnYnYgy = saleChnYnYgy;
    }

    public String getSaleChnYnYge() {
        return saleChnYnYge;
    }

    public void setSaleChnYnYge(String saleChnYnYge) {
        this.saleChnYnYge = saleChnYnYge;
    }

    public String getSaleChnYnCpn() {
        return saleChnYnCpn;
    }

    public void setSaleChnYnCpn(String saleChnYnCpn) {
        this.saleChnYnCpn = saleChnYnCpn;
    }

    public String getSaleChnYnTng() {
        return saleChnYnTng;
    }

    public void setSaleChnYnTng(String saleChnYnTng) {
        this.saleChnYnTng = saleChnYnTng;
    }

    public String getSaleChnYnDdn() {
        return saleChnYnDdn;
    }

    public void setSaleChnYnDdn(String saleChnYnDdn) {
        this.saleChnYnDdn = saleChnYnDdn;
    }

    public String[] getUserProdBrandList() {
        return userProdBrandList;
    }

    public void setUserProdBrandList(String[] userProdBrandList) {
        this.userProdBrandList = userProdBrandList;
    }

    public String getUserProdBrands() {
        return userProdBrands;
    }

    public void setUserProdBrands(String userProdBrands) {
        this.userProdBrands = userProdBrands;
    }

    public String getProdHqBrandCd() {
        return prodHqBrandCd;
    }

    public void setProdHqBrandCd(String prodHqBrandCd) {
        this.prodHqBrandCd = prodHqBrandCd;
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

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
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
}