package kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service.NaverPlaceLinkService;
import kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service.NaverPlaceLinkVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

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
     * @param naverPlaceLinkVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getAccessToken.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAccessToken(NaverPlaceLinkVO naverPlaceLinkVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        Map<String, Object> resultMap = naverPlaceLinkService.getAccessToken(naverPlaceLinkVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 네이버 로그인 성공 후, 네.아.로 Unique ID 저장
     * @param naverPlaceLinkVO
     * @return
     */
    @GetMapping(value = "/saveNaverUniqueId")
    @ResponseBody
    public Result saveNaverUniqueId(NaverPlaceLinkVO naverPlaceLinkVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = naverPlaceLinkService.saveNaverUniqueId(naverPlaceLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 동의여부확인 API 호출
     *
     * @param naverPlaceLinkVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getAgreeYn.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgreeYn(NaverPlaceLinkVO naverPlaceLinkVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        Map<String, Object> resultMap = naverPlaceLinkService.getAgreeYn(naverPlaceLinkVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 업체목록조회 API 호출
     *
     * @param naverPlaceLinkVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getPlaceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPlaceList(NaverPlaceLinkVO naverPlaceLinkVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        Map<String, Object> resultMap = naverPlaceLinkService.getPlaceList(naverPlaceLinkVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 업체 수정/등록 API 호출
     *
     * @param naverPlaceLinkVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/savePlace.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePlace(NaverPlaceLinkVO naverPlaceLinkVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        Map<String, Object> resultMap = naverPlaceLinkService.savePlace(naverPlaceLinkVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 연동 추가 API
     *
     * @param naverPlaceLinkVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/mappingPlace.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result mappingPlace(NaverPlaceLinkVO naverPlaceLinkVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        Map<String, Object> resultMap = naverPlaceLinkService.mappingPlace(naverPlaceLinkVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 연동 해지 API
     *
     * @param naverPlaceLinkVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/unMappingPlace.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result unMappingPlace(NaverPlaceLinkVO naverPlaceLinkVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        Map<String, Object> resultMap = naverPlaceLinkService.unMappingPlace(naverPlaceLinkVO);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }
}
