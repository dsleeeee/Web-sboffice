package kr.co.solbipos.mobile.sale.status.storeDayOfWeekSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.storeDayOfWeekSale.service.MobileStoreDayOfWeekSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileStoreDayOfWeekSaleMapper.java
 * @Description : (모바일) 매장매출 >
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.27  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.04.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileStoreDayOfWeekSaleMapper {

    /** 요일별 - 조회 */
    List<DefaultMap<Object>> getMobileStoreDayOfWeekSaleList(MobileStoreDayOfWeekSaleVO mobileStoreDayOfWeekSaleVO);
}