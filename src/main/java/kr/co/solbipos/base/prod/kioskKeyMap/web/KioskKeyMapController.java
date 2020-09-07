package kr.co.solbipos.base.prod.kioskKeyMap.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapService;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.prod.kioskOption.service.KioskOptionVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuSelProdVO;
import kr.co.solbipos.sale.day.day.service.DayVO;
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

    /**
     * Constructor Injection
     */
    @Autowired
    public KioskKeyMapController(SessionService sessionService, KioskKeyMapService kioskKeyMapService) {
        this.sessionService = sessionService;
        this.kioskKeyMapService = kioskKeyMapService;
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
        model.addAttribute("kioskPosList", convertToJson(kioskPosList)  );

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
}
