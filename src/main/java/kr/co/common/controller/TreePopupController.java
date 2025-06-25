package kr.co.common.controller;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.service.treePopup.TreePopupService;
import kr.co.common.service.treePopup.TreePopupVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
/**
 * @Class Name  : TreePopupController.java
 * @Description : 상품분류모듈 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value="/treePopup")
public class TreePopupController {

    /** sservice */
    private final SessionService sessionService;
    private final TreePopupService treePopupService;

    /** Constructor Injection */
    @Autowired
    public TreePopupController(SessionService sessionService, TreePopupService treePopupService) {
        this.sessionService = sessionService;
        this.treePopupService = treePopupService;
    }



    /**
     * 상품정보 분류 트리 조회3
     * @param   treePopupVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author  김유승
     * @since   2025.06.20
     */
    @RequestMapping(value = "/getProdClassTree3.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassTree3(TreePopupVO treePopupVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<TreePopupVO> result = treePopupService.getProdClassTree3(treePopupVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 상품정보 분류 플랫 조회
     *
     * @param   treePopupVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author  김유승
     * @since   2025.06.20
     */
    @RequestMapping(value = "/getProdClassCdNm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassCdNm(TreePopupVO treePopupVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String result = treePopupService.getProdClassCdNm(treePopupVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
