package kr.co.solbipos.base.prod.menuRank.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.corner.service.CornerVO;
import kr.co.solbipos.base.prod.menuRank.service.MenuRankService;
import kr.co.solbipos.base.prod.menuRank.service.MenuRankVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
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
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : MenuRankController.java
 * @Description : 기초관리 - 상품관리 - 메뉴 순위 표시 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.06  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/menuRank")
public class MenuRankController {

    private final SessionService sessionService;
    private final MenuRankService menuRankService;

    @Autowired
    public MenuRankController(SessionService sessionService, MenuRankService menuRankService) {

        this.sessionService = sessionService;
        this.menuRankService = menuRankService;
    }

    /**
     * 메뉴 순위 표시 관리 메인화면
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2020.08.06
     * @return
     */
    @RequestMapping(value = "/display/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "base/prod/menuRank/menuRankDisplay";
    }

    /**
     *  메뉴 순위 표시 관리 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2020.08.06
     * @return
     */
    @RequestMapping(value = "/display/getRankInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRankInfo(MenuRankVO menuRankVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = menuRankService.getRankInfo(menuRankVO, sessionInfoVO);

        return returnListJson(Status.OK, list, menuRankVO);
    }

    /**
     *  메뉴 순위 표시 사용/미사용 매장 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2020.08.06
     * @return
     */
    @RequestMapping(value = "/display/getRegStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRegStore(MenuRankVO menuRankVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = menuRankService.getRegStore(menuRankVO, sessionInfoVO);

        return returnListJson(Status.OK, list, menuRankVO);
    }

    /**
     * 메뉴 순위 표시 미사용 처리
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2020.08.06
     * @return
     */
    @RequestMapping(value = "/display/deleteStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteStore(@RequestBody MenuRankVO[] menuRanks, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = menuRankService.deleteStore(menuRanks, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 메뉴 순위 표시 사용 처리
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2020.08.06
     * @return
     */
    @RequestMapping(value = "/display/insertStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result insertStore(@RequestBody MenuRankVO[] menuRanks, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = menuRankService.insertStore(menuRanks, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 메뉴 순위 표시 관리 저장
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2020.08.06
     * @return
     */
    @RequestMapping(value = "/display/saveRankUse.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRankUse(@RequestBody MenuRankVO[] menuRanks, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = menuRankService.saveRankUse(menuRanks, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
