package kr.co.solbipos.sale.status.dcfgPeriodSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.dcfgPeriodSale.service.DcfgPeriodSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DcfgPeriodSaleMapper.java
 * @Description : 매출관리 > 매출현황2 > 할인구분기간상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.14  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.06.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DcfgPeriodSaleMapper {

    /**
     * 할인구분기간상세 리스트 조회
     * @param dcfgPeriodSaleVO
     * @return
     */
    List<DefaultMap<String>> getDcfgPeriodSaleList(DcfgPeriodSaleVO dcfgPeriodSaleVO);
}
