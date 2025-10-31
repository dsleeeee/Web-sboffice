package kr.co.solbipos.kookmin.generalInfo.dailySaleReport.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kookmin.generalInfo.dailySaleReport.service.DailySaleReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name  : DailySaleReportMapper.java
 * @Description : 국민대 > 총괄정보 > 일일매출보고서
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.23  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.10.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface DailySaleReportMapper {

    /** 일일매출보고서 리스트 조회 */
    List<DefaultMap<Object>> getDailySaleReportList(DailySaleReportVO dailySaleReportVO);
}
