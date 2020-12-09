package kr.co.solbipos.membr.anals.dayMembr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.dayMembr.service.DayMembrService;
import kr.co.solbipos.membr.anals.dayMembr.service.DayMembrVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
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
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : DayMembrController.java
 * @Description : 회원관리 > 회원분석 > 일자별회원 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.08.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019. 08.13
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/membr/anals/dayMembr/")
public class DayMembrController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DayMembrService service;
    private final SessionService sessionService;
    private final DayService dayService;

    /** Constructor Injection */
    @Autowired
    public DayMembrController(DayMembrService service, SessionService sessionService, DayService dayService) {
        this.service = service;
        this.sessionService = sessionService;
        this.dayService = dayService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "dayMembr/dayMembrView.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = dayService.getPayColList(dayVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        return "membr/anals/dayMembr/dayMembrView";
    }

    /**
     * 일자별회원 구매내역 조회
     *
     * @param dayMembrVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "dayMembr/getDayMembrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayMembrList(DayMembrVO dayMembrVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//        System.out.println("test : " + dayMembrVO.getEndDate());

        List<DefaultMap<Object>> result = service.getDayMembrList(dayMembrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayMembrVO);
    }

    /**
     * 매출정보 상세조회 - 팝업
     *
     * @param dayMembrVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "dayMembr/getDayMembrPurchsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayMembrPurchsList(DayMembrVO dayMembrVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getDayMembrPurchsList(dayMembrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayMembrVO);
    }

    /**
     * 회원정보 상세조회 - 팝업
     *
     * @param dayMembrVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "dayMembr/getDayMembrDetail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayMembrDetail(DayMembrVO dayMembrVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> membrInfo = service.getDayMembrDetail(dayMembrVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("membrInfo", membrInfo);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 회원정보 매출 상세조회 - 팝업
     *
     * @param dayMembrVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "dayMembr/getDayMembrDetailPurchsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayMembrDetailPurchsList(DayMembrVO dayMembrVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getDayMembrDetailPurchsList(dayMembrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayMembrVO);
    }

}
