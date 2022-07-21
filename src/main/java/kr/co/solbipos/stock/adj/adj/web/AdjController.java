package kr.co.solbipos.stock.adj.adj.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service.ExcelUploadStoreVO;
import kr.co.solbipos.stock.adj.adj.service.AdjService;
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
 * @Class Name : AdjController.java
 * @Description : 재고관리 > 실사/조정/폐기 > 조정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.08  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 11.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/adj/adj")
public class AdjController {
    private final SessionService sessionService;
    private final AdjService adjService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public AdjController(SessionService sessionService, AdjService adjService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.adjService = adjService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 조정관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/adj/view.sb", method = RequestMethod.GET)
    public String adjView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> listReason = new ArrayList<DefaultMap<String>>();
        // CARD사 목록 조회
        listReason = adjService.getAdjReason(sessionInfoVO);
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

        return "stock/adj/adj/adj";
    }


    /**
     * 조정관리 - 조정관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   adjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/adj/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAdjList(HttpServletRequest request, HttpServletResponse response,
        Model model, AdjVO adjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = adjService.getAdjList(adjVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, adjVO);
    }



    /**
     * 조정관리 - 조정 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   adjVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/adj/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteAdj(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody AdjVO[] adjVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = adjService.deleteAdj(adjVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 조정관리 - 조정 진행구분 및 제목 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   adjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/adjRegist/procFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, AdjVO adjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = adjService.getProcFgCheck(adjVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 조정관리 - 조정등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   adjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/adjRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAdjRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, AdjVO adjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = adjService.getAdjRegistList(adjVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, adjVO);
    }


    /**
     * 조정관리 - 조정상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   adjVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/adjRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAdjRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody AdjVO[] adjVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = adjService.saveAdjRegist(adjVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 조정관리 - 조정등록시 상품정보 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   adjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 06.
     */
    @RequestMapping(value = "/adjRegist/getProdInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, AdjVO adjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = adjService.getProdInfo(adjVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 조정관리 - 조정 상세 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   adjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/adjDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAdjDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, AdjVO adjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = adjService.getAdjDtlList(adjVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, adjVO);
    }


    /**
     * 조정관리 - 조정 상세 상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   adjVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/adjDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAdjDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody AdjVO[] adjVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = adjService.saveAdjDtl(adjVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 조정관리 - 엑셀업로드
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 17.
     */
    @RequestMapping(value = "/adjRegist/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
        Model model, ExcelUploadStoreVO excelUploadStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = adjService.excelUpload(excelUploadStoreVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
