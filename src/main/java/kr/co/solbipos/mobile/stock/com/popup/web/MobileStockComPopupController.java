package kr.co.solbipos.mobile.stock.com.popup.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.mobile.stock.com.popup.service.MobileStockComPopupService;
import kr.co.solbipos.mobile.stock.com.popup.service.MobileStockComPopupVO;
import kr.co.solbipos.mobile.stock.status.dailyIoStock.service.MobileDailyIoStockVO;
import kr.co.solbipos.mobile.stock.status.periodIoStock.service.MobilePeriodIoStockVO;
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockVO;
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
 * @Class Name : MobileStockComPopupController.java
 * @Description : (모바일)재고현황공통팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.30  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.30
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/stock/com/popup")
public class MobileStockComPopupController {

    private final SessionService sessionService;
    private final MobileStockComPopupService mobileStockComPopupService;

    @Autowired
    public MobileStockComPopupController(SessionService sessionService, MobileStockComPopupService mobileStockComPopupService) {
        this.sessionService = sessionService;
        this.mobileStockComPopupService = mobileStockComPopupService;
    }


    /**
     * 일자별수불현황 - 일자별수불현황 팝업 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.07.30
     */
    @RequestMapping(value = "/mobileDailyIoStockInfo/getDailyIoStockInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDailyIoStockInfoList(HttpServletRequest request, HttpServletResponse response, MobileDailyIoStockVO mobileDailyIoStockVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileStockComPopupService.getDailyIoStockInfoList(mobileDailyIoStockVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileDailyIoStockVO);
    }

    /**
     * 기간수불현황 - 상품코드 선택 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param	mobilePeriodIostockVO
     * @return  String
     * @author  김유승
     * @since   2024.07.30
     */
    @RequestMapping(value = "/mobileCmmQtyDtl/getCmmProdCodeDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCmmProdCodeDtlList(HttpServletRequest request, HttpServletResponse response, Model model, MobilePeriodIoStockVO mobilePeriodIostockVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = mobileStockComPopupService.getCmmProdCodeDtlList(mobilePeriodIostockVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobilePeriodIostockVO);
    }

    /**
     * 매장일수불 - 각 수량별 팝업 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.07.30
     */
    @RequestMapping(value = "/mobileCmmQtyDtl/getCmmQtyDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCmmQtyDtlList(HttpServletRequest request, HttpServletResponse response, MobilePeriodIoStockVO mobilePeriodIostockVO, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = mobileStockComPopupService.getCmmQtyDtlList(mobilePeriodIostockVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobilePeriodIostockVO);
    }

    /**
     * 거래처 선택모듈 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   mobileStockComPopupVO
     * @return  String
     * @author  김유승
     * @since   2024.07.30
     */
    @RequestMapping(value = "/selectVendrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrList(HttpServletRequest request, HttpServletResponse response,
                               Model model, MobileStockComPopupVO mobileStockComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileStockComPopupService.getVendrList(mobileStockComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileStockComPopupVO);
    }

}

