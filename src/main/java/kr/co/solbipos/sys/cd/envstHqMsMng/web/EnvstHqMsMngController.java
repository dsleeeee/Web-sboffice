package kr.co.solbipos.sys.cd.envstHqMsMng.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.cd.envstHqMsMng.service.EnvstHqMsMngService;
import kr.co.solbipos.sys.cd.envstHqMsMng.service.EnvstHqMsMngVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : EnvstHqMsMngController.java
 * @Description : 시스템관리 > 코드관리 > 환경설정사용설정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.06  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.03.06
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sys/cd/envstHqMsMng")
public class EnvstHqMsMngController {

    private final EnvstHqMsMngService envstHqMsMngService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public EnvstHqMsMngController(EnvstHqMsMngService envstHqMsMngService, SessionService sessionService) {
        this.envstHqMsMngService = envstHqMsMngService;
        this.sessionService = sessionService;
    }

    /**
     * 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2023.03.06
     */
    @RequestMapping(value = "/envstHqMsMng/view.sb", method = RequestMethod.GET)
    public String envConfigView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/cd/envstHqMsMng/envstHqMsMng";
    }

    /**
     * 사용 환경설정
     * @param   request
     * @param   response
     * @param   envstHqMsMngVO
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2023.03.06
     */
    @RequestMapping(value = "/envstHqMsMng/getRegEnvstList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRegEnvstList(HttpServletRequest request, HttpServletResponse response,
                               EnvstHqMsMngVO envstHqMsMngVO, Model model) {

        List<DefaultMap<String>> list = envstHqMsMngService.getRegEnvstList(envstHqMsMngVO);

        return ReturnUtil.returnListJson(Status.OK, list, envstHqMsMngVO);

    }

    /**
     * 미사용 환경설정
     * @param   request
     * @param   response
     * @param   envstHqMsMngVO
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2023.03.06
     */
    @RequestMapping(value = "/envstHqMsMng/getNoRegEnvstList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNoRegEnvstList(HttpServletRequest request, HttpServletResponse response,
                               EnvstHqMsMngVO envstHqMsMngVO, Model model) {

        List<DefaultMap<String>> list = envstHqMsMngService.getNoRegEnvstList(envstHqMsMngVO);

        return ReturnUtil.returnListJson(Status.OK, list, envstHqMsMngVO);

    }

    /**
     * 사용 환경설정 등록
     * @param   request
     * @param   envstHqMsMngVOs
     * @return  Result
     * @author  권지현
     * @since   2023.03.06
     */
    @RequestMapping(value = "/envstHqMsMng/saveEnvstHqMsMng.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveEnvstHqMsMng(@RequestBody EnvstHqMsMngVO[] envstHqMsMngVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = envstHqMsMngService.saveEnvstHqMsMng(envstHqMsMngVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 미사용 환경설정 등록
     * @param   request
     * @param   envstHqMsMngVOs
     * @return  Result
     * @author  권지현
     * @since   2023.03.06
     */
    @RequestMapping(value = "/envstHqMsMng/deleteEnvstHqMsMng.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteEnvstHqMsMng(@RequestBody EnvstHqMsMngVO[] envstHqMsMngVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = envstHqMsMngService.deleteEnvstHqMsMng(envstHqMsMngVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
