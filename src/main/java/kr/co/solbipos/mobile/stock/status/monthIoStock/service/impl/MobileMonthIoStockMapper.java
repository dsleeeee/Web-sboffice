package kr.co.solbipos.mobile.stock.status.monthIoStock.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.stock.status.monthIoStock.service.MobileMonthIoStockVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : MobileMonthIoStockMapper.java
 * @Description : (모바일)재고현황 > 월수불현황
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
public interface MobileMonthIoStockMapper {

    /** 월수불현황 리스트 조회(매장권한) */
    List<DefaultMap<String>> storeMonthIoStockList(MobileMonthIoStockVO mobileMonthIoStockVO);
    List<DefaultMap<String>> storeMonthIoStockExcelList(MobileMonthIoStockVO mobileMonthIoStockVO);

    /** 월수불현황 리스트 조회(본사권한) */
    List<DefaultMap<String>> hqMonthIoStockList(MobileMonthIoStockVO mobileMonthIoStockVO);
    List<DefaultMap<String>> hqMonthIoStockExcelList(MobileMonthIoStockVO mobileMonthIoStockVO);
}
