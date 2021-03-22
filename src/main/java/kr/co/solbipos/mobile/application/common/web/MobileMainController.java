package kr.co.solbipos.mobile.application.common.web;

import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Class Name : MobileMainController.java
 * @Description : 어플리케이션 > 메인화면 이동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.03.10  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2021.03.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
public class MobileMainController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public MobileMainController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * 로그인 후 메인 페이지로 이동
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param redirectAttributes RedirectAttributes
     * @param model Model
     * @return String
     */
    @RequestMapping(value = "/mobile/main.sb", method = RequestMethod.GET)
    public String main(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {

        if (!sessionService.isValidSession(request)) {
            return "redirect:/mobile/auth/login.sb";
        }

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        OrgnFg orgnFg = sessionInfoVO.getOrgnFg();

        if ( request.getParameter("sid") != null && request.getParameter("sid").length() > 0 ) {
            redirectAttributes.addAttribute("sid", request.getParameter("sid"));
        }

        /**
         * 유져 권한 타입<br>
         * */
        // 시스템 : SYSTEM
        if(orgnFg == OrgnFg.MASTER) {
            return "redirect:"+"/mobile/application/main/content/sys.sb";
        }
        // 대리점 : AGENCY
        else if(orgnFg == OrgnFg.AGENCY) {
            return "redirect:"+"/mobile/application/main/content/agency.sb";
        }
        // 본사 : HEDOFC(HEAD OFFICE)
        else if(orgnFg == OrgnFg.HQ) {
            return "redirect:"+"/mobile/application/main/content/hq.sb";
        }
        // 가맹점 : MRHST(MEMBER BRANCH STORE)
        else if(orgnFg == OrgnFg.STORE) {
            return "redirect:"+"/mobile/application/main/content/store.sb";
        }

        return "mobile/application/main/mrhstMain";
    }

}
