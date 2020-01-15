package kr.co.solbipos.sale.cmmSalePopup.dayBillInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.cmmSalePopup.dayBillInfo.service.DayBillInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DayBillInfoMapper {

    /** 매장별 영수건수 팝업 - 매장별 영수건수 리스트 조회 */
    List<DefaultMap<Object>> getDayStoreBillList(DayBillInfoVO dayBillInfoVO);
}