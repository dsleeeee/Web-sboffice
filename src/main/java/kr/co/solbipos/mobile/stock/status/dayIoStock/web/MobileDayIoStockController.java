package kr.co.solbipos.mobile.stock.status.dayIoStock.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.stock.status.dayIoStock.service.MobileDayIoStockService;
import kr.co.solbipos.mobile.stock.status.dayIoStock.service.MobileDayIoStockVO;
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
 * @Class Name : MobileDayIoStockController.java
 * @Description : (모바일)재고현황 > 일수불현황
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
@RequestMapping("/mobile/stock/status/dayIoStock")
public class MobileDayIoStockController {

    private final SessionService sessionService;
    private final MobileDayIoStockService mobileDayIoStockService;

    @Autowired
    public MobileDayIoStockController(SessionService sessionService, MobileDayIoStockService mobileDayIoStockService) {
        this.sessionService = sessionService;
        this.mobileDayIoStockService = mobileDayIoStockService;
    }

    /**
     * 일수불현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobileDayIoStock/list.sb", method = RequestMethod.GET)
    public String dayIostockView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String url = "";

        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.HQ)){
            url = "mobile/stock/status/dayIoStock/mobileHqDayIoStock";
        } else {
            url = "mobile/stock/status/dayIoStock/mobileStoreDayIoStock";
        }

        return url;
    }

    /**
     * 일수불현황 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobileDayIoStock/viewList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dayIostockList(HttpServletRequest request, HttpServletResponse response, Model model, MobileDayIoStockVO mobileDayIoStockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileDayIoStockService.dayIostockList(mobileDayIoStockVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobileDayIoStockVO);
    }

    /**
     * 일수불현황 - 리스트 조회(엑셀)
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobileDayIoStock/viewExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dayIostockExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MobileDayIoStockVO mobileDayIoStockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileDayIoStockService.dayIostockExcelList(mobileDayIoStockVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobileDayIoStockVO);
    }
}
