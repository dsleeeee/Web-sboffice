package kr.co.solbipos.sale.anals.versusPeriod.cls.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface VersusPeriodClassService {
	/** 대비기간매출분석 - 분류상품별 리스트 조회 */
    List<DefaultMap<String>> getVersusPeriodClassList(VersusPeriodClassVO versusPeriodClassVO, SessionInfoVO sessionInfoVO);
    /** 대비기간매출분석 - 분류상품별 리스트 상세 조회 */
	List<DefaultMap<String>> getVersusPeriodClassDtlList(VersusPeriodClassVO versusPeriodClassVO, SessionInfoVO sessionInfoVO);
	/** 대비기간매출분석 - 브랜드 코드 조회조건 */
	List<DefaultMap<String>> getBrandCdList(VersusPeriodClassVO versusPeriodClassVO, SessionInfoVO sessionInfoVO);

}
