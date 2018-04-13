package kr.co.solbipos.pos.controller.confg.func;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.pos.service.confg.func.FuncService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.utils.jsp.CmmCodeUtil;
import lombok.extern.slf4j.Slf4j;
import static kr.co.solbipos.utils.spring.StringUtil.convertToJson;


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
    FuncService funcService;
    
    @Autowired
    SessionService sessionService;

    @Autowired
    CmmCodeUtil cmmCode;
    
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String funcList(HttpServletRequest request, HttpServletResponse response, Model model) {
        
        log.error(":::: POS 기능정의 ::::");
        
        SessionInfo sessionInfo = sessionService.getSessionInfo(request);
        
        /** POS 기능정의 리스트 조회 */
        List<DefaultMap<String>> funcList = funcService.getFuncList(sessionInfo);
        
        model.addAttribute("fnkeyFgCode", cmmCode.getCommCodeAll("034"));
        model.addAttribute("storeFgCode", cmmCode.getCommCodeAll("088"));
        model.addAttribute("posFgCode", cmmCode.getCommCodeAll("035"));
        model.addAttribute("funcList", convertToJson(funcList));
        
        return "pos/confg/func/func";
    }
    
    
    
}
