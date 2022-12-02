package kr.co.solbipos.store.manage.storemanage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import kr.co.solbipos.store.manage.storemanage.service.*;
import kr.co.solbipos.store.manage.terminalManage.service.StoreCornerVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreManageMapper.java
 * @Description : 가맹점관리 > 매장관리 > 매장정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018. 06.08  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreManageMapper {

    /** 매장 목록 조회 */
    List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO);

    /** 매장 목록 엑셀조회 */
    List<DefaultMap<String>> getStoreExcelList(StoreManageVO storeManageVO);

    /** 매장 상세 조회 */
    DefaultMap<String> getStoreDetail(StoreManageVO storeManageVO);

    /** 설치 포스수 조회 */
    int getInstPosCnt(StoreManageVO storeManageVO);

    /** 매장 콤보리스트 조회 */
    List<DefaultMap<String>> getStoreComboList(StoreManageVO storeManageVO);

    /** 매장환경조회 팝업 데이터(매장정보) 조회 */
    DefaultMap<String> getStoreEnvInfo(StoreManageVO storeManageVO);

    /** 매장환경조회 팝업 데이터(포스정보) 조회 */
    List<DefaultMap<String>> getStorePosInfo(StoreManageVO storeManageVO);

    /** 매장환경조회 팝업 데이터(주방프린터) 조회 */
    List<DefaultMap<String>> getStorePrintInfo(StoreManageVO storeManageVO);

    /** 신규매장 등록을 위한 매장 코드 조회 */
    String getStoreCd(StoreManageVO storeManageVO);

//    /** 포스 프로그램 구분 조회 */
//    String getPosEnvValue(StoreManageVO storeManageVO);

    /** 신규 매장정보 저장 */
    int saveStoreInfo(StoreManageVO storeManageVO);

    /** 매장정보 수정 */
    int updateStoreInfo(StoreManageVO storeManageVO);

    /** 매장포스승인정보 저장 */
    int updateStorePosVanInfo(StorePosVO storePosVO);

//    /** 해당 브랜드의 분류 복사하여 매장 분류 등록하기 */
//    int copyClsInfo(StoreManageVO storeManageVO);

    /** 매장 기본 사용자 등록 */
    int insertStoreDefaultUser(StoreManageVO storeManageVO);

    /** 매장 웹 사용자 등록 */
    int insertStoreWebUser(StoreManageVO storeManageVO);

    /** 기본 매출 시간대 */
    int insertStoreTimeSlot(StoreManageVO storeManageVO);

    /** 포스 출력물 템플릿 등록 (단독) */
    int insertDefaultPrintTemplete(StoreManageVO storeManageVO);

    /** 포스 실제출력물 등록 (단독) */
    int insertDefaultPrint(StoreManageVO storeManageVO);

    /** 포스 출력물 템플릿 등록 (프랜차이즈) */
    int insertHqPrintTemplete(StoreManageVO storeManageVO);

    /** 포스 실제출력물 등록 (프랜차이즈) */
    int insertHqPrint(StoreManageVO storeManageVO);

    /** 포스 마스터 등록*/
    int insertPosInfo(StoreManageVO storeManageVO);

    /** 매장 기본 코너 등록 */
    int insertStoreCorner(StoreCornerVO storeCornerVO);

    /** 매장 기본 창고 등록 */
    int insertStorage(StoreManageVO storeManageVO);

    /** 회원 기본등급 등록 */
    int insertMemberClass(MemberClassVO memberClassVO);

    /** 결제수단분류 생성 */
    int insertTbMsPayMethodClass(StoreManageVO storeManageVO);

    /** 상품권 생성 */
    int insertTbMsGift(StoreManageVO storeManageVO);

    /** 쿠폰 생성 */
    int insertTbMsCoupon(StoreManageVO storeManageVO);

    /** 테이블 기본 그룹 생성 */
    int insertTabGroup(TableGroupVO tableGroupVO);

    /** 매장환경 등록  */
    int insertStoreEnvInfo(StoreManageVO storeManageVO);

    /** 매장환경 등록  */
    int updateStoreEnvst(StoreEnvVO storeEnvVO);

    /** 외식환경 등록 */
    int insertFoodEnvInfo(StoreManageVO storeManageVO);

    /** 포스환경 등록 */
    int insertPosEnvInfo(StoreManageVO storeManageVO);

    /** 포스 기능키 등록 */
    int insertPosFunKeyInfo(StoreManageVO storeManageVO);

    /** 주방 프린터 환경 등록 */
    int insertKitchenPrintEnvInfo(StoreManageVO storeManageVO);

    /** 상품 분류 복사 */
    int insertStoreProductClass(StoreManageVO storeManageVO);
    int insertStoreHqProductClass(StoreManageVO storeManageVO);

    /** 상품 복사 */
    int insertStoreProduct(StoreManageVO storeManageVO);
    int insertStoreHqProduct(StoreManageVO storeManageVO);

    /** 판매가 복사 */
    int insertStoreSaleUprc(StoreManageVO storeManageVO);
    int insertStoreHqSaleUprc(StoreManageVO storeManageVO);

    /** 공급가 복사  */
    int insertStoreSplyUprc(StoreManageVO storeManageVO);
//    int insertStoreHqSplyUprc(StoreManageVO storeManageVO);

    /** 바코드 복사  */
    int insertStoreBarcd(StoreManageVO storeManageVO);
    int insertStoreHqBarcd(StoreManageVO storeManageVO);

    /** 상품상세설명 복사 */
    int insertStoreHqProdInfo(StoreManageVO storeManageVO);

    /** 상품 KIOSK 판매 시간설정 */
    int insertStoreHqKioskSaleTime(StoreManageVO storeManageVO);

    /** 취급상품 복사  */
    int insertStoreHqProductStore(StoreManageVO storeManageVO);
    int deleteStoreProductStore(StoreManageVO storeManageVO);
    int insertStoreProductStore(StoreManageVO storeManageVO);

    /** 사이드(속성) 복사  */
    int insertStoreHqSdattrClass(StoreManageVO storeManageVO);
    int insertStoreHqSdattr(StoreManageVO storeManageVO);
    int insertStoreSdattrClass(StoreManageVO storeManageVO);
    int insertStoreSdattr(StoreManageVO storeManageVO);

    /** 사이드(선택메뉴) 복사  */
    int insertStoreHqSdselGroup(StoreManageVO storeManageVO);
    int insertStoreHqSdselClass(StoreManageVO storeManageVO);
    int insertStoreHqSdselProd(StoreManageVO storeManageVO);
    int insertStoreSdselGroup(StoreManageVO storeManageVO);
    int insertStoreSdselClass(StoreManageVO storeManageVO);
    int insertStoreSdselProd(StoreManageVO storeManageVO);

    /** 상품 설명 복사 */
    int insertStoreProdInfo(StoreManageVO storeManageVO);
    /** 상품 판매 시간대 복사 */
    int insertStoreSaleTime(StoreManageVO storeManageVO);

    /** 매장 기능키 복사  */
    int copyStoreFnkey(StoreFnkeyVO storeFnkeyVO);

    /** 포스 기능키 복사  */
    int copyPosFnkey(PosFnkeyVO posFnkeyVO);

    /** 매장설정 XML정보 복사 */
    int copyStoreConfXml(ConfgXmlVO confgXmlVO);

    /** 복사할 매장의 포스 MAX */
    String getCopyStorePosMax(ConfgXmlVO confgXmlVO);

    /** 포스설정 XML정보 복사 */
    int copyPosConfXml(ConfgXmlVO confgXmlVO);

    /** 포스기능키별 적용매장 등록 */
    int registPosFnkeyStore(StoreFnkeyVO storeFnkeyVO);

    /** 터치키 분류 복사 TODO */
    int copyFnkeyClassCopy(TouchKeyClassVO touchkeyClassVO);

    /** 터치키 그룹명 복사 TODO */
    int copyFnkeyGrpCopy(TouchKeyClassVO touchkeyClassVO);

    /** 터치키 복사 TODO */
    int copyFnkeyCopy(TouchKeyVO touchkeyVO);

    /** 매장환경 정보 조회 */
    List<DefaultMap<String>> getEnvGroupList(StoreEnvVO storeEnvVO);

    /** 매장환경 정보 저장 */
    int insertStoreConfig(StoreEnvVO storeEnvVO);

    /** 매장환경 정보 수정 */
    int updateStoreConfig(StoreEnvVO storeEnvVO);

    /** 터치키 재설정 */
    String updateTouchKeyMng(StoreEnvVO storeEnvVO);

    /** 매장 포스 환경 정보 조회 */
    List<DefaultMap<String>> getPosEnvGroupList(StorePosEnvVO storePosEnvVOs);

    /** 매장 포스 환경 정보 저장 */
    int insertPosConfig(StorePosEnvVO storePosEnvVO);

    /** 매장 포스 환경 정보 수정 */
    int updatePosConfig(StorePosEnvVO storePosEnvVO);

    /** 매장 메인포스 제외, 나머지 포스는 서브포스로 변경 */
    int updateToSubPos(StorePosEnvVO storePosEnvVO);

    /** 포스 목록 조회  */
    List<DefaultMap<String>> getPosList(StorePosEnvVO storePosEnvVO);

    /** 테이블 그룹 (selectBox 용) */
    List<DefaultMap<String>> getGroupList(StorePosEnvVO storePosEnvVO);

    /** 테이블 그룹설정 정보 저장 */
    int savePosTabGrp(StorePosEnvVO storePosEnvVO);

    /** 테이블 명칭설정정보 저장 */
    int updatePosNm(StorePosEnvVO storePosEnvVO);

    /** 공통코드 복사 TODO */
    String copyCmmNameCode(StoreNmcodeVO nmcodeVO);

    /** TID 복사 프로시저 */
//    String copyTid(StoreNmcodeVO nmcodeVO);

    /** 매장 포스 환경 삭제 */
    int deletePosEnvTarget(StorePosEnvVO storePosEnvVO);

    /** 매장 포스 환경 복사 */
    int copyPosEnvInfo(StorePosEnvVO storePosEnvVO);

    /** 메인포스 변경 */
    int updatePosEnv(StorePosEnvVO storePosEnvVO);

    /** 포스데이터 복사 */
    int copyPosInfo(StorePosEnvVO storePosEnvVO);

    /** 기능키 복사시, 포스 기능키 기존거 삭제 */
    int deletePosFunkeyTarget(StorePosEnvVO storePosEnvVO);

    /** 기능키 복사*/
    int copyPosFunKeyInfo(StorePosEnvVO storePosEnvVO);

    /** 포스 설치되어있는지 체크 */
    int chkInstallPos(StorePosEnvVO storePosEnvVO);

    /** 매출자료가 있는지 체크 */
    int chkSaleYn(StorePosEnvVO storePosEnvVO);

    /** 포스 환경 삭제 */
    int deletePosEnv(StorePosEnvVO storePosEnvVO);

    /** 포스 환경 삭제 */
    int deletePosFunkey(StorePosEnvVO storePosEnvVO);

    /** 포스 환경 삭제 */
    int deletePosMaster(StorePosEnvVO storePosEnvVO);

    /** 주방프린터 조회 */
    List<DefaultMap<String>> getKitchenPrintInfo(StoreEnvVO storeEnvVO);

    /** 주방프린터 등록 */
    int insertKitchenPrint(KitchenPrintVO kitchenPrintVO);

    /** 주방프린터 수정 */
    int updateKitchenPrint(KitchenPrintVO kitchenPrintVO);

    /** 주방프린터 삭제 */
    int deleteKitchenPrint(KitchenPrintVO kitchenPrintVO);

    /** 주방프린터 출력상품 조회 */
    List<DefaultMap<String>> getPrintProductInfo(StoreProductVO storeProductVO);

    /** 상품 분류 조회 */
    List<DefaultMap<String>> getProdClass(StoreProductVO storeProductVO);

    /** 주방프린터 출력상품 등록 */
    int insertKitchenPrintProduct(StoreProductVO storeProductVO);

    /** 주방프린터 출력상품 삭제 */
    int deleteKitchenPrintProduct(StoreProductVO storeProductVO);

    /** 터치키 복사할 본사 목록 조회 */
    List<DefaultMap<String>> getHqList();

//    /** 터치키 복사할 브랜드 목록 조회 */
//    List<DefaultMap<String>> getHqBrandList(HqBrandVO hqBrandVO);

    /** 터치키 복사할 매장 목록 조회 */
    List<DefaultMap<String>> getTouchKeyStoreList(HqManageVO hqManageVO);

    /** 포스기능키 기존설정 조회 */
    String getFuncKeyXml(DefaultMap<String> param);

    /** 포스기능키 XML 정보 생성 */
    int insertFuncKeyConfgXml(DefaultMap<String> param);

    //    /** 벤사, 코너 정보 조회 */
//    List<DefaultMap<String>> getVanCornrList(StoreManageVO storeManageVO);

    /** 설치포스 MAX Pos No값 조회 */
    int getInstPosCntMax(StoreManageVO storeManageVO);

    /** 매장코드 중복체크*/
    int getStoreCdCnt(StoreManageVO storeManageVO);

    /** 권한그룹복사를 위한 본사목록 조회 */
    List<DefaultMap<String>> authHqList(StoreManageVO storeManageVO);

    /** 권한그룹복사를 위한 매장목록 조회 */
    List<DefaultMap<String>> authStoreList(StoreManageVO storeManageVO);

    /** 사용메뉴 조회 */
    List<DefaultMap<String>> avlblMenu(StoreManageVO storeManageVO);

    /** 미사용 메뉴 조회 */
    List<DefaultMap<String>> beUseMenu(StoreManageVO storeManageVO);

    /** 메뉴권한복사 */
    int copyAuth(StoreMenuVO storeMenuVO);

    /** 권한예외 복사 시, 복사기준본사의 권한예외 값 조회  */
    List<DefaultMap<String>> exceptMenu(StoreMenuVO storeMenuVO);

    /** 권한예외 복사 */
    int copyAuthExcp(StoreMenuVO storeMenuVO);

    /** 권한확인 */
    int isAuth(StoreMenuVO storeMenus);

    /** 메뉴권한 추가*/
    int addAuth(StoreMenuVO storeMenus);

    /** 권한 삭제 */
    int removeAuth(StoreMenuVO storeMenus);

    /** 권한 전체 삭제 */
    int removeAuthAll(StoreMenuVO storeMenus);

    /** 사업자번호 중복체크 */
    DefaultMap<String> bizNoCheckCount(StoreManageVO storeManageVO);

    /** 본사 세트구성상품 복사  */
    int insertStoreHqSetConfigProd(StoreManageVO storeManageVO);

    /** 본사 상태구분 값 조회 */
    String getHqSysStatFg(StoreManageVO storeManageVO);

    /** 매장코드 8 자리 이상 사용하는 본사인지 조회 */
    String getUseDigit8Store(StoreManageVO storeManageVO);

    /** 웹사용자아이디 중복체크*/
    int getUserIdCnt(StoreManageVO storeManageVO);

    /** ERP를 연동하는 본사인지 확인 */
    String getErpLinkHq(StoreManageVO storeManageVO);

    /** ERP 연동 매장 조회 */
    List<DefaultMap<String>> getErpStore(StoreManageVO storeManageVO);

    /** ERP 연동 매장에 NXPOS_STORE_CD 값 Update */
    int updateErpStore(StoreManageVO storeManageVO);

    /** 선택한 ERP 연동 매장이 미등록 매장이 맞는지 확인 */
    int getErpStoreUnRegConfm(StoreManageVO storeManageVO);

    /** 매장포스목록 조회 */
    List<DefaultMap<String>> getEnvPosList(StoreManageVO storeManageVO);

    /** 매장포스 중 메인포스로 사용할 포스 조회 */
    String getUseMainPos(StoreManageVO storeManageVO);

    /** 매장포스 환경설정값 변경 */
    int updatePosEnvVal(StorePosEnvVO storePosEnvVO);

    /** 매장의 환경설정값 조회 */
    String getStoreEnvVal(StoreManageVO storeManageVO);

    /** 재료 및 원산지 정보 복사  */
    int insertStoreHqProductRecpOrigin(StoreManageVO storeManageVO);

    /** 상품-원산지 매핑정보 복사  */
    int insertStoreHqProductRecpProd(StoreManageVO storeManageVO);

    /** 재료 및 알레르기 정보 복사  */
    int insertStoreHqProductAlgiInfo(StoreManageVO storeManageVO);

    /** 상품-알레르기 매핑정보 복사  */
    int insertStoreHqProductAlgiProd(StoreManageVO storeManageVO);

    /** 판매터치키 메뉴 임시테이블 삭제 */
    int deleteAllTmpTouchKeyClass(StoreEnvVO storeEnvVO);

    /** 판매터치키 임시테이블 삭제 */
    int deleteAllTmpTouchKey(StoreEnvVO storeEnvVO);

    /** 임시테이블에 매장 판매터치키 분류 입력 */
    int insertTmpStoreTouchKeyClass(StoreEnvVO storeEnvVO);

    /** 임시테이블에 매장 판매터치키 입력*/
    int insertTmpStoreTouchKey(StoreEnvVO storeEnvVO);

    /** 매장 판매터치키 분류 기존정보 삭제 */
    int deleteOrgStoreTouchKeyClass(StoreEnvVO storeEnvVO);

    /** 매장 판매터치키 기존정보 삭제 */
    int deleteOrgStoreTouchKey(StoreEnvVO storeEnvVO);

    /** 매장 판매터치키 분류 재정렬*/
    int chgSortStoreTouchKeyClass(StoreEnvVO storeEnvVO);

    /** 매장 판매터치키 재정렬 (01: 셀 사이즈)*/
    int chgSortStoreTouchKey01(StoreEnvVO storeEnvVO);

    /** 매장 판매터치키 재정렬 (02: 상품명)*/
    int chgSortStoreTouchKey02(StoreEnvVO storeEnvVO);

    /** 매장 판매터치키 재정렬 (03: 가격)*/
    int chgSortStoreTouchKey03(StoreEnvVO storeEnvVO);

    /** 본사 환경변수 조회 */
    String getHqEnvst(MemberClassVO memberClassVO);

    /** 회원 기본등급 등록(프랜차이즈 매장용) */
    int insertStoreMemberClass(MemberClassVO memberClassVO);

    /** 특이매장 데이터 처리 */
    String unusualStoreRegInfo(StoreManageVO storeManageVO);

    /** 본사-지사 조회(콤보박스용) */
    List<DefaultMap<String>> getBranchCombo(StoreManageVO storeManageVO);

    /** 코드별 본사 공통코드 콤보박스 조회 */
    List<DefaultMap<Object>> getHqNmcodeComboList(StoreManageVO storeManageVO);

    /** 매장정보 저장 (맘스터치 추가정보) */
    int mergeStoreInfoAddMoms(StoreManageVO storeManageVO);

    /** 브랜드 콤보박스 조회 */
    List<DefaultMap<Object>> getHqBrandCdComboList(StoreManageVO storeManageVO);
}