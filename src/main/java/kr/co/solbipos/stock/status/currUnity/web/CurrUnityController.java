package kr.co.solbipos.stock.status.currUnity.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.common.data.structure.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.status.currUnity.service.CurrUnityService;
import kr.co.solbipos.stock.status.currUnity.service.CurrUnityVO;

@Controller
@RequestMapping("/stock/status/currUnity")
public class CurrUnityController {
	private final SessionService sessionService;
	private final CurrUnityService currUnityService;

	@Autowired
	public CurrUnityController(SessionService sessionService, CurrUnityService currUnityService) {
		this.sessionService = sessionService;
		this.currUnityService = currUnityService;
	}

	@RequestMapping(value = "/prod/list.sb", method = RequestMethod.GET)
	public String getCurrUnityView(HttpServletRequest request, HttpServletResponse response, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		return "stock/status/currUnity/currUnity";
	}

	/** 본사매장통합현재고 - 본사매장통합현재고 리스트 조회 */
	@ResponseBody
	@RequestMapping(value = "/prod/getCurrUnityList.sb", method = RequestMethod.POST)
	public Result getCurrUnityList(HttpServletRequest request, HttpServletResponse response, CurrUnityVO currUnityVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = currUnityService.getCurrUnityList(currUnityVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, currUnityVO);
	}

	/** 본사매장통합현재고 - 본사매장통합현재고 본사 상세 리스트 조회 */
	@ResponseBody
	@RequestMapping(value = "/prod/getCurrUnityHqDtlList.sb", method = RequestMethod.POST)
	public Result getCurrUnityHqDtlList(HttpServletRequest request, HttpServletResponse response, CurrUnityVO currUnityVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = currUnityService.getCurrUnityHqDtlList(currUnityVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, currUnityVO);
	}

	/** 본사매장통합현재고 - 본사매장통합현재고 매장 상세 리스트 조회 */
	@ResponseBody
	@RequestMapping(value = "/prod/getCurrUnityStoreDtlList.sb", method = RequestMethod.POST)
	public Result getCurrUnityStoreDtlList(HttpServletRequest request, HttpServletResponse response, CurrUnityVO currUnityVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = currUnityService.getCurrUnityStoreDtlList(currUnityVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, currUnityVO);
	}
	
	/** 본사매장통합현재고 - 본사매장통합현재고 전체 엑셀 리스트 조회 */
	@ResponseBody
	@RequestMapping(value = "/prod/getCurrUnityExcelList.sb", method = RequestMethod.POST)
	public Result getCurrUnityExcelList(HttpServletRequest request, HttpServletResponse response, CurrUnityVO currUnityVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = currUnityService.getCurrUnityExcelList(currUnityVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, currUnityVO);
	}
	
	/** 본사매장통합현재고 - 본사매장통합현재고 본사 상세 전체 엑셀 리스트 조회 */
	@ResponseBody
	@RequestMapping(value = "/prod/getCurrUnityHqDtlExcelList.sb", method = RequestMethod.POST)
	public Result getCurrUnityHqDtlExcelList(HttpServletRequest request, HttpServletResponse response, CurrUnityVO currUnityVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = currUnityService.getCurrUnityHqDtlExcelList(currUnityVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, currUnityVO);
	}
	
	/** 본사매장통합현재고 - 본사매장통합현재고 매장 상세 전체 엑셀 리스트 조회 */
	@ResponseBody
	@RequestMapping(value = "/prod/getCurrUnityStoreDtlExcelList.sb", method = RequestMethod.POST)
	public Result getCurrUnityStoreDtlExcelList(HttpServletRequest request, HttpServletResponse response, CurrUnityVO currUnityVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = currUnityService.getCurrUnityStoreDtlExcelList(currUnityVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, currUnityVO);
	}
}
