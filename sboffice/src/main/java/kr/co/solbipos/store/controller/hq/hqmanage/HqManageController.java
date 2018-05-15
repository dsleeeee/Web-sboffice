package kr.co.solbipos.store.controller.hq.hqmanage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import kr.co.solbipos.store.service.hq.hqmanage.HqManageService;

/**
 * 가맹점관리 > 본사정보 > 본사정보관리
 * 
 * @author 김지은
 */
@Controller
@RequestMapping(value = "/store/hq/hqmanage/hqmanage/")
public class HqManageController {

    @Autowired
    HqManageService service;
    
    
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, 
            Model model) {
        return "store/hq/hqmanage/hqManage";
    }
    
}
