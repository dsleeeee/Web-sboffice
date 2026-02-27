package kr.co.solbipos.naverPlace.naverPlace.naverPlacePlusLink.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service.NaverPlaceApiVO;
import kr.co.solbipos.naverPlace.naverPlace.naverPlacePlusLink.service.NaverPlacePlusApiVO;
import kr.co.solbipos.naverPlace.naverPlace.naverPlacePlusLink.service.NaverPlacePlusLinkService;
import kr.co.solbipos.naverPlace.naverPlace.naverPlacePlusLink.service.NaverPlacePlusLinkVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name  : NaverPlacePlusLinkController.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버플레이스 플러스 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.02.23  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.02.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/naverPlace/naverPlace/naverPlacePlusLink")
public class NaverPlacePlusLinkController {

    private final SessionService sessionService;
    private final NaverPlacePlusLinkService naverPlacePlusLinkService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor Injection
     */
    @Autowired
    public NaverPlacePlusLinkController(SessionService sessionService, NaverPlacePlusLinkService naverPlacePlusLinkService) {
        this.sessionService = sessionService;
        this.naverPlacePlusLinkService = naverPlacePlusLinkService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        NaverPlacePlusLinkVO naverPlacePlusLinkVO = new NaverPlacePlusLinkVO();
        NaverPlacePlusApiVO naverPlacePlusApiVO = new NaverPlacePlusApiVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultMap2 = new HashMap<String, Object>();

        // 네.아.로 Unique ID 조회
        String uniqueId = naverPlacePlusLinkService.getNaverUniqueId(naverPlacePlusLinkVO, sessionInfoVO);
        model.addAttribute("uniqueId", uniqueId);

        if (uniqueId != null) {
            if (uniqueId != "") {
                // 동의여부 API 호출
                resultMap = naverPlacePlusLinkService.getAgreeYn(naverPlacePlusApiVO, sessionInfoVO);

                // 연동정보 조회
                DefaultMap<String> result = naverPlacePlusLinkService.getNaverStore(naverPlacePlusLinkVO, sessionInfoVO);

                if (result != null && result.size() > 0 && !result.isEmpty()) {
                    if (result.getStr("naverPlaceId") != null && result.getStr("naverPlaceId") != "") {
                        // 실제 연동이 됐는지 연동 조회 API 호출
                        naverPlacePlusApiVO.setProjections(""); // 초기화
                        naverPlacePlusApiVO.setPlaceId(result.getStr("naverPlaceId"));
                        resultMap2 = naverPlacePlusLinkService.getNaverLinkYn(naverPlacePlusApiVO, sessionInfoVO);
                    }
                }
            }
        }

        model.addAttribute("agreeYn", convertToJson(resultMap));
        model.addAttribute("linkYn", convertToJson(resultMap2));
        LOGGER.info("동의여부 API: " + resultMap);
        LOGGER.info("연동 조회 API: " + resultMap2);

        return "naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusLink";
    }

    /**
     * 네이버 로그인 state 값 저장
     *
     * @param naverPlacePlusLinkVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2026.02.06
     */
    @RequestMapping(value = "/saveNaverState.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveNaverState(@RequestBody NaverPlacePlusLinkVO naverPlacePlusLinkVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = naverPlacePlusLinkService.saveNaverState(naverPlacePlusLinkVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 네이버 로그인 성공 후, 네.아.로 Unique ID 저장
     *
     * @param
     * @return
     * @author 이다솜
     * @since 2026.02.06
     */
    @RequestMapping(value = "/saveNaverUniqueId.sb", method = RequestMethod.GET)
    public String viewPop(HttpServletRequest request, HttpServletResponse response, Model model) {

        NaverPlacePlusApiVO naverPlacePlusApiVO = new NaverPlacePlusApiVO();
        naverPlacePlusApiVO.setCode(request.getParameter("code"));
        naverPlacePlusApiVO.setState(request.getParameter("state"));

        int result = naverPlacePlusLinkService.saveNaverUniqueId(naverPlacePlusApiVO);

        // 연동 단계 파악을 위한 화면 정보 셋팅
        model.addAttribute("prePage", "login");
        return "naverPlace/naverPlace/naverPlacePlusLink/popup/naverPlacePlusPop";
    }

    /**
     * 동의여부확인 API 호출
     *
     * @param naverPlacePlusApiVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getAgreeYn.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgreeYn(NaverPlacePlusApiVO naverPlacePlusApiVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> resultMap = naverPlacePlusLinkService.getAgreeYn(naverPlacePlusApiVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 업체목록조회 API 호출
     *
     * @param naverPlacePlusApiVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getPlaceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPlaceList(NaverPlacePlusApiVO naverPlacePlusApiVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        List<Map<String, Object>> resultMap = naverPlacePlusLinkService.getPlaceList(naverPlacePlusApiVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 연동 해지 API
     *
     * @param naverPlacePlusApiVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/unMappingPlace.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result unMappingPlace(NaverPlacePlusApiVO naverPlacePlusApiVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> resultMap = naverPlacePlusLinkService.unMappingPlace(naverPlacePlusApiVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 팝업 화면 호출
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/naverPlacePlusPop.sb", method = RequestMethod.GET)
    public String naverPlacePlusPop(HttpServletRequest request, HttpServletResponse response, Model model) {

        //
        model.addAttribute("uniqueId", request.getParameter("uniqueId"));

        // 연동 단계 파악을 위한 화면 정보 셋팅
        model.addAttribute("prePage", "agree");
        return "naverPlace/naverPlace/naverPlacePlusLink/popup/naverPlacePlusPop";
    }
}
