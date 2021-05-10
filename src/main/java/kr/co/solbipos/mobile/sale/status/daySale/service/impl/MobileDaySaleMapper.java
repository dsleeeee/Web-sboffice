package kr.co.solbipos.mobile.sale.status.daySale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.daySale.service.MobileDaySaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileDaySaleMapper.java
 * @Description : (모바일) 매출현황 > 일별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.05.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileDaySaleMapper {

    /** 매출종합 - 조회 */
    DefaultMap<String> getMobileDaySaleTotalList(MobileDaySaleVO mobileDaySaleVO);

    /** 결제수단 - 조회 */
    List<DefaultMap<Object>> getMobileDaySalePayList(MobileDaySaleVO mobileDaySaleVO);

    /** 할인내역 - 조회 */
    List<DefaultMap<Object>> getMobileDaySaleDcList(MobileDaySaleVO mobileDaySaleVO);

    /** 내점현황 - 조회 */
    DefaultMap<String> getMobileDaySaleShopList(MobileDaySaleVO mobileDaySaleVO);

    /** 내점/배달/포장 - 조회 */
    List<DefaultMap<Object>> getMobileDaySaleDlvrList(MobileDaySaleVO mobileDaySaleVO);

    /** 내점/배달/포장 - 차트 조회 */
    List<DefaultMap<Object>> getMobileDaySaleDlvrChartList(MobileDaySaleVO mobileDaySaleVO);
    List<DefaultMap<Object>> getMobileDaySaleDlvrChart2List(MobileDaySaleVO mobileDaySaleVO);

    /** 일자별 매출현황 - 조회 */
    List<DefaultMap<Object>> getMobileDaySaleDtlList(MobileDaySaleVO mobileDaySaleVO);
}