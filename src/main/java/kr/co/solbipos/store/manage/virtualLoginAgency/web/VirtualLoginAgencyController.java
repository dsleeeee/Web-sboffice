package kr.co.solbipos.store.manage.virtualLoginAgency.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.virtualLoginAgency.service.VirtualLoginAgencyService;
import kr.co.solbipos.store.manage.virtualLoginAgency.service.VirtualLoginAgencyVO;
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

@Controller
@RequestMapping("/store/manage/virtualLoginAgency")
public class VirtualLoginAgencyController {

    private final SessionService sessionService;
    private final VirtualLoginAgencyService virtualLoginAgencyService;

    /**
     * Constructor Injection
     */
    @Autowired
    public VirtualLoginAgencyController(SessionService sessionService, VirtualLoginAgencyService virtualLoginAgencyService) {
        this.sessionService = sessionService;
        this.virtualLoginAgencyService = virtualLoginAgencyService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String virtualLoginAgencyView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "store/manage/virtualLoginAgency/virtualLoginAgency";
    }

    /**
     * 총판/대리점 가상로그인 조회
     *
     * @param virtualLoginAgencyVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 31.
     */
    @RequestMapping(value = "/virtualLoginAgency/getVirtualLoginAgencyrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVirtualLoginAgencyrList(VirtualLoginAgencyVO virtualLoginAgencyVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = virtualLoginAgencyService.getVirtualLoginAgencyrList(virtualLoginAgencyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, virtualLoginAgencyVO);
    }
}