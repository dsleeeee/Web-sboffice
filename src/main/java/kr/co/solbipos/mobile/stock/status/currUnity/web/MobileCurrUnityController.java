package kr.co.solbipos.mobile.stock.status.currUnity.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.currUnity.service.MobileCurrUnityService;
import kr.co.solbipos.mobile.stock.status.currUnity.service.MobileCurrUnityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : MobileCurrUnityController.java
 * @Description : (모바일)재고현황 > 본사매장통합현재고
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.19  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/stock/status/currUnity")
public class MobileCurrUnityController {

    private final SessionService sessionService;
    private final MobileCurrUnityService mobileCurrUnityService;

    @Autowired
    public MobileCurrUnityController(SessionService sessionService, MobileCurrUnityService mobileCurrUnityService) {
        this.sessionService = sessionService;
        this.mobileCurrUnityService = mobileCurrUnityService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileCurrUnity/list.sb", method = RequestMethod.GET)
    public String getCurrUnityView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "mobile/stock/status/currUnity/mobileCurrUnity";
    }

    /**
     * 본사매장통합현재고 - 본사매장통합현재고 리스트 조회
     *
     * @param mobileCurrUnityVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @ResponseBody
    @RequestMapping(value = "/mobileCurrUnity/getCurrUnityList.sb", method = RequestMethod.POST)
    public Result getCurrUnityList(HttpServletRequest request, HttpServletResponse response, MobileCurrUnityVO mobileCurrUnityVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileCurrUnityService.getCurrUnityList(mobileCurrUnityVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileCurrUnityVO);
    }

    /**
     * 본사매장통합현재고 - 본사매장통합현재고 본사 상세 리스트 조회
     *
     * @param mobileCurrUnityVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @ResponseBody
    @RequestMapping(value = "/mobileCurrUnity/getCurrUnityHqDtlList.sb", method = RequestMethod.POST)
    public Result getCurrUnityHqDtlList(HttpServletRequest request, HttpServletResponse response, MobileCurrUnityVO mobileCurrUnityVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileCurrUnityService.getCurrUnityHqDtlList(mobileCurrUnityVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileCurrUnityVO);
    }

    /**
     * 본사매장통합현재고 - 본사매장통합현재고 매장 상세 리스트 조회
     *
     * @param mobileCurrUnityVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @ResponseBody
    @RequestMapping(value = "/mobileCurrUnity/getCurrUnityStoreDtlList.sb", method = RequestMethod.POST)
    public Result getCurrUnityStoreDtlList(HttpServletRequest request, HttpServletResponse response, MobileCurrUnityVO mobileCurrUnityVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileCurrUnityService.getCurrUnityStoreDtlList(mobileCurrUnityVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileCurrUnityVO);
    }

    /**
     * 본사매장통합현재고 - 본사매장통합현재고 전체 엑셀 리스트 조회
     *
     * @param mobileCurrUnityVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @ResponseBody
    @RequestMapping(value = "/mobileCurrUnity/getCurrUnityExcelList.sb", method = RequestMethod.POST)
    public Result getCurrUnityExcelList(HttpServletRequest request, HttpServletResponse response, MobileCurrUnityVO mobileCurrUnityVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileCurrUnityService.getCurrUnityExcelList(mobileCurrUnityVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileCurrUnityVO);
    }

    /**
     * 본사매장통합현재고 - 본사매장통합현재고 본사 상세 전체 엑셀 리스트 조회
     *
     * @param mobileCurrUnityVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @ResponseBody
    @RequestMapping(value = "/mobileCurrUnity/getCurrUnityHqDtlExcelList.sb", method = RequestMethod.POST)
    public Result getCurrUnityHqDtlExcelList(HttpServletRequest request, HttpServletResponse response, MobileCurrUnityVO mobileCurrUnityVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileCurrUnityService.getCurrUnityHqDtlExcelList(mobileCurrUnityVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileCurrUnityVO);
    }

    /**
     * 본사매장통합현재고 - 본사매장통합현재고 매장 상세 전체 엑셀 리스트 조회
     *
     * @param mobileCurrUnityVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @ResponseBody
    @RequestMapping(value = "/mobileCurrUnity/getCurrUnityStoreDtlExcelList.sb", method = RequestMethod.POST)
    public Result getCurrUnityStoreDtlExcelList(HttpServletRequest request, HttpServletResponse response, MobileCurrUnityVO mobileCurrUnityVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileCurrUnityService.getCurrUnityStoreDtlExcelList(mobileCurrUnityVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileCurrUnityVO);
    }

}
