package kr.co.solbipos.base.controller.prod.touchkey;

import static kr.co.common.utils.spring.StringUtil.convertToJson;
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
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.Prop;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.base.service.prod.touchkey.TouchkeyService;
import lombok.extern.slf4j.Slf4j;

/**
 * 기초관리 - 상품관리 - 판매터치키등록
 * @author 조병준
 */

@Slf4j
@Controller
@RequestMapping(value = "/base/prod/touchkey/touchkey")
public class TouchkeyController {

    private final String RESULT_URI = "base/prod/touchkey";

    @Autowired
    Prop prop;
    @Autowired
    SessionService sessionService;
    @Autowired
    MessageService messageService;

    @Autowired
    TouchkeyService touchkeyService;

    /**
     * 판매 터치키 화면 오픈
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpSession session, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        //화면에 표시할 상점의 상품 정보 조회
        model.addAttribute("prods", convertToJson(touchkeyService.selectProdByStore(sessionInfoVO)));

        //TODO 매장의 터치키 환경 설정 값을 조회해서 셋팅
        model.addAttribute("maxGroupRow", "2");


        return RESULT_URI + "/touchkey";
    }

    /**
     * 판매 터치키 기존 설정 조회
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result openTouchkey(HttpServletRequest request, HttpSession session, Model model) {
        /*
        String xml = "";
        xml = "<mxGraphModel rowPerPage=\"3\"><root><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/><UserObject label=\"그룹명\" id=\"g1\"><mxCell vertex=\"1\" parent=\"1\"><mxGeometry width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><UserObject label=\"그룹명\" id=\"g2\"><mxCell style=\"fillColor=#ffff00;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"80\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><UserObject label=\"그룹명\" id=\"g3\"><mxCell style=\"fillColor=#00b050;fontColor=#ff0000;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"160\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject></root></mxGraphModel>";
        xml += "|";
        xml += "<mxGraphModel rowPerPage=\"6\"><root><mxCell id=\"0\"/><mxCell id=\"1\" value=\"g1\" parent=\"0\" visible=\"0\"/><UserObject label=\"상품1\" price=\"10392\" id=\"p1\"><mxCell vertex=\"1\" parent=\"1\"><mxGeometry width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><UserObject label=\"상품2\" price=\"5261\" id=\"p2\"><mxCell vertex=\"1\" parent=\"1\"><mxGeometry x=\"80\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><UserObject label=\"상품3\" price=\"11571\" id=\"p3\"><mxCell vertex=\"1\" parent=\"1\"><mxGeometry x=\"160\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><mxCell id=\"2\" value=\"g2\" parent=\"0\" visible=\"0\"/><UserObject label=\"상품2\" price=\"5261\" id=\"p4\"><mxCell style=\"fillColor=#c4e6a1;\" vertex=\"1\" parent=\"2\"><mxGeometry y=\"60\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><UserObject label=\"상품3\" price=\"11571\" id=\"p5\"><mxCell style=\"fillColor=#fff9e5;fontColor=#367eb2;\" vertex=\"1\" parent=\"2\"><mxGeometry x=\"80\" y=\"60\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><mxCell id=\"3\" value=\"g3\" parent=\"0\"/><UserObject label=\"상품3\" price=\"11571\" id=\"p6\"><mxCell vertex=\"1\" parent=\"3\"><mxGeometry x=\"240\" y=\"60\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject></root></mxGraphModel>";
        log.debug(xml);
        */
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        log.debug(sessionInfoVO.toString());
        String xml = touchkeyService.selectTouchkeyByStore(sessionInfoVO);
        log.debug(xml);
        return new Result(Status.OK, xml);
    }

    /**
     * 판매 터치키 저장
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTouchKey(HttpServletRequest request, HttpSession session, Model model) {

        Result result = new Result(Status.FAIL);
        try {
            log.debug(request.getParameter("xml"));
            String xml = URLDecoder.decode(request.getParameter("xml"), "UTF-8").replace("\n", "&#xa;");
            SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
            log.debug(sessionInfoVO.toString());

            result = touchkeyService.setTouchkey(sessionInfoVO, XssPreventer.unescape(xml));
            log.debug(result.toString());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
