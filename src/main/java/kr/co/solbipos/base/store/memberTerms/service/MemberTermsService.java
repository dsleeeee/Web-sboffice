package kr.co.solbipos.base.store.memberTerms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MemberTermsService.java
 * @Description : 기초관리 > 매장관리 > 회원약관관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.07.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MemberTermsService {

    /** 회원약관관리 - 조회 */
    List<DefaultMap<Object>> getMemberTermsList(MemberTermsVO memberTermsVO, SessionInfoVO sessionInfoVO);
}