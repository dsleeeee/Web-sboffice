package kr.co.solbipos.store.manage.virtuallogin.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.SessionUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginService;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static kr.co.common.utils.HttpUtils.getClientIp;
import static kr.co.common.utils.spring.StringUtil.generateUUID;

/**
 * @Class Name : VirtualLoginController.java
 * @Description : 가맹점관리 > 매장관리 > 가상 로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/store/manage/virtualLogin")
public class VirtualLoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** service */
    private final VirtualLoginService virtualLoginService;
    private final SessionService sessionService;
    private final AuthService authService;
    private final CmmMenuService cmmMenuService;

    /** Constructor Injection */
    @Autowired
    public VirtualLoginController(VirtualLoginService virtualLoginService,
        SessionService sessionService, AuthService authService, CmmMenuService cmmMenuService) {
        this.virtualLoginService = virtualLoginService;
        this.sessionService = sessionService;
        this.authService = authService;
        this.cmmMenuService = cmmMenuService;
    }

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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = virtualLoginService.getVirtualLoginList(virtualLoginVO, sessionInfoVO);

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
        // 기존 세션 조회
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        // 기존세션 이용하여 권한조회
        int authResult = virtualLoginService.checkVirtualLoginAuth(sessionInfoVO.getUserId());

        virtualLoginVO.setUserIdCkeck(virtualLoginVO.getvUserId());
        virtualLoginVO.setvUserIdCkeck(sessionInfoVO.getUserId());
        int authResultCheck = virtualLoginService.checkVirtualLoginAuthCheck(virtualLoginVO, sessionInfoVO);

        if(authResultCheck <= 0)
        {
            try {
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('가상로그인 권한이 없습니다..'); window.close();</script>");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 권한 있는 경우 가상로그인 진행, 가상로그인 시 신규세션ID 생성하여 redis 에 저장한다 : 20180918 노현수
        if ( authResult > 0 ) {
            String vGetUserId = sessionInfoVO.getUserId();
            BaseEnv.VIRTUAL_LOGIN_ID = virtualLoginVO.getvUserId();

            StopWatch sw = new StopWatch();
            sw.start();
            LOGGER.info("가상로그인 시작 : {} sessionInfoVO.getUserId(): ", sessionInfoVO.getUserId()+", BaseEnv.VIRTUAL_LOGIN_ID:"+BaseEnv.VIRTUAL_LOGIN_ID);
            // 신규 세션 생성을 위해 VO 재사용
            sessionInfoVO = new SessionInfoVO();
            sessionInfoVO.setLoginIp(getClientIp(request));
            sessionInfoVO.setBrwsrInfo(request.getHeader("User-Agent"));
            // 사용자ID를 가상로그인ID로 설정
            sessionInfoVO.setUserId(BaseEnv.VIRTUAL_LOGIN_ID);
            // 가상로그인 아이디는 현재 아이디가 되어야함
            sessionInfoVO.setvUserId(vGetUserId);
            // userId 로 사용자 조회 ( sessionInfoVO 값 Override 주의 )
            sessionInfoVO = authService.selectWebUser(sessionInfoVO);
            // 가상로그인은 로그인 상태 정상으로 판단
            sessionInfoVO.setLoginResult(LoginResult.SUCCESS);
            // sessionId 세팅
            sessionInfoVO.setSessionId( generateUUID() );
            // 사용자의 메뉴 리스트 Set : 권한포함
            sessionInfoVO.setMenuData(cmmMenuService.getUserMenuList(sessionInfoVO));
            // 즐겨찾기 메뉴 리스트 Set
            sessionInfoVO.setBkmkMenuData(cmmMenuService.getBkmkMenuList(sessionInfoVO));
            // 고정 메뉴 리스트 Set
            sessionInfoVO.setFixedMenuData(cmmMenuService.getFixedMenuList(sessionInfoVO));

//             로고이미지 구분(파일여부 체크)
            String path = BaseEnv.FILE_UPLOAD_DIR + "logo_img/";
//            String path = "D:\\" + "logo_img/";

            File file1 = new File(path + sessionInfoVO.getHqOfficeCd() + ".PNG");
            File file2 = new File(path + sessionInfoVO.getHqOfficeCd() + ".JPG");

            boolean isExists1 = file1.exists();
            boolean isExists2 = file2.exists();

            if(isExists1 ||isExists2) {
                sessionInfoVO.setLogoImg("Y");
            } else {
                sessionInfoVO.setLogoImg("N");
            }

            // 본사는 소속된 가맹점을 세션에 저장
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                List<String> storeCdList =
                        cmmMenuService.getStoreCdList( sessionInfoVO.getOrgnCd() );
                sessionInfoVO.setArrStoreCdList( storeCdList );
            }
            // 레디스에 세션 Set
            sessionService.setSessionInfo(sessionInfoVO);
            // 세션 담기
            SessionUtil.setEnv(request.getSession(), sessionInfoVO.getSessionId(), sessionInfoVO);
            sw.stop();
            LOGGER.info("가상로그인 성공 처리 시간 : {}", sw.getTotalTimeSeconds());

            // 로그인이력 생성
            virtualLoginService.insertLoginHistory(sessionInfoVO);

            redirectAttributes.addAttribute("sid", sessionInfoVO.getSessionId());
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
     *
     * @param   request HttpServletRequest
     * @param   response HttpServletResponse
     * @param   virtualLoginVO VirtualLoginVO
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/virtualLogin/vLogout.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result vLogout(HttpServletRequest request, HttpServletResponse response, VirtualLoginVO virtualLoginVO) {
        // 레디스에 담겨진 가상로그인 정보'만' 삭제
        sessionService.deleteSessionInfo(request.getParameter("sid"));
        return ReturnUtil.returnJson(Status.OK);
    }

}
