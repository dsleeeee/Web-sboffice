package kr.co.solbipos.adi.sms.smsSend.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * @Class Name : SmsSendService.java
 * @Description : 부가서비스 > SMS관리 > SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SmsSendService {

    /** 발신번호 조회 */
    List<DefaultMap<Object>> getSmsTelNoComboList(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO);

    /** 관리자/총판/본사/매장 명칭 조회 */
    DefaultMap<Object> getStoreNmList(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO);

    /** 잔여수량 조회 */
    DefaultMap<Object> getSmsQtyList(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO);

    /** 전송,예약 저장 */
    int getSmsSendReserveSave(SmsSendVO[] smsSendVOs, SessionInfoVO sessionInfoVO);

    /** 첨부파일 저장 */
    String getSmsSendFileSave(MultipartHttpServletRequest multi, SessionInfoVO sessionInfoVO);

    /** 수신자추가 팝업 - 조회 */
    List<DefaultMap<Object>> getAddresseeAddList(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO);
}