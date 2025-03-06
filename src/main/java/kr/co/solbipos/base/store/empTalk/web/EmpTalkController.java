package kr.co.solbipos.base.store.empTalk.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.adi.board.board.service.BoardVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuSelProdVO;
import kr.co.solbipos.base.store.empTalk.service.EmpTalkService;
import kr.co.solbipos.base.store.empTalk.service.EmpTalkVO;
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

/**
 * @Class Name : EmpTalkController.java
 * @Description : 기초관리 > 매장관리 > 키오스크 직원대화
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.02.12  김유승      최초생성
 *
 * @author 링크 WEB개발팀 김유승
 * @since 2025.02.12
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/store/empTalk")
public class EmpTalkController {

    private final SessionService sessionService;
    private final EmpTalkService empTalkService;
    private final CmmEnvUtil cmmEnvUtil;

    public EmpTalkController(SessionService sessionService, EmpTalkService empTalkService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.empTalkService = empTalkService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/empTalk/view.sb", method = RequestMethod.GET)
    public String empStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/store/empTalk/empTalk";
    }

    /**
     * 키오스크 직원대화 관리 - 조회
     *
     * @param   empTalkVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 02. 12.
     */
    @RequestMapping(value = "/empTalk/getEmpTalkList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpTalkList(EmpTalkVO empTalkVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empTalkService.getEmpTalkList(empTalkVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empTalkVO);
    }

    /**
     * 키오스크 직원대화 관리 - 저장
     *
     * @param   request
     * @param   response
     * @param   empTalkVOs
     * @param   model Model
     * @return  Result
     * @author  김유승
     * @since   2025. 02. 12.
     */
    @RequestMapping(value = "/empTalk/saveEmpTalk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveEmpTalk(@RequestBody EmpTalkVO[] empTalkVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = empTalkService.saveEmpTalk(empTalkVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 직원대화 관리 - 기준매장 상용구 적용
     *
     * @param   request
     * @param   response
     * @param   empTalkVO
     * @param   model Model
     * @return  Result
     * @author  김유승
     * @since   2025. 02. 12.
     */
    @RequestMapping(value = "/empTalk/empTalkRegStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result empTalkRegStore(@RequestBody EmpTalkVO empTalkVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empTalkService.empTalkRegStore(empTalkVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
