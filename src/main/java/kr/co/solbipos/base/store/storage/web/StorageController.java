package kr.co.solbipos.base.store.storage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.storage.service.StorageService;
import kr.co.solbipos.base.store.storage.service.StorageVO;
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
* @Class Name : StorageController.java
* @Description : 
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
 * @ 2018.08.13  김영근      최초생성
 * @ 2018.11.20  김지은      기능오류 수정 및 angular 변경
 * @ 2018.12.28  김지은      매장환경 복사 팝업 생성
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.13
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/base/store/storage")
public class StorageController {


    private final StorageService storageService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public StorageController(StorageService storageService, SessionService sessionService) {
        this.storageService = storageService;
        this.sessionService = sessionService;
    }

    /**
     * 창고정보 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/storage/view.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "base/store/storage/storage";
    }

    /**
     * 창고정보 리스트조회
     *
     * @param storageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/storage/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStorageList(StorageVO storageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        
        List<DefaultMap<String>> list = storageService.getStorageList(storageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storageVO);
    }
}
