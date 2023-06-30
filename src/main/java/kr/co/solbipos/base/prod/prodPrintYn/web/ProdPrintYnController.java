package kr.co.solbipos.base.prod.prodPrintYn.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodPrintYn.service.ProdPrintYnService;
import kr.co.solbipos.base.prod.prodPrintYn.service.ProdPrintYnVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : ProdPrintYnController.java
 * @Description : 기초관리 > 상품관리2 > 출력여부관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.06.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/prodPrintYn")
public class ProdPrintYnController {

    private final SessionService sessionService;
    private final ProdPrintYnService prodPrintYnService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdPrintYnController(SessionService sessionService, ProdPrintYnService prodPrintYnService) {
        this.sessionService = sessionService;
        this.prodPrintYnService = prodPrintYnService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodPrintYn/list.sb", method = RequestMethod.GET)
    public String prodPrintYnView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/prod/prodPrintYn/prodPrintYnTab";
    }

    /**
     * 옵션관리 탭 - 조회
     *
     * @param prodPrintYnVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 28.
     */
    @RequestMapping(value = "/prodOptionPrintYn/getProdOptionPrintYnList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdOptionPrintYnList(ProdPrintYnVO prodPrintYnVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodPrintYnService.getProdOptionPrintYnList(prodPrintYnVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodPrintYnVO);
    }

    /**
     * 옵션관리 탭 - 저장
     *
     * @param prodPrintYnVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 28.
     */
    @RequestMapping(value = "/prodOptionPrintYn/getProdOptionPrintYnSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdOptionPrintYnSave(@RequestBody ProdPrintYnVO[] prodPrintYnVOs, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodPrintYnService.getProdOptionPrintYnSave(prodPrintYnVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사이드메뉴관리 탭 - 조회
     *
     * @param prodPrintYnVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 28.
     */
    @RequestMapping(value = "/sideMenuProdPrintYn/getSideMenuProdPrintYnList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuProdPrintYnList(ProdPrintYnVO prodPrintYnVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodPrintYnService.getSideMenuProdPrintYnList(prodPrintYnVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodPrintYnVO);
    }

    /**
     * 사이드메뉴관리 탭 - 저장
     *
     * @param prodPrintYnVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 28.
     */
    @RequestMapping(value = "/sideMenuProdPrintYn/getSideMenuProdPrintYnSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuProdPrintYnSave(@RequestBody ProdPrintYnVO[] prodPrintYnVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodPrintYnService.getSideMenuProdPrintYnSave(prodPrintYnVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}