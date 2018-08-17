package kr.co.solbipos.base.store.tablelayout.web;

import com.nhncorp.lucy.security.xss.XssPreventer;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.tablelayout.service.TableLayoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Class Name : TableLayoutController.java
 * @Description : 기초관리 > 매장관리 > 테이블관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/store/tableLayout/tableLayout")
public class TableLayoutController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    private final String RESULT_URI = "base/store/tablelayout";

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
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        LOGGER.debug(sessionInfoVO.toString());
        String xml = tableLayoutService.selectTableLayoutByStore(sessionInfoVO);
        LOGGER.debug(xml);
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

        //LOGGER.debug( XssPreventer.unescape(request.getParameter("xml")) );
        xml = XssPreventer.unescape(request.getParameter("xml"));

        LOGGER.debug(xml);
        //LOGGER.debug(XssPreventer.unescape(xml));

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        //LOGGER.debug(sessionInfoVO.toString());

        result = tableLayoutService.setTableLayout(sessionInfoVO, xml);
        LOGGER.debug(result.toString());
        return result;
    }

}
