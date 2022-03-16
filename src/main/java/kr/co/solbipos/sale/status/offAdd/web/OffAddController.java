package kr.co.solbipos.sale.status.offAdd.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.offAdd.service.OffAddService;
import kr.co.solbipos.sale.status.offAdd.service.OffAddVO;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgService;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : SideController.java
 * @Description : 매출관리 > 매출현황2 > 오프라인추가매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.11  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/offAdd")
public class OffAddController {
    private final SessionService sessionService;
    private final OffAddService offAddService;
    private final ProdPayFgService prodPayFgService;

    public OffAddController(SessionService sessionService, OffAddService offAddService, ProdPayFgService prodPayFgService) {
        this.sessionService = sessionService;
        this.offAddService = offAddService;
        this.prodPayFgService = prodPayFgService;
    }

    /**
     * 오프라인추가매출현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2022.03.11
     */
    @RequestMapping(value = "/offAdd/view.sb", method = RequestMethod.GET)
    public String WeightView(HttpServletRequest request, HttpServletResponse response, Model model) {

        ProdPayFgVO prodPayFgVO = new ProdPayFgVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 결제수단 조회
        List<DefaultMap<String>> payColList = prodPayFgService.getPayColList(prodPayFgVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        return "sale/status/offAdd/offAddTab";
    }

    /**
     * 일별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   offAddVO
     * @return  String
     * @author  권지현
     * @since   2022.03.11
     */
    @RequestMapping(value = "/offAdd/getOffAddDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOffAddDayList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, OffAddVO offAddVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = offAddService.getOffAddDayList(offAddVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  offAddVO);
    }

    /**
     * 일별 상세 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   offAddVO
     * @return  String
     * @author  권지현
     * @since   2022.03.14
     */
    @RequestMapping(value = "/offAdd/getOffAddDayDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOffAddDayDetailList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, OffAddVO offAddVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = offAddService.getOffAddDayDetailList(offAddVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  offAddVO);
    }


    /**
     * 월별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   offAddVO
     * @return  String
     * @author  권지현
     * @since   2022.03.14
     */
    @RequestMapping(value = "/offAdd/getOffAddMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOffAddMonthList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, OffAddVO offAddVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = offAddService.getOffAddMonthList(offAddVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  offAddVO);
    }

    /**
     * 월별 상세 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   offAddVO
     * @return  String
     * @author  권지현
     * @since   2022.03.14
     */
    @RequestMapping(value = "/offAdd/getOffAddMonthDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOffAddMonthDetailList(HttpServletRequest request, HttpServletResponse response,
                                         Model model, OffAddVO offAddVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = offAddService.getOffAddMonthDetailList(offAddVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  offAddVO);
    }


    /**
     * 상품별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   offAddVO
     * @return  String
     * @author  권지현
     * @since   2022.03.15
     */
    @RequestMapping(value = "/offAdd/getOffAddProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOffAddProdList(HttpServletRequest request, HttpServletResponse response,
                                     Model model, OffAddVO offAddVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = offAddService.getOffAddProdList(offAddVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  offAddVO);
    }

    /**
     * 상품별 엑셀 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   offAddVO
     * @return  String
     * @author  권지현
     * @since   2022.03.15
     */
    @RequestMapping(value = "/offAdd/getOffAddProdExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOffAddProdExcelList(HttpServletRequest request, HttpServletResponse response,
                                           Model model, OffAddVO offAddVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = offAddService.getOffAddProdExcelList(offAddVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  offAddVO);
    }
}
