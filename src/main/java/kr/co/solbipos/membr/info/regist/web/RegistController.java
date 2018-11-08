package kr.co.solbipos.membr.info.regist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.credit.service.CreditStoreVO;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.membr.info.regist.validate.Regist;
import kr.co.solbipos.membr.info.regist.validate.RegistDelete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJsonBindingFieldError;

/**
 * @Class Name : RegistController.java
 * @Description : 회원관리 > 회원정보 > 회원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018.05.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/membr/info/view/")
public class RegistController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RegistService registService;
    private final SessionService sessionService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public RegistController(RegistService registService, SessionService sessionService,
        CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.registService = registService;
        this.sessionService = sessionService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "view/list.sb", method = RequestMethod.GET) public String registList(
        HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 등록 매장 조회
        List regstrStoreList = registService.selectRgstrStore(sessionInfoVO);
        // 등록 매장 전체 포함
        String regstrStoreListAll = cmmCodeUtil.assmblObj(regstrStoreList, "name", "value", UseYn.ALL);

        // 회원등급 리스트 조회
        List membrClassList = registService.selectMembrClassList(sessionInfoVO);

        String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.N);

        // 본사일 경우 해당 본사의 기본매장(코드)을 조회 해야 함.
        // [보나비]의 경우 기본매장코드를 사용하여
        // 회원등록 매장이 기본매장일 경우 후불회원 적용매장을 등록한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }
        model.addAttribute("regstrStoreListAll", regstrStoreListAll);
        model.addAttribute("comboData", membrClassListAll);
        model.addAttribute("defaultStoreCd", defaultStoreCd);

        return "membr/info/view/memberInfo";
    }

    /**
     * 회원정보 리스트 조회
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "view/list.sb", method = RequestMethod.POST) @ResponseBody
    public Result registListPost(RegistVO registVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = registService.selectMembers(registVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, registVO);
    }

    /**
     * 회원정보 조회
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/getMemberInfo.sb", method = RequestMethod.POST) @ResponseBody
    public Result baseListPost(RegistVO registVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        RegistVO vo = registService.selectMember(registVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, vo);
    }

    /**
     * 회원정보 등록
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/regist.sb", method = RequestMethod.POST) @ResponseBody
    public Result baseRegist(@Validated(Regist.class) @RequestBody RegistVO registVO, BindingResult bindingResult,
        HttpServletRequest request, HttpServletResponse response, Model model) {

        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        SessionInfoVO si = sessionService.getSessionInfo(request);
        // 기본값 세팅
        registVO.setMembrClassCd("000");    // TODO 추후 회원등급 개발되면 수정
        registVO.setLunarYn("N");
        registVO.setMembrOrgnCd(si.getOrgnCd());
        registVO.setRegId(si.getUserId());
        registVO.setRegDt(DateUtil.currentDateTimeString());
        registVO.setModId(si.getUserId());
        registVO.setModDt(DateUtil.currentDateTimeString());

        int result = registService.saveRegistMember(registVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 회원정보 삭제
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/remove.sb", method = RequestMethod.POST) @ResponseBody
    public Result baseRemove(@Validated(RegistDelete.class) RegistVO registVO, BindingResult bindingResult,
        HttpServletRequest request, HttpServletResponse response, Model model) {
        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        SessionInfoVO si = sessionService.getSessionInfo(request);
        registVO.setModId(si.getUserId());
        registVO.setModDt(DateUtil.currentDateTimeString());

        int result = registService.deleteMember(registVO);
        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 후불 회원 등록 매장 조회
     * @param creditStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "credit/getCreditStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCreditStoreList(CreditStoreVO creditStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        Map<String, Object> result = registService.getCreditStoreLists(creditStoreVO, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /***
     * 후불매장 등록
     * @param creditStoreVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "credit/saveCreditStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCreditStore(@RequestBody CreditStoreVO[] creditStoreVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.saveCreditStore(creditStoreVOs, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
