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

	/** 반품현황 > 상품별 반품현황탭 - 조회 */
	List<DefaultMap<String>> getRtnStatusProdList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);

	/** 반품현황 > 상품별 반품현황탭 - 엑셀 조회 */
	List<DefaultMap<String>> getRtnStatusProdExcelList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);

	/** 반품현황 - 일자별 엑셀 전체 리스트 조회 */
    List<DefaultMap<String>> getRtnstatusDayExcelList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);
    
    /** 반품현황 - 일자별 엑셀 전체 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnstatusDayDtlExcelList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);
    
    /** 반품현황 - 포스별 엑셀 전체 상세 리스트 조회 */
	List<DefaultMap<String>>getRtnStatusPosDtlExcelList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);

	/** 영수증별 상세 - 결제수단 컬럼 리스트 조회 */
	List<DefaultMap<String>> getPayColAddList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);

	/** 영수증별 상세 - 할인 컬럼 리스트 조회 */
	List<DefaultMap<String>> getDcColList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);

	/** 반품현황 - 객수 컬럼 리스트 조회 */
	List<DefaultMap<String>> getGuestColList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);

	/** 반품현황 - 영수증별 리스트 조회 */
	List<DefaultMap<String>> getRtnstatusBillList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO);

}
