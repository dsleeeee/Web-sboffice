package kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service.NaverOrderApiVO;
import kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service.NaverOrderLinkService;
import kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service.NaverOrderLinkVO;
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
import java.util.HashMap;
import java.util.Map;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name  : NaverOrderLinkController.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버 주문연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.13  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.13
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/naverPlace/naverPlace/naverOrderLink")
public class NaverOrderLinkController {

    private final SessionService sessionService;
    private final NaverOrderLinkService naverOrderLinkService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public NaverOrderLinkController(SessionService sessionService, NaverOrderLinkService naverOrderLinkService) {
        this.sessionService = sessionService;
        this.naverOrderLinkService = naverOrderLinkService;
    }

    /**
     * 페이지 이동
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        NaverOrderLinkVO naverOrderLinkVO = new NaverOrderLinkVO();
        NaverOrderApiVO naverOrderApiVO = new NaverOrderApiVO();
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> resultMap2 = new HashMap<>();

        // 개발/운영 Api URL 조회
        naverOrderLinkVO.setApiInfo("NAVER_PLACE_POP_URL");
        naverOrderLinkVO.setApiUrl("API_URL");
        naverOrderLinkVO.setApiKey("");
        DefaultMap<Object> apiInfo = naverOrderLinkService.getApiUrl(naverOrderLinkVO, sessionInfoVO);
        model.addAttribute("popUrl", apiInfo.getStr("apiUrl"));

        // 네.아.로 Unique ID 조회
        String uniqueId = naverOrderLinkService.getNaverUniqueId(naverOrderLinkVO, sessionInfoVO);
        model.addAttribute("uniqueId", uniqueId);

        if (uniqueId != null && !uniqueId.isEmpty()) {

            naverOrderApiVO.setUniqueId(uniqueId);
            naverOrderApiVO.setChannelType("NAVER");

            // 동의여부확인 API 호출
            resultMap = naverOrderLinkService.getAgreeYn(naverOrderApiVO, sessionInfoVO);

            // 매장 단건조회 API 호출(주문연동 매장 유무 확인)
            resultMap2 = naverOrderLinkService.getPlace(naverOrderApiVO, sessionInfoVO);
        }

        model.addAttribute("agreeYn", convertToJson(resultMap));
        model.addAttribute("linkYn", convertToJson(resultMap2));
        LOGGER.info("동의여부확인 API: " + resultMap);
        LOGGER.info("주문연동여부: " + resultMap2);

        return "naverPlace/naverPlace/naverOrderLink/naverOrderLink";
    }

    /**
     * 상태 체크
     */
    @RequestMapping(value = "/getStatus.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStatus(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        NaverOrderLinkVO naverOrderLinkVO = new NaverOrderLinkVO();
        NaverOrderApiVO naverOrderApiVO = new NaverOrderApiVO();
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> resultMap2 = new HashMap<>();

        // 네.아.로 Unique ID 조회
        String uniqueId = naverOrderLinkService.getNaverUniqueId(naverOrderLinkVO, sessionInfoVO);

        if (uniqueId != null && !uniqueId.isEmpty()) {

            naverOrderApiVO.setUniqueId(uniqueId);
            naverOrderApiVO.setChannelType("NAVER");

            // 동의여부확인 API 호출
            resultMap = naverOrderLinkService.getAgreeYn(naverOrderApiVO, sessionInfoVO);

            // 매장 단건조회 API 호출(주문연동 매장 유무 확인)
            resultMap2 = naverOrderLinkService.getPlace(naverOrderApiVO, sessionInfoVO);
        }

        DefaultMap<String> resultMap3 = new DefaultMap<>();
        resultMap3.put("uniqueId", uniqueId);
        resultMap3.put("agreeYn", convertToJson(resultMap));
        resultMap3.put("linkYn", convertToJson(resultMap2));

        return ReturnUtil.returnJson(Status.OK, resultMap3);
    }

    /**
     * 업체리스트조회 API 호출
     */
    @RequestMapping(value = "/getPlaceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPlaceList(NaverOrderApiVO naverOrderApiVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> resultMap = naverOrderLinkService.getPlaceList(naverOrderApiVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 매장등록 API 호출
     */
    @RequestMapping(value = "/regPlace.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result regPlace(NaverOrderApiVO naverOrderApiVO, HttpServletRequest request,
                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> resultMap = naverOrderLinkService.regPlace(naverOrderApiVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 매장수정 API 호출
     */
    @RequestMapping(value = "/modPlace.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result modPlace(NaverOrderApiVO naverOrderApiVO, HttpServletRequest request,
                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> resultMap = naverOrderLinkService.modPlace(naverOrderApiVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 매장 단건조회 API 호출
     */
    @RequestMapping(value = "/getPlace.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPlace(NaverOrderApiVO naverOrderApiVO, HttpServletRequest request,
                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> resultMap = naverOrderLinkService.getPlace(naverOrderApiVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }













    /**
     * 연동 팝업 화면 호출
     */
    @RequestMapping(value = "/naverOrderPop.sb", method = RequestMethod.GET)
    public String naverOrderPop(HttpServletRequest request, HttpServletResponse response, Model model) {

        // 연동 단계 파악을 위한 화면 정보 셋팅
        model.addAttribute("prePage", "agree");
        return "naverPlace/naverPlace/naverOrderLink/popup/naverOrderPop";
    }






















}
