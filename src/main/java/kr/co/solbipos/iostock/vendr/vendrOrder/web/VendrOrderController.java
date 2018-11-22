package kr.co.solbipos.iostock.vendr.vendrOrder.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.vendr.vendrOrder.service.VendrOrderService;
import kr.co.solbipos.iostock.vendr.vendrOrder.service.VendrOrderVO;
import kr.co.solbipos.iostock.volmErr.volmErr.service.VolmErrService;
import kr.co.solbipos.iostock.volmErr.volmErr.service.VolmErrVO;
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

/**
 * @Class Name : VendrOrderController.java
 * @Description : 수불관리 > 거래처(매입)입출고관리 > 발주등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.20  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 11.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/vendr/vendrOrder")
public class VendrOrderController {
    private final SessionService sessionService;
    private final VendrOrderService vendrOrderService;
    private final VolmErrService volmErrService;

    @Autowired
    public VendrOrderController(SessionService sessionService, VendrOrderService vendrOrderService, VolmErrService volmErrService) {
        this.sessionService = sessionService;
        this.vendrOrderService = vendrOrderService;
        this.volmErrService = volmErrService;
    }

    /**
     * 거래처 발주등록 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 11. 20.
     */
    @RequestMapping(value = "/vendrOrder/view.sb", method = RequestMethod.GET)
    public String vendrOrderView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/vendr/vendrOrder/vendrOrder";
    }


    /**
     * 거래처 발주등록 - 거래처 발주등록 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 20.
     */
    @RequestMapping(value = "/vendrOrder/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrOrderList(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrOrderVO vendrOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = vendrOrderService.getVendrOrderList(vendrOrderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vendrOrderVO);
    }


    /**
     * 거래처 발주등록 - 발주정보 상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 20.
     */
    @RequestMapping(value = "/vendrOrderDtl/slipInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrOrderVO vendrOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = vendrOrderService.getSlipInfo(vendrOrderVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 발주등록 - 발주정보 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 20.
     */
    @RequestMapping(value = "/vendrOrderDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVendrOrderDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrOrderVO vendrOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = vendrOrderService.saveVendrOrderDtl(vendrOrderVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 발주등록 - 발주정보 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 22.
     */
    @RequestMapping(value = "/vendrOrderDtl/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteVendrOrderDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrOrderVO vendrOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vendrOrderService.deleteVendrOrderDtl(vendrOrderVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 발주등록 - 발주상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 21.
     */
    @RequestMapping(value = "/vendrOrderProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrOrderProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrOrderVO vendrOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = vendrOrderService.getVendrOrderProdList(vendrOrderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vendrOrderVO);
    }


    /**
     * 거래처 발주등록 - 진행구분 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 21.
     */
    @RequestMapping(value = "/vendrOrderProd/procFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrOrderVO vendrOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = vendrOrderService.getProcFgCheck(vendrOrderVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 발주등록 - 발주상품 등록 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 21.
     */
    @RequestMapping(value = "/vendrOrderProdReg/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrOrderProdRegList(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrOrderVO vendrOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = vendrOrderService.getVendrOrderProdRegList(vendrOrderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vendrOrderVO);
    }


    /**
     * 거래처 발주등록 - 발주상품 등록 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrOrderVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 22.
     */
    @RequestMapping(value = "/vendrOrderProdReg/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVendrOrderProdReg(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody VendrOrderVO[] vendrOrderVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vendrOrderService.saveVendrOrderProdReg(vendrOrderVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }









    /**
     * 다이나믹 콤보조회
     * @param   request
     * @param   response
     * @param   model
     * @param   volmErrVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 22.
     */
    @RequestMapping(value = "/vendrOrderDtl/getDynamicCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDynamicCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, VolmErrVO volmErrVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        volmErrVO.setSelectTable("TB_HQ_NMCODE");
        volmErrVO.setSelectCd("NMCODE_CD");
        volmErrVO.setSelectNm("NMCODE_NM");
        volmErrVO.setSelectWhere("HQ_OFFICE_CD='"+sessionInfoVO.getHqOfficeCd()+"' AND NMCODE_GRP_CD = 'AA1'");
        List<DefaultMap<String>> list = volmErrService.selectDynamicCodeList(volmErrVO);

        return ReturnUtil.returnListJson(Status.OK, list, volmErrVO);
    }


    /**
     * 거래처 발주등록 - 거래처 선택모듈 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 21.
     */
    @RequestMapping(value = "/vendrOrder/selectVendrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrList(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrOrderVO vendrOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = vendrOrderService.getVendrList(vendrOrderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vendrOrderVO);
    }
}
