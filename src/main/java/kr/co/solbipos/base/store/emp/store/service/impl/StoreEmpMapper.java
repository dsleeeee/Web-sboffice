package kr.co.solbipos.base.store.emp.store.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpMenuVO;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreEmpServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 매장사원관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.16  hblee      최초생성
 * @ 2018.11.23  김지은     angular 방식으로 변경 및 로직 수정(타 페이지와 통일성 맞춤)
 *
 * @author NHN한국사이버결제 이한빈
 * @since 2018.08.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreEmpMapper {

    /** 매장 사원정보 리스트 조회*/
    List<DefaultMap<String>> getStoreEmpList(StoreEmpVO storeEmpVO);

    /** 매장 웹유저아이디 조회*/
    int getStoreUserIdCnt(StoreEmpVO storeEmpVO);

    /** 매장 신규사원번호 조회 */
    String getStoreEmpNo(StoreEmpVO storeEmpVO);

    /** 매장 사원정보 등록*/
    int insertStoreEmpInfo(StoreEmpVO storeEmpVO);

    /** 웹 로그인 정보 등록*/
    int insertWbUserInfo(StoreEmpVO storeEmpVO);

    /** 매장 사원정보 수정*/
    int updateStoreEmpInfo(StoreEmpVO storeEmpVO);

    /** 웹 로그인 정보 수정*/
    int saveWbUserInfo(StoreEmpVO storeEmpVO);

    /** 매장 사원정보 상세 */
    DefaultMap<String> getStoreEmpDtlInfo(StoreEmpVO storeEmpVO);

    /** 현재 패스워드 조회 */
    String getStoreEmpPassword(StoreEmpVO storeEmpVO);

    /** 패스워드 변경 */
    int updateUserPassword(StoreEmpVO storeEmpVO);

    /** 패스워드 변경 히스토리 등록*/
    int insertPasswordHistory(StoreEmpVO storeEmpVO);

    /** 권한복사를 위한 매장 사원 리스트 조회 */
    List<DefaultMap<String>> authStoreEmpList(StoreEmpVO storeEmpVO);

    /** 사용메뉴 조회 */
    List<DefaultMap<String>> avlblMenu(StoreEmpVO storeEmpVO);

    /** 미사용메뉴 조회 */
    List<DefaultMap<String>> beUseMenu(StoreEmpVO storeEmpVO);

    /** 메뉴권한복사 */
    int copyAuth(StoreEmpMenuVO storeEmpMenuVO);

    /** 권한예외 복사 시, 복사기준본사의 권한예외 값 조회  */
    List<DefaultMap<String>> exceptMenu(StoreEmpMenuVO storeEmpMenuVO);

    /** 권한예외 복사 */
    int copyAuthExcp(StoreEmpMenuVO storeEmpMenuVO);

    /** 권한확인 */
    int isAuth(StoreEmpMenuVO storeEmpMenus);

    /** 메뉴권한 추가*/
    int addAuth(StoreEmpMenuVO storeEmpMenus);

    /** 권한 삭제 */
    int removeAuth(StoreEmpMenuVO storeEmpMenus);

    /** 권한 전체 삭제 */
    int removeAuthAll(StoreEmpMenuVO storeEmpMenus);

}
