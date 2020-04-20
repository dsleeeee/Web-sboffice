package kr.co.solbipos.sale.status.prod.rank.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ProdRankService {
	/** 상품별 매출 - 상품매출순위 리스트 조회 */
    List<DefaultMap<String>> getProdRankList(ProdRankVO prodRankVO, SessionInfoVO sessionInfoVO);

    /** 상품별 매출 - 차트 조회 */
    List<DefaultMap<String>> getProdRankChartList(ProdRankVO prodRankVO, SessionInfoVO sessionInfoVO);
}
