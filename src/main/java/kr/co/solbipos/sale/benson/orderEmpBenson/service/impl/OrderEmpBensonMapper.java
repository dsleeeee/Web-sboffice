package kr.co.solbipos.sale.benson.orderEmpBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.orderEmpBenson.service.OrderEmpBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : OrderEmpBensonMapper.java
 * @Description : 벤슨 > 매출현황2 > 주문자현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10 김유승        최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */

@Mapper
@Repository
public interface OrderEmpBensonMapper {
    /** 기간별탭 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodList(OrderEmpBensonVO orderEmpBensonVO);

    /** 기간별탭 엑셀 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodExcelList(OrderEmpBensonVO orderEmpBensonVO);

    /** 기간별탭 상세 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodDtlList(OrderEmpBensonVO orderEmpBensonVO);

    /** 기간별탭 상세 엑셀 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodDtlExcelList(OrderEmpBensonVO orderEmpBensonVO);

    /** 일자별 조회 */
    List<DefaultMap<String>> getOrderEmpDayList(OrderEmpBensonVO orderEmpBensonVO);

    /** 일자별 엑셀 조회 */
    List<DefaultMap<String>> getOrderEmpDayExcelList(OrderEmpBensonVO orderEmpBensonVO);

    /** 판매자별 매출 -판매자 리스트 조회  */
    List<DefaultMap<String>> getEmpMebList(OrderEmpBensonVO orderEmpBensonVO);
}
