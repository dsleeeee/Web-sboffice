package kr.co.solbipos.sale.store.storeChannel.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreChannelService.java
 * @Description : 맘스터치 > 점포매출 > 채널별 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreChannelService {

    /** 주문채널별현황 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPayColList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO);

    /** 주문채널별현황 - 할인수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getDcColList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO);

    /** 주문채널별현황 - 객수 컬럼 리스트 조회 */
    List<DefaultMap<String>> getGuestColList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO);

    /** 주문채널별현황 - 주문채널 구분자 조회 */
    List<DefaultMap<String>> getDlvrInFgColList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO);

    /** 주문채널별현황 - 기간별 탭 조회 */
    List<DefaultMap<String>> getStoreChannelPeriodList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO);

    /** 주문채널별현황 - 일별 탭 조회 */
    List<DefaultMap<String>> getStoreChannelDayList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO);

    /** 주문채널별현황 - 일별 탭 조회 */
    List<DefaultMap<String>> getStoreChannelDtlList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO);

    /** 주문채널별현황 - 월별 탭 조회 */
    List<DefaultMap<String>> getStoreChannelMonthList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO);
}
