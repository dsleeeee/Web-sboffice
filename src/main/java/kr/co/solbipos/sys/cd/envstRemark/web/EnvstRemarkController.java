package kr.co.solbipos.sys.cd.envstRemark.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.cd.envstRemark.service.EnvstRemarkService;
import kr.co.solbipos.sys.cd.envstRemark.service.EnvstRemarkVO;
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
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : EnvstRemarkController.java
 * @Description : 시스템관리 > 코드관리 > 환경설정기능설명
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.03  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.03.03
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sys/cd/envstRemark")
public class EnvstRemarkController {

    private final EnvstRemarkService envstRemarkService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public EnvstRemarkController(EnvstRemarkService envstRemarkService, SessionService sessionService) {
        this.envstRemarkService = envstRemarkService;
        this.sessionService = sessionService;
    }

    /**
     * 환경설정기능설명 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2023.03.03
     */
    @RequestMapping(value = "/envstRemark/view.sb", method = RequestMethod.GET)
    public String envConfigView(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        List<DefaultMap<String>> envstGrpList = envstRemarkService.getEnvstGrpList();

        model.addAttribute("envstGrpList", convertToJson(envstGrpList)  );

        return "sys/cd/envstRemark/envstRemark";
    }

    /**
     * 환경설정관리 - 대표명칭 조회
     * @param   request
     * @param   response
     * @param   envstRemarkVO
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2023.03.03
     */
    @RequestMapping(value = "/envstRemark/envst/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvstList(HttpServletRequest request, HttpServletResponse response,
                               EnvstRemarkVO envstRemarkVO, Model model) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 대표명칭 코드목록 조회
        list = envstRemarkService.getEnvstList(envstRemarkVO);

        return ReturnUtil.returnListJson(Status.OK, list, envstRemarkVO);

    }

    /**
     * 환경설정관리 - 설명 저장
     * @param   request
     * @param   envstRemarkVO
     * @return  Result
     * @author  권지현
     * @since   2023.03.03
     */
    @RequestMapping(value = "/envstRemark/envst/saveEnvstRemark.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveEnvstRemark(@RequestBody EnvstRemarkVO envstRemarkVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = envstRemarkService.saveEnvstRemark(envstRemarkVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
