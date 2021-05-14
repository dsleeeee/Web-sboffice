package kr.co.solbipos.mobile.sale.status.weekSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.weekSale.service.MobileWeekSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileWeekSaleMapper.java
 * @Description : (모바일) 매출현황 > 주간매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.14  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.05.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileWeekSaleMapper {

    /** 매출종합 - 조회 */
    DefaultMap<String> getMobileWeekSaleTotalList(MobileWeekSaleVO mobileWeekSaleVO);

    /** 결제수단 - 조회 */
    List<DefaultMap<Object>> getMobileWeekSalePayList(MobileWeekSaleVO mobileWeekSaleVO);

    /** 할인내역 - 조회 */
    List<DefaultMap<Object>> getMobileWeekSaleDcList(MobileWeekSaleVO mobileWeekSaleVO);

    /** 내점현황 - 조회 */
    DefaultMap<String> getMobileWeekSaleShopList(MobileWeekSaleVO mobileWeekSaleVO);

    /** 내점/배달/포장 - 조회 */
    List<DefaultMap<Object>> getMobileWeekSaleDlvrList(MobileWeekSaleVO mobileWeekSaleVO);

    /** 내점/배달/포장 - 차트 조회 */
    List<DefaultMap<Object>> getMobileWeekSaleDlvrChartList(MobileWeekSaleVO mobileWeekSaleVO);
    List<DefaultMap<Object>> getMobileWeekSaleDlvrChart2List(MobileWeekSaleVO mobileWeekSaleVO);

    /** 일자별 매출현황 - 조회 */
    List<DefaultMap<Object>> getMobileWeekSaleDtlList(MobileWeekSaleVO mobileWeekSaleVO);
}