package kr.co.solbipos.mobile.sale.status.monthSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.monthSale.service.MobileMonthSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileMonthSaleMapper.java
 * @Description : (모바일) 매출현황 > 월별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.05.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileMonthSaleMapper {

    /** 매출종합 - 조회 */
    DefaultMap<String> getMobileMonthSaleTotalList(MobileMonthSaleVO mobileMonthSaleVO);

    /** 결제수단 - 조회 */
    List<DefaultMap<Object>> getMobileMonthSalePayList(MobileMonthSaleVO mobileMonthSaleVO);

    /** 할인내역 - 조회 */
    List<DefaultMap<Object>> getMobileMonthSaleDcList(MobileMonthSaleVO mobileMonthSaleVO);

    /** 내점현황 - 조회 */
    DefaultMap<String> getMobileMonthSaleShopList(MobileMonthSaleVO mobileMonthSaleVO);

    /** 내점/배달/포장 - 조회 */
    List<DefaultMap<Object>> getMobileMonthSaleDlvrList(MobileMonthSaleVO mobileMonthSaleVO);

    /** 내점/배달/포장 - 차트 조회 */
    List<DefaultMap<Object>> getMobileMonthSaleDlvrChartList(MobileMonthSaleVO mobileMonthSaleVO);
    List<DefaultMap<Object>> getMobileMonthSaleDlvrChart2List(MobileMonthSaleVO mobileMonthSaleVO);

    /** 월자별 매출현황 - 조회 */
    List<DefaultMap<Object>> getMobileMonthSaleDtlList(MobileMonthSaleVO mobileMonthSaleVO);
}