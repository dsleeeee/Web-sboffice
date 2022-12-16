package kr.co.solbipos.sale.prod.saleProdRankMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.saleProdRankMoms.service.SaleProdRankMomsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SaleProdRankMomsMapper.java
 * @Description : 맘스터치 > 상품매출분석 > 상품별매출순위
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.06   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.12.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SaleProdRankMomsMapper {

    /** 상품별매출순위 조회 */
    List<DefaultMap<String>> getSaleProdRankList(SaleProdRankMomsVO saleProdRankMomsVO);

    /** 상품별매출순위 조회(엑셀용) */
    List<DefaultMap<String>> getSaleProdRankExcelList(SaleProdRankMomsVO saleProdRankMomsVO);
}
