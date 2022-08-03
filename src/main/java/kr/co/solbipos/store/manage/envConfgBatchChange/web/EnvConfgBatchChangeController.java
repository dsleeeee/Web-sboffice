package kr.co.solbipos.store.manage.envConfgBatchChange.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.envConfgBatchChange.service.EnvConfgBatchChangeService;
import kr.co.solbipos.store.manage.envConfgBatchChange.service.EnvConfgBatchChangeVO;
import kr.co.solbipos.sys.cd.envconfg.service.EnvConfgService;
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
 * @Class Name : envConfgBatchChange.java
 * @Description : 기초관리 > 매장정보관리 > 환경변수일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.02.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.02.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/store/manage/envConfgBatchChange")
public class EnvConfgBatchChangeController {

    private final SessionService sessionService;
    private final EnvConfgBatchChangeService envConfgBatchChangeService;
    private final EnvConfgService envConfgService;

    /**
     * Constructor Injection
     */
    @Autowired
    public EnvConfgBatchChangeController(SessionService sessionService, EnvConfgBatchChangeService envConfgBatchChangeService, EnvConfgService envConfgService) {
        this.sessionService = sessionService;
        this.envConfgBatchChangeService = envConfgBatchChangeService;
        this.envConfgService = envConfgService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/envConfgBatchChange/list.sb", method = RequestMethod.GET)
    public String envConfgBatchChangeView(HttpServletRequest request, HttpServletResponse response, Model model) {

        List<DefaultMap<String>> envstGrpList = envConfgService.getEnvstGrpList();

        model.addAttribute("envstGrpList", convertToJson(envstGrpList)  );

        return "store/manage/envConfgBatchChange/envConfgBatchChange";
    }

    /**
     * 본사환경탭 - 조회
     *
     * @param envConfgBatchChangeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 02. 17.
     */
    @RequestMapping(value = "/envConfgBatchChangeHq/getEnvConfgBatchChangeHqList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvConfgBatchChangeHqList(EnvConfgBatchChangeVO envConfgBatchChangeVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = envConfgBatchChangeService.getEnvConfgBatchChangeHqList(envConfgBatchChangeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, envConfgBatchChangeVO);
    }

    /**
     * 환경설정 조회 팝업 - 조회
     *
     * @param envConfgBatchChangeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 02. 17.
     */
    @RequestMapping(value = "/searchEnvConfg/getSearchEnvConfgList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchEnvConfgList(EnvConfgBatchChangeVO envConfgBatchChangeVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = envConfgBatchChangeService.getSearchEnvConfgList(envConfgBatchChangeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, envConfgBatchChangeVO);
    }

    /**
     * 본사환경탭 - 환경변수값 콤보박스 조회
     *
     * @param envConfgBatchChangeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 02. 17.
     */
    @RequestMapping(value = "/envConfgBatchChangeHq/getEnvstValComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvstValComboList(EnvConfgBatchChangeVO envConfgBatchChangeVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = envConfgBatchChangeService.getEnvstValComboList(envConfgBatchChangeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, envConfgBatchChangeVO);
    }

    /**
     * 본사환경탭 - 저장
     *
     * @param envConfgBatchChangeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 02. 17.
     */
    @RequestMapping(value = "/envConfgBatchChangeHq/getEnvConfgBatchChangeHqSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvConfgBatchChangeHqSave(@RequestBody EnvConfgBatchChangeVO[] envConfgBatchChangeVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = envConfgBatchChangeService.getEnvConfgBatchChangeHqSave(envConfgBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장환경탭 - 조회
     *
     * @param envConfgBatchChangeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 02. 17.
     */
    @RequestMapping(value = "/envConfgBatchChangeStore/getEnvConfgBatchChangeStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvConfgBatchChangeStoreList(EnvConfgBatchChangeVO envConfgBatchChangeVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = envConfgBatchChangeService.getEnvConfgBatchChangeStoreList(envConfgBatchChangeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, envConfgBatchChangeVO);
    }

    /**
     * 매장환경탭 - 저장
     *
     * @param envConfgBatchChangeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 02. 17.
     */
    @RequestMapping(value = "/envConfgBatchChangeStore/getEnvConfgBatchChangeStoreSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvConfgBatchChangeStoreSave(@RequestBody EnvConfgBatchChangeVO[] envConfgBatchChangeVOs, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = envConfgBatchChangeService.getEnvConfgBatchChangeStoreSave(envConfgBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장포스환경탭 - 조회
     *
     * @param envConfgBatchChangeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 02. 17.
     */
    @RequestMapping(value = "/envConfgBatchChangeStorePos/getEnvConfgBatchChangeStorePosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvConfgBatchChangeStorePosList(EnvConfgBatchChangeVO envConfgBatchChangeVO, HttpServletRequest request,
                                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = envConfgBatchChangeService.getEnvConfgBatchChangeStorePosList(envConfgBatchChangeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, envConfgBatchChangeVO);
    }

    /**
     * 매장포스환경탭 - 저장
     *
     * @param envConfgBatchChangeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 02. 17.
     */
    @RequestMapping(value = "/envConfgBatchChangeStorePos/getEnvConfgBatchChangeStorePosSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvConfgBatchChangeStorePosSave(@RequestBody EnvConfgBatchChangeVO[] envConfgBatchChangeVOs, HttpServletRequest request,
                                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = envConfgBatchChangeService.getEnvConfgBatchChangeStorePosSave(envConfgBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 기능키 조회 팝업 - 조회
     *
     * @param envConfgBatchChangeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.09.14
     */
    @RequestMapping(value = "/searchFnkey/getSearchFnkeyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchFnkeyList(EnvConfgBatchChangeVO envConfgBatchChangeVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = envConfgBatchChangeService.getSearchFnkeyList(envConfgBatchChangeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, envConfgBatchChangeVO);
    }

    /**
     * 기능키명칭탭 - 조회
     *
     * @param envConfgBatchChangeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.09.14
     */
    @RequestMapping(value = "/envConfgBatchChangeFnkey/getEnvConfgBatchChangeFnkeyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvConfgBatchChangeFnkeyList(EnvConfgBatchChangeVO envConfgBatchChangeVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = envConfgBatchChangeService.getEnvConfgBatchChangeFnkeyList(envConfgBatchChangeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, envConfgBatchChangeVO);
    }

    /**
     * 기능키명칭탭 - 저장
     *
     * @param envConfgBatchChangeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.09.15
     */
    @RequestMapping(value = "/envConfgBatchChangeFnkey/getEnvConfgBatchChangeFnkeySave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvConfgBatchChangeFnkeySave(@RequestBody EnvConfgBatchChangeVO[] envConfgBatchChangeVOs, HttpServletRequest request,
                                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = envConfgBatchChangeService.getEnvConfgBatchChangeFnkeySave(envConfgBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 기능키명칭탭 - 저장
     *
     * @param envConfgBatchChangeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.09.15
     */
    @RequestMapping(value = "/envConfgBatchChangeEnvSetting/getEnvConfgBatchChangeEnvSettingSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvConfgBatchChangeEnvSettingSave(@RequestBody EnvConfgBatchChangeVO[] envConfgBatchChangeVOs, HttpServletRequest request,
                                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = envConfgBatchChangeService.getEnvConfgBatchChangeEnvSettingSave(envConfgBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}