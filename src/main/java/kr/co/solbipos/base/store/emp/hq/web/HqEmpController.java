package kr.co.solbipos.base.store.emp.hq.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpService;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpVO;
import kr.co.solbipos.base.store.emp.hq.service.enums.HqEmpResult;
import kr.co.solbipos.base.store.emp.hq.validate.Dtl;
import kr.co.solbipos.base.store.emp.hq.validate.Mod;
import kr.co.solbipos.base.store.emp.hq.validate.Reg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnJsonBindingFieldError;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;


/**
 * @Class Name : HqEmpController.java
 * @Description : 기초관리 > 매장관리 > 본사사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자      수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  정상화      최초생성
 *
 * @author NHN한국사이버결제 KCP 정상화
 * @since 2018. 08.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/store/emp/hq")
public class HqEmpController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SessionService sessionService;
    @Autowired
    HqEmpService hqEmpService;

    /**
     * 본사사원 리스트 화면
     * @return the string
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String view() {
        return "base/store/emp/hqEmp";
    }


    /**
     * 본사사원 리스트 조회
     * @param hqEmpVO
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    public Result view(HqEmpVO hqEmpVO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        List<DefaultMap<String>> list = hqEmpService.selectHqEmpList(hqEmpVO);

        return returnListJson(Status.OK, list,hqEmpVO);
    }

    /**
     * 본사사원정보 등록
     * @param hqEmpVO
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/regist.sb", method = RequestMethod.POST)
    public Result regist(@RequestBody @Validated(Reg.class) HqEmpVO hqEmpVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HqEmpResult hqEmpResult = hqEmpService.insertHqEmpInfo(hqEmpVO,sessionInfoVO);

        return returnJson(Status.OK, hqEmpResult);
    }

    /**
     * 본사사원정보 사원번호조회
     * @param hqEmpVO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/chkHqEmpNo.sb", method = RequestMethod.POST)
    public Result chkHqEmpNo(HqEmpVO hqEmpVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HqEmpResult hqEmpResult = hqEmpService.selectHqEmpNoCnt(hqEmpVO, sessionInfoVO);

        return returnJson(Status.OK, hqEmpResult);
    }

    /**
     * 본사사원정보 웹유저아이디조회
     * @param hqEmpVO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/chkHqUserId.sb", method = RequestMethod.POST)
    public Result chkHqUserId(HqEmpVO hqEmpVO) {

        HqEmpResult hqEmpResult= hqEmpService.selectHqUserIdCnt(hqEmpVO);

        return returnJson(Status.OK, hqEmpResult);
    }

    /**
     * 본사사원정보 상세
     * @param hqEmpVO
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail.sb", method = RequestMethod.POST)
    public Result getDtlInfo(@Validated(Dtl.class)HqEmpVO hqEmpVO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        DefaultMap<String> result = hqEmpService.selectHqEmpDtlInfo(hqEmpVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 본사사원정보 수정
     * @param hqEmpVO
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    public Result save(@RequestBody @Validated(Reg.class) HqEmpVO hqEmpVO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            LOGGER.info( "bindingResult : " + bindingResult.hasErrors() );

            return returnJsonBindingFieldError(bindingResult);
        }

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HqEmpResult hqEmpResult = hqEmpService.saveHqEmpInfo(hqEmpVO,sessionInfoVO);

        return returnJson(Status.OK, hqEmpResult);
    }

    /**
     * 비밀번호 변경
     * @param hqEmpVO
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyPassword.sb", method = RequestMethod.POST)
    public Result modifyPassword(@Validated(Mod.class) HqEmpVO hqEmpVO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HqEmpResult hqEmpResult = hqEmpService.modifyPassword(hqEmpVO,sessionInfoVO);

        return returnJson(Status.OK, hqEmpResult);
    }


}
