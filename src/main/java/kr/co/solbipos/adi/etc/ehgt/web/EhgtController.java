package kr.co.solbipos.adi.etc.ehgt.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnJsonBindingFieldError;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.validate.CustomCollectionValidator;
import kr.co.common.validate.Save;
import kr.co.solbipos.adi.etc.ehgt.service.CrncyCdVO;
import kr.co.solbipos.adi.etc.ehgt.service.EhgtService;
import kr.co.solbipos.adi.etc.ehgt.service.EhgtVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : EhgtController.java
 * @Description : 부가서비스 > 환율관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.09  조병준      최초생성
 *
 * @author NHN한국사이버결제 조병준
 * @since 2018. 08.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/adi/etc/ehgt/")
public class EhgtController {

    private final EhgtService ehgtService;
    private final SessionService sessionService;
    private final MessageService messageService;
    private final CustomCollectionValidator customCollectionValidator;

    /** Constructor Injection */
    @Autowired
    public EhgtController(EhgtService ehgtService, SessionService sessionService,
        MessageService messageService, CustomCollectionValidator customCollectionValidator) {
        this.ehgtService = ehgtService;
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.customCollectionValidator = customCollectionValidator;
    }

    /**
     * 환율 관리 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "regist/list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> hqCrncys = ehgtService.getCdListByGrpCd(UseYn.Y, sessionInfoVO);

        //입력 table과 grid의 헤더를 위한 데이터
        model.addAttribute("hqCrncys", hqCrncys);

        return "adi/etc/ehgt/regist";
    }

    /**
     * 환율 관리 리스트 조회
     *
     * @param ehgtVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "regist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(@Valid EhgtVO ehgtVO, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        if(bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = ehgtService.getEhgtListBySaleDt(ehgtVO, sessionInfoVO);

        return returnListJson(Status.OK, list);
    }

    /**
     * 환율 관리 해당일의 환율 조회
     *
     * @param ehgtVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "regist/detail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result detail(EhgtVO ehgtVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = ehgtService.getEhgtDetailBySaleDt(ehgtVO, sessionInfoVO);

        return returnListJson(Status.OK, list);
    }

    /**
     * 환율 등록
     *
     * @param ehgtVOs
     * @param bindingResult
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "regist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody @Valid List<EhgtVO> ehgtVOs,
            BindingResult bindingResult, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        customCollectionValidator.validate(ehgtVOs, bindingResult, Save.class);
        if(bindingResult.hasErrors()) {
            //return returnJsonBindingFieldError(bindingResult);
            return returnJson(Status.FAIL, "msg", messageService.get("ehgt.krwAmt")
                    + messageService.get("cmm.input.fail"));
        }

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = ehgtService.saveEhgts(ehgtVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }


    /**
     * 통화구분 팝업 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "crncy/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCrncyList(HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = ehgtService.getCdListByGrpCd(UseYn.ALL, sessionInfoVO);
        return returnListJson(Status.OK, list);
    }

    /**
     * 통화구분 저장
     *
     * @param crncyCdVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "crncy/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCrncy(@RequestBody @Valid List<CrncyCdVO> crncyCdVOs,
            BindingResult bindingResult, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        customCollectionValidator.validate(crncyCdVOs, bindingResult, Save.class);
        if(bindingResult.hasErrors()) {
            //return returnJsonBindingFieldError(bindingResult);
            return returnJson(Status.FAIL, "msg", messageService.get("ehgt.krwAmt")
                    + messageService.get("cmm.input.fail"));
        }

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = ehgtService.updateCrncyCd(crncyCdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
