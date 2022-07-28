package kr.co.solbipos.iostock.frnchs.prod.web;

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
import kr.co.solbipos.iostock.frnchs.prod.service.ProdFrnchsService;
import kr.co.solbipos.iostock.frnchs.prod.service.ProdFrnchsVO;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : ProdController.java
 * @Description : 수불관리 > 본사-매장간 입출고관리 > 상품별 입출고내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.05  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 12.05
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/frnchs/prod")
public class ProdFrnchsController {
    private final SessionService sessionService;
    private final ProdFrnchsService prodFrnchsService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;
    private final DstbReqService dstbReqService;

    @Autowired
    public ProdFrnchsController(SessionService sessionService, ProdFrnchsService prodFrnchsService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService, DstbReqService dstbReqService) {
        this.sessionService = sessionService;
        this.prodFrnchsService = prodFrnchsService;
        this.storeOrderService = storeOrderService;
        this.cmmEnvService = cmmEnvService;
        this.dstbReqService = dstbReqService;
    }

    /**
     * 거래처 상품별 입출고내역 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/prod/view.sb", method = RequestMethod.GET)
    public String prodFrnchsView(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "iostock/frnchs/prod/prod";
    }


    /**
     * 본사 상품별 입출고내역 - 상품별 입출고내역 엑셀리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/prod/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdFrnchsList(HttpServletRequest request, HttpServletResponse response,
        Model model, ProdFrnchsVO prodFrnchsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>>
            list = prodFrnchsService.getProdFrnchsList(prodFrnchsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodFrnchsVO);
    }
    
    
    /**
     * 거래처 상품별 입출고내역 - 상품별 입출고내역 엑셀리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodVO
     * @return  String
     * @author  조동훤
     * @since   2020. 04. 21.
     */
    @RequestMapping(value = "/prod/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdFrnchsExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, ProdFrnchsVO prodFrnchsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>>
            list = prodFrnchsService.getProdFrnchsExcelList(prodFrnchsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodFrnchsVO);
    }
    
    
    /**
     * 거래처 발주대비 입고현황 - 상품 입고현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderStockInfoVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/prodInOutstockInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInOutstockInfoList(HttpServletRequest request, HttpServletResponse response,
        Model model, ProdFrnchsVO prodFrnchsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodFrnchsService.getProdInOutstockInfoList(prodFrnchsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodFrnchsVO);
    }
}
