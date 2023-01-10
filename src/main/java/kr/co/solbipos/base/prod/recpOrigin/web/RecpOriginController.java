package kr.co.solbipos.base.prod.recpOrigin.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.recpOrigin.service.RecpOriginService;
import kr.co.solbipos.base.prod.recpOrigin.service.RecpOriginVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
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
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : RecpOriginController.java
 * @Description : 기초관리 > 상품관리 > 원산지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2020.07.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/recpOrigin")
public class RecpOriginController {

    private final SessionService sessionService;
    private final RecpOriginService recpOriginService;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public RecpOriginController(SessionService sessionService, RecpOriginService recpOriginService, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.recpOriginService = recpOriginService;
        this.dayProdService = dayProdService;
        this.cmmEnvUtil = cmmEnvUtil;
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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        return "base/prod/recpOrigin/recpOriginTab";
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

    /**
     * 상품-원산지관리탭 - 조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 25.
     */
    @RequestMapping(value = "/prodRecpOrigin/getProdRecpOriginList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRecpOriginList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getProdRecpOriginList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 상품-원산지관리탭 - 재료 및 원산지 등록 조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 25.
     */
    @RequestMapping(value = "/prodRecpOrigin/getProdRecpOriginDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRecpOriginDetailList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getProdRecpOriginDetailList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 재료 및 원산지 등록 팝업 - 조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 25.
     */
    @RequestMapping(value = "/prodRecpOriginAdd/getProdRecpOriginAddList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRecpOriginAddList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getProdRecpOriginAddList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 재료 및 원산지 등록 팝업 - 저장
     *
     * @param recpOriginVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 25.
     */
    @RequestMapping(value = "/prodRecpOriginAdd/getProdRecpOriginAddSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRecpOriginAddSave(@RequestBody RecpOriginVO[] recpOriginVOs, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = recpOriginService.getProdRecpOriginAddSave(recpOriginVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 상품-원산지관리탭 - 저장
     *
     * @param recpOriginVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 10.
     */
    @RequestMapping(value = "/prodRecpOrigin/getProdRecpOriginSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRecpOriginSave(@RequestBody RecpOriginVO[] recpOriginVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = recpOriginService.getProdRecpOriginSave(recpOriginVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}