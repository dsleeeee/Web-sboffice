package kr.co.solbipos.base.store.emp.store.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.store.service.enums.StoreEmpResult;

import java.util.List;

/**
 * @Class Name : StoreEmpService.java
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
public interface StoreEmpService
{
    /** 매장 사원 목록 조회 */
    List<DefaultMap<String>> getStoreEmpList(StoreEmpVO storeEmpVO, SessionInfoVO sessionInfoVO);

    /** 본사 사원정보 상세*/
    DefaultMap<String> getStoreEmpDtlInfo(StoreEmpVO storeEmpVO, SessionInfoVO sessionInfoVO);

    /** 본사 웹유저아이디 조회*/
    StoreEmpResult getStoreUserIdCnt(StoreEmpVO storeEmpVO);

    /** 본사 사원정보 등록*/
    StoreEmpResult insertStoreEmpInfo(StoreEmpVO storeEmpVO, SessionInfoVO sessionInfoVO);

    /** 본사 사원정보 수정*/
    StoreEmpResult saveStoreEmpInfo(StoreEmpVO storeEmpVO, SessionInfoVO sessionInfoVO);

//    /** 본사 사원번호 패스워드변경*/
//    StoreEmpResult modifyPassword(StoreEmpVO storeEmpVO, SessionInfoVO sessionInfoVO);

}
