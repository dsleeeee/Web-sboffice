package kr.co.solbipos.base.prod.prod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * @Class Name : ProdService.java
 * @Description : 기초관리 - 상품관리 - 상품조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  장혁수      최초생성
 *
 * @author NHN한국사이버결제 KCP 장혁수
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdService {

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 조회
     * @param prodVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> getProdList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품명 조회
     * @param prodVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> getProdNmList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 조회(엑셀다운로드용)
     * @param prodVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> getProdExcelList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 상세 조회
     * @param prodVO 세션정보
     * @return XML_String
     */
    DefaultMap<String> getProdDetail(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 상품정보 저장 */
//    int saveProductInfo(ProdVO prodVO, SessionInfoVO sessionInfoVO);
//    long saveProductInfo(ProdVO prodVO, SessionInfoVO sessionInfoVO);
    String saveProductInfo(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 상품 적용/미적용 매장 조회 */
    List<DefaultMap<String>> getStoreList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 상품 적용매장 등록 */
    int insertProdStore(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO);

    /** 상품 적용매장 삭제 */
    int deleteProdStore(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO);

    /** 상품 등록매장 판매가 변경 */
    int updateStoreSaleUprc(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO);

    /** 상품코드 중복체크 */
    int getProdCdCnt(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 바코드 중복체크 */
    List<DefaultMap<String>> chkBarCd(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 매장 적용/미적용 상품 조회 */
    List<DefaultMap<String>> getStoreProdBatchList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 매장 적용상품 등록 */
    int insertStoreProdBatch(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO);

    /** 상품 신규등록,수정 팝업 - 상품 이미지 저장 */
    boolean getProdImageFileSave(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo);

    /** 미적용 상품 거래처 조회 팝업 - 조회 */
    List<DefaultMap<String>> getSearchNoProdVendrList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 브랜드 콤보박스 리스트 조회 */
    List<DefaultMap<String>> getBrandComboList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴관리의 선택상품에 등록된 상품인지 조회 */
    List<DefaultMap<Object>> getSideProdChk(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 프린트 조회 */
    List<DefaultMap<String>> getKitchenprintList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 프린트 연결 */
    int kitchenprintLink(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO);

    /** 브랜드 리스트 조회(선택 콤보박스용) */
    List<DefaultMap<Object>> getBrandList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 브랜드 리스트 조회(선택 콤보박스용, 선택한 상품에서 현재 사용중인 브랜드 + 사용여부 'Y'인 브랜드) */
    List<DefaultMap<Object>> getBrandList2(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 세트구성상품 팝업 - 구성내역 리스트 조회 */
    List<DefaultMap<String>> getSetConfigProdList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 세트구성상품 팝업 - 상품 리스트 조회 */
    List<DefaultMap<String>> getSrchSetConfigProdList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 세트구성상품 팝업 - 상품 등록/수정/삭제 */
    int saveSetConfigProd(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO);

    /** 선택메뉴 조회 팝업 */
    List<DefaultMap<String>> getSearchSdselGrpList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 발주 단위 구분 조회 */
    List<DefaultMap<String>> getPoUnitFgData(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 선택상품삭제 */
    int selectProdDelete(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO);

    /** 전체상품삭제 */
    int allProdDelete(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** KIOSK 판매시간 시간설정 조회 */
    List<DefaultMap<String>> getProdSaleTime(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 상품옵션그룹 조회 팝업 */
    List<DefaultMap<String>> getSearchOptionGrpList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 단품/세트선택설정 조회 팝업 */
    List<DefaultMap<String>> getSearchGroupProdList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 보증금상품코드 조회 팝업 */
    List<DefaultMap<String>> getSearchDepositProdList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 매장상품일괄등록 - 매장목록 조회 */
    List<DefaultMap<String>> selectStoreList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 상품정보 저장 전 체크 - 선택한 선택메뉴코드가 세트('C')이면서, 나(현재 선택한 상품)를 가진 세트가 있는지 확인 */
    String getSideMenuChk(ProdVO prodVO, SessionInfoVO sessionInfoVO);
}
