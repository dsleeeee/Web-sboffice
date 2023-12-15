package kr.co.solbipos.base.multilingual.fnkeyCmNmcd.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.captionMsg.service.CaptionMsgVO;
import kr.co.solbipos.base.multilingual.fnkeyCmNmcd.service.FnkeyCmNmcdService;
import kr.co.solbipos.base.multilingual.fnkeyCmNmcd.service.FnkeyCmNmcdVO;
import kr.co.solbipos.base.multilingual.kioskSideOption.service.KioskSideOptionVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : FnkeyCmNmcdContoller.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(기능키/공통코드)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.12  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.12.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/multilingual/fnkeyCmNmcd")
public class FnkeyCmNmcdController {

    private final SessionService sessionService;
    private final FnkeyCmNmcdService fnkeyCmNmcdService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public FnkeyCmNmcdController(SessionService sessionService, FnkeyCmNmcdService fnkeyCmNmcdService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.fnkeyCmNmcdService = fnkeyCmNmcdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 12. 12.
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(CaptionMsgVO captionMsgVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 공통코드 탭 공통코드 그룹코드 조회(콤보박스용)
        model.addAttribute("nmcodeGrpCdList", cmmCodeUtil.assmblObj(fnkeyCmNmcdService.getNmcodeGrpCdList(), "name", "value", UseYn.ALL));

        return "base/multilingual/fnkeyCmNmcd/fnkeyCmNmcdTab";
    }

    /**
     * 기능키(공통) 탭 리스트 조회
     * @param fnkeyCmNmcdVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2023. 12. 12.
     */
    @RequestMapping(value = "/getCmPosFnkeyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCmPosFnkeyList(FnkeyCmNmcdVO fnkeyCmNmcdVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = fnkeyCmNmcdService.getCmPosFnkeyList(fnkeyCmNmcdVO, sessionInfoVO);

        return returnListJson(Status.OK, list, fnkeyCmNmcdVO);
    }

    /**
     * 기능키(공통) 탭 영문, 중문, 일문 저장
     * @param fnkeyCmNmcdVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2023. 12. 12.
     */
    @RequestMapping(value = "/saveCmPosFnkey.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCmPosFnkey(@RequestBody FnkeyCmNmcdVO[] fnkeyCmNmcdVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = fnkeyCmNmcdService.saveCmPosFnkey(fnkeyCmNmcdVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 기능키(매장) 탭 리스트 조회
     * @param fnkeyCmNmcdVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2023. 12. 12.
     */
    @RequestMapping(value = "/getStoreFnkeyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreFnkeyList(FnkeyCmNmcdVO fnkeyCmNmcdVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = fnkeyCmNmcdService.getStoreFnkeyList(fnkeyCmNmcdVO, sessionInfoVO);

        return returnListJson(Status.OK, list, fnkeyCmNmcdVO);
    }

    /**
     * 기능키(매장) 탭 영문, 중문, 일문 저장
     * @param fnkeyCmNmcdVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2023. 12. 12.
     */
    @RequestMapping(value = "/saveStoreFnkey.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreFnkey(@RequestBody FnkeyCmNmcdVO[] fnkeyCmNmcdVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = fnkeyCmNmcdService.saveStoreFnkey(fnkeyCmNmcdVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 공통코드 탭 리스트 조회
     * @param fnkeyCmNmcdVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2023. 12. 12.
     */
    @RequestMapping(value = "/getCmNmcdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCmNmcdList(FnkeyCmNmcdVO fnkeyCmNmcdVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = fnkeyCmNmcdService.getCmNmcdList(fnkeyCmNmcdVO, sessionInfoVO);

        return returnListJson(Status.OK, list, fnkeyCmNmcdVO);
    }

    /**
     * 공통코드 탭 영문, 중문, 일문 저장
     * @param fnkeyCmNmcdVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveCmNmcd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCmNmcd(@RequestBody FnkeyCmNmcdVO[] fnkeyCmNmcdVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = fnkeyCmNmcdService.saveCmNmcd(fnkeyCmNmcdVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
