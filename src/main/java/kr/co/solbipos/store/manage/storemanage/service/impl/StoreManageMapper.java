package kr.co.solbipos.store.manage.storemanage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import kr.co.solbipos.store.hq.brand.service.HqBrandVO;
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

    /** 매장 상세 조회 */
    DefaultMap<String> getStoreDetail(StoreManageVO storeManageVO);

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

    /** 포스 출력물 마스터 등록 (단독) */
    int insertDefaultPrintTemplete(StoreManageVO storeManageVO);

    /** 포스 출력물 마스터 등록(프랜차이즈) */
    int insertHqPrintTemplete(StoreManageVO storeManageVO);

    /** 포스 마스터 등록*/
    int insertPosInfo(StoreManageVO storeManageVO);

    /** 매장 기본 코너 등록 */
    int insertStoreCorner(StoreCornerVO storeCornerVO);

    /** 회원 기본등급 등록 */
    int insertMemberClass(MemberClassVO memberClassVO);

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

    /** 판매가 복사 */
    int copySaleUprc(StoreManageVO storeManageVO);

    /** 공급가 복사  */
    int updateSplyUprc(StoreManageVO storeManageVO);

    /** 매장설정 XML정보 복사 */
    int copyConfXml(ConfgXmlVO confgXmlVO);

    /** 터치키 분류 복사 TODO */
    int copyFnkeyClassCopy(TouchKeyClassVO touchkeyClassVO);

    /** 터치키 복사 TODO */
    int copyFnkeyCopy(TouchKeyVO touchkeyVO);

    /** 매장환경 정보 조회 */
    List<DefaultMap<String>> getEnvGroupList(StoreEnvVO storeEnvVO);

    /** 매장환경 정보 저장 */
    int insertStoreConfig(StoreEnvVO storeEnvVO);

    /** 매장환경 정보 수정 */
    int updateStoreConfig(StoreEnvVO storeEnvVO);

    /** 매장 포스 환경 정보 조회 */
    List<DefaultMap<String>> getPosEnvGroupList(StorePosEnvVO storePosEnvVOs);

    /** 매장 포스 환경 정보 저장 */
    int insertPosConfig(StorePosEnvVO storePosEnvVO);

    /** 매장 포스 환경 정보 수정 */
    int updatePosConfig(StorePosEnvVO storePosEnvVO);

    /** 포스가 메인서버로 변경되면, 나머지 포스는 서브포스로 변경 TODO */
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

    /** 포스 복사 타겟포스의 데이터 삭제 */
    int deletePosTarget(StorePosEnvVO storePosEnvVO);

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

    ///////////////////////////////////////////////////////매장조회
    /** 벤사, 코너 정보 조회 */
    List<DefaultMap<String>> getVanCornrList(StoreManageVO storeManageVO);

    /** 설치 포스수 조회 */
    int getInstPosCnt(StoreManageVO storeManageVO);



    //    /** 코너별 승인 목록 조회 */
//    List<DefaultMap<String>> getCornrApproveList(StoreManageVO storeManageVO);

//    /** 포스별 승인 목록 조회*/
//    List<DefaultMap<String>> getPosApproveList(StoreManageVO storeManageVO);


}

