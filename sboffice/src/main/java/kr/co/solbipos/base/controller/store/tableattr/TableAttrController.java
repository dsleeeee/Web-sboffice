package kr.co.solbipos.base.controller.store.tableattr;

import static kr.co.solbipos.utils.spring.StringUtil.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nhncorp.lucy.security.xss.XssPreventer;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.base.service.store.tableattr.TableAttrService;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.Result;
import kr.co.solbipos.system.Prop;
import kr.co.solbipos.utils.jsp.CmmCodeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 기초관리 - 매장관리 - 테이블속성
 * @author 조병준
 */

@Slf4j
@Controller
@RequestMapping(value = "/base/store/tableattr/tableattr")
public class TableAttrController {

    private final String RESULT_URI = "base/store/tableattr";
    
    @Autowired
    Prop prop;
    @Autowired
    SessionService sessionService;
    @Autowired
    MessageService messageService;
    @Autowired
    CmmCodeUtil cmmCode;
    
    @Autowired
    TableAttrService tableAttrService;


    /**
     * 테이블 구성 화면 오픈
     * 
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpSession session, Model model) {
        
        //테이블속성 항목값 - 공통코드
        model.addAttribute("tableAttrs", cmmCode.getCommCodeAll("207"));
        //Default 테이블속성 조회 - 각 항목의 좌표 사용을 위해
        model.addAttribute("defaults", convertToJson(tableAttrService.selectTableAttrDefault()));
        
        return RESULT_URI + "/tableAttr";
    }

    /**
     * 테이블 속성 기존 설정 조회
     * 
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result open(HttpServletRequest request, HttpSession session, Model model) {

        SessionInfo sessionInfo = sessionService.getSessionInfo(request);
        
        log.debug(sessionInfo.toString());
        String xml = tableAttrService.selectTableAttrByStore(sessionInfo);
        log.debug(xml);
        return new Result(Status.OK, xml);
    }

    /**
     * 테이블 속성 저장
     * 
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(HttpServletRequest request, HttpSession session, Model model) {

        String xml = "";
        Result result = new Result(Status.FAIL);
        try {
          log.debug(request.getParameter("xml"));
          xml = URLDecoder.decode(request.getParameter("xml"), "UTF-8").replace("\n", "&#xa;");
          log.debug(XssPreventer.unescape(xml));
          
          SessionInfo sessionInfo = sessionService.getSessionInfo(request);
          log.debug(sessionInfo.toString());
          
          result = tableAttrService.setTableAttr(sessionInfo, XssPreventer.unescape(xml));
          log.debug(result.toString());
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }

        return result;
    }

}
