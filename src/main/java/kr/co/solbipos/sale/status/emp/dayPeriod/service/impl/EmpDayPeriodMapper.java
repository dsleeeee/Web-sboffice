package kr.co.solbipos.sale.status.emp.dayPeriod.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.emp.dayPeriod.service.EmpDayPeriodVO;

@Mapper
@Repository
public interface EmpDayPeriodMapper {
    /** 판매자별매출 설정기간별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getEmpDayPeriodList(EmpDayPeriodVO empDayPeriodVO);

    /** 판매자별매출 설정기간별 탭 - 리스트 상세 조회 */
	List<DefaultMap<String>> getEmpDayPeriodDtlList(EmpDayPeriodVO empDayPeriodVO);
    /** 판매자별매출 설정기간별 탭 - 리스트 상세(엑셀) 조회 */
	List<DefaultMap<String>> getEmpDayPeriodDtlExcelList(EmpDayPeriodVO empDayPeriodVO);
}
