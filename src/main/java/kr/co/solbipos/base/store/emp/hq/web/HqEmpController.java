package kr.co.solbipos.base.store.emp.hq.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpService;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpVO;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpMenuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @Class Name : HqEmpController.java
 * @Description : 기초관리 > 매장관리 > 본사사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자      수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  정상화      최초생성
 * @ 2018.11.20  김지은      angular 방식으로 수정
 *
 * @author NHN한국사이버결제 KCP 정상화
 * @since 2018. 08.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/store/emp/hq")
public class HqEmpController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final HqEmpService hqEmpService;

    /** Constructor Injection */
    @Autowired
    public HqEmpController(SessionService sessionService, HqEmpService hqEmpService) {
        this.sessionService = sessionService;
        this.hqEmpService = hqEmpService;
    }

    /**
     * 본사사원 리스트 화면
     * @param model
     * @return the string
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String view(Model model) {
        return "base/store/emp/hqEmp";
    }


    /**
     * 본사 사원 목록 조회
     * @param hqEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    public Result view(HttpServletRequest request, HqEmpVO hqEmpVO,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = hqEmpService.getHqEmpList(hqEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, list,hqEmpVO);
    }

    /**
     * 본사사원정보 상세 조회
     * @param hqEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail.sb", method = RequestMethod.POST)
    public Result getDtlInfo(HqEmpVO hqEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        DefaultMap<String> result = hqEmpService.getHqEmpDtlInfo(hqEmpVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 본사사원정보 등록
     * @param hqEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/regist.sb", method = RequestMethod.POST)
    public Result regist(@RequestBody HqEmpVO hqEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        EmpResult empResult = hqEmpService.insertHqEmpInfo(hqEmpVO,sessionInfoVO);

        return returnJson(Status.OK, empResult);
    }

    /**
     * 본사사원정보 웹 사용자 ID 조회 (중복체크)
     * @param hqEmpVO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/chkHqUserId.sb", method = RequestMethod.POST)
    public Result chkHqUserId(HqEmpVO hqEmpVO) {

        EmpResult empResult= hqEmpService.getHqUserIdCnt(hqEmpVO);

        return returnJson(Status.OK, empResult);
    }

    /**
     * 본사사원정보 수정
     * @param hqEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    public Result save(@RequestBody HqEmpVO hqEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        EmpResult empResult = hqEmpService.saveHqEmpInfo(hqEmpVO,sessionInfoVO);

        return returnJson(Status.OK, empResult);
    }

    /**
     * 비밀번호 변경
     * @param hqEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyPassword.sb", method = RequestMethod.POST)
    public Result modifyPassword(@RequestBody HqEmpVO hqEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        EmpResult empResult = hqEmpService.modifyPassword(hqEmpVO,sessionInfoVO);

        return returnJson(Status.OK, empResult);
    }

    /**
     * 권한복사를 위한 본사 사원 리스트 조회
     * @param hqEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/authHqEmpList.sb", method = RequestMethod.POST)
    public Result authHqEmpList(HqEmpVO hqEmpVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 메뉴권한 복사할 본사 사원 목록 조회
        List<DefaultMap<String>> authHqEmpList = hqEmpService.authHqEmpList(hqEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, authHqEmpList);
    }

    /**
     * 사용메뉴 조회
     * @param hqEmpVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2020.06.01
     */
    @RequestMapping(value = "/avlblMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result avlblMenu(HqEmpVO hqEmpVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 사용메뉴 조회
        List<DefaultMap<String>> avlblMenu = hqEmpService.avlblMenu(hqEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, avlblMenu);
    }

    /**
     * 미사용메뉴 조회
     * @param hqEmpVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2020.06.01
     */
    @RequestMapping(value = "/beUseMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result beUseMenu(HqEmpVO hqEmpVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 미사용메뉴 조회
        List<DefaultMap<String>> beUseMenu = hqEmpService.beUseMenu(hqEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, beUseMenu);
    }

    /**
     * 메뉴권한복사
     * @param   hqEmpMenuVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2020.06.01
     */
    @RequestMapping(value = "/copyAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyAuth(@RequestBody HqEmpMenuVO hqEmpMenuVO, HttpServletRequest request,
                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = hqEmpService.copyAuth(hqEmpMenuVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사용메뉴 추가
     * @param   hqEmpMenus
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2020.06.01
     */
    @RequestMapping(value = "/addAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result addAuth(@RequestBody HqEmpMenuVO[] hqEmpMenus, HttpServletRequest request,
                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = hqEmpService.addAuth(hqEmpMenus, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사용메뉴 삭제
     * @param   hqEmpMenus
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2020.06.01
     */
    @RequestMapping(value = "/removeAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result removeAuth(@RequestBody HqEmpMenuVO[] hqEmpMenus, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = hqEmpService.removeAuth(hqEmpMenus, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

}
