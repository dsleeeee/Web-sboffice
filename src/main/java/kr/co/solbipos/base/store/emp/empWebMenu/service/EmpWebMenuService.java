package kr.co.solbipos.base.store.emp.empWebMenu.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : EmpWebMenuService.java
 * @Description : 기초관리 > 사원관리 > 메뉴별권한복사
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.24  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.07.24
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface EmpWebMenuService {

    /** 메뉴목록 조회 */
    List<DefaultMap<String>> getMenuList(EmpWebMenuVO empWebMenuVO, SessionInfoVO sessionInfoVO);

    /** 사원목록 조회 */
    List<DefaultMap<String>> getUseEmp(EmpWebMenuVO empWebMenuVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> getUnuesdEmp(EmpWebMenuVO empWebMenuVO, SessionInfoVO sessionInfoVO);

    /** 사원정보 저장 */
    int getEmpWebMenuSave(EmpWebMenuVO[] empWebMenuVOs, SessionInfoVO sessionInfoVO);

}
