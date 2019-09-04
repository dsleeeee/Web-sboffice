package kr.co.solbipos.membr.anals.dayMembr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
//import kr.co.solbipos.membr.anals.dayMembr.service.getDayMembrStoreVO;

import java.util.List;

/**
 * @Class Name : DayMembrService.java
 * @Description : 회원관리 > 회원분석 > 일자별회원 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.08.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DayMembrService {

    /** 일자별회원 구매내역*/
    List<DefaultMap<Object>> getDayMembrList(DayMembrVO dayMembrVO, SessionInfoVO sessionInfoVO);

    /** 매출정보 상세조회 - 팝업 */
    List <DefaultMap<Object>> getDayMembrPurchsList(DayMembrVO dayMembrVO, SessionInfoVO sessionInfoVO);
}
