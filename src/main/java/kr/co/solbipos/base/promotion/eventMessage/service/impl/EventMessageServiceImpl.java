package kr.co.solbipos.base.promotion.eventMessage.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.promotion.eventMessage.service.EventMessageService;
import kr.co.solbipos.base.promotion.eventMessage.service.EventMessageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : EventMessageServiceImpl.java
 * @Description : 기초관리 - 프로모션관리 - 이벤트문구출력관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.03  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .05. 03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("EventMessageService")
public class EventMessageServiceImpl implements EventMessageService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final EventMessageMapper eventMessageMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public EventMessageServiceImpl(EventMessageMapper eventMessageMapper, MessageService messageService) {
        this.eventMessageMapper = eventMessageMapper;
        this.messageService = messageService;
    }

    /** 이벤트문구출력관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getEventMessageList(@RequestBody EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO) {

        eventMessageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        eventMessageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            eventMessageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return eventMessageMapper.getEventMessageList(eventMessageVO);
    }

    /** 이벤트문구출력물 등록/수정 */
    @Override
    public String saveEventMessage(EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        eventMessageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        eventMessageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        eventMessageVO.setRegDt(currentDt);
        eventMessageVO.setRegId(sessionInfoVO.getUserId());
        eventMessageVO.setModDt(currentDt);
        eventMessageVO.setModId(sessionInfoVO.getUserId());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            eventMessageVO.setStoreCd(sessionInfoVO.getStoreCd());
            eventMessageVO.setRegFg(sessionInfoVO.getOrgnFg().getCode()); // 이벤트문구출력물 등록구분 H:본사 S:매장
        }

        // 신규등록 시, 이벤트문구출력물 코드 생성
        String msgCd = "";
        if(!"".equals(eventMessageVO.getMsgCd()) && eventMessageVO.getMsgCd() != null){
            msgCd = eventMessageVO.getMsgCd();

            // 수정모드 시, 적용상품 체크 여부에 따른 적용상품 리스트 데이터 삭제
            if("0".equals(eventMessageVO.getPrintCondiFg())){
                eventMessageMapper.deleteEventMessageProdAll(eventMessageVO);
            }

        }else{
            msgCd = eventMessageMapper.getCode(eventMessageVO);
            eventMessageVO.setMsgCd(msgCd);
        }

        // 이벤트문구출력물 코드가 있는 경우, 저장
        if(!"".equals(msgCd) && msgCd != null){
            result = eventMessageMapper.saveEventMessage(eventMessageVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        if(result > 0){
            return msgCd;
        }else{
            return "";
        }
    }

    /** 이벤트문구출력물 상세 조회 */
    @Override
    public DefaultMap<String> getEventMessageDetail(EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO){

        eventMessageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        eventMessageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            eventMessageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return eventMessageMapper.getEventMessageDetail(eventMessageVO);
    }

    /** 이벤트문구출력관리 적용상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getEventMessageProdList(@RequestBody EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO) {

        eventMessageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        eventMessageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            eventMessageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return eventMessageMapper.getEventMessageProdList(eventMessageVO);
    }

    /** 이벤트문구출력관리 적용상품 선택팝업 상품리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO) {

        eventMessageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        eventMessageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            eventMessageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        eventMessageVO.setUserId(sessionInfoVO.getUserId());

        return eventMessageMapper.getProdList(eventMessageVO);
    }

    /** 이벤트문구출력관리 적용상품 선택팝업 상품추가/수정/삭제 */
    @Override
    public int saveEventMessageProd(EventMessageVO[] eventMessageVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for ( EventMessageVO eventMessageVO : eventMessageVOs ) {

            eventMessageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            eventMessageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            eventMessageVO.setRegDt(currentDt);
            eventMessageVO.setRegId(sessionInfoVO.getUserId());
            eventMessageVO.setModDt(currentDt);
            eventMessageVO.setModId(sessionInfoVO.getUserId());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                eventMessageVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            if ( eventMessageVO.getStatus() == GridDataFg.INSERT ) {

                // 이벤트문구출력관리 적용상품 추가
                result = eventMessageMapper.insertEventMessageProd(eventMessageVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( eventMessageVO.getStatus() == GridDataFg.UPDATE ){

                // 이벤트문구출력관리 적용상품 조건수량 수정
                result = eventMessageMapper.updateEventMessageProd(eventMessageVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( eventMessageVO.getStatus() == GridDataFg.DELETE ){

                // 이벤트문구출력관리 적용상품 삭제
                result = eventMessageMapper.deleteEventMessageProd(eventMessageVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }
        }

        if ( procCnt == eventMessageVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /** 이벤트문구출력관리 적용매장 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getEventMessageStoreList(@RequestBody EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO) {

        eventMessageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        eventMessageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            eventMessageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return eventMessageMapper.getEventMessageStoreList(eventMessageVO);
    }

    /** 이벤트문구출력관리 적용매장 선택팝업 매장리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(@RequestBody EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO) {

        eventMessageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        eventMessageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            eventMessageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return eventMessageMapper.getStoreList(eventMessageVO);
    }

    /** 이벤트문구출력관리 적용매장 선택팝업 전매장적용 */
    @Override
    public int insertEventMessageStoreAll(EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        eventMessageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        eventMessageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        eventMessageVO.setRegDt(currentDt);
        eventMessageVO.setRegId(sessionInfoVO.getUserId());
        eventMessageVO.setModDt(currentDt);
        eventMessageVO.setModId(sessionInfoVO.getUserId());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            eventMessageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 이벤트문구출력관리 전매장적용
        result = eventMessageMapper.insertEventMessageStoreAll(eventMessageVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 이벤트문구출력관리 적용매장 선택팝업 매장추가/삭제 */
    @Override
    public int saveEventMessageStore(EventMessageVO[] eventMessageVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for ( EventMessageVO eventMessageVO : eventMessageVOs ) {

            eventMessageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            eventMessageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            eventMessageVO.setRegDt(currentDt);
            eventMessageVO.setRegId(sessionInfoVO.getUserId());
            eventMessageVO.setModDt(currentDt);
            eventMessageVO.setModId(sessionInfoVO.getUserId());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                eventMessageVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            if ( eventMessageVO.getStatus() == GridDataFg.INSERT ) {

                // 이벤트문구출력관리 적용매장 추가
                result = eventMessageMapper.insertEventMessageStore(eventMessageVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( eventMessageVO.getStatus() == GridDataFg.DELETE ){

                // 이벤트문구출력관리 적용매장 삭제
                result = eventMessageMapper.deleteEventMessageStore(eventMessageVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }
        }

        if ( procCnt == eventMessageVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

}
