package kr.co.solbipos.base.store.empStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : EmpStoreService.java
 * @Description : 기초관리 > 매장관리 > 사원별매장관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.05.12  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.05.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface EmpStoreService {

    /** 사원별탭 - 사원정보 조회 */
    List<DefaultMap<Object>> getEmpStoreEmpList(EmpStoreVO empStoreVO, SessionInfoVO sessionInfoVO);

    /** 사원별탭 - 관리매장 조회 */
    List<DefaultMap<Object>> getEmpManageStoreList(EmpStoreVO empStoreVO, SessionInfoVO sessionInfoVO);

    /** 사원별탭 - 미관리매장 조회 */
    List<DefaultMap<Object>> getEmpNoManageStoreList(EmpStoreVO empStoreVO, SessionInfoVO sessionInfoVO);

    /** 사원별탭 - 관리매장 추가 */
    int getEmpManageStoreSave(EmpStoreVO[] empStoreVOs, SessionInfoVO sessionInfoVO);

    /** 사원별탭 - 관리매장 삭제 */
    int getEmpManageStoreDelete(EmpStoreVO[] empStoreVOs, SessionInfoVO sessionInfoVO);

    /** 매장별탭 - 매장정보 조회 */
    List<DefaultMap<Object>> getEmpStoreStoreList(EmpStoreVO empStoreVO, SessionInfoVO sessionInfoVO);

    /** 매장별탭 - 관리사원 조회 */
    List<DefaultMap<Object>> getStoreManageEmpList(EmpStoreVO empStoreVO, SessionInfoVO sessionInfoVO);

    /** 매장별탭 - 미관리사원 조회 */
    List<DefaultMap<Object>> getStoreNoManageEmpList(EmpStoreVO empStoreVO, SessionInfoVO sessionInfoVO);

    /** 매장별탭 - 관리사원 추가 */
    int getStoreManageEmpSave(EmpStoreVO[] empStoreVOs, SessionInfoVO sessionInfoVO);

    /** 매장별탭 - 관리사원 삭제 */
    int getStoreManageEmpDelete(EmpStoreVO[] empStoreVOs, SessionInfoVO sessionInfoVO);
}