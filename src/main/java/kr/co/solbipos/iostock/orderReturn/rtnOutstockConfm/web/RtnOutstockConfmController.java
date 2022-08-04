package kr.co.solbipos.iostock.orderReturn.rtnOutstockConfm.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqService;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockConfm.service.RtnOutstockConfmService;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockConfm.service.RtnOutstockConfmVO;
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
 * @Class Name : RtnOutstockConfmController.java
 * @Description : 수불관리 > 매장반품관리 > 반품매장출고
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.17  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018.10.17
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/orderReturn/rtnOutstockConfm")
public class RtnOutstockConfmController {
    private final SessionService sessionService;
    private final RtnOutstockConfmService rtnOutstockConfmService;
    private final CmmEnvUtil cmmEnvUtil;
    private final DstbReqService dstbReqService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;

    @Autowired
    public RtnOutstockConfmController(SessionService sessionService, RtnOutstockConfmService rtnOutstockConfmService, CmmEnvUtil cmmEnvUtil, DstbReqService dstbReqService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService) {
        this.sessionService = sessionService;
        this.rtnOutstockConfmService = rtnOutstockConfmService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.dstbReqService = dstbReqService;
        this.storeOrderService = storeOrderService;
        this.cmmEnvService = cmmEnvService;
    }

    /**
     * 반품매장출고 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 05.
     */
    @RequestMapping(value = "/rtnOutstockConfm/view.sb", method = RequestMethod.GET)
    public String rtnOutstockConfmView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // [1241 창고사용여부] 환경설정값 조회
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("storageEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1241"), "0"));
            System.out.println("storageEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1241"), "0"));
        } else {
            model.addAttribute("storageEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1241"), "0"));
            System.out.println("storageEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1241"), "0"));
        }

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

        return "iostock/orderReturn/rtnOutstockConfm/rtnOutstockConfm";
    }

    /**
     * 반품매장출고 - 매장요청 미확정건, 출고자료 미생성건 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 05.
     */
    @RequestMapping(value = "/rtnOutstockConfm/reqNoConfirmCnt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getReqNoConfirmCnt(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnOutstockConfmVO rtnOutstockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnOutstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = rtnOutstockConfmService.getReqNoConfirmCnt(rtnOutstockConfmVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품매장출고 - 반품매장출고 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 05.
     */
    @RequestMapping(value = "/rtnOutstockConfm/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnOutstockConfmList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnOutstockConfmVO rtnOutstockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnOutstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnOutstockConfmService.getRtnOutstockConfmList(rtnOutstockConfmVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnOutstockConfmVO);
    }

    /**
     * 반품매장출고 - 반품매장출고
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockConfmVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockConfm/saveOutstockConfirm", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDataCreate(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnOutstockConfmVO[] rtnOutstockConfmVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnOutstockConfmService.saveOutstockConfirm(rtnOutstockConfmVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품매장출고 - 전표상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockConfmDtl/getSlipNoInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipNoInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnOutstockConfmVO rtnOutstockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnOutstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = rtnOutstockConfmService.getSlipNoInfo(rtnOutstockConfmVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품매장출고 - 반품매장출고 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockConfmDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnOutstockConfmDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnOutstockConfmVO rtnOutstockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnOutstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnOutstockConfmService.getRtnOutstockConfmDtlList(rtnOutstockConfmVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnOutstockConfmVO);
    }

    /**
     * 반품매장출고 - 반품매장출고 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockConfmVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockConfmDtl/save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnOutstockConfmDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnOutstockConfmVO[] rtnOutstockConfmVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnOutstockConfmService.saveRtnOutstockConfmDtl(rtnOutstockConfmVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품매장출고 - 반품매장출고 이후 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockConfmDtl/saveOutstockAfter.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveOutstockAfter(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnOutstockConfmVO rtnOutstockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnOutstockConfmService.saveOutstockAfter(rtnOutstockConfmVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
