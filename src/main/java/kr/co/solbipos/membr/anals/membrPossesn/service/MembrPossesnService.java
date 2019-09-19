package kr.co.solbipos.membr.anals.membrPossesn.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MembrPossesnService.java
 * @Description : 회원관리 > 회원분석 > 일자별회원 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.09.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MembrPossesnService {

    /** 회원매출점유*/
    List<DefaultMap<Object>> getMembrPossesnList(MembrPossesnVO membrPossesnVO, SessionInfoVO sessionInfoVO);
}
