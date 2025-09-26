package kr.co.solbipos.base.multilingual.storeKioskSideOption.web;


import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.multilingual.kioskSideOption.service.KioskSideOptionVO;
import kr.co.solbipos.base.multilingual.storeKioskSideOption.service.StoreKioskSideOptionService;
import kr.co.solbipos.base.multilingual.storeKioskSideOption.service.StoreKioskSideOptionVO;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapService;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreKioskSideOptionController.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(키오스크/사이드/옵션)(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.19  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.09.19
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/multilingual/storeKioskSideOption")
public class StoreKioskSideOptionController {

    private final SessionService sessionService;
    private final StoreKioskSideOptionService storeKioskSideOptionService;
    private final KioskKeyMapService kioskKeyMapService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreKioskSideOptionController(SessionService sessionService, StoreKioskSideOptionService storeKioskSideOptionService, KioskKeyMapService kioskKeyMapService, CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.storeKioskSideOptionService = storeKioskSideOptionService;
        this.kioskKeyMapService = kioskKeyMapService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2025.09.19
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
        StoreKioskSideOptionVO storeKioskSideOptionVO = new StoreKioskSideOptionVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 키오스크용 포스 조회
        List<DefaultMap<String>> kioskPosList = kioskKeyMapService.getKioskPosList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskPosList", convertToJson(kioskPosList));

        // 키오스크 포스 조회(중분류 사용 포스만 조회)
        List<DefaultMap<String>> kioskPos2List = storeKioskSideOptionService.getKioskPosComboList(storeKioskSideOptionVO, sessionInfoVO);
        model.addAttribute("kioskPos2List", convertToJson(kioskPos2List));

        return "base/multilingual/storeKioskSideOption/storeKioskSideOptionTab";
    }

    /**
     *  키오스크(카테고리명) 탭 리스트 조회(매장)
     *
     * @param storeKioskSideOptionVO
     * @param request
     * @author  이다솜
     * @since   2025. 09. 19.
     */
    @RequestMapping(value = "/getStoreKioskCategoryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreKioskCategoryList(StoreKioskSideOptionVO storeKioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeKioskSideOptionService.getStoreKioskCategoryList(storeKioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeKioskSideOptionVO);
    }

    /**
     * 키오스크(카테고리명) 영문, 중문, 일문 저장(매장)
     * @param storeKioskSideOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2025. 09. 19.
     */
    @RequestMapping(value = "/saveStoreKioskCategory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreKioskCategory(@RequestBody StoreKioskSideOptionVO[] storeKioskSideOptionVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeKioskSideOptionService.saveStoreKioskCategory(storeKioskSideOptionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 키오스크중분류(카테고리명) 카테고리(대분류) 콤보박스 조회(매장)
     * @param storeKioskSideOptionVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2025. 09. 19.
     */
    @RequestMapping(value = "/getStoreKioskCategoryComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreKioskCategoryComboList(StoreKioskSideOptionVO storeKioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeKioskSideOptionService.getStoreKioskCategoryComboList(storeKioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeKioskSideOptionVO);
    }

    /**
     * 키오스크중분류(카테고리명) 탭 리스트 조회(매장)
     * @param storeKioskSideOptionVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/getStoreKioskMClsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreKioskMClsList(StoreKioskSideOptionVO storeKioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeKioskSideOptionService.getStoreKioskMClsList(storeKioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeKioskSideOptionVO);
    }

    /**
     * 키오스크중분류(카테고리명) 영문, 중문, 일문 저장(매장)
     * @param storeKioskSideOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/saveStoreKioskMCls.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreKioskMCls(@RequestBody StoreKioskSideOptionVO[] storeKioskSideOptionVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeKioskSideOptionService.saveStoreKioskMCls(storeKioskSideOptionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     *  사이드(선택그룹명) 탭 리스트 조회(매장)
     *
     * @param storeKioskSideOptionVO
     * @param request
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/getStoreSideSdselGrpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSideSdselGrpList(StoreKioskSideOptionVO storeKioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeKioskSideOptionService.getStoreSideSdselGrpList(storeKioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeKioskSideOptionVO);
    }

    /**
     * 사이드(선택그룹명) 영문, 중문, 일문 저장(매장)
     * @param storeKioskSideOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/saveStoreSideSdselGrp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreSideSdselGrp(@RequestBody StoreKioskSideOptionVO[] storeKioskSideOptionVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeKioskSideOptionService.saveStoreSideSdselGrp(storeKioskSideOptionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     *  사이드(선택분류명) 탭 리스트 조회(매장)
     *
     * @param storeKioskSideOptionVO
     * @param request
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/getStoreSideSdselClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSideSdselClassList(StoreKioskSideOptionVO storeKioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeKioskSideOptionService.getStoreSideSdselClassList(storeKioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeKioskSideOptionVO);
    }

    /**
     * 사이드(선택분류명) 영문, 중문, 일문 저장(매장)
     * @param storeKioskSideOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/saveStoreSideSdselClass.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreSideSdselClass(@RequestBody StoreKioskSideOptionVO[] storeKioskSideOptionVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeKioskSideOptionService.saveStoreSideSdselClass(storeKioskSideOptionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     *  옵션(그룹명) 탭 리스트 조회(매장)
     *
     * @param storeKioskSideOptionVO
     * @param request
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/getStoreOptionGrpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOptionGrpList(StoreKioskSideOptionVO storeKioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeKioskSideOptionService.getStoreOptionGrpList(storeKioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeKioskSideOptionVO);
    }

    /**
     * 옵션(그룹명) 영문, 중문, 일문 저장(매장)
     * @param storeKioskSideOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/saveStoreOptionGrp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreOptionGrp(@RequestBody StoreKioskSideOptionVO[] storeKioskSideOptionVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeKioskSideOptionService.saveStoreOptionGrp(storeKioskSideOptionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     *  옵션(옵션명) 탭 리스트 조회(매장)
     *
     * @param storeKioskSideOptionVO
     * @param request
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/getStoreOptionValList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOptionValList(StoreKioskSideOptionVO storeKioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeKioskSideOptionService.getStoreOptionValList(storeKioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeKioskSideOptionVO);
    }

    /**
     * 옵션(옵션명) 영문, 중문, 일문 저장(매장)
     * @param storeKioskSideOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/saveStoreOptionVal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreOptionVal(@RequestBody StoreKioskSideOptionVO[] storeKioskSideOptionVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeKioskSideOptionService.saveStoreOptionVal(storeKioskSideOptionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
