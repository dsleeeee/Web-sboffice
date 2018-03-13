package kr.co.solbipos.base.controller.store.tablelayout;

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
import kr.co.solbipos.base.service.store.tablelayout.TableLayoutService;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.Result;
import kr.co.solbipos.system.Prop;
import lombok.extern.slf4j.Slf4j;

/**
 * 기초관리 - 매장관리 - 테이블관리
 * @author 조병준
 */

@Slf4j
@Controller
@RequestMapping(value = "/base/store/tablelayout/tablelayout")
public class TableLayoutController {

    private final String RESULT_URI = "base/store/tablelayout";
    
    @Autowired
    Prop prop;
    @Autowired
    SessionService sessionService;
    @Autowired
    MessageService messageService;
    
    @Autowired
    TableLayoutService tableLayoutService;

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
        return RESULT_URI + "/tableLayout";
    }

    /**
     * 테이블 구성 기존 설정 조회
     * 
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result openTouchkey(HttpServletRequest request, HttpSession session, Model model) {

      //String xml = "<mxGraphModel>  <root>    <mxCell id=\"0\"/>    <mxCell id=\"1\" parent=\"0\"/>    <mxCell id=\"4\" value=\"1\" style=\"ts2\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"130\" y=\"40\" width=\"80\" height=\"80\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"5\" value=\"2\" style=\"ts2\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"130\" y=\"150\" width=\"80\" height=\"80\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"2\" value=\"2층\" parent=\"0\" visible=\"0\"/>    <mxCell id=\"6\" value=\"3\" style=\"ts2\" vertex=\"1\" parent=\"2\">      <mxGeometry x=\"20\" y=\"20\" width=\"80\" height=\"80\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"7\" value=\"4\" style=\"ts2\" vertex=\"1\" parent=\"2\">      <mxGeometry x=\"150\" y=\"20\" width=\"80\" height=\"80\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"3\" value=\"3층\" parent=\"0\" visible=\"0\"/>    <mxCell id=\"8\" value=\"5\" style=\"ts2\" vertex=\"1\" parent=\"3\">      <mxGeometry x=\"30\" y=\"330\" width=\"80\" height=\"80\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"9\" value=\"6\" style=\"ts2\" vertex=\"1\" parent=\"3\">      <mxGeometry x=\"270\" y=\"200\" width=\"80\" height=\"80\" as=\"geometry\"/>    </mxCell>  </root></mxGraphModel>";
        SessionInfo sessionInfo = sessionService.getSessionInfo(request);
        
        log.debug(sessionInfo.toString());
        String xml = tableLayoutService.selectTableLayoutByStore(sessionInfo);
        log.debug(xml);
        return new Result(Status.OK, xml);
    }

    /**
     * 테이블 구성 저장
     * 
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTouchKey(HttpServletRequest request, HttpSession session, Model model) {

        String xml = "";
        Result result = new Result(Status.FAIL);
        try {
            log.debug(request.getParameter("bgImage"));
            log.debug(request.getParameter("bgColor"));
            log.debug(request.getParameter("xml"));
            xml = URLDecoder.decode(request.getParameter("xml"), "UTF-8").replace("\n", "&#xa;");
            log.debug(XssPreventer.unescape(xml));
            SessionInfo sessionInfo = sessionService.getSessionInfo(request);
            log.debug(sessionInfo.toString());
            
            result = tableLayoutService.setTableLayout(sessionInfo, XssPreventer.unescape(xml));
            log.debug(result.toString());
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        return result;
    }

}
