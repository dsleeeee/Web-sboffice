package kr.co.solbipos.iostock.order.storeOrder.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmCodeService;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderDtlVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreOrderController.java
 * @Description : 수불관리 > 수주관리 > 주문등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.10  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 09.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/order/storeOrder")
public class StoreOrderController {

    private final SessionService sessionService;
    private final CmmEnvService cmmEnvService;
    private final StoreOrderService storeOrderService;
    private final CmmCodeService cmmCodeService;

    @Autowired
    public StoreOrderController(SessionService sessionService, CmmEnvService cmmEnvService, StoreOrderService storeOrderService, CmmCodeService cmmCodeService) {
        this.sessionService = sessionService;
        this.cmmEnvService = cmmEnvService;
        this.storeOrderService = storeOrderService;
        this.cmmCodeService = cmmCodeService;
    }

    /**
     * 주문등록 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 09. 10.
     */
    @RequestMapping(value = "/storeOrder/view.sb", method = RequestMethod.GET)
    public String storeCloseView(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 본사 환경설정 1042(수발주옵션) 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1042");
        String envst1042 = cmmEnvService.getHqEnvst(hqEnvstVO);

        // 본사 환경설정 1242(거래처출고구분) 조회
        hqEnvstVO.setEnvstCd("1242");
        String envst1242 = CmmUtil.nvl(cmmEnvService.getHqEnvst(hqEnvstVO), "0");

        // 매장 환경설정 1044(출고요청일자선택) 조회
        StoreEnvVO storeEnvVO = new StoreEnvVO();
        storeEnvVO.setStoreCd(sessionInfoVO.getStoreCd());
        storeEnvVO.setEnvstCd("1044");
        String envst1044 = CmmUtil.nvl(cmmEnvService.getStoreEnvst(storeEnvVO), "Y");

        // 출고요청가능일 조회
        StoreOrderVO storeOrderVO = new StoreOrderVO();
        storeOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
        String reqDate = storeOrderService.getReqDate(storeOrderVO);

        // 본사 거래처 콤보박스
        model.addAttribute("vendrList", convertToJson(storeOrderService.getHqVendrCombo(storeOrderVO, sessionInfoVO)));

        model.addAttribute("envst1042", envst1042);
        model.addAttribute("envst1242", envst1242);
        model.addAttribute("envst1044", envst1044);
        model.addAttribute("reqDate" , reqDate);

        return "iostock/order/storeOrder/storeOrder";
    }

    /**
     * 주문등록 - 주문 HD 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 10.
     */
    @RequestMapping(value = "/storeOrder/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOrderList(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreOrderVO storeOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeOrderVO.setStoreCd(sessionInfoVO.getStoreCd());

        List<DefaultMap<String>> list = storeOrderService.getStoreOrderList(storeOrderVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeOrderVO);
    }

    /**
     * 주문등록 - 주문 DT 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeOrderDtlVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 11.
     */
    @RequestMapping(value = "/storeOrderDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOrderDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreOrderDtlVO storeOrderDtlVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeOrderDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeOrderDtlVO.setStoreCd(sessionInfoVO.getStoreCd());

        List<DefaultMap<String>> list = storeOrderService.getStoreOrderDtlList(storeOrderDtlVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeOrderDtlVO);
    }

    /**
     * 주문등록 - 상품추가 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeOrderDtlVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 11.
     */
    @RequestMapping(value = "/storeOrderRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOrderRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreOrderDtlVO storeOrderDtlVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeOrderDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeOrderDtlVO.setStoreCd(sessionInfoVO.getStoreCd());

        List<DefaultMap<String>> list = storeOrderService.getStoreOrderRegistList(storeOrderDtlVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeOrderDtlVO);
    }

    /**
     * 주문등록 - 주문상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   storeOrderDtlVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 12.
     */
    @RequestMapping(value = "/storeOrderRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreOrderRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody StoreOrderDtlVO[] storeOrderDtlVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeOrderService.saveStoreOrderRegist(storeOrderDtlVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 주문등록 - 매장마감여부 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 12.
     */
    @RequestMapping(value = "/storeOrderRegist/storeCloseCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreCloseCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreOrderVO storeOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeOrderVO.setStoreCd(sessionInfoVO.getStoreCd());

        DefaultMap<String> result = storeOrderService.getStoreCloseCheck(storeOrderVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 주문등록 - 주문진행구분 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 12.
     */
    @RequestMapping(value = "/storeOrderRegist/orderProcFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderProcFgCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreOrderVO storeOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 넘어오는 매장코드가 없으면 session 값의 매장코드로 세팅한다.
        if(StringUtil.getOrBlank(storeOrderVO.getStoreCd()).equals("")) {
            storeOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        DefaultMap<String> result = storeOrderService.getOrderProcFgCheck(storeOrderVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 주문등록 - 매장여신 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 12.
     */
    @RequestMapping(value = "/storeOrderRegist/storeLoan.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreLoan(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreOrderVO storeOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 넘어오는 매장코드가 없으면 session 값의 매장코드로 세팅한다.
        if(StringUtil.getOrBlank(storeOrderVO.getStoreCd()).equals("")) {
            storeOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        DefaultMap<String> result = storeOrderService.getStoreLoan(storeOrderVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 주문등록 - 출고요청가능일인지 여부 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 14.
     */
    @RequestMapping(value = "/storeOrderRegist/orderDateCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOrderDateCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreOrderVO storeOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(storeOrderVO.getStoreCd() == null || storeOrderVO.getStoreCd() == ""){
        	storeOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        DefaultMap<String> result = storeOrderService.getStoreOrderDateCheck(storeOrderVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 주문등록 - 주문등록 확정
     * @param   request
     * @param   response
     * @param   model
     * @param   storeOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 17.
     */
    @RequestMapping(value = "/storeOrderDtl/confirm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreOrderConfirm(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody StoreOrderVO storeOrderVO) {


        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeOrderService.saveStoreOrderConfirm(storeOrderVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 주문등록 - 엑셀업로드
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadMPSVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 10.
     */
    @RequestMapping(value = "/storeOrderRegist/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
        Model model, ExcelUploadMPSVO excelUploadMPSVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeOrderService.excelUpload(excelUploadMPSVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 콤보조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 10.
     */
    @RequestMapping(value = "/storeOrder/getCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreOrderVO storeOrderVO) {

        List<DefaultMap<String>> list = cmmCodeService.selectCmmCodeList(request.getParameter("nmcodeGrpCd"));

        return ReturnUtil.returnListJson(Status.OK, list, storeOrderVO);
    }

    /**
     * 주문등록 출고요청일자에 등록한 주문 총 합계 금액 조회
     * @param StoreOrderVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/storeOrder/getOrderTotAmt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderTotAmt(StoreOrderVO StoreOrderVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = storeOrderService.getOrderTotAmt(StoreOrderVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
