package kr.co.solbipos.store.manage.virtuallogin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginService;
import lombok.extern.slf4j.Slf4j;

/**
 * 가맹점관리 > 매장관리 > 가상로그인
 *
 * @author 노현수
 */
@Slf4j
@Controller
@RequestMapping(value = "/store/manage/virtualLogin")
public class VirtualLoginController {

    @Autowired
    VirtualLoginService virtualLoginService;

    /**
     * 가상로그인 - 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/virtualLogin/view.sb", method = RequestMethod.GET)
    public String virtualLoginView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "store/manage/virtualLogin/virtualLogin";
    }

    /**
     * 가상로그인 - 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/virtualLogin/list.sb", method = RequestMethod.GET)
    @ResponseBody
    public String virtualLoginList(HttpServletRequest request, HttpServletResponse response,
            Model model) {




        return "";
    }

    /**
     * 가상로그인 - 로그인 수행
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/virtualLogin/login.sb", method = RequestMethod.GET)
    public String virtualLoginLogin(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "";
    }

}
