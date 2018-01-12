package kr.co.solbipos.application.controller.user;

import static kr.co.solbipos.utils.spring.StringUtil.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.user.User;
import kr.co.solbipos.application.service.user.UserService;
import kr.co.solbipos.application.validate.user.Find;
import kr.co.solbipos.structure.JsonResult;
import kr.co.solbipos.utils.spring.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author 정용길
 */

@Slf4j
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 인증번호 발사!!
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "sendNum.sb", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendNum(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return null;
    }

    /**
     * 아이디 찾기 성공 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    /*
    @RequestMapping(value = "idFindOk.sb", method = RequestMethod.GET)
    public String idFindOk(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "user/login:idFindOk";
    }
    */

    /**
     * 아이디 찾기 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "idFind.sb", method = RequestMethod.GET)
    public String idFind(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "user/login:idFind";
    }

    /**
     * 아이디 찾기
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "idFind.sb", method = RequestMethod.POST)
    public String idFindProcess(@Validated(Find.class) User user, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/login:idFind";
        }
        
        String findUserNm = userService.selectUserCheck(user);

        if(StringUtil.isEmpty(findUserNm)) {
            model.addAttribute("msg", "아이디를 찾을 수 없습니다.");
            return "user/login:idFind";
        }
        
        model.addAttribute("findUserNm", strMaskingHalf(findUserNm));
        
        return "user/login:idFindOk";
    }

    /**
     * 패스워드 찾기 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdFind.sb", method = RequestMethod.GET)
    public String passwordFind(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "user/login:pwdFind";
    }

    /**
     * 패스워드 찾기 > 비밀번호 변경 페이지로 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdFind.sb", method = RequestMethod.POST)
    public String passwordFindProcess(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "redirect:/user/pwdChg.sb";
    }

    /**
     * 패스워드 변경 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdChg.sb", method = RequestMethod.GET)
    public String passwordChange(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "user/login:pwdChg";
    }

    /**
     * 패스워드 변경
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdChg.sb", method = RequestMethod.POST)
    public String passwordChangeProcess(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "redirect:/user/pwdChgOk.sb";
    }

    /**
     * 패스워드 변경 완료 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdChgOk.sb", method = RequestMethod.GET)
    public String pwdChgOk(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "user/login:pwdChgOk";
    }
}


