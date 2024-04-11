package kr.co.solbipos.base.price.splyPrice.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.splyPrice.service.SplyPriceService;
import kr.co.solbipos.base.price.splyPrice.service.SplyPriceVO;
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
 * @Class Name : SplyPriceController.java
 * @Description : 기초관리 - 가격관리 - 공급가관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.04  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/splyPrice")
public class SplyPriceController {

    private final SessionService sessionService;
    private final SplyPriceService splyPriceService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public SplyPriceController(SessionService sessionService, SplyPriceService splyPriceService, CmmEnvUtil cmmEnvUtil){
        this.sessionService = sessionService;
        this.splyPriceService = splyPriceService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "base/price/splyPrice/splyPriceTab";
    }

    /**
     * 본사 공급가관리 조회
     *
     * @param splyPriceVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHqSplyPriceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSplyPriceList(SplyPriceVO splyPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = splyPriceService.getHqSplyPriceList(splyPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, splyPriceVO);
    }

    /**
     * 본사 공급가관리 엑셀다운로드 조회
     *
     * @param splyPriceVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHqSplyPriceExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSplyPriceExcelList(SplyPriceVO splyPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = splyPriceService.getHqSplyPriceExcelList(splyPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, splyPriceVO);
    }

    /**
     * 본사 공급가 저장
     * @param splyPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveHqSplyPrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqSplyPrice(@RequestBody SplyPriceVO[] splyPriceVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = splyPriceService.saveHqSplyPrice(splyPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 본사 공급가 엑셀 양식다운로드 조회
     *
     * @param splyPriceVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHqSplyPriceExcelUploadSampleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSplyPriceExcelUploadSampleList(SplyPriceVO splyPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = splyPriceService.getHqSplyPriceExcelUploadSampleList(splyPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, splyPriceVO);
    }

    /**
     * 공급가 업로드 임시테이블 전체 삭제
     * @param splyPriceVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteSplyPriceExcelUploadCheckAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteSplyPriceExcelUploadCheckAll(@RequestBody SplyPriceVO splyPriceVO, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = splyPriceService.deleteSplyPriceExcelUploadCheckAll(splyPriceVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 공급가 업로드 임시테이블 삭제
     * @param splyPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteSplyPriceExcelUploadCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteSplyPriceExcelUploadCheck(@RequestBody SplyPriceVO[] splyPriceVOs, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = splyPriceService.deleteSplyPriceExcelUploadCheck(splyPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 공급가 업로드 임시테이블 저장
     * @param splyPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveSplyPriceExcelUploadCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSplyPriceExcelUploadCheck(@RequestBody SplyPriceVO[] splyPriceVOs, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = splyPriceService.saveSplyPriceExcelUploadCheck(splyPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 공급가 업로드 임시테이블 데이터 조회
     * @param splyPriceVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getSplyPriceExcelUploadCheckList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSplyPriceExcelUploadCheckList(SplyPriceVO splyPriceVO, HttpServletRequest request,
                                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = splyPriceService.getSplyPriceExcelUploadCheckList(splyPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, splyPriceVO);
    }

    /**
     * 공급가 검증결과 저장
     * @param splyPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveSplyPriceExcelUploadCheckResult.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSplyPriceExcelUploadCheckResult(@RequestBody SplyPriceVO[] splyPriceVOs, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = splyPriceService.saveSplyPriceExcelUploadCheckResult(splyPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 공급가 엑셀업로드 저장
     * @param splyPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveHqSplyPriceExcelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqSplyPriceExcelUpload(@RequestBody SplyPriceVO[] splyPriceVOs, HttpServletRequest request,
                                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = splyPriceService.saveHqSplyPriceExcelUpload(splyPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
