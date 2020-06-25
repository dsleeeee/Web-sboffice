package kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadService;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
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
 * @Class Name : ExcelUploadController.java
 * @Description : 수불/재고 공통 엑셀업로드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.10  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 12.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/cmmExcelUpload/excelUpload")
public class ExcelUploadController {
    private final SessionService sessionService;
    private final ExcelUploadService excelUploadService;

    @Autowired
    public ExcelUploadController(SessionService sessionService, ExcelUploadService excelUploadService) {
        this.sessionService = sessionService;
        this.excelUploadService = excelUploadService;
    }


    /**`F
     * 엑셀업로드 - 엑셀업로드 TEMP 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadVOs
     * @return  String
     * @author  안동관
     * @since   2018. 12. 10.
     */
    @RequestMapping(value = "/excelUpload/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveExcelUpload(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody ExcelUploadVO[] excelUploadVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = excelUploadService.saveExcelUpload(excelUploadVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 11.
     */
    @RequestMapping(value = "/excelUpload/saveUpdateProdCd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUpdateProdCd(HttpServletRequest request, HttpServletResponse response,
        Model model, ExcelUploadVO excelUploadVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = excelUploadService.saveUpdateProdCd(excelUploadVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 10.
     */
    @RequestMapping(value = "/excelUpload/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteExcelUpload(HttpServletRequest request, HttpServletResponse response,
        Model model, ExcelUploadVO excelUploadVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = excelUploadService.deleteExcelUpload(excelUploadVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 엑셀업로드 - 엑셀업로드 실패내역 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 10.
     */
    @RequestMapping(value = "/excelUploadErrInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExcelUploadErrInfoList(HttpServletRequest request, HttpServletResponse response,
        Model model, ExcelUploadVO excelUploadVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = excelUploadService.getExcelUploadErrInfoList(excelUploadVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, excelUploadVO);
    }
}
