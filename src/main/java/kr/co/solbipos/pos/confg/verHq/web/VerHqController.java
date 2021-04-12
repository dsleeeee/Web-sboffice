package kr.co.solbipos.pos.confg.verHq.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.vermanage.service.ApplcStoreVO;
import kr.co.solbipos.pos.confg.vermanage.service.VerInfoVO;
import kr.co.solbipos.pos.confg.vermanage.service.VerManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
* @Class Name : VerHqController.java
* @Description : 기초관리 > 매장관리 > 버전관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2021.04.06  권지현      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 권지현
* @since 2021. 04.06
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/pos/confg/verHq")
public class VerHqController {

    private final VerManageService verManageService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public VerHqController(VerManageService verManageService, SessionService sessionService) {
        this.verManageService = verManageService;
        this.sessionService = sessionService;
    }

    /**
     * 버전관리 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "pos/confg/varhq/verHq";
    }

}
