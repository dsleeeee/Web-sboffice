package kr.co.solbipos.adi.sms.smsSend.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SmsSendMapper.java
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
@Mapper
@Repository
public interface SmsSendMapper {

    /** 발신번호 유무 체크 */
    List<DefaultMap<Object>> getSmsTelNoComboList(SmsSendVO smsSendVO);

    /** 관리자/총판/본사/매장 명칭 조회 */
    DefaultMap<Object> getStoreNmList(SmsSendVO smsSendVO);

    /** 잔여수량 조회 */
    DefaultMap<Object> getSmsQtyList(SmsSendVO smsSendVO);

    /** 전송,예약 저장 insert */
    int getSmsSendReserveSaveInsert(SmsSendVO smsSendVO);
    int getSmsSendReserveSaveInsertLMS(SmsSendVO smsSendVO);

    /** 잔여수량 조회 */
    String getSmsQty(SmsSendVO smsSendVO);

    /** 잔여수량 저장 update */
    int getSmsQtySaveUpdate(SmsSendVO smsSendVO);

    /** 수신자추가 팝업 - 조회 */
    List<DefaultMap<Object>> getAddresseeAddList(SmsSendVO smsSendVO);

    /** 전송이력시퀀스 조회 */
    String getSmsSendSeq(SessionInfoVO sessionInfoVO);

    /** 전송이력 저장 insert */
    int getSmsSendSeqSaveInsert(SmsSendVO smsSendVO);
}