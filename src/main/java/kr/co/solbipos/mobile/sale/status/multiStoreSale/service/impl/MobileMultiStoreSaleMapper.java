package kr.co.solbipos.mobile.sale.status.multiStoreSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.multiStoreSale.service.MobileMultiStoreSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileMultiStoreSaleMapper.java
 * @Description : (모바일) 매출현황 > 다중매장매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.20  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.05.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileMultiStoreSaleMapper {

    /** 다중매장매출현황 - 조회 */
    List<DefaultMap<Object>> getMobileMultiStoreSaleList(MobileMultiStoreSaleVO mobileMultiStoreSaleVO);
    List<DefaultMap<Object>> getMobileMultiStoreSale2List(MobileMultiStoreSaleVO mobileMultiStoreSaleVO);

    /** 다중매장매출현황 - 차트 조회 */
    List<DefaultMap<Object>> getMobileMultiStoreSaleChartList(MobileMultiStoreSaleVO mobileMultiStoreSaleVO);

    /** 일자-매장별 매출현황 - 조회 */
    List<DefaultMap<Object>> getMobileMultiStoreSaleDayStoreList(MobileMultiStoreSaleVO mobileMultiStoreSaleVO);
}