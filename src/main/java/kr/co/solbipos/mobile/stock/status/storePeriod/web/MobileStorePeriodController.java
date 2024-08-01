package kr.co.solbipos.mobile.stock.status.storePeriod.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.storePeriod.service.MobileStorePeriodService;
import kr.co.solbipos.mobile.stock.status.storePeriod.service.MobileStorePeriodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * @Class Name : MobileStorePeriodController.java
 * @Description : (모바일)재고현황 > 매장기간수불
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.23  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/stock/status/storePeriod")
public class MobileStorePeriodController {

    private final SessionService sessionService;
    private final MobileStorePeriodService mobileStorePeriodService;

    @Autowired
    public MobileStorePeriodController(SessionService sessionService, MobileStorePeriodService mobileStorePeriodService) {
        this.sessionService = sessionService;
        this.mobileStorePeriodService = mobileStorePeriodService;
    }

    /**
     * 매장기간수불 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStorePeriod/list.sb", method = RequestMethod.GET)
    public String storePeriodView(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        return "mobile/stock/status/storePeriod/mobileStorePeriod";
    }

    /**
     * 매장기간수불 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   mobileStorePeriodVO
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStorePeriod/mobileStorePeriodList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreperiodList(HttpServletRequest request, HttpServletResponse response, Model model, MobileStorePeriodVO mobileStorePeriodVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = mobileStorePeriodService.getStorePeriodList(mobileStorePeriodVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobileStorePeriodVO);
    }

    /**
     * 매장기간수불 - 상품코드 선택 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param	mobileStorePeriodVO
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStorePeriod/storeperiodDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStorePeriodDtlList(HttpServletRequest request, HttpServletResponse response, Model model, MobileStorePeriodVO mobileStorePeriodVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = mobileStorePeriodService.getStorePeriodDtlList(mobileStorePeriodVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobileStorePeriodVO);
    }

    /**
     * 매장기간수불 - 수량 선택 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param	mobileStorePeriodVO
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStorePeriod/storeperiodQtyDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStorePeriodQtyDtlList(HttpServletRequest request, HttpServletResponse response, Model model, MobileStorePeriodVO mobileStorePeriodVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = mobileStorePeriodService.getStorePeriodQtyDtlList(mobileStorePeriodVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobileStorePeriodVO);
    }

    /**
     * 매장기간수불 - 엑셀 다운로드 전체 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   mobileStorePeriodVO
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStorePeriod/mobileStorePeriodExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreperiodExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MobileStorePeriodVO mobileStorePeriodVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = mobileStorePeriodService.getStoreperiodExcelList(mobileStorePeriodVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobileStorePeriodVO);
    }
}
