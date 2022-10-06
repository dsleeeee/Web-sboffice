package kr.co.solbipos.sale.prod.periodProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.periodProd.service.PeriodProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PeriodProdMapper.java
 * @Description : 맘스터치 > 승인관리2 > 대비기간별 상품 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PeriodProdMapper {

    /** 상품별 매출 순위 */
    List<DefaultMap<Object>> getPeriodProdList(PeriodProdVO periodProdVO);
    List<DefaultMap<Object>> getPeriodProdExcelList(PeriodProdVO periodProdVO);
}