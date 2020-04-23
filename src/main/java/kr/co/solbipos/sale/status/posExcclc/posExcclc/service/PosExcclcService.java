package kr.co.solbipos.sale.status.posExcclc.posExcclc.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;

import java.util.List;

public interface PosExcclcService {

	/** POS정산내역 - POS정산내역 리스트 조회 */
	List<DefaultMap<String>> getPosExcclcList(PosExcclcVO posExcclcVO, SessionInfoVO sessionInfoVO);

    /** POS정산내역 - POS정산내역 세부 리스트 조회 */
    DefaultMap<String> getPosExcclcDetailInfo(PosExcclcVO posExcclcVO);
    
    /** POS정산내역 - POS정산내역 전체 엑셀 리스트 조회 */
    List<DefaultMap<String>> getPosExcclcExcelList(PosExcclcVO posExcclcVO, SessionInfoVO sessionInfoVO);
}
