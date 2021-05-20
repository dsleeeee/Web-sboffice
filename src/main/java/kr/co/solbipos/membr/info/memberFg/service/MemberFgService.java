
package kr.co.solbipos.membr.info.memberFg.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MemberFgService.java
 * @Description : 회원관리 > 회원정보 > 선불/후불회원관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MemberFgService {

    /** 회원 등급 리스트 조회 */
    List<DefaultMap<String>> getMembrClassList(SessionInfoVO sessionInfoVO);

    /** 선불 회원 조회 */
    List<DefaultMap<String>> getMemberPrepaid(MemberFgVO memberFgVO, SessionInfoVO sessionInfoVO);
    /** 미선불 회원 조회 */
    List<DefaultMap<String>> getMemberNoPrepaid(MemberFgVO memberFgVO, SessionInfoVO sessionInfoVO);

    /** 후불 회원 조회 */
    List<DefaultMap<String>> getMemberPostpaid(MemberFgVO memberFgVO, SessionInfoVO sessionInfoVO);

    /** 미후불 회원 조회 */
    List<DefaultMap<String>> getMemberNoPostpaid(MemberFgVO memberFgVO, SessionInfoVO sessionInfoVO);

    /** 선불 회원 등록록 */
    int regPrepaid(MemberFgVO[] memberFgVOs, SessionInfoVO sessionInfoVO);

    /** 후후불 회 등록록 */
    int regPostpaid(MemberFgVO[] memberFgVOs, SessionInfoVO sessionInfoVO);

}
