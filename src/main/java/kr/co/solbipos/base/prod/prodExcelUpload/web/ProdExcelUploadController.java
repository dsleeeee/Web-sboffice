package kr.co.solbipos.base.prod.prodExcelUpload.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodExcelUpload.service.ProdExcelUploadService;
import kr.co.solbipos.base.prod.prodExcelUpload.service.ProdExcelUploadVO;
import kr.co.solbipos.base.prod.prod.service.enums.PriceEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

@Controller
@RequestMapping("/base/prod/prodExcelUpload")
public class ProdExcelUploadController {

    private final SessionService sessionService;
    private final ProdExcelUploadService prodExcelUploadService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdExcelUploadController(SessionService sessionService, ProdExcelUploadService prodExcelUploadService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.prodExcelUploadService = prodExcelUploadService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodExcelUpload/list.sb", method = RequestMethod.GET)
    public String prodExcelUploadView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 상품등록 본사 통제여부
        ProdEnvFg prodEnvstVal = ProdEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0020"));

        // 상품코드 채번방식
        ProdNoEnvFg prodNoEnvFg;
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0028"));
        }else{
            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0028"));
        }

        model.addAttribute("prodEnvstVal", prodEnvstVal);
        model.addAttribute("prodNoEnvFg", prodNoEnvFg);

        return "base/prod/prodExcelUpload/prodExcelUpload";
    }

    /**
     * 검증결과 전체 삭제
     *
     * @param prodExcelUploadVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 09. 09.
     */
    @RequestMapping(value = "/prodExcelUpload/getProdExcelUploadCheckDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdExcelUploadCheckDeleteAll(@RequestBody ProdExcelUploadVO prodExcelUploadVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodExcelUploadService.getProdExcelUploadCheckDeleteAll(prodExcelUploadVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 검증결과 조회
     *
     * @param prodExcelUploadVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 09. 09.
     */
    @RequestMapping(value = "/prodExcelUpload/getProdExcelUploadList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdExcelUploadList(ProdExcelUploadVO prodExcelUploadVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodExcelUploadService.getProdExcelUploadList(prodExcelUploadVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodExcelUploadVO);
    }

    /**
     * 업로드시 임시테이블 저장
     *
     * @param prodExcelUploadVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 10. 14.
     */
    @RequestMapping(value = "/prodExcelUpload/getProdExcelUploadAddSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdExcelUploadAddSave(@RequestBody ProdExcelUploadVO[] prodExcelUploadVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodExcelUploadService.getProdExcelUploadAddSave(prodExcelUploadVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 검증결과 저장
     *
     * @param prodExcelUploadVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 10. 14.
     */
    @RequestMapping(value = "/prodExcelUpload/getProdExcelUploadCheckSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdExcelUploadCheckSave(@RequestBody ProdExcelUploadVO[] prodExcelUploadVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodExcelUploadService.getProdExcelUploadCheckSave(prodExcelUploadVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}