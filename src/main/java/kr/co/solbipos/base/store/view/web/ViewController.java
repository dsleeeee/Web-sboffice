package kr.co.solbipos.base.store.view.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.view.service.VanConfigVO;
import kr.co.solbipos.base.store.view.service.ViewService;
import kr.co.solbipos.base.store.view.service.ViewVO;
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
import java.util.List;

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

    @Autowired
    ViewService service;

    @Autowired
    MessageService messageService;

    @Autowired
    SessionService sessionService;

    @Autowired
    CmmCodeUtil cmmCodeUtil;


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
        List<DefaultMap<String>> list = service.getViewList(viewVO);

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
        DefaultMap<String> storeInfo = service.getViewDetail(viewVO);

        //VAN사설정정보
        VanConfigVO vanConfigVO = new VanConfigVO();
        vanConfigVO.setStoreCd(viewVO.getStoreCd());
        List<DefaultMap<String>> vanConfigList = service.getVanconfgList(vanConfigVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("storeInfo", storeInfo);
        resultMap.put("vanConfigList", vanConfigList);

        //매장코너정보
        //2:코너개별승인
        if( "2".equals(storeInfo.getStr("cornerUseYn")) ) {
            List<DefaultMap<String>> cornrApproveList = service.getCornrApproveList(storeInfo.getStr("storeCd"));
            resultMap.put("cornrApproveList", cornrApproveList);
        }
        //3:포스별승인
        else if( "3".equals(storeInfo.getStr("cornerUseYn")) ) {
            List<DefaultMap<String>> posApproveList = service.getPosApproveList(storeInfo.getStr("storeCd"));
            resultMap.put("posApproveList", posApproveList);
        }

        return returnJson(Status.OK, resultMap);
    }

    /**
     * VAN사설정정보 조회
     *
     * @param vanConfgVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/vanconfg/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result vanconfgList(VanConfigVO vanConfgVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {


      List<DefaultMap<String>> list = service.getVanconfgList(vanConfgVO);

      return returnListJson(Status.OK, list, vanConfgVO);

    }
}
