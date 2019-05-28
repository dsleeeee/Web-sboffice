package kr.co.solbipos.membr.anals.prepaidDtl.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.postpaidDtl.service.PostpaidDtlVO;

import java.util.List;

/**
 * @Class Name : PrepaidDtlService.java
 * @Description : 회원관리 > 회원분석 > 선불회원상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.05.27  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2019.05.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PrepaidDtlService {

    /** 선불 회원 충전, 사용 내역 상세 */
    List<DefaultMap<Object>> getPrepaidDtlMemberList(PrepaidDtlVO prepaidDtlVO, SessionInfoVO sessionInfoVO);
}
