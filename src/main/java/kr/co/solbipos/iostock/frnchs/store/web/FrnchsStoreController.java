package kr.co.solbipos.iostock.frnchs.store.web;

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
import kr.co.solbipos.iostock.frnchs.store.service.FrnchsStoreService;
import kr.co.solbipos.iostock.frnchs.store.service.FrnchsStoreVO;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : TodayBillSaleDtlController.java
 * @Description : 매출관리 > 매출현황 > 분류별상품 상세현황
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
@RequestMapping("/iostock/frnchs/store")
public class FrnchsStoreController {
    private final SessionService sessionService;
    private final FrnchsStoreService frnchsStoreService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;
    private final DstbReqService dstbReqService;

    @Autowired
    public FrnchsStoreController(SessionService sessionService, FrnchsStoreService frnchsStoreService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService, DstbReqService dstbReqService) {
        this.sessionService = sessionService;
        this.frnchsStoreService = frnchsStoreService;
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
     * @author	정유경
     * @since   2020.03.04
     */
    @RequestMapping(value = "/store/list.sb", method = RequestMethod.GET)
    public String frnchsStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "iostock/frnchs/store/store";
    }


    /**
     * 매장별 입출고내역 - 매장별 입출고내역 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  정유경
     * @since   2020.03.04
     */
    @RequestMapping(value = "/store/frnchsStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreVO frnchsStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = frnchsStoreService.getFrnchsStoreList(frnchsStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreVO);
    }

    /**
     * 매장별 입출고내역 - 매장별 입출고내역 엑셀리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  조동훤
     * @since   2020.04.22
     */
    @RequestMapping(value = "/store/frnchsStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreExcelList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreVO frnchsStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = frnchsStoreService.getFrnchsStoreExcelList(frnchsStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreVO);
    }

    
    /**
     * 매장별 입출고내역 상세 레이어- 매장별 입출고내역 매장상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  정유경
     * @since   2020.03.04
     */
    @RequestMapping(value = "/store/frnchsStoreInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreInfoList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreVO frnchsStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = frnchsStoreService.getFrnchsStoreInfoList(frnchsStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreVO);
    }

    /**
     * 매장별 입출고내역 상세 레이어- 매장별 입출고내역 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  정유경
     * @since   2020.03.04
     */
    @RequestMapping(value = "/store/frnchsStoreDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreDtlList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreVO frnchsStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = frnchsStoreService.getFrnchsStoreDtlList(frnchsStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreVO);
    }
}
