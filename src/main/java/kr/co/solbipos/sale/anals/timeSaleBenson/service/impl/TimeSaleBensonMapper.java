package kr.co.solbipos.sale.anals.timeSaleBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.timeSaleBenson.service.TimeSaleBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TimeSaleBensonMapper.java
 * @Description : 벤슨 > 매출분석 > 시간대매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.20  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.20
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TimeSaleBensonMapper {

    /** 일별 탭 - 조회 */
    List<DefaultMap<Object>> getTimeSaleBensonDayList(TimeSaleBensonVO timeSaleBensonVO);

    /** 일별 탭 - 조회조건 엑셀다운로드 */
    List<DefaultMap<Object>> getTimeSaleBensonDayExcelList(TimeSaleBensonVO timeSaleBensonVO);

    /** 월별 탭 - 조회 */
    List<DefaultMap<Object>> getTimeSaleBensonMonthList(TimeSaleBensonVO timeSaleBensonVO);

    /** 월별 탭 - 조회조건 엑셀다운로드 */
    List<DefaultMap<Object>> getTimeSaleBensonMonthExcelList(TimeSaleBensonVO timeSaleBensonVO);
}
