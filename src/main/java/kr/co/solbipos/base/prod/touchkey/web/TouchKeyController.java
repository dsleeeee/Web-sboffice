package kr.co.solbipos.base.prod.touchkey.web;

import com.nhncorp.lucy.security.xss.XssPreventer;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyService;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyStyleVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : TouchKeyController.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 * @ 2018.09.19  노현수      메소드정리/분리
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/touchKey/touchKey")
public class TouchKeyController {

    private final String RESULT_URI = "base/prod/touchKey";
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final TouchKeyService touchkeyService;
    private final CmmEnvUtil cmmEnvUtil;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public TouchKeyController(SessionService sessionService,
        TouchKeyService touchkeyService, CmmEnvUtil cmmEnvUtil, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.touchkeyService = touchkeyService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.cmmCodeUtil = cmmCodeUtil;
    }


    /**
     * 판매 터치키 화면 오픈
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String touchKeyView(HttpServletRequest request, HttpSession session, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        TouchKeyVO params = new TouchKeyVO();
        params.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        params.setStoreCd(sessionInfoVO.getStoreCd());
        params.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        TouchKeyClassVO touchKeyClassVO = new TouchKeyClassVO();
        touchKeyClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyClassVO.setStoreCd(sessionInfoVO.getStoreCd());
        touchKeyClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        touchKeyClassVO.setPageNo(1);

        TouchKeyStyleVO touchKeyStyleVO = new TouchKeyStyleVO();
        touchKeyStyleVO.setStyleCd("");

        // 상품분류 콤보
        model.addAttribute("srchClsFgCombo", cmmCodeUtil.assmblObj(touchkeyService.getProductClassListForTouchKey(params), "prodClassNm", "prodClassCd", UseYn.ALL));
        // 터치키 분류 페이지별 스타일 코드 조회
        model.addAttribute("touchKeyStyleCd", convertToJson(touchkeyService.getTouchKeyPageStyleCd(touchKeyClassVO)));
        // 터치키 스타일 코드 목록 조회
        model.addAttribute("touchKeyStyleCdList", convertToJson(touchkeyService.getTouchKeyStyleCdList()));
        // 터치키 스타일 목록 조회
        model.addAttribute("touchKeyStyleList", convertToJson(touchkeyService.getTouchKeyStyleList(
            touchKeyStyleVO)));
        // 본사or매장의 터치키 환경 설정 값을 조회해서 셋팅
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("maxGroupRow", cmmEnvUtil.getHqEnvst(sessionInfoVO, "0041"));
        } else {
            model.addAttribute("maxGroupRow", cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1041"));
        }

        return RESULT_URI + "/touchKey";
    }

    /**
     * 판매 터치키 목록 조회 (상품정보 목록)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProductListForTouchKey(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        TouchKeyVO params = new TouchKeyVO();
        params.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        params.setStoreCd(sessionInfoVO.getStoreCd());
        params.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = touchkeyService.getProductListForTouchKey(params);

        return returnListJson(Status.OK, list);
    }

    /**
     * 판매 터치키 기존 설정 조회
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/touchKeyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTouchKeyXml(HttpServletRequest request, HttpSession session, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String xml = touchkeyService.getTouchKeyXml(sessionInfoVO);
        return new Result(Status.OK, xml);
    }

    /**
     * 판매 터치키 저장
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTouchKey(HttpServletRequest request, HttpSession session, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Result result = new Result(Status.FAIL);
        try {
            String xml =
                URLDecoder.decode(request.getParameter("xml"), "UTF-8").replace("\n", "&#xa;");
            result = touchkeyService.saveTouchkey(sessionInfoVO, XssPreventer.unescape(xml));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
