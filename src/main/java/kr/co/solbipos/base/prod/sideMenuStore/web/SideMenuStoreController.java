package kr.co.solbipos.base.prod.sideMenuStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.sideMenuStore.service.SideMenuStoreService;
import kr.co.solbipos.base.prod.sideMenuStore.service.SideMenuStoreVO;
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
 * @Class Name : SideMenuStoreController.java
 * @Description : 기초관리 > 상품관리2 > 매장별사이드관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.06.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/sideMenuStore")
public class SideMenuStoreController {

    private final SessionService sessionService;
    private final SideMenuStoreService sideMenuStoreService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public SideMenuStoreController(SessionService sessionService, SideMenuStoreService sideMenuStoreService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.sideMenuStoreService = sideMenuStoreService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/sideMenuStore/list.sb", method = RequestMethod.GET)
    public String sideMenuStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        /** 맘스터치 */
        // [1250 맘스터치] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
        }
        /** //맘스터치 */

        return "base/prod/sideMenuStore/sideMenuStoreTab";
    }

    /**
     * 선택분류(매장별) 탭 - 조회
     *
     * @param sideMenuStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 07.
     */
    @RequestMapping(value = "/sideMenuClassStore/getSideMenuClassStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuClassStoreList(SideMenuStoreVO sideMenuStoreVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuStoreService.getSideMenuClassStoreList(sideMenuStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuStoreVO);
    }

    /**
     * 선택분류(매장별) 탭 - 저장
     *
     * @param sideMenuStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 07.
     */
    @RequestMapping(value = "/sideMenuClassStore/getSideMenuClassStoreSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuClassStoreSave(@RequestBody SideMenuStoreVO[] sideMenuStoreVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuStoreService.getSideMenuClassStoreSave(sideMenuStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 선택분류(선택분류별) 탭 - 조회
     *
     * @param sideMenuStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 07.
     */
    @RequestMapping(value = "/sideMenuClass/getSideMenuClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuClassList(SideMenuStoreVO sideMenuStoreVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuStoreService.getSideMenuClassList(sideMenuStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuStoreVO);
    }

    /**
     * 선택상품(매장별) 탭 - 조회
     *
     * @param sideMenuStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 07.
     */
    @RequestMapping(value = "/sideMenuProdStore/getSideMenuProdStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuProdStoreList(SideMenuStoreVO sideMenuStoreVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuStoreService.getSideMenuProdStoreList(sideMenuStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuStoreVO);
    }

    /**
     * 선택상품(매장별) 탭 - 저장
     *
     * @param sideMenuStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 07.
     */
    @RequestMapping(value = "/sideMenuProdStore/getSideMenuProdStoreSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuProdStoreSave(@RequestBody SideMenuStoreVO[] sideMenuStoreVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuStoreService.getSideMenuProdStoreSave(sideMenuStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}