package kr.co.solbipos.base.store.view.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.view.service.VanConfigVO;
import kr.co.solbipos.base.store.view.service.ViewService;
import kr.co.solbipos.base.store.view.service.ViewVO;
import kr.co.solbipos.base.store.view.service.enums.CornerUseYn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
* @Class Name : ViewController.java
* @Description : 기초관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
 * @ 2018.08.13  김영근      최초생성
 * @ 2018.11.20  김지은      기능오류 수정 및 angular 변경
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.13
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/base/store/view")
public class ViewController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ViewService viewService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public ViewController(ViewService viewService, SessionService sessionService) {
        this.viewService = viewService;
        this.sessionService = sessionService;
    }

    /**
     * 매장정보조회 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "base/store/view/view";
    }

    /**
     * 매장정보 리스트조회
     *
     * @param viewVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(ViewVO viewVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        //기본정보
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        viewVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        List<DefaultMap<String>> list = viewService.getViewList(viewVO);

        return returnListJson(Status.OK, list, viewVO);
    }

    /**
     * 매장정보 상세조회
     *
     * @param viewVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/dtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result detail(ViewVO viewVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        //매장 상세정보
        DefaultMap<String> storeInfo = viewService.getViewDetail(viewVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("storeInfo", storeInfo);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * VAN사 환경설정 정보 조회
     * 벤사 목록도 함께 조회 (코너, 포스)
     * @param vanConfgVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/vanConfg/vanConfigInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result vanConfigInfo(VanConfigVO vanConfgVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<DefaultMap<String>> posTerminalList = null;
        List<DefaultMap<String>> cornrTerminalList = null;

        CornerUseYn cornerUseYnVal = CornerUseYn.getEnum(viewService.getCornerUseYnVal(vanConfgVO));

        // 포스별승인 목록
        posTerminalList = viewService.getPosTerminalList(vanConfgVO);

        // 코너개별승인 목록
        cornrTerminalList = viewService.getCornerTerminalList(vanConfgVO);


        resultMap.put("cornerUseYnVal", cornerUseYnVal);
        resultMap.put("posTerminalList", posTerminalList);
        resultMap.put("cornrTerminalList", cornrTerminalList);

        return returnJson(Status.OK, resultMap);
    }
}
