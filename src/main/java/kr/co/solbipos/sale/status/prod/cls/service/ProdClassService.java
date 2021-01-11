package kr.co.solbipos.sale.status.prod.cls.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ProdClassService {
	/** 분류별상품탭 - 조회 */
    List<DefaultMap<String>> getProdClassList(ProdClassVO prodClassVO, SessionInfoVO sessionInfoVO);
    
    /** 분류별상품탭 - 엑셀 조회 */
    List<DefaultMap<String>> getProdClassExcelList(ProdClassVO prodClassVO, SessionInfoVO sessionInfoVO);
}