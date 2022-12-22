package kr.co.solbipos.sale.pay.storePayMonth.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.pay.storePayMonth.service.StorePayMonthVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StorePayMonthMapper.java
 * @Description : 맘스터치 > 결제수단매출 > 매장-월별결제수단매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.21  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StorePayMonthMapper {

    /** 조회 */
    List<DefaultMap<Object>> getStorePayMonthList(StorePayMonthVO storePayMonthVO);
    List<DefaultMap<Object>> getStorePayMonthExcelList(StorePayMonthVO storePayMonthVO);
}