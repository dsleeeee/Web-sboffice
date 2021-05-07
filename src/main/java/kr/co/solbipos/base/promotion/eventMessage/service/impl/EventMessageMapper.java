package kr.co.solbipos.base.promotion.eventMessage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.promotion.eventMessage.service.EventMessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : EventMessageMapper.java
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
@Mapper
@Repository
public interface EventMessageMapper {

    /** 이벤트문구출력관리 리스트 조회 */
    List<DefaultMap<String>> getEventMessageList(EventMessageVO eventMessageVO);

    /** 이벤트문구출력물 코드 생성 */
    String getCode(EventMessageVO eventMessageVO);

    /** 이벤트문구출력물 저장 */
    int saveEventMessage(EventMessageVO eventMessageVO);

    /** 이벤트문구출력물 상세 조회 */
    DefaultMap<String> getEventMessageDetail(EventMessageVO eventMessageVO);

    /** 이벤트문구출력관리 적용상품 리스트 조회 */
    List<DefaultMap<String>> getEventMessageProdList(EventMessageVO eventMessageVO);

    /** 이벤트문구출력관리 적용상품 선택팝업 상품리스트 조회 */
    List<DefaultMap<String>> getProdList(EventMessageVO eventMessageVO);

    /** 이벤트문구출력관리 적용상품 선택팝업 상품추가 */
    int insertEventMessageProd(EventMessageVO eventMessageVO);

    /** 이벤트문구출력관리 적용상품 조건수량 수정 */
    int updateEventMessageProd(EventMessageVO eventMessageVO);

    /** 이벤트문구출력관리 적용상품 삭제 */
    int deleteEventMessageProd(EventMessageVO eventMessageVO);

    /** 이벤트문구출력관리 적용매장 리스트 조회 */
    List<DefaultMap<String>> getEventMessageStoreList(EventMessageVO eventMessageVO);

    /** 이벤트문구출력관리 적용매장 선택팝업 매장리스트 조회 */
    List<DefaultMap<String>> getStoreList(EventMessageVO eventMessageVO);

    /** 이벤트문구출력관리 적용매장 선택팝업 전매장적용 */
    int insertEventMessageStoreAll(EventMessageVO eventMessageVO);

    /** 이벤트문구출력관리 적용매장 선택팝업 매장추가 */
    int insertEventMessageStore(EventMessageVO eventMessageVO);

    /** 이벤트문구출력관리 적용매장 삭제 */
    int deleteEventMessageStore(EventMessageVO eventMessageVO);
}
