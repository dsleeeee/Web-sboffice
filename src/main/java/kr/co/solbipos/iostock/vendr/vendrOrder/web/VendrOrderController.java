package kr.co.solbipos.iostock.vendr.vendrOrder.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.cmm.service.IostockCmmService;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
import kr.co.solbipos.iostock.vendr.vendrOrder.service.VendrOrderService;
import kr.co.solbipos.iostock.vendr.vendrOrder.service.VendrOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
    private final IostockCmmService iostockCmmService;

    @Autowired
    public VendrOrderController(SessionService sessionService, VendrOrderService vendrOrderService, IostockCmmService iostockCmmService) {
        this.sessionService = sessionService;
        this.vendrOrderService = vendrOrderService;
        this.iostockCmmService = iostockCmmService;
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
     * 거래처 발주등록 - 거래처 발주 리스트 조회
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
     * 거래처 발주등록 - 발주정보 진행상태 변경
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 23.
     */
    @RequestMapping(value = "/vendrOrderDtl/saveProcFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProcFg(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrOrderVO vendrOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vendrOrderService.saveProcFg(vendrOrderVO, sessionInfoVO);

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
     * 거래처 발주등록 - 발주상품 추가/변경 등록 리스트 조회
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
     * 거래처 발주등록 - 발주상품 추가/변경 등록 리스트 저장
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
     * 다이나믹 콤보조회 - 발주타입 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 21.
     */
    @RequestMapping(value = "/vendrOrderDtl/getOrderTypeCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderTypeCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            iostockCmmVO.setSelectTable("TB_HQ_NMCODE");
            iostockCmmVO.setSelectCd("NMCODE_CD");
            iostockCmmVO.setSelectNm("NMCODE_NM");
            iostockCmmVO.setSelectWhere("HQ_OFFICE_CD='"+sessionInfoVO.getHqOfficeCd()+"' AND NMCODE_GRP_CD = 'AA1'");
            list = iostockCmmService.selectDynamicCodeList(iostockCmmVO, sessionInfoVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            iostockCmmVO.setSelectTable("TB_MS_STORE_NMCODE");
            iostockCmmVO.setSelectCd("NMCODE_CD");
            iostockCmmVO.setSelectNm("NMCODE_NM");
            iostockCmmVO.setSelectWhere("STORE_CD='"+sessionInfoVO.getStoreCd()+"' AND NMCODE_GRP_CD = 'AA1'");
            list = iostockCmmService.selectDynamicCodeList(iostockCmmVO, sessionInfoVO);
        }

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }


    /**
     * 다이나믹 콤보조회 - 발주타입 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 21.
     */
    @RequestMapping(value = "/vendrOrderDtl/getProcFgCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        iostockCmmVO.setSelectTable("TB_CM_NMCODE");
        iostockCmmVO.setSelectCd("NMCODE_CD");
        iostockCmmVO.setSelectNm("NMCODE_NM");
        iostockCmmVO.setSelectWhere("NMCODE_GRP_CD='096' AND NMCODE_ITEM_1 LIKE '%"+iostockCmmVO.getProcFg()+"%'");
        List<DefaultMap<String>> list = iostockCmmService.selectDynamicCodeList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }


    /**
     * 거래처 발주등록 - 발주서 발주정보 조회(발주처, 공급자 정보)
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrOrderVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 27.
     */
    @RequestMapping(value = "/vendrOrderReport/vendrOrderReportInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrOrderReportInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrOrderVO vendrOrderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = vendrOrderService.getVendrOrderReportInfo(vendrOrderVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 발주등록 - 엑셀업로드
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 18.
     */
    @RequestMapping(value = "/vendrOrderProdReg/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
        Model model, ExcelUploadVO excelUploadVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vendrOrderService.excelUpload(excelUploadVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
