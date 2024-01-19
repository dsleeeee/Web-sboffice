package kr.co.solbipos.sale.moms.timeSaleMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.moms.timeSaleMoms.service.TimeSaleMomsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TimeSaleMomsMapper.java
 * @Description : 맘스터치 > 간소화화면 > 시간대매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.03  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.01.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TimeSaleMomsMapper {

    /** 시간대매출 - 조회 */
    List<DefaultMap<Object>> getTimeSaleMomsList(TimeSaleMomsVO timeSaleMomsVO);

    /** 시간대매출 - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getTimeSaleMomsExcelList(TimeSaleMomsVO timeSaleMomsVO);

    /** 시간대매출 - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getTimeSaleMomsExcelDivisionList(TimeSaleMomsVO timeSaleMomsVO);
}