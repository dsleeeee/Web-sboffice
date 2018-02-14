package kr.co.solbipos.base.controller.prod.touchkey;

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
    SampleService sampleService;

    @Autowired
    MessageService messageService;

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
    public JsonResult openTouchkey(HttpServletRequest request, HttpSession session, Model model) {

      String xml = "";
      xml = "<mxGraphModel rowPerPage=\"3\"><root><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/><UserObject label=\"그룹명\" id=\"g1\"><mxCell vertex=\"1\" parent=\"1\"><mxGeometry width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><UserObject label=\"그룹명\" id=\"g2\"><mxCell style=\"fillColor=#ffff00;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"80\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><UserObject label=\"그룹명\" id=\"g3\"><mxCell style=\"fillColor=#00b050;fontColor=#ff0000;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"160\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject></root></mxGraphModel>";
      xml += "|";
      xml += "<mxGraphModel rowPerPage=\"6\"><root><mxCell id=\"0\"/><mxCell id=\"1\" value=\"g1\" parent=\"0\" visible=\"0\"/><UserObject label=\"상품1\" price=\"10392\" id=\"p1\"><mxCell vertex=\"1\" parent=\"1\"><mxGeometry width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><UserObject label=\"상품2\" price=\"5261\" id=\"p2\"><mxCell vertex=\"1\" parent=\"1\"><mxGeometry x=\"80\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><UserObject label=\"상품3\" price=\"11571\" id=\"p3\"><mxCell vertex=\"1\" parent=\"1\"><mxGeometry x=\"160\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><mxCell id=\"2\" value=\"g2\" parent=\"0\" visible=\"0\"/><UserObject label=\"상품2\" price=\"5261\" id=\"p4\"><mxCell style=\"fillColor=#c4e6a1;\" vertex=\"1\" parent=\"2\"><mxGeometry y=\"60\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><UserObject label=\"상품3\" price=\"11571\" id=\"p5\"><mxCell style=\"fillColor=#fff9e5;fontColor=#367eb2;\" vertex=\"1\" parent=\"2\"><mxGeometry x=\"80\" y=\"60\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject><mxCell id=\"3\" value=\"g3\" parent=\"0\"/><UserObject label=\"상품3\" price=\"11571\" id=\"p6\"><mxCell vertex=\"1\" parent=\"3\"><mxGeometry x=\"240\" y=\"60\" width=\"80\" height=\"60\" as=\"geometry\"/></mxCell></UserObject></root></mxGraphModel>";
      log.info(xml);
      Result result = new Result(Status.OK, xml);
      return new JsonResult(result);
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
    public JsonResult saveTouchKey(HttpServletRequest request, HttpSession session, Model model) {

        String xml = "";
        try {
          log.debug(request.getParameter("xml"));
          xml = URLDecoder.decode(request.getParameter("xml"), "UTF-8").replace("\n", "&#xa;");
          String xmlArr[] = xml.split("\\|");
          log.info(xmlArr[0]);
          log.info(xmlArr[1]);
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        Result result = new Result(Status.OK);

        return new JsonResult(result);
    }

}
