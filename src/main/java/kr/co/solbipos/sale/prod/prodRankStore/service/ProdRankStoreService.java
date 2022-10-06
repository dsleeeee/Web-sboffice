package kr.co.solbipos.sale.prod.prodRankStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.prodRankStore.service.ProdRankStoreVO;

import java.util.List;

/**
 * @Class Name : ProdRankStoreService.java
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
public interface ProdRankStoreService {

    /** 일별 상품 매출 현황 조회 */
    List<DefaultMap<Object>> getProdRankStoreList(ProdRankStoreVO prodRankStoreVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getProdRankStoreExcelList(ProdRankStoreVO prodRankStoreVO, SessionInfoVO sessionInfoVO);

}