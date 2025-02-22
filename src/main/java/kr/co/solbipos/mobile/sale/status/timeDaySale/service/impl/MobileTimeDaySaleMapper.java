package kr.co.solbipos.mobile.sale.status.timeDaySale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.timeDaySale.service.MobileTimeDaySaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileTimeDaySaleMapper.java
 * @Description : (모바일) 매출현황 > 시간대별(일자별)매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileTimeDaySaleMapper {

    /** 일자-시간대별 - 조회 */
    List<DefaultMap<Object>> getMobileTimeDaySaleDateTimeList(MobileTimeDaySaleVO mobileTimeDaySaleVO);

    /** 시간대별 - 조회 */
    List<DefaultMap<Object>> getMobileTimeDaySaleTimeList(MobileTimeDaySaleVO mobileTimeDaySaleVO);

    /** 시간대별 - 차트 조회 */
    List<DefaultMap<Object>> getMobileTimeDaySaleTimeChartList(MobileTimeDaySaleVO mobileTimeDaySaleVO);
}