package kr.co.solbipos.application.main.content.web;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.main.ContentService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.common.enums.MainSrchFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * 메인
 *
 * @author 김지은
 */

@Controller
@RequestMapping(value = "/application/main/content")
public class ContentController {

    @Autowired
    ContentService contentService;

    @Autowired
    SessionService sessionService;

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

        return "application/main/hedofcMain";
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
        
        /** 가상로그인시 ID 설정 */
        if ( request.getParameter("vLoginId") != null && request.getParameter("vLoginId").length() > 0 ) {
            model.addAttribute("vLoginId", request.getParameter("vLoginId"));
        }
        
        return "application/main/mrhstMain";
    }

}
