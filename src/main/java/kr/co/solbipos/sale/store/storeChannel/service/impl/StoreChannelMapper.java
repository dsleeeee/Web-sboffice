package kr.co.solbipos.sale.store.storeChannel.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreChannelMapper.java
 * @Description : 매출관리 > 배달현황 > 주문채널별현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.01  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.09.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreChannelMapper {

    /** 주문채널별현황 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPayColList(StoreChannelVO storeChannelVO);

    /** 주문채널별현황 - 할인수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getDcColList(StoreChannelVO storeChannelVO);

    /** 주문채널별현황 - 객수 컬럼 리스트 조회 */
    List<DefaultMap<String>> getGuestColList(StoreChannelVO storeChannelVO);

    /** 주문채널별현황 - 주문채널 구분자 조회 */
    List<DefaultMap<String>> getDlvrInFgColList(StoreChannelVO storeChannelVO);

    /** 주문채널별현황 - 기간별 탭 조회 */
    List<DefaultMap<String>> getStoreChannelPeriodList(StoreChannelVO storeChannelVO);

    /** 주문채널별현황 - 일별 탭 조회 */
    List<DefaultMap<String>> getStoreChannelDayList(StoreChannelVO storeChannelVO);

    /** 주문채널별현황 - 일별 상세 조회 */
    List<DefaultMap<String>> getStoreChannelDtlList(StoreChannelVO storeChannelVO);

    /** 주문채널별현황 - 월별 탭 조회 */
    List<DefaultMap<String>> getStoreChannelMonthList(StoreChannelVO storeChannelVO);

}
