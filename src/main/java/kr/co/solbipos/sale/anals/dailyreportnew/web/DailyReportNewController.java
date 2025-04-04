package kr.co.solbipos.sale.anals.dailyreportnew.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.dailyreportnew.service.DailyReportNewService;
import kr.co.solbipos.sale.anals.dailyreportnew.service.DailyReportNewVO;

/**
 * @Class Name : DailyReportController.java
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
@Controller
@RequestMapping(value = "/sale/anals/dailyReportNew/")
public class DailyReportNewController {
	private final Logger 				LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService 		sessionService;
    private final DailyReportNewService	dailyReportNewService;
    private final CmmEnvUtil 			cmmEnvUtil;

    @Autowired
    public DailyReportNewController(SessionService sessionService, DailyReportNewService dailyReportNewService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService 		= sessionService;
        this.dailyReportNewService	= dailyReportNewService;
        this.cmmEnvUtil 			= cmmEnvUtil;
    }



	/**
	 * 영업일보 - 화면 이동 (매출관리 > 매출분석 > 영업일보)
	 * @param   request
	 * @param   response
	 * @param   model
	 * @return  String
	 * @author  조현수
	 * @since   2020.01.28
	*/
    @RequestMapping(value="view.sb", method=RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
    	return "sale/anals/dailyReportNew/dailyReport";
    }



    /**
	 * 영업일보 조회 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportNewVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.01.28
	*/
    @RequestMapping(value="report/list.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getReportList(DailyReportNewVO dailyReportNewVO, HttpServletRequest request) {
        SessionInfoVO		sessionInfoVO 	= sessionService.getSessionInfo(request);
        Map<String, Object> rtnMap 			= dailyReportNewService.getReportList(dailyReportNewVO, sessionInfoVO);

        return returnJson(Status.OK, rtnMap);

    }



    /**
	 * 영업일보 구성 조회 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportNewVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.02.05
	*/
    @RequestMapping(value="config/list.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getConfigList(DailyReportNewVO dailyReportNewVO, HttpServletRequest request) {
        SessionInfoVO		sessionInfoVO 	= sessionService.getSessionInfo(request);
        Map<String, Object> rtnMap 			= dailyReportNewService.getConfigList(dailyReportNewVO, sessionInfoVO);

        return returnJson(Status.OK, rtnMap);
        //return ReturnUtil.returnListJson(Status.OK, list, outstockReqDateVO);

    }



    /**
	 * 영업일보 구성 저장 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportNewVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.02.05
	*/
    @RequestMapping(value = "config/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveConfigList(@RequestBody DailyReportNewVO[] dailyReportNewVOs, HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO 	sessionInfoVO 	= sessionService.getSessionInfo(request);
        int 			result 			= dailyReportNewService.saveConfigList(dailyReportNewVOs, sessionInfoVO);
        return returnJson(Status.OK, result);
    }



    /**
	 * 결재라인 저장 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportNewVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.02.05
	*/
    @RequestMapping(value = "report/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePayLineList(@RequestBody DailyReportNewVO[] dailyReportNewVOs, HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO 	sessionInfoVO 	= sessionService.getSessionInfo(request);
        int 			result 			= dailyReportNewService.savePayLineList(dailyReportNewVOs, sessionInfoVO);
        return returnJson(Status.OK, result);
    }
}
