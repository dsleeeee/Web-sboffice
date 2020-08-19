package kr.co.solbipos.stock.curr.hqCurr.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrService;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;

/**
 * @Class Name : HqCurrController.java
 * @Description : 재고관리 > 재고현황 > 현재고현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.30  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 10.30
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/curr/hqCurr")
public class HqCurrController {
    private final SessionService sessionService;
    private final HqCurrService hqCurrService;
    private final CmmEnvService cmmEnvService;

    @Autowired
    public HqCurrController(SessionService sessionService, HqCurrService hqCurrService, CmmEnvService cmmEnvService) {
        this.sessionService = sessionService;
        this.hqCurrService = hqCurrService;
        this.cmmEnvService = cmmEnvService;
    }

    /**
     * 현재고현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 30.
     */
    @RequestMapping(value = "/hqCurr/view.sb", method = RequestMethod.GET)
    public String hqCurrView(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 본사 환경설정 0003(레시피사용여부) 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("0003");
        String envst0003 = cmmEnvService.getHqEnvst(hqEnvstVO);

        // 본사 환경설정 0008(저울바코드구성) 조회
        hqEnvstVO.setEnvstCd("0008");
        String envst0008 = cmmEnvService.getHqEnvst(hqEnvstVO);

        model.addAttribute("envst0003", envst0003);
        model.addAttribute("envst0008", envst0008);

        return "stock/curr/hqCurr/hqCurr";
    }

    /**
     * 현재고현황 - 현재고현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqCurrVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 30.
     */
    @RequestMapping(value = "/hqCurr/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqCurrList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqCurrVO hqCurrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqCurrService.getHqCurrList(hqCurrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqCurrVO);
    }
    
    /**
     * 현재고현황 - 재고현황 팝업 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  박정은
     * @since   2020.04.13
     */
	@RequestMapping(value = "/hqCurr/getHqCurrDtlList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getCmmStockStatusList(HttpServletRequest request, HttpServletResponse response, HqCurrVO hqCurrVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = hqCurrService.gethqCurrDtlList(hqCurrVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, hqCurrVO);
	}
	
	/**
     * 현재고현황 - 현재고현황 엑셀 전체 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqCurrVO
     * @return  String
     * @author  박정은
     * @since   2020. 04. 21
     */
    @RequestMapping(value = "/hqCurr/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqCurrExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqCurrVO hqCurrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqCurrService.getHqCurrExcelList(hqCurrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqCurrVO);
    }
}
