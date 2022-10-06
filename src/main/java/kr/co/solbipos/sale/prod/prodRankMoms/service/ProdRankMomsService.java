package kr.co.solbipos.sale.prod.prodRankMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ProdRankService.java
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
public interface ProdRankMomsService {

    /** 상품별 매출 순위 승인 조회 */
    List<DefaultMap<String>> getProdRankList(ProdRankMomsVO prodRankMomsVO, SessionInfoVO sessionInfoVO);

    /** 상품매출순위탭 - 차트 조회 */
    List<DefaultMap<String>> getProdRankChartList(ProdRankMomsVO prodRankMomsVO, SessionInfoVO sessionInfoVO);

    /** 상품매출순위탭 - 엑셀 조회 */
    List<DefaultMap<String>> getProdRankExcelList(ProdRankMomsVO prodRankMomsVO, SessionInfoVO sessionInfoVO);


}