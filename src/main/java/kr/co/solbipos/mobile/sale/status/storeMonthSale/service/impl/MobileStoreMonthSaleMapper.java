package kr.co.solbipos.mobile.sale.status.storeMonthSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.storeMonthSale.service.MobileStoreMonthSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileStoreMonthSaleMapper.java
 * @Description : 모바일 매장매출 > 월별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.03  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.09.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileStoreMonthSaleMapper {
 /** 일자별 매출현황 - 조회 */
    List<DefaultMap<Object>> getMobileStoreMonthSaleDtlList(MobileStoreMonthSaleVO mobileStoreMonthSaleVO);
}