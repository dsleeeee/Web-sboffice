package kr.co.solbipos.base.prod.prodBatchChange.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodBatchChange.service.ProdBatchChangeService;
import kr.co.solbipos.base.prod.prodBatchChange.service.ProdBatchChangeVO;
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
@RequestMapping("/base/prod/prodBatchChange")
public class ProdBatchChangeController {

    private final SessionService sessionService;
    private final ProdBatchChangeService prodBatchChangeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdBatchChangeController(SessionService sessionService, ProdBatchChangeService prodBatchChangeService) {
        this.sessionService = sessionService;
        this.prodBatchChangeService = prodBatchChangeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodBatchChange/list.sb", method = RequestMethod.GET)
    public String prodBatchChangeView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/prod/prodBatchChange/prodBatchChange";
    }

    /**
     * 상품정보일괄변경 조회
     *
     * @param prodBatchChangeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 28.
     */
    @RequestMapping(value = "/prodBatchChange/getProdBatchChangeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdBatchChangeList(ProdBatchChangeVO prodBatchChangeVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodBatchChangeService.getProdBatchChangeList(prodBatchChangeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodBatchChangeVO);
    }

    /**
     * 상품정보일괄변경 저장
     *
     * @param prodBatchChangeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 28.
     */
    @RequestMapping(value = "/prodBatchChange/getProdBatchChangeSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdBatchChangeSave(@RequestBody ProdBatchChangeVO[] prodBatchChangeVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodBatchChangeService.getProdBatchChangeSave(prodBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}