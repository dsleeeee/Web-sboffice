package kr.co.solbipos.adi.sms.remainAmtChk.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name : RemainAmtChkService.java
 * @Description : 부가서비스 > SMS분석 > 잔여금액확인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface RemainAmtChkService {

    /** 조회 */
    List<DefaultMap<String>> getRemainAmtChkList(RemainAmtChkVO remainAmtChkVO, SessionInfoVO sessionInfoVO);

    /** 충전/사용내역 팝업 - 조회 */
    List<DefaultMap<String>> getRemainAmtHistList(RemainAmtChkVO remainAmtChkVO, SessionInfoVO sessionInfoVO);
}
