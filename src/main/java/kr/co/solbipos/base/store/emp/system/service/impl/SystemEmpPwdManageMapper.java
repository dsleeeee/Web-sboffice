package kr.co.solbipos.base.store.emp.system.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.emp.system.service.SystemEmpPwdManageVO;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SystemEmpPwdManageMapper.java
 * @Description : 시스템관리 > 사원관리 > 비밀번호 임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.12  김지은      최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 12.12
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SystemEmpPwdManageMapper {

    /** 비밀번호 임의변경 대상 조회 */
    List<DefaultMap<String>> getPwdManageList(SystemEmpPwdManageVO systemEmpPwdManageVO);

    /** 기존 비밀번호 조회 */
    String getOldPassword(SystemEmpPwdManageVO systemEmpPwdManageVO);

    /** 비밀번호 변경 */
    int updatePassword(SystemEmpPwdManageVO systemEmpPwdManageVO);

    /** 비밀번호 변경이력 저장 */
    int insertPasswordHistory(SystemEmpPwdManageVO systemEmpPwdManageVO);
}
