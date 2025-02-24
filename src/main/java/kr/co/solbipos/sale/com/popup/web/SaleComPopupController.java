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
    
    /**
     * 매출공통팝업 - 상품선택(대분류) 팝업
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 04.
     */
    @RequestMapping(value = "/classProd/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getClassProdList(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.getClassProdList(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
    
    /**
     * 매출공통팝업 - 상품선택(상품) 팝업
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 04.
     */
    @RequestMapping(value = "/prodNm/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.getProdList(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
    
    
    /**
     * 매출공통팝업 - 상품선택(상품) - 결제수단별탭 팝업
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  박지선
     * @since   2020. 03. 03.
     */
    @RequestMapping(value = "/payFg/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayFgList(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.getPayFgList(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
    
    /**
     * 매출공통팝업 - 매장정보,매출종합내역 - 영수증 팝업
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 03. 09.
     */
    @RequestMapping(value = "/billSalePop1/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectBillSalePop1(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.selectBillSalePop1(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
    
    /**
     * 매출공통팝업 - 결제내역 - 영수증 팝업
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 03. 09.
     */
    @RequestMapping(value = "/billSalePop3/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectBillSalePop3(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.selectBillSalePop3(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
    
    /**
     * 매출공통팝업 - 회원정보 - 영수증 팝업
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 03. 09.
     */
    @RequestMapping(value = "/billSalePop4/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectBillSalePop4(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.selectBillSalePop4(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
    
    /**
     * 매출공통팝업 - 신용카드,현금 결제내역 - 영수증 팝업
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 03. 09.
     */
    @RequestMapping(value = "/billSalePop5/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectBillSalePop5(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.selectBillSalePop5(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
    
    /**
     * 매출공통팝업 - 상품내역 - 영수증 팝업
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 03. 09.
     */
    @RequestMapping(value = "/billSalePop6/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectBillSalePop6(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.selectBillSalePop6(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
    
    /**
     * 매출공통팝업 - 원거래매출정보 - 영수증 팝업
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 03. 09.
     */
    @RequestMapping(value = "/billSalePop7/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectBillSalePop7(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.selectBillSalePop7(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
    
    /**
     * 매출공통팝업 - 신용카드,현금 결제취소내역 - 영수증 팝업
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 03. 09.
     */
    @RequestMapping(value = "/billRtnPop5/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectBillRtnPop5(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.selectBillRtnPop5(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
    
    /**
     * 매출공통팝업 - 원 신용카드,현금 결제내역 - 영수증 팝업
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleComPopupVO
     * @return  String
     * @author  김진
     * @since   2020. 03. 09.
     */
    @RequestMapping(value = "/billRealPop/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectbillRealPop(HttpServletRequest request, HttpServletResponse response, Model model, SaleComPopupVO saleComPopupVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = saleComPopupService.selectbillRealPop(saleComPopupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleComPopupVO);
    }
}
