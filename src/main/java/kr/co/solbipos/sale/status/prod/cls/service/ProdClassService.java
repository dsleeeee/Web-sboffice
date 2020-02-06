package kr.co.solbipos.sale.status.prod.cls.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ProdClassService {
	/** 상품별 매출 - 상품매출순위 리스트 조회 */
    List<DefaultMap<String>> getProdClassList(ProdClassVO prodClassVO, SessionInfoVO sessionInfoVO);

}
