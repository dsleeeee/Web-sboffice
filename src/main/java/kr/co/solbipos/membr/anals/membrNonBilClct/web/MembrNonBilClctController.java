package kr.co.solbipos.membr.anals.membrNonBilClct.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.membrNonBilClct.service.MembrNonBilClctService;
import kr.co.solbipos.membr.anals.membrNonBilClct.service.MembrNonBilClctVO;
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
@RequestMapping("/membr/anals/membrNonBilClct")
public class MembrNonBilClctController {

    private final SessionService sessionService;
    private final MembrNonBilClctService membrNonBilClctService;

    /** Constructor Injection */
    @Autowired
    public MembrNonBilClctController(SessionService sessionService, MembrNonBilClctService membrNonBilClctService){
        this.sessionService = sessionService;
        this.membrNonBilClctService = membrNonBilClctService;
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

        return "membr/anals/membrNonBilClct/membrNonBilClct";
    }

    /**
     * 회원 미수금현황 조회
     *
     * @param membrNonBilClctVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "membrNonBilClct/getMembrNonBilClctList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMembrNonBilClctList(MembrNonBilClctVO membrNonBilClctVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//        System.out.println("test : 1111");

        List<DefaultMap<Object>> result = membrNonBilClctService.getMembrNonBilClctList(membrNonBilClctVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, membrNonBilClctVO);
    }

    /**
     * 회원 미수금현황 상세조회
     *
     * @param membrNonBilClctVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "membrNonBilClct/getMembrNonBilClctDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMembrNonBilClctDetailList(MembrNonBilClctVO membrNonBilClctVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = membrNonBilClctService.getMembrNonBilClctDetailList(membrNonBilClctVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, membrNonBilClctVO);
    }
}