package kr.co.solbipos.mobile.application.main.content.web;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.common.service.ResrceInfoBaseVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.application.main.content.service.MobileContentService;
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

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @Class Name : MobileContentController.java
 * @Description : (모바일) 어플리케이션 > 메인 > 내용
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.27  김설아      최초생성
 *
 * @author 솔비포스 WEB개발팀 김설아
 * @since 2021.05.27
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/mobile/application/main/content")
public class MobileContentController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MobileContentService mobileContentService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public MobileContentController(MobileContentService mobileContentService, SessionService sessionService) {
        this.mobileContentService = mobileContentService;
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

        /** 총 매장수(전체, 오픈, 폐점, 중지, 데모) */
        List<DefaultMap<String>> storeCntList = mobileContentService.getMobileStoreCntList(sessionInfoVO);
        model.addAttribute("storeCntList", storeCntList);
//        System.out.println("storeCntList : "+storeCntList);

        /** 총 포스수(전체, 오픈, 폐점, 중지, 데모) */
        List<DefaultMap<String>> posCntList = mobileContentService.getMobilePosCntList(sessionInfoVO);
        model.addAttribute("posCntList", posCntList);

        /** 주간매출(매장수/포스수) */
        List<DefaultMap<String>> weekSaleList = mobileContentService.getMobileWeekSaleList(sessionInfoVO);
        model.addAttribute("weekSaleList", weekSaleList);

        /** 공지사항 */
        List<DefaultMap<String>> noticeList = mobileContentService.getMobileNoticeList(sessionInfoVO);
        model.addAttribute("noticeList", noticeList);

        /** 주간 POS 설치현황(신규설치/재설치) */
        List<DefaultMap<String>> weekPosInstList = mobileContentService.getMobileWeekPosInstList(sessionInfoVO);
        model.addAttribute("weekPosInstList", weekPosInstList);

        /** 날씨 */

        /** 주간 POS 설치 상위 대리점 */
        List<DefaultMap<String>> weekPosInstTopList = mobileContentService.getMobileWeekPosInstTopList(sessionInfoVO);
        model.addAttribute("weekPosInstTopList", weekPosInstTopList);

        return "mobile/application/main/mobileSystemMain";
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

        /** 총 매장수(전체, 오픈, 폐점, 중지, 데모) */
        List<DefaultMap<String>> storeCntList = mobileContentService.getMobileStoreCntList(sessionInfoVO);
        model.addAttribute("storeCntList", storeCntList);

        /** 총 포스수(전체, 오픈, 폐점, 중지, 데모) */
        List<DefaultMap<String>> posCntList = mobileContentService.getMobilePosCntList(sessionInfoVO);
        model.addAttribute("posCntList", posCntList);

        /** 주간매출(매장수/포스수) */
        List<DefaultMap<String>> weekSaleList = mobileContentService.getMobileWeekSaleList(sessionInfoVO);
        model.addAttribute("weekSaleList", weekSaleList);

        /** 공지사항 */
        List<DefaultMap<String>> noticeList = mobileContentService.getMobileNoticeList(sessionInfoVO);
        model.addAttribute("noticeList", noticeList);

        /** 주간 POS 설치현황(신규설치/재설치) */
        List<DefaultMap<String>> weekPosInstList = mobileContentService.getMobileWeekPosInstList(sessionInfoVO);
        model.addAttribute("weekPosInstList", weekPosInstList);

        /** 날씨 */

        /** 주간 매출 상위 가맹점 */
        List<DefaultMap<String>> weekSaleAgencyTopList = mobileContentService.getMobileWeekSaleAgencyTopList(sessionInfoVO);
        model.addAttribute("weekSaleAgencyTopList", weekSaleAgencyTopList);


        /** 가상로그인시 세션ID 설정 */
        if ( request.getParameter("sid") != null && request.getParameter("sid").length() > 0 ) {
            model.addAttribute("sid", request.getParameter("sid"));
        }

        return "mobile/application/main/mobileAgencyMain";
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

        /** 총 매장수(전체, 오픈, 폐점, 중지, 데모) */
        List<DefaultMap<String>> storeCntList = mobileContentService.getMobileStoreCntList(sessionInfoVO);
        model.addAttribute("storeCntList", storeCntList);

        /** 총 포스수(전체, 오픈, 폐점, 중지, 데모) */
        List<DefaultMap<String>> posCntList = mobileContentService.getMobilePosCntList(sessionInfoVO);
        model.addAttribute("posCntList", posCntList);

        /** 공지사항 */
        List<DefaultMap<String>> noticeList = mobileContentService.getMobileNoticeList(sessionInfoVO);
        model.addAttribute("noticeList", noticeList);

        /** 매출현황 */
        List<DefaultMap<String>> saleWeekList = mobileContentService.getMobileSaleWeekList(sessionInfoVO);
        model.addAttribute("saleWeekList", saleWeekList);

        /** 매출 상위 상품 */
        List<DefaultMap<String>> saleProdWeekList = mobileContentService.getMobileSaleProdWeekList(sessionInfoVO);
        model.addAttribute("saleProdWeekList", saleProdWeekList);

        /** 매출 상위 가맹점 1주일 */
        List<DefaultMap<String>> saleStoreWeekList = mobileContentService.getMobileSaleStoreWeekList(sessionInfoVO);
        model.addAttribute("saleStoreWeekList", saleStoreWeekList);

        /** 날씨 */


        /** 가상로그인시 세션ID 설정 */
        if ( request.getParameter("sid") != null && request.getParameter("sid").length() > 0 ) {
            model.addAttribute("sid", request.getParameter("sid"));
        }

        return "mobile/application/main/mobileHedofcMain"; // 그래프
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

        /** 오늘의 매출건수 */
        List<DefaultMap<String>> daySaleCntList = mobileContentService.getMobileDaySaleCntList(sessionInfoVO);
        model.addAttribute("daySaleCntList", daySaleCntList);

        /** 오늘의 매출금액 */
        List<DefaultMap<String>> daySaleAmtList = mobileContentService.getMobileDaySaleAmtList(sessionInfoVO);
        model.addAttribute("daySaleAmtList", daySaleAmtList);

        /** 공지사항 */
        List<DefaultMap<String>> noticeList = mobileContentService.getMobileNoticeList(sessionInfoVO);
        model.addAttribute("noticeList", noticeList);

        /** 매출현황 */
        List<DefaultMap<String>> saleWeekList = mobileContentService.getMobileSaleWeekList(sessionInfoVO);
        model.addAttribute("saleWeekList", saleWeekList);

        /** 매출 상위 상품 */
        List<DefaultMap<String>> saleProdWeekList = mobileContentService.getMobileSaleProdWeekList(sessionInfoVO);
        model.addAttribute("saleProdWeekList", saleProdWeekList);

        /** 날씨 */


        /** 가상로그인시 세션ID 설정 */
        if ( request.getParameter("sid") != null && request.getParameter("sid").length() > 0 ) {
            model.addAttribute("sid", request.getParameter("sid"));
        }

        return "mobile/application/main/mobileMrhstMain"; // 그래프
    }

}
