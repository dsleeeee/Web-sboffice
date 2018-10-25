package kr.co.solbipos.base.store.emp.store.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.BizException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.validate.UserPwChange;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpService;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpVO;
import kr.co.solbipos.base.store.emp.store.service.enums.StoreEmpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StoreEmpController.java
 * @Description : 기초관리 > 매장관리 > 매장사원관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.16  hblee      최초생성
 *
 * @author NHN한국사이버결제 이한빈
 * @since 2018.08.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(StoreEmpController.PREFIX)
public class StoreEmpController {
    static final String PREFIX = "base/store/emp/store";

    private final SessionService sessionService;
    private final MessageService messageService;
    private final StoreEmpService storeEmpService;

    /** Constructor Injection */
    @Autowired
    public StoreEmpController(SessionService sessionService, MessageService messageService,
        StoreEmpService storeEmpService) {
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.storeEmpService = storeEmpService;
    }

    @GetMapping("/list.sb")
    public String list(Model model) { return PREFIX + "Emp"; }

    /**
     * 매장사원 목록을 조회한다.
     * @param request
     * @param storeEmpVO
     * @return
     * @author 이한빈
     * @since 2018. 08. 16.
     */
    @PostMapping("/list.sb")
    @ResponseBody
    public Result getStoreEmpList(HttpServletRequest request, StoreEmpVO storeEmpVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        if( sessionInfoVO.getOrgnFg() == OrgnFg.STORE )
            storeEmpVO.setStoreCd(sessionInfoVO.getOrgnCd());

        // 매장사원목록 조회
        List<DefaultMap<String>> list = storeEmpService.getStoreEmpList(storeEmpVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeEmpVO);
    }

    /**
     * 매장사원을 상세조회한다.
     * @param request
     * @param storeEmpVO
     * @return
     * @author 이한빈
     * @since 2018. 08. 16.
     */
    @PostMapping("/detail.sb")
    @ResponseBody
    public Result getStoreEmp(HttpServletRequest request, StoreEmpVO storeEmpVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        if( sessionInfoVO.getOrgnFg() == OrgnFg.STORE )
            storeEmpVO.setStoreCd(sessionInfoVO.getOrgnCd());

        // 매장사원 조회
        DefaultMap<String> storeEmp = storeEmpService.getStoreEmp(storeEmpVO);

        return ReturnUtil.returnJson(Status.OK, storeEmp);
    }

    /**
     * 매장사원을 등록/수정한다.
     * @param request
     * @param storeEmpVO
     * @return
     * @author 이한빈
     * @since 2018. 08. 16.
     */
    @PostMapping("/save.sb")
    @ResponseBody
    public Result saveStoreEmp(HttpServletRequest request, @Valid @RequestBody StoreEmpVO storeEmpVO,
                    BindingResult bindingResult) {
        /////////////////////////////////////////////// 입력값 검증 - 시작 //////////////////////////////////////////////////////////
        if( bindingResult.hasErrors() )
            return ReturnUtil.returnJsonBindingFieldError(bindingResult);

        HashMap<String, String> map = new HashMap<>();
        // 웹 사용자 등록이거나 비밀번호가 변경되었을 경우 비밀번호 검증
        if( storeEmpVO.getWebUserRegist() || !StringUtil.isEmpty(storeEmpVO.getNewPwd()) ) {
            try {
                storeEmpVO.setNewPwd(storeEmpService.getValidPwd(storeEmpVO));
            } catch (BizException e) {
                map.put("newPwd", e.getMessage());
            }
        }

        // 웹 사용여부가 사용이면서 웹 사용자 ID가 없는 경우 에러메세지 리턴
        if( storeEmpVO.getWebUseYn() == UseYn.Y && StringUtil.isEmpty(storeEmpVO.getUserId()) ) {
            map.put("userId", messageService.get("storeEmp.userId") + messageService.get("cmm.require.text"));
        }

        if( !map.isEmpty() ) return ReturnUtil.returnJson(Status.FAIL, map);
        /////////////////////////////////////////////// 입력값 검증 - 종료 //////////////////////////////////////////////////////////

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 접속자가 매장사원이 아니면 매장 사원을 등록할 수 없음.
        if( sessionInfoVO.getOrgnFg() != OrgnFg.STORE )
            throw new BizException(messageService.get("cmm.access.denied"));

        if( StringUtil.isEmpty(storeEmpVO.getStoreCd()) ) {
            storeEmpVO.setStoreCd(sessionInfoVO.getOrgnCd());
            storeEmpVO.setEmpRegist(true);
        }

        storeEmpVO.setRegId(sessionInfoVO.getUserId());
        storeEmpVO.setRegDt(currentDateTimeString());

        // 매장사원 저장
        StoreEmpResult result = storeEmpService.saveStoreEmp(storeEmpVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 웹사용자ID 중복체크한다.
     * @param request
     * @param userId
     * @return
     * @author 이한빈
     * @since 2018. 08. 16.
     */
    @PostMapping("/checkUserId.sb")
    @ResponseBody
    public Result checkDuplicateUserId(HttpServletRequest request, String userId) {
        // 아이디 중복된 아이디인지
        boolean isDuplicated = storeEmpService.checkDuplicateUserId(userId);

        return ReturnUtil.returnJson(Status.OK, isDuplicated);
    }

    /**
     * 비밀번호를 변경한다.
     * @param request
     * @param storeEmpVO
     * @return
     * @author 이한빈
     * @since 2018. 08. 16.
     */
    @PostMapping("/modifyPwd.sb")
    @ResponseBody
    public Result updatePwd(HttpServletRequest request, @Validated(UserPwChange.class) @RequestBody StoreEmpVO storeEmpVO,
                    BindingResult bindingResult) {
        /////////////////////////////////////////////// 입력값 검증 - 시작 //////////////////////////////////////////////////////////
        if( bindingResult.hasErrors() )
            return ReturnUtil.returnJsonBindingFieldError(bindingResult);

        // 비밀번호 검증
        try {
            storeEmpVO.setNewPwd(storeEmpService.getValidPwd(storeEmpVO));
        } catch (BizException e) {
            return ReturnUtil.returnJson(Status.FAIL, "newPwd", e.getMessage());
        }
        /////////////////////////////////////////////// 입력값 검증 - 종료 //////////////////////////////////////////////////////////

        storeEmpVO.setRegId(sessionService.getSessionInfo(request).getUserId());
        storeEmpVO.setRegDt(currentDateTimeString());

        // 웹 사용자 패스워드 변경
        StoreEmpResult result = storeEmpService.saveWebUser(storeEmpVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
