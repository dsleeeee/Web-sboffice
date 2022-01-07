package kr.co.solbipos.base.store.tablelayout.web;

import com.nhncorp.lucy.security.xss.XssPreventer;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.tableattr.service.TableAttrNewService;
import kr.co.solbipos.base.store.tableattr.service.TableAttrVO;
import kr.co.solbipos.base.store.tablelayout.service.TableLayoutService;
import kr.co.solbipos.base.store.tablelayout.service.TableVO;

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
@RequestMapping(value = "/base/store/tableLayout/tableNewLayout")
public class TableLayoutNewController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final String RESULT_URI = "base/store/tablelayout";

    @Autowired
    SessionService sessionService;
    @Autowired
    MessageService messageService;

    @Autowired
    TableLayoutService tableLayoutService;

    @Autowired
    TableAttrNewService tableAttrNewService;
    @Autowired
    CmmEnvUtil cmmEnvUtil;

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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    	//테이블속성 항목값 - 공통코드
        model.addAttribute("tableAttrs", convertToJson(tableAttrNewService.selectTblAttrCommCode()));
        //Default 테이블속성 조회 - 각 항목의 좌표 사용을 위해
        model.addAttribute("defaults", convertToJson(tableAttrNewService.selectTableAttrDefault()));

        // 1098 테이블속성사이즈보정사용여부 200*200
        String envstCd      = "1098";
        String attrSizeCd   = StringUtil.getOrDefault(cmmEnvUtil.getStoreEnvst(sessionInfoVO, envstCd), "0");
        model.addAttribute("attrSizeCd", attrSizeCd);

        return RESULT_URI + "/tableNewLayout";
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

        System.out.println("컨트롤러 조회 : " + sessionInfoVO.getStoreCd());

        LOGGER.debug(sessionInfoVO.toString());
        String xml = tableLayoutService.selectTableLayoutByStore(sessionInfoVO);
        LOGGER.debug(xml);
        return new Result(Status.OK, xml);
    }

    /**
     * 테이블 속성 기존 설정 조회(유형별)
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
        String xml = tableAttrNewService.selectTableAttrByStore(sessionInfoVO, tableAttrVO);
        //LOGGER.debug(xml);
        return new Result(Status.OK, xml);
    }

    /**
     * 테이블 속성 기존 설정 조회(테이블별)
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/attrNumView.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result openTblNumAttrView(HttpServletRequest request, HttpSession session, Model model, TableAttrVO tableAttrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        LOGGER.debug(sessionInfoVO.toString());
        String xml = tableAttrNewService.selectTableAttrByNum(sessionInfoVO, tableAttrVO);
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
    public Result saveLayout(HttpServletRequest request, HttpSession session, Model model, TableVO tableVO) {

//        String xml = "";
//        Result result = new Result(Status.FAIL);
//
//        //LOGGER.debug( XssPreventer.unescape(request.getParameter("xml")) );
//        xml = XssPreventer.unescape(request.getParameter("xml"));
//
//        LOGGER.debug(xml);
//        //LOGGER.debug(XssPreventer.unescape(xml));
//
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//        //LOGGER.debug(sessionInfoVO.toString());
//
//        result = tableLayoutService.setTableLayout(sessionInfoVO, xml);
//        LOGGER.debug(result.toString());
//        return result;
    	String xml = "";
        Result result = new Result(Status.FAIL);

	try {

        LOGGER.debug(tableVO.getXml());

    	xml = URLDecoder.decode(tableVO.getXml(), "UTF-8").replace("\n", "&#xa;");

    	LOGGER.debug(XssPreventer.unescape(xml));

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    	LOGGER.debug(sessionInfoVO.toString());

    	result = tableLayoutService.setTableLayout(sessionInfoVO, XssPreventer.unescape(xml));
    	LOGGER.debug(result.toString());
    } catch (UnsupportedEncodingException e) {
    	e.printStackTrace();
    }

    	return result;
    }

    /**
     * 테이블 속성 저장(테이블 유형별)
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

        	LOGGER.debug(tableAttrVO.getXmlGraph());
        	LOGGER.debug(tableAttrVO.getXmlPreview());

        	xmlGraph = URLDecoder.decode(tableAttrVO.getXmlGraph(), "UTF-8").replace("\n", "&#xa;");
        	xmlPreview = URLDecoder.decode(tableAttrVO.getXmlPreview(), "UTF-8").replace("\n", "&#xa;");

        	LOGGER.debug(XssPreventer.unescape(xmlGraph));
        	LOGGER.debug(XssPreventer.unescape(xmlPreview));

        	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        	LOGGER.debug(sessionInfoVO.toString());

        	result = tableAttrNewService.setTableAttr(sessionInfoVO, tableAttrVO, XssPreventer.unescape(xmlGraph), XssPreventer.unescape(xmlPreview));
        	LOGGER.debug(result.toString());

        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }

        return result;
    }

    /**
     * 테이블 속성 저장(테이블 번호별)
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/saveTblNumAttr.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTblNumAttr(HttpServletRequest request, HttpSession session, Model model, TableAttrVO tableAttrVO) {

    	String xmlGraph = "";
        String xmlPreview = "";

        Result result = new Result(Status.FAIL);
        try {

        	LOGGER.debug(tableAttrVO.getXmlGraph());
        	LOGGER.debug(tableAttrVO.getXmlPreview());

        	xmlGraph = URLDecoder.decode(tableAttrVO.getXmlGraph(), "UTF-8").replace("\n", "&#xa;");
        	xmlPreview = URLDecoder.decode(tableAttrVO.getXmlPreview(), "UTF-8").replace("\n", "&#xa;");

        	LOGGER.debug(XssPreventer.unescape(xmlGraph));
        	LOGGER.debug(XssPreventer.unescape(xmlPreview));

        	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        	LOGGER.debug(sessionInfoVO.toString());

        	result = tableAttrNewService.setTableNumAttr(sessionInfoVO, tableAttrVO, XssPreventer.unescape(xmlGraph), XssPreventer.unescape(xmlPreview));
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
    	// 파일서버 대응 경로 지정 (로컬)
//    	String uploadPath = request.getSession().getServletContext().getRealPath("/resources/graph/upload");
        // 파일서버 대응 경로 지정 (운영)
        String uploadPath = BaseEnv.FILE_UPLOAD_DIR + "table_img/";


    	LOGGER.debug("path : " + uploadPath);
    	LOGGER.debug("originalName : "+file.getOriginalFilename());
    	LOGGER.debug("size : "+file.getSize());
    	LOGGER.debug("contentType : "+file.getContentType());

        return new ResponseEntity<String>(tableLayoutService.uploadFile(uploadPath, sessionInfoVO.getStoreCd(), file.getOriginalFilename(), file.getBytes()), HttpStatus.OK);
    }

    /**
     * 테이블 기존 데이터 삭제 후 매장생성시로 초기화
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/initLayout.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result initLayout(HttpServletRequest request, HttpSession session, Model model, TableAttrVO tableAttrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        LOGGER.debug(sessionInfoVO.toString());
        tableAttrNewService.initLayout(sessionInfoVO, tableAttrVO);

        return new Result(Status.OK);
    }
}
