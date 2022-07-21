package kr.co.solbipos.stock.disuse.disuse.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.stock.disuse.disuse.service.DisuseService;
import kr.co.solbipos.stock.disuse.disuse.service.DisuseVO;
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
 * @Class Name : DisuseController.java
 * @Description : 재고관리 > 실사/조정/폐기 > 폐기관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.12  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 11.12
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/disuse/disuse")
public class DisuseController {
    private final SessionService sessionService;
    private final DisuseService disuseService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public DisuseController(SessionService sessionService, DisuseService disuseService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.disuseService = disuseService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 폐기관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/disuse/view.sb", method = RequestMethod.GET)
    public String disuseView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // [1241 창고사용여부] 환경설정값 조회
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("storageEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1241"), "0"));
            System.out.println("storageEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1241"), "0"));
        } else {
            model.addAttribute("storageEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1241"), "0"));
            System.out.println("storageEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1241"), "0"));
        }

        return "stock/disuse/disuse/disuse";
    }


    /**
     * 폐기관리 - 폐기관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   disuseVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/disuse/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDisuseList(HttpServletRequest request, HttpServletResponse response,
        Model model, DisuseVO disuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = disuseService.getDisuseList(disuseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, disuseVO);
    }


    /**
     * 폐기관리 - 폐기 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   disuseVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/disuse/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteDisuse(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DisuseVO[] disuseVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = disuseService.deleteDisuse(disuseVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 폐기관리 - 폐기 진행구분 및 제목 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   disuseVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/disuseRegist/procFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, DisuseVO disuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = disuseService.getProcFgCheck(disuseVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 폐기관리 - 폐기등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   disuseVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/disuseRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDisuseRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, DisuseVO disuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = disuseService.getDisuseRegistList(disuseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, disuseVO);
    }


    /**
     * 폐기관리 - 폐기상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   disuseVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/disuseRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDisuseRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DisuseVO[] disuseVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = disuseService.saveDisuseRegist(disuseVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 폐기관리 - 폐기등록시 상품정보 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   disuseVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 06.
     */
    @RequestMapping(value = "/disuseRegist/getProdInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, DisuseVO disuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = disuseService.getProdInfo(disuseVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 폐기관리 - 폐기 상세 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   disuseVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/disuseDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDisuseDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, DisuseVO disuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = disuseService.getDisuseDtlList(disuseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, disuseVO);
    }


    /**
     * 폐기관리 - 폐기 상세 상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   disuseVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/disuseDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDisuseDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DisuseVO[] disuseVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = disuseService.saveDisuseDtl(disuseVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 폐기관리 - 엑셀업로드
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadMPSVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 17.
     */
    @RequestMapping(value = "/disuseRegist/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
        Model model, ExcelUploadMPSVO excelUploadMPSVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = disuseService.excelUpload(excelUploadMPSVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
