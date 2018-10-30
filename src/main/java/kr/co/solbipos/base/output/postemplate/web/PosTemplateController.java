package kr.co.solbipos.base.output.postemplate.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.output.postemplate.service.PosTemplateService;
import kr.co.solbipos.base.output.postemplate.service.PosTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : TemplateController.java
 * @Description : 기초관리 > 출력물관리 > 포스출력물관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.04  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/output/posTemplate")
public class PosTemplateController {

    private final PosTemplateService posTemplateService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public PosTemplateController(PosTemplateService posTemplateService, SessionService sessionService) {
        this.posTemplateService = posTemplateService;
        this.sessionService = sessionService;
    }

    /**
     * 포스출력물관리 - 페이지 이동
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return String
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/view.sb", method = RequestMethod.GET)
    public String posView(HttpServletRequest request, HttpServletResponse response, Model model) {
        
        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        PosTemplateVO params = new PosTemplateVO();
        // 출력물종류 목록 조회
        list = posTemplateService.getPrintTypeList(params);
        model.addAttribute("listPrintType", convertToJson(list));

        return "base/output/posTemplate/posTemplate";
    }
    
    /**
     * 포스출력물관리 - 출력물코드 목록 조회
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param posTemplateVO PosTemplateVO
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/code/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrintCodeList(HttpServletRequest request, HttpServletResponse response,
        PosTemplateVO posTemplateVO, Model model) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 출력물코드 목록 조회
        list = posTemplateService.getPrintCodeList(posTemplateVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, posTemplateVO);

    }
    
    /**
     * 포스출력물관리 - 출력물템플릿 목록 조회
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param posTemplateVO PosTemplateVO
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosTemplateList(HttpServletRequest request, HttpServletResponse response,
        PosTemplateVO posTemplateVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 소속구분 설정
        posTemplateVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        posTemplateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        posTemplateVO.setStoreCd(sessionInfoVO.getStoreCd());

        // 실제출력물 없는경우 대비해서 저장처리
        posTemplateService.insertPosTemplatePrint(posTemplateVO, sessionInfoVO);
        // 출력물코드 목록 재조회
        list = posTemplateService.getPosTemplateList(posTemplateVO);

        return ReturnUtil.returnListJson(Status.OK, list, posTemplateVO);
        
    }

    /**
     * 포스출력물관리 - 출력물템플릿 목록 저장
     *
     * @param posTemplateVOs PosTemplateVO[]
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/saveList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePosTemplateList(@RequestBody PosTemplateVO[] posTemplateVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = posTemplateService.savePosTemplateList(posTemplateVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * 포스출력물관리 - 출력물템플릿 저장
     *
     * @param posTemplateVO PosTemplateVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePosTemplate(@RequestBody PosTemplateVO posTemplateVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = posTemplateService.savePosTemplate(posTemplateVO, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * 포스출력물관리 - 출력물템플릿 실제출력물저장
     *
     * @param posTemplateVO PosTemplateVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/applyToPrint.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePosTemplatePrint(@RequestBody PosTemplateVO posTemplateVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = posTemplateService.updatePosTemplatePrint(posTemplateVO, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * 포스출력물관리 - 출력물템플릿 매장적용
     *
     * @param posTemplateVO PosTemplateVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/applyToStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result applyToStoreTemplate(@RequestBody PosTemplateVO posTemplateVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = posTemplateService.applyToStoreTemplate(posTemplateVO, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

}
