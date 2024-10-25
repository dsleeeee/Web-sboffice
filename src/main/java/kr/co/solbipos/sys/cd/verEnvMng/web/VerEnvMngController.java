package kr.co.solbipos.sys.cd.verEnvMng.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.cd.envconfg.service.EnvConfgService;
import kr.co.solbipos.sys.cd.systemcd.service.SystemCdVO;
import kr.co.solbipos.sys.cd.verEnvMng.service.VerEnvMngService;
import kr.co.solbipos.sys.cd.verEnvMng.service.VerEnvMngVO;
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
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : VerEnvMngController.java
 * @Description : 시스템관리 > 코드관리 > 버전별환경설정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.10.23  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2024.10.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sys/cd/verEnvMng")
public class VerEnvMngController {

    private final VerEnvMngService verEnvMngService;
    private final SessionService sessionService;
    private final EnvConfgService envConfgService;

    /** Constructor Injection */
    @Autowired
    public VerEnvMngController(VerEnvMngService verEnvMngService, SessionService sessionService, EnvConfgService envConfgService) {
        this.verEnvMngService = verEnvMngService;
        this.sessionService = sessionService;
        this.envConfgService = envConfgService;
    }

    /**
     * 페이지 이동
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2024. 10. 23
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        //환경그룹 목록조회??
        model.addAttribute("envstGrpList", convertToJson(envConfgService.getEnvstGrpList()));

        return "sys/cd/verEnvMng/verEnvMng";
    }

    /**
     * 버전 리스트 조회
     * @param request
     * @param response
     * @param verEnvMngVO
     * @param model
     * @return
     * @author  이다솜
     * @since   2024. 10. 23
     */
    @RequestMapping(value = "/getVerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVerList(HttpServletRequest request, HttpServletResponse response, VerEnvMngVO verEnvMngVO, Model model) {

        // 버전 리스트 조회
        List<DefaultMap<String>> list = verEnvMngService.getVerList(verEnvMngVO);

        return ReturnUtil.returnListJson(Status.OK, list, verEnvMngVO);

    }

    /**
     * 버전 등록
     * @param verEnvMngVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2024. 10. 23
     */
    @RequestMapping(value = "/saveVer.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVer(@RequestBody VerEnvMngVO[] verEnvMngVOs, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = verEnvMngService.saveVer(verEnvMngVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 대표명칭 리스트 조회
     * @param request
     * @param response
     * @param verEnvMngVO
     * @param model
     * @return
     * @author  이다솜
     * @since   2024. 10. 23
     */
    @RequestMapping(value = "/getEnvstList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvstList(HttpServletRequest request, HttpServletResponse response, VerEnvMngVO verEnvMngVO, Model model) {

        // 대표명칭 리스트 조회
        List<DefaultMap<String>> list = verEnvMngService.getEnvstList(verEnvMngVO);

        return returnListJson(Status.OK, list, verEnvMngVO);

    }

    /**
     * 대표명칭 사용여부 저장
     * @param verEnvMngVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2024. 10. 23
     */
    @RequestMapping(value = "/saveEnvst.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveEnvst(@RequestBody VerEnvMngVO[] verEnvMngVOs, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = verEnvMngService.saveEnvst(verEnvMngVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 세부명칭 리스트 조회
     * @param request
     * @param response
     * @param verEnvMngVO
     * @param model
     * @return
     * @author  이다솜
     * @since   2024. 10. 23
     */
    @RequestMapping(value = "/getEnvstDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvstDtlList(HttpServletRequest request, HttpServletResponse response, VerEnvMngVO verEnvMngVO, Model model) {

        // 세부명칭 리스트 조회
        List<DefaultMap<String>> list = verEnvMngService.getEnvstDtlList(verEnvMngVO);

        return returnListJson(Status.OK, list, verEnvMngVO);

    }

    /**
     * 세부명칭 초기값여부 저장
     * @param verEnvMngVOs
     * @param request
     * @param response
     * @param model
     * @param model
     * @return
     * @author  이다솜
     * @since   2024. 10. 23
     */
    @RequestMapping(value = "/saveEnvstDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveEnvstDtl(@RequestBody VerEnvMngVO[] verEnvMngVOs, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = verEnvMngService.saveEnvstDtl(verEnvMngVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
