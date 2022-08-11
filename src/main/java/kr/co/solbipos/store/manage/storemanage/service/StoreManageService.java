package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : StoreManageService.java
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
public interface StoreManageService {

    /** 매장 목록 조회 */
    List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 매장 목록 엑셀조회 */
    List<DefaultMap<String>> getStoreExcelList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 매장정보 상세조회 */
    Map<String, Object> getStoreDetail(StoreManageVO storeManageVO);

    /** 매장 콤보 리스트 조회 */
    List<DefaultMap<String>> getStoreComboList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 매장환경조회 팝업 데이터 조회 */
    Map<String, Object> getStoreEnvInfo(StoreManageVO storeManageVO);

    /** 매장 신규등록 */
    String saveStoreInfo(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 매장 정보 수정 */
    int updateStoreInfo(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 매장 포스승인정보 저장 */
    int saveStorePosVanInfo(StorePosVO[] storePosVOs, SessionInfoVO sessionInfoVO);

    /** 매장환경 정보 조회 */
    List<DefaultMap<String>> getEnvGroupList(StoreEnvVO storeEnvVO);

    /** 매장환경 정보 저장 */
    int saveStoreConfig(StoreEnvVO[] storeEnvVOs, SessionInfoVO sessionInfoVO);

    /** 매장 포스 환경정보 조회 */
    List<DefaultMap<String>> getPosEnvGroupList(StorePosEnvVO storePosEnvVOs);

    /** 매장 포스 환경정보 저장 */
    int savePosConfig(StorePosEnvVO[] storePosEnvVOs, SessionInfoVO sessionInfoVO);

    /** 포스 목록 조회 */
    List<DefaultMap<String>> getPosList(StorePosEnvVO storePosEnvVO);

    /** 테이블 그룹설정 (selectBox 용) */
    List<DefaultMap<String>> getGroupList(StorePosEnvVO storePosEnvVO);

    /** 테이블 그룹설정정보 저장 */
    int savePosTabGrp(StorePosEnvVO[] storePosEnvVOs, SessionInfoVO sessionInfoVO);

    /** 테이블 명칭설정정보 저장 */
    int savePosTabNm(StorePosEnvVO[] storePosEnvVOs, SessionInfoVO sessionInfoVO);

    /** 포스 셋팅 복사 */
    int copyPosSetting(StorePosEnvVO storePosEnvVO, SessionInfoVO sessionInfoVO);

    /** 포스 삭제 */
    int deletePos(StorePosEnvVO storePosEnvVO, SessionInfoVO sessionInfoVO);

    /** 주방프린터 목록 조회 */
    List<DefaultMap<String>> getKitchenPrintInfo(StoreEnvVO storeEnvVO);

    /** 주방프린터 목록 저장 */
    int saveKitchenPrintInfo(KitchenPrintVO[] kitchenPrintVOs, SessionInfoVO sessionInfoVO);

    /** 주방프린터 출력상품 목록 조회 */
    List<StoreProductVO> getPrintProductInfo(StoreProductVO storeProductVO, UseYn useYn);

    /** 주방프린터 출력상품 목록 저장 */
    int saveKitchenPrintProduct(StoreProductVO[] storeProductVOs, SessionInfoVO sessionInfoVO);

    /** 터치키 복사할 본사 목록 조회 */
    List<DefaultMap<String>> getHqList();

//    /** 터치키 복사할 브랜드 목록 조회 */
//    List<DefaultMap<String>> getHqBrandList(HqBrandVO hqBrandVO);

    /** 터치키 복사할 매장 목록 조회 */
    List<DefaultMap<String>> getTouchKeyStoreList(HqManageVO hqManageVO);

    /** 설치포스 수 추가 */
    int savePosCnt(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 매장코드 중복체크 */
    int getStoreCdCnt(StoreManageVO storeManageVO);

    /** 권한그룹복사를 위한 본사목록 조회 */
    List<DefaultMap<String>> authHqList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 권한그룹복사를 위한 매장목록 조회 */
    List<DefaultMap<String>> authStoreList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 사용 메뉴 */
    List<DefaultMap<String>> avlblMenu(StoreManageVO storeManageVO);

    /** 미사용 메뉴 */
    List<DefaultMap<String>> beUseMenu(StoreManageVO storeManageVO);

    /** 메뉴권한복사 */
    int copyAuth(StoreMenuVO storeMenuVO, SessionInfoVO sessionInfoVO);

    /** 메뉴 권한 추가 */
    int addAuth(StoreMenuVO[] storeMenus, SessionInfoVO sessionInfoVO);

    /** 메뉴 권한 삭제 */
    int removeAuth(StoreMenuVO[] storeMenus, SessionInfoVO sessionInfoVO);

    /** 사업자번호 중복체크 */
    DefaultMap<String> bizNoCheckCount(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 본사 상태구분 값 조회 */
    String getHqSysStatFg(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 매장코드 8 자리 이상 사용하는 본사인지 조회 */
    String getUseDigit8Store(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 웹 사용자 아이디 체크*/
    EmpResult chkUserId(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** ERP를 연동하는 본사인지 확인 */
    String getErpLinkHq(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** ERP 연동 매장 조회 */
    List<DefaultMap<String>> getErpStore(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 매장 메인포스 제외, 나머지 포스는 서브포스로 변경 */
    int updateToSubPos(StorePosEnvVO storePosEnvVO, SessionInfoVO sessionInfoVO);

    /** 매장포스목록 조회 */
    List<DefaultMap<String>> getEnvPosList(StoreManageVO storeManageVO);

    /** 매장포스 중 메인포스로 사용할 포스 조회 */
    String getUseMainPos(StoreManageVO storeManageVO);

    /** 매장포스 환경설정값 변경 */
    int updatePosEnvVal(StorePosEnvVO storePosEnvVO, SessionInfoVO sessionInfoVO);

    /** 매장의 환경설정값 조회 */
    String getStoreEnvVal(StoreManageVO storeManageVO);


}
