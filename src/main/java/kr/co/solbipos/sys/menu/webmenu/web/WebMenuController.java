package kr.co.solbipos.sys.menu.webmenu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.validate.WebMenuSave;
import kr.co.solbipos.application.common.service.ResrceInfoVO;
import kr.co.solbipos.sys.menu.webmenu.service.WebMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.*;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 *
 * 시스템 관리 > 메뉴관리 > 프로그램 메뉴관리
 *
 * @author 정용길
 */
@Controller
@RequestMapping(value = "sys/menu/webMenu/webMenu/")
public class WebMenuController {

    private final WebMenuService webMenuService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public WebMenuController(WebMenuService webMenuService, SessionService sessionService) {
        this.webMenuService = webMenuService;
        this.sessionService = sessionService;
    }

    /**
     * 프로그램 메뉴관리 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String webMenu(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<HashMap<String, Object>> rList = webMenuService.makeupTree();
        model.addAttribute("webMenuList", convertToJson(rList));
        return "sys/menu/webmenu/webMenu";
    }

    /**
     * 리소스 메뉴 데이터 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result webMenuList(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        List<HashMap<String, Object>> rList = webMenuService.makeupTree();
        return returnListJson(Status.OK, rList);
    }

    /**
     * 트리에서 리소스 선택시 조회
     *
     * @param resrceInfoVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result webMenuView(ResrceInfoVO resrceInfoVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        List<String> result = webMenuService.selectWebMenu(resrceInfoVO);
        return returnListJson(Status.OK, result);
    }

    /**
     * 리소스 저장 : 메뉴, 기능 저장
     *
     * @param resrceInfoVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result webMenuSave(@Validated(WebMenuSave.class) @RequestBody ResrceInfoVO resrceInfoVO,
            BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
            Model model) {

        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        boolean result = webMenuService.insertMenu(resrceInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 리소스 삭제
     *
     * @param resrceInfoVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "remove.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result webMenuRemove(ResrceInfoVO resrceInfoVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        int result = webMenuService.deleteWebMenu(resrceInfoVO);

        return returnJson(Status.OK, result);
    }

}


