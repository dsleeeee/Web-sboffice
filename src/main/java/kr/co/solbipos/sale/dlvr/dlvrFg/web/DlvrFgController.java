package kr.co.solbipos.sale.dlvr.dlvrFg.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.dlvr.dlvrFg.service.DlvrFgService;
import kr.co.solbipos.sale.dlvr.dlvrFg.service.DlvrFgVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/sale/dlvr/dlvrFg")
public class DlvrFgController {

    private final SessionService sessionService;
    private final DlvrFgService dlvrFgService;

    public DlvrFgController(SessionService sessionService, DlvrFgService dlvrFgService) {
        this.sessionService = sessionService;
        this.dlvrFgService = dlvrFgService;
    }

    /**
     * 배달구분 콤보박스 데이터 hq/ms 113
     *
     * @param dlvrFgVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.05.24
     */
    @RequestMapping(value = "/dlvrFgProd/getDlvrFgData.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrFg(DlvrFgVO dlvrFgVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = dlvrFgService.getDlvrFgData(dlvrFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dlvrFgVO);
    }

    /**
     * 상품별탭 - 유형별
     *
     * @param dlvrFgVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.05.21
     */
    @RequestMapping(value = "/dlvrFgProd/getOrderFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderFg(DlvrFgVO dlvrFgVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dlvrFgService.getOrderFg(dlvrFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dlvrFgVO);
    }

    /**
     * 상품별탭 - 상품별
     *
     * @param dlvrFgVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.05.25
     */
    @RequestMapping(value = "/dlvrFgProd/getProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProd(DlvrFgVO dlvrFgVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dlvrFgService.getProd(dlvrFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dlvrFgVO);
    }

    /**
     * 상품별탭 - 상품별(상세)
     *
     * @param dlvrFgVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.05.25
     */
    @RequestMapping(value = "/dlvrFgProd/getProdDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdDtl(DlvrFgVO dlvrFgVO, HttpServletRequest request,
                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dlvrFgService.getProdDtl(dlvrFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dlvrFgVO);
    }

    /**
     * 상품-영수별매출상세
     *
     * @param dlvrFgVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021.07.29
     */
    @RequestMapping(value = "/dlvrFgProd/getSaleDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleDtl(DlvrFgVO dlvrFgVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dlvrFgService.getSaleDtl(dlvrFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dlvrFgVO);
    }
}