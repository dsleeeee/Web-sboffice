package kr.co.solbipos.base.store.storeType.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
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
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreTypeController.java
 * @Description : 기초관리 - 매장관리 - 매장타입관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.28  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.06.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/store/storeType")
public class StoreTypeController {

    private final SessionService sessionService;
    private final StoreTypeService storeTypeService;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreTypeController(SessionService sessionService, StoreTypeService storeTypeService, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.storeTypeService = storeTypeService;
        this.dayProdService = dayProdService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    private HttpServletRequest request;

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 06. 28.
     */
    @RequestMapping(value = "/storeType/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("brandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        // 매장타입조회(콤보박스용)
        model.addAttribute("storeTypeList", convertToJson(storeTypeService.getStoreTypeCombo(storeTypeVO, sessionInfoVO)));

        // 메뉴그룹조회(콤보박스용)
        model.addAttribute("storeGroupList", convertToJson(storeTypeService.getStoreGroupCombo(storeTypeVO, sessionInfoVO)));

        // 매장타입자동적용(1106)
        model.addAttribute("storeTypeAutoEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1106"), "0"));

        // 매장타입판매가설정(1107)
        model.addAttribute("storeTypeApplyEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1107"), "0"));

        // 내점/배달/포장가격관리(0044)
        model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));

        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        return "base/store/storeType/storeTypeTab";
    }

    /**
     * 매장타입관리 - 매장타입조회
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 06. 28.
     */
    @RequestMapping(value = "/storeType/getStoreType.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreType(StoreTypeVO storeTypeVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getStoreType(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 매장타입관리 - 매장타입저장
     * @param storeTypeVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 06. 28.
     */
    @RequestMapping(value = "/storeType/saveStoreType.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreType(@RequestBody StoreTypeVO[] storeTypeVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeTypeService.saveStoreType(storeTypeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장타입관리 - 매장연결조회
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 06. 29.
     */
    @RequestMapping(value = "/storeType/getStoreMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreMapping(StoreTypeVO storeTypeVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getStoreMapping(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 매장타입관리 - 매장연결삭제
     * @param storeTypeVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 06. 29.
     */
    @RequestMapping(value = "/storeType/deleteStoreMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteStoreMapping(@RequestBody StoreTypeVO[] storeTypeVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeTypeService.deleteStoreMapping(storeTypeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장타입관리 - 매장조회
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 06. 29.
     */
    @RequestMapping(value = "/storeType/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(StoreTypeVO storeTypeVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getStoreList(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 매장타입관리 - 매장연결등록
     * @param storeTypeVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 06. 29.
     */
    @RequestMapping(value = "/storeType/saveStoreMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreMapping(@RequestBody StoreTypeVO[] storeTypeVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeTypeService.saveStoreMapping(storeTypeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장타입관리 - 메뉴그룹연결조회
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 06. 29.
     */
    @RequestMapping(value = "/storeType/getMenuGroupMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuGroupMapping(StoreTypeVO storeTypeVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getMenuGroupMapping(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 매장타입관리 - 메뉴그룹연결삭제
     * @param storeTypeVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 06. 29.
     */
    @RequestMapping(value = "/storeType/deleteMenuGroupMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteMenuGroupMapping(@RequestBody StoreTypeVO[] storeTypeVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeTypeService.deleteMenuGroupMapping(storeTypeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장타입관리 - 메뉴그룹조회
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 06. 29.
     */
    @RequestMapping(value = "/storeType/getMenuGroupList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuGroupList(StoreTypeVO storeTypeVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getMenuGroupList(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 매장타입관리 - 메뉴그룹연결등록
     * @param storeTypeVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 06. 29.
     */
    @RequestMapping(value = "/storeType/saveMenuGroupMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMenuGroupMapping(@RequestBody StoreTypeVO[] storeTypeVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeTypeService.saveMenuGroupMapping(storeTypeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 메뉴그룹관리 - 메뉴그룹조회
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 01.
     */
    @RequestMapping(value = "/storeType/getMenuGroup.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuGroup(StoreTypeVO storeTypeVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getMenuGroup(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 메뉴그룹관리 - 메뉴그룹저장
     * @param storeTypeVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 01.
     */
    @RequestMapping(value = "/storeType/saveMenuGroup.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMenuGroup(@RequestBody StoreTypeVO[] storeTypeVOs, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeTypeService.saveMenuGroup(storeTypeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 메뉴그룹관리 - 상품연결조회
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 01.
     */
    @RequestMapping(value = "/storeType/getProdMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdMapping(StoreTypeVO storeTypeVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getProdMapping(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 메뉴그룹관리 - 상품조회
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 01.
     */
    @RequestMapping(value = "/storeType/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(StoreTypeVO storeTypeVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getProdList(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 메뉴그룹관리 - 상품연결저장
     * @param storeTypeVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 01.
     */
    @RequestMapping(value = "/storeType/saveProdMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProdMapping(@RequestBody StoreTypeVO[] storeTypeVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeTypeService.saveProdMapping(storeTypeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장타입관리 - 매장타입 매장적용 매장타입조회(콤보박스용)
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 05.
     */
    @RequestMapping(value = "/storeType/getStoreTypeCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreTypeCombo(StoreTypeVO storeTypeVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getStoreTypeCombo(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeTypeVO);
    }

    /**
     * 매장타입관리 - 매장타입 매장적용 팝업 매장리스트 조회
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 01.
     */
    @RequestMapping(value = "/storeType/getStoreTypeApplyStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreTypeApplyStoreList(StoreTypeVO storeTypeVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeTypeService.getStoreTypeApplyStoreList(storeTypeVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeTypeVO);
    }

    /**
     * 매장타입관리 - 매장타입 매장적용 팝업 매장적용(매장타입적용관리 테이블에 등록)
     * @param storeTypeVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 05.
     */
    @RequestMapping(value = "/storeType/saveStoreTypeApplyStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreTypeApplyStore(@RequestBody StoreTypeVO[] storeTypeVOs, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeTypeService.saveStoreTypeApplyStore(storeTypeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장타입관리 - 매장타입 매장적용 메뉴그룹조회(콤보박스용)
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 08.
     */
    @RequestMapping(value = "/storeType/getStoreGroupCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreGroupCombo(StoreTypeVO storeTypeVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getStoreGroupCombo(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeTypeVO);
    }

    /**
     * 매장타입관리 - 매장타입변경이력조회
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 11. 19.
     */
    @RequestMapping(value = "/storeType/getStoreTypeChgHist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreTypeChgHist(StoreTypeVO storeTypeVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getStoreTypeChgHist(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 매장타입관리 - 메뉴그룹변경이력조회
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 11. 19.
     */
    @RequestMapping(value = "/storeType/getMenuGroupChgHist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuGroupChgHist(StoreTypeVO storeTypeVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getMenuGroupChgHist(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 매장적용이력 탭 - 조회
     *
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 11. 21.
     */
    @RequestMapping(value = "/storeApplyChgHist/getStoreApplyChgHistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreApplyChgHistList(StoreTypeVO storeTypeVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getStoreApplyChgHistList(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeTypeVO);
    }

    /**
     * 매장적용이력 탭 - 취소
     *
     * @param storeTypeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 11. 21.
     */
    @RequestMapping(value = "/storeApplyChgHist/getStoreApplyChgHistSaveUpdate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreApplyChgHistSaveUpdate(@RequestBody StoreTypeVO[] storeTypeVOs, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeTypeService.getStoreApplyChgHistSaveUpdate(storeTypeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장적용이력 상세 팝업 - 조회
     *
     * @param storeTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 11. 21.
     */
    @RequestMapping(value = "/storeApplyChgHist/getStoreApplyChgHistDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreApplyChgHistDtlList(StoreTypeVO storeTypeVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTypeService.getStoreApplyChgHistDtlList(storeTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeTypeVO);
    }

}
