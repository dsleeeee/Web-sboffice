package kr.co.solbipos.sale.status.prod.payFg.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ProdPayFgService {
	/** 상품별 매출 - 결제수단별  리스트 조회 */
    List<DefaultMap<String>> getProdPayFgList(ProdPayFgVO prodPayFgVO, SessionInfoVO sessionInfoVO);
    
    /** 결제수단별탭 - 조회 */
    List<DefaultMap<String>> getPayColList(ProdPayFgVO prodPayFgVO, SessionInfoVO sessionInfoVO);
    
    /** 결제수단별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getProdPayFgExcelList(ProdPayFgVO prodPayFgVO, SessionInfoVO sessionInfoVO);
}