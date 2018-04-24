package kr.co.solbipos.pos.controller.confg.func;

import static kr.co.solbipos.utils.grid.ReturnUtil.returnJson;
import static kr.co.solbipos.utils.grid.ReturnUtil.returnListJson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.pos.domain.confg.func.Func;
import kr.co.solbipos.pos.service.confg.func.FuncService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.Result;
import kr.co.solbipos.utils.jsp.CmmCodeUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 포스관리 > POS 설정관리 > POS 기능정의
 * 
 * @author 김지은
 */
@Slf4j
@Controller
@RequestMapping(value = "/pos/confg/func/func")
public class FuncController {

    @Autowired
    FuncService service;
    
    @Autowired
    SessionService sessionService;

    @Autowired
    CmmCodeUtil cmmCode;
    
    /**
     * 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, 
            Model model) {
        
        // 기능구분 조회
        model.addAttribute("fnkeyFgList", cmmCode.getCommCodeAll("034"));

        return "pos/confg/func/func";
    }
    
    /**
     * 기능구분 상세 조회
     * 
     * @param func
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "funcList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(Func func, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.list(func);
        
        return returnListJson(Status.OK, list, func);
    }
    
    /**
     * 기능구분상세 저장
     * 
     * @param func
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Func[] func, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        SessionInfo sessionInfo = sessionService.getSessionInfo(request);
        
        int result = service.save(func, sessionInfo);
        
        return returnJson(Status.OK, result);
    }
    
    
}
