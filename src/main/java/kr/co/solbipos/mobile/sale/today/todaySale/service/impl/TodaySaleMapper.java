package kr.co.solbipos.mobile.sale.today.todaySale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.today.todaySale.service.TodaySaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TodaySaleMapper.java
 * @Description : (모바일) 매출현황 > 당일매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.02  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.04.02
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TodaySaleMapper {

    /** 당일매출종합 - 조회 */
    DefaultMap<String> getTodaySaleList(TodaySaleVO todaySaleVO);

    /** 당일매출종합 - 조회 */
    DefaultMap<String> getTodaySaleNullList(TodaySaleVO todaySaleVO);

    /** 결제수단 조회 */
    List<DefaultMap<Object>> getTodaySalePayList(TodaySaleVO todaySaleVO);

    /** 할인내역 조회 */
    List<DefaultMap<Object>> getTodaySaleDcList(TodaySaleVO todaySaleVO);

    /** 매장/배달/포장 조회 */
    List<DefaultMap<Object>> getTodaySaleDlvrList(TodaySaleVO todaySaleVO);

    /** 시간대별 조회 */
    List<DefaultMap<Object>> getTodaySaleTimeList(TodaySaleVO todaySaleVO);
}