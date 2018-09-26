package kr.co.solbipos.membr.anals.credit.service;


import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.regist.service.CreditStoreVO;
import kr.co.solbipos.membr.info.regist.service.RegistVO;

import java.util.List;

/**
 * @Class Name : CreditService.java
 * @Description : 회원관리 > 회원분석 > 후불회원
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.20  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.09.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface CreditService {

    /** 후불 회원 외상, 입금 내역 */
    List<DefaultMap<Object>> getCreditMemberList(CreditStoreVO creditStoreVO, SessionInfoVO sessionInfoVO);


    //    /**
//     * 등록 매장 리스트 조회
//     *
//     * @return
//     */
//    List<DefaultMap<String>> selectRgstrStore(SessionInfoVO sessionInfoVO);

}
