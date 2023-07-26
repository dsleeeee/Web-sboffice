package kr.co.solbipos.base.prod.storeProdPrintYn.web;

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
import kr.co.solbipos.base.prod.storeProdPrintYn.service.StoreProdPrintYnService;
import kr.co.solbipos.base.prod.storeProdPrintYn.service.StoreProdPrintYnVO;
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
 * @Class Name : StoreProdPrintYnController.java
 * @Description : 기초관리 > 상품관리2 > 매장별출력여부관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.24  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.07.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/storeProdPrintYn")
public class StoreProdPrintYnController {

    private final SessionService sessionService;
    private final StoreProdPrintYnService storeProdPrintYnService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreProdPrintYnController(SessionService sessionService, StoreProdPrintYnService storeProdPrintYnService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.storeProdPrintYnService = storeProdPrintYnService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/storeProdPrintYn/list.sb", method = RequestMethod.GET)
    public String storeProdPrintYnView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        /** 맘스터치 */
        // [1250 맘스터치] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
        }
        /** //맘스터치 */

        return "base/prod/storeProdPrintYn/storeProdPrintYnTab";
    }

    /**
     * 옵션관리 탭 - 조회
     *
     * @param storeProdPrintYnVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 07. 24.
     */
    @RequestMapping(value = "/storeProdOptionPrintYn/getStoreProdOptionPrintYnList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreProdOptionPrintYnList(StoreProdPrintYnVO storeProdPrintYnVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeProdPrintYnService.getStoreProdOptionPrintYnList(storeProdPrintYnVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeProdPrintYnVO);
    }

    /**
     * 옵션관리 탭 - 저장
     *
     * @param storeProdPrintYnVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 07. 24.
     */
    @RequestMapping(value = "/storeProdOptionPrintYn/getStoreProdOptionPrintYnSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreProdOptionPrintYnSave(@RequestBody StoreProdPrintYnVO[] storeProdPrintYnVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeProdPrintYnService.getStoreProdOptionPrintYnSave(storeProdPrintYnVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사이드메뉴관리 탭 - 조회
     *
     * @param storeProdPrintYnVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 07. 24.
     */
    @RequestMapping(value = "/storeSideMenuProdPrintYn/getStoreSideMenuProdPrintYnList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSideMenuProdPrintYnList(StoreProdPrintYnVO storeProdPrintYnVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeProdPrintYnService.getStoreSideMenuProdPrintYnList(storeProdPrintYnVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeProdPrintYnVO);
    }

    /**
     * 사이드메뉴관리 탭 - 저장
     *
     * @param storeProdPrintYnVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 07. 24.
     */
    @RequestMapping(value = "/storeSideMenuProdPrintYn/getStoreSideMenuProdPrintYnSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSideMenuProdPrintYnSave(@RequestBody StoreProdPrintYnVO[] storeProdPrintYnVOs, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeProdPrintYnService.getStoreSideMenuProdPrintYnSave(storeProdPrintYnVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}