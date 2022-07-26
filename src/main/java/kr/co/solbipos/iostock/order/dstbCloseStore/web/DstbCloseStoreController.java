package kr.co.solbipos.iostock.order.dstbCloseStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.DstbCloseStoreService;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.DstbCloseStoreVO;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqService;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
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
 * @Class Name : DstbCloseStoreController.java
 * @Description : 수불관리 > 수주관리 > 분배마감(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.20  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 09.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/iostock/order/dstbCloseStore")
public class DstbCloseStoreController {
    private final SessionService sessionService;
    private final DstbCloseStoreService dstbCloseStoreService;
    private final DstbReqService dstbReqService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;

    @Autowired
    public DstbCloseStoreController(SessionService sessionService, DstbCloseStoreService dstbCloseStoreService, DstbReqService dstbReqService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService) {
        this.sessionService = sessionService;
        this.dstbCloseStoreService = dstbCloseStoreService;
        this.dstbReqService = dstbReqService;
        this.storeOrderService = storeOrderService;
        this.cmmEnvService = cmmEnvService;
    }

    /**
     * 분배마감 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 09. 20.
     */
    @RequestMapping(value = "/dstbCloseStore/view.sb", method = RequestMethod.GET)
    public String dstbCloseStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 본사 환경설정 1242(거래처출고사용여부) 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1242");
        model.addAttribute("envst1242", CmmUtil.nvl(cmmEnvService.getHqEnvst(hqEnvstVO), "0"));

        // 본사 거래처 콤보박스
        StoreOrderVO storeOrderVO = new StoreOrderVO();
        model.addAttribute("vendrList", convertToJson(storeOrderService.getHqVendrCombo(storeOrderVO, sessionInfoVO)));

        // 현재 로그인 사원에 맵핑된 거래처코드 조회
        DstbReqVO dstbReqVO = new DstbReqVO();
        model.addAttribute("empVendrCd", dstbReqService.getEmployeeVendr(dstbReqVO, sessionInfoVO));

        return "iostock/order/dstbCloseStore/dstbCloseStore";
    }

    /**
     * 분배마감 - 분배 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 20.
     */
    @RequestMapping(value = "/dstbCloseStore/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseStoreList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseStoreVO dstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseStoreService.getDstbCloseStoreList(dstbCloseStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseStoreVO);
    }

    /**
     * 분배마감 - 분배 리스트 확정
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 20.
     */
    @RequestMapping(value = "/dstbCloseStore/saveConfirm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbCloseStoreConfirm(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbCloseStoreVO[] dstbCloseStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseStoreService.saveDstbCloseStoreConfirm(dstbCloseStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 분배마감 - 분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 20.
     */
    @RequestMapping(value = "/dstbCloseStoreDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseStoreDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseStoreVO dstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseStoreService.getDstbCloseStoreDtlList(dstbCloseStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseStoreVO);
    }

    /**
     * 분배마감 - 분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 20.
     */
    @RequestMapping(value = "/dstbCloseStoreDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbCloseStoreDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbCloseStoreVO[] dstbCloseStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseStoreService.saveDstbCloseStoreDtl(dstbCloseStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 분배마감 - 추가분배시 주문가능여부 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 21.
     */
    @RequestMapping(value = "/dstbCloseStoreAdd/getOrderFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderFg(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseStoreVO dstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = dstbCloseStoreService.getOrderFg(dstbCloseStoreVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 분배마감 - 추가분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 21.
     */
    @RequestMapping(value = "/dstbCloseStoreAdd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbAddList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseStoreVO dstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseStoreService.getDstbAddList(dstbCloseStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseStoreVO);
    }

    /**
     * 분배마감 - 추가분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 21.
     */
    @RequestMapping(value = "/dstbCloseStoreAdd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbAdd(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbCloseStoreVO[] dstbCloseStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseStoreService.saveDstbAdd(dstbCloseStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 분배마감 - 엑셀업로드
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadMPSVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 14.
     */
    @RequestMapping(value = "/dstbCloseStoreAdd/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
        Model model, ExcelUploadMPSVO excelUploadMPSVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseStoreService.excelUpload(excelUploadMPSVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
