package kr.co.solbipos.store.manage.terminalManage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
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

    /** 매장 조회 */
    List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO);

    /** 포스/터미널 설정 환경변수 조회 */
    String getTerminalEnv(StoreEnvVO storeEnvVO);

    /** 포스 목록 조회 */
    List<DefaultMap<String>> getPosList(StorePosVO storePosVO);

    /** 터미널 환경변수 값 저장 */
    int updateTerminalEnvst(StoreEnvVO storeEnvVO, SessionInfoVO sessionInfoVO);

    /** 포스 van 정보 저장 */
    int savePosInfo(StorePosVO[] storePosVOs, SessionInfoVO sessionInfoVO);

    /** 코너 목록 조회 */
    List<DefaultMap<String>> getCornerList(StoreCornerVO storeCornerVO);

    /** 코너 정보 저장 */
    int saveCornerInfo(StoreCornerVO[] storeCornerVOs, SessionInfoVO sessionInfoVO);
}
