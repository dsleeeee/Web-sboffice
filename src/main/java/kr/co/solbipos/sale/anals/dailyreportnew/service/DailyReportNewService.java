package kr.co.solbipos.sale.anals.dailyreportnew.service;

import java.util.List;
import java.util.Map;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : DailyReportService.java
 * @Description : 매출관리 > 매출분석 > 영업일보
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.28  조현수       최초생성
 * @ 2020.
 *
 * @author NHN한국사이버결제 KCP
 * @since 2020. 01.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DailyReportNewService {

    /**
	 * 영업일보 조회 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportNewVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.01.28
	*/
  //List<DefaultMap<String>> 	getDailyReport		(DailyReportVO dailyReportNewVO, SessionInfoVO sessionInfoVO);
  //DefaultMap<String> 	getDailyReport		(DailyReportNewVO dailyReportNewVO, SessionInfoVO sessionInfoVO);
	Map<String, Object>	getReportList		(DailyReportNewVO dailyReportNewVO, SessionInfoVO sessionInfoVO);



    /**
	 * 영업일보 구성 조회 (결재라인 + 영업일보 구성) (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportNewVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.01.28
	*/
	Map<String, Object>	getConfigList		(DailyReportNewVO dailyReportNewVO, SessionInfoVO sessionInfoVO);



    /**
	 * 영업일보 구성 저장 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportNewVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.02.05
	*/
	int 				saveConfigList		(DailyReportNewVO[] dailyReportNewVOs, SessionInfoVO sessionInfoVO);


    /**
	 * 결재라인 저장 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportNewVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.02.05
	*/
	int 				savePayLineList		(DailyReportNewVO[] dailyReportNewVOs, SessionInfoVO sessionInfoVO);
}
