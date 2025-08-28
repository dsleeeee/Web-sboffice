package kr.co.solbipos.base.pay.mCoupnProdMapping.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.pay.mCoupnProdMapping.service.MCoupnProdMappingService;
import kr.co.solbipos.base.pay.mCoupnProdMapping.service.MCoupnProdMappingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : MCoupnProdMappingController.java
 * @Description : 기초관리 > 결제수단 > 모바일쿠폰상품매핑
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.08.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/pay/mCoupnProdMapping")
public class MCoupnProdMappingController {

    private final SessionService sessionService;
    private final MCoupnProdMappingService mCoupnProdMappingService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MCoupnProdMappingController(SessionService sessionService, MCoupnProdMappingService mCoupnProdMappingService) {
        this.sessionService = sessionService;
        this.mCoupnProdMappingService = mCoupnProdMappingService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mCoupnProdMapping/list.sb", method = RequestMethod.GET)
    public String mCoupnProdMappingView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/pay/mCoupnProdMapping/mCoupnProdMapping";
    }

    /**
     * 모바일쿠폰상품매핑 - 조회
     *
     * @param mCoupnProdMappingVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2025. 08. 19.
     */
    @RequestMapping(value = "/mCoupnProdMapping/getMCoupnProdMappingList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnProdMappingList(MCoupnProdMappingVO mCoupnProdMappingVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mCoupnProdMappingService.getMCoupnProdMappingList(mCoupnProdMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mCoupnProdMappingVO);
    }

    /**
     * 모바일쿠폰상품매핑 - 모바일쿠폰사-상품코드 최대수
     *
     * @param mCoupnProdMappingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author 김설아
     * @since 2025. 08. 19.
     */
    @RequestMapping(value = "/mCoupnProdMapping/getMCoupnProdMappingCnt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnProdMappingCnt(MCoupnProdMappingVO mCoupnProdMappingVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = mCoupnProdMappingService.getMCoupnProdMappingCnt(mCoupnProdMappingVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 모바일쿠폰상품매핑 - 엑셀업로드 양식 조회
     *
     * @param mCoupnProdMappingVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2025. 08. 22.
     */
    @RequestMapping(value = "/mCoupnProdMapping/getMCoupnProdMappingExcelUploadSampleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnProdMappingExcelUploadSampleList(MCoupnProdMappingVO mCoupnProdMappingVO, HttpServletRequest request,
                                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mCoupnProdMappingService.getMCoupnProdMappingExcelUploadSampleList(mCoupnProdMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mCoupnProdMappingVO);
    }

    /**
     * 모바일쿠폰상품매핑 엑셀업로드 팝업 - 검증결과 전체 삭제
     *
     * @param mCoupnProdMappingVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2025. 08. 25.
     */
    @RequestMapping(value = "/mCoupnProdMappingExcelUpload/getMCoupnProdMappingExcelUploadCheckDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnProdMappingExcelUploadCheckDeleteAll(@RequestBody MCoupnProdMappingVO mCoupnProdMappingVO, HttpServletRequest request,
                                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = mCoupnProdMappingService.getMCoupnProdMappingExcelUploadCheckDeleteAll(mCoupnProdMappingVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 모바일쿠폰상품매핑 엑셀업로드 팝업 - 업로드시 임시테이블 저장
     *
     * @param mCoupnProdMappingVOs
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2025. 08. 25.
     */
    @RequestMapping(value = "/mCoupnProdMappingExcelUpload/getMCoupnProdMappingExcelUploadCheckSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnProdMappingExcelUploadCheckSave(@RequestBody MCoupnProdMappingVO[] mCoupnProdMappingVOs, HttpServletRequest request,
                                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = mCoupnProdMappingService.getMCoupnProdMappingExcelUploadCheckSave(mCoupnProdMappingVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 모바일쿠폰상품매핑 엑셀업로드 결과 팝업 - 조회
     *
     * @param mCoupnProdMappingVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2025. 08. 26.
     */
    @RequestMapping(value = "/mCoupnProdMappingExcelUploadResult/getMCoupnProdMappingExcelUploadResultList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnProdMappingExcelUploadResultList(MCoupnProdMappingVO mCoupnProdMappingVO, HttpServletRequest request,
                                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mCoupnProdMappingService.getMCoupnProdMappingExcelUploadResultList(mCoupnProdMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mCoupnProdMappingVO);
    }

    /**
     * 모바일쿠폰상품매핑 엑셀업로드 결과 팝업 - 저장
     *
     * @param mCoupnProdMappingVOs
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2025. 08. 27.
     */
    @RequestMapping(value = "/mCoupnProdMappingExcelUploadResult/getMCoupnProdMappingExcelUploadResultSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnProdMappingExcelUploadResultSave(@RequestBody MCoupnProdMappingVO[] mCoupnProdMappingVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = mCoupnProdMappingService.getMCoupnProdMappingExcelUploadResultSave(mCoupnProdMappingVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 모바일쿠폰상품매핑 이력조회 팝업 - 조회
     *
     * @param mCoupnProdMappingVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2025. 08. 27.
     */
    @RequestMapping(value = "/mCoupnProdMappingHist/getMCoupnProdMappingHistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnProdMappingHistList(MCoupnProdMappingVO mCoupnProdMappingVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mCoupnProdMappingService.getMCoupnProdMappingHistList(mCoupnProdMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mCoupnProdMappingVO);
    }

    /**
     * 모바일쿠폰상품매핑 이력조회 팝업 - 모바일쿠폰사-상품코드 최대수
     *
     * @param mCoupnProdMappingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author 김설아
     * @since 2025. 08. 27.
     */
    @RequestMapping(value = "/mCoupnProdMappingHist/getMCoupnProdMappingHistCnt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnProdMappingHistCnt(MCoupnProdMappingVO mCoupnProdMappingVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = mCoupnProdMappingService.getMCoupnProdMappingHistCnt(mCoupnProdMappingVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }
}