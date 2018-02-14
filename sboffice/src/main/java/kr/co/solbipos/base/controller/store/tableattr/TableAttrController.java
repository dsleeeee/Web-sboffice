package kr.co.solbipos.base.controller.store.tableattr;

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
import kr.co.solbipos.application.service.sample.SampleService;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.JsonResult;
import kr.co.solbipos.structure.Result;
import kr.co.solbipos.structure.Result.Status;
import kr.co.solbipos.system.Prop;
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
    SampleService sampleService;

    @Autowired
    MessageService messageService;

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
      return RESULT_URI + "/tableAttr";
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
    public JsonResult openTouchkey(HttpServletRequest request, HttpSession session, Model model) {

      String xml = "<mxGraphModel>  <root>    <mxCell id=\"0\"/>    <mxCell id=\"1\" parent=\"0\"/>    <mxCell id=\"5\" value=\"담당자\" style=\"tableAttr;fontStyle=2;\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"250\" y=\"200\" width=\"150\" height=\"50\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"2\" value=\"테이블명\" style=\"tableAttr;align=left;verticalAlign=top;fontSize=17;\" vertex=\"1\" parent=\"1\">      <mxGeometry width=\"400\" height=\"50\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"6\" value=\"경과시간\" style=\"tableAttr;verticalAlign=bottom;align=right;fontColor=#ff0000;fontSize=17;\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"150\" y=\"300\" width=\"250\" height=\"100\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"4\" value=\"손님수\" style=\"tableAttr;fontStyle=1;\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"50\" y=\"200\" width=\"150\" height=\"50\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"7\" value=\"메뉴리스트\" style=\"tableAttr;align=left;verticalAlign=top;\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"10\" y=\"50\" width=\"300\" height=\"260\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"3\" value=\"주문금액\" style=\"tableAttr\" vertex=\"1\" parent=\"1\">      <mxGeometry y=\"320\" width=\"400\" height=\"80\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"9\" value=\"배달연락처\" style=\"tableAttr;fontStyle=4;\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"200\" width=\"200\" height=\"50\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"8\" value=\"배달주소\" style=\"tableAttr;fontFamily=Hanna;\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"100\" y=\"320\" width=\"300\" height=\"80\" as=\"geometry\"/>    </mxCell>  </root></mxGraphModel>"; 

      log.info(xml);
      Result result = new Result(Status.OK, xml);
      return new JsonResult(result);
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
    public JsonResult saveTouchKey(HttpServletRequest request, HttpSession session, Model model) {

        String xml = "";
        try {
          log.debug(request.getParameter("xml"));
          xml = URLDecoder.decode(request.getParameter("xml"), "UTF-8").replace("\n", "&#xa;");
          //TODO 저장 Service 구현 필요
          log.debug(xml);
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        Result result = new Result(Status.OK);

        return new JsonResult(result);
    }

}
