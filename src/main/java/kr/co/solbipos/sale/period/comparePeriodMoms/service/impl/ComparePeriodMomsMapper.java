package kr.co.solbipos.sale.period.comparePeriodMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.period.comparePeriodMoms.service.ComparePeriodMomsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ComparePeriodMomsMapper.java
 * @Description : 맘스터치 > 매출분석 > 대비기간별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.06   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.12.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ComparePeriodMomsMapper {

    /** 대비기간별 매출 조회 */
    List<DefaultMap<String>> getComparePeriodList(ComparePeriodMomsVO comparePeriodMomsVO);

}
