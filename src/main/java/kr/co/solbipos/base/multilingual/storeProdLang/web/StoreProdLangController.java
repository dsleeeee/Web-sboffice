package kr.co.solbipos.base.multilingual.storeProdLang.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.storeProdLang.service.StoreProdLangService;
import kr.co.solbipos.base.multilingual.storeProdLang.service.StoreProdLangVO;
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
 * @Class Name : StoreProdLangController.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(상품)(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.25  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.09.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/multilingual/storeProdLang")
public class StoreProdLangController {

    private final SessionService sessionService;
    private final StoreProdLangService storeProdLangService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreProdLangController(SessionService sessionService, StoreProdLangService storeProdLangService) {
        this.sessionService = sessionService;
        this.storeProdLangService = storeProdLangService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2025.09.25
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(StoreProdLangVO storeProdLangVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "base/multilingual/storeProdLang/storeProdLangTab";
    }

    /**
     * 상품명 탭 리스트 조회(매장)
     * @param storeProdLangVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2025.09.25
     */
    @RequestMapping(value = "/getStoreProdNmList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreProdNmList(StoreProdLangVO storeProdLangVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeProdLangService.getStoreProdNmList(storeProdLangVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeProdLangVO);
    }

    /**
     * 상품명 영문, 중문, 일문 저장(매장)
     * @param storeProdLangVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025.09.25
     */
    @RequestMapping(value = "/saveStoreProdNm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreProdNm(@RequestBody StoreProdLangVO[] storeProdLangVOs, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeProdLangService.saveStoreProdNm(storeProdLangVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 상품설명 탭 리스트 조회(매장)
     * @param storeProdLangVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2023. 12. 28.
     */
    @RequestMapping(value = "/getStoreProdInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreProdInfoList(StoreProdLangVO storeProdLangVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeProdLangService.getStoreProdInfoList(storeProdLangVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeProdLangVO);
    }

    /**
     * 상품설명 영문, 중문, 일문 저장(매장)
     * @param storeProdLangVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025.09.25
     */
    @RequestMapping(value = "/saveStoreProdInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreProdInfo(@RequestBody StoreProdLangVO[] storeProdLangVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeProdLangService.saveStoreProdInfo(storeProdLangVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
