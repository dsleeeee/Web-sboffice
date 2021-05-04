package kr.co.solbipos.pos.license.instlManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.license.instlManage.service.InstlManageService;
import kr.co.solbipos.pos.license.instlManage.service.InstlManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

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
     * 업체현황탭 - 업체현황조회
     *
     * @param instlManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/instlManage/getAgencyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgencyList(InstlManageVO instlManageVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = instlManageService.getAgencyList(instlManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, instlManageVO);
    }

    /**
     * 업체현황탭 - 업체현황 상세조회
     *
     * @param instlManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/instlManage/getAgencyDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgencyDtlList(InstlManageVO instlManageVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = instlManageService.getAgencyDtlList(instlManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, instlManageVO);
    }

    /**
     * 업체현황탭 - 매장별매출 상세조회
     *
     * @param instlManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/instlManage/getAgencyPurchsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgencyPurchsList(InstlManageVO instlManageVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = instlManageService.getAgencyPurchsList(instlManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, instlManageVO);
    }

    /**
     * 설치현황탭 - 설치현황조회
     *
     * @param instlManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/instlManage/getInstlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInstlList(InstlManageVO instlManageVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = instlManageService.getInstlList(instlManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, instlManageVO);
    }

    /**
     * 설치현황탭 - 설치현황 상세조회
     *
     * @param instlManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/instlManage/getInstlDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInstlDetailList(InstlManageVO instlManageVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = instlManageService.getInstlDetailList(instlManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, instlManageVO);
    }

    /**
     * 설치현황탭 - 설치현황 설치정보 상세조회
     *
     * @param instlManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/instlManage/getInstlDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInstlDtlList(InstlManageVO instlManageVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = instlManageService.getInstlDtlList(instlManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, instlManageVO);
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

    /**
     * 설치관리 - 설치요청 삭제
     *
     * @param instlManageVO[]
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/delRequestDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result delRequestDtl(@RequestBody InstlManageVO[] instlManageVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = instlManageService.delRequestDtl(instlManageVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}