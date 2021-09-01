package kr.co.solbipos.application.pos.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.validate.Login;
import kr.co.solbipos.application.common.service.ResrceInfoBaseVO;
import kr.co.solbipos.application.pos.service.MemberVO;
import kr.co.solbipos.application.pos.service.SimpleMemberJoinService;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.ProdAuthEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyService;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyStyleVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import kr.co.solbipos.application.pos.posBoard.service.PosBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.List;

import static kr.co.common.utils.HttpUtils.getClientIp;
import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @Class Name : SimpleMemberJoinController.java
 * @Description : POS 화면에서 간편 회원가입
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 08.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/application/pos/")
public class SimpleMemberJoinController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SimpleMemberJoinService service;
    @Autowired
    SessionService sessionService;
    @Autowired
    CmmCodeUtil cmmCodeUtil;
    @Autowired
    AuthService authService;
    @Autowired
    MessageService messageService;
    @Autowired
    CmmEnvUtil cmmEnvUtil;

    @Autowired
    PosBoardService posBoardService;
    @Autowired
    TouchKeyService touchKeyService;
    @Autowired
    ProdService prodService;

    private final String POS_MEMBER_FG_ENVST_CD = "1067"; // 포스회원등록 구분

    /** 화면이동 테스트
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "smjtest.sb", method = RequestMethod.GET)
    public String smjtest(HttpServletRequest request, HttpServletResponse response,
                       Model model ) {


        return "application/pos/smjTest";

    }

    /** 화면이동 테스트
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "agridTest.sb", method = RequestMethod.GET)
    public String agridTest(HttpServletRequest request, HttpServletResponse response,
        Model model) {

        return "application/pos/agridTest";

    }

    /**
     * 상품 목록 조회 화면
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "simpleMemberJoin.sb", method = RequestMethod.GET)
    public String excpForwardView(HttpServletRequest request, HttpServletResponse response,
        Model model) {
        return "application/pos/simpleMemberJoin";
    }


    /**
     * TODO
     * POS 화면에서 띄우는건 메뉴등이 필요없기때문에 tiles 에 예외등록해두고 우선은 해당 컨트롤러에서 STORE_CD 와 HW_AUTH_KEY 로 인증체크 후 세션을 맺도록 함.
     * POS 화면 웹 로그인
     * @param sessionInfoVO
     * @param bindingResult
     * @param request
     * @param response
     * @param model
     * @return
     */
//    @RequestMapping(value = "posLogin.sb", method = RequestMethod.POST)
    @RequestMapping(value = "posLogin.sb", method = {RequestMethod.POST, RequestMethod.GET})
    public String posLoginProcess(@Validated(Login.class) SessionInfoVO sessionInfoVO,
                                  BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
                                  Model model) {

        LOGGER.info("posLogin start");

        String returnUrl = "";

        //if(!isEmpty(sessionInfoVO.getHwAuthKey())) {
        if(!isEmpty(request.getParameter("storeCd")) && !isEmpty(request.getParameter("hwAuthKey")) && !isEmpty(request.getParameter("url"))) {
            LOGGER.info("posLogin store : {} , hwAuthKey : {} , url : {}", request.getParameter("storeCd"), request.getParameter("hwAuthKey"), request.getParameter("url"));

            sessionInfoVO.setLoginIp(getClientIp(request));
            sessionInfoVO.setBrwsrInfo(request.getHeader("User-Agent"));

            // 로그인 시도
            SessionInfoVO posSi = authService.posLogin(sessionInfoVO);
            // 세션 생성
            sessionService.setSessionInfo(request, response, posSi);

            returnUrl = "application/pos/"+request.getParameter("url");

            // 포스회원등록 구분 환경변수값(1067) 조회
            StoreEnvVO storeEnvVO = new StoreEnvVO();
            storeEnvVO.setStoreCd(sessionInfoVO.getStoreCd());
            storeEnvVO.setEnvstCd(POS_MEMBER_FG_ENVST_CD);

            model.addAttribute("posMemberFgEnvstVal", service.getEnvstVal(storeEnvVO).toString());
            
            // 세션 정보 추가(매장구성세트상품, 판매터치키에서 사용하는 세션 정보추가)
            sessionInfoVO.setStartDate(new SimpleDateFormat( "yyyyMMdd").format (System.currentTimeMillis()));
            sessionInfoVO.setEndDate(new SimpleDateFormat( "yyyyMMdd").format (System.currentTimeMillis()));
            sessionInfoVO.setOrgnFg(OrgnFg.STORE);
            sessionInfoVO.setHqOfficeCd(service.getHqOfficeCd(storeEnvVO).toString());

            // POS 화면에서 게시판(포스용)
            if(request.getParameter("url").equals("posBoard/posBoard")){
                LOGGER.info("posLogin userId : {} , readYn : {} , noticePopupYn : {}", request.getParameter("userId"), request.getParameter("readYn"), request.getParameter("noticePopupYn"));

                /** 포스에서 받아올 값 */
                // PosBoardVO posBoardVO = new PosBoardVO();
                // posBoardVO.setReadYn(readYn);
                // 열람구분(포스에서 받는 수신여부)
                String readYn = request.getParameter("readYn");
                model.addAttribute("readYn", readYn);
                // 공지팝업 여부(미열람 공지사항 띄움)
                String noticePopupYn = request.getParameter("noticePopupYn");
                model.addAttribute("noticePopupYn", noticePopupYn);

                /** userId 체크 */
                if(isEmpty(request.getParameter("userId"))) {
                    throw new AuthenticationException(messageService.get("cmm.access.denied"), "/application/pos/posBoard/boardMenuAuth.sb");
                } else {
                     // 사용자ID(포스에서 받는 사용자ID)
                     String userId = request.getParameter("userId");
                     model.addAttribute("userId", userId);
                }

                /** 공지사항 페이지이동 권한체크 */
                String boardAuth = posBoardService.getBoardAuth(sessionInfoVO, "000328"); // 메뉴코드 추가 공지사항(000328)
                LOGGER.info("posLogin boardAuth : {}", boardAuth);
                if(boardAuth.equals("0")) {
                    throw new AuthenticationException(messageService.get("cmm.access.denied"), "/application/pos/posBoard/boardMenuAuth.sb");
                }
            }
            // POS 화면에서 매장구성세트상품(포스용)
            else if(request.getParameter("url").equals("posSideMenu/posSideMenu")){
                LOGGER.info("posLogin userId : {} , posSideMenu", request.getParameter("userId"));

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

                // 본사or매장의 터치키 환경 설정 값을 조회해서 셋팅
                if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
                    model.addAttribute("maxClassRow", cmmEnvUtil.getHqEnvst(sessionInfoVO, "0041"));
                } else {
                    model.addAttribute("maxClassRow", cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1041"));
                }

                // 터치키 관련 권한정보 가져오기 : 2019-08-08 이다솜
                String touchKeyEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0017"));
                model.addAttribute("touchKeyEnvstVal", touchKeyEnvstVal);

                // 터치키 그룹 가져오기
                List<DefaultMap<String>> touchKeyGrpList = touchKeyService.getTouchKeyGrp(params, sessionInfoVO);
                model.addAttribute("touchKeyGrp", convertToJson(touchKeyGrpList));

                /**
                 *  상품정보관리
                 * */

                ProdVO prodVO = new ProdVO();

                // 상품생성설정
                ProdAuthEnvFg prodAuthEnvstVal = ProdAuthEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0042"));

                // 상품코드 채번방식
                ProdNoEnvFg prodNoEnvFg;
                if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                    prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0028"));
                }else{
                    prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0028"));
                }

                model.addAttribute("prodAuthEnvstVal", prodAuthEnvstVal);
                model.addAttribute("prodNoEnvFg", prodNoEnvFg);

                model.addAttribute("brandList", convertToJson(prodService.getBrandList(prodVO, sessionInfoVO)));

                // 매장상품제한구분 환경변수 값(환경변수 1100 사용)
                String sideEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1100"));
                model.addAttribute("sideEnvstVal", sideEnvstVal);

                model.addAttribute("startDate", sessionInfoVO.getStartDate());
                model.addAttribute("endDate", sessionInfoVO.getEndDate());
                model.addAttribute("orgnFg", sessionInfoVO.getOrgnFg().getCode());
                model.addAttribute("hqOfficeCd", sessionInfoVO.getHqOfficeCd());
                model.addAttribute("storeCd", sessionInfoVO.getStoreCd());

                if ( sessionInfoVO != null && sessionInfoVO.getUserId() != null ) {
                    // jsp > sessionscope 로 쓸수 있게 httpsession 에 세팅
                    HttpSession session = request.getSession();
                    session.setAttribute("sessionInfo", sessionInfoVO);
                }

                /** userId 체크 */
                if(isEmpty(request.getParameter("userId"))) {
                    throw new AuthenticationException(messageService.get("cmm.access.denied"), "/application/pos/posSideMenu/posSideMenu.sb");
                } else {
                    // 사용자ID(포스에서 받는 사용자ID)
                    String userId = request.getParameter("userId");
                    model.addAttribute("userId", userId);
                }

                /** 매장구성세트상품 페이지이동 권한체크 */
                String sideAuth = posBoardService.getBoardAuth(sessionInfoVO, "000136"); // 메뉴코드 추가 사이드메뉴관리(000136)
               LOGGER.info("posLogin sideAuth : {}", sideAuth);

                String prodAuth = posBoardService.getBoardAuth(sessionInfoVO, "000128"); // 메뉴코드 추가 상품정보관리(000128)
                LOGGER.info("posLogin prodAuth : {}", prodAuth);

                String touchKeyAuth = posBoardService.getBoardAuth(sessionInfoVO, "000135"); // 메뉴코드 추가 판매터치키등록(000135)
                LOGGER.info("posLogin touchKeyAuth : {}", touchKeyAuth);

                if(sideAuth.equals("0") && prodAuth.equals("0") && touchKeyAuth.equals("0")) {
                    throw new AuthenticationException(messageService.get("cmm.access.denied"), "/application/pos/posSideMenu/posSideMenu.sb");
                }
            }
            // POS 화면에서 판매터치키(포스용)
            else if(request.getParameter("url").equals("posTouchKey/posTouchKey")){
                LOGGER.info("posLogin userId : {} , posTouchKey", request.getParameter("userId"));

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

                // 본사or매장의 터치키 환경 설정 값을 조회해서 셋팅
                if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
                    model.addAttribute("maxClassRow", cmmEnvUtil.getHqEnvst(sessionInfoVO, "0041"));
                } else {
                    model.addAttribute("maxClassRow", cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1041"));
                }

                // 터치키 관련 권한정보 가져오기 : 2019-08-08 이다솜
                String envstCd = "0017";
                String touchKeyEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, envstCd));
                model.addAttribute("touchKeyEnvstVal", touchKeyEnvstVal);

                model.addAttribute("startDate", sessionInfoVO.getStartDate());
                model.addAttribute("endDate", sessionInfoVO.getEndDate());
                model.addAttribute("orgnFg", sessionInfoVO.getOrgnFg().getCode());
                model.addAttribute("hqOfficeCd", sessionInfoVO.getHqOfficeCd());
                model.addAttribute("storeCd", sessionInfoVO.getStoreCd());

                if ( sessionInfoVO != null && sessionInfoVO.getUserId() != null ) {
                    // jsp > sessionscope 로 쓸수 있게 httpsession 에 세팅
                    HttpSession session = request.getSession();
                    session.setAttribute("sessionInfo", sessionInfoVO);
                }

                // 터치키 그룹 가져오기
                List<DefaultMap<String>> touchKeyGrpList = touchKeyService.getTouchKeyGrp(params, sessionInfoVO);
                model.addAttribute("touchKeyGrp", convertToJson(touchKeyGrpList));

                /** userId 체크 */
                if(isEmpty(request.getParameter("userId"))) {
                    throw new AuthenticationException(messageService.get("cmm.access.denied"), "/application/pos/posTouchKey/posTouchKey.sb");
                } else {
                    // 사용자ID(포스에서 받는 사용자ID)
                    String userId = request.getParameter("userId");
                    model.addAttribute("userId", userId);
                }

                String touchKeyAuth = posBoardService.getBoardAuth(sessionInfoVO, "000135"); // 메뉴코드 추가 판매터치키등록(000135)
                LOGGER.info("posLogin touchKeyAuth : {}", touchKeyAuth);

                if(touchKeyAuth.equals("0")) {
                    throw new AuthenticationException(messageService.get("cmm.access.denied"), "/application/pos/posTouchKey/posTouchKey.sb");
                }
            }
            // POS 화면에서 주방프린터상품연결(포스용)
            else if(request.getParameter("url").equals("posKitchenPrint/posKitchenPrint")){
                LOGGER.info("posLogin userId : {} , posKitchenPrint", request.getParameter("userId"));

                model.addAttribute("orgnFg", sessionInfoVO.getOrgnFg().getCode());
                model.addAttribute("hqOfficeCd", sessionInfoVO.getHqOfficeCd());
                model.addAttribute("storeCd", sessionInfoVO.getStoreCd());

                if ( sessionInfoVO != null && sessionInfoVO.getUserId() != null ) {
                    // jsp > sessionscope 로 쓸수 있게 httpsession 에 세팅
                    HttpSession session = request.getSession();
                    session.setAttribute("sessionInfo", sessionInfoVO);
                }

                /** userId 체크 */
                if(isEmpty(request.getParameter("userId"))) {
                    throw new AuthenticationException(messageService.get("cmm.access.denied"), "/application/pos/posKitchenPrint/posKitchenPrint.sb");
                } else {
                    // 사용자ID(포스에서 받는 사용자ID)
                    String userId = request.getParameter("userId");
                    model.addAttribute("userId", userId);
                }

                String touchKeyAuth = posBoardService.getBoardAuth(sessionInfoVO, "000135"); // 메뉴코드 추가 판매터치키등록(000135)
                LOGGER.info("posLogin touchKeyAuth : {}", touchKeyAuth);

                if(touchKeyAuth.equals("0")) {
                    throw new AuthenticationException(messageService.get("cmm.access.denied"), "/application/pos/posKitchenPrint/posKitchenPrint.sb");
                }
            }
        }
        else {
            throw new AuthenticationException(messageService.get("login.pos.error"), "/error/application/pos/403.sb");
        }

        return returnUrl;
    }

    /**
     * 회원 등록
     * @param   memberVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  안동관
     * @since   2018. 08. 14.
     */
    @RequestMapping(value = "simpleMemberJoin/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody MemberVO memberVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.save(memberVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }


}
