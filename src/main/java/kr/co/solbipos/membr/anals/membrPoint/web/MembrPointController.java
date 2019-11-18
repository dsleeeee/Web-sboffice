package kr.co.solbipos.membr.anals.membrPoint.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.membrPoint.service.MembrPointService;
import kr.co.solbipos.membr.anals.membrPoint.service.MembrPointVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

@Controller
@RequestMapping("/membr/anals/membrPoint")
public class MembrPointController {

    private final SessionService sessionService;
    private final MembrPointService membrPointService;

    /** Constructor Injection */
    @Autowired
    public MembrPointController(SessionService sessionService, MembrPointService membrPointService){
        this.sessionService = sessionService;
        this.membrPointService = membrPointService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "membr/anals/membrPoint/membrPoint";
    }

    /**
     * 회원 포인트실적 조회
     *
     * @param membrPointVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "membrPoint/getMembrPointList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMembrPointList(MembrPointVO membrPointVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//        System.out.println("test : 1111");

        List<DefaultMap<Object>> result = membrPointService.getMembrPointList(membrPointVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, membrPointVO);
    }

}