package kr.co.solbipos.adi.sms.smsChargeHist.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SmsChargeHistService.java
 * @Description : 부가서비스 > SMS관리 > SMS충전내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SmsChargeHistService {

    /** SMS충전내역 - 조회 */
    List<DefaultMap<Object>> getSmsChargeHistList(SmsChargeHistVO smsChargeHistVO, SessionInfoVO sessionInfoVO);

    /** SMS충전내역 - 엑셀 조회 */
    List<DefaultMap<Object>> getSmsChargeHistExcelList(SmsChargeHistVO smsChargeHistVO, SessionInfoVO sessionInfoVO);

    /** SMS임의충전 팝업 - 조회 */
    List<DefaultMap<Object>> getSmsChargeRegistList(SmsChargeHistVO smsChargeHistVO, SessionInfoVO sessionInfoVO);

    /** SMS임의충전 팝업 - 저장 */
    int getSmsChargeRegistSave(SmsChargeHistVO smsChargeHistVO, SessionInfoVO sessionInfoVO);
}