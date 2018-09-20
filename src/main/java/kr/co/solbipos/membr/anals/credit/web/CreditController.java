package kr.co.solbipos.membr.anals.credit.web;

import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.credit.service.CreditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Class Name : CreditController.java
 * @Description : 회원관리 > 회원분석 > 후불회원
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.20  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.09.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/membr/anals/credit/")
public class CreditController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CreditService service;

    @Autowired
    SessionService sessionService;

//    @Autowired
//    CmmCodeUtil cmmCodeUtil;

//    @Autowired
//    CmmEnvUtil cmmEnvUtil;

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "credit/creditView.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "membr/anals/credit/creditView";
    }

//    /**
//     * 회원정보 리스트 조회
//     *
//     * @param registVO
//     * @param request
//     * @param response
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "view/list.sb", method = RequestMethod.POST)
//    @ResponseBody
//    public Result registListPost(RegistVO registVO, HttpServletRequest request, HttpServletResponse response, Model
//            model) {
//
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//
//        List<DefaultMap<Object>> result = registService.selectMembers(registVO, sessionInfoVO);
//
//        return ReturnUtil.returnListJson(Status.OK, result, registVO);
//    }
//
//    /**
//     * 회원정보 등록
//     *
//     * @param registVO
//     * @param request
//     * @param response
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "base/regist.sb", method = RequestMethod.POST)
//    @ResponseBody
//    public Result baseRegist(@Validated(Regist.class) @RequestBody RegistVO registVO, BindingResult bindingResult,
//                              HttpServletRequest request, HttpServletResponse response, Model model) {
//
//        // 입력값 에러 처리
//        if (bindingResult.hasErrors()) {
//            return returnJsonBindingFieldError(bindingResult);
//        }
//
//        SessionInfoVO si = sessionService.getSessionInfo(request);
//        // 기본값 세팅
//        registVO.setMembrClassCd("000");
//        registVO.setLunarYn("N");
//        registVO.setMembrOrgnCd(si.getOrgnCd());
//        registVO.setRegId(si.getUserId());
//        registVO.setRegDt(DateUtil.currentDateTimeString());
//        registVO.setModId(si.getUserId());
//        registVO.setModDt(DateUtil.currentDateTimeString());
//
//        int result = registService.saveRegistMember(registVO);
//
//        LOGGER.info(">>>>>>>>> getMembrNo : "+ registVO.getMembrNo());
//        LOGGER.info(">>>>>>>>> getMembrOrgnCd : "+ registVO.getMembrOrgnCd());
//        LOGGER.info(">>>>>>>>> creditStore : "+ registVO.getCreditStore());
//
//        // 본사에서 등록시
//        if(si.getOrgnFg() == OrgnFg.HQ) {
//
//            // 후불회원 적용매장 등록
//            if( !StringUtil.isEmpties(registVO.getCreditStore()) ) {
//                result += registService.saveCreditStores(registVO);
//            }
//        }
//
//        return ReturnUtil.returnJson(Status.OK, result);
//    }
}
