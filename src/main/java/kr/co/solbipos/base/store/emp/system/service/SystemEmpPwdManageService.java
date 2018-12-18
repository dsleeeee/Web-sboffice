package kr.co.solbipos.base.store.emp.system.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageVO;

import java.util.List;

/**
 * @Class Name : SystemEmpPwdManageService.java
 * @Description : 시스템관리 > 사원관리 > 비밀번호 임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.27  김지은      최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 12.12
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SystemEmpPwdManageService {

    /** 비밀번호 임의변경 대상 조회 */
    List<DefaultMap<String>> getPwdManageList(SystemEmpPwdManageVO systemEmpPwdManageVO, SessionInfoVO sessionInfoVO);

    /** 비밀번호 변경 */
    PwChgResult modifyPwd(SystemEmpPwdManageVO systemEmpPwdManageVO);

}
