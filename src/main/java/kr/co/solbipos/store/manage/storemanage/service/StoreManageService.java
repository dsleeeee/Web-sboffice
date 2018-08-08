package kr.co.solbipos.store.manage.storemanage.service;

import java.util.List;
import java.util.Map;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.brand.service.HqBrandVO;

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
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreManageService {

    /** 매장 목록 조회 */
    List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO);

    /** 매장정보 상세조회 */
    Map<String, Object> getStoreDetail(StoreManageVO storeManageVO);

    /** 매장 콤보 리스트 조회 */
    List<DefaultMap<String>> getStoreComboList(StoreManageVO storeManageVO);

    /** 매장환경조회 팝업 데이터 조회 */
    Map<String, Object> getStoreEnvInfo(StoreManageVO storeManageVO);

    /** 매장 신규등록 */
    int saveStoreInfo(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 매장 정보 수정 */
    int updateStoreInfo(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

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

    /** 포스그룹설정 selectBox 용 그룹목록 조회 */
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

    /** 터치키 복사할 브랜드 목록 조회 */
    List<DefaultMap<String>> getHqBrandList(HqBrandVO hqBrandVO);

    /** 터치키 복사할 매장 목록 조회 */
    List<DefaultMap<String>> getTouchKeyStoreList(HqBrandVO hqBrandVO);

}
