package kr.co.solbipos.sale.status.dc.dcfg.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DcDcfgService {
    /** 할인구분별  탭- 리스트 조회 */
    List<DefaultMap<String>> getDcDcfgList(DcDcfgVO dcDcfgVO, SessionInfoVO sessionInfoVO);

    /** 할인구분별  탭- 엑셀 리스트 조회 */
    List<DefaultMap<String>> getDcDcfgExcelList(DcDcfgVO dcDcfgVO, SessionInfoVO sessionInfoVO);

    /** 할인구분별 탭 - 리스트 상세 조회 */
	List<DefaultMap<String>> getDcDcfgDtlList(DcDcfgVO dcDcfgVO, SessionInfoVO sessionInfoVO);

	/** 할인구분별 탭 - 할인유형 콤보박스 리스트 조회 */
	List<DefaultMap<String>> getDcNmlList(DcDcfgVO dcDcfgVO, SessionInfoVO sessionInfoVO);

}
