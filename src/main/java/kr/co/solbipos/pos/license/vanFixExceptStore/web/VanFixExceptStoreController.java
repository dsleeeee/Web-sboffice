package kr.co.solbipos.pos.license.vanFixExceptStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.license.vanFixExceptStore.service.VanFixExceptStoreService;
import kr.co.solbipos.pos.license.vanFixExceptStore.service.VanFixExceptStoreVO;
import kr.co.solbipos.store.manage.closeStore.service.CloseStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : VanFixExceptStoreController.java
 * @Description : 포스관리 > 라이선스 관리 > VAN사 변경허용 매장관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.09  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.04.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/pos/license/vanFixExceptStore")
public class VanFixExceptStoreController {

    /** service */
    private final VanFixExceptStoreService vanFixExceptStoreService;
    private final SessionService sessionService;

    @Autowired
    public VanFixExceptStoreController(VanFixExceptStoreService vanFixExceptStoreService, SessionService sessionService) {
        this.vanFixExceptStoreService = vanFixExceptStoreService;
        this.sessionService = sessionService;
    }

    /**
     * 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.04.09
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response,
                                   Model model) {

        model.addAttribute("vanComboList", convertToJson(vanFixExceptStoreService.getVanComboList()));

        return "pos/license/vanFixExceptStore/vanFixExceptStore";
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   vanFixExceptStoreVO
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2024.04.09
     */

    @RequestMapping(value = "/vanFixExceptStore/getVanFixExceptStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVanFixExceptStore(HttpServletRequest request, HttpServletResponse response,
                                    VanFixExceptStoreVO vanFixExceptStoreVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = vanFixExceptStoreService.getVanFixExceptStore(vanFixExceptStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vanFixExceptStoreVO);
    }

    /**
     * 매장 조회
     * @param   request
     * @param   response
     * @param   vanFixExceptStoreVO
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2024.04.09
     */
    @RequestMapping(value = "/vanFixExceptStore/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(HttpServletRequest request, HttpServletResponse response,
                               VanFixExceptStoreVO vanFixExceptStoreVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = vanFixExceptStoreService.getStoreList(vanFixExceptStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vanFixExceptStoreVO);
    }

    /**
     * 변경허용매장 등록
     * @param vanFixExceptStoreVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  김유승
     * @since   2024.04.09
     */
    @RequestMapping(value = "/vanFixExceptStore/saveFixExceptStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveFixExceptStore(@RequestBody VanFixExceptStoreVO[] vanFixExceptStoreVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vanFixExceptStoreService.saveFixExceptStore(vanFixExceptStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 변경허용매장 삭제
     * @param vanFixExceptStoreVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  김유승
     * @since   2024.04.09
     */
    @RequestMapping(value = "/vanFixExceptStore/deleteFixExceptStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteFixExceptStore(@RequestBody VanFixExceptStoreVO[] vanFixExceptStoreVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vanFixExceptStoreService.deleteFixExceptStore(vanFixExceptStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}