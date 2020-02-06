package kr.co.solbipos.sale.anals.versusPeriod.hour.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.hour.service.ProdHourVO;

public interface VersusPeriodHourService {
	/** 상품별 매출 - 시간대별 리스트 조회 */
    List<DefaultMap<String>> getVersusPeriodHourList(VersusPeriodHourVO versusPeriodHourVO, SessionInfoVO sessionInfoVO);

}
