package kr.co.solbipos.sale.store.storeMonthPay.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.store.storeMonthPay.service.StoreMonthPayVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreMonthPayMapper.java
 * @Description : 맘스터치 > 점포매출 > 점포-월별 결제 수단 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreMonthPayMapper {

    /** 조회 */
    List<DefaultMap<Object>> getStoreMonthPayList(StoreMonthPayVO payDayDeviceVO);
}