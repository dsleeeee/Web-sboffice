package kr.co.solbipos.base.prod.corner.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.corner.service.CornerService;
import kr.co.solbipos.base.prod.corner.service.CornerVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.store.manage.terminalManage.service.StoreCornerVO;
import kr.co.solbipos.store.manage.terminalManage.service.TerminalManageService;
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
 * @Class Name : CornerController.java
 * @Description : 기초관리 - 상품관리 - 코너관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.27  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 02.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/corner/prodCorner")
public class CornerController {

    private final SessionService sessionService;
    private final TerminalManageService terminalManageService;
    private final CornerService cornerService;

    /** Constructor Injection */
    @Autowired
    public CornerController(SessionService sessionService, TerminalManageService terminalManageService, CornerService cornerService) {
        this.sessionService = sessionService;
        this.terminalManageService = terminalManageService;
        this.cornerService = cornerService;
    }

    /**
     * 상품별 코너변경 메인화면
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2020.02.27
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 코너 목록 조회
        StoreCornerVO storeCornerVO = new StoreCornerVO();
        storeCornerVO.setStoreCd(sessionInfoVO.getStoreCd());

        List<DefaultMap<String>> cornerList =  terminalManageService.getCornerList(storeCornerVO);;

        model.addAttribute("cornerList", convertToJson(cornerList) );

        return "base/prod/corner/prodCorner";
    }

    /**
     * 상품별 코너변경 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2020.02.27
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result gerProdCornerList(CornerVO cornerVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = cornerService.getProdCornerList(cornerVO, sessionInfoVO);

        return returnListJson(Status.OK, list, cornerVO);
    }

    /***
     * 코너 이동
     * @param cornerVOs
     * @param request
     * @author 이다솜
     * @since 2020.02.27
     * @return
     */
    @RequestMapping(value = "/changeProdCorner.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result changeProdCorner(@RequestBody CornerVO[] cornerVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = cornerService.changeProdCorner(cornerVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
