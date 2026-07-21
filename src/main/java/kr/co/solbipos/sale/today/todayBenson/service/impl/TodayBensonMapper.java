package kr.co.solbipos.sale.today.todayBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.today.todayBenson.service.TodayBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TodayBensonMapper.java
 * @Description : 벤슨 > 매출분석 > 당일매출현황(영수증)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.16  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.16
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface TodayBensonMapper {

    /** 당일매출현황(영수증) 리스트 조회 */
    List<DefaultMap<Object>> getTodayBensonList(TodayBensonVO todayBensonVO);

    /** 당일매출현황(영수증) 엑셀 다운로드 조회 */
    List<DefaultMap<Object>> getTodayBensonExcelList(TodayBensonVO todayBensonVO);

    /** 영수증조회팝업 - 영수증 출력데이터 조회 */
    DefaultMap<String> getBillPrintData(TodayBensonVO todayBensonVO);
}
