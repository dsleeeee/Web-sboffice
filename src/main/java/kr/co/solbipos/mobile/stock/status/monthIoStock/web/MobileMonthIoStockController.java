package kr.co.solbipos.mobile.stock.status.monthIoStock.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.stock.status.monthIoStock.service.MobileMonthIoStockService;
import kr.co.solbipos.mobile.stock.status.monthIoStock.service.MobileMonthIoStockVO;
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
 * @Class Name : MobileMonthIoStockController.java
 * @Description : (모바일)재고현황 > 월수불현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.19  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/stock/status/monthIoStock")
public class MobileMonthIoStockController {

    private final SessionService sessionService;
    private final MobileMonthIoStockService mobileMonthIoStockService;

    @Autowired
    public MobileMonthIoStockController(SessionService sessionService, MobileMonthIoStockService mobileMonthIoStockService) {
        this.sessionService = sessionService;
        this.mobileMonthIoStockService = mobileMonthIoStockService;
    }

    /**
     * 월수불현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobileMonthIoStock/list.sb", method = RequestMethod.GET)
    public String monthIostockView(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String url = "";

        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.HQ)){
            url = "mobile/stock/status/monthIoStock/mobileHqMonthIoStock";
        } else if(sessionInfoVO.getOrgnFg().equals(OrgnFg.STORE)){
            url = "mobile/stock/status/monthIoStock/mobileStoreMonthIoStock";
        }
        return url;
    }

    /**
     * 월수불현황 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobileMonthIoStock/viewList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result monthIostockList(HttpServletRequest request, HttpServletResponse response, Model model, MobileMonthIoStockVO mobileMonthIostockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileMonthIoStockService.monthIoStockList(mobileMonthIostockVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobileMonthIostockVO);
    }

    /**
     * 월수불현황 - 리스트 조회 엑셀
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobileMonthIoStock/viewExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result monthIostockExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MobileMonthIoStockVO mobileMonthIostockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileMonthIoStockService.monthIoStockExcelList(mobileMonthIostockVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobileMonthIostockVO);
    }
}
