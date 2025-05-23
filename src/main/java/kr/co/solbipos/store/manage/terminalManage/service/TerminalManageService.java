package kr.co.solbipos.store.manage.terminalManage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;
import kr.co.solbipos.store.manage.storemanage.service.StorePosVO;

import java.util.List;

/**
 * @Class Name : TerminalManageService.java
 * @Description : 가맹점관리 > 매장관리 > 매장터미널관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018. 10.06  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.06
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TerminalManageService {

    /** 벤더 목록 */
    List<DefaultMap<String>> getVendorList();

    /** 매장 조회 */
    List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 포스/터미널 설정 환경변수 조회 */
    String getTerminalEnv(StoreEnvVO storeEnvVO);

    /** 포스 목록 조회 */
    List<DefaultMap<String>> getPosList(StorePosVO storePosVO);

    /** 코너 목록 조회 */
    List<DefaultMap<String>> getCornerList(StoreCornerVO storeCornerVO);

    /** 터미널 환경변수 값 저장 */
    int updateTerminalEnvst(StoreEnvVO storeEnvVO, SessionInfoVO sessionInfoVO);

    /** 포스 터미널 목록 조회 */
    List<DefaultMap<String>> getPosTerminalList(StoreTerminalVO storeTerminalVO, SessionInfoVO sessionInfoVO);

    /** 포스 터미널 정보 저장 */
    int savePosTerminalInfo(StoreTerminalVO[] storeTerminalVOs, SessionInfoVO sessionInfoVO);

    /** 코너 터미널 정보 조회 */
    List<DefaultMap<String>> getCornerTerminalList(StoreTerminalVO storeTerminalVO);

    /** 코너 터미널 정보 저장 */
    int saveCornerTerminalInfo(StoreTerminalVO[] storeTerminalVOs, SessionInfoVO sessionInfoVO);

    /** 코너 목록 조회 */
    List<DefaultMap<String>> getCornerDtlList(StoreCornerVO storeCornerVO);

    /** 코너 저장 */
    int insertCorner(StoreCornerVO[] storeCornerVOs, SessionInfoVO sessionInfoVO);

    /** 매장터미널관리 조회 */
    List<DefaultMap<Object>> getTerminalManageList(StoreTerminalVO storeTerminalVO, SessionInfoVO sessionInfoVO);

    /** 터미널 정보 복사 */
    int copyTerminalInfo(StoreTerminalVO storeTerminalVO, SessionInfoVO sessionInfoVO);

    /** 터미널 콤보박스(코너사용설정) 선택값에 따른 터미널 환경설정 저장 */
    int chgTerminalEnv(StoreEnvVO storeEnvVO, SessionInfoVO sessionInfoVO);

    List<DefaultMap<String>> getChkTerminalNull(StoreTerminalVO storeTerminalVO);
}
