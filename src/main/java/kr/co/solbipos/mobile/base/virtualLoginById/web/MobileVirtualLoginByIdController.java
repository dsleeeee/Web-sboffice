package kr.co.solbipos.mobile.base.virtualLoginById.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.base.virtualLoginById.service.MobileVirtualLoginByIdService;
import kr.co.solbipos.mobile.base.virtualLoginById.service.MobileVirtualLoginByIdVO;
import kr.co.solbipos.store.manage.virtualLoginById.service.VirtualLoginByIdVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : MobileVirtualLoginByIdController.java
 * @Description : 기초관리_모바일 > 가상로그인(아이디별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.10  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/mobile/base/virtualLoginById")
public class MobileVirtualLoginByIdController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MobileVirtualLoginByIdService mobileVirtualLoginByIdService;
    private final SessionService sessionService;

    public MobileVirtualLoginByIdController(MobileVirtualLoginByIdService mobileVirtualLoginByIdService, SessionService sessionService) {

        this.mobileVirtualLoginByIdService = mobileVirtualLoginByIdService;
        this.sessionService = sessionService;
    }

    /**
     * 페이지 이동
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  이다솜
     * @since   2025. 07. 10
     */
    @RequestMapping(value = "/mobileVirtualLoginById/view.sb", method = RequestMethod.GET)
        public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "mobile/base/virtualLoginById/mobileVirtualLoginById";
    }

    /**
     * 가상로그인(아이디별) - 조회
     * @param request
     * @param response
     * @param mobileVirtualLoginByIdVO
     * @param model
     * @return
     * @author  이다솜
     * @since   2025. 07. 10
     */
    @RequestMapping(value = "/mobileVirtualLoginById/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileVirtualLoginByIdList(HttpServletRequest request, HttpServletResponse response,
                                                MobileVirtualLoginByIdVO mobileVirtualLoginByIdVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = mobileVirtualLoginByIdService.getMobileVirtualLoginByIdList(mobileVirtualLoginByIdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileVirtualLoginByIdVO);

    }

}
