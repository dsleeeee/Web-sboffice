package kr.co.solbipos.iostock.vendr.vendrInstock.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.iostock.vendr.vendrInstock.service.VendrInstockService;
import kr.co.solbipos.iostock.vendr.vendrInstock.service.VendrInstockVO;
import kr.co.solbipos.iostock.volmErr.volmErr.service.VolmErrService;
import kr.co.solbipos.stock.adj.adj.service.AdjVO;

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
 * @Class Name : VendrInstockController.java
 * @Description : 수불관리 > 거래처(매입)입출고관리 > 입고/반출등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.26  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 11.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/vendr/vendrInstock")
public class VendrInstockController {
    private final SessionService sessionService;
    private final VendrInstockService vendrInstockService;
    private final VolmErrService volmErrService;

    @Autowired
    public VendrInstockController(SessionService sessionService, VendrInstockService vendrInstockService, VolmErrService volmErrService) {
        this.sessionService = sessionService;
        this.vendrInstockService = vendrInstockService;
        this.volmErrService = volmErrService;
    }

    /**
     * 거래처 입고/반출등록 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 11. 26.
     */
    @RequestMapping(value = "/vendrInstock/view.sb", method = RequestMethod.GET)
    public String vendrInstockView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/vendr/vendrInstock/vendrInstock";
    }


    /**
     * 거래처 입고/반출등록 - 거래처 입고/반출 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrInstockVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 26.
     */
    @RequestMapping(value = "/vendrInstock/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrInstockList(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrInstockVO vendrInstockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = vendrInstockService.getVendrInstockList(vendrInstockVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vendrInstockVO);
    }


    /**
     * 거래처 입고/반출등록 - 거래처 입고/반출 등록시 발주번호 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrInstockVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 27.
     */
    @RequestMapping(value = "/vendrInstockOrderSlip/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrInstockOrderSlipList(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrInstockVO vendrInstockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = vendrInstockService.getVendrInstockOrderSlipList(vendrInstockVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vendrInstockVO);
    }


    /**
     * 거래처 입고/반출등록 - 입고/반출정보 상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrInstockVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 26.
     */
    @RequestMapping(value = "/vendrInstockDtl/slipInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrInstockVO vendrInstockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = vendrInstockService.getSlipInfo(vendrInstockVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 입고/반출등록 - 입고/반출 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrInstockVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 26.
     */
    @RequestMapping(value = "/vendrInstockDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVendrInstockDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrInstockVO vendrInstockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = vendrInstockService.saveVendrInstockDtl(vendrInstockVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 입고/반출등록 - 입고/반출정보 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrInstockVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 27.
     */
    @RequestMapping(value = "/vendrInstockDtl/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteVendrInstockDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrInstockVO vendrInstockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vendrInstockService.deleteVendrInstockDtl(vendrInstockVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrInstockVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 27.
     */
    @RequestMapping(value = "/vendrInstockDtl/saveProcFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProcFg(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrInstockVO vendrInstockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vendrInstockService.saveProcFg(vendrInstockVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 입고/반출등록 - 입고/반출상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrInstockVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 27.
     */
    @RequestMapping(value = "/vendrInstockProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrInstockProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrInstockVO vendrInstockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = vendrInstockService.getVendrInstockProdList(vendrInstockVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vendrInstockVO);
    }


    /**
     * 거래처 입고/반출등록 - 진행구분 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrInstockVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 27.
     */
    @RequestMapping(value = "/vendrInstockProd/procFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrInstockVO vendrInstockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = vendrInstockService.getProcFgCheck(vendrInstockVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 입고/반출등록 - 입고/반출상품 등록 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrInstockVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 27.
     */
    @RequestMapping(value = "/vendrInstockProdReg/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrInstockProdRegList(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrInstockVO vendrInstockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = vendrInstockService.getVendrInstockProdRegList(vendrInstockVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vendrInstockVO);
    }


    /**
     * 거래처 입고/반출등록 - 입고/반출상품 등록 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrInstockVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 27.
     */
    @RequestMapping(value = "/vendrInstockProdReg/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVendrInstockProdReg(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody VendrInstockVO[] vendrInstockVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vendrInstockService.saveVendrInstockProdReg(vendrInstockVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 입고/반출등록 - 입고/반출상품 발주내역으로 세팅 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrInstockVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 28.
     */
    @RequestMapping(value = "/vendrInstockOrderInfoReg/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrInstockOrderInfoRegList(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrInstockVO vendrInstockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = vendrInstockService.getVendrInstockOrderInfoRegList(vendrInstockVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vendrInstockVO);
    }


    /**
     * 거래처 입고/반출등록 - 반출서 반출정보 조회(반출처, 공급자 정보)
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrInstockVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 31.
     */
    @RequestMapping(value = "/vendrInstockReport/vendrInstockReportInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrInstockReportInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrInstockVO vendrInstockVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = vendrInstockService.getVendrInstockReportInfo(vendrInstockVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 입고/반출등록 - 엑셀업로드
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadMPSVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 18.
     */
    @RequestMapping(value = "/vendrInstockProdReg/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
        Model model, ExcelUploadMPSVO excelUploadMPSVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vendrInstockService.excelUpload(excelUploadMPSVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
       
}
