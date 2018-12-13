package kr.co.solbipos.base.store.emp.system.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;

import java.util.List;

/**
 * @Class Name : SystemEmpService.java
 * @Description : 시스템관리 > 사원관리 > 사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.27  김지은      최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 11.27
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SystemEmpService {

    /** 사원정보 리스트 조회*/
    List<DefaultMap<String>> getSystemEmpList(SystemEmpVO systemEmpVO, SessionInfoVO sessionInfoVO);

    /** 사원정보 상세*/
    DefaultMap<String> getSystemEmpDtlInfo(SystemEmpVO systemEmpVO, SessionInfoVO sessionInfoVO);

    /** 웹유저아이디 조회*/
    EmpResult getSystemUserIdCnt(SystemEmpVO systemEmpVO);

    /** 사원정보 등록*/
    EmpResult insertSystemEmpInfo(SystemEmpVO systemEmpVO, SessionInfoVO sessionInfoVO);

    /** 사원정보 수정*/
    EmpResult saveSystemEmpInfo(SystemEmpVO systemEmpVO, SessionInfoVO sessionInfoVO);

    /** 사원번호 패스워드변경*/
    EmpResult modifyPassword(SystemEmpVO systemEmpVO, SessionInfoVO sessionInfoVO);


}
