package kr.co.solbipos.iostock.order.dstbCloseProd.web;
/**
 * @Class Name : DstbCloseProdController.java
 * @Description : 수불관리 > 수주관리 > 분배마감(상품별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.01  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018.10.01
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
import kr.co.solbipos.iostock.order.dstbCloseProd.service.DstbCloseProdService;
import kr.co.solbipos.iostock.order.dstbCloseProd.service.DstbCloseProdVO;
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

@Controller
@RequestMapping("/iostock/order/dstbCloseProd")
public class DstbCloseProdController {
    private final SessionService sessionService;
    private final DstbCloseProdService dstbCloseProdService;
    private final DstbReqService dstbReqService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;

    @Autowired
    public DstbCloseProdController(SessionService sessionService, DstbCloseProdService dstbCloseProdService, DstbReqService dstbReqService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService) {
        this.sessionService = sessionService;
        this.dstbCloseProdService = dstbCloseProdService;
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
     * @since   2018. 10. 01.
     */
    @RequestMapping(value = "/dstbCloseProd/view.sb", method = RequestMethod.GET)
    public String dstbCloseProdView(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "iostock/order/dstbCloseProd/dstbCloseProd";
    }

    /**
     * 분배마감 - 분배 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 01.
     */
    @RequestMapping(value = "/dstbCloseProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseProdVO dstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseProdService.getDstbCloseProdList(dstbCloseProdVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseProdVO);
    }

    /**
     * 분배마감 - 분배 리스트 확정
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 01.
     */
    @RequestMapping(value = "/dstbCloseProd/saveConfirm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbCloseProdConfirm(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbCloseProdVO[] dstbCloseProdVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseProdService.saveDstbCloseProdConfirm(dstbCloseProdVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 분배마감 - 분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 01.
     */
    @RequestMapping(value = "/dstbCloseProdDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseProdDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseProdVO dstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseProdService.getDstbCloseProdDtlList(dstbCloseProdVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseProdVO);
    }

    /**
     * 분배마감 - 분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 01.
     */
    @RequestMapping(value = "/dstbCloseProdDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbCloseProdDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbCloseProdVO[] dstbCloseProdVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseProdService.saveDstbCloseProdDtl(dstbCloseProdVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 분배마감 - 추가등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 02.
     */
    @RequestMapping(value = "/dstbCloseProdAddProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseProdAddProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseProdVO dstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseProdService.getDstbCloseProdAddProdList(dstbCloseProdVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseProdVO);
    }

    /**
     * 분배마감 - 추가등록 분배등록 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 02.
     */
    @RequestMapping(value = "/dstbCloseProdAddRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseProdAddRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseProdVO dstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseProdService.getDstbCloseProdAddRegistList(dstbCloseProdVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseProdVO);
    }

    /**
     * 분배마감 - 추가분배 분배등록 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 02.
     */
    @RequestMapping(value = "/dstbCloseProdAddRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbCloseProdAddRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbCloseProdVO[] dstbCloseProdVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseProdService.saveDstbCloseProdAddRegist(dstbCloseProdVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
    
    
    /**
     * 분배마감 - 매장 마감여부 확인
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVO
     * @return  String
     * @author  조동훤
     * @since   2020. 04. 22.
     */
    @RequestMapping(value = "/dstbCloseProdAddProd/dstbList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseProdAddProdDstbList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseProdVO dstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        int result = dstbCloseProdService.getDstbCloseProdAddProdDstbList(dstbCloseProdVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
