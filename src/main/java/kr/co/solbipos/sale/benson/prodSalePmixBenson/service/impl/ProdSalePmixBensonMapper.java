package kr.co.solbipos.sale.benson.prodSalePmixBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.prodSalePmixBenson.service.ProdSalePmixBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdSalePmixBensonMapper.java
 * @Description : 벤슨 > 간소화화면 > 상품매출(P.MIX)
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
public interface ProdSalePmixBensonMapper {

    /** 상품매출(P.MIX) - 조회 */
    List<DefaultMap<Object>> getProdSalePmixBensonList(ProdSalePmixBensonVO prodSalePmixBensonVO);

    /** 상품매출(P.MIX) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSalePmixBensonExcelList(ProdSalePmixBensonVO prodSalePmixBensonVO);
}
