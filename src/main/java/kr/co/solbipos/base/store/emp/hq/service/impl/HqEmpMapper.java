package kr.co.solbipos.base.store.emp.hq.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpMenuVO;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : HqEmpMapper.java
 * @Description : 기초관리 > 사원관리 > 사원정보관리(이전 : 기초관리 > 매장관리 > 본사사원정보관리)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  정상화      최초생성
 *
 * @author NHN한국사이버결제 KCP 정상화
 * @since 2018. 08.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface HqEmpMapper {

    /** 본사 사원정보 리스트 조회*/
    List<DefaultMap<String>> getHqEmpList(HqEmpVO hqEmpVO);

    /** 본사 웹유저아이디 조회*/
    int getHqUserIdCnt(HqEmpVO hqEmpVO);

    /** 신규 사원번호 조회 */
    String getHqEmpNo(HqEmpVO hqEmpVO);

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
    DefaultMap<String> getHqEmpDtlInfo(HqEmpVO hqEmpVO);

    /** 현재 패스워드 조회 */
    String getHqEmpPassword(HqEmpVO hqEmpVO);

    /** 권한복사를 위한 본사 사원 리스트 조회 */
    List<DefaultMap<String>> authHqEmpList(HqEmpVO hqEmpVO);

    /** 사용메뉴 조회 */
    List<DefaultMap<String>> avlblMenu(HqEmpVO hqEmpVO);

    /** 미사용메뉴 조회 */
    List<DefaultMap<String>> beUseMenu(HqEmpVO hqEmpVO);

    /** 메뉴권한복사 */
    int copyAuth(HqEmpMenuVO hqEmpMenuVO);

    /** 권한예외 복사 시, 복사기준본사의 권한예외 값 조회  */
    List<DefaultMap<String>> exceptMenu(HqEmpMenuVO hqEmpMenuVO);

    /** 권한예외 복사 */
    int copyAuthExcp(HqEmpMenuVO hqEmpMenuVO);

    /** 권한확인 */
    int isAuth(HqEmpMenuVO hqEmpMenus);

    /** 메뉴권한 추가*/
    int addAuth(HqEmpMenuVO hqEmpMenus);

    /** 권한 삭제 */
    int removeAuth(HqEmpMenuVO hqEmpMenus);

    /** 권한 전체 삭제 */
    int removeAuthAll(HqEmpMenuVO hqEmpMenus);

}
