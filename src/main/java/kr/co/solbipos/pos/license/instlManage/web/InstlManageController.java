package kr.co.solbipos.pos.license.instlManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.license.instlManage.service.InstlManageService;
import kr.co.solbipos.pos.license.instlManage.service.InstlManageVO;
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
@RequestMapping("/pos/license/instlManage")
public class InstlManageController {

    private final SessionService sessionService;
    private final InstlManageService instlManageService;

    /** Constructor Injection */
    @Autowired
    public InstlManageController(SessionService sessionService, InstlManageService instlManageService){
        this.sessionService = sessionService;
        this.instlManageService = instlManageService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String instlManageView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "pos/license/instlManage/instlManage";
    }

    /**
     * 설치관리 - 설치요청 목록 조회
     *
     * @param instlManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getInstlRequestList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInstallRequestList(InstlManageVO instlManageVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = instlManageService.getInstlRequestList(instlManageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, instlManageVO);
    }

    /**
     * 설치관리 - 설치요청 목록 상세
     *
     * @param instlManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getInstlRequestDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInstlRequestDtl(InstlManageVO instlManageVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = instlManageService.getInstlRequestDtl(instlManageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, instlManageVO);
    }
}
