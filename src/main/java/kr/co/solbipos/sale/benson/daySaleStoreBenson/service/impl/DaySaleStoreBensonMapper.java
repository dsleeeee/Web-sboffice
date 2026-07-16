package kr.co.solbipos.sale.benson.daySaleStoreBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.daySaleStoreBenson.service.DaySaleStoreBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DaySaleStoreBensonMapper.java
 * @Description : 벤슨 > 간소화화면 > 일별매출(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface DaySaleStoreBensonMapper {

    /** 일별매출(매장) - 조회 */
    List<DefaultMap<Object>> getDaySaleStoreBensonList(DaySaleStoreBensonVO daySaleStoreBensonVO);

    /** 일별매출(매장) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getDaySaleStoreBensonExcelList(DaySaleStoreBensonVO daySaleStoreBensonVO);

    /** 일별매출(매장) - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getDaySaleStoreBensonExcelDivisionList(DaySaleStoreBensonVO daySaleStoreBensonVO);
}
