package kr.co.solbipos.dlvr.info.dlvrExcelUpload.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodExcelUpload.service.ProdExcelUploadVO;
import kr.co.solbipos.dlvr.info.dlvrEmp.service.DlvrEmpService;
import kr.co.solbipos.dlvr.info.dlvrEmp.service.DlvrEmpVO;
import kr.co.solbipos.dlvr.info.dlvrExcelUpload.service.DlvrExcelUploadService;
import kr.co.solbipos.dlvr.info.dlvrExcelUpload.service.DlvrExcelUploadVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @Class Name : DlvrExcelUploadController.java
 * @Description : 배달관리 > CID 배달정보 > 배달지엑셀업로드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.06.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/dlvr/manage/info")
public class DlvrExcelUploadController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DlvrExcelUploadService dlvrExcelUploadService;
    private final SessionService sessionService;

    @Autowired
    public DlvrExcelUploadController(DlvrExcelUploadService dlvrExcelUploadService, SessionService sessionService) {
        this.dlvrExcelUploadService = dlvrExcelUploadService;
        this.sessionService = sessionService;
    }

    /**
     * 배달지엑셀업로드 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.14
     */
    @RequestMapping(value = "/dlvrExcelUpload/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "dlvr/info/dlvrExcelUpload/dlvrExcelUpload";
    }

    /**
     * 배달지엑셀업로드 임시테이블 데이터 전체 삭제
     *
     * @param dlvrExcelUploadVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.14
     */
    @RequestMapping(value = "/dlvrExcelUpload/getDlvrExcelUploadDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrExcelUploadDeleteAll(DlvrExcelUploadVO dlvrExcelUploadVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dlvrExcelUploadService.getDlvrExcelUploadDeleteAll(dlvrExcelUploadVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 업로드시 임시테이블 저장
     *
     * @param dlvrExcelUploadVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.06.15
     */
    @RequestMapping(value = "/dlvrExcelUpload/getDlvrExcelUploadCheckSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrExcelUploadCheckSave(@RequestBody DlvrExcelUploadVO[] dlvrExcelUploadVOs, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dlvrExcelUploadService.getDlvrExcelUploadCheckSave(dlvrExcelUploadVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 임시테이블 조회
     *
     * @param dlvrExcelUploadVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.06.15
     */
    @RequestMapping(value = "/dlvrExcelUpload/getDlvrExcelUploadCheckList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrExcelUploadCheckList(DlvrExcelUploadVO dlvrExcelUploadVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dlvrExcelUploadService.getDlvrExcelUploadCheckList(dlvrExcelUploadVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dlvrExcelUploadVO);
    }

    /**
     * 배달지엑셀업로드 임시테이블 데이터 삭제
     *
     * @param dlvrExcelUploadVOs
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.15
     */
    @RequestMapping(value = "/dlvrExcelUpload/getDlvrExcelUploadCheckDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrExcelUploadCheckDelete(@RequestBody DlvrExcelUploadVO[] dlvrExcelUploadVOs, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = dlvrExcelUploadService.getDlvrExcelUploadCheckDelete(dlvrExcelUploadVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 검증결과 저장
     *
     * @param dlvrExcelUploadVOs
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.15
     */
    @RequestMapping(value = "/dlvrExcelUpload/getDlvrExcelUploadCheckSaveAdd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrExcelUploadCheckSaveAdd(@RequestBody DlvrExcelUploadVO[] dlvrExcelUploadVOs, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = dlvrExcelUploadService.getDlvrExcelUploadCheckSaveAdd(dlvrExcelUploadVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 결과 저장
     *
     * @param dlvrExcelUploadVOs
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.15
     */
    @RequestMapping(value = "/dlvrExcelUpload/getDeliveryTelNoSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDeliveryTelNoSave(@RequestBody DlvrExcelUploadVO[] dlvrExcelUploadVOs, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = dlvrExcelUploadService.getDeliveryTelNoSave(dlvrExcelUploadVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 검증완료로 저장된 값 임시테이블에서 삭제
     *
     * @param dlvrExcelUploadVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.15
     */
    @RequestMapping(value = "/dlvrExcelUpload/getDlvrExcelUploadCheckDelete2.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrExcelUploadCheckDelete2(DlvrExcelUploadVO dlvrExcelUploadVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = dlvrExcelUploadService.getDlvrExcelUploadCheckDelete2(dlvrExcelUploadVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
