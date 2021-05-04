package kr.co.solbipos.mobile.sale.status.todaySale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.todaySale.service.MobileTodaySaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileTodaySaleMapper.java
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
public interface MobileTodaySaleMapper {

    /** 당일매출종합 - 조회 */
    DefaultMap<String> getMobileTodaySaleList(MobileTodaySaleVO mobileTodaySaleVO);

    /** 결제수단 조회 */
    List<DefaultMap<Object>> getMobileTodaySalePayList(MobileTodaySaleVO mobileTodaySaleVO);

    /** 할인내역 조회 */
    List<DefaultMap<Object>> getMobileTodaySaleDcList(MobileTodaySaleVO mobileTodaySaleVO);

    /** 매장/배달/포장 조회 */
    List<DefaultMap<Object>> getMobileTodaySaleDlvrList(MobileTodaySaleVO mobileTodaySaleVO);

    /** 시간대별 조회 */
    List<DefaultMap<Object>> getMobileTodaySaleTimeList(MobileTodaySaleVO mobileTodaySaleVO);
}