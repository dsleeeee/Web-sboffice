package kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service.NaverPlaceApiVO;
import kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service.NaverPlaceLinkService;
import kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service.NaverPlaceLinkVO;
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
import java.util.List;
import java.util.Map;

/**
 * @Class Name  : NaverPlaceLinkController.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버플레이스 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.27  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.01.27
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/naverPlace/naverPlace/naverPlaceLink")
public class NaverPlaceLinkController {

    private final SessionService sessionService;
    private final NaverPlaceLinkService naverPlaceLinkService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor Injection
     */
    @Autowired
    public NaverPlaceLinkController(SessionService sessionService, NaverPlaceLinkService naverPlaceLinkService) {
        this.sessionService = sessionService;
        this.naverPlaceLinkService = naverPlaceLinkService;
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

        return "naverPlace/naverPlace/naverPlaceLink/naverPlaceLink";
    }

    /**
     * 인증 API Access Token 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2021.04.01
     */
    @RequestMapping(value = "/getAccessToken.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAccessToken(HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        Map<String, Object> resultMap = naverPlaceLinkService.getAccessToken();

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 네이버 로그인 state 값 저장
     *
     * @param naverPlaceLinkVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2026.02.06
     */
    @RequestMapping(value = "/saveNaverState.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveNaverState(@RequestBody NaverPlaceLinkVO naverPlaceLinkVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = naverPlaceLinkService.saveNaverState(naverPlaceLinkVO, sessionInfoVO);

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

        NaverPlaceApiVO naverPlaceApiVO = new NaverPlaceApiVO();
        naverPlaceApiVO.setCode(request.getParameter("code"));
        naverPlaceApiVO.setState(request.getParameter("state"));

        int result = naverPlaceLinkService.saveNaverUniqueId(naverPlaceApiVO);

        return "naverPlace/naverPlace/naverPlaceLink/naverPlacePop";
    }

    /**
     * 동의여부확인 API 호출
     *
     * @param naverPlaceApiVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getAgreeYn.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgreeYn(NaverPlaceApiVO naverPlaceApiVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> resultMap = naverPlaceLinkService.getAgreeYn(naverPlaceApiVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 업체목록조회 API 호출
     *
     * @param naverPlaceApiVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getPlaceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPlaceList(NaverPlaceApiVO naverPlaceApiVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<Map<String, Object>> resultMap = naverPlaceLinkService.getPlaceList(naverPlaceApiVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 업체 등록/수정 API 호출
     *
     * @param naverPlaceApiVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/savePlace.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePlace(NaverPlaceApiVO naverPlaceApiVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> resultMap = naverPlaceLinkService.savePlace(naverPlaceApiVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 연동 추가 API
     *
     * @param naverPlaceApiVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/mappingPlace.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result mappingPlace(NaverPlaceApiVO naverPlaceApiVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> resultMap = naverPlaceLinkService.mappingPlace(naverPlaceApiVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 연동 해지 API
     *
     * @param naverPlaceApiVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/unMappingPlace.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result unMappingPlace(NaverPlaceApiVO naverPlaceApiVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> resultMap = naverPlaceLinkService.unMappingPlace(naverPlaceApiVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 업종 조회 API
     *
     * @param naverPlaceApiVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getBusinessCategory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBusinessCategory(NaverPlaceApiVO naverPlaceApiVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<Map<String, Object>> resultMap = naverPlaceLinkService.getBusinessCategory(naverPlaceApiVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 팝업창 닫기용 화면
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewPop2.sb", method = RequestMethod.GET)
    public String viewPop2(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "naverPlace/naverPlace/naverPlaceLink/naverPlacePop";
    }

}
