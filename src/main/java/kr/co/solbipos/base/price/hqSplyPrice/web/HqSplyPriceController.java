package kr.co.solbipos.base.price.hqSplyPrice.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.hqSplyPrice.service.HqSplyPriceService;
import kr.co.solbipos.base.price.hqSplyPrice.service.HqSplyPriceVO;
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
 * @Class Name : HqSplyPriceController.java
 * @Description : 기초관리 - 가격관리 - 본사공급가관리
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
@RequestMapping(value = "/base/price/hqSplyPrice")
public class HqSplyPriceController {

    private final SessionService sessionService;
    private final HqSplyPriceService hqSplyPriceService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public HqSplyPriceController(SessionService sessionService, HqSplyPriceService hqSplyPriceService, CmmEnvUtil cmmEnvUtil){
        this.sessionService = sessionService;
        this.hqSplyPriceService = hqSplyPriceService;
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

        return "base/price/hqSplyPrice/hqSplyPriceTab";
    }

    /**
     * 본사 공급가관리 조회
     *
     * @param hqSplyPriceVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHqSplyPriceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSplyPriceList(HqSplyPriceVO hqSplyPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = hqSplyPriceService.getHqSplyPriceList(hqSplyPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, hqSplyPriceVO);
    }

    /**
     * 본사 공급가관리 엑셀다운로드 조회
     *
     * @param hqSplyPriceVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHqSplyPriceExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSplyPriceExcelList(HqSplyPriceVO hqSplyPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = hqSplyPriceService.getHqSplyPriceExcelList(hqSplyPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, hqSplyPriceVO);
    }

    /**
     * 본사 공급가 저장
     * @param hqSplyPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveHqSplyPrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqSplyPrice(@RequestBody HqSplyPriceVO[] hqSplyPriceVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqSplyPriceService.saveHqSplyPrice(hqSplyPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 본사 공급가 엑셀 양식다운로드 조회
     *
     * @param hqSplyPriceVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHqSplyPriceExcelUploadSampleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSplyPriceExcelUploadSampleList(HqSplyPriceVO hqSplyPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = hqSplyPriceService.getHqSplyPriceExcelUploadSampleList(hqSplyPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, hqSplyPriceVO);
    }

    /**
     * 공급가 업로드 임시테이블 전체 삭제
     * @param hqSplyPriceVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteSplyPriceExcelUploadCheckAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteSplyPriceExcelUploadCheckAll(@RequestBody HqSplyPriceVO hqSplyPriceVO, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqSplyPriceService.deleteSplyPriceExcelUploadCheckAll(hqSplyPriceVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 공급가 업로드 임시테이블 삭제
     * @param hqSplyPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteSplyPriceExcelUploadCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteSplyPriceExcelUploadCheck(@RequestBody HqSplyPriceVO[] hqSplyPriceVOs, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqSplyPriceService.deleteSplyPriceExcelUploadCheck(hqSplyPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 공급가 업로드 임시테이블 저장
     * @param hqSplyPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveSplyPriceExcelUploadCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSplyPriceExcelUploadCheck(@RequestBody HqSplyPriceVO[] hqSplyPriceVOs, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqSplyPriceService.saveSplyPriceExcelUploadCheck(hqSplyPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 공급가 업로드 임시테이블 데이터 조회
     * @param hqSplyPriceVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getSplyPriceExcelUploadCheckList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSplyPriceExcelUploadCheckList(HqSplyPriceVO hqSplyPriceVO, HttpServletRequest request,
                                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = hqSplyPriceService.getSplyPriceExcelUploadCheckList(hqSplyPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, hqSplyPriceVO);
    }

    /**
     * 공급가 업로드 검증결과 저장
     * @param hqSplyPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveSplyPriceExcelUploadCheckResult.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSplyPriceExcelUploadCheckResult(@RequestBody HqSplyPriceVO[] hqSplyPriceVOs, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqSplyPriceService.saveSplyPriceExcelUploadCheckResult(hqSplyPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 본사 공급가 엑셀업로드 저장
     * @param hqSplyPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveHqSplyPriceExcelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqSplyPriceExcelUpload(@RequestBody HqSplyPriceVO[] hqSplyPriceVOs, HttpServletRequest request,
                                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqSplyPriceService.saveHqSplyPriceExcelUpload(hqSplyPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
