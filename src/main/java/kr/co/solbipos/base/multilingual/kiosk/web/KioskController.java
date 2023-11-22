package kr.co.solbipos.base.multilingual.kiosk.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.captionMsg.service.CaptionMsgVO;
import kr.co.solbipos.base.multilingual.kiosk.service.KioskService;
import kr.co.solbipos.base.multilingual.kiosk.service.KioskVO;
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
 * @Class Name : KioskController.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(키오스크(카테고리)/사이드/옵션)
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
@RequestMapping("/base/multilingual/kiosk")
public class KioskController {

    private final SessionService sessionService;
    private final KioskService kioskService;
    private final KioskKeyMapService kioskKeyMapService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public KioskController(SessionService sessionService, KioskService kioskService, KioskKeyMapService kioskKeyMapService, CmmCodeUtil cmmCodeUtil){
        this.sessionService = sessionService;
        this.kioskService = kioskService;
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
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 키오스크 키맵그룹 조회
        List<DefaultMap<String>> kioskTuClsTypeList = kioskKeyMapService.getKioskTuClsTypeList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskTuClsTypeList", kioskTuClsTypeList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(kioskTuClsTypeList, "name", "value", UseYn.ALL));

        return "base/multilingual/kiosk/kioskTab";
    }

    /**
     *  키오스크(카테고리) 탭 리스트 조회
     *
     * @param kioskVO
     * @param request
     * @author  이다솜
     * @since   2023. 11. 20.
     */
    @RequestMapping(value = "/getKioskCategoryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskCategoryList(KioskVO kioskVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskService.getKioskCategoryList(kioskVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskVO);
    }

    /**
     * 키오스크(카테고리) 영문, 중문, 일문 저장
     * @param kioskVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 11. 20.
     */
    @RequestMapping(value = "/saveKioskCategory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveKioskCategory(@RequestBody KioskVO[] kioskVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskService.saveKioskCategory(kioskVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
