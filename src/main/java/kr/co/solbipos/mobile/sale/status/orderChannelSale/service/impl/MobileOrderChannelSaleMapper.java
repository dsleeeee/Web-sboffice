package kr.co.solbipos.mobile.sale.status.orderChannelSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.orderChannelSale.service.MobileOrderChannelSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileOrderChannelSaleMapper.java
 * @Description : (모바일) 매출현황 > 주문채널별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.30  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.08.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileOrderChannelSaleMapper {

    /** 모바일 매출현황 - 주문채널 구분자 조회 */
    List<DefaultMap<String>> getDlvrInFgColList(MobileOrderChannelSaleVO mobileOrderChannelSaleVO);

    /** 모바일 매출현황 - 주문채널별 현황 조회 */
    List<DefaultMap<String>> getOrderChannelSalePayList(MobileOrderChannelSaleVO mobileOrderChannelSaleVO);

    /** 모바일 매출현황 - 주문채널 일자별 매출현황 조회 */
    List<DefaultMap<String>> getOrderChannelSaleDtlList(MobileOrderChannelSaleVO mobileOrderChannelSaleVO);
}
