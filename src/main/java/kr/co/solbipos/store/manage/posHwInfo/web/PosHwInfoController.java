package kr.co.solbipos.store.manage.posHwInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.posHwInfo.service.PosHwInfoService;
import kr.co.solbipos.store.manage.posHwInfo.service.PosHwInfoVO;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginService;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginVO;
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
import java.util.List;

/**
 * @Class Name : PosHwInfoController.java
 * @Description : 기초관리 > 매장정보관리 > 포스 H/W정보 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.07.13
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/store/manage/posHwInfo")
public class PosHwInfoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** service */
    private final PosHwInfoService posHwInfoService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public PosHwInfoController(PosHwInfoService posHwInfoService, SessionService sessionService) {
        this.posHwInfoService = posHwInfoService;
        this.sessionService = sessionService;
    }

    /**
     * 가상로그인 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2021.07.13
     */
    @RequestMapping(value = "/posHwInfo/view.sb", method = RequestMethod.GET)
    public String virtualLoginView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "store/manage/posHwInfo/posHwInfo";
    }

    /**
     * 가상로그인 - 조회
     * @param   request
     * @param   response
     * @param   posHwInfoVO
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2021.07.13
     */

    @RequestMapping(value = "/posHwInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVirtualLoginList(HttpServletRequest request, HttpServletResponse response,
                                      PosHwInfoVO posHwInfoVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = posHwInfoService.getPosHwInfo(posHwInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, posHwInfoVO);

    }

}
