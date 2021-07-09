package kr.co.solbipos.base.prod.prodBarcd.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodBarcd.service.ProdBarcdService;
import kr.co.solbipos.base.prod.prodBarcd.service.ProdBarcdVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : BarcdController.java
 * @Description : 기초관리 - 상품관리 - 상품바코드등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.01  권지현       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/prodBarcd")
public class ProdBarcdController {

    private final SessionService sessionService;
    private final ProdBarcdService prodBarcdService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    MessageService messageService;

    public ProdBarcdController(SessionService sessionService, ProdBarcdService prodBarcdService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.prodBarcdService = prodBarcdService;
        this.cmmEnvUtil = cmmEnvUtil;
    }


    /**
     * 상품조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        return "base/prod/prodBarcd/barcd";
    }

    /**
     * 상품조회
     *
     * @param prodBarcdVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(ProdBarcdVO prodBarcdVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodBarcdService.getProdList(prodBarcdVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodBarcdVO);
    }

    /** 상품전체조회(엑셀다운로드용) */
    @RequestMapping(value = "/getProdExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdExcelList(ProdBarcdVO prodBarcdVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodBarcdService.getProdExcelList(prodBarcdVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodBarcdVO);
    }

    /** 상품상세조회 */
    @RequestMapping(value = "/detail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdDetail(ProdBarcdVO prodBarcdVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return returnJson(Status.OK, "list", prodBarcdService.getProdDetail(prodBarcdVO, sessionInfoVO));
    }

    /** 바코드 중복 체크 */
    @RequestMapping(value = "/chkBarCd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkBarCd(ProdBarcdVO prodBarcdVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = prodBarcdService.chkBarCd(prodBarcdVO, sessionInfoVO);
        if(result.size() < 1){
            return returnJson(Status.OK);
        } else {
            return returnJson(Status.FAIL, result);
        }
    }

    /** 바코드 중복 체크 */
    @RequestMapping(value = "/chkBarCds.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkBarCds(@RequestBody ProdBarcdVO[] prodBarcdVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = prodBarcdService.chkBarCds(prodBarcdVOs, sessionInfoVO);
        if(result.size() < 1){
            return returnJson(Status.OK);
        } else {
            return returnJson(Status.FAIL, result);
        }
    }

    /** 저장 */
    @RequestMapping(value = "/saveBarcd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveBarcd(@RequestBody ProdBarcdVO[] prodBarcdVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodBarcdService.saveBarcd(prodBarcdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /** 검증결과 제거 */
    @RequestMapping(value = "/getExcelUploadCheckDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExcelUploadCheckDeleteAll(@RequestBody ProdBarcdVO prodBarcdVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodBarcdService.getExcelUploadCheckDeleteAll(prodBarcdVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /** 검증결과 저장 */
    @RequestMapping(value = "/getExcelUploadCheckSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExcelUploadCheckSave(@RequestBody ProdBarcdVO[] prodBarcdVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodBarcdService.getExcelUploadCheckSave(prodBarcdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /** 임시테이블 정보 조회 */
    @RequestMapping(value = "/getExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExcelList(ProdBarcdVO prodBarcdVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodBarcdService.getExcelList(prodBarcdVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodBarcdVO);
    }

    /** 엑셀 저장 */
    @RequestMapping(value = "/saveBarcdExcel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveBarcdExcel(@RequestBody ProdBarcdVO[] prodBarcdVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodBarcdService.saveBarcdExcel(prodBarcdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
