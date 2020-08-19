package kr.co.solbipos.sale.status.rtnStatus.day.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface RtnStatusDayService {
    /** 반품현황 - 일자별 리스트 조회 */
    List<DefaultMap<String>> getRtnStatusDayList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);

    /** 반품현황 - 일자별 상세 리스트 조회 */
	List<DefaultMap<String>> getRtnStatusDayDtlList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);

	/** 반품현황 - 포스볗 상세 리스트 조회 */
	List<DefaultMap<String>> getRtnStatusPosDtlList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);

	/** 반품현황 - 포스별 상세 리스트 조회 */
	List<DefaultMap<String>> getRtnStatusProdList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);
	
	/** 반품현황 - 일자별 엑셀 전체 리스트 조회 */
    List<DefaultMap<String>> getRtnstatusDayExcelList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);
    
    /** 반품현황 - 일자별 엑셀 전체 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnstatusDayDtlExcelList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);
    
    /** 반품현황 - 포스별 엑셀 전체 상세 리스트 조회 */
	List<DefaultMap<String>>getRtnStatusPosDtlExcelList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);
	
	/** 반품현황 - 포스별 상세 리스트 조회 */
	List<DefaultMap<String>> getRtnStatusProdExcelList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);

}
