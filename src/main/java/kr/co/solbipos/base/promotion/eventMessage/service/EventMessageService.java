package kr.co.solbipos.base.promotion.eventMessage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : EventMessageService.java
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
public interface EventMessageService {

    /** 이벤트문구출력관리 리스트 조회 */
    List<DefaultMap<String>> getEventMessageList(EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO);

    /** 이벤트문구출력물 등록/수정 */
    String saveEventMessage(EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO);

    /** 이벤트문구출력물 상세 조회 */
    DefaultMap<String> getEventMessageDetail(EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO);

    /** 이벤트문구출력관리 적용상품 리스트 조회 */
    List<DefaultMap<String>> getEventMessageProdList(EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO);

    /** 이벤트문구출력관리 적용상품 선택팝업 상품리스트 조회 */
    List<DefaultMap<String>> getProdList(EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO);

    /** 이벤트문구출력관리 적용상품 선택팝업 상품추가/수정/삭제 */
    int saveEventMessageProd(EventMessageVO[] eventMessageVOs, SessionInfoVO sessionInfoVO);

    /** 이벤트문구출력관리 적용매장 리스트 조회 */
    List<DefaultMap<String>> getEventMessageStoreList(EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO);

    /** 이벤트문구출력관리 적용매장 선택팝업 매장리스트 조회 */
    List<DefaultMap<String>> getStoreList(EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO);

    /** 이벤트문구출력관리 적용매장 선택팝업 전매장적용 */
    int insertEventMessageStoreAll(EventMessageVO eventMessageVO, SessionInfoVO sessionInfoVO);

    /** 이벤트문구출력관리 적용매장 선택팝업 매장추가/삭제 */
    int saveEventMessageStore(EventMessageVO[] eventMessageVOs, SessionInfoVO sessionInfoVO);

}
