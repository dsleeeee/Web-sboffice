package kr.co.solbipos.kookmin.acquire.acquireSilpRegist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.kookmin.acquire.acquireSilpRegist.service.AcquireSlipRegistService;
import kr.co.solbipos.kookmin.acquire.acquireSilpRegist.service.AcquireSlipRegistVO;
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
 * @Class Name  : AcquireSlipRegistController.java
 * @Description : 국민대 > 매입관리 > 매입전표등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.21  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.11.21
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/kookmin/acquire/acquireSlipRegist")
public class AcquireSlipRegistController {

    private final SessionService sessionService;
    private final AcquireSlipRegistService acquireSlipRegistService;

    /**
     * Constructor Injection
     */
    public AcquireSlipRegistController(SessionService sessionService, AcquireSlipRegistService acquireSlipRegistService) {
        this.sessionService = sessionService;
        this.acquireSlipRegistService = acquireSlipRegistService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/acquireSlipRegist/view.sb", method = RequestMethod.GET)
    public String acquireSlipRegist(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/acquire/acquireSlipRegist/acquireSlipRegist";
    }

    /**
     * 매입전표등록 - 거래처 입고/반출 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acquireSlipRegistVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 21.
     */
    @RequestMapping(value = "/acquireSlipRegist/getSearchInOutStockList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchInOutStockList(HttpServletRequest request, HttpServletResponse response,
                                      Model model, AcquireSlipRegistVO acquireSlipRegistVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = acquireSlipRegistService.getSearchInOutStockList(acquireSlipRegistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, acquireSlipRegistVO);
    }

    /**
     * 매입전표등록 - 입고/반출정보 상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acquireSlipRegistVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 21.
     */
    @RequestMapping(value = "/inOutStockDtl/getInOutStockSlipInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInOutStockSlipInfo(HttpServletRequest request, HttpServletResponse response,
                              Model model, AcquireSlipRegistVO acquireSlipRegistVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = acquireSlipRegistService.getInOutStockSlipInfo(acquireSlipRegistVO, sessionInfoVO);


        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매입전표등록 - 입고/반출정보 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   acquireSlipRegistVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 24.
     */
    @RequestMapping(value = "/inOutStockDtl/saveInOutStockSlipInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveInOutStockSlipInfo(HttpServletRequest request, HttpServletResponse response,
                                      Model model, AcquireSlipRegistVO acquireSlipRegistVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = acquireSlipRegistService.saveInOutStockSlipInfo(acquireSlipRegistVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매입전표등록 - 입고/반출정보 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   acquireSlipRegistVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 25.
     */
    @RequestMapping(value = "/inOutStockDtl/deleteInOutStockSlipInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteInOutStockSlipInfo(HttpServletRequest request, HttpServletResponse response,
                                        Model model, AcquireSlipRegistVO acquireSlipRegistVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acquireSlipRegistService.deleteInOutStockSlipInfo(acquireSlipRegistVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매입전표등록 - 입고/반출정보 진행상태 변경
     * @param   request
     * @param   response
     * @param   model
     * @param   acquireSlipRegistVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 25.
     */
    @RequestMapping(value = "/inOutStockDtl/saveInOutStockProcFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveInOutStockProcFg(HttpServletRequest request, HttpServletResponse response,
                             Model model, AcquireSlipRegistVO acquireSlipRegistVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acquireSlipRegistService.saveInOutStockProcFg(acquireSlipRegistVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매입전표등록 - 진행구분 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acquireSlipRegistVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 25.
     */
    @RequestMapping(value = "/inOutStockProd/getChkProcFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getChkProcFg(HttpServletRequest request, HttpServletResponse response,
                                 Model model, AcquireSlipRegistVO acquireSlipRegistVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = acquireSlipRegistService.getChkProcFg(acquireSlipRegistVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매입전표등록 - 입고/반출상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acquireSlipRegistVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 25.
     */
    @RequestMapping(value = "/inOutStockProd/getSearchInOutStockProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchInOutStockProdList(HttpServletRequest request, HttpServletResponse response,
                                             Model model, AcquireSlipRegistVO acquireSlipRegistVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = acquireSlipRegistService.getSearchInOutStockProdList(acquireSlipRegistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, acquireSlipRegistVO);
    }

    /**
     * 매입전표등록 - 입고/반출상품 등록 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   acquireSlipRegistVOs
     * @return  String
     * @author  김유승
     * @since   2025. 11. 25.
     */
    @RequestMapping(value = "/inOutStockProd/saveInOutStockProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveInOutStockProd(HttpServletRequest request, HttpServletResponse response,
                                          Model model, @RequestBody AcquireSlipRegistVO[] acquireSlipRegistVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acquireSlipRegistService.saveInOutStockProd(acquireSlipRegistVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매입전표등록 - 입고/반출상품 등록 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acquireSlipRegistVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 26.
     */
    @RequestMapping(value = "/inOutStockProdReg/getSearchInOutStockRegList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchInOutStockRegList(HttpServletRequest request, HttpServletResponse response,
                                             Model model, AcquireSlipRegistVO acquireSlipRegistVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = acquireSlipRegistService.getSearchInOutStockRegList(acquireSlipRegistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, acquireSlipRegistVO);
    }

    /**
     * 매장 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   selectStoreVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 26.
     */
    @RequestMapping(value = "/acquireSelectStore/getAcquireSelectStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcquireSelectStoreList(HttpServletRequest request, HttpServletResponse response,
                                     Model model, SelectStoreVO selectStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = acquireSlipRegistService.getAcquireSelectStoreList(selectStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, selectStoreVO);
    }

    /**
     * 거래처 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 27.
     */
    @RequestMapping(value = "/acquireSelectVendr/getAcquireSelectVendrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcquireSelectVendrList(HttpServletRequest request, HttpServletResponse response,
                                            Model model, IostockCmmVO iostockCmmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = acquireSlipRegistService.getAcquireSelectVendrList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }

    /**
     * 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadMPSVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 27.
     */
    @RequestMapping(value = "/excelUploadInOutStockProd/deleteExcelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteExcelUpload(HttpServletRequest request, HttpServletResponse response,
                                    Model model, ExcelUploadMPSVO excelUploadMPSVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acquireSlipRegistService.deleteExcelUpload(excelUploadMPSVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 엑셀업로드 - 엑셀업로드 TEMP 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadMPSVOs
     * @return  String
     * @author  김유승
     * @since   2025. 11. 27.
     */
    @RequestMapping(value = "/excelUploadInOutStockProd/saveExcelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveExcelUpload(HttpServletRequest request, HttpServletResponse response,
                                  Model model, @RequestBody ExcelUploadMPSVO[] excelUploadMPSVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acquireSlipRegistService.saveExcelUpload(excelUploadMPSVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadMPSVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 27.
     */
    @RequestMapping(value = "/excelUploadInOutStockProd/saveUpdateProdCd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUpdateProdCd(HttpServletRequest request, HttpServletResponse response,
                                   Model model, ExcelUploadMPSVO excelUploadMPSVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acquireSlipRegistService.saveUpdateProdCd(excelUploadMPSVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매입전표등록 - 엑셀업로드
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadMPSVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 27.
     */
    @RequestMapping(value = "/inOutStockProdReg/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
                              Model model, ExcelUploadMPSVO excelUploadMPSVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acquireSlipRegistService.excelUpload(excelUploadMPSVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매입전표등록 - 반출서 반출정보 조회(반출처, 공급자 정보)
     * @param   request
     * @param   response
     * @param   model
     * @param   acquireSlipRegistVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/inOutStockReport/getInOutStockInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInOutStockInfo(HttpServletRequest request, HttpServletResponse response,
                                            Model model, AcquireSlipRegistVO acquireSlipRegistVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = acquireSlipRegistService.getInOutStockInfo(acquireSlipRegistVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매입전표등록 - 입고/반출상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acquireSlipRegistVO
     * @return  String
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/inOutStockReport/getInOutStockProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInOutStockProdList(HttpServletRequest request, HttpServletResponse response,
                                          Model model, AcquireSlipRegistVO acquireSlipRegistVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = acquireSlipRegistService.getInOutStockProdList(acquireSlipRegistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, acquireSlipRegistVO);
    }
}
