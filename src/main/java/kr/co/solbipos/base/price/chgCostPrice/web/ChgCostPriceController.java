package kr.co.solbipos.base.price.chgCostPrice.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.chgCostPrice.service.ChgCostPriceService;
import kr.co.solbipos.base.price.chgCostPrice.service.ChgCostPriceVO;
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
 * @Class Name : ChgCostPriceController.java
 * @Description : 기초관리 - 가격관리 - 원가임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.29  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/chgCostPrice")
public class ChgCostPriceController {

    private final SessionService sessionService;
    private final ChgCostPriceService chgCostPriceService;

    /** Constructor Injection */
    @Autowired
    public ChgCostPriceController(SessionService sessionService, ChgCostPriceService chgCostPriceService){
        this.sessionService = sessionService;
        this.chgCostPriceService = chgCostPriceService;
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

        return "base/price/chgCostPrice/chgCostPriceTab";
    }

    /**
     * 원가임의변경 원가 조회
     * @param chgCostPriceVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.04.30
     */
    @RequestMapping(value = "/getCostPriceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCostPriceList(ChgCostPriceVO chgCostPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = chgCostPriceService.getCostPriceList(chgCostPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, chgCostPriceVO);
    }

    /**
     * 원가임의변경 원가 엑셀다운로드 조회
     * @param chgCostPriceVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.04.30
     */
    @RequestMapping(value = "/getCostPriceExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCostPriceExcelList(ChgCostPriceVO chgCostPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = chgCostPriceService.getCostPriceExcelList(chgCostPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, chgCostPriceVO);
    }

    /**
     * 원가임의변경 원가 변경
     * @param chgCostPriceVOs
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.04.30
     */
    @RequestMapping(value = "/saveCostPrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCostPrice(@RequestBody ChgCostPriceVO[] chgCostPriceVOs, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = chgCostPriceService.saveCostPrice(chgCostPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 원가임의변경 엑셀 양식다운로드 조회
     *
     * @param chgCostPriceVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.04.30
     */
    @RequestMapping(value = "/getCostPriceExcelUploadSampleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCostPriceExcelUploadSampleList(ChgCostPriceVO chgCostPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = chgCostPriceService.getCostPriceExcelUploadSampleList(chgCostPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, chgCostPriceVO);
    }

    /**
     * 원가임의변경 원가 업로드 임시테이블 전체 삭제
     * @param chgCostPriceVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteCostPriceExcelUploadCheckAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCostPriceExcelUploadCheckAll(@RequestBody ChgCostPriceVO chgCostPriceVO, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = chgCostPriceService.deleteCostPriceExcelUploadCheckAll(chgCostPriceVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 원가임의변경 원가 업로드 임시테이블 삭제
     * @param chgCostPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteCostPriceExcelUploadCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCostPriceExcelUploadCheck(@RequestBody ChgCostPriceVO[] chgCostPriceVOs, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = chgCostPriceService.deleteCostPriceExcelUploadCheck(chgCostPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 원가임의변경 원가 업로드 임시테이블 저장
     * @param chgCostPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveCostPriceExcelUploadCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCostPriceExcelUploadCheck(@RequestBody ChgCostPriceVO[] chgCostPriceVOs, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = chgCostPriceService.saveCostPriceExcelUploadCheck(chgCostPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 원가임의변경 원가 업로드 임시테이블 데이터 조회
     * @param chgCostPriceVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getCostPriceExcelUploadCheckList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCostPriceExcelUploadCheckList(ChgCostPriceVO chgCostPriceVO, HttpServletRequest request,
                                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = chgCostPriceService.getCostPriceExcelUploadCheckList(chgCostPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, chgCostPriceVO);
    }

    /**
     * 원가임의변경 원가 업로드 검증결과 저장
     * @param chgCostPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveCostPriceExcelUploadCheckResult.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCostPriceExcelUploadCheckResult(@RequestBody ChgCostPriceVO[] chgCostPriceVOs, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = chgCostPriceService.saveCostPriceExcelUploadCheckResult(chgCostPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 원가임의변경 원가 엑셀업로드 저장
     * @param chgCostPriceVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveCostPriceExcelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCostPriceExcelUpload(@RequestBody ChgCostPriceVO[] chgCostPriceVOs, HttpServletRequest request,
                                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = chgCostPriceService.saveCostPriceExcelUpload(chgCostPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
