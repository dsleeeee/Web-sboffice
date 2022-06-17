package kr.co.solbipos.sale.status.orderEmp.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.emp.day.service.EmpDayVO;
import kr.co.solbipos.sale.status.orderEmp.service.OrderEmpVO;
import kr.co.solbipos.sale.status.side.service.SideVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : OrderEmpMapper.java
 * @Description : 매출관리 > 매출현황2 > 주문자현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.10 권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface OrderEmpMapper {
    /** 기간별탭 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodList(OrderEmpVO orderEmpVO);

    /** 기간별탭 엑셀 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodExcelList(OrderEmpVO orderEmpVO);

    /** 기간별탭 상세 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodDtlList(OrderEmpVO orderEmpVO);

    /** 기간별탭 상세 엑셀 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodDtlExcelList(OrderEmpVO orderEmpVO);

    /** 일자별 조회 */
    List<DefaultMap<String>> getOrderEmpDayList(OrderEmpVO orderEmpVO);

    /** 일자별 엑셀 조회 */
    List<DefaultMap<String>> getOrderEmpDayExcelList(OrderEmpVO orderEmpVO);

    /** 판매자별 매출 -판매자 리스트 조회  */
    List<DefaultMap<String>> getEmpMebList(OrderEmpVO orderEmpVO);
}
