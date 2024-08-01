package kr.co.solbipos.mobile.stock.status.periodIoStock.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.stock.status.periodIoStock.service.MobilePeriodIoStockService;
import kr.co.solbipos.mobile.stock.status.periodIoStock.service.MobilePeriodIoStockVO;
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
 * @Class Name : MobilePeriodIoStockController.java
 * @Description : (모바일)재고현황 > 기간수불현황
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
@RequestMapping("/mobile/stock/status/periodIoStock")
public class MobilePeriodIoStockController {

    private final SessionService sessionService;
    private final MobilePeriodIoStockService mobilePeriodIoStockService;

    @Autowired
    public MobilePeriodIoStockController(SessionService sessionService, MobilePeriodIoStockService mobilePeriodIoStockService) {
        this.sessionService = sessionService;
        this.mobilePeriodIoStockService = mobilePeriodIoStockService;
    }

    /**
     * 기간수불현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobilePeriodIoStock/list.sb", method = RequestMethod.GET)
    public String periodIostockView(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String url = "";
        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.HQ)){
            url = "mobile/stock/status/periodIoStock/mobileHqPeriodIoStock";
        } else if(sessionInfoVO.getOrgnFg().equals(OrgnFg.STORE)){
            url = "mobile/stock/status/periodIoStock/mobileStorePeriodIoStock";
        }
        return url;
    }

    /**
     * 기간수불현황 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param	mobilePeriodIostockVO
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobilePeriodIoStock/periodIoStockList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPeriodIoStockList(HttpServletRequest request, HttpServletResponse response, Model model, MobilePeriodIoStockVO mobilePeriodIostockVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = mobilePeriodIoStockService.getPeriodIoStockList(mobilePeriodIostockVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobilePeriodIostockVO);
    }

    /**
     * 기간수불현황 - 상품코드 선택 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param	mobilePeriodIostockVO
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobilePeriodIoStock/periodIoStockProdDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPeriodIoStockProdDtlList(HttpServletRequest request, HttpServletResponse response, Model model, MobilePeriodIoStockVO mobilePeriodIostockVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = mobilePeriodIoStockService.getPeriodIoStockProdDtlList(mobilePeriodIostockVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobilePeriodIostockVO);
    }

    /**
     * 기간수불현황 - 엑셀 다운로드 전체 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param	mobilePeriodIostockVO
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobilePeriodIoStock/periodIoStockExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPeriodIoStockExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MobilePeriodIoStockVO mobilePeriodIostockVO) {
        System.out.println("테스트: " + mobilePeriodIostockVO.getUnitFg());
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = mobilePeriodIoStockService.getPeriodIoStockExcelList(mobilePeriodIostockVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobilePeriodIostockVO);
    }
}
