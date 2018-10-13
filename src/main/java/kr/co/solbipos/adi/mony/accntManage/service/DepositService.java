package kr.co.solbipos.adi.mony.accntManage.service;


import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.mony.accntManage.service.impl.AccntVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DepositService.java
 * @Description : 부가서비스 > 금전처리 > 계정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.12  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.12
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DepositService {

    /** 계정 조회 */
    List<DefaultMap<String>> getDepositAccntList(AccntVO accntVO, SessionInfoVO sessionInfoVO);
}
