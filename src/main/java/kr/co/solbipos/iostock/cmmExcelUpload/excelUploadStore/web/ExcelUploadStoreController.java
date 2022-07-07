package kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service.ExcelUploadStoreService;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service.ExcelUploadStoreVO;
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
 * @Class Name : ExcelUploadStoreController.java
 * @Description : 공통팝업 실사/조정/폐기 엑셀업로드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.07.04
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/cmmExcelUpload/excelUploadStore")
public class ExcelUploadStoreController {

    private final SessionService sessionService;
    private final ExcelUploadStoreService excelUploadStoreService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ExcelUploadStoreController(SessionService sessionService, ExcelUploadStoreService excelUploadStoreService) {
        this.sessionService = sessionService;
        this.excelUploadStoreService = excelUploadStoreService;
    }

    /**
     * 엑셀업로드 - 엑셀업로드 TEMP 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadStoreVOs
     * @return  String
     * @author  권지현
     * @since   2022.07.04
     */
    @RequestMapping(value = "/excelUploadStore/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveExcelUpload(HttpServletRequest request, HttpServletResponse response,
                                  Model model, @RequestBody ExcelUploadStoreVO[] excelUploadStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = excelUploadStoreService.saveExcelUpload(excelUploadStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.07.04
     */
    @RequestMapping(value = "/excelUploadStore/saveUpdateProdCd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUpdateProdCd(HttpServletRequest request, HttpServletResponse response,
                                   Model model, ExcelUploadStoreVO excelUploadStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = excelUploadStoreService.saveUpdateProdCd(excelUploadStoreVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.07.04
     */
    @RequestMapping(value = "/excelUploadStore/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteExcelUpload(HttpServletRequest request, HttpServletResponse response,
                                    Model model, ExcelUploadStoreVO excelUploadStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = excelUploadStoreService.deleteExcelUpload(excelUploadStoreVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 엑셀업로드 - 엑셀업로드 실패내역 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   excelUploadStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.07.04
     */
    @RequestMapping(value = "/excelUploadStoreErrInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExcelUploadErrInfoList(HttpServletRequest request, HttpServletResponse response,
                                            Model model, ExcelUploadStoreVO excelUploadStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = excelUploadStoreService.getExcelUploadErrInfoList(excelUploadStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, excelUploadStoreVO);
    }
}