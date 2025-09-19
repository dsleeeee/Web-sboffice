package kr.co.solbipos.base.multilingual.kioskSideOption.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.kioskSideOption.service.KioskSideOptionService;
import kr.co.solbipos.base.multilingual.kioskSideOption.service.KioskSideOptionVO;
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

/**
 * @Class Name : KioskSideOptionController.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(키오스크/사이드/옵션)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.11.20  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.11.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/multilingual/kioskSideOption")
public class KioskSideOptionController {

    private final SessionService sessionService;
    private final KioskSideOptionService kioskSideOptionService;
    private final KioskKeyMapService kioskKeyMapService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public KioskSideOptionController(SessionService sessionService, KioskSideOptionService kioskSideOptionService, KioskKeyMapService kioskKeyMapService, CmmCodeUtil cmmCodeUtil){
        this.sessionService = sessionService;
        this.kioskSideOptionService = kioskSideOptionService;
        this.kioskKeyMapService = kioskKeyMapService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 11. 20.
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
        KioskSideOptionVO kioskSideOptionVO = new KioskSideOptionVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 키오스크 키맵그룹 조회
        List<DefaultMap<String>> kioskTuClsTypeList = kioskKeyMapService.getKioskTuClsTypeList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskTuClsTypeList", kioskTuClsTypeList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(kioskTuClsTypeList, "name", "value", UseYn.ALL));

        // 키오스크 키맵그룹 조회(중분류 사용 키맵그룹만 조회)
        List<DefaultMap<String>> kioskTuClsType2List = kioskSideOptionService.getKioskTuClsTypeComboList(kioskSideOptionVO, sessionInfoVO);
        model.addAttribute("kioskTuClsType2List", kioskTuClsType2List.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(kioskTuClsType2List, "name", "value", UseYn.ALL));

        return "base/multilingual/kioskSideOption/kioskSideOptionTab";
    }

    /**
     *  키오스크(카테고리명) 탭 리스트 조회
     *
     * @param kioskSideOptionVO
     * @param request
     * @author  이다솜
     * @since   2023. 11. 20.
     */
    @RequestMapping(value = "/getKioskCategoryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskCategoryList(KioskSideOptionVO kioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskSideOptionService.getKioskCategoryList(kioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskSideOptionVO);
    }

    /**
     * 키오스크(카테고리명) 영문, 중문, 일문 저장
     * @param kioskSideOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 11. 20.
     */
    @RequestMapping(value = "/saveKioskCategory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveKioskCategory(@RequestBody KioskSideOptionVO[] kioskSideOptionVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskSideOptionService.saveKioskCategory(kioskSideOptionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 키오스크중분류(카테고리명) 카테고리(대분류) 콤보박스 조회
     * @param kioskSideOptionVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/getKioskCategoryComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskCategoryComboList(KioskSideOptionVO kioskSideOptionVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kioskSideOptionService.getKioskCategoryComboList(kioskSideOptionVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskSideOptionVO);
    }

    /**
     * 키오스크중분류(카테고리명) 탭 리스트 조회
     * @param kioskSideOptionVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/getKioskMClsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskMClsList(KioskSideOptionVO kioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskSideOptionService.getKioskMClsList(kioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskSideOptionVO);
    }

    /**
     * 키오스크중분류(카테고리명) 영문, 중문, 일문 저장
     * @param kioskSideOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2025. 09. 18.
     */
    @RequestMapping(value = "/saveKioskMCls.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveKioskMCls(@RequestBody KioskSideOptionVO[] kioskSideOptionVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskSideOptionService.saveKioskMCls(kioskSideOptionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     *  사이드(선택그룹명) 탭 리스트 조회
     *
     * @param kioskSideOptionVO
     * @param request
     * @author  이다솜
     * @since   2023. 11. 23.
     */
    @RequestMapping(value = "/getSideSdselGrpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideSdselGrpList(KioskSideOptionVO kioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskSideOptionService.getSideSdselGrpList(kioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskSideOptionVO);
    }

    /**
     * 사이드(선택그룹명) 영문, 중문, 일문 저장
     * @param kioskSideOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 11. 23.
     */
    @RequestMapping(value = "/saveSideSdselGrp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSideSdselGrp(@RequestBody KioskSideOptionVO[] kioskSideOptionVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskSideOptionService.saveSideSdselGrp(kioskSideOptionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     *  사이드(선택분류명) 탭 리스트 조회
     *
     * @param kioskSideOptionVO
     * @param request
     * @author  이다솜
     * @since   2023. 11. 23.
     */
    @RequestMapping(value = "/getSideSdselClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideSdselClassList(KioskSideOptionVO kioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskSideOptionService.getSideSdselClassList(kioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskSideOptionVO);
    }

    /**
     * 사이드(선택분류명) 영문, 중문, 일문 저장
     * @param kioskSideOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 11. 23.
     */
    @RequestMapping(value = "/saveSideSdselClass.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSideSdselClass(@RequestBody KioskSideOptionVO[] kioskSideOptionVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskSideOptionService.saveSideSdselClass(kioskSideOptionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     *  옵션(그룹명) 탭 리스트 조회
     *
     * @param kioskSideOptionVO
     * @param request
     * @author  이다솜
     * @since   2023. 11. 28.
     */
    @RequestMapping(value = "/getOptionGrpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOptionGrpList(KioskSideOptionVO kioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskSideOptionService.getOptionGrpList(kioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskSideOptionVO);
    }

    /**
     * 옵션(그룹명) 영문, 중문, 일문 저장
     * @param kioskSideOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 11. 28.
     */
    @RequestMapping(value = "/saveOptionGrp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveOptionGrp(@RequestBody KioskSideOptionVO[] kioskSideOptionVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskSideOptionService.saveOptionGrp(kioskSideOptionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     *  옵션(옵션명) 탭 리스트 조회
     *
     * @param kioskSideOptionVO
     * @param request
     * @author  이다솜
     * @since   2023. 11. 28.
     */
    @RequestMapping(value = "/getOptionValList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOptionValList(KioskSideOptionVO kioskSideOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskSideOptionService.getOptionValList(kioskSideOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskSideOptionVO);
    }

    /**
     * 옵션(옵션명) 영문, 중문, 일문 저장
     * @param kioskSideOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 11. 28.
     */
    @RequestMapping(value = "/saveOptionVal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveOptionVal(@RequestBody KioskSideOptionVO[] kioskSideOptionVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskSideOptionService.saveOptionVal(kioskSideOptionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

}
