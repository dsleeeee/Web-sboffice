package kr.co.solbipos.kookmin.stock.stockAcins.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsService;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsVO;
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
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;
/**
 * @Class Name  : StockAcinsController.java
 * @Description : 국민대 > 재고관리 > 재고실사
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/kookmin/stock/stockAcins")
public class StockAcinsController {

    private final SessionService sessionService;
    private final StockAcinsService stockAcinsService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public StockAcinsController(SessionService sessionService, StockAcinsService stockAcinsService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.stockAcinsService = stockAcinsService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 재고실사 - 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/stockAcins/view.sb", method = RequestMethod.GET)
    public String stockAcins(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> listReason = new ArrayList<DefaultMap<String>>();
        // 실사사유 목록 조회
        listReason = stockAcinsService.getSearchAcinsReason(sessionInfoVO);
        // 콤보박스용 데이터 생성
        List<HashMap<String, String>> combo = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> m = new HashMap<>();
        for ( HashMap<String, String> imspList : listReason ) {
            m = new HashMap<>();
            m.put("name", imspList.get("nmcodeNm"));
            m.put("value", imspList.get("nmcodeCd"));
            combo.add(m);
        }
        // 사유
        model.addAttribute("reasonData", convertToJson(combo));

        // [1241 창고사용여부] 환경설정값 조회 (사용여부 확인)
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("storageEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1241"), "0"));
            System.out.println("storageEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1241"), "0"));
        } else {
            model.addAttribute("storageEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1241"), "0"));
            System.out.println("storageEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1241"), "0"));
        }

        return "kookmin/stock/stockAcins/stockAcins";
    }

    /**
     * 재고실사 - 실사관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 10.
     */
    @RequestMapping(value = "/stockAcins/getSearchAcinsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchAcinsList(HttpServletRequest request, HttpServletResponse response,
                               Model model, StockAcinsVO stockAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = stockAcinsService.getSearchAcinsList(stockAcinsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, stockAcinsVO);
    }

    /**
     * 재고실사 - 실사 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVOS
     * @return  String
     * @author  김유승
     * @since   2025. 12. 10.
     */
    @RequestMapping(value = "/stockAcins/deleteAcins.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteAcins(HttpServletRequest request, HttpServletResponse response,
                              Model model, @RequestBody StockAcinsVO[] stockAcinsVOS) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockAcinsService.deleteAcins(stockAcinsVOS, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고실사 - 실사등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 10.
     */
    @RequestMapping(value = "/stockAcinsRegist/getSearchAcinsRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchAcinsRegistList(HttpServletRequest request, HttpServletResponse response,
                                     Model model, StockAcinsVO stockAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = stockAcinsService.getSearchAcinsRegistList(stockAcinsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, stockAcinsVO);
    }

    /**
     * 재고실사 - 실사상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVOS
     * @return  String
     * @author  김유승
     * @since   2025. 12. 11.
     */
    @RequestMapping(value = "/stockAcinsRegist/saveAcins.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAcins(HttpServletRequest request, HttpServletResponse response,
                                  Model model, @RequestBody StockAcinsVO[] stockAcinsVOS) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockAcinsService.saveAcins(stockAcinsVOS, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고실사 - 실사 진행구분 및 제목 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 11.
     */
    @RequestMapping(value = "/stockAcins/getProcFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCheck(HttpServletRequest request, HttpServletResponse response,
                                 Model model, StockAcinsVO stockAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = stockAcinsService.getProcFgCheck(stockAcinsVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고실사 - 실사등록시 상품정보 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 11.
     */
    @RequestMapping(value = "/stockAcins/getAcinsInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcinsInfo(HttpServletRequest request, HttpServletResponse response,
                              Model model, StockAcinsVO stockAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = stockAcinsService.getAcinsInfo(stockAcinsVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고실사 - 실사 상세 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 11..
     */
    @RequestMapping(value = "/stockAcinsDtl/getSearchAcinsDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchAcinsDtlList(HttpServletRequest request, HttpServletResponse response,
                                  Model model, StockAcinsVO stockAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = stockAcinsService.getSearchAcinsDtlList(stockAcinsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, stockAcinsVO);
    }

    /**
     * 재고실사 - 실사 상세 상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVOS
     * @return  String
     * @author  김유승
     * @since   2025. 12. 11.
     */
    @RequestMapping(value = "/stockAcinsDtl/saveAcinsDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAcinsDtl(HttpServletRequest request, HttpServletResponse response,
                               Model model, @RequestBody StockAcinsVO[] stockAcinsVOS) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockAcinsService.saveAcinsDtl(stockAcinsVOS, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 15.
     */
    @RequestMapping(value = "/stockCmm/deleteExcelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteExcelUpload(HttpServletRequest request, HttpServletResponse response,
                                    Model model, StockAcinsVO stockAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockAcinsService.deleteExcelUpload(stockAcinsVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 엑셀업로드 - 엑셀업로드 TEMP 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVOS
     * @return  String
     * @author  김유승
     * @since   2025. 12. 15.
     */
    @RequestMapping(value = "/stockCmm/saveExcelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveExcelUpload(HttpServletRequest request, HttpServletResponse response,
                                  Model model, @RequestBody StockAcinsVO[] stockAcinsVOS) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockAcinsService.saveExcelUpload(stockAcinsVOS, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 15.
     */
    @RequestMapping(value = "/stockCmm/saveUpdateProdCd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUpdateProdCd(HttpServletRequest request, HttpServletResponse response,
                                   Model model, StockAcinsVO stockAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockAcinsService.saveUpdateProdCd(stockAcinsVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 엑셀업로드 - 엑셀업로드 실패내역 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 15.
     */
    @RequestMapping(value = "/stockCmm/getExcelUploadErrInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExcelUploadErrInfoList(HttpServletRequest request, HttpServletResponse response,
                                            Model model, StockAcinsVO stockAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = stockAcinsService.getExcelUploadErrInfoList(stockAcinsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, stockAcinsVO);
    }

    /**
     * 재고실사 - 엑셀업로드
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 15.
     */
    @RequestMapping(value = "/stockAcinsRegist/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
                              Model model, StockAcinsVO stockAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockAcinsService.excelUpload(stockAcinsVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
