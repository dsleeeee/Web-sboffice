package kr.co.solbipos.kookmin.stock.stockAdjust.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service.ExcelUploadStoreVO;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsVO;
import kr.co.solbipos.kookmin.stock.stockAdjust.service.StockAdjustService;
import kr.co.solbipos.kookmin.stock.stockAdjust.service.StockAdjustVO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;
/**
 * @Class Name  : StockAdjustController.java
 * @Description : 국민대 > 재고관리 > 재고조정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.16  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.16
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/kookmin/stock/stockAdjust")
public class StockAdjustController {

    private final SessionService sessionService;
    private final StockAdjustService stockAdjustService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     *  Constructor Injection
     */
    @Autowired
    public StockAdjustController(SessionService sessionService, StockAdjustService stockAdjustService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.stockAdjustService = stockAdjustService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 재고조정 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     */
    @RequestMapping(value = "/stockAdjust/view.sb", method = RequestMethod.GET)
    public String stockAdjust(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> listReason = new ArrayList<DefaultMap<String>>();
        // CARD사 목록 조회
        listReason = stockAdjustService.getAdjustReason(sessionInfoVO);
        // 콤보박스용 데이터 생성
        List<HashMap<String, String>> combo = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> m = new HashMap<>();
        for ( HashMap<String, String> cardCmpnyList : listReason ) {
            m = new HashMap<>();
            m.put("name", cardCmpnyList.get("nmcodeNm"));
            m.put("value", cardCmpnyList.get("nmcodeCd"));
            combo.add(m);
        }
        // 사유
        model.addAttribute("reasonData", convertToJson(combo));

        // [1241 창고사용여부] 환경설정값 조회
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("storageEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1241"), "0"));
            System.out.println("storageEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1241"), "0"));
        } else {
            model.addAttribute("storageEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1241"), "0"));
            System.out.println("storageEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1241"), "0"));
        }

        return "kookmin/stock/stockAdjust/stockAdjust";
    }

    /**
     * 재고조정 - 재고조정 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAdjustVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 16.
     */
    @RequestMapping(value = "/stockAdjust/getSearchAdjustList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchAdjustList(HttpServletRequest request, HttpServletResponse response,
                             Model model, StockAdjustVO stockAdjustVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = stockAdjustService.getSearchAdjustList(stockAdjustVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, stockAdjustVO);
    }

    /**
     * 재고조정 - 조정 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAdjustVOs
     * @return  String
     * @author  김유승
     * @since   2025. 12. 16.
     */
    @RequestMapping(value = "/stockAdjust/deleteAdjust.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteAdjust(HttpServletRequest request, HttpServletResponse response,
                            Model model, @RequestBody StockAdjustVO[] stockAdjustVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockAdjustService.deleteAdjust(stockAdjustVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고조정 - 조정 진행구분 및 제목 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAdjustVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 16.
     */
    @RequestMapping(value = "/stockAdjust/getProcFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCheck(HttpServletRequest request, HttpServletResponse response,
                                 Model model, StockAdjustVO stockAdjustVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = stockAdjustService.getProcFgCheck(stockAdjustVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고조정 - 조정 상세 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAdjustVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 16.
     */
    @RequestMapping(value = "/stockAdjustDtl/getSearchAdjustDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchAdjustDtlList(HttpServletRequest request, HttpServletResponse response,
                                Model model, StockAdjustVO stockAdjustVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = stockAdjustService.getSearchAdjustDtlList(stockAdjustVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, stockAdjustVO);
    }

    /**
     * 재고조정 - 조정 상세 상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAdjustVOs
     * @return  String
     * @author  김유승
     * @since   2025. 12. 16.
     */
    @RequestMapping(value = "/stockAdjustDtl/saveAdjustDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAdjustDtl(HttpServletRequest request, HttpServletResponse response,
                             Model model, @RequestBody StockAdjustVO[] stockAdjustVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockAdjustService.saveAdjustDtl(stockAdjustVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고조정 - 조정등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAdjustVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 16.
     */
    @RequestMapping(value = "/stockAdjustRegist/getSearchAdjustRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchAdjustRegistList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, StockAdjustVO stockAdjustVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = stockAdjustService.getSearchAdjustRegistList(stockAdjustVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, stockAdjustVO);
    }

    /**
     * 재고조정 - 조정상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAdjustVOs
     * @return  String
     * @author  김유승
     * @since   2025. 12. 16.
     */
    @RequestMapping(value = "/stockAdjustRegist/saveAdjustRegist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAdjustRegist(HttpServletRequest request, HttpServletResponse response,
                                Model model, @RequestBody StockAdjustVO[] stockAdjustVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockAdjustService.saveAdjustRegist(stockAdjustVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고조정 - 조정등록시 상품정보 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAdjustVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 16.
     */
    @RequestMapping(value = "/stockAdjust/getAdjustInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAdjustInfo(HttpServletRequest request, HttpServletResponse response,
                              Model model, StockAdjustVO stockAdjustVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = stockAdjustService.getAdjustInfo(stockAdjustVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고조정 - 엑셀업로드
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 7.
     */
    @RequestMapping(value = "/stockAdjustRegist/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
                              Model model, StockAcinsVO stockAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockAdjustService.excelUpload(stockAcinsVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
