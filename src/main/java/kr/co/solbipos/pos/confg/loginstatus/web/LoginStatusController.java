package kr.co.solbipos.pos.confg.loginstatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.solbipos.pos.confg.loginstatus.service.LoginStatusService;
import kr.co.solbipos.pos.confg.loginstatus.service.LoginStatusVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : LoginStatusController.java
 * @Description : 포스관리 > POS 설정관리 > POS 로그인현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/pos/confg/loginStatus/loginStatus/")
public class LoginStatusController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoginStatusService loginStatusService;

    /**
     * POS관리 > POS 설정관리 > POS 로그인 현황 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String loginstatusList(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "pos/confg/loginStatus/loginStatus";
    }

    /**
     * POS관리 > POS 설정관리 > POS 로그인 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result loginstatusListPost(LoginStatusVO loginStatusVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        LOGGER.debug(loginStatusVO.toString());
        List<DefaultMap<Object>> result = loginStatusService.selectLoginStatus(loginStatusVO);

        return returnListJson(Status.OK, result, loginStatusVO);
    }
}


