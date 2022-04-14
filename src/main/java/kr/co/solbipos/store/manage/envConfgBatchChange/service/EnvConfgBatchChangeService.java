package kr.co.solbipos.store.manage.envConfgBatchChange.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : EnvConfgBatchChangeService.java
 * @Description : 기초관리 > 매장정보관리 > 환경변수일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.02.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.02.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface EnvConfgBatchChangeService {

    /** 본사환경탭 - 조회 */
    List<DefaultMap<Object>> getEnvConfgBatchChangeHqList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 환경설정 조회 팝업 - 조회 */
    List<DefaultMap<Object>> getSearchEnvConfgList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 본사환경탭 - 환경변수값 콤보박스 조회 */
    List<DefaultMap<Object>> getEnvstValComboList(EnvConfgBatchChangeVO EnvConfgBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 본사환경탭 - 저장 */
    int getEnvConfgBatchChangeHqSave(EnvConfgBatchChangeVO[] EnvConfgBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 매장환경탭 - 조회 */
    List<DefaultMap<Object>> getEnvConfgBatchChangeStoreList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 매장환경탭 - 저장 */
    int getEnvConfgBatchChangeStoreSave(EnvConfgBatchChangeVO[] EnvConfgBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 매장포스환경탭 - 조회 */
    List<DefaultMap<Object>> getEnvConfgBatchChangeStorePosList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 매장포스환경탭 - 저장 */
    int getEnvConfgBatchChangeStorePosSave(EnvConfgBatchChangeVO[] EnvConfgBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 기능키 조회 팝업 - 조회 */
    List<DefaultMap<Object>> getSearchFnkeyList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 기능키명칭탭 - 조회 */
    List<DefaultMap<Object>> getEnvConfgBatchChangeFnkeyList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 기능키명칭탭 - 저장 */
    int getEnvConfgBatchChangeFnkeySave(EnvConfgBatchChangeVO[] EnvConfgBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 환경설정관리 - 저장 */
    int getEnvConfgBatchChangeEnvSettingSave(EnvConfgBatchChangeVO[] envConfgBatchChangeVOs, SessionInfoVO sessionInfoVO);

}