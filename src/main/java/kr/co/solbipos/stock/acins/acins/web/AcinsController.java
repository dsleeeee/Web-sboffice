package kr.co.solbipos.stock.acins.acins.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.cmm.service.IostockCmmService;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service.ExcelUploadStoreVO;
import kr.co.solbipos.stock.acins.acins.service.AcinsService;
import kr.co.solbipos.stock.acins.acins.service.AcinsVO;
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
 * @Class Name : AcinsController.java
 * @Description : 재고관리 > 실사/조정/폐기 > 실사관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.02  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 11.02
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/acins/acins")
public class AcinsController {
    private final SessionService sessionService;
    private final AcinsService acinsService;
    private final IostockCmmService iostockCmmService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public AcinsController(SessionService sessionService, AcinsService acinsService, IostockCmmService iostockCmmService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.acinsService = acinsService;
        this.iostockCmmService = iostockCmmService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 실사관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 11. 02.
     */
    @RequestMapping(value = "/acins/view.sb", method = RequestMethod.GET)
    public String acinsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> listReason = new ArrayList<DefaultMap<String>>();
        // CARD사 목록 조회
        listReason = acinsService.getAcinsReason(sessionInfoVO);
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

        return "stock/acins/acins/acins";
    }


    /**
     * 실사관리 - 실사관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 02.
     */
    @RequestMapping(value = "/acins/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcinsList(HttpServletRequest request, HttpServletResponse response,
        Model model, AcinsVO acinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = acinsService.getAcinsList(acinsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, acinsVO);
    }



    /**
     * 실사관리 - 실사 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/acins/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteAcins(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody AcinsVO[] acinsVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acinsService.deleteAcins(acinsVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 실사 진행구분 및 제목 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/acinsRegist/procFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, AcinsVO acinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = acinsService.getProcFgCheck(acinsVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 실사등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/acinsRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcinsRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, AcinsVO acinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = acinsService.getAcinsRegistList(acinsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, acinsVO);
    }


    /**
     * 실사관리 - 실사상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/acinsRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAcinsRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody AcinsVO[] acinsVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acinsService.saveAcinsRegist(acinsVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 실사등록시 상품정보 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 06.
     */
    @RequestMapping(value = "/acinsRegist/getProdInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, AcinsVO acinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = acinsService.getProdInfo(acinsVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 실사 상세 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/acinsDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcinsDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, AcinsVO acinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = acinsService.getAcinsDtlList(acinsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, acinsVO);
    }


    /**
     * 실사관리 - 실사 상세 상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/acinsDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAcinsDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody AcinsVO[] acinsVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acinsService.saveAcinsDtl(acinsVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 엑셀업로드
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 14.
     */
    @RequestMapping(value = "/acinsRegist/excelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUpload(HttpServletRequest request, HttpServletResponse response,
        Model model, ExcelUploadStoreVO excelUploadStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acinsService.excelUpload(excelUploadStoreVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
    
    /**
     * 다이나믹 콤보조회 - 출고창고 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  M2M
     * @since   2020. 08. 27.
     */
    @RequestMapping(value = "/acins/getOutStorageCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOutStorageCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
        iostockCmmVO.setSelectTable("TB_HQ_STORAGE");
        iostockCmmVO.setSelectCd("STORAGE_CD");
        iostockCmmVO.setSelectNm("STORAGE_NM");
        iostockCmmVO.setSelectWhere("HQ_OFFICE_CD='"+sessionInfoVO.getHqOfficeCd()+"'");
//        iostockCmmVO.setSelectWhere("HQ_OFFICE_CD='"+iostockCmmVO.getHqOfficeCd()+"'");
            list = iostockCmmService.selectDynamicCodeList(iostockCmmVO, sessionInfoVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            iostockCmmVO.setSelectTable("TB_MS_STORAGE");
            iostockCmmVO.setSelectCd("STORAGE_CD");
            iostockCmmVO.setSelectNm("STORAGE_NM");
            iostockCmmVO.setSelectWhere("STORE_CD='"+sessionInfoVO.getStoreCd()+"'");
            list = iostockCmmService.selectDynamicCodeList(iostockCmmVO, sessionInfoVO);
        }


        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }    
}
