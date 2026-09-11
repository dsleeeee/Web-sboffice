package kr.co.solbipos.sys.link.naverPlaceEasyLink.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.link.naverPlaceEasyLink.service.NaverPlaceEasyLinkService;
import kr.co.solbipos.sys.link.naverPlaceEasyLink.service.NaverPlaceEasyLinkVO;
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

/**
 * @Class Name : NaverPlaceEasyLinkController.java
 * @Description : 시스템관리 > 연동 > 네이버플레이스 간편연동 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/link/naverPlaceEasyLink")
public class NaverPlaceEasyLinkController {

    private final SessionService sessionService;
    private final NaverPlaceEasyLinkService naverPlaceEasyLinkService;

    /**
     * Constructor Injection
     */
    @Autowired
    public NaverPlaceEasyLinkController(SessionService sessionService, NaverPlaceEasyLinkService naverPlaceEasyLinkService) {
        this.sessionService = sessionService;
        this.naverPlaceEasyLinkService = naverPlaceEasyLinkService;
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
        return "sys/link/naverPlaceEasyLink/naverPlaceEasyLink";
    }

    /**
     * 사용자현황 조회
     * @param naverPlaceEasyLinkVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getNaverPlaceUserList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNaverPlaceUserList(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = naverPlaceEasyLinkService.getNaverPlaceUserList(naverPlaceEasyLinkVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, naverPlaceEasyLinkVO);
    }

    /**
     * 간편연동 업로드 검증 및 임시데이터 저장
     * @param naverPlaceEasyLinkVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveNaverPlaceEasyLinkExcelCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveNaverPlaceEasyLinkExcelCheck(@RequestBody NaverPlaceEasyLinkVO[] naverPlaceEasyLinkVOs, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = naverPlaceEasyLinkService.saveNaverPlaceEasyLinkExcelCheck(naverPlaceEasyLinkVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 간편연동 업로드 검증결과 조회
     * @param naverPlaceEasyLinkVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getNaverPlaceEasyLinkExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNaverPlaceEasyLinkExcelList(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = naverPlaceEasyLinkService.getNaverPlaceEasyLinkExcelList(naverPlaceEasyLinkVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, naverPlaceEasyLinkVO);
    }

    /**
     * 간편연동 업로드 임시데이터 전체 삭제(세션 기준)
     * @param naverPlaceEasyLinkVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteNaverPlaceEasyLinkExcelAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteNaverPlaceEasyLinkExcelAll(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = naverPlaceEasyLinkService.deleteNaverPlaceEasyLinkExcelAll(sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 간편연동 업로드 임시데이터 개별 삭제
     * @param naverPlaceEasyLinkVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteNaverPlaceEasyLinkExcel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteNaverPlaceEasyLinkExcel(@RequestBody NaverPlaceEasyLinkVO[] naverPlaceEasyLinkVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = naverPlaceEasyLinkService.deleteNaverPlaceEasyLinkExcel(naverPlaceEasyLinkVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 간편연동 업로드 검증성공 데이터 저장
     * @param naverPlaceEasyLinkVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveNaverPlaceEasyLinkExcel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveNaverPlaceEasyLinkExcel(@RequestBody NaverPlaceEasyLinkVO[] naverPlaceEasyLinkVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = naverPlaceEasyLinkService.saveNaverPlaceEasyLinkExcel(naverPlaceEasyLinkVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

}
