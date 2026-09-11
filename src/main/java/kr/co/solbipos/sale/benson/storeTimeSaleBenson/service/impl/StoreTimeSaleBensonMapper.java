package kr.co.solbipos.sale.benson.storeTimeSaleBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.storeTimeSaleBenson.service.StoreTimeSaleBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreTimeSaleBensonMapper.java
 * @Description : 벤슨 > 매출조회 > 지정가맹점_시간대별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreTimeSaleBensonMapper {

    /** 지정가맹점_시간대별 - 조회 */
    List<DefaultMap<Object>> getStoreTimeSaleBensonList(StoreTimeSaleBensonVO storeTimeSaleBensonVO);
}
