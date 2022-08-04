package kr.co.solbipos.iostock.orderReturn.rtnOutstockData.web;
/**
 * @Class Name : RtnOutstockDataController.java
 * @Description : 수불관리 > 매장반품관리 > 반품자료생성
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
import kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.RtnOutstockDataService;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.RtnOutstockDataVO;
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
@RequestMapping("/iostock/orderReturn/rtnOutstockData")
public class RtnOutstockDataController {
    private final SessionService sessionService;
    private final RtnOutstockDataService rtnOutstockDataService;
    private final DstbReqService dstbReqService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;

    @Autowired
    public RtnOutstockDataController(SessionService sessionService, RtnOutstockDataService rtnOutstockDataService, DstbReqService dstbReqService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService) {
        this.sessionService = sessionService;
        this.rtnOutstockDataService = rtnOutstockDataService;
        this.dstbReqService = dstbReqService;
        this.storeOrderService = storeOrderService;
        this.cmmEnvService = cmmEnvService;
    }

    /**
     * 반품자료생성 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockData/view.sb", method = RequestMethod.GET)
    public String rtnOutstockDataView(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "iostock/orderReturn/rtnOutstockData/rtnOutstockData";
    }

    /**
     * 반품자료생성 - 반품자료생성 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockDataVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockData/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnOutstockDataList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnOutstockDataVO rtnOutstockDataVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnOutstockDataVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnOutstockDataService.getRtnOutstockDataList(rtnOutstockDataVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnOutstockDataVO);
    }

    /**
     * 반품자료생성 - 반품자료생성
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockDataVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockData/saveDataCreate", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDataCreate(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnOutstockDataVO[] rtnOutstockDataVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnOutstockDataService.saveDataCreate(rtnOutstockDataVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품자료생성 - 반품자료생성 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockDataVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockDataDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnOutstockDataDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnOutstockDataVO rtnOutstockDataVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnOutstockDataVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnOutstockDataService.getRtnOutstockDataDtlList(rtnOutstockDataVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnOutstockDataVO);
    }
}
