package kr.co.solbipos.sys.controller.menu.webmenu;

import static kr.co.solbipos.utils.grid.ReturnUtil.*;
import static kr.co.solbipos.utils.spring.StringUtil.*;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.Result;
import kr.co.solbipos.sys.service.menu.webmenu.WebMenuService;
import kr.co.solbipos.sys.validate.menu.webmenu.WebMenuSave;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 시스템 관리 > 메뉴관리 > 프로그램 메뉴관리
 * 
 * @author 정용길
 */
@Slf4j
@Controller
@RequestMapping(value = "sys/menu/webmenu/webmenu/")
public class WebMenuController {

    @Autowired
    WebMenuService webMenuService;

    @Autowired
    SessionService sessionService;

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
     * @param resrceInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result webMenuView(ResrceInfo resrceInfo, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        List<String> result = webMenuService.selectWebMenu(resrceInfo);
        return returnListJson(Status.OK, result);
    }

    /**
     * 리소스 저장 : 메뉴, 기능 저장
     * 
     * @param resrceInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result webMenuSave(@Validated(WebMenuSave.class) @RequestBody ResrceInfo resrceInfo,
            BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
            Model model) {

        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        boolean result = webMenuService.insertMenu(resrceInfo);

        return returnJson(Status.OK, result);
    }

    /**
     * 리소스 삭제
     * 
     * @param resrceInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "remove.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result webMenuRemove(ResrceInfo resrceInfo, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        int result = webMenuService.deleteWebMenu(resrceInfo);

        return returnJson(Status.OK, result);
    }

}


