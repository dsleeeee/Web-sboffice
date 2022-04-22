package kr.co.solbipos.store.manage.closeStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.rank.service.StoreRankVO;
import kr.co.solbipos.store.manage.closeStore.service.CloseStoreService;
import kr.co.solbipos.store.manage.closeStore.service.CloseStoreVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * @Class Name : CloseStoreController.java
 * @Description : 기초관리 > 매장정보관리 > 폐점예정매장
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.22  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.04.22
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/store/manage/closeStore")
public class CloseStoreController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** service */
    private final CloseStoreService closeStoreService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public CloseStoreController(CloseStoreService closeStoreService, SessionService sessionService) {
        this.closeStoreService = closeStoreService;
        this.sessionService = sessionService;
    }

    /**
     * 폐점예정매장 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2022.04.22
     */
    @RequestMapping(value = "/closeStore/view.sb", method = RequestMethod.GET)
    public String virtualLoginView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "store/manage/closeStore/closeStore";
    }

    /**
     * 폐점예정매장 - 조회
     * @param   request
     * @param   response
     * @param   closeStoreVO
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2022.04.22
     */

    @RequestMapping(value = "/closeStore/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCloseStoreList(HttpServletRequest request, HttpServletResponse response,
                                    CloseStoreVO closeStoreVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = closeStoreService.getCloseStoreList(closeStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, closeStoreVO);

    }

    /**
     * 폐점예정매장 - 매장폐점
     * @param closeStoreVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  권지현
     * @since   2022.04.20
     */
    @RequestMapping(value = "/closeStore/saveCloseStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCloseStore(@RequestBody CloseStoreVO[] closeStoreVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = closeStoreService.saveCloseStore(closeStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
