package kr.co.solbipos.sale.com.popup.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.com.popup.service.SaleComPopupService;
import kr.co.solbipos.sale.com.popup.service.SaleComPopupVO;
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
 * @Class Name : ProdPayFgController.java
 * @Description : 매출공통팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.20  김진      최초생성
 *
 * @author 솔비포스 
 * @since 2020.01.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/com/popup")
public class SaleComPopupController {
    private final SessionService sessionService;
    private final SaleComPopupService saleComPopupService;

    @Autowired
    public SaleComPopupController(SessionService sessionService, SaleComPopupService saleComPopupService) {
        this.sessionService = sessionService;
        this.saleComPopupService = saleComPopupService;
    }

    /**
     * 매출공통팝업 - 테이블별 매출현황 팝업(실매출 클릭)
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 20.
     */
    @RequestMapping(value = "/table/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTablePopList(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = saleComPopupService.getTablePopList(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
    
    /**
     * 매출공통팝업 - 상품매출내역 팝업(수량 클릭)
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 20.
     */
    @RequestMapping(value = "/prod/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdPopList(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.getProdPopList(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
    
    /**
     * 매출공통팝업 - 승인현황(매장현황) 팝업(매장명 클릭)
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 04.
     */
    @RequestMapping(value = "/appr/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getApprPopList(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.getApprPopList(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
}
