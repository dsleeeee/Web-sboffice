package kr.co.solbipos.pos.license.instlAgency.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.pos.license.instlAgency.service.InstlAgencyService;
import kr.co.solbipos.pos.license.instlAgency.service.InstlAgencyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

@Controller
@RequestMapping("/pos/license/instlAgency")
public class InstlAgencyController {

    private final SessionService sessionService;
    private final InstlAgencyService instlAgencyService;

    /** Constructor Injection */
    @Autowired
    public InstlAgencyController(SessionService sessionService, InstlAgencyService instlAgencyService){
        this.sessionService = sessionService;
        this.instlAgencyService = instlAgencyService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String instlAgencyView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "pos/license/instlAgency/instlAgency";
    }

    /**
     * 설치업체관리 조회
     *
     * @param instlAgencyVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getInstlAgency.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInstlAgency(InstlAgencyVO instlAgencyVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = instlAgencyService.getInstlAgency(instlAgencyVO, sessionInfoVO);

        return returnListJson(Status.OK, list, instlAgencyVO);
    }

    /**
     * 설치업체관리 상세정보 조회
     *
     * @param instlAgencyVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getInstlAgencyDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInstlAgencyDtl(InstlAgencyVO instlAgencyVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = instlAgencyService.getInstlAgencyDtl(instlAgencyVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 설치업체관리 저장
     *
     * @param instlAgencyVO
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping(value = "/saveAgency.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAgency(InstlAgencyVO instlAgencyVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = instlAgencyService.saveAgency(instlAgencyVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 설치업체 사원목록 조회
     *
     * @param instlAgencyVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getAgencyEmp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgencyEmp(InstlAgencyVO instlAgencyVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = instlAgencyService.getAgencyEmp(instlAgencyVO, sessionInfoVO);

        return returnListJson(Status.OK, list, instlAgencyVO);
    }

    /**
     * 설치업체 사원상세 조회
     *
     * @param instlAgencyVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getAgencyEmpDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgencyEmpDtl(InstlAgencyVO instlAgencyVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = instlAgencyService.getAgencyEmpDtl(instlAgencyVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 설치업체 사원상세 저장
     *
     * @param instlAgencyVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveAgencyEmp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAgencyEmp(InstlAgencyVO instlAgencyVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        EmpResult empResult = instlAgencyService.saveAgencyEmp(instlAgencyVO,sessionInfoVO);

        return returnJson(Status.OK, empResult);
    }

}
