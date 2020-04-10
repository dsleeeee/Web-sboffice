package kr.co.solbipos.base.store.tablelayout.web;

import com.nhncorp.lucy.security.xss.XssPreventer;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.tableattr.service.TableAttrService;
import kr.co.solbipos.base.store.tableattr.service.TableAttrVO;
import kr.co.solbipos.base.store.tablelayout.service.TableLayoutService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Class Name : TableLayoutController.java
 * @Description : 기초관리 > 매장관리 > 테이블관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
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
        model.addAttribute("tableAttrs", convertToJson(tableAttrService.selectTblAttrCommCode()));
        //Default 테이블속성 조회 - 각 항목의 좌표 사용을 위해
        model.addAttribute("defaults", convertToJson(tableAttrService.selectTableAttrDefault()));

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
    public Result openLayoutView(HttpServletRequest request, HttpSession session, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        LOGGER.debug(sessionInfoVO.toString());
        String xml = tableLayoutService.selectTableLayoutByStore(sessionInfoVO);
        LOGGER.debug(xml);
        return new Result(Status.OK, xml);
    }

    /**
     * 테이블 속성 기존 설정 조회
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/attrView.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result openTblAttrView(HttpServletRequest request, HttpSession session, Model model, TableAttrVO tableAttrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        LOGGER.debug(sessionInfoVO.toString());
        String xml = tableAttrService.selectTableAttrByStore(sessionInfoVO, tableAttrVO);
        //LOGGER.debug(xml);
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
    @RequestMapping(value = "/saveLayout.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveLayout(HttpServletRequest request, HttpSession session, Model model) {

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

    /**
     * 테이블 속성 저장
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/saveTblAttr.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTblAttr(HttpServletRequest request, HttpSession session, Model model, TableAttrVO tableAttrVO) {

    	String xmlGraph = "";
        String xmlPreview = "";

        Result result = new Result(Status.FAIL);
        try {
          LOGGER.debug(request.getParameter("xmlGraph"));
          LOGGER.debug(request.getParameter("xmlPreview"));

          xmlGraph = URLDecoder.decode(request.getParameter("xmlGraph"), "UTF-8").replace("\n", "&#xa;");
          xmlPreview = URLDecoder.decode(request.getParameter("xmlPreview"), "UTF-8").replace("\n", "&#xa;");

          LOGGER.debug(XssPreventer.unescape(xmlGraph));
          LOGGER.debug(XssPreventer.unescape(xmlPreview));

          SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
          LOGGER.debug(sessionInfoVO.toString());

          result = tableAttrService.setTableAttr(sessionInfoVO, tableAttrVO, XssPreventer.unescape(xmlGraph), XssPreventer.unescape(xmlPreview));
          LOGGER.debug(result.toString());
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value="/uploadImageFile.sb", method=RequestMethod.POST, produces="text/plain;charset=utf-8")
    public ResponseEntity<String> uploadImageFile(MultipartFile file, HttpServletRequest request, HttpSession session) throws Exception {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

    	//this.path = request.getRequestURI();
    	String uploadPath = request.getSession().getServletContext().getRealPath("/resources/graph/upload");

    	LOGGER.debug("path : " + uploadPath);
    	LOGGER.debug("originalName : "+file.getOriginalFilename());
    	LOGGER.debug("size : "+file.getSize());
    	LOGGER.debug("contentType : "+file.getContentType());

        return new ResponseEntity<String>(tableLayoutService.uploadFile(uploadPath, sessionInfoVO.getStoreCd(), file.getOriginalFilename(), file.getBytes()), HttpStatus.OK);
    }
}
