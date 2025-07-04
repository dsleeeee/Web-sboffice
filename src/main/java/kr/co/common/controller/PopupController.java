package kr.co.common.controller;

import kr.co.common.data.domain.AgencyVO;
import kr.co.common.data.domain.HqOfficeVO;
import kr.co.common.data.domain.VanVO;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.popup.PopupService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : PopupController.java
 * @Description : 공통 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.24  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.24
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/popup")
public class PopupController {

    /** service */
    private final SessionService sessionService;
    private final PopupService popupService;

    /** Constructor Injection */
    @Autowired
    public PopupController(SessionService sessionService, PopupService popupService) {
        this.sessionService = sessionService;
        this.popupService = popupService;
    }

    /**
     * 벤사 목록 조회
     * @param vanVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 김지은
     * @since 2018.06.08
     */
    @RequestMapping(value = "/getVanList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVanList(VanVO vanVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = popupService.getVanList(vanVO);

        return returnListJson(Status.OK, list, vanVO);
    }

    /**
     * 대리점 목록 조회
     * @param agencyVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 김지은
     * @since 2018.06.08
     */
    @RequestMapping(value = "/getAgencyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgencyList(AgencyVO agencyVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = popupService.getAgencyList(agencyVO, sessionInfoVO);

        return returnListJson(Status.OK, list, agencyVO);
    }

    /**
     * [본사 선택 팝업] 본사 목록 조회
     * @param hqOfficeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 김지은
     * @since 2018.11.01
     */
    @RequestMapping(value = "/getHqList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqList(HqOfficeVO hqOfficeVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = popupService.getHqList(hqOfficeVO, sessionInfoVO);

        return returnListJson(Status.OK, list, hqOfficeVO);
    }

    /**
     * [매장 선택 팝업] 해당 세션의 매장 목록 조회
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 김지은
     * @since 2018.11.14
     */
    @RequestMapping(value = "/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(StoreManageVO storeManageVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = popupService.getStoreList(storeManageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeManageVO);
    }

    /**
     * [매장 선택 팝업] 매장 목록 조회 (가맹점 로직 추가)
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 권지현
     * @since 2021.08.03
     */
    @RequestMapping(value = "/getSearchStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchStoreList(StoreManageVO storeManageVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = popupService.getSearchStoreList(storeManageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeManageVO);
    }

    /**
     * 상품정보 분류 트리 조회
     *
     * @param prodVO ProdVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return
     * @author 노현수
     * @since 2018.11.12
     */
    @RequestMapping(value = "/getProdClassTree.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassTree(ProdVO prodVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<ProductClassVO> result = popupService.getProdClassTree(prodVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 상품정보 분류 플랫 조회
     *
     * @param prodVO ProdVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return
     * @author 노현수
     * @since 2018.11.12
     */
    @RequestMapping(value = "/getProdClassCdNm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassCdNm(ProdVO prodVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String result = popupService.getProdClassCdNm(prodVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }


    /**
     * 상품 찾기 팝업에서 상품 목록 조회
     *
     * @param prodVO ProdVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return
     * @author 김지은
     * @since 2018.12.21
     */
    @RequestMapping(value = "/getProductList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProductList(ProdVO prodVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = popupService.getProductList(prodVO, sessionInfoVO);

        return returnListJson(Status.OK, result, prodVO);
    }


    /**
     * [본사 + 매장 선택 팝업] 해당 세션의 본사 + 매장 목록 조회
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2021.01.11
     */
    @RequestMapping(value = "/getHqStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqStoreList(StoreManageVO storeManageVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = popupService.getHqStoreList(storeManageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeManageVO);
    }

}
