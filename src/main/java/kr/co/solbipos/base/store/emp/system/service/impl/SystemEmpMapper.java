package kr.co.solbipos.base.store.emp.system.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.emp.system.service.SystemEmpVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SystemEmpMapper.java
 * @Description : 시스템관리 > 사원관리 > 사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.26  김지은      최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 11.26
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SystemEmpMapper {

    /** 본사 사원정보 리스트 조회*/
    List<DefaultMap<String>> getSystemEmpList(SystemEmpVO systemEmpVO);

    /** 본사 웹유저아이디 조회*/
    int getSystemUserIdCnt(SystemEmpVO systemEmpVO);

    /** 신규 사원번호 조회 */
    String getSystemEmpNo();

    /** 본사 사원정보 등록*/
    int insertSystemEmpInfo(SystemEmpVO systemEmpVO);

    /** 웹 로그인 정보 등록*/
    int insertWbUserInfo(SystemEmpVO systemEmpVO);

    /** 본사 사원정보 수정*/
    int updateSystemEmpInfo(SystemEmpVO systemEmpVO);

    /** 웹 로그인 정보 수정*/
    int saveWbUserInfo(SystemEmpVO systemEmpVO);

    /** 패스워드 변경 히스토리 등록*/
    int insertPasswordHistory(SystemEmpVO systemEmpVO);

    /** 패스워드 변경 */
    int updateUserPassword(SystemEmpVO systemEmpVO);

    /** 본사 사원정보 상세 */
    DefaultMap<String> getSystemEmpDtlInfo(SystemEmpVO systemEmpVO);

    /** 현재 패스워드 조회 */
    String getSystemEmpPassword(SystemEmpVO systemEmpVO);

}
