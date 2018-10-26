package kr.co.solbipos.base.store.emp.store.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpVO;
import kr.co.solbipos.base.store.emp.store.service.WebUserVO;
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

    /** 매장사원목록 조회 */
    List<DefaultMap<String>> getStoreEmpList(StoreEmpVO storeEmpVO);

    /** 매장사원 조회 */
    DefaultMap<String> getStoreEmp(StoreEmpVO storeEmpVO);

    /** 매장사원 등록 */
    int insertStoreEmp(StoreEmpVO storeEmpVO);

    /** 매장사원 수정 */
    int updateStoreEmp(StoreEmpVO storeEmpVO);

    /** 기존 패스워드 조회 */
    String getCurrentPwd(StoreEmpVO storeEmpVO);

    /** 존재하는 웹 사용자 ID 조회 (중복체크) */
    String getExistUserId(String userId);

    /** 웹 사용자 패스워드 이력 등록 */
    int insertWebPwdChangeHistory(WebUserVO webUserVO);

    /** 웹 사용자 정보 등록/수정 */
    int saveWebUser(WebUserVO webUserVO);

    /** 웹 사용자 사용여부 수정 */
    int updateWebUserUseYn(WebUserVO webUserVO);
}
