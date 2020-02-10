package kr.co.solbipos.sale.status.prod.hour.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ProdHourService {
	/** 상품별 매출 - 시간대별 리스트 조회 */
    List<DefaultMap<String>> getProdHourList(ProdHourVO prodHourVO, SessionInfoVO sessionInfoVO);

}
