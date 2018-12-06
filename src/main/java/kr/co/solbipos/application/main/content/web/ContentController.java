package kr.co.solbipos.application.main.content.web;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.main.ContentService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.security.EncUtil;
import kr.co.solbipos.application.common.enums.MainSrchFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
* @Class Name : ContentController.java
* @Description : 어플리케이션 > 메인 > 내용
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/application/main/content")
public class ContentController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ContentService contentService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public ContentController(ContentService contentService, SessionService sessionService) {
        this.contentService = contentService;
        this.sessionService = sessionService;
    }

    /**
     * 메인페이지 (유저권한타입 : 시스템(SYSTEM))
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "sys.sb", method = RequestMethod.GET)
    public String mainSYSTEM(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        /** 총 매장수(전체, 오픈, 폐점, 데모) */
        /** 총 포스수(전체, 오픈, 폐점, 데모) */
        /** 주간 매출(매장수, 포스수) */
        /** 주간 포스 설치현황(신규 설치, 재설치) */
        /** 주간 포스 설치 상위 대리점 */
        /** 날씨 */
        /** 공지사항 */
        List<DefaultMap<String>> noticeList = contentService.getNotice(sessionInfoVO);

        model.addAttribute("noticeList", noticeList);

        String pUserPwd = EncUtil.setEncSHA256("ckp" + "0000");  // 포스 패스워드

        return "application/main/systemMain";
    }


    /**
     * 메인페이지 (유저권한타입 : 총판/대리점(AGENCY))
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "agency.sb", method = RequestMethod.GET)
    public String mainAGENCY(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        /** 총 매장수(전체, 오픈, 폐점) */
        /** 총 포스수(전체, 오픈, 폐점) */
        /** 주간 매출(매장수, 포스수) */
        /** 주간 설치현황(신규 설치, 재설치) */
        /** 주간 매출 상위 가맹점 */
        /** 날씨 */
        /** 공지사항 */
        List<DefaultMap<String>> noticeList = contentService.getNotice(sessionInfoVO);

        model.addAttribute("noticeList", noticeList);

        /** 가상로그인시 세션ID 설정 */
        if ( request.getParameter("sid") != null && request.getParameter("sid").length() > 0 ) {
            model.addAttribute("sid", request.getParameter("sid"));
        }

        return "application/main/agencyMain";
    }


    /**
     * 메인페이지 (유저권한타입 : 본사(HEDOFC))
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "hq.sb", method = RequestMethod.GET)
    public String mainHEDOFC(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        /** 총 매장수, 총 포스수, 주간 폐점매장 */
        /** 매출현황 날짜 select box */
        List<Map<String,String>> dateSelList1 = contentService.getDateSelList(MainSrchFg.TYPE1);
        List<Map<String,String>> dateSelList2 = contentService.getDateSelList(MainSrchFg.TYPE2);
        /** 매출현황 (일별, 요일벌(1개월), 월별(1년)) */
        /** 메츨 상품 순위 (당일, 1주일, 1개월) */
        /** 매출 상위 가맹점 */
        /** 수발주 정보 */
        /** 회원 현황 */
        /** 공지사항 */
        List<DefaultMap<String>> noticeList = contentService.getNotice(sessionInfoVO);

        model.addAttribute("dateSelList1", dateSelList1);
        model.addAttribute("dateSelList2", dateSelList2);
        model.addAttribute("noticeList", noticeList);

        /** 가상로그인시 세션ID 설정 */
        if ( request.getParameter("sid") != null && request.getParameter("sid").length() > 0 ) {
            model.addAttribute("sid", request.getParameter("sid"));
        }

//        return "application/main/hedofcMain";
        return "application/main/hedofcMain_test";
    }


    /**
     * 메인페이지 (유저권한타입 : 매장(MRHST), 가맹점)
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "store.sb", method = RequestMethod.GET)
    public String mainMRHST(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        /** 매출현황 날짜 select box */
        List<Map<String,String>> dateSelList1 = contentService.getDateSelList(MainSrchFg.TYPE1);
        List<Map<String,String>> dateSelList2 = contentService.getDateSelList(MainSrchFg.TYPE2);
        /** 오늘의 매출건수 (총 건수, 신용카드, 현금, 현금영수증, 기타) */
        /** 오늘의 매출금액 (총 금액, 신용카드, 현금, 현금영수증, 기타) */
        /** 상품매출 top10 */
        /** 날씨 */
        /** 공지사항 */
        List<DefaultMap<String>> noticeList = contentService.getNotice(sessionInfoVO);

        model.addAttribute("dateSelList1", dateSelList1);
        model.addAttribute("dateSelList2", dateSelList2);
        model.addAttribute("noticeList", noticeList);

        /** 가상로그인시 세션ID 설정 */
        if ( request.getParameter("sid") != null && request.getParameter("sid").length() > 0 ) {
            model.addAttribute("sid", request.getParameter("sid"));
        }

        //return "application/main/mrhstMain";
        return "application/main/mrhstMain_test";
    }

}
