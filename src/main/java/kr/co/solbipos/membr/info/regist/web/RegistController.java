package kr.co.solbipos.membr.info.regist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
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

    @Autowired
    RegistService registService;

    @Autowired
    SessionService sessionService;

    @Autowired
    CmmCodeUtil cmmCodeUtil;

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "view/list.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 등록 매장 조회
        List regstrStoreList = registService.selectRgstrStore(sessionInfoVO);
        // 등록 매장 전체 포함
        String regstrStoreListAll = cmmCodeUtil.assmblObj(regstrStoreList, "name", "value", UseYn.ALL);

        model.addAttribute("regstrStoreListAll", regstrStoreListAll);
        model.addAttribute("periodDate", getPeriodList());
        model.addAttribute("weddingData", getWedding());

        return "membr/info/view/view";
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
    @RequestMapping(value = "view/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result registListPost(RegistVO registVO, HttpServletRequest request, HttpServletResponse response, Model
            model) {

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
    @RequestMapping(value = "base/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result baseListPost(RegistVO registVO, HttpServletRequest request, HttpServletResponse response, Model
            model) {
        RegistVO vo = registService.selectMember(registVO);
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
    @RequestMapping(value = "base/regist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result baseRegist(@Validated(Regist.class) @RequestBody RegistVO registVO, BindingResult bindingResult,
                              HttpServletRequest request, HttpServletResponse response, Model model) {

        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        SessionInfoVO si = sessionService.getSessionInfo(request);
        // 기본값 세팅
        registVO.setMembrClassCd("000");
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
    @RequestMapping(value = "base/remove.sb", method = RequestMethod.POST)
    @ResponseBody
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
     * 조회 기간 콤보 박스 내용
     *
     * @return
     */
    public String getPeriodList() {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> c = new HashMap<>();
        c.put("name", "기간 미사용");
        c.put("value", "all");
        list.add(c);
        c = new HashMap<>();
        c.put("name", "가입일");
        c.put("value", "reg");
        list.add(c);
        /*
        // 최종 방문일은 TB_MB_MEMBER_POINT 테이블 LAST_SALE_DATE 을 기준으로 기간 조회하는데
        // 현시점에서는 TB_MB_MEMBER_POINT 을 구현하지 않음
        c = new HashMap<>();
        c.put("name", "최종 방문일");
        c.put("value", "last");
        list.add(c);
        */
        return StringUtil.convertToJson(list);
    }

    /**
     * 결혼 유무 콤보박스 내용 테스트
     *
     * @return
     */
    public String getWedding() {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> c = new HashMap<>();
        c.put("name", "미혼");
        c.put("value", "N");
        list.add(c);
        c = new HashMap<>();
        c.put("name", "기혼");
        c.put("value", "Y");
        list.add(c);
        return StringUtil.convertToJson(list);
    }
}
