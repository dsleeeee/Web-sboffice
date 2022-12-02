package kr.co.solbipos.base.promotion.promotion.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PromotionVO.java
 * @Description : 기초관리 - 프로모션관리 - 프로모션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .04. 13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PromotionVO extends PageVO {

    private static final long serialVersionUID = -3113495758336766350L;

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
    /** 매장명 */
    private String storeNm;
    /** 프로모션코드 */
    private String promotionCd;
    /** 프로모션명 */
    private String promotionNm;
    /** 메모 */
    private String memo;
    /** 사용여부 */
    private String useYn;
    /** 일정 */
    private String promotionDate;
    /** 적용기간 사용여부 */
    private String dateYn;
    /** 적용기간 시작일 */
    private String startYmd;
    /** 적용기간 종료일 */
    private String endYmd;
    /** 적용시간 사용여부 */
    private String timeYn;
    /** 적용시간 시작시간 */
    private String startTime;
    /** 적용시간 종료시간 */
    private String endTime;
    /** 요일 사용여부 */
    private String weekYn;
    /** 월요일 */
    private String monYn;
    /** 화요일 */
    private String tueYn;
    /** 수요일 */
    private String wedYn;
    /** 목요일 */
    private String thuYn;
    /** 금요일 */
    private String friYn;
    /** 토요일 */
    private String satYn;
    /** 일요일 */
    private String sunYn;
    /** 적용 조건 적용대상 */
    private String memberTargetDs;
    /** 적용 조건 적용등급 */
    private String memberClassCd;
    /** 최소 구매 금액 */
    private int minSaleAmt;
    /** 최대 구매 금액 */
    private int maxSaleAmt;
    /** 적용 상품 체크 여부 */
    private String prodCdYn;
    /** 적용 상품 구매 대상 */
    private String selectProdDs;
    /** 적용 상품 교차선택 구분 */
    private String selectProdCrossFg;
    /** 적용 상품 수량 */
    private String selectProdCnt;
    /** 혜택순번 */
    private String beneSeq;
    /** 혜택유형 */
    private String typeCd;
    /** 할인구분 */
    private String applyDcDs;
    /** 할인 적용값 */
    private String dcSet;
    /** 혜택출력 문구 */
    private String printMessage;
    /** 증정구분 */
    private String presentDs;
    /** 증정품 교차선택구분 */
    private String selectCrossFg;
    /** 증정품 수량 */
    private String selectGiftCnt;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 적용상품 순번 */
    private String condiProdSeq;
    /** 상품코드/분류 구분 */
    private String gubunDs;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 상품분류코드명 */
    private String prodClassNm;
    /** 조건수량 */
    private String prodQty;
    /** 매장상태구분 */
    private String sysStatFg;
    /** 증정수량 */
    private String giftQty;
    /** 프로모션 등록구분 - H:본사 S;매장 */
    private String regFg;
    /** 상품등록구분 */
    private String prodSelectExceptFg;
    /** 매장등록구분 */
    private String storeSelectExceptFg;
    /** 프로모션 적용 구분(내점) */
    private String dlv1Yn;
    /** 프로모션 적용 구분(배달) */
    private String dlv2Yn;
    /** 프로모션 적용 구분(포장) */
    private String dlv3Yn;
    /** 매장타입코드 */
    private String storeTypeCd;
    /** 메뉴그룹코드 */
    private String storeGroupCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 사용자 아이디 */
    private String userId;
    /** 프로모션 종류 */
    private String promoType;
    /** 본사분담금 */
    private int hqChargeUprc;
    /** 매장분담금 */
    private int msChargeUprc;
    /** 제휴분담금 */
    private int partnerChargeUprc;
    /** 구매횟수제한 */
    private int momsPurchsCntLimit;
    /** 구매갯수제한 */
    private int momsPurchsQtyLimit;
    /** 프로모션 적용 구분(자사앱(내점)) */
    private String dlv4Yn;
    /** 프로모션 적용 구분(자사앱(배달)) */
    private String dlv5Yn;
    /** 프로모션 적용 구분(자사앱(포장)) */
    private String dlv6Yn;
    /** 프로모션 적용 구분(요기요) */
    private String dlv7Yn;
    /** 프로모션 적용 구분(쿠팡이츠) */
    private String dlv8Yn;
    /** 프로모션 적용 구분(위메프오) */
    private String dlv9Yn;
    /** 프로모션 적용 구분(페이코) */
    private String dlv10Yn;
    /** 프로모션 적용 구분(배달특급) */
    private String dlv11Yn;
    /** 프로모션 적용 구분(카카오) */
    private String dlv12Yn;
    /** 프로모션 적용 구분(배달의민족) */
    private String dlv13Yn;
    /** 프로모션 적용 구분(땡겨요) */
    private String dlv14Yn;
    /** 프로모션 적용 구분(네이버주문) */
    private String dlv15Yn;
    /** 입금일 */
    private String depositYmd;
    /** 제한된 결제*/
    private String blockPayCd;
    /** 버전일련번호 */
    private String verSerNo;
    /** 분담금구분 */
    private String chargeDs;
    /** 사용자별 브랜드코드 */
    private String userBrands;
    /** 사용자별 브랜드코드 */
    private String[] userBrandList;
    /** 매장브랜드코드 */
    private String storeHqBrandCd;
    /** 지사코드 */
    private String branchCd;
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
    /** 사원번호 */
    private String empNo;


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

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getPromotionCd() {
        return promotionCd;
    }

    public void setPromotionCd(String promotionCd) {
        this.promotionCd = promotionCd;
    }

    public String getPromotionNm() {
        return promotionNm;
    }

    public void setPromotionNm(String promotionNm) {
        this.promotionNm = promotionNm;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getPromotionDate() {
        return promotionDate;
    }

    public void setPromotionDate(String promotionDate) {
        this.promotionDate = promotionDate;
    }

    public String getDateYn() {
        return dateYn;
    }

    public void setDateYn(String dateYn) {
        this.dateYn = dateYn;
    }

    public String getStartYmd() {
        return startYmd;
    }

    public void setStartYmd(String startYmd) {
        this.startYmd = startYmd;
    }

    public String getEndYmd() {
        return endYmd;
    }

    public void setEndYmd(String endYmd) {
        this.endYmd = endYmd;
    }

    public String getTimeYn() {
        return timeYn;
    }

    public void setTimeYn(String timeYn) {
        this.timeYn = timeYn;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWeekYn() {
        return weekYn;
    }

    public void setWeekYn(String weekYn) {
        this.weekYn = weekYn;
    }

    public String getMonYn() {
        return monYn;
    }

    public void setMonYn(String monYn) {
        this.monYn = monYn;
    }

    public String getTueYn() {
        return tueYn;
    }

    public void setTueYn(String tueYn) {
        this.tueYn = tueYn;
    }

    public String getWedYn() {
        return wedYn;
    }

    public void setWedYn(String wedYn) {
        this.wedYn = wedYn;
    }

    public String getThuYn() {
        return thuYn;
    }

    public void setThuYn(String thuYn) {
        this.thuYn = thuYn;
    }

    public String getFriYn() {
        return friYn;
    }

    public void setFriYn(String friYn) {
        this.friYn = friYn;
    }

    public String getSatYn() {
        return satYn;
    }

    public void setSatYn(String satYn) {
        this.satYn = satYn;
    }

    public String getSunYn() {
        return sunYn;
    }

    public void setSunYn(String sunYn) {
        this.sunYn = sunYn;
    }

    public String getMemberTargetDs() {
        return memberTargetDs;
    }

    public void setMemberTargetDs(String memberTargetDs) {
        this.memberTargetDs = memberTargetDs;
    }

    public String getMemberClassCd() {
        return memberClassCd;
    }

    public void setMemberClassCd(String memberClassCd) {
        this.memberClassCd = memberClassCd;
    }

    public int getMinSaleAmt() {
        return minSaleAmt;
    }

    public void setMinSaleAmt(int minSaleAmt) {
        this.minSaleAmt = minSaleAmt;
    }

    public int getMaxSaleAmt() {
        return maxSaleAmt;
    }

    public void setMaxSaleAmt(int maxSaleAmt) {
        this.maxSaleAmt = maxSaleAmt;
    }

    public String getProdCdYn() {
        return prodCdYn;
    }

    public void setProdCdYn(String prodCdYn) {
        this.prodCdYn = prodCdYn;
    }

    public String getSelectProdDs() {
        return selectProdDs;
    }

    public void setSelectProdDs(String selectProdDs) {
        this.selectProdDs = selectProdDs;
    }

    public String getSelectProdCrossFg() {
        return selectProdCrossFg;
    }

    public void setSelectProdCrossFg(String selectProdCrossFg) {
        this.selectProdCrossFg = selectProdCrossFg;
    }

    public String getSelectProdCnt() {
        return selectProdCnt;
    }

    public void setSelectProdCnt(String selectProdCnt) {
        this.selectProdCnt = selectProdCnt;
    }

    public String getBeneSeq() {
        return beneSeq;
    }

    public void setBeneSeq(String beneSeq) {
        this.beneSeq = beneSeq;
    }

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public String getApplyDcDs() {
        return applyDcDs;
    }

    public void setApplyDcDs(String applyDcDs) {
        this.applyDcDs = applyDcDs;
    }

    public String getDcSet() {
        return dcSet;
    }

    public void setDcSet(String dcSet) {
        this.dcSet = dcSet;
    }

    public String getPrintMessage() {
        return printMessage;
    }

    public void setPrintMessage(String printMessage) {
        this.printMessage = printMessage;
    }

    public String getPresentDs() {
        return presentDs;
    }

    public void setPresentDs(String presentDs) {
        this.presentDs = presentDs;
    }

    public String getSelectCrossFg() {
        return selectCrossFg;
    }

    public void setSelectCrossFg(String selectCrossFg) {
        this.selectCrossFg = selectCrossFg;
    }

    public String getSelectGiftCnt() {
        return selectGiftCnt;
    }

    public void setSelectGiftCnt(String selectGiftCnt) {
        this.selectGiftCnt = selectGiftCnt;
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

    public String getCondiProdSeq() {
        return condiProdSeq;
    }

    public void setCondiProdSeq(String condiProdSeq) {
        this.condiProdSeq = condiProdSeq;
    }

    public String getGubunDs() {
        return gubunDs;
    }

    public void setGubunDs(String gubunDs) {
        this.gubunDs = gubunDs;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getProdClassNm() {
        return prodClassNm;
    }

    public void setProdClassNm(String prodClassNm) {
        this.prodClassNm = prodClassNm;
    }

    public String getProdQty() {
        return prodQty;
    }

    public void setProdQty(String prodQty) {
        this.prodQty = prodQty;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String getGiftQty() {
        return giftQty;
    }

    public void setGiftQty(String giftQty) {
        this.giftQty = giftQty;
    }

    public String getRegFg() {
        return regFg;
    }

    public void setRegFg(String regFg) {
        this.regFg = regFg;
    }

    public String getProdSelectExceptFg() {
        return prodSelectExceptFg;
    }

    public void setProdSelectExceptFg(String prodSelectExceptFg) {
        this.prodSelectExceptFg = prodSelectExceptFg;
    }

    public String getStoreSelectExceptFg() {
        return storeSelectExceptFg;
    }

    public void setStoreSelectExceptFg(String storeSelectExceptFg) {
        this.storeSelectExceptFg = storeSelectExceptFg;
    }

    public String getDlv1Yn() {
        return dlv1Yn;
    }

    public void setDlv1Yn(String dlv1Yn) {
        this.dlv1Yn = dlv1Yn;
    }

    public String getDlv2Yn() {
        return dlv2Yn;
    }

    public void setDlv2Yn(String dlv2Yn) {
        this.dlv2Yn = dlv2Yn;
    }

    public String getDlv3Yn() {
        return dlv3Yn;
    }

    public void setDlv3Yn(String dlv3Yn) {
        this.dlv3Yn = dlv3Yn;
    }

    public String getStoreTypeCd() {
        return storeTypeCd;
    }

    public void setStoreTypeCd(String storeTypeCd) {
        this.storeTypeCd = storeTypeCd;
    }

    public String getStoreGroupCd() {
        return storeGroupCd;
    }

    public void setStoreGroupCd(String storeGroupCd) {
        this.storeGroupCd = storeGroupCd;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPromoType() {
        return promoType;
    }

    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }

    public int getHqChargeUprc() {
        return hqChargeUprc;
    }

    public void setHqChargeUprc(int hqChargeUprc) {
        this.hqChargeUprc = hqChargeUprc;
    }

    public int getMsChargeUprc() {
        return msChargeUprc;
    }

    public void setMsChargeUprc(int msChargeUprc) {
        this.msChargeUprc = msChargeUprc;
    }

    public int getPartnerChargeUprc() {
        return partnerChargeUprc;
    }

    public void setPartnerChargeUprc(int partnerChargeUprc) {
        this.partnerChargeUprc = partnerChargeUprc;
    }

    public int getMomsPurchsCntLimit() {
        return momsPurchsCntLimit;
    }

    public void setMomsPurchsCntLimit(int momsPurchsCntLimit) {
        this.momsPurchsCntLimit = momsPurchsCntLimit;
    }

    public int getMomsPurchsQtyLimit() {
        return momsPurchsQtyLimit;
    }

    public void setMomsPurchsQtyLimit(int momsPurchsQtyLimit) {
        this.momsPurchsQtyLimit = momsPurchsQtyLimit;
    }

    public String getDlv4Yn() {
        return dlv4Yn;
    }

    public void setDlv4Yn(String dlv4Yn) {
        this.dlv4Yn = dlv4Yn;
    }

    public String getDlv5Yn() {
        return dlv5Yn;
    }

    public void setDlv5Yn(String dlv5Yn) {
        this.dlv5Yn = dlv5Yn;
    }

    public String getDlv6Yn() {
        return dlv6Yn;
    }

    public void setDlv6Yn(String dlv6Yn) {
        this.dlv6Yn = dlv6Yn;
    }

    public String getDlv7Yn() {
        return dlv7Yn;
    }

    public void setDlv7Yn(String dlv7Yn) {
        this.dlv7Yn = dlv7Yn;
    }

    public String getDlv8Yn() {
        return dlv8Yn;
    }

    public void setDlv8Yn(String dlv8Yn) {
        this.dlv8Yn = dlv8Yn;
    }

    public String getDlv9Yn() {
        return dlv9Yn;
    }

    public void setDlv9Yn(String dlv9Yn) {
        this.dlv9Yn = dlv9Yn;
    }

    public String getDlv10Yn() {
        return dlv10Yn;
    }

    public void setDlv10Yn(String dlv10Yn) {
        this.dlv10Yn = dlv10Yn;
    }

    public String getDlv11Yn() {
        return dlv11Yn;
    }

    public void setDlv11Yn(String dlv11Yn) {
        this.dlv11Yn = dlv11Yn;
    }

    public String getDlv12Yn() {
        return dlv12Yn;
    }

    public void setDlv12Yn(String dlv12Yn) {
        this.dlv12Yn = dlv12Yn;
    }

    public String getDlv13Yn() {
        return dlv13Yn;
    }

    public void setDlv13Yn(String dlv13Yn) {
        this.dlv13Yn = dlv13Yn;
    }

    public String getDlv14Yn() {
        return dlv14Yn;
    }

    public void setDlv14Yn(String dlv14Yn) {
        this.dlv14Yn = dlv14Yn;
    }

    public String getDlv15Yn() {
        return dlv15Yn;
    }

    public void setDlv15Yn(String dlv15Yn) {
        this.dlv15Yn = dlv15Yn;
    }

    public String getDepositYmd() {
        return depositYmd;
    }

    public void setDepositYmd(String depositYmd) {
        this.depositYmd = depositYmd;
    }

    public String getBlockPayCd() {
        return blockPayCd;
    }

    public void setBlockPayCd(String blockPayCd) {
        this.blockPayCd = blockPayCd;
    }

    public String getVerSerNo() {
        return verSerNo;
    }

    public void setVerSerNo(String verSerNo) {
        this.verSerNo = verSerNo;
    }

    public String getChargeDs() {
        return chargeDs;
    }

    public void setChargeDs(String chargeDs) {
        this.chargeDs = chargeDs;
    }

    public String getUserBrands() {
        return userBrands;
    }

    public void setUserBrands(String userBrands) {
        this.userBrands = userBrands;
    }

    public String[] getUserBrandList() {
        return userBrandList;
    }

    public void setUserBrandList(String[] userBrandList) {
        this.userBrandList = userBrandList;
    }

    public String getStoreHqBrandCd() {
        return storeHqBrandCd;
    }

    public void setStoreHqBrandCd(String storeHqBrandCd) {
        this.storeHqBrandCd = storeHqBrandCd;
    }

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
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

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
}
