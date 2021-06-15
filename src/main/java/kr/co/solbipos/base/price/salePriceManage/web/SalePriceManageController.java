package kr.co.solbipos.base.price.salePriceManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.salePriceManage.service.SalePriceManageService;
import kr.co.solbipos.base.price.salePriceManage.service.SalePriceManageVO;
import kr.co.solbipos.base.prod.prod.service.enums.PriceEnvFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

@Controller
@RequestMapping("/base/price/salePriceManage")
public class SalePriceManageController {

    private final SessionService sessionService;
    private final SalePriceManageService salePriceManageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public SalePriceManageController(SessionService sessionService, SalePriceManageService salePriceManageService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.salePriceManageService = salePriceManageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/salePriceManage/list.sb", method = RequestMethod.GET)
    public String salePriceManageView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 내점/배달/포장 가격관리 사용여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));
        }else{
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));
        }

        return "base/price/salePriceManage/salePriceManage";
    }

    /**
     * 판매가관리 조회
     *
     * @param salePriceManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 13.
     */
    @RequestMapping(value = "/salePriceManage/getSalePriceManageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceManageList(SalePriceManageVO salePriceManageVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = salePriceManageService.getSalePriceManageList(salePriceManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, salePriceManageVO);
    }

    /**
     * 판매가관리 저장
     *
     * @param salePriceManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 14.
     */
    @RequestMapping(value = "/salePriceManage/getSalePriceManageSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceManageSave(@RequestBody SalePriceManageVO[] salePriceManageVOs, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceManageService.getSalePriceManageSave(salePriceManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}