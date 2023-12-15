package kr.co.solbipos.base.multilingual.fnkeyCmNmcd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : FnkeyCmNmcdService.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(기능키/공통코드)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.12  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.12.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface FnkeyCmNmcdService {

    /** 기능키(공통) 탭 리스트 조회 */
    List<DefaultMap<String>> getCmPosFnkeyList(FnkeyCmNmcdVO fnkeyCmNmcdVO, SessionInfoVO sessionInfoVO);

    /** 기능키(공통) 탭 영문, 중문, 일문 저장 */
    int saveCmPosFnkey(FnkeyCmNmcdVO[] fnkeyCmNmcdVOs,  SessionInfoVO sessionInfoVO);

    /** 기능키(매장) 탭 리스트 조회 */
    List<DefaultMap<String>> getStoreFnkeyList(FnkeyCmNmcdVO fnkeyCmNmcdVO, SessionInfoVO sessionInfoVO);

    /** 기능키(매장) 탭 영문, 중문, 일문 저장 */
    int saveStoreFnkey(FnkeyCmNmcdVO[] fnkeyCmNmcdVOs,  SessionInfoVO sessionInfoVO);

    /** 공통코드 탭 공통코드 그룹코드 조회(콤보박스용) */
    List<DefaultMap<String>> getNmcodeGrpCdList();

    /** 공통코드 탭 리스트 조회 */
    List<DefaultMap<String>> getCmNmcdList(FnkeyCmNmcdVO fnkeyCmNmcdVO, SessionInfoVO sessionInfoVO);

    /** 공통코드 탭 영문, 중문, 일문 저장 */
    int saveCmNmcd(FnkeyCmNmcdVO[] fnkeyCmNmcdVOs,  SessionInfoVO sessionInfoVO);

}
