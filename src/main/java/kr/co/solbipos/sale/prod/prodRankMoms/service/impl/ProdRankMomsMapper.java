package kr.co.solbipos.sale.prod.prodRankMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.prodRankMoms.service.ProdRankMomsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdRankMomsMapper.java
 * @Description : 맘스터치 > 상품매출분석 > 상품별 매출 순위
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.30  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdRankMomsMapper {

    /** 상품별 매출 순위 */
    List<DefaultMap<String>> getProdRankList(ProdRankMomsVO prodRankMomsVO);

    /** 상품매출순위탭 - 차트 조회 */
    List<DefaultMap<String>> getProdRankChartList(ProdRankMomsVO prodRankMomsVO);

    /** 상품매출순위탭 - 엑셀 조회 */
    List<DefaultMap<String>> getProdRankExcelList(ProdRankMomsVO prodRankMomsVO);
}