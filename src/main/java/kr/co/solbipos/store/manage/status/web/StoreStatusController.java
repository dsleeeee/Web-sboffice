package kr.co.solbipos.store.manage.status.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.status.service.StoreStatusService;
import kr.co.solbipos.store.manage.status.service.StoreStatusVO;
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
@RequestMapping("/store/manage/status/store")
public class StoreStatusController {

    private final SessionService sessionService;
    private final StoreStatusService storeStatusService;

    /** Constructor Injection */
    @Autowired
    public StoreStatusController(SessionService sessionService, StoreStatusService storeStatusService) {
        this.sessionService = sessionService;
        this.storeStatusService = storeStatusService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String statusView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "store/manage/status/status";
    }

    /**
     * 매장탭 - 매장정보조회
     *
     * @param storeStatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getStatusStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStatusStoreList(StoreStatusVO storeStatusVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        System.out.println("LISTSCALE : " + storeStatusVO.getListScale());

        List<DefaultMap<Object>> result = storeStatusService.getStatusStoreList(storeStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeStatusVO);
    }

    /**
     * 매장탭 - 코너 상세조회
     *
     * @param storeStatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getStatusStoreCornerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStatusStoreCornerList(StoreStatusVO storeStatusVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeStatusService.getStatusStoreCornerList(storeStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeStatusVO);
    }

    /**
     * 관리업체탭 - 관리업체 조회
     *
     * @param storeStatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getStatusAgencyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStatusAgencyList(StoreStatusVO storeStatusVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeStatusService.getStatusAgencyList(storeStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeStatusVO);
    }

    /**
     * 관리업체탭 - 관리업체 상세조회
     *
     * @param storeStatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getStatusAgencyDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStatusAgencyDetailList(StoreStatusVO storeStatusVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeStatusService.getStatusAgencyDetailList(storeStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeStatusVO);
    }

    /**
     * VAN사탭 - VAN사 조회
     *
     * @param storeStatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getStatusVanList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStatusVanList(StoreStatusVO storeStatusVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeStatusService.getStatusVanList(storeStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeStatusVO);
    }

    /**
     * VAN사탭 - VAN사 상세조회
     *
     * @param storeStatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getStatusVanDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStatusVanDetailList(StoreStatusVO storeStatusVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeStatusService.getStatusVanDetailList(storeStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeStatusVO);
    }

    /**
     * POS설치현황탭 - POS설치현황 조회
     *
     * @param storeStatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getStatusPosInstallList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStatusPosInstallList(StoreStatusVO storeStatusVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeStatusService.getStatusPosInstallList(storeStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeStatusVO);
    }
}