package kr.co.solbipos.base.prod.kioskKeyMap.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : KioskKeyMapController.java
 * @Description : 기초관리 - 상품관리 - 키오스크키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.03  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 09.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/kioskKeyMap")
public class KioskKeyMapController {

    private final SessionService sessionService;
    private final KioskKeyMapService kioskKeyMapService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public KioskKeyMapController(SessionService sessionService, KioskKeyMapService kioskKeyMapService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.kioskKeyMapService = kioskKeyMapService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2020. 09. 04.
     */
    @RequestMapping(value = "/kioskKeyMap/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 키오스크용 포스 조회
        List<DefaultMap<String>> kioskPosList = kioskKeyMapService.getKioskPosList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskPosList", convertToJson(kioskPosList));

        // 키오스크 키맵그룹 조회
        List<DefaultMap<String>> kioskTuClsTypeList = kioskKeyMapService.getKioskTuClsTypeList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskTuClsTypeList", convertToJson(kioskTuClsTypeList)  );

        // 키오스크 키맵그룹 사용여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("kioskKeyMapGrpFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1104"), "0"));
        }else{
            model.addAttribute("kioskKeyMapGrpFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1104") , "0"));
        }

        return "base/prod/kioskKeyMap/kioskKeyMap";
    }

    /**
     * 키오스크 카테고리(분류) 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2020. 09. 04.
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskCategory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskCategory(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getKioskCategory(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 키오스크 카테고리(분류) 저장
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2020. 09. 04.
     */
    @RequestMapping(value = "/kioskKeyMap/saveKioskCategory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveKioskCategory(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveKioskCategory(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 키맵 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2020. 09. 07.
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskKeyMap.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskKeyMap(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getKioskKeyMap(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 키오스크 키맵 수정
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2020. 09. 10.
     */
    @RequestMapping(value = "/kioskKeyMap/updateKioskKeyMap.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateKioskKeyMap(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.updateKioskKeyMap(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 상품 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @author  이다솜
     * @since   2020. 09. 07.
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskProdList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskKeyMapService.getKioskProdList(kioskKeyMapVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskKeyMapVO);
    }

    /**
     * 키오스크 키맵 등록
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2020. 09. 07.
     */
    @RequestMapping(value = "/kioskKeyMap/saveKioskKeyMap.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveKioskKeyMap(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveKioskKeyMap(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 키맵그룹 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 06. 04.
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskTuClsTypeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskTuClsTypeList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kioskKeyMapService.getKioskTuClsTypeList(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskKeyMapVO);
    }


    /**
     * 키오스크 키맵 신규그룹추가
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 06. 04.
     */
    @RequestMapping(value = "/kioskKeyMap/createKioskTuClsType.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result createKioskTuClsType(@RequestBody KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.createKioskTuClsType(kioskKeyMapVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 키맵 그룹복제(신규생성)
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 06. 07.
     */
    @RequestMapping(value = "/kioskKeyMap/copyKioskTuClsType.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyKioskTuClsType(@RequestBody KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.copyKioskTuClsType(kioskKeyMapVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 키맵 그룹복제(delete insert)
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2021.12.15
     */
    @RequestMapping(value = "/kioskKeyMap/copyStoreKioskTuClsType.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyStoreKioskTuClsType(@RequestBody KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.copyStoreKioskTuClsType(kioskKeyMapVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 키맵매장적용 - 매장리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.06.08
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskKeyMapService.getStoreList(kioskKeyMapVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskKeyMapVO);
    }

    /**
     * 키오스크 키맵매장적용
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 06. 08.
     */
    @RequestMapping(value = "/kioskKeyMap/saveKioskKeyMapStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveKioskKeyMapStore(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveKioskKeyMapStore(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 매장적용(매장/포장) - 매장 키오스크 포스 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.06.09
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getStoreKioskPosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreKioskPosList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskKeyMapService.getStoreKioskPosList(kioskKeyMapVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskKeyMapVO);
    }

    /**
     * 키오스크 매장적용(매장/포장) - 본사/매장 환경설정값 저장
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 06. 09.
     */
    @RequestMapping(value = "/kioskKeyMap/saveHqStoreKioskPosEnv.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqStoreKioskPosEnv(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveHqStoreKioskPosEnv(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 매장적용(매장/포장) - 키오스크 환경설정 값 가져오기
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.06.09
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskEnv.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskEnv(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String sEnvstVal = kioskKeyMapService.getKioskEnv(kioskKeyMapVO, sessionInfoVO);

        return returnJson(Status.OK, sEnvstVal);
    }


    /**
     * 키오스크 추천메뉴 - 추천메뉴코드 가져오기
     *
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.08
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getRecmd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecmd(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getRecmd(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 키오스크 추천메뉴 - 추천메뉴코드 저장
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.09
     */
    @RequestMapping(value = "/kioskKeyMap/saveRecmd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRecmd(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveRecmd(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 추천메뉴 - 추천상품 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.09
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getRecmdProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecmdProd(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getRecmdProd(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 키오스크 추천메뉴 - 추천상품으로 등록할 상품
     *
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.09
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getRecmdProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecmdProdList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getRecmdProdList(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskKeyMapVO);
    }

    /**
     * 키오스크 추천메뉴 - 추천메뉴 저장(하위 왼쪽그리드)
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.09
     */
    @RequestMapping(value = "/kioskKeyMap/saveRecmdProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRecmdProd(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
        kioskKeyMapVO.setRecmdCd(kioskKeyMapVOs[0].getRecmdCd());

        kioskKeyMapService.deleteRecmdProd(kioskKeyMapVO, sessionInfoVO);

        int result = kioskKeyMapService.saveRecmdProd(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 추천메뉴 - 상품 > 추천메뉴 저장(하위 오른쪽 그리드)
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.09
     */
    @RequestMapping(value = "/kioskKeyMap/addRecmdProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result addRecmdProd(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.addRecmdProd(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
