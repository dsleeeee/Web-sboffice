package kr.co.solbipos.sale.benson.prodSalePmixStoreBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.prodSalePmixStoreBenson.service.ProdSalePmixStoreBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdSalePmixStoreBensonMapper.java
 * @Description : 벤슨 > 간소화화면 > 상품매출(P.MIX 매장)
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
public interface ProdSalePmixStoreBensonMapper {

    /** 상품매출(P.MIX 매장) - 조회 */
    List<DefaultMap<Object>> getProdSalePmixStoreBensonList(ProdSalePmixStoreBensonVO prodSalePmixStoreBensonVO);

    /** 상품매출(P.MIX 매장) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSalePmixStoreBensonExcelList(ProdSalePmixStoreBensonVO prodSalePmixStoreBensonVO);

    /** 상품매출(P.MIX 매장) - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSalePmixStoreBensonExcelDivisionList(ProdSalePmixStoreBensonVO prodSalePmixStoreBensonVO);
}
