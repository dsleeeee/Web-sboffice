package kr.co.solbipos.adi.resve.resveInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.resve.resveInfo.service.ResveInfoService;
import kr.co.solbipos.adi.resve.resveInfo.service.ResveInfoVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/adi/resve/resveInfo")
public class ResveController {

    private final SessionService sessionService;
    private final ResveInfoService resveInfoService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ResveController(SessionService sessionService, ResveInfoService resveInfoService) {
        this.sessionService = sessionService;
        this.resveInfoService = resveInfoService;
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
        return "adi/resve/resveInfo/resveInfo";
    }

    /**
     * 예약현황 - 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getResveList(ResveInfoVO resveInfoVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> result = resveInfoService.getResveList(resveInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, resveInfoVO);

    }

}