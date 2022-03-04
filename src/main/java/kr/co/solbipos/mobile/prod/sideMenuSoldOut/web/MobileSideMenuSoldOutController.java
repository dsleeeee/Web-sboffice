package kr.co.solbipos.mobile.prod.sideMenuSoldOut.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.prod.sideMenuSoldOut.service.MobileSideMenuSoldOutService;
import kr.co.solbipos.mobile.prod.sideMenuSoldOut.service.MobileSideMenuSoldOutVO;
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
 * @Class Name : MobileSideMenuSoldOutController.java
 * @Description : 상품관리 > 품절관리(선택메뉴)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.03  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.03
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/mobile/prod/sideMenuSoldOut")
public class MobileSideMenuSoldOutController {

    private final SessionService sessionService;
    private final MobileSideMenuSoldOutService mobileSideMenuSoldOutService;

    public MobileSideMenuSoldOutController(SessionService sessionService, MobileSideMenuSoldOutService mobileSideMenuSoldOutService) {
        this.sessionService = sessionService;
        this.mobileSideMenuSoldOutService = mobileSideMenuSoldOutService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileSideMenuSoldOut/list.sb", method = RequestMethod.GET)
    public String mobileSideMenuSoldOutView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "mobile/prod/sideMenuSoldOut/mobileSideMenuSoldOut";
    }

    /**
     * 선택그룹 조회
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.03.04
     */
    @RequestMapping(value = "/mobileSideMenuSoldOut/getMobileSideMenuGrpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileSideMenuGrpList(HttpServletRequest request, HttpServletResponse response, Model model, MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileSideMenuSoldOutService.getMobileSideMenuGrpList(mobileSideMenuSoldOutVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileSideMenuSoldOutVO);
    }

    /**
     * 선택분류 조회
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.03.04
     */
    @RequestMapping(value = "/mobileSideMenuSoldOut/getMobileSideMenuClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileSideMenuClassList(HttpServletRequest request, HttpServletResponse response, Model model, MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileSideMenuSoldOutService.getMobileSideMenuClassList(mobileSideMenuSoldOutVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileSideMenuSoldOutVO);
    }

    /**
     * 선택그룹 조회
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.03.04
     */
    @RequestMapping(value = "/mobileSideMenuSoldOut/getMobileSideMenuProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileSideMenuProdList(HttpServletRequest request, HttpServletResponse response, Model model, MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileSideMenuSoldOutService.getMobileSideMenuProdList(mobileSideMenuSoldOutVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileSideMenuSoldOutVO);
    }

    /**
     * 선택메뉴 품절관리 - 품절여부 저장
     * @param mobileSideMenuSoldOutVOs MobileSideMenuSoldOutVO[]
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/mobileSideMenuSoldOut/getMobileSideMenuSoldOutSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileSideMenuSoldOutSave(@RequestBody MobileSideMenuSoldOutVO[] mobileSideMenuSoldOutVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = mobileSideMenuSoldOutService.getMobileSideMenuSoldOutSave(mobileSideMenuSoldOutVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
