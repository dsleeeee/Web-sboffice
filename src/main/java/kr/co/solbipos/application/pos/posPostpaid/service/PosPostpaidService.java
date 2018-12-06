package kr.co.solbipos.application.pos.posPostpaid.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PosPostpaidService.java
 * @Description : POS 화면에서 세금계산서 발행 신청
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.03  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 12.03
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PosPostpaidService {

    /** 대상 회원목록 조회 */
    List<DefaultMap<String>> getMemberList(PosPostpaidStoreVO posPostpaidStoreVO, SessionInfoVO sessionInfoVO);

    /** 세금계산서 발행 요청 */
    int saveTaxBillRequet(PosPostpaidStoreVO posPostpaidStoreVO, SessionInfoVO sessionInfoVO);
}
