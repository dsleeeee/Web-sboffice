package kr.co.solbipos.store.manage.virtuallogin.web;

import static kr.co.common.utils.HttpUtils.getClientIp;
import static kr.co.common.utils.spring.StringUtil.convertToJson;
import static kr.co.common.utils.spring.StringUtil.generateUUID;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.SessionUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.enums.LoginOrigin;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.LoginHistVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginService;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : VirtualLoginController.java
 * @Description : 가맹점관리 > 매장관리 > 가상 로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Slf4j
@Controller
@RequestMapping(value = "/store/manage/virtualLogin")
public class VirtualLoginController {

    /** service */
    @Autowired
    VirtualLoginService virtualLoginService;
    @Autowired
    SessionService sessionService;
    @Autowired
    AuthService authService;
    @Autowired
    CmmMenuService cmmMenuService;

    /**
     * 가상로그인 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  노현수
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/virtualLogin/view.sb", method = RequestMethod.GET)
    public String virtualLoginView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "store/manage/virtualLogin/virtualLogin";
    }

    /**
     * 가상로그인 - 조회
     * @param   request
     * @param   response
     * @param   virtualLoginVO
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 08.
     */
    
    @RequestMapping(value = "/virtualLogin/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVirtualLoginList(HttpServletRequest request, HttpServletResponse response,
            VirtualLoginVO virtualLoginVO, Model model) {

        List<DefaultMap<String>> list = virtualLoginService.getVirtualLoginList(virtualLoginVO);

        return ReturnUtil.returnListJson(Status.OK, list, virtualLoginVO);

    }

    /**
     * 가상로그인 - 로그인 수행
     * @param   request
     * @param   response
     * @param   redirectAttributes
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/virtualLogin/vLogin.sb", method = RequestMethod.POST)
    public String vLogin(HttpServletRequest request, HttpServletResponse response, VirtualLoginVO virtualLoginVO, RedirectAttributes redirectAttributes
            ) {
        
        String returnUrl = "/main.sb";
        
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        // 권한조회
        int authResult = virtualLoginService.checkVirtualLoginAuth(sessionInfoVO.getUserId());
        
        if ( authResult > 0 ) {
            
            BaseEnv.VIRTUAL_LOGIN_ID = virtualLoginVO.getVUserId();
            
            StopWatch sw = new StopWatch();
            sw.start();
            log.info("가상로그인 시작 : {} ", sessionInfoVO.getUserId());
            
            sessionInfoVO = new SessionInfoVO();
            sessionInfoVO.setLoginResult(LoginResult.SUCCESS);
            sessionInfoVO.setLoginIp(getClientIp(request));
            sessionInfoVO.setBrwsrInfo(request.getHeader("User-Agent"));
            // 사용자ID를 가상로그인ID로 설정
            sessionInfoVO.setUserId(BaseEnv.VIRTUAL_LOGIN_ID);
            sessionInfoVO.setVUserId(BaseEnv.VIRTUAL_LOGIN_ID);
            // userId 로 사용자 조회
            sessionInfoVO = authService.selectWebUser(sessionInfoVO);
            // sessionId 세팅
            sessionInfoVO.setSessionId( generateUUID() );
            // 권한 있는 메뉴 저장
            sessionInfoVO.setAuthMenu( authService.selectAuthMenu( sessionInfoVO ) );
            // 고정 메뉴 리스트 저장
            sessionInfoVO.setFixMenu( cmmMenuService.selectFixingMenu( sessionInfoVO ) );
            // 즐겨찾기 메뉴 리스트 저장
            sessionInfoVO.setBkmkMenu( cmmMenuService.selectBkmkMenu( sessionInfoVO ) );
            // 전체메뉴 조회(리스트)
            sessionInfoVO.setMenuData( convertToJson( cmmMenuService.makeMenu( sessionInfoVO, "A" ) ) );
            // 즐겨찾기메뉴 조회 (리스트)
            sessionInfoVO.setBkmkData( convertToJson( cmmMenuService.makeMenu( sessionInfoVO, "F" ) ) );
            // 고정 메뉴 조회 (리스트)
            sessionInfoVO.setFixData( convertToJson(sessionInfoVO.getFixMenu()) );
            // 본사는 소속된 가맹점을 세션에 저장
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                List<String> storeCdList =
                        cmmMenuService.selectStoreCdList( sessionInfoVO.getOrgnCd() );
                sessionInfoVO.setArrStoreCdList( storeCdList );
            }
            // 세선유틸에 담아두기
            SessionUtil.setEnv(request.getSession(), BaseEnv.VIRTUAL_LOGIN_ID, sessionInfoVO);
            
            sw.stop();
            log.info("가상로그인 성공 처리 시간 : {}", sw.getTotalTimeSeconds());
            
            // 로그인이력 생성
            virtualLoginService.insertLoginHistory(sessionInfoVO);
            
            redirectAttributes.addAttribute("vLoginId", BaseEnv.VIRTUAL_LOGIN_ID);
            return "redirect:" + returnUrl;
            
        } else {
            try {
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('가상로그인 권한이 없습니다.'); window.close();</script>");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return null;
        
    }
    
    /**
     * 가상로그인 - 로그인 종료
     * @param   request
     * @param   response
     * @param   redirectAttributes
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/virtualLogin/vLogout.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result vLogout(HttpServletRequest request, HttpServletResponse response, VirtualLoginVO virtualLoginVO) {
        
        // 세선유틸에 담겨진 가상로그인 정보 삭제
        SessionUtil.removeEnv(request.getSession(), virtualLoginVO.getVUserId());
        
        return ReturnUtil.returnJson(Status.OK);
        
    }

}
