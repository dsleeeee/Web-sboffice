package kr.co.solbipos.mobile.application.direct.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusService;
import kr.co.solbipos.application.common.service.ResrceInfoVO;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.application.direct.service.MobileDirectService;
import kr.co.solbipos.mobile.application.direct.service.MobileDirectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;

import static kr.co.common.utils.HttpUtils.getClientIp;
import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @Class Name : MobileDirectController.java
 * @Description : QR > 원하는 페이지 오픈
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.11.24  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.11.24
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/mobile/direct/")
public class MobileDirectController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SessionService sessionService;
    @Autowired
    AuthService authService;
    @Autowired
    MessageService messageService;
    @Autowired
    CmmMenuService cmmMenuService;
    @Autowired
    MobileDirectService mobileDirectService;

    /**
     * 로그인 후 URL페이지로 이동
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return String
     */
    @RequestMapping(value = "mobileDirectLogin.sb", method = {RequestMethod.POST, RequestMethod.GET})
    public String main(SessionInfoVO sessionInfoVO, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        LOGGER.info("mobileDirectLogin start");

        String returnUrl = "";
        String url = "";

        ResrceInfoVO resrceInfoVO = new ResrceInfoVO();
        MobileDirectVO mobileDirectVO = new MobileDirectVO();

//        String accessCd = URLDecoder.decode(request.getParameter("ACCESS_CD"), "UTF-8").replace("\n", "&#xa;");
        String accessCd = request.getParameter("ACCESS_CD");

        if (!isEmpty(request.getParameter("ACCESS_CD"))) {
            LOGGER.info("mobileDirectLogin ACCESS_CD : {} ", accessCd);

            mobileDirectVO.setAccessCd(accessCd);

            // 매장코드,구분URL,USER_ID
            String[] arrAccessCd = mobileDirectService.getAccess(mobileDirectVO, sessionInfoVO).split(",");
            System.out.println("값값 arrAccessCd : " + arrAccessCd[0] + " / " + arrAccessCd[1] + " / " + arrAccessCd[2]);

            sessionInfoVO.setLoginIp(getClientIp(request));
            sessionInfoVO.setBrwsrInfo(request.getHeader("User-Agent"));
            sessionInfoVO.setHwAuthKey("000");
            sessionInfoVO.setUserId(arrAccessCd[2]);
            sessionInfoVO.setStoreCd(arrAccessCd[0]);

            // 로그인 시도
            SessionInfoVO posSi = authService.posLogin(sessionInfoVO);

            // 세션 생성
            sessionService.setSessionInfo(sessionInfoVO);

            System.out.println("--- 세션 생성 됨 ---");
            System.out.println(sessionInfoVO.getStoreCd());

            // 최종교환권 번호
            if (arrAccessCd[1].equals("mobileVoucherNo")) {

                url = "/mobile/sale/status/mobileVoucherNo/view.sb";
                returnUrl = "mobile/sale/status/voucherNo/mobileDirectVoucherNo";
                String userId = arrAccessCd[2];
                model.addAttribute("userId", userId);

                /** 공지사항 페이지이동 권한체크 */
                resrceInfoVO.setDispLevel(2L);
                resrceInfoVO.setUrl(url);

                String boardAuth = Integer.toString(cmmMenuService.menuResrceChk(resrceInfoVO));
                LOGGER.info("posLogin boardAuth : {}", boardAuth);
                if(boardAuth.equals("0")) {
                    throw new AuthenticationException(messageService.get("cmm.access.denied"), url);
                }
            }
        }
        return returnUrl;
    }


    /**
     * 세션 생성
     *
     * @param sessionInfoVO
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.02
     */
    @RequestMapping(value = "getSessionInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSessionInfo(MobileDirectVO mobileDirectVO, SessionInfoVO sessionInfoVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        // 매장코드,구분URL,USER_ID
        String[] arrAccessCd = mobileDirectService.getAccess(mobileDirectVO, sessionInfoVO).split(",");
        System.out.println("값값 arrAccessCd : " + arrAccessCd[0] + " / " + arrAccessCd[1] + " / " + arrAccessCd[2]);

        sessionInfoVO.setLoginIp(getClientIp(request));
        sessionInfoVO.setBrwsrInfo(request.getHeader("User-Agent"));
        sessionInfoVO.setHwAuthKey("000");
        sessionInfoVO.setUserId(arrAccessCd[2]);
        sessionInfoVO.setStoreCd(arrAccessCd[0]);

        // 로그인 시도
        SessionInfoVO posSi = authService.posLogin(sessionInfoVO);

        // 세션 생성
        sessionService.setSessionInfo(sessionInfoVO);

        return returnJson(Status.OK);
    }

}
