package kr.co.solbipos.base.prod.prod.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : ProdController.java
 * @Description : 기초관리 - 상품관리 - 상품조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  장혁수       최초생성
 * @ 2018.10.19  노현수       생성자 주입, 상품조회 관련 변경
 *
 * @author NHN한국사이버결제 KCP 장혁수
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/prod/prod")
public class ProdController {

    private final SessionService sessionService;
    private final ProdService prodService;

    /** Constructor Injection */
    @Autowired
    public ProdController(SessionService sessionService, ProdService prodService) {
        this.sessionService = sessionService;
        this.prodService = prodService;
    }

    /**
     * 상품조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "base/prod/prod/prod";
    }

    /**
     * 상품조회
     *
     * @param prodVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(ProdVO prodVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = prodService.list(prodVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodVO);
    }

    /**
     * 상품상세조회
     * @param prodVO ProdVO
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result detail(ProdVO prodVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        DefaultMap result = prodService.detail(prodVO, sessionInfoVO);
        List<DefaultMap<String>> list = prodService.unitstProdList(prodVO, sessionInfoVO);
        result.put("list", list);

        return returnJson(Status.OK, result);
    }
}
