package kr.co.solbipos.pos.license.oper.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.license.oper.service.OperService;
import kr.co.solbipos.pos.license.oper.service.OperVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/pos/license/oper")
public class OperController {

    private final SessionService sessionService;
    private final OperService operService;

    /** Constructor Injection */
    @Autowired
    public OperController(SessionService sessionService, OperService operService){
        this.sessionService = sessionService;
        this.operService = operService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String instalManageView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "pos/license/oper/oper";
    }

    /**
     * 매출매장현황탭 - 매출매장현황조회
     *
     * @param operVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/oper/getSaleStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleStoreList(OperVO operVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//        System.out.println("test11111");

        List<DefaultMap<Object>> result = operService.getSaleStoreList(operVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, operVO);
    }

    /**
     * 대리점인증현황탭 - 대리점인증현황조회
     *
     * @param operVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/oper/getAgencyAuthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgencyAuthList(OperVO operVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = operService.getAgencyAuthList(operVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, operVO);
    }

}
