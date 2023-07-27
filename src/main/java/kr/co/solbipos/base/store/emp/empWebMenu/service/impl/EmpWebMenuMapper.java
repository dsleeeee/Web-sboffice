package kr.co.solbipos.base.store.emp.empWebMenu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.emp.empWebMenu.service.EmpWebMenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : EmpWebMenuMapper.java
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

@Mapper
@Repository
public interface EmpWebMenuMapper {

    /** 메뉴목록 조회 */
    List<DefaultMap<String>> getMenuList(EmpWebMenuVO empWebMenuVO);

    /** 사용사원목록 조회 */
    List<DefaultMap<String>> getUseEmp(EmpWebMenuVO empWebMenuVO);

    /** 미사용사원목록 조회 */
    List<DefaultMap<String>> getUnuesdEmp(EmpWebMenuVO empWebMenuVO);

    /** 사원정보 저장 */
    int getEmpWebMenuSaveInsert(EmpWebMenuVO empWebMenuVO);
    int getEmpWebMenuSaveDelete(EmpWebMenuVO empWebMenuVO);

}