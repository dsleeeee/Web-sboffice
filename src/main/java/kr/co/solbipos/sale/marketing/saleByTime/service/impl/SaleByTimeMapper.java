package kr.co.solbipos.sale.marketing.saleByTime.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.marketing.saleByTime.service.SaleByTimeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : SaleByTimeMapper.java
 * @Description : 미스터피자 > 마케팅조회 > 시간대별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface SaleByTimeMapper {

    /** 시간대별매출 - 조회 */
    List<DefaultMap<Object>> getSaleByTimeList(SaleByTimeVO saleByTimeVO);
}
