package kr.co.solbipos.base.prod.prodOption.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodOption.service.ProdOptionService;
import kr.co.solbipos.base.prod.prodOption.service.ProdOptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : ProdOptionController.java
 * @Description : 기초관리 - 상품관리 - 옵션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.19  권지현       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/prodOption")
public class ProdOptionController {

    private final SessionService sessionService;
    private final ProdOptionService prodOptionService;

    @Autowired
    MessageService messageService;

    public ProdOptionController(SessionService sessionService, ProdOptionService prodOptionService) {
        this.sessionService = sessionService;
        this.prodOptionService = prodOptionService;
    }


    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping(value = "/prodOption/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "base/prod/prodOption/prodOption";
    }

    /**
     * 옵션그룹조회
     *
     * @param prodOptionVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodOption/getProdOptionGroup.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdOptionGroup(ProdOptionVO prodOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodOptionService.getProdOptionGroup(prodOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodOptionVO);
    }

    /**
     * 옵션그룹저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param prodOptionVOs ProdOptionVO[]
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2022.12.19
     */
    @RequestMapping(value = "/prodOption/saveProdOptionGroup.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProdOptionGroup(@RequestBody ProdOptionVO[] prodOptionVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = prodOptionService.saveProdOptionGroup(prodOptionVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 옵션속성조회
     *
     * @param prodOptionVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodOption/getProdOptionVal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdOptionVal(ProdOptionVO prodOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodOptionService.getProdOptionVal(prodOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodOptionVO);
    }

    /**
     * 옵션속성저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param prodOptionVOs ProdOptionVO[]
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2022.12.19
     */
    @RequestMapping(value = "/prodOption/saveProdOptionVal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProdOptionVal(@RequestBody ProdOptionVO[] prodOptionVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = prodOptionService.saveProdOptionVal(prodOptionVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
