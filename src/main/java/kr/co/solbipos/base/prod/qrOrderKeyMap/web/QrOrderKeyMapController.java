package kr.co.solbipos.base.prod.qrOrderKeyMap.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.qrOrderKeyMap.service.QrOrderKeyMapService;
import kr.co.solbipos.base.prod.qrOrderKeyMap.service.QrOrderKeyMapVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
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
 * @Class Name  : QrOrderKeyMapController.java
 * @Description : 기초관리 > 상품관리2 > QR주문키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.28  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.11.28
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/qrOrderKeyMap")
public class QrOrderKeyMapController {

    private final SessionService sessionService;
    private final QrOrderKeyMapService qrOrderKeyMapService;
    private final CmmEnvUtil cmmEnvUtil;
    private final DayProdService dayProdService;


    /**
     * Constructor Injection
     */
    public QrOrderKeyMapController(SessionService sessionService, QrOrderKeyMapService qrOrderKeyMapService, CmmEnvUtil cmmEnvUtil, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.qrOrderKeyMapService = qrOrderKeyMapService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.dayProdService = dayProdService;
    }


    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/qrOrderKeyMap/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));

        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        return "base/prod/qrOrderKeyMap/qrOrderKeyMapRegist";
    }

    /**
     * QR오더 카테고리 (분류) 조회
     *
     * @param   qrOrderKeyMapVO
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/qrOrderKeyMap/getQrOrderCategory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getQrOrderCategory(QrOrderKeyMapVO qrOrderKeyMapVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = qrOrderKeyMapService.getQrOrderCategory(qrOrderKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * QR오더 - QR오더 카테고리(분류) 저장
     *
     * @param   qrOrderKeyMapVOS
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/qrOrderKeyMap/saveQrOrderCategory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveQrOrderCategory(@RequestBody QrOrderKeyMapVO[] qrOrderKeyMapVOS, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = qrOrderKeyMapService.saveQrOrderCategory(qrOrderKeyMapVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * QR오더 키맵 조회
     *
     * @param   qrOrderKeyMapVO
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/qrOrderKeyMap/getQrOrderKeyMap.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getQrOrderKeyMap(QrOrderKeyMapVO qrOrderKeyMapVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = qrOrderKeyMapService.getQrOrderKeyMap(qrOrderKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * QR오더 - QR오더 키맵 수정
     *
     * @param   qrOrderKeyMapVOS
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/qrOrderKeyMap/updateQrOrderKeyMap.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateQrOrderKeyMap(@RequestBody QrOrderKeyMapVO[] qrOrderKeyMapVOS, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = qrOrderKeyMapService.updateQrOrderKeyMap(qrOrderKeyMapVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * QR오더 상품 조회
     *
     * @param   qrOrderKeyMapVO
     * @param   request
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/qrOrderKeyMap/getQrOrderProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getQrOrderProdList(QrOrderKeyMapVO qrOrderKeyMapVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = qrOrderKeyMapService.getQrOrderProdList(qrOrderKeyMapVO, sessionInfoVO);

        return returnListJson(Status.OK, list, qrOrderKeyMapVO);
    }

    /**
     * QR오더 - QR오더 키맵 수정
     *
     * @param   qrOrderKeyMapVOS
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/qrOrderKeyMap/saveQrOrderKeyMap.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveQrOrderKeyMap(@RequestBody QrOrderKeyMapVO[] qrOrderKeyMapVOS, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = qrOrderKeyMapService.saveQrOrderKeyMap(qrOrderKeyMapVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
