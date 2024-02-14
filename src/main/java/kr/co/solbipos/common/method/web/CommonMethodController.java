package kr.co.solbipos.common.method.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.common.method.service.CommonMethodService;
import kr.co.solbipos.common.method.service.CommonMethodVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static kr.co.common.utils.HttpUtils.getClientIp;
import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : commonMethodController.java
 * @Description : (공통) 화면 공통 사용 메소드 모음
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.02.13  이다솜      최초생성
 *
 * @author 솔비포스 IT개발실 WEB개발팀 이다솜
 * @since 2024.02.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/common/method")
public class CommonMethodController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final CommonMethodService commonMethodService;

    /**
     * Constructor Injection
     */
    @Autowired
    public CommonMethodController(SessionService sessionService, CommonMethodService commonMethodService) {
        this.sessionService = sessionService;
        this.commonMethodService = commonMethodService;
    }

    /**
     * 사용자 행위 기록
     * @param commonMethodVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveUserAct.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUserAct(@RequestBody CommonMethodVO commonMethodVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        sessionInfoVO.setLoginIp(getClientIp(request));

        int result = commonMethodService.saveUserAct(commonMethodVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}