package kr.co.solbipos.mobile.stock.status.storeDay.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.storeDay.service.MobileStoreDayService;
import kr.co.solbipos.mobile.stock.status.storeDay.service.MobileStoreDayVO;
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
 * @Class Name : MobileStoreDayController.java
 * @Description : (모바일)재고현황 > 매장일수불
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.23  김유승      최초생성
 *
 * @author 솔비포스
 * @since 2024.07.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/stock/status/storeDay")
public class MobileStoreDayController {

    private final SessionService sessionService;
    private final MobileStoreDayService mobileStoreDayService;

    @Autowired
    public MobileStoreDayController(SessionService sessionService, MobileStoreDayService mobileStoreDayService) {
        this.sessionService = sessionService;
        this.mobileStoreDayService = mobileStoreDayService;
    }

    /**
     * 매장일수불 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStoreDay/list.sb", method = RequestMethod.GET)
    public String storeDayView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "mobile/stock/status/storeDay/mobileStoreDay";
    }

    /**
     * 매장일수불 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStoreDay/viewList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result storeDayList(HttpServletRequest request, HttpServletResponse response, Model model, MobileStoreDayVO mobileStoreDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileStoreDayService.storeDayList(mobileStoreDayVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobileStoreDayVO);
    }

    /**
     * 매장일수불 - 엑셀 전체 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStoreDay/viewExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result storeDayExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MobileStoreDayVO mobileStoreDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileStoreDayService.storeDayExcelList(mobileStoreDayVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobileStoreDayVO);
    }

}
