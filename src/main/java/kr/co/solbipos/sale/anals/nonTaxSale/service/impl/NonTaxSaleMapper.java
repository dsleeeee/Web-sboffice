package kr.co.solbipos.sale.anals.nonTaxSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.nonTaxSale.service.NonTaxSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name  : NonTaxSaleMapper.java
 * @Description : 미스터피자 > 매출분석 > 비과세매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.02  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.02
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface NonTaxSaleMapper {
    /** 비과세매출 - 조회 */
    List<DefaultMap<Object>> getNonTaxSaleList(NonTaxSaleVO nonTaxSaleVO);
}
