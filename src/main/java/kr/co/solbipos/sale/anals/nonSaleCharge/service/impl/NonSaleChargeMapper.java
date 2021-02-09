package kr.co.solbipos.sale.anals.nonSaleCharge.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.nonSaleCharge.service.NonSaleChargeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : NonSaleChargeMapper.java
 * @Description : 매출관리 - 매출분석 - 비매출충전내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.02.04  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.02.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface NonSaleChargeMapper {

    /** 비매출 충전내역 조회 */
    List<DefaultMap<String>> getNonSaleChargeList(NonSaleChargeVO nonSaleChargeVO);

    /** 비매출 충전내역 조회 Excel 다운로드 */
    List<DefaultMap<String>> getNonSaleChargeExcelList(NonSaleChargeVO nonSaleChargeVO);
}
