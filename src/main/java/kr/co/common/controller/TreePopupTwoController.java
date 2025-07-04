package kr.co.common.controller;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.service.treePopupTwo.TreePopupTwoService;
import kr.co.common.service.treePopupTwo.TreePopupTwoVO;
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
 * @Class Name  : TreePopupTwoController.java
 * @Description : 상품분류모듈 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.27  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value="/treePopupTwo")
public class TreePopupTwoController {

    private final SessionService sessionService;
    private final TreePopupTwoService treePopupTwoService;

    @Autowired
    public TreePopupTwoController(SessionService sessionService, TreePopupTwoService treePopupTwoService) {
        this.sessionService = sessionService;
        this.treePopupTwoService = treePopupTwoService;
    }

    /**
     * 상품정보 분류 트리(체크박스) 조회2
     * @param   treePopupTwoVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author  김유승
     * @since   2025.06.25
     */
    @RequestMapping(value = "/getProdClassTreeCheck2.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassTreeCheck2(TreePopupTwoVO treePopupTwoVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<TreePopupTwoVO> result = treePopupTwoService.getProdClassTreeCheck2(treePopupTwoVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 상품정보 분류 플랫 조회
     *
     * @param   treePopupTwoVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author  김유승
     * @since   2025.06.25
     */
    @RequestMapping(value = "/getProdClassCdNmCheck2.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassCdNmCheck2(TreePopupTwoVO treePopupTwoVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String result = treePopupTwoService.getProdClassCdNmCheck2(treePopupTwoVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
