package kr.co.solbipos.pos.confg.mainVerManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.mainVerManage.service.MainVerManageService;
import kr.co.solbipos.pos.confg.mainVerManage.service.MainVerManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : MainVerManageController.java
 * @Description : 포스관리 > POS 설정관리 > POS 메인버전관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/pos/confg/mainVerManage")
public class MainVerManageController {

    private final SessionService sessionService;
    private final MainVerManageService mainVerManageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MainVerManageController(SessionService sessionService, MainVerManageService mainVerManageService) {
        this.sessionService = sessionService;
        this.mainVerManageService = mainVerManageService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mainVerManage/list.sb", method = RequestMethod.GET)
    public String mainVerManageView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "pos/confg/mainVerManage/mainVerManage";
    }

    /**
     * POS 메인버전관리 - 조회
     *
     * @param mainVerManageVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2025. 08. 07.
     */
    @RequestMapping(value = "/mainVerManage/getMainVerManageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMainVerManageList(MainVerManageVO mainVerManageVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mainVerManageService.getMainVerManageList(mainVerManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mainVerManageVO);
    }

    /**
     * POS 메인버전관리 - 메인버전 삭제
     *
     * @param mainVerManageVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2025. 08. 07.
     */
    @RequestMapping(value = "/mainVerManage/getMainVerManageDel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMainVerManageDel(@RequestBody MainVerManageVO mainVerManageVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = mainVerManageService.getMainVerManageDel(mainVerManageVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 메인버전 등록 팝업 - 조회
     *
     * @param mainVerManageVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2025. 08. 07.
     */
    @RequestMapping(value = "/mainVerRegist/getMainVerRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMainVerRegistList(MainVerManageVO mainVerManageVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mainVerManageService.getMainVerRegistList(mainVerManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mainVerManageVO);
    }

    /**
     * 메인버전 등록 팝업 - 등록
     *
     * @param mainVerManageVOs
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2025. 08. 07.
     */
    @RequestMapping(value = "/mainVerRegist/getMainVerRegistSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMainVerRegistSave(@RequestBody MainVerManageVO[] mainVerManageVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = mainVerManageService.getMainVerRegistSave(mainVerManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}