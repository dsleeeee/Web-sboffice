package kr.co.solbipos.pos.install.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.install.service.InstallManageService;
import kr.co.solbipos.pos.install.service.InstallVO;
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
 * @Class Name : InstallManageController.java
 * @Description : 포스관리 > 설치관리 > 설치관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.01.02  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2019. 01.02
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/pos/install/")
public class InstallManageController {

    private final InstallManageService installManageService;
    private final SessionService sessionService;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public InstallManageController(InstallManageService installManageService, SessionService sessionService,
        CmmCodeUtil cmmCodeUtil) {
        this.installManageService = installManageService;
        this.sessionService = sessionService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 설치관리 페이지 이동 (설치요청)
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "installManage/installManage/installManage.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        return "pos/install/installManage/installManage";
    }

    /**
     * 설치관리 - 설치요청 목록 조회
     *
     * @param installVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "installManage/installManage/getInstallRequestList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInstallRequestList(InstallVO installVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = installManageService.getInstallRequestList(installVO);

        return returnListJson(Status.OK, list, installVO);
    }

    /**
     * 설치관리 - 요청 포스 목록 조회
     *
     * @param installVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "installManage/installManage/getPosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosList(InstallVO installVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = installManageService.getPosList(installVO);

        return returnListJson(Status.OK, list, installVO);
    }


    /**
     * 설치관리 - 설치 요청
     *
     * @param installVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "installManage/installManage/saveInstallRequest.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveInstallRequest(@RequestBody InstallVO[] installVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = installManageService.saveInstallRequest(installVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
