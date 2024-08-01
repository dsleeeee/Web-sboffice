package kr.co.solbipos.mobile.stock.status.dailyIoStock.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.stock.status.dailyIoStock.service.MobileDailyIoStockVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : MobileDailyIoStockMapper.java
 * @Description : (모바일)재고현황 > 일자별수불현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.19  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileDailyIoStockMapper {

    /** 일수불현황 - 일수불현황 리스트 조회 */
    List<DefaultMap<String>> getDailyIoStockList(MobileDailyIoStockVO mobileDailyIoStockVO);
    /** 일수불현황 - 일수불현황 리스트(엑셀) 조회 */
    List<DefaultMap<String>> getDailyIoStockExcelList(MobileDailyIoStockVO mobileDailyIoStockVO);
}
