package kr.co.solbipos.mobile.stock.status.dailyIoStock.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.stock.status.dailyIoStock.service.MobileDailyIoStockService;
import kr.co.solbipos.mobile.stock.status.dailyIoStock.service.MobileDailyIoStockVO;
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
 * @Class Name : MobileDailyIoStockController.java
 * @Description : (모바일)재고현황 > 일자별수불현황
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
@RequestMapping("/mobile/stock/status/dailyIoStock")
public class MobileDailyIoStockController {

    private final SessionService sessionService;
    private final MobileDailyIoStockService mobileDailyIoStockService;

    @Autowired
    public MobileDailyIoStockController(SessionService sessionService, MobileDailyIoStockService mobileDailyIoStockService) {
        this.sessionService = sessionService;
        this.mobileDailyIoStockService = mobileDailyIoStockService;
    }

    /**
     * 일수불현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.07.19
     */
    @RequestMapping(value = "/mobileDailyIoStock/list.sb", method = RequestMethod.GET)
    public String periodIostockView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String url = "";
        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.HQ)){
            url = "mobile/stock/status/dailyIoStock/mobileHqDailyIoStock";
        } else if(sessionInfoVO.getOrgnFg().equals(OrgnFg.STORE)){
            url = "mobile/stock/status/dailyIoStock/mobileStoreDailyIoStock";
        }
        return url;
    }

    /**
     * 일수불현황 - 일수불현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.07.19
     */
    @RequestMapping(value = "/mobileDailyIoStock/getDailyIoStockList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDailyIoStockList(HttpServletRequest request, HttpServletResponse response, MobileDailyIoStockVO mobileDailyIoStockVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileDailyIoStockService.getDailyIoStockList(mobileDailyIoStockVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileDailyIoStockVO);
    }

    /**
     * 일수불현황 - 일수불현황 리스트(엑셀) 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.07.19
     */
    @RequestMapping(value = "/mobileDailyIoStock/getDailyIoStockExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDailyIoStockExcelList(HttpServletRequest request, HttpServletResponse response, MobileDailyIoStockVO mobileDailyIoStockVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileDailyIoStockService.getDailyIoStockExcelList(mobileDailyIoStockVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileDailyIoStockVO);
    }
}
