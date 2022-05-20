package kr.co.solbipos.sale.status.nonSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.nonSale.service.NonSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : NonSaleMapper.java
 * @Description : 매출관리 > 매출현황2 > 보증금현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.05.16  권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.05.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface NonSaleMapper {
    /** 일별 조회 */
    List<DefaultMap<String>> getNonSaleDayList(NonSaleVO nonSaleVO);

    /** 일별 엑셀 조회 */
    List<DefaultMap<String>> getNonSaleDayExcelList(NonSaleVO nonSaleVO);

}
