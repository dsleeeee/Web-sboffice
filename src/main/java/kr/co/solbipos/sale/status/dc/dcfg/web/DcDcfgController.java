package kr.co.solbipos.sale.status.dc.dcfg.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.dc.dcfg.service.DcDcfgService;
import kr.co.solbipos.sale.status.dc.dcfg.service.DcDcfgVO;

@Controller
@RequestMapping("/sale/status/dc")
public class DcDcfgController {
    private final SessionService sessionService;
    private final DcDcfgService dcDcfgService;

    @Autowired
    public DcDcfgController(SessionService sessionService, DcDcfgService dcDcfgService) {
        this.sessionService = sessionService;
        this.dcDcfgService = dcDcfgService;
    }

    @RequestMapping(value = "/dcfg/view.sb", method = RequestMethod.GET)
    public String dcDcfgView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/dc/dcSale";
    }
    
    @RequestMapping(value = "/dcfg/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDcDcfgList(HttpServletRequest request, HttpServletResponse response,
        Model model, DcDcfgVO dcDcfgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcDcfgService.getDcDcfgList(dcDcfgVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, dcDcfgVO);
    }
    
    @RequestMapping(value = "/dcfg/dtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDcDcfgDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, DcDcfgVO dcDcfgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcDcfgService.getDcDcfgDtlList(dcDcfgVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, dcDcfgVO);
    }
    
    @RequestMapping(value = "/dcfg/dcNmList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDcNmList(HttpServletRequest request, HttpServletResponse response,
        Model model, DcDcfgVO dcDcfgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcDcfgService.getDcNmlList(dcDcfgVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, dcDcfgVO);
    }
}
