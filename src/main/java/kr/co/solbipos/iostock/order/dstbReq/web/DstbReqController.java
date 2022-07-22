package kr.co.solbipos.iostock.order.dstbReq.web;
/**
 * @Class Name : dstbReqController.java
 * @Description : 수불관리 > 수주관리 > 분배등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.17  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 09.17
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
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/iostock/order/dstbReq")
public class DstbReqController {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final DstbReqService dstbReqService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;

    @Autowired
    public DstbReqController(SessionService sessionService, DstbReqService dstbReqService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService) {
        this.sessionService = sessionService;
        this.dstbReqService = dstbReqService;
        this.storeOrderService = storeOrderService;
        this.cmmEnvService = cmmEnvService;
    }

    /**
     * 분배등록 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 09. 17.
     */
    @RequestMapping(value = "/dstbReq/view.sb", method = RequestMethod.GET)
    public String dstbReqView(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "iostock/order/dstbReq/dstbReq";
    }


    /**
     * 분배등록 - 분배 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbReqVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 18.
     */
    @RequestMapping(value = "/dstbReq/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbReqList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbReqVO dstbReqVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbReqVO.setOrgnFg		(sessionInfoVO.getOrgnFg().getCode());	//소속구분(M:시스템, A:대리점, H:본사, S:매장,가맹점
        dstbReqVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd()		);	//본사코드
        dstbReqVO.setStoreCd	(sessionInfoVO.getStoreCd()			);	//매장코드
        LOGGER.debug("### OrgnFg        : " + dstbReqVO.getOrgnFg		());
        LOGGER.debug("### hqOfficeCd    : " + dstbReqVO.getHqOfficeCd	());
        LOGGER.debug("### storeCd       : " + dstbReqVO.getStoreCd		());

        List<DefaultMap<String>> list = dstbReqService.getDstbReqList(dstbReqVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbReqVO);
    }

    /**
     * 분배등록 - 분배완료
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbReqVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 19.
     */
    @RequestMapping(value = "/dstbReq/saveDstbConfirm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbConfirm(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbReqVO[] dstbReqVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbReqService.saveDstbConfirm(dstbReqVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 분배등록 - 분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbReqVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 18.
     */
    @RequestMapping(value = "/dstbReqDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbReqDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbReqVO dstbReqVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbReqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbReqService.getDstbReqDtlList(dstbReqVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbReqVO);
    }

    /**
     * 분배등록 - 분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbReqVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 18.
     */
    @RequestMapping(value = "/dstbReqDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbReqDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbReqVO[] dstbReqVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbReqService.saveDstbReqDtl(dstbReqVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
