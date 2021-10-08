package kr.co.solbipos.mobile.sale.status.orderStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.orderStatus.service.MobileOrderStatusVO;
import kr.co.solbipos.sale.orderStatus.orderStatus.service.OrderStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileOrderStatusMapper.java
 * @Description : 매출현황 > 주문현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.10.01  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.10.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface MobileOrderStatusMapper {
    /** 주문현황 - 조회 */
    List<DefaultMap<String>> getMobileOrderStatusList(MobileOrderStatusVO mobileOrderStatusVO);

    /** 주문현황 상세 팝업 - 조회 */
    List<DefaultMap<String>> getMobileOrderStatusDtlList(MobileOrderStatusVO mobileOrderStatusVO);

}
