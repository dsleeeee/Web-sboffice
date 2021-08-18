package kr.co.solbipos.base.store.emp.cardInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.cardInfo.service.EmpCardInfoService;
import kr.co.solbipos.base.store.emp.cardInfo.service.EmpCardInfoVO;
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
 * @Class Name : EmpCardInfoController.java
 * @Description : 기초관리 - 사원관리 - 사원카드정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/store/emp/cardInfo")
public class EmpCardInfoController {

    private final SessionService sessionService;
    private final EmpCardInfoService empCardInfoService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public EmpCardInfoController(SessionService sessionService, EmpCardInfoService empCardInfoService, CmmEnvUtil cmmEnvUtil){
        this.sessionService = sessionService;
        this.empCardInfoService = empCardInfoService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 08. 13.
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "base/store/emp/empCardInfo";
    }

    /**
     * 사원카드정보 조회
     * @param empCardInfoVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 08. 13.
     */
    @RequestMapping(value = "/getEmpCardInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpCardInfo(EmpCardInfoVO empCardInfoVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = empCardInfoService.getEmpCardInfo(empCardInfoVO, sessionInfoVO);

        return returnListJson(Status.OK, list, empCardInfoVO);
    }

    /**
     * 사원카드정보 전체 삭제
     * @param empCardInfoVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 08. 13.
     */
    @RequestMapping(value = "/deleteEmpCardInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteEmpCardInfo(@RequestBody EmpCardInfoVO empCardInfoVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empCardInfoService.deleteEmpCardInfo(empCardInfoVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사원카드정보 저장
     * @param empCardInfoVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 08. 13.
     */
    @RequestMapping(value = "/saveEmpCardInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveEmpCardInfo(@RequestBody EmpCardInfoVO[] empCardInfoVOs, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empCardInfoService.saveEmpCardInfo(empCardInfoVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
