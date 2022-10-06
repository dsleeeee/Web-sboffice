package kr.co.solbipos.sale.prod.prodRankStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.prodRankStore.service.ProdRankStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdRankStoreMapper.java
 * @Description : 맘스터치 > 승인관리2 > 상품판매순위(점포)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.05  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdRankStoreMapper {

    /** 상품별 매출 순위 */
    List<DefaultMap<Object>> getProdRankStoreList(ProdRankStoreVO prodRankStoreVO);
    List<DefaultMap<Object>> getProdRankStoreExcelList(ProdRankStoreVO prodRankStoreVO);
}