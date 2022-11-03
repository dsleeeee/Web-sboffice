package kr.co.solbipos.membr.info.regist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusService;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import kr.co.solbipos.membr.info.regist.enums.WeddingYn;
import kr.co.solbipos.membr.info.regist.service.MemberMappingVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnJsonBindingFieldError;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : RegistController.java
 * @Description : 회원관리 > 회원정보 > 회원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 * @ 2018.11.08  김지은      회원정보관리 수정
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
    private final MessageService messageService;
    private final SendStatusService sendStatusService; // SMS전송

    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public RegistController(RegistService registService, SessionService sessionService, MessageService messageService, SendStatusService sendStatusService,
                            CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.registService = registService;
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.sendStatusService = sendStatusService; // SMS전송
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 회원정보조회 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "view/list.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 등록 매장 조회
        List regstrStoreList = registService.getRegistStore(sessionInfoVO);
        // 등록 매장 전체 포함
        String regstrStoreListAll = cmmCodeUtil.assmblObj(regstrStoreList, "name", "value", UseYn.SELECT);
        // 회원등급 리스트 조회
        List membrClassList = registService.getMembrClassList(sessionInfoVO);

        String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.ALL);
        String membrClassListSelect = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.N);

        // 본사일 경우 해당 본사의 기본매장(코드)을 조회 해야 함.
        // [보나비]의 경우 기본매장코드를 사용하여
        // 회원등록 매장이 기본매장일 경우 후불회원 적용매장을 등록한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        model.addAttribute("regstrStoreList", regstrStoreListAll);
        model.addAttribute("memberClassList", membrClassListAll);
        model.addAttribute("memberClassSelect", membrClassListSelect);
        model.addAttribute("defaultStoreCd", defaultStoreCd);


        SendStatusVO sendStatusVO = new SendStatusVO();
        // SMS전송 - 메세지그룹 조회
        List<DefaultMap<String>> msgGrpColList = sendStatusService.getMsgGrpColList(sendStatusVO, sessionInfoVO);
        model.addAttribute("msgGrpColList", msgGrpColList);

        // 회원 강제삭제를 위한 강제삭제 체크용 비밀번호 조회
        model.addAttribute("forcedDeleteChkPwd", registService.getForcedDeleteChkPwd());

        // 회원등급 관리구분
        model.addAttribute("membrClassManageFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1237"), "1"));


        /** 광운대 아이스링크 */
        // [1246 광운대아이스링크] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("kwuEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1246"), "0"));
            System.out.println("kwuEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1246"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("kwuEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1246"), "0"));
            System.out.println("kwuEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1246"), "0"));
        }

        // 코드별 공통코드 콤보박스 조회
        // 회원추가정보-회원구분
        List membrFgComboList = registService.getNmcodeComboList(sessionInfoVO, "142");
        String membrFgComboListAll = "";
        if (membrFgComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "선택");
            m.put("value", "");
            list.add(m);
            membrFgComboListAll = convertToJson(list);
        } else {
            membrFgComboListAll = cmmCodeUtil.assmblObj(membrFgComboList, "name", "value", UseYn.SELECT);
        }
        model.addAttribute("membrFgComboList", membrFgComboListAll);
        // 단체구분
        List groupFgComboList = registService.getNmcodeComboList(sessionInfoVO, "143");
        String groupFgComboListAll = "";
        if (groupFgComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "선택");
            m.put("value", "");
            list.add(m);
            groupFgComboListAll = convertToJson(list);
        } else {
            groupFgComboListAll = cmmCodeUtil.assmblObj(groupFgComboList, "name", "value", UseYn.SELECT);
        }
        model.addAttribute("groupFgComboList", groupFgComboListAll);
        // 강사명
        List teacherCdComboList = registService.getNmcodeComboList(sessionInfoVO, "139");
        String teacherCdComboListAll = "";
        if (teacherCdComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "선택");
            m.put("value", "");
            list.add(m);
            teacherCdComboListAll = convertToJson(list);
        } else {
            teacherCdComboListAll = cmmCodeUtil.assmblObj(teacherCdComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("teacherCdComboList", teacherCdComboListAll);
        // 강습구분
        List classFgComboList = registService.getNmcodeComboList(sessionInfoVO, "140");
        String classFgComboListAll = "";
        if (classFgComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "선택");
            m.put("value", "");
            list.add(m);
            classFgComboListAll = convertToJson(list);
        } else {
            classFgComboListAll = cmmCodeUtil.assmblObj(classFgComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("classFgComboList", classFgComboListAll);
        // 스케이트종류
        List skateFgComboList = registService.getNmcodeComboList(sessionInfoVO, "144");
        String skateFgComboListAll = "";
        if (skateFgComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "선택");
            m.put("value", "");
            list.add(m);
            skateFgComboListAll = convertToJson(list);
        } else {
            skateFgComboListAll = cmmCodeUtil.assmblObj(skateFgComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("skateFgComboList", skateFgComboListAll);
        /** //광운대 아이스링크 */


        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
            model.addAttribute("posLoginReconnect", request.getParameter("posLoginReconnect"));
        }

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
    @RequestMapping(value = "view/getMemberlist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberlist(RegistVO registVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = registService.getMemberList(registVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, registVO);
    }

    /**
     * 회원정보 리스트 조회(Excel 용)
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "view/getMemberListExcel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberListExcel(RegistVO registVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = registService.getMemberListExcel(registVO, sessionInfoVO);

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
    @RequestMapping(value = "base/getMemberInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result baseListPost(RegistVO registVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = registService.getMemberInfo(registVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
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
    @RequestMapping(value = "base/registMemberInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result registMemberInfo(@Validated(Regist.class) @RequestBody RegistVO registVO, BindingResult bindingResult,
                                   HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // TODO: 2020-06-22 권한(매장 / 본사)별 검색조건 추가 

        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        // 생일 특문 제거
        if(null != registVO.getBirthday() && !"".equals(registVO.getBirthday())) {
            registVO.setBirthday(registVO.getBirthday().replaceAll("-", ""));
        }


        // 결혼여부 선택값이 미혼이면 결혼기념일 null
        if(registVO.getWeddingYn() == WeddingYn.N) {
            registVO.setWeddingday(null);
        } else {
            registVO.setWeddingday(registVO.getWeddingday().replaceAll("-",""));
        }

        // 회원 등록
        String result = registService.registMemberInfo(registVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 회원정보 수정
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/updateMemberInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateMemberInfo(@Validated(Regist.class) @RequestBody RegistVO registVO, BindingResult bindingResult,
                                   HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        // 생일 특문 제거
        if(null != registVO.getBirthday() && !"".equals(registVO.getBirthday())) {
            registVO.setBirthday(registVO.getBirthday().replaceAll("-", ""));
        }

        // 결혼여부 선택값이 미혼이면 결혼기념일 null
        if(registVO.getWeddingYn() == WeddingYn.N) {
            registVO.setWeddingday(null);
        } else {
            registVO.setWeddingday(registVO.getWeddingday().replaceAll("-",""));
        }

        int result = registService.updateMemberInfo(registVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 회원정보 삭제
     *
     * @param registVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/remove.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result baseRemove(@Validated(RegistDelete.class) @RequestBody RegistVO[] registVOs, BindingResult bindingResult,
                             HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        int result = registService.deleteMemberInfo(registVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 후불 회원 등록 매장 조회
     * @param postpaidStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "postpaid/getPostpaidStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPostpaidStoreList(PostpaidStoreVO postpaidStoreVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = registService.getPostpaidStoreLists(postpaidStoreVO, si);

        return ReturnUtil.returnListJson(Status.OK, list);
    }

    /***
     * 후불매장 등록
     * @param postpaidStoreVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "postpaid/registPostpaidStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result registPostpaidStore(@RequestBody PostpaidStoreVO[] postpaidStoreVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.registPostpaidStore(postpaidStoreVOs, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /***
     * 후불매장 삭제
     * @param postpaidStoreVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "postpaid/deletePostpaidStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deletePostpaidStore(@RequestBody PostpaidStoreVO[] postpaidStoreVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.deletePostpaidStore(postpaidStoreVOs, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 회원 매핑 대리점 조회
     * @param memberMappingVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "mapping/getMappingCompany.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMappingCompany(MemberMappingVO memberMappingVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = registService.getMappingCompany(memberMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, memberMappingVO);
    }

    /**
     * 회원 거래처 매핑 팝업 - 회원 거래처 매핑 조회
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "view/getMemberVendorMappingList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberVendorMappingList(RegistVO registVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = registService.getMemberVendorMappingList(registVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, registVO);
    }

    /**
     * 카드정보 리스트 조회
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "view/getCardlist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardlist(RegistVO registVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = registService.getCardList(registVO, sessionInfoVO);

        model.addAttribute("orgnCd", sessionInfoVO.getOrgnCd());
        model.addAttribute("orgnNm", sessionInfoVO.getOrgnNm());
        return ReturnUtil.returnListJson(Status.OK, result, registVO);
    }

    /**
     * 회원명 중복체크
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/getMemberNmCount.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberNmCount(RegistVO registVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.getMemberNmCount(registVO, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 전화번호 중복체크
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/getMemberTelNoCount.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberTelNoCount(RegistVO registVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.getMemberTelNoCount(registVO, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 카드정보 중복체크
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/registCardInfoCount.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberCardInfoCount(RegistVO registVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.getMemberCardInfoCount(registVO, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 카드 중복 체크( 카드번호 사용중인 회원번호 / X (해당 카드번호 미사용) )
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/getMemberCardInfoCountDetail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberCardInfoCountDetail(RegistVO registVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        String result = registService.getMemberCardInfoCountDetail(registVO, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /***
     * 카드정보 등록
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/registCardInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result registCardInfo(@RequestBody RegistVO registVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.registCardInfo(registVO, si);

        return ReturnUtil.returnJson(Status.OK, registVO.getMembrNo());
    }

    /***
     * 카드정보 등록
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/updateMemberCard.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateMembrCard(@RequestBody RegistVO registVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.updateMembrCard(registVO, si);

        return ReturnUtil.returnJson(Status.OK, registVO.getMembrNo());
    }


    /**
     * 배달정보 리스트 조회
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "view/getDlvrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrList(RegistVO registVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = registService.getDlvrList(registVO, sessionInfoVO);

//        return ReturnUtil.returnListJson(Status.OK, result, registVO);
        return returnJson(Status.OK, result);
    }

    /**
     * 중분류 리스트 조회
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "view/getDlvrMzoneList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrMzoneList(RegistVO registVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = registService.getDlvrMzoneList(registVO, sessionInfoVO);

//        return ReturnUtil.returnListJson(Status.OK, result, registVO);
        return returnJson(Status.OK, result);
    }

    /**
     * 배달전화번호정보 리스트 조회
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "view/getDlvrTelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrTelList(RegistVO registVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = registService.getDlvrTelList(registVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, registVO);
    }

    /***
     * 배달정보 등록
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/registDlvrInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result registDlvrInfo(@RequestBody RegistVO registVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.registDlvrInfo(registVO, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }
    /***
     * 배달전화번호정보 등록
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/registDlvrTelInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result registDlvrTelInfo(@RequestBody RegistVO registVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.registDlvrTelInfo(registVO, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /***
     * 배달전화번호정보 수정
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/updateDlvrTelInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateDlvrTelInfo(@RequestBody RegistVO registVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.updateDlvrTelInfo(registVO, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /***
     * 배달전화번호정보 삭제
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/deleteDlvrTelInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteDlvrTelInfo(@RequestBody RegistVO registVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.deleteDlvrTelInfo(registVO, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 배달주소지 수정
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 11. 17.
     */
    @RequestMapping(value = "base/updateDlvrAddrInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateDlvrAddrInfo(@RequestBody RegistVO registVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.updateDlvrAddrInfo(registVO, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 배달주소지 삭제
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 11. 17.
     */
    @RequestMapping(value = "base/deleteDlvrAddrInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteDlvrAddrInfo(@RequestBody RegistVO registVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = registService.deleteDlvrAddrInfo(registVO, si);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 회원정보 포인트변경내역 조회
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 11. 27.
     */
    @RequestMapping(value = "base/getMemberInfoPointList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberInfoPointList(RegistVO registVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = registService.getMemberInfoPointList(registVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, registVO);
    }

    /**
     * 회원정보 구매내역 조회
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 11. 27.
     */
    @RequestMapping(value = "base/getMemberInfoBuyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberInfoBuyList(RegistVO registVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = registService.getMemberInfoBuyList(registVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, registVO);
    }

    /**
     * 회원 포인트 조회 팝업 - 조회
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 12. 10.
     */
    @RequestMapping(value = "base/getSearchMemberPointList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchMemberPointList(RegistVO registVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = registService.getSearchMemberPointList(registVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, registVO);
    }

    /**
     * 회원 포인트 이관 팝업 - 저장
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 12. 10.
     */
    @RequestMapping(value = "/base/getMemberPointMoveSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberPointMoveSave(@RequestBody RegistVO registVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = registService.getMemberPointMoveSave(registVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 회원 등급 조회 팝업 - 조회
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 10.
     */
    @RequestMapping(value = "base/getSearchMemberClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchMemberClassList(RegistVO registVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = registService.getSearchMemberClassList(registVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, registVO);
    }

    /**
     * 회원 포인트 조정 팝업 - 저장
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 10.
     */
    @RequestMapping(value = "/base/getMemberPointAdjustSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberPointAdjustSave(@RequestBody RegistVO registVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = registService.getMemberPointAdjustSave(registVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 회원 포인트 조정 팝업 - 조회
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 10.
     */
    @RequestMapping(value = "base/getMemberPointAdjustList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberPointAdjustList(RegistVO registVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = registService.getMemberPointAdjustList(registVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 선택회원삭제
     *
     * @param registVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2022. 02. 11.
     */
    @RequestMapping(value = "base/selectMemberDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectMemberDelete(@RequestBody RegistVO[] registVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = registService.selectMemberDelete(registVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 전체회원삭제
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2022. 02. 11.
     */
    @RequestMapping(value = "base/allMemberDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result allMemberDelete(@RequestBody RegistVO registVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = registService.allMemberDelete(registVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 회원정보 조회 (광운대아이스링크 추가정보)
     *
     * @param registVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "base/getMemberInfoAddKwu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberInfoAddKwu(RegistVO registVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = registService.getMemberInfoAddKwu(registVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}