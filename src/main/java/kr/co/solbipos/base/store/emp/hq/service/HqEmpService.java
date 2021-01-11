package kr.co.solbipos.base.store.emp.hq.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;

import java.util.List;

/**
 * @Class Name : HqEmpService.java
 * @Description : 기초관리 > 사원관리 > 사원정보관리(이전 : 기초관리 > 매장관리 > 본사사원정보관리)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  정상화      최초생성
 * @ 2018.11.20  김지은      angular 방식으로 수정
 *
 * @author NHN한국사이버결제 KCP 정상화
 * @since 2018. 08.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface HqEmpService {

    /** 본사 사원정보 리스트 조회*/
    List<DefaultMap<String>> getHqEmpList(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO);

    /** 본사 사원정보 상세*/
    DefaultMap<String> getHqEmpDtlInfo(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO);

    /** 본사 웹유저아이디 조회*/
    EmpResult getHqUserIdCnt(HqEmpVO hqEmpVO);

    /** 본사 사원정보 등록*/
    EmpResult insertHqEmpInfo(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO);

    /** 본사 사원정보 수정*/
    EmpResult saveHqEmpInfo(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO);

    /** 본사 사원번호 패스워드변경*/
    EmpResult modifyPassword(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO);

    /** 권한복사를 위한 본사 사원 리스트 조회 */
    List<DefaultMap<String>> authHqEmpList(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO);

    /** 사용 메뉴 */
    List<DefaultMap<String>> avlblMenu(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO);

    /** 미사용 메뉴 */
    List<DefaultMap<String>> beUseMenu(HqEmpVO hqEmpVO, SessionInfoVO sessionInfoVO);

    /** 메뉴권한복사 */
    int copyAuth(HqEmpMenuVO hqEmpMenu, SessionInfoVO sessionInfoVO);

    /** 메뉴 권한 추가 */
    int addAuth(HqEmpMenuVO[] hqEmpMenu, SessionInfoVO sessionInfoVO);

    /** 메뉴 권한 삭제 */
    int removeAuth(HqEmpMenuVO[] hqEmpMenu, SessionInfoVO sessionInfoVO);

}
