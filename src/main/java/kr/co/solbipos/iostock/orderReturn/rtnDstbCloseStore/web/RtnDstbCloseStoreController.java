package kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqService;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.RtnDstbCloseStoreService;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.RtnDstbCloseStoreVO;
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
 * @Class Name : RtnDstbCloseStoreController.java
 * @Description : 수불관리 > 매장반품관리 > 반품마감(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.16  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 10.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/iostock/orderReturn/rtnDstbCloseStore")
public class RtnDstbCloseStoreController {
    private final SessionService sessionService;
    private final RtnDstbCloseStoreService rtnDstbCloseStoreService;
    private final DstbReqService dstbReqService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;

    @Autowired
    public RtnDstbCloseStoreController(SessionService sessionService, RtnDstbCloseStoreService rtnDstbCloseStoreService, DstbReqService dstbReqService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService) {
        this.sessionService = sessionService;
        this.rtnDstbCloseStoreService = rtnDstbCloseStoreService;
        this.dstbReqService = dstbReqService;
        this.storeOrderService = storeOrderService;
        this.cmmEnvService = cmmEnvService;
    }

    /**
     * 반품마감 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStore/view.sb", method = RequestMethod.GET)
    public String rtnDstbCloseStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStore";
    }

    /**
     * 반품마감 - 분배 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStore/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnDstbCloseStoreList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstbCloseStoreVO rtnDstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstbCloseStoreService.getRtnDstbCloseStoreList(rtnDstbCloseStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstbCloseStoreVO);
    }

    /**
     * 반품마감 - 분배 리스트 확정
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseStoreVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStore/saveConfirm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnDstbCloseStoreConfirm(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstbCloseStoreService.saveRtnDstbCloseStoreConfirm(rtnDstbCloseStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품마감 - 분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStoreDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnDstbCloseStoreDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstbCloseStoreVO rtnDstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstbCloseStoreService.getRtnDstbCloseStoreDtlList(rtnDstbCloseStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstbCloseStoreVO);
    }

    /**
     * 반품마감 - 분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseStoreVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStoreDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnDstbCloseStoreDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstbCloseStoreService.saveRtnDstbCloseStoreDtl(rtnDstbCloseStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품마감 - 추가분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStoreAdd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbAddList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstbCloseStoreVO rtnDstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstbCloseStoreService.getDstbAddList(rtnDstbCloseStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstbCloseStoreVO);
    }

    /**
     * 반품마감 - 추가분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseStoreVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStoreAdd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbAdd(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstbCloseStoreService.saveDstbAdd(rtnDstbCloseStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 반품마감 - 엑셀업로드
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 14.
     */
    @RequestMapping(value = "/rtndstbCloseStoreAdd/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
        Model model, ExcelUploadVO excelUploadVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstbCloseStoreService.excelUpload(excelUploadVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

}
