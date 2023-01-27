package kr.co.solbipos.base.prod.prod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdMapper.java
 * @Description : 기초관리 - 상품관리 - 상품조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  장혁수       최초생성
 * @ 2018.10.19  노현수       상품조회 관련 변경
 *
 * @author NHN한국사이버결제 KCP 장혁수
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdMapper {

    /**
     * 상품 조회
     * @param prodVO
     * @return List
     */
    List<DefaultMap<String>> getProdList(ProdVO prodVO);

    /**
     * 상품명 조회
     * @param prodVO
     * @return List
     */
    List<DefaultMap<String>> getProdNmList(ProdVO prodVO);

    /**
     * 상품 조회(엑셀다운로드용)
     * @param prodVO
     * @return List
     */
    List<DefaultMap<String>> getProdExcelList(ProdVO prodVO);

    /**
     * 상품 상세 조회
     * @param prodVO
     * @return DefaultMap
     */
    DefaultMap<String> getProdDetail(ProdVO prodVO);

    /**
     * 연결상품 조회
     * @param prodVO
     * @return List
     */
    List<DefaultMap<String>> getLinkedProdList(ProdVO prodVO);

    /** 상품정보 저장 */
    int saveProductInfo(ProdVO prodVO);

    /** 상품정보 추가 저장 */
    int saveProdInfo(ProdVO prodVO);

    /** 본사에서는 프로시저 호출에 사용할 상품의 존재여부를 미리 체크함 */
    int getProdExistInfo(ProdVO prodVO);

    /** 상품코드 조회 */
    String getProdCd(ProdVO prodVO);

    /** prefix 상품코드 조회*/
    String getPrefixProdCd(ProdVO prodVO);

    /** 본사 상품정보 등록시 매장 상품정보에 등록 */
    String insertHqProdToStoreProd(ProdVO prodVO);

    /** 본사 상품정보 수정시 매장 상품정보 수정 */
    String updateHqProdToStoreProd(ProdVO prodVO);

    /** 상품 적용/미적용 매장 조회 */
    List<DefaultMap<String>> getStoreList(ProdVO prodVO);

    /** 상품 적용 매장 등록 */
    int insertProdStore(ProdVO prodVO);

    /** 상품 판매가 저장 */
    int saveSalePrice(ProdVO prodVO);

    /** 상품 판매가 변경 히스토리 등록 */
    int saveSalePriceHistory(ProdVO prodVO);

    /** 상품등록 본사통제여부가 본사인 경우, 본사 상품 등록시 매장 상품 등록 */
    int insertProdStoreDetail(ProdVO prodVO);

    /** 상품판매가 본사통제여부가 본사인 경우, 본사 판매가 등록시 매장 판매가 등록 */
    String saveStoreSalePrice(ProdVO prodVO);

    /** 상품 적용 매장 삭제 */
    int deleteProdStore(ProdVO prodVO);

    /** 상품 매장 적용 삭제시, 해당 상품의 USE_YN 값 변경 */
    int deleteProdStoreDetail(ProdVO prodVO);

    /** 판매가 변경 히스토리 등록 count 조회 */
    int getRegistProdCount(ProdVO prodVO);

    /** 매장 상품 판매가 변경 히스토리 등록 */
    int updateStoreSaleUprcHistory(ProdVO prodVO);

    /** 매장 상품 판매가 변경 */
    int updateStoreSaleUprc(ProdVO prodVO);

    /** 상품코드 중복체크*/
    int getProdCdCnt(ProdVO prodVO);

    /** 바코드 중복체크*/
    List<DefaultMap<String>> chkBarCd(ProdVO prodVO);

    /** 바코드 중복체크(본사)*/
    List<DefaultMap<String>> chkBarCdHq(ProdVO prodVO);

    /** 상품 바코드 존재 여부 확인 */
    int getProdBarCdCnt(ProdVO prodVO);

    /** 상품 적용 매장 등록시, 본사의 상품의 바코드 매장으로 등록 */
    int insertProdBarcdStoreDetail(ProdVO prodVO);

    /** 본사 상품 등록시, 본사 상품의 바코드 등록 */
    int saveProdBarcd(ProdVO prodVO);

    /** 바코드가 공백일경우 기존 바코드 정보 삭제 */
    int deleteProdBarcd(ProdVO prodVO);

    /** 바코드가 공백일경우 기존 바코드 정보 삭제(매장것도 삭제) */
    int deleteProdBarcdStore(ProdVO prodVO);

    /** 바코드가 공백일경우 기존 바코드 정보 삭제(매장것도 삭제) */
    int deleteProdBarcdStoreHq(ProdVO prodVO);

    /** 매장 상품의 바코드 등록 프로시저 */
    int saveProdBarcdStore(ProdVO prodVO);

    /** 본사상품 매장 등록 시, 해당 상품을 사용하는 매장에도  사이드 그룹 추가 */
    String insertSdselGrpToStore (ProdVO prodVO);

    /** 본사상품 매장 등록 시, 해당 상품을 사용하는 매장에도  사이드 분류 추가 */
    String insertSdselClassToStore (ProdVO prodVO);

    /** 본사상품 매장 등록 시, 해당 상품을 사용하는 매장에도  사이드 선택상품 추가 */
    String insertSdselProdToStore (ProdVO prodVO);

    /** 매장 적용 상품 조회 */
    List<DefaultMap<String>> getStoreProdRegList(ProdVO prodVO);

    /** 매장 미적용 상품 조회 */
    List<DefaultMap<String>> getStoreProdNoRegList(ProdVO prodVO);

    /** 본사 상품 등록시, 본사 상품의 상품분류 등록 */
    String insertClsHqToStore(ProdVO prodVO);

    /** 본사 상품 등록시, 본사 상품의 상품분류 수정 */
    String updateClsHqToStore(ProdVO prodVO);

    /** 상품 이미지 저장시 파일여부 체크 */
    String getProdImageFileSaveCheck(ProdVO ProdVO);

    /** 상품 신규등록,수정 팝업 - 상품 이미지 저장 insert */
    int getProdImageFileSaveInsert(ProdVO ProdVO);

    /** 상품 신규등록,수정 팝업 - 상품 이미지 저장 update */
    int getProdImageFileSaveUpdate(ProdVO ProdVO);

    /** 상품 신규등록,수정 팝업 - 상품 이미지 저장 delete */
    int getProdImageFileSaveDelete(ProdVO ProdVO);

    /** 상품 이미지 삭제시 파일명 가져오기 */
    String getProdImageFileSaveImgFileNm(ProdVO ProdVO);

    /** 미적용 상품 거래처 조회 팝업 - 조회 */
    List<DefaultMap<String>> getSearchNoProdVendrList(ProdVO prodVO);

    /** 브랜드 콤보박스 리스트 조회 */
    List<DefaultMap<String>> getBrandComboList(ProdVO prodVO);

    /** 거래처 삭제 */
    int getVendorProdSaveUpdate(ProdVO prodVO);

    /** 거래처 저장 */
    int getVendorProdSaveInsert(ProdVO prodVO);

    /** 사이드메뉴관리의 선택상품에 등록된 상품인지 조회 */
    List<DefaultMap<Object>> getSideProdChk(ProdVO prodVO);


    /** 본사환경설정 [1111 사이드상품자동생성] 조회 */
    String getHqEnvCodeSide(ProdVO prodVO);

    /** 사이드메뉴관리의 선택상품에 등록된 상품 리스트 */
    String getHqSdselProd(ProdVO prodVO);

    /** 매장 상품저장시 등록매장 테이블에도 저장 */
    int insertHqProdStoreTotal(ProdVO prodVO);
    int insertHqProdStore(ProdVO prodVO);

    /** 본사 상품등록시 선택한 사이드메뉴에 걸린 상품 매장에 저장 */
    int insertHqSdselProdStoreTotal(ProdVO prodVO);

    /** 본사 상품등록시 선택한 사이드메뉴에 걸린 상품 바코드 매장에 저장 */
    int insertHqSdselProdStoreBarcdTotal(ProdVO prodVO);

    /** 본사 상품등록시 선택한 사이드메뉴에 걸린 상품 분류 매장에 저장 */
    int insertHqSdselProdStoreClassTotal(ProdVO prodVO);
    int insertHqSdselProdStoreClass(ProdVO prodVO);

    /** 프린터 리스트 조회 */
    List<DefaultMap<String>> getKitchenprintList(ProdVO prodVO);

    /** 프린트 연결 */
    int kitchenprintLink(ProdVO prodVO);

    /** 브랜드 리스트 조회(선택 콤보박스용) */
    List<DefaultMap<Object>> getBrandList(ProdVO prodVO);

    /** 브랜드 리스트 조회(선택 콤보박스용, 선택한 상품에서 현재 사용중인 브랜드 + 사용여부 'Y'인 브랜드) */
    List<DefaultMap<Object>> getBrandList2(ProdVO prodVO);

    /** 세트구성상품 팝업 - 구성내역 리스트 조회 */
    List<DefaultMap<String>> getSetConfigProdList(ProdVO prodVO);

    /** 세트구성상품 팝업 - 상품 리스트 조회 */
    List<DefaultMap<String>> getSrchSetConfigProdList(ProdVO prodVO);

    /** 세트구성상품 팝업 - 상품 새 표기순번 조회 */
    String getSetConfigProdDispSeq(ProdVO prodVO);

    /** 세트구성상품 팝업 - 구성내역 상품등록 */
    int insertSetConfigProd(ProdVO prodVO);

    /** 세트구성상품 팝업 - 구성내역 상품수정 */
    int updateSetConfigProd(ProdVO prodVO);

    /** 세트구성상품 팝업 - 구성내역 상품삭제 */
    int deleteSetConfigProd(ProdVO prodVO);

    /** 선택메뉴 조회 팝업 */
    List<DefaultMap<String>> getSearchSdselGrpList(ProdVO prodVO);

    /** 발주 단위 구분 조회 */
    List<DefaultMap<String>> getPoUnitFgData(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사) 이벤트상품조건 */
    int getHqEventMsgProdCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사) 사이드선택상품 */
    int getHqProductSdselProdCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사) 세트구성상품 */
    int getHqProductUnitstProdCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사) 프로모션 혜택품목 */
    int getHqPromoBeneProdCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사) 프로모션 적용상품 */
    int getHqPromoCondiProdCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사) 메뉴그룹 상품설정 */
    int getHqStoreProdGroupDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사수불) 실사재고 상세 */
    int getStHqActualInspectionDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사수불) 재고조정 상세 */
    int getStHqAdjustDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사수불) 재고폐기 상세 */
    int getStHqDisuseDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사수불) 세트구성해체내역 */
    int getStHqSetprodCompositionCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사수불) 세트구성해체내역 상세 */
    int getStHqSetprodCompositionDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사수불) 현재고 */
    int getStHqStockCurCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사수불) 이동전표-본사간 이동내역 상세 */
    int getPoHqMoveDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사/매장 수불) 분배출고-매장별분배출고내역 */
    int getPoHqDistributeCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사/매장 수불) 출고전표-매장출고내역 상세 */
    int getPoHqOutstockDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사수불) 거래처-업체입고반출전표 상세 */
    int getPoHqVendrInstockDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사수불) 거래처-업체발주전표 상세 */
    int getPoHqVendrOrderDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사수불) 물량오류-매장입고오류내역 상세 */
    int getPoStoreInstockErrorDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사수불) 이동전표-매장간이동내역 상세 */
    int getPoStoreMoveDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 매장) 이벤트상품조건 */
    int getMsEventMsgProdCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 매장) 세트구성상품 */
    int getMsProductUnitstProdCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 매장) 프로모션 혜택품목 */
    int getMsPromoBeneProdCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 매장) 프로모션 적용상품 */
    int getMsPromoCondiProdCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 매장수불) 실사재고 상세 */
    int getStStoreActualInspectionDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 매장수불) 재고조정 상세 */
    int getStStoreAdjustDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 매장수불) 재고폐기 상세 */
    int getStStoreDisuseDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 매장수불) 현재고 */
    int getStStoreStockCurCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사/매장 수불) 분배출고-매장별분배출고내역 */
    int getPoHqStoreDistributeCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 주문전표-본사주문내역 상세 */
    int getPoHqStoreOrderDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 본사/매장 수불) 출고전표-매장출고내역 상세 */
    int getPoHqStoreOutstockDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 매대창고간이동내역 상세 */
    int getPoStoreStandMoveDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 거래처-업체입고반출전표 상세 */
    int getPoStoreVendrInstockDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품삭제 전 데이터 확인 : 거래처-업체발주전표 상세 */
    int getPoStoreVendrOrderDtlCnt(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품추가정보 영구삭제 */
    int deleteProdInfoProdInfo(ProdVO prodVO);

    /** 상품 삭제 팝업 - 키오스크판매시간대정보 영구삭제 */
    int deleteProdInfoSaleTime(ProdVO prodVO);

    /** 상품 삭제 팝업 - 쿠폰적용상품 영구삭제 */
    int deleteProdInfoCouponProd(ProdVO prodVO);

    /** 상품 삭제 팝업 - 키오스크 키맵설정 영구삭제 */
    int deleteProdInfoKioskKey(ProdVO prodVO);

    /** 상품 삭제 팝업 - 키오스크 추천메뉴정보 영구삭제 */
    int deleteProdInfoKioskRecmd(ProdVO prodVO);

    /** 상품 삭제 팝업 - 키오스크 메뉴추천리스트 영구삭제 */
    int deleteProdInfoKioskRecmdProd(ProdVO prodVO);

    /** 상품 삭제 팝업 - 재료/알러지-상품맵핑정보 영구삭제 */
    int deleteProdInfoAlgiProd(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품 바코드 영구삭제 */
    int deleteProdInfoBarcd(ProdVO prodVO);

    /** 상품 삭제 팝업 - 배달앱 상품명-맵핑정보 영구삭제 */
    int deleteProdInfoDlvrProdNm(ProdVO prodVO);

    /** 상품 삭제 팝업 - 마스터-키오스크옵션상품 영구삭제 */
    int deleteProdInfoOption(ProdVO prodVO);

    /** 상품 삭제 팝업 - 재료-상품맵핑정보 영구삭제 */
    int deleteProdInfoRecpProd(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품 판매금액 영구삭제 */
    int deleteProdInfoSalePrice(ProdVO prodVO);

    /** 상품 삭제 팝업 - 거래처별_취급상품 영구삭제 */
    int deleteProdInfoVendorProd(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품_세트구성_상품 영구삭제 */
    int deleteProdInfoUnitstProd(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품_판매터치키 영구삭제 */
    int deleteProdInfoTouchKey(ProdVO prodVO);

    /** 상품 삭제 팝업 - 본사) 상품별_취급매장 영구삭제 */
    int deleteProdInfoHqProductStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 매장) 주방프린터_출력상품 영구삭제 */
    int deleteProdInfoPrintProd(ProdVO prodVO);

    /** 상품 삭제 팝업 - 매장) 상품_사이드선택_상품 영구삭제 */
    int deleteProdInfoSdselProd(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품 이미지 영구삭제 관련, 기존 상품이미지 정보 조회 */
    List<DefaultMap<String>> getProdImgInfo(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품 이미지 영구삭제 */
    int deleteProdInfoImage(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품정보 영구삭제 */
    int deleteProdInfo(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품추가정보 영구삭제 (전매장) */
    int deleteProdInfoProdInfoAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 키오스크판매시간대정보 영구삭제 (전매장) */
    int deleteProdInfoSaleTimeAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 쿠폰적용상품 영구삭제 (전매장) */
    int deleteProdInfoCouponProdAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 키오스크 키맵설정 영구삭제 (전매장) */
    int deleteProdInfoKioskKeyAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 키오스크 추천메뉴정보 영구삭제 (전매장) */
    int deleteProdInfoKioskRecmdAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 키오스크 메뉴추천리스트 영구삭제 (전매장) */
    int deleteProdInfoKioskRecmdProdAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 재료/알러지-상품맵핑정보 영구삭제 (전매장) */
    int deleteProdInfoAlgiProdAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품 바코드 영구삭제 (전매장) */
    int deleteProdInfoBarcdAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 배달앱 상품명-맵핑정보 영구삭제 (전매장) */
    int deleteProdInfoDlvrProdNmAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 마스터-키오스크옵션상품 영구삭제 (전매장) */
    int deleteProdInfoOptionAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 재료-상품맵핑정보 영구삭제 (전매장) */
    int deleteProdInfoRecpProdAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품 판매금액 영구삭제 (전매장) */
    int deleteProdInfoSalePriceAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 거래처별_취급상품 영구삭제 (전매장) */
    int deleteProdInfoVendorProdAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품_세트구성_상품 영구삭제 (전매장) */
    int deleteProdInfoUnitstProdAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품_판매터치키 영구삭제 (전매장) */
    int deleteProdInfoTouchKeyAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 매장) 주방프린터_출력상품 영구삭제(전매장) */
    int deleteProdInfoPrintProdAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 매장) 상품_사이드선택_상품 영구삭제 (전매장)*/
    int deleteProdInfoSdselProdAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품 이미지 영구삭제 관련, 기존 상품이미지 정보 조회(전매장) */
    List<DefaultMap<String>> getProdImgInfoAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품 이미지 영구삭제 (전매장) */
    int deleteProdInfoImageAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품정보 영구삭제(전매장) */
    int deleteProdInfoAllStore(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품 사용여부 '미사용' 으로 변경 */
    int updateProdUseYn(ProdVO prodVO);

    /** 상품 삭제 팝업 - 본사에 속한 매장의 상품도 '미사용' 으로 변경 */
    int updateStoreProdUseYn(ProdVO prodVO);

    /** 상품 삭제 팝업 - [임시]삭제할 상품 임시테이블 등록 */
    int insertTmpDelProduct(ProdVO prodVO);

    /** 상품 삭제 팝업 - [임시]상품삭제 테이블 전체삭제 */
    int deleteAllTmpDelProduct(ProdVO prodVO);

    /** 상품 삭제 팝업 - 상품 전체리스트 조회*/
    List<DefaultMap<String>> getAllProdList(ProdVO prodVO);

    /** KIOSK 판매시간 시간설정 등록 */
    int insertProdSaleTime(ProdVO prodVO);

    /** KIOSK 판매시간 시간설정 삭제 */
    int deleteProdSaleTime(ProdVO prodVO);

    /** KIOSK 판매시간 시간설정 조회 */
    List<DefaultMap<String>> getProdSaleTime(ProdVO prodVO);

    /** 본사상품 매장적용시, 기존 매장의 KIOSK 판매시간 시간설정 삭제 */
    int deleteStoreProdSaleTime(ProdVO prodVO);

    /** 본사상품 매장적용시, 본사의 KIOSK 판매시간 시간설정 매장적용 */
    int insertStoreProdSaleTime(ProdVO prodVO);

    /** 해당 상품의 KIOSK 판매시간 사용여부 파악 */
    String getProdSaleTimeFg(ProdVO prodVO);

    /** 상품 등록매장 적용시, 기존 매장의 KIOSK 판매시간 시간설정 삭제 */
    int deleteProdStoreProdSaleTime(ProdVO prodVO);

    /** 상품 등록매장 적용시, 본사의 KIOSK 판매시간 시간설정 매장적용 */
    int insertProdStoreProdSaleTime(ProdVO prodVO);

    /** 상품정보 추가 테이블 조회 */
    String getProdInfoRowCount(ProdVO prodVO);

    /** 상품옵션그룹 조회 팝업 */
    List<DefaultMap<String>> getSearchOptionGrpList(ProdVO prodVO);

    /** 단품/세트선택설정 조회 팝업 */
    List<DefaultMap<String>> getSearchGroupProdList(ProdVO prodVO);

    /** 보증금상품코드 조회 팝업 */
    List<DefaultMap<String>> getSearchDepositProdList(ProdVO prodVO);

    /** 매장상품일괄등록 - 매장목록 조회 */
    List<DefaultMap<String>> selectStoreList(ProdVO prodVO);
}