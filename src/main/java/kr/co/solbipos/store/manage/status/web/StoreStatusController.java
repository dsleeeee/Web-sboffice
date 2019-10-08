package kr.co.solbipos.store.manage.status.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
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
import static kr.co.common.utils.grid.ReturnUtil.returnJson;

@Controller
@RequestMapping("/store/manage/status")
public class StoreStatusController {

    private final SessionService sessionService;
    private final StoreStatusService storeStatusService;
    private final DayService dayService;

    /** Constructor Injection */
    @Autowired
    public StoreStatusController(SessionService sessionService, StoreStatusService storeStatusService, DayService dayService) {
        this.sessionService = sessionService;
        this.storeStatusService = storeStatusService;
        this.dayService = dayService;
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

        DayVO dayVO = new DayVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = dayService.getPayColList(dayVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

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
    @RequestMapping(value = "/store/getStatusStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStatusStoreList(StoreStatusVO storeStatusVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

//        System.out.println("LISTSCALE : " + storeStatusVO.getListScale());

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
    @RequestMapping(value = "/cornerdtl/getStatusStoreCornerList.sb", method = RequestMethod.POST)
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
    @RequestMapping(value = "/agency/getStatusAgencyList.sb", method = RequestMethod.POST)
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
    @RequestMapping(value = "/agency/getStatusAgencyDetailList.sb", method = RequestMethod.POST)
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
    @RequestMapping(value = "/van/getStatusVanList.sb", method = RequestMethod.POST)
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
    @RequestMapping(value = "/van/getStatusVanDetailList.sb", method = RequestMethod.POST)
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
    @RequestMapping(value = "/posInstl/getStatusPosInstallList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStatusPosInstallList(StoreStatusVO storeStatusVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeStatusService.getStatusPosInstallList(storeStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeStatusVO);
    }

    /**
     * 매장현황 탭 - 관리매장 승인내역 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeStatusVO
     * @return  String
     * @author  이다솜
     * @since   2019. 09. 23.
     */
    @RequestMapping(value = "/storeAppr/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStatusApprList(HttpServletRequest request, HttpServletResponse response,
                                    Model model, StoreStatusVO storeStatusVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeStatusService.getStatusApprList(storeStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeStatusVO);
    }

    /**
     * 매장현황 탭 - 관리매장 승인내역 >> 카드/현금승인현황
     * @param   request
     * @param   response
     * @param   model
     * @param   storeStatusVO
     * @return  String
     * @author  이다솜
     * @since   2019. 09. 27.
     */
    @RequestMapping(value = "/storeAppr/cardOrCashApprList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardOrCashApprList(HttpServletRequest request, HttpServletResponse response,
                                        Model model, StoreStatusVO storeStatusVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeStatusService.getCardOrCashApprList(storeStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeStatusVO);
    }

    /**
     * 매장현황 탭 - 관리매장 승인내역 >> 매출상세내역
     * @param   request
     * @param   response
     * @param   model
     * @param   storeStatusVO
     * @return  String
     * @author  이다솜
     * @since   2019. 09. 27.
     */
    @RequestMapping(value = "/storeAppr/getSaleDtlInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleDtlInfo(HttpServletRequest request, HttpServletResponse response,
                                 Model model, StoreStatusVO storeStatusVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        /*DefaultMap<String> result = hqEmpService.getHqEmpDtlInfo(hqEmpVO, sessionInfoVO);

        return returnJson(Status.OK, result);*/

        // 매출상세내역
        DefaultMap<String> result = storeStatusService.getSaleDtlInfo(storeStatusVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장현황 탭 - 관리매장 승인내역 >> 신용카드/현금 결제내역
     * @param   request
     * @param   response
     * @param   model
     * @param   storeStatusVO
     * @return  String
     * @author  이다솜
     * @since   2019. 09. 27.
     */
    @RequestMapping(value = "/storeAppr/getCardPayInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardPayInfo(HttpServletRequest request, HttpServletResponse response,
                                 Model model, StoreStatusVO storeStatusVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeStatusService.getCardPayInfo(storeStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeStatusVO);
    }

    /**
     * 매장현황 탭 - 관리매장 승인내역 >> 상품내역
     * @param   request
     * @param   response
     * @param   model
     * @param   storeStatusVO
     * @return  String
     * @author  이다솜
     * @since   2019. 09. 27.
     */
    @RequestMapping(value = "/storeAppr/getSaleProductInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleProductInfo(HttpServletRequest request, HttpServletResponse response,
                                     Model model, StoreStatusVO storeStatusVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeStatusService.getSaleProductInfo(storeStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeStatusVO);
    }
}