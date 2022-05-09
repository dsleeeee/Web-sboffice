package kr.co.solbipos.base.store.view.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.storemanage.service.StorePosEnvVO;

import java.util.List;
import java.util.Map;

/**
* @Class Name : ViewService.java
* @Description : 기초관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.08.13  김영근      최초생성
* @ 2018.11.20  김지은      angular 방식으로 변경
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.13
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface ViewService {

    /** 매장정보 리스트조회 */
    List<DefaultMap<String>> getViewList(ViewVO viewVO, SessionInfoVO sessionInfoVO);

    /** 매장정보 리스트 엑셀 조회 */
    List<DefaultMap<String>> getStoreListExcel(ViewVO viewVO, SessionInfoVO sessionInfoVO);

    /** 매장정보 상세조회 */
    DefaultMap<String> getViewDetail(ViewVO viewVO);

    /** 코너 사용여부 조회 */
    String getCornerUseYnVal(VanConfigVO vanConfgVO);

    /** 포스별 승인 목록 조회 */
    List<DefaultMap<String>> getPosTerminalList(VanConfigVO vanConfigVO);

    /** 코너별 승인 목록 조회 */
    List<DefaultMap<String>> getCornerTerminalList(VanConfigVO vanConfigVO);

    /** 포스목록 조회 */
    List<DefaultMap<String>> getPosList(StorePosEnvVO storePosEnvVO);

    /** 매장환경 복사 */
    int copyStoreEnv(CopyStoreEnvVO[] copyStoreEnvVOs, Map<String, Object> posParam, SessionInfoVO sessionInfoVO);

    /** 매장환경 복사를 위한 정보 조회 */
    List<DefaultMap<String>> getStoreEnvInfoList(CopyStoreEnvVO copyStoreEnvVO, SessionInfoVO sessionInfoVO);

    /** 매장 리스트 조회 */
    List<DefaultMap<String>> getStoreList(ViewVO viewVO, SessionInfoVO sessionInfoVO);

    /** 매장 판매터치키 콤보박스 데이터 조회 */
    List<DefaultMap<String>>getStoreTouchKeyGrpCombo(ViewVO viewVO, SessionInfoVO sessionInfoVO);

    /** 매장 판매터치키 선택그룹 복사 */
    int copyStoreTouchKeyGrp(CopyStoreEnvVO[] copyStoreEnvVOs, SessionInfoVO sessionInfoVO);
}
