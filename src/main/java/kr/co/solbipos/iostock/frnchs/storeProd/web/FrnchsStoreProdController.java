package kr.co.solbipos.iostock.frnchs.storeProd.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.common.service.code.CmmEnvService;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqService;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.frnchs.storeProd.service.FrnchsStoreProdService;
import kr.co.solbipos.iostock.frnchs.storeProd.service.FrnchsStoreProdVO;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : TodayBillSaleDtlController.java
 * @Description : 수불관리 > 본사-매장간 입출고 내역 > 매장-상품별 입출고내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.08  정유경      최초생성
 *
 * @author 솔비포스
 * @since 2020.03.04
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/frnchs/storeprod")
public class FrnchsStoreProdController {
    private final SessionService sessionService;
    private final FrnchsStoreProdService frnchsStoreProdService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;
    private final DstbReqService dstbReqService;

    @Autowired
    public FrnchsStoreProdController(SessionService sessionService, FrnchsStoreProdService frnchsStoreProdService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService, DstbReqService dstbReqService) {
        this.sessionService = sessionService;
        this.frnchsStoreProdService = frnchsStoreProdService;
        this.storeOrderService = storeOrderService;
        this.cmmEnvService = cmmEnvService;
        this.dstbReqService = dstbReqService;
    }


    /**매장별 입출고내역
     * 분류별상품현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author
     * @since   2020.03.04
     */
    @RequestMapping(value = "/storeprod/list.sb", method = RequestMethod.GET)
    public String frnchsStoreProdView(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "iostock/frnchs/storeProd/storeProd";
    }


    /**
     * 매장-상품별 입출고내역 - 매장-상품별 입출고내역 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  정유경
     * @since   2020.03.04
     */
    @RequestMapping(value = "/storeprod/frnchsStoreProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreProdList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreProdVO frnchsStoreProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = frnchsStoreProdService.getFrnchsStoreProdList(frnchsStoreProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreProdVO);
    }
    
    /**
     * 매장-상품별 입출고내역 - 매장-상품별 입출고내역 엑셀리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  조동훤
     * @since   2020.04.23
     */
    @RequestMapping(value = "/storeprod/frnchsStoreProdExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreProdExcelList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreProdVO frnchsStoreProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = frnchsStoreProdService.getFrnchsStoreProdExcelList(frnchsStoreProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreProdVO);
    }

    /**
     * 매장-상품별 입출고내역 - 매장-상품별 입출고내역 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  정유경
     * @since   2020.03.04
     */
    @RequestMapping(value = "/storeprod/frnchsStoreProdDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreProdDtlList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreProdVO frnchsStoreProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = frnchsStoreProdService.getFrnchsStoreProdDtlList(frnchsStoreProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreProdVO);
    }
}
