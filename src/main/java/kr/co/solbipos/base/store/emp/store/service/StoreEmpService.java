package kr.co.solbipos.base.store.emp.store.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.store.service.enums.StoreEmpResult;

import java.util.List;

/**
 * @Class Name : StoreEmpService.java
 * @Description : 기초관리 > 매장관리 > 매장사원관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.16  hblee      최초생성
 *
 * @author NHN한국사이버결제 이한빈
 * @since 2018.08.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreEmpService
{
    /** 매장사원 목록 조회 */
    List<DefaultMap<String>> getStoreEmpList(StoreEmpVO storeEmpVO);

    /** 매장사원 조회 */
    DefaultMap<String> getStoreEmp(StoreEmpVO storeEmpVO);

    /** 매장사원 저장 */
    StoreEmpResult saveStoreEmp(StoreEmpVO storeEmpVO);

    /** 웹 사용자 정보 수정 */
    StoreEmpResult updateWebUser(StoreEmpVO storeEmpVO);

    /** 웹 사용자 정보 저장 */
    StoreEmpResult saveWebUser(StoreEmpVO storeEmpVO);

    /** 존재하는 웹 사용자 ID 조회 (중복체크) */
    boolean checkDuplicateUserId(String userId);

    /** 유효한 패스워드 암호화하여 리턴 */
    String getValidPwd(StoreEmpVO storeEmpVO);
}
