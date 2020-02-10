package kr.co.solbipos.sale.anals.versusPeriod.cls.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface VersusPeriodClassService {
	/** 대비기간매출분석 - 분류상품별 리스트 조회 */
    List<DefaultMap<String>> getVersusPeriodClassList(VersusPeriodClassVO versusPeriodClassVO, SessionInfoVO sessionInfoVO);

	List<DefaultMap<String>> getVersusPeriodClassDtlList(VersusPeriodClassVO versusPeriodClassVO, SessionInfoVO sessionInfoVO);

}
