package kr.co.solbipos.kookmin.stock.stockDisuse.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsVO;
import kr.co.solbipos.kookmin.stock.stockDisuse.service.StockDisuseService;
import kr.co.solbipos.kookmin.stock.stockDisuse.service.StockDisuseVO;
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
 * @Class Name  : StockDisuseController.java
 * @Description : 국민대 > 재고관리 > 재고폐기
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.17  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.17
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/kookmin/stock/stockDisuse")
public class StockDisuseController {

    private final SessionService sessionService;
    private final StockDisuseService stockDisuseService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     *  Constructor Injection
     */
    @Autowired
    public StockDisuseController(SessionService sessionService, StockDisuseService stockDisuseService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.stockDisuseService = stockDisuseService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 재고폐기 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     */
    @RequestMapping(value = "/stockDisuse/view.sb", method = RequestMethod.GET)
    public String disuseView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> listReason = new ArrayList<DefaultMap<String>>();
        // CARD사 목록 조회
        listReason = stockDisuseService.getDisuseReason(sessionInfoVO);
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

        return "kookmin/stock/stockDisuse/stockDisuse";
    }

    /**
     * 재고폐기 - 재고폐기 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockDisuseVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 17.
     */
    @RequestMapping(value = "/stockDisuse/getSearchStockDisuseList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchStockDisuseList(HttpServletRequest request, HttpServletResponse response,
                                Model model, StockDisuseVO stockDisuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = stockDisuseService.getSearchStockDisuseList(stockDisuseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, stockDisuseVO);
    }


    /**
     * 재고폐기 - 폐기 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   stockDisuseVOS
     * @return  String
     * @author  김유승
     * @since   2025. 12. 17.
     */
    @RequestMapping(value = "/stockDisuse/deleteDisuse.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteDisuse(HttpServletRequest request, HttpServletResponse response,
                               Model model, @RequestBody StockDisuseVO[] stockDisuseVOS) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockDisuseService.deleteDisuse(stockDisuseVOS, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고폐기 - 폐기 진행구분 및 제목 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockDisuseVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 17.
     */
    @RequestMapping(value = "/stockDisuseRegist/getProcFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCheck(HttpServletRequest request, HttpServletResponse response,
                                 Model model, StockDisuseVO stockDisuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = stockDisuseService.getProcFgCheck(stockDisuseVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고폐기 - 폐기등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockDisuseVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 17.
     */
    @RequestMapping(value = "/stockDisuseRegist/getSearchDisuseRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchDisuseRegistList(HttpServletRequest request, HttpServletResponse response,
                                      Model model, StockDisuseVO stockDisuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = stockDisuseService.getSearchDisuseRegistList(stockDisuseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, stockDisuseVO);
    }

    /**
     * 재고폐기 - 폐기상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   stockDisuseVOS
     * @return  String
     * @author  김유승
     * @since   2025. 12. 17.
     */
    @RequestMapping(value = "/stockDisuseRegist/saveDisuse.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDisuse(HttpServletRequest request, HttpServletResponse response,
                                   Model model, @RequestBody StockDisuseVO[] stockDisuseVOS) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockDisuseService.saveDisuse(stockDisuseVOS, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고폐기 - 폐기 상세 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockDisuseVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 17.
     */
    @RequestMapping(value = "/stockDisuseDtl/getSearchDisuseDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchDisuseDtlList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, StockDisuseVO stockDisuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = stockDisuseService.getSearchDisuseDtlList(stockDisuseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, stockDisuseVO);
    }


    /**
     * 재고폐기 - 폐기 상세 상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   stockDisuseVOS
     * @return  String
     * @author  김유승
     * @since   2025. 12. 17.
     */
    @RequestMapping(value = "/stockDisuseDtl/saveDisuseDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDisuseDtl(HttpServletRequest request, HttpServletResponse response,
                                Model model, @RequestBody StockDisuseVO[] stockDisuseVOS) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockDisuseService.saveDisuseDtl(stockDisuseVOS, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 재고폐기 - 엑셀업로드
     * @param   request
     * @param   response
     * @param   model
     * @param   stockAcinsVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 18.
     */
    @RequestMapping(value = "/stockDisuseRegist/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
                              Model model, StockAcinsVO stockAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = stockDisuseService.excelUpload(stockAcinsVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
