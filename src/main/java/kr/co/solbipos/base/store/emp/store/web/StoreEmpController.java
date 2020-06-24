package kr.co.solbipos.base.store.emp.store.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpMenuVO;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpService;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpVO;
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

/**
 * @Class Name : StoreEmpController.java
 * @Description : 기초관리 > 매장관리 > 매장사원관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.16  hblee      최초생성
 * @ 2018.11.23  김지은     angular 방식으로 변경 및 로직 수정(타 페이지와 통일성 맞춤)
 *
 * @author NHN한국사이버결제 이한빈
 * @since 2018.08.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("base/store/emp/store")
public class StoreEmpController {

    private final SessionService sessionService;
    private final MessageService messageService;
    private final StoreEmpService storeEmpService;

    /** Constructor Injection */
    @Autowired
    public StoreEmpController(SessionService sessionService, MessageService messageService,
        StoreEmpService storeEmpService) {
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.storeEmpService = storeEmpService;
    }

    /***
     * 매장 사원 리스트 화면
     * @param model
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String list(Model model) {
        return "base/store/emp/storeEmp";
    }

    /**
     * 매장 사원 목록 조회
     * @param request
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author 이한빈
     * @since 2018. 08. 16.
     */
    @ResponseBody
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    public Result getStoreEmpList(HttpServletRequest request, StoreEmpVO storeEmpVO,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 매장사원목록 조회
        List<DefaultMap<String>> list = storeEmpService.getStoreEmpList(storeEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeEmpVO);
    }

    /**
     * 매장 사원 정보 상세 조회
     * @param request
     * @param storeEmpVO
     * @param   response
     * @param   model
     * @return
     * @author 이한빈
     * @since 2018. 08. 16.
     */
    @ResponseBody
    @RequestMapping(value = "/detail.sb", method = RequestMethod.POST)
    public Result getStoreEmp(HttpServletRequest request, StoreEmpVO storeEmpVO,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> storeEmp = storeEmpService.getStoreEmpDtlInfo(storeEmpVO, sessionInfoVO);

        return returnJson(Status.OK, storeEmp);
    }


    /**
     * 매장 사원 정보 등록
     * @param storeEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/regist.sb", method = RequestMethod.POST)
    public Result regist(@RequestBody StoreEmpVO storeEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        EmpResult storeEmpResult = storeEmpService.insertStoreEmpInfo(storeEmpVO,sessionInfoVO);

        return returnJson(Status.OK, storeEmpResult);
    }


    /**
     * 매장 사원 정보 웹 사용자 ID 조회 (중복체크)
     * @param storeEmpVO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/chkStoreUserId.sb", method = RequestMethod.POST)
    public Result chkStoreUserId(StoreEmpVO storeEmpVO) {

        EmpResult storeEmpResult= storeEmpService.getStoreUserIdCnt(storeEmpVO);

        return returnJson(Status.OK, storeEmpResult);
    }

    /**
     * 매장 사원 정보 수정
     * @param storeEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    public Result save(@RequestBody StoreEmpVO storeEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        EmpResult storeEmpResult = storeEmpService.saveStoreEmpInfo(storeEmpVO, sessionInfoVO);

        return returnJson(Status.OK, storeEmpResult);
    }

    /**
     * 비밀번호 변경
     * @param storeEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyPassword.sb", method = RequestMethod.POST)
    public Result modifyPassword(@RequestBody StoreEmpVO storeEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        EmpResult empResult = storeEmpService.modifyPassword(storeEmpVO,sessionInfoVO);

        return returnJson(Status.OK, empResult);
    }

    /**
     * 권한복사를 위한 매장 사원 리스트 조회
     * @param storeEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/authStoreEmpList.sb", method = RequestMethod.POST)
    public Result authHqEmpList(StoreEmpVO storeEmpVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 메뉴권한 복사할 매장 사원 목록 조회
        List<DefaultMap<String>> authStoreEmpList = storeEmpService.authStoreEmpList(storeEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, authStoreEmpList);
    }

    /**
     * 사용메뉴 조회
     * @param storeEmpVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2020.06.22
     */
    @RequestMapping(value = "/avlblMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result avlblMenu(StoreEmpVO storeEmpVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 사용메뉴 조회
        List<DefaultMap<String>> avlblMenu = storeEmpService.avlblMenu(storeEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, avlblMenu);
    }

    /**
     * 미사용메뉴 조회
     * @param storeEmpVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2020.06.22
     */
    @RequestMapping(value = "/beUseMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result beUseMenu(StoreEmpVO storeEmpVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 미사용메뉴 조회
        List<DefaultMap<String>> beUseMenu = storeEmpService.beUseMenu(storeEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, beUseMenu);
    }

    /**
     * 메뉴권한복사
     * @param   storeEmpMenuVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2020.06.22
     */
    @RequestMapping(value = "/copyAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyAuth(@RequestBody StoreEmpMenuVO storeEmpMenuVO, HttpServletRequest request,
                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = storeEmpService.copyAuth(storeEmpMenuVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사용메뉴 추가
     * @param   storeEmpMenus
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2020.06.22
     */
    @RequestMapping(value = "/addAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result addAuth(@RequestBody StoreEmpMenuVO[] storeEmpMenus, HttpServletRequest request,
                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = storeEmpService.addAuth(storeEmpMenus, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사용메뉴 삭제
     * @param   storeEmpMenus
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2020.06.22
     */
    @RequestMapping(value = "/removeAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result removeAuth(@RequestBody StoreEmpMenuVO[] storeEmpMenus, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = storeEmpService.removeAuth(storeEmpMenus, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }
}
