package kr.co.solbipos.sale.anals.abc.abc.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface AbcService {
	
	/**상픔ABC분석 - 상픔ABC분석 리스트 조회   */
    List<DefaultMap<String>> getAbcList(AbcVO abcVO, SessionInfoVO sessionInfoVO);
   
}
