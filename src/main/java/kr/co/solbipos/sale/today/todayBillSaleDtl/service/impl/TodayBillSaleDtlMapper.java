package kr.co.solbipos.sale.today.todayBillSaleDtl.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.today.todayBillSaleDtl.service.TodayBillSaleDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TodayBillSaleDtlMapper {
    /** 영수증별매출상세현황 - 영수증별매출상세 리스트 조회 */
    List<DefaultMap<String>> getTodayBillSaleDtlList(TodayBillSaleDtlVO todayBillSaleDtlVO);

}
