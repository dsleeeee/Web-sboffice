package kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqService;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service.RtnStoreOrderDtlVO;
import kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service.RtnStoreOrderService;
import kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service.RtnStoreOrderVO;
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

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : RtnStoreOrderController.java
 * @Description : 수불관리 > 반품관리 > 반품등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.15  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 10.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/orderReturn/rtnStoreOrder")
public class RtnStoreOrderController {
    private final SessionService sessionService;
    private final CmmEnvService cmmEnvService;
    private final RtnStoreOrderService rtnStoreOrderService;
    private final StoreOrderService storeOrderService;
    private final DstbReqService dstbReqService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public RtnStoreOrderController(SessionService sessionService, CmmEnvService cmmEnvService, RtnStoreOrderService rtnStoreOrderService, StoreOrderService storeOrderService, DstbReqService dstbReqService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.cmmEnvService = cmmEnvService;
        this.rtnStoreOrderService = rtnStoreOrderService;
        this.storeOrderService = storeOrderService;
        this.dstbReqService = dstbReqService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 반품등록 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/rtnStoreOrder/view.sb", method = RequestMethod.GET)
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
        RtnStoreOrderVO rtnStoreOrderVO = new RtnStoreOrderVO();
        rtnStoreOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        rtnStoreOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
        String reqDate = rtnStoreOrderService.getReqDate(rtnStoreOrderVO);

        // 본사 거래처 콤보박스
        StoreOrderVO storeOrderVO = new StoreOrderVO();
        model.addAttribute("vendrList", convertToJson(storeOrderService.getHqVendrCombo(storeOrderVO, sessionInfoVO)));

        // 현재 로그인 사원에 맵핑된 거래처코드 조회
        DstbReqVO dstbReqVO = new DstbReqVO();
        model.addAttribute("empVendrCd", dstbReqService.getEmployeeVendr(dstbReqVO, sessionInfoVO));

        model.addAttribute("envst1042", envst1042);
        model.addAttribute("envst1242", envst1242);
        model.addAttribute("envst1044", envst1044);
        model.addAttribute("reqDate" , reqDate);

        // [1241 창고사용여부] 환경설정값 조회
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("storageEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1241"), "0"));
            System.out.println("storageEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1241"), "0"));
        } else {
            model.addAttribute("storageEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1241"), "0"));
            System.out.println("storageEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1241"), "0"));
        }

        return "iostock/orderReturn/rtnStoreOrder/rtnStoreOrder";
    }

    /**
     * 반품등록 - 반품 HD 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnStoreOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/rtnStoreOrder/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnStoreOrderList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnStoreOrderVO rtnStoreOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnStoreOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        rtnStoreOrderVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        //        rtnStoreOrderVO.setStoreCd(sessionInfoVO.getStoreCd());

        List<DefaultMap<String>> list = rtnStoreOrderService.getRtnStoreOrderList(rtnStoreOrderVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnStoreOrderVO);
    }

    /**
     * 반품등록 - 반품 DT 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnStoreOrderDtlVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/rtnStoreOrderDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnStoreOrderDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnStoreOrderDtlVO rtnStoreOrderDtlVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnStoreOrderDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        //        rtnStoreOrderDtlVO.setStoreCd(sessionInfoVO.getStoreCd());

        List<DefaultMap<String>> list = rtnStoreOrderService.getRtnStoreOrderDtlList(rtnStoreOrderDtlVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnStoreOrderDtlVO);
    }

    /**
     * 반품등록 - 상품추가 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnStoreOrderDtlVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/rtnStoreOrderRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnStoreOrderRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnStoreOrderDtlVO rtnStoreOrderDtlVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnStoreOrderDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        //        rtnStoreOrderDtlVO.setStoreCd(sessionInfoVO.getStoreCd());

        List<DefaultMap<String>> list = rtnStoreOrderService.getRtnStoreOrderRegistList(rtnStoreOrderDtlVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnStoreOrderDtlVO);
    }

    /**
     * 반품등록 - 반품상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnStoreOrderDtlVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/rtnStoreOrderRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnStoreOrderRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnStoreOrderDtlVO[] rtnStoreOrderDtlVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = rtnStoreOrderService.saveRtnStoreOrderRegist(rtnStoreOrderDtlVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
    
    /**
     * 반품등록 - 반품상세 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnStoreOrderDtlVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/rtnStoreOrderDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnStoreOrderDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnStoreOrderDtlVO[] rtnStoreOrderDtlVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnStoreOrderService.saveRtnStoreOrderDtl(rtnStoreOrderDtlVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
    
    /**
     * 반품등록 - 주문진행구분 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnStoreOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/rtnStoreOrderRegist/orderProcFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderProcFgCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnStoreOrderVO rtnStoreOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnStoreOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 넘어오는 매장코드가 없으면 session 값의 매장코드로 세팅한다.
        //        if(StringUtil.getOrBlank(rtnStoreOrderVO.getStoreCd()).equals("")) {
        //            rtnStoreOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
        //        }

        DefaultMap<String> result = rtnStoreOrderService.getOrderProcFgCheck(rtnStoreOrderVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품등록 -  반품등록 확정
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnStoreOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/rtnStoreOrderDtl/confirm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnStoreOrderConfirm(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody RtnStoreOrderVO rtnStoreOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnStoreOrderService.saveRtnStoreOrderConfirm(rtnStoreOrderVO, sessionInfoVO);

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
    @RequestMapping(value = "/rtnStoreOrderRegist/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
        Model model, ExcelUploadMPSVO excelUploadMPSVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = rtnStoreOrderService.excelUpload(excelUploadMPSVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
