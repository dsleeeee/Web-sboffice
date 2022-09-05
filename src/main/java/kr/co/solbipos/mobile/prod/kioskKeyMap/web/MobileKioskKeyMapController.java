package kr.co.solbipos.mobile.prod.kioskKeyMap.web;

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
import kr.co.solbipos.mobile.prod.kioskKeyMap.service.MobileKioskKeyMapService;
import kr.co.solbipos.mobile.prod.kioskKeyMap.service.MobileKioskKeyMapVO;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleService;
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
 * @Class Name : MobileKioskKeyMapController.java
 * @Description : 상품관리 > 키오스크키맵
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.08.22  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.08.22
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/mobile/prod/kioskKeyMap")
public class MobileKioskKeyMapController {

    private final SessionService sessionService;
    private final MobileKioskKeyMapService mobileKioskKeyMapService;
    private final KioskKeyMapService kioskKeyMapService;

    public MobileKioskKeyMapController(SessionService sessionService, MobileProdSaleService mobileProdSaleService, MobileKioskKeyMapService mobileKioskKeyMapService, KioskKeyMapService kioskKeyMapService) {
        this.sessionService = sessionService;
        this.mobileKioskKeyMapService = mobileKioskKeyMapService;
        this.kioskKeyMapService = kioskKeyMapService;
    }


    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileKioskKeyMap/list.sb", method = RequestMethod.GET)
    public String mobileKioskKeyMapView(HttpServletRequest request, HttpServletResponse response, Model model) {
        KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 키오스크용 포스 조회
        List<DefaultMap<String>> kioskPosList = kioskKeyMapService.getKioskPosList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskPosList", convertToJson(kioskPosList));


        return "mobile/prod/kioskKeyMap/mobileKioskKeyMap";
    }

    /**
     * 매장키맵조회
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.08.22
     */
    @RequestMapping(value = "/mobileKioskKeyMap/getMobileKioskKeyMapStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileKioskKeyMapStoreList(HttpServletRequest request, HttpServletResponse response, Model model, MobileKioskKeyMapVO mobileKioskKeyMapVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileKioskKeyMapService.getMobileKioskKeyMapStoreList(mobileKioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileKioskKeyMapVO);
    }

    /**
     * 포장키맵조회
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.08.22
     */
    @RequestMapping(value = "/mobileKioskKeyMap/getMobileKioskKeyMapPackList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileKioskKeyMapPackList(HttpServletRequest request, HttpServletResponse response, Model model, MobileKioskKeyMapVO mobileKioskKeyMapVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileKioskKeyMapService.getMobileKioskKeyMapPackList(mobileKioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileKioskKeyMapVO);
    }

    /**
     * 키맵 저장
     * @param mobileKioskKeyMapVOs MobileKioskKeyMapVO[]
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/mobileKioskKeyMap/getMobileKioskKeyMapGrpSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileKioskKeyMapGrpSave(@RequestBody MobileKioskKeyMapVO[] mobileKioskKeyMapVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = mobileKioskKeyMapService.getMobileKioskKeyMapGrpSave(mobileKioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 중분류 조회
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.08.22
     */
    @RequestMapping(value = "/mobileKioskKeyMap/getMobileKioskKeyMapMList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileKioskKeyMapMList(HttpServletRequest request, HttpServletResponse response, Model model, MobileKioskKeyMapVO mobileKioskKeyMapVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileKioskKeyMapService.getMobileKioskKeyMapMList(mobileKioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileKioskKeyMapVO);
    }

    /**
     * 키맵 저장
     * @param mobileKioskKeyMapVOs MobileKioskKeyMapVO[]
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/mobileKioskKeyMap/getMobileKioskKeyMapMGrpSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileKioskKeyMapMGrpSave(@RequestBody MobileKioskKeyMapVO[] mobileKioskKeyMapVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = mobileKioskKeyMapService.getMobileKioskKeyMapMGrpSave(mobileKioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키맵상품조회
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.08.23
     */
    @RequestMapping(value = "/mobileKioskKeyMap/getMobileKioskKeyMapProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileKioskKeyMapProdList(HttpServletRequest request, HttpServletResponse response, Model model, MobileKioskKeyMapVO mobileKioskKeyMapVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileKioskKeyMapService.getMobileKioskKeyMapProdList(mobileKioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileKioskKeyMapVO);
    }

    /**
     * 키맵 상품 저장
     * @param mobileKioskKeyMapVOs MobileKioskKeyMapVO[]
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/mobileKioskKeyMap/getMobileKioskKeyMapProdSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileKioskKeyMapProdSave(@RequestBody MobileKioskKeyMapVO[] mobileKioskKeyMapVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = mobileKioskKeyMapService.getMobileKioskKeyMapProdSave(mobileKioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
