package kr.co.solbipos.base.store.emp.hq.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class Name : HqEmpMapper.java
 * @Description : 기초관리 > 매장관리 > 본사사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  정상화      최초생성
 *
 * @author NHN한국사이버결제 KCP 정상화
 * @since 2018. 08.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
public interface HqEmpMapper {

    /** 본사 사원정보 리스트 조회*/
    <E>List<E> selectHqEmpList(HqEmpVO hqEmpVO);

    /** 본사 사원번호 조회*/
    int selectHqEmpNoCnt(HqEmpVO hqEmpVO);

    /** 본사 웹유저아이디 조회*/
    int selectHqUserIdCnt(HqEmpVO hqEmpVO);

    /** 본사 사원정보 등록*/
    int insertHqEmpInfo(HqEmpVO hqEmpVO);

    /** 웹 로그인 정보 등록*/
    int insertWbUserInfo(HqEmpVO hqEmpVO);

    /** 본사 사원정보 수정*/
    int updateHqEmpInfo(HqEmpVO hqEmpVO);

    /** 웹 로그인 정보 수정*/
    int saveWbUserInfo(HqEmpVO hqEmpVO);

    /** 패스워드 변경 히스토리 등록*/
    int insertPasswordHistory(HqEmpVO hqEmpVO);

    /** 패스워드 변경 */
    int updateUserPassword(HqEmpVO hqEmpVO);

    /** 본사 사원정보 상세 */
    DefaultMap<String> selectHqEmpDtlInfo(HqEmpVO hqEmpVO);

    /** 현재 패스워드 조회 */
    String selectHqEmpPassword(HqEmpVO hqEmpVO);
}
