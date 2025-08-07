package kr.co.solbipos.store.manage.pwdManageSaleChk.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.store.manage.pwdManageSaleChk.service.PwdManageSaleChkService;
import kr.co.solbipos.store.manage.pwdManageSaleChk.service.PwdManageSaleChkVO;
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
/**
 * @Class Name  : PwdManageSaleChkController.java
 * @Description : 기초관리 > 매출조회 비밀번호 관리 > 매출조회 비밀번호 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/store/manage/pwdManageSaleChk")
public class PwdManageSaleChkController {

    private final SessionService sessionService;
    private final PwdManageSaleChkService pwdManageSaleChkService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PwdManageSaleChkController(SessionService sessionService, PwdManageSaleChkService pwdManageSaleChkService) {
        this.sessionService = sessionService;
        this.pwdManageSaleChkService = pwdManageSaleChkService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/pwdManageSaleChk/list.sb", method = RequestMethod.GET)
    public String saleCancelStatusView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "store/manage/pwdManageSaleChk/pwdManageSaleChk";
    }

    /**
     * 취소현황 - 전체점포탭 조회
     *
     * @param   pwdManageSaleChkVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 08. 05.
     */
    @RequestMapping(value = "/pwdManageSaleChk/getPwdManageSaleChkList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPwdManageSaleChkList(PwdManageSaleChkVO pwdManageSaleChkVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = pwdManageSaleChkService.getPwdManageSaleChkList(pwdManageSaleChkVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, pwdManageSaleChkVO);
    }

    /**
     * 매출조회 설정변경 - 패스워드초기화
     * @param   request
     * @param   response
     * @param   pwdManageSaleChkVO
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2025. 08. 06.
     */
    @RequestMapping(value = "/pwdManageSaleChk/getClearSalePwd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getClearSalePwd(@RequestBody PwdManageSaleChkVO pwdManageSaleChkVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        pwdManageSaleChkVO.setRegId(sessionService.getSessionInfo().getUserId());
        pwdManageSaleChkVO.setModId(sessionService.getSessionInfo().getUserId());

        // 패스워드 변경
        PwChgResult result = pwdManageSaleChkService.getClearPwdSave(pwdManageSaleChkVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매출조회 설정변경 - 패스워드 변경
     * @param   request
     * @param   response
     * @param   pwdManageSaleChkVO
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2025. 08. 06.
     */
    @RequestMapping(value = "/pwdManageSaleChk/getModifySalePwd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getModifySalePwd(@RequestBody PwdManageSaleChkVO pwdManageSaleChkVO, HttpServletRequest request,
                                 HttpServletResponse response,  Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);


        pwdManageSaleChkVO.setRegId(sessionService.getSessionInfo().getUserId());
        pwdManageSaleChkVO.setModId(sessionService.getSessionInfo().getUserId());

        // 패스워드 변경
        PwChgResult result = pwdManageSaleChkService.getModifySalePwd(pwdManageSaleChkVO);

        return returnJson(Status.OK, result);
    }
}
