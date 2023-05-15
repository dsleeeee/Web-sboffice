package kr.co.solbipos.store.manage.pwdManageStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.pwdManageStore.service.PwdManageStoreService;
import kr.co.solbipos.store.manage.pwdManageStore.service.PwdManageStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : PwdManageStoreController.java
 * @Description : 기초관리 > 비밀번호 임의변경 > 비밀번호 임의변경(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.05.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/store/manage/pwdManageStore")
public class PwdManageStoreController {

    private final SessionService sessionService;
    private final PwdManageStoreService pwdManageStoreService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PwdManageStoreController(SessionService sessionService, PwdManageStoreService pwdManageStoreService) {
        this.sessionService = sessionService;
        this.pwdManageStoreService = pwdManageStoreService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/pwdManageStore/list.sb", method = RequestMethod.GET)
    public String pwdManageStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "store/manage/pwdManageStore/pwdManageStore";
    }

    /**
     * 비밀번호 임의변경(매장) - 조회
     *
     * @param pwdManageStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 05. 11.
     */
    @RequestMapping(value = "/pwdManageStore/getPwdManageStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPwdManageStoreList(PwdManageStoreVO pwdManageStoreVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = pwdManageStoreService.getPwdManageStoreList(pwdManageStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, pwdManageStoreVO);
    }
}