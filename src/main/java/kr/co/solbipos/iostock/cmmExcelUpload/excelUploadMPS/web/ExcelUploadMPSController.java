package kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSService;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
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
 * @Class Name : ExcelUploadMPSController.java
 * @Description : 공통팝업 수불/재고 엑셀업로드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.10  김설아      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2020. 09.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/cmmExcelUpload/excelUploadMPS")
public class ExcelUploadMPSController {

    private final SessionService sessionService;
    private final ExcelUploadMPSService excelUploadMPSService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ExcelUploadMPSController(SessionService sessionService, ExcelUploadMPSService excelUploadMPSService) {
        this.sessionService = sessionService;
        this.excelUploadMPSService = excelUploadMPSService;
    }

    /**
     * 엑셀업로드 - 엑셀업로드 TEMP 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadMPSVOs
     * @return  String
     * @author  김설아
     * @since   2020. 09. 10.
     */
    @RequestMapping(value = "/excelUploadMPS/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveExcelUpload(HttpServletRequest request, HttpServletResponse response,
                                  Model model, @RequestBody ExcelUploadMPSVO[] excelUploadMPSVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = excelUploadMPSService.saveExcelUpload(excelUploadMPSVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadMPSVO
     * @return  String
     * @author  김설아
     * @since   2020. 09. 10.
     */
    @RequestMapping(value = "/excelUploadMPS/saveUpdateProdCd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUpdateProdCd(HttpServletRequest request, HttpServletResponse response,
                                   Model model, ExcelUploadMPSVO excelUploadMPSVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = excelUploadMPSService.saveUpdateProdCd(excelUploadMPSVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadMPSVO
     * @return  String
     * @author  김설아
     * @since   2020. 09. 10.
     */
    @RequestMapping(value = "/excelUploadMPS/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteExcelUpload(HttpServletRequest request, HttpServletResponse response,
                                    Model model, ExcelUploadMPSVO excelUploadMPSVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = excelUploadMPSService.deleteExcelUpload(excelUploadMPSVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 엑셀업로드 - 엑셀업로드 실패내역 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadMPSVO
     * @return  String
     * @author  김설아
     * @since   2020. 09. 10.
     */
    @RequestMapping(value = "/excelUploadMPSErrInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExcelUploadErrInfoList(HttpServletRequest request, HttpServletResponse response,
                                            Model model, ExcelUploadMPSVO excelUploadMPSVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = excelUploadMPSService.getExcelUploadErrInfoList(excelUploadMPSVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, excelUploadMPSVO);
    }
}