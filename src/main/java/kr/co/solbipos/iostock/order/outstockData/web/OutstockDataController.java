package kr.co.solbipos.iostock.order.outstockData.web;
/**
 * @Class Name : OutstockDataController.java
 * @Description : 수불관리 > 수주관리 > 출고자료생성
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.04  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018.10.04
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

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
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataService;
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataVO;
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

@Controller
@RequestMapping("/iostock/order/outstockData")
public class OutstockDataController {
    private final SessionService sessionService;
    private final OutstockDataService outstockDataService;
    private final DstbReqService dstbReqService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;

    @Autowired
    public OutstockDataController(SessionService sessionService, OutstockDataService outstockDataService, DstbReqService dstbReqService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService) {
        this.sessionService = sessionService;
        this.outstockDataService = outstockDataService;
        this.dstbReqService = dstbReqService;
        this.storeOrderService = storeOrderService;
        this.cmmEnvService = cmmEnvService;
    }

    /**
     * 출고자료생성 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 04.
     */
    @RequestMapping(value = "/outstockData/view.sb", method = RequestMethod.GET)
    public String outstockDataView(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "iostock/order/outstockData/outstockData";
    }

    /**
     * 출고자료생성 - 출고자료생성 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockDataVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 04.
     */
    @RequestMapping(value = "/outstockData/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOutstockDataList(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockDataVO outstockDataVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        outstockDataVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = outstockDataService.getOutstockDataList(outstockDataVO);

        return ReturnUtil.returnListJson(Status.OK, list, outstockDataVO);
    }

    /**
     * 출고자료생성 - 출고자료생성
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockDataVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 04.
     */
    @RequestMapping(value = "/outstockData/saveDataCreate", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDataCreate(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody OutstockDataVO[] outstockDataVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = outstockDataService.saveDataCreate(outstockDataVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 출고자료생성 - 출고자료생성 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockDataVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 04.
     */
    @RequestMapping(value = "/outstockDataDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOutstockDataDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockDataVO outstockDataVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        outstockDataVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = outstockDataService.getOutstockDataDtlList(outstockDataVO);

        return ReturnUtil.returnListJson(Status.OK, list, outstockDataVO);
    }
}
