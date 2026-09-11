package kr.co.solbipos.sale.benson.storePayDayBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.storePayDayBenson.service.StorePayDayBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StorePayDayBensonMapper.java
 * @Description : 벤슨 > 결제수단매출 > 매장-일별결제수단매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StorePayDayBensonMapper {

    /** 조회 */
    List<DefaultMap<Object>> getStorePayDayBensonList(StorePayDayBensonVO storePayDayBensonVO);
    List<DefaultMap<Object>> getStorePayDayBensonExcelList(StorePayDayBensonVO storePayDayBensonVO);
}
