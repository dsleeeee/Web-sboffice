package kr.co.solbipos.sale.moms.timeProdSalePmixMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.moms.timeProdSalePmixMoms.service.TimeProdSalePmixMomsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TimeProdSalePmixMomsMapper.java
 * @Description : 맘스터치 > 간소화화면 > 시간대별 상품매출(P.MIX)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.05.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.05.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TimeProdSalePmixMomsMapper {

    /** 시간대별 상품매출(P.MIX) - 조회 */
    List<DefaultMap<Object>> getTimeProdSalePmixMomsList(TimeProdSalePmixMomsVO timeProdSalePmixMomsVO);

    /** 시간대별 상품매출(P.MIX) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getTimeProdSalePmixMomsExcelList(TimeProdSalePmixMomsVO timeProdSalePmixMomsVO);
}