package kr.co.solbipos.store.manage.storeview.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.store.manage.storeview.service.StoreViewService;
import kr.co.solbipos.store.manage.storeview.service.StoreViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
* @Class Name : StoreViewController.java
* @Description : 가맹점 관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.08.07  김영근      최초생성
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.07
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/store/manage/storeView")
public class StoreViewController {

    @Autowired
    StoreViewService service;

    @Autowired
    SessionService sessionService;

    /**
     * 매장정보조회 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/storeView/list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "store/manage/storeView/storeView";
    }

    /**
     * 매장정보조회
     *
     * @param storeViewVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/storeView/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(StoreViewVO storeViewVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.getStoreViewList(storeViewVO);

        return returnListJson(Status.OK, list, storeViewVO);
    }
}
