package kr.co.solbipos.sale.dlvr.dlvrRate.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.dlvr.dlvrRate.service.DlvrRateService;
import kr.co.solbipos.sale.dlvr.dlvrRate.service.DlvrRateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @Class Name : DlvrRateController.java
 * @Description : 맘스터치 > 점포매출 > 배달방문비중
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/dlvr/dlvrRate")
public class DlvrRateController {

    private final SessionService sessionService;
    private final DlvrRateService dlvrRateService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DlvrRateController(SessionService sessionService, DlvrRateService dlvrRateService) {
        this.sessionService = sessionService;
        this.dlvrRateService = dlvrRateService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dlvrRate/list.sb", method = RequestMethod.GET)
    public String dlvrRateView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/dlvr/dlvrRate/dlvrRate";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dlvrRateVO
     * @return  String
     * @author  권지현
     * @since   2022.10.14
     */
    @RequestMapping(value = "/dlvrRate/getDlvrRateList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrRateList(HttpServletRequest request, HttpServletResponse response, Model model, DlvrRateVO dlvrRateVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dlvrRateService.getDlvrRateList(dlvrRateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dlvrRateVO);
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dlvrRateVO
     * @return  String
     * @author  권지현
     * @since   2022.10.14
     */
    @RequestMapping(value = "/dlvrRate/getDlvrRateExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrRateExcelList(HttpServletRequest request, HttpServletResponse response, Model model, DlvrRateVO dlvrRateVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dlvrRateService.getDlvrRateExcelList(dlvrRateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dlvrRateVO);
    }

}