package kr.co.solbipos.application.controller.main;

import static kr.co.solbipos.utils.spring.StringUtil.convertToJson;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import kr.co.solbipos.application.domain.board.total.Total;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.service.main.ContentService;
import kr.co.solbipos.service.session.SessionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

        SessionInfo sessionInfo = sessionService.getSessionInfo(request);

        // 총 매장수(전체, 오픈, 폐점, 데모)
        
        // 총 포스수(전체, 오픈, 폐점, 데모)
        
        // 주간 매출(매장수, 포스수)
        
        // 주간 포스 설치현황(신규 설치, 재설치)
        
        // 주간 포스 설치 상위 대리점
        
        // 날씨
        
        // 공지사항
        List<Total> noticeList = contentService.getNotice(sessionInfo);
        
        model.addAttribute("noticeList", convertToJson(noticeList));
        
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

        SessionInfo sessionInfo = sessionService.getSessionInfo(request);

        // 총 매장수(전체, 오픈, 폐점)
        
        // 총 포스수(전체, 오픈, 폐점)
        
        // 주간 매출(매장수, 포스수)
        
        // 주간 설치현황(신규 설치, 재설치)
        
        // 주간 매출 상위 가맹점
        
        // 날씨
        
        // 공지사항
        List<Total> noticeList = contentService.getNotice(sessionInfo);
        
        model.addAttribute("noticeList", convertToJson(noticeList));
        
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

        SessionInfo sessionInfo = sessionService.getSessionInfo(request);

        // 총 매장수, 총 포스수, 주간 폐점매장
        
        // 매출현황 날짜 select box
        List<Map<String,String>> dateSelList1 = contentService.getDateSelList("1");
        List<Map<String,String>> dateSelList2 = contentService.getDateSelList("2");
        
        // 매출현황 (일별, 요일벌(1개월), 월별(1년))
        
        // 메츨 상품 순위 (당일, 1주일, 1개월)
        
        // 매출 상위 가맹점
        
        // 수발주 정보
        
        // 회원 현황
        
        // 공지사항
        List<Total> noticeList = contentService.getNotice(sessionInfo);

        model.addAttribute("dateSelList1", convertToJson(dateSelList1));
        model.addAttribute("dateSelList2", convertToJson(dateSelList2));
        model.addAttribute("noticeList", convertToJson(noticeList));

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

        SessionInfo sessionInfo = sessionService.getSessionInfo(request);

        // 매출현황 날짜 select box
        List<Map<String,String>> dateSelList1 = contentService.getDateSelList("1");
        List<Map<String,String>> dateSelList2 = contentService.getDateSelList("2");

        // 오늘의 매출건수 (총 건수, 신용카드, 현금, 현금영수증, 기타)
        
        // 오늘의 매출금액 (총 금액, 신용카드, 현금, 현금영수증, 기타)
        
        // 매출현황(그래프용)
        
        // 상품매출 top10
        
        // 날씨
        
        // 공지사항
        List<Total> noticeList = contentService.getNotice(sessionInfo);
        
        model.addAttribute("dateSelList1", convertToJson(dateSelList1));
        model.addAttribute("dateSelList2", convertToJson(dateSelList2));
        model.addAttribute("noticeList", convertToJson(noticeList));
        
        return "application/main/mrhstMain";
    }
    
}
