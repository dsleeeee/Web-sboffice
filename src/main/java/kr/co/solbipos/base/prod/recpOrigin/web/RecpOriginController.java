package kr.co.solbipos.base.prod.recpOrigin.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.recpOrigin.service.RecpOriginService;
import kr.co.solbipos.base.prod.recpOrigin.service.RecpOriginVO;
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
@RequestMapping("/base/prod/recpOrigin")
public class RecpOriginController {

    private final SessionService sessionService;
    private final RecpOriginService recpOriginService;

    /**
     * Constructor Injection
     */
    @Autowired
    public RecpOriginController(SessionService sessionService, RecpOriginService recpOriginService) {
        this.sessionService = sessionService;
        this.recpOriginService = recpOriginService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/recpOrigin/list.sb", method = RequestMethod.GET)
    public String recpOriginView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/prod/recpOrigin/recpOrigin";
    }

    /**
     * 원산지관리 조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 10.
     */
    @RequestMapping(value = "/recpOrigin/getRecpOriginList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpOriginList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getRecpOriginList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 브랜드 콤보박스 리스트 조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021. 03. 18.
     */
    @RequestMapping(value = "/recpOrigin/getBrandComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBrandComboList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        recpOriginVO.setHqOfficeCd(request.getParameter("hqOfficeCd"));

        List<DefaultMap<Object>> result = recpOriginService.getBrandComboList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 원산지관리 저장
     *
     * @param recpOriginVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 10.
     */
    @RequestMapping(value = "/recpOrigin/getRecpOriginSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpOriginSave(@RequestBody RecpOriginVO[] recpOriginVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = recpOriginService.getRecpOriginSave(recpOriginVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 재료-상품 조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 10.
     */
    @RequestMapping(value = "/recpOrigin/getRecpOriginDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpOriginDetailList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getRecpOriginDetailList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 재료-상품 등록 팝업 - 상품조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 13.
     */
    @RequestMapping(value = "/recpProd/getRecpProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpProdList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getRecpProdList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 재료-상품 등록 팝업 - 재료-상품 저장
     *
     * @param recpOriginVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 13.
     */
    @RequestMapping(value = "/recpOrigin/getRecpOriginDetailSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpOriginDetailSave(@RequestBody RecpOriginVO[] recpOriginVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = recpOriginService.getRecpOriginDetailSave(recpOriginVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}