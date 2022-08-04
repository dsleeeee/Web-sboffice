package kr.co.solbipos.iostock.orderReturn.rtnDstbReq.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqService;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import kr.co.solbipos.iostock.orderReturn.rtnDstbReq.service.RtnDstbReqService;
import kr.co.solbipos.iostock.orderReturn.rtnDstbReq.service.RtnDstbReqVO;
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
 * @Class Name : RtnDstbReqController.java
 * @Description : 수불관리 > 매장반품관리 > 반품등록(요청분)
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
@RequestMapping("/iostock/orderReturn/rtnDstbReq")
public class RtnDstbReqController {
    private final SessionService sessionService;
    private final RtnDstbReqService rtnDstbReqService;
    private final DstbReqService dstbReqService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;

    @Autowired
    public RtnDstbReqController(SessionService sessionService, RtnDstbReqService rtnDstbReqService, DstbReqService dstbReqService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService) {
        this.sessionService = sessionService;
        this.rtnDstbReqService = rtnDstbReqService;
        this.dstbReqService = dstbReqService;
        this.storeOrderService = storeOrderService;
        this.cmmEnvService = cmmEnvService;
    }

    /**
     * 반품등록 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbReq/view.sb", method = RequestMethod.GET)
    public String rtnDstbReqView(HttpServletRequest request, HttpServletResponse response, Model model) {

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


        return "iostock/orderReturn/rtnDstbReq/rtnDstbReq";
    }


    /**
     * 반품등록 - 분배 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbReqVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbReq/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnDstbReqList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstbReqVO rtnDstbReqVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstbReqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstbReqService.getRtnDstbReqList(rtnDstbReqVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstbReqVO);
    }

    /**
     * 반품등록 - 분배완료
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbReqVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbReq/saveDstbConfirm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbConfirm(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnDstbReqVO[] rtnDstbReqVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstbReqService.saveDstbConfirm(rtnDstbReqVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품등록 - 분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbReqVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbReqDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnDstbReqDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstbReqVO rtnDstbReqVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstbReqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstbReqService.getRtnDstbReqDtlList(rtnDstbReqVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstbReqVO);
    }

    /**
     * 반품등록 - 분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbReqVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbReqDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnDstbReqDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnDstbReqVO[] rtnDstbReqVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstbReqService.saveRtnDstbReqDtl(rtnDstbReqVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
