package kr.co.solbipos.store.hq.hqmanage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageService;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqMenuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @Class Name : HqManageController.java
 * @Description : 가맹점관리 > 본사정보 > 본사정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value="/store/hq/hqManage/")
public class HqManageController {

    private final HqManageService service;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public HqManageController(HqManageService service, SessionService sessionService) {
        this.service = service;
        this.sessionService = sessionService;
    }

    /**
     * 본사정보관리 화면 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "hqManage/list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "store/hq/hqManage/hqManage";
    }

    /**
     * 본사정보관리 리스트 조회
     * @param   hqManageVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "hqManage/getHqList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(HqManageVO hqManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.list(hqManageVO);

        return returnListJson(Status.OK, list, hqManageVO);
    }

    /**
     * 본사정보 상세조회
     * @param   hqManageVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "master/getHqDetailInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqDetailInfo(HqManageVO hqManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        DefaultMap<String> result = service.getHqDetailInfo(hqManageVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사업자번호 중복체크
     * @param   hqManageVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "master/chkBizNo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkBizNo(HqManageVO hqManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        int cnt = service.chkBizNo(hqManageVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사업자번호 사용현황 조회
     * @param   hqManageVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "master/bizUseList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result bizUseList(HqManageVO hqManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.getBizUseList(hqManageVO);

        return returnListJson(Status.OK, list);
    }

    /**
     * 사업자번호 사용현황 상세
     * @param   hqManageVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "master/bizInfoDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result bizInfoDtl(HqManageVO hqManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        DefaultMap<String> result = service.getBizInfoDtl(hqManageVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 본사 신규등록
     * @param   hqManageVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "master/saveHqInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqInfo(@RequestBody HqManageVO hqManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 사업자번호 정리
        String bizNo = hqManageVO.getBizNo1() + hqManageVO.getBizNo2() + hqManageVO.getBizNo3();
        hqManageVO.setBizNo(bizNo);

        // 오픈일자
        String sysOpenDate = hqManageVO.getSysOpenDate();
        hqManageVO.setSysOpenDate(StringUtils.remove(sysOpenDate, "-"));

        // 폐점일자
        String sysClosureDate = hqManageVO.getSysClosureDate();
        hqManageVO.setSysClosureDate(StringUtils.remove(sysClosureDate, "-"));

        int cnt = service.regist(hqManageVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 본사 수정
     * @param   hqManageVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "master/updateHqInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateHqInfo(@RequestBody HqManageVO hqManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 사업자번호 정리
        String bizNo = hqManageVO.getBizNo1() + hqManageVO.getBizNo2() + hqManageVO.getBizNo3();
        hqManageVO.setBizNo(bizNo);

        // 오픈일자
        String sysOpenDate = hqManageVO.getSysOpenDate();
        hqManageVO.setSysOpenDate(StringUtils.remove(sysOpenDate, "-"));

        // 폐점일자
        String sysClosureDate = hqManageVO.getSysClosureDate();
        hqManageVO.setSysClosureDate(StringUtils.remove(sysClosureDate, "-"));

        int cnt = service.modify(hqManageVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 메뉴권한 데이터 조회
     * @param   hqManageVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "authorExcept/getAuthHqList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result authHqList(HqManageVO hqManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 복사할 본사 조회
        List<DefaultMap<String>> authHqList = service.authHqList(hqManageVO);

        // 사용가능한 메뉴 조회
        List<DefaultMap<String>> avlblMenu = service.avlblMenu(hqManageVO);

        // 사용중인 메뉴 조회
        List<DefaultMap<String>> beUseMenu = service.beUseMenu(hqManageVO);

        resultMap.put("authHqList", authHqList);
        resultMap.put("avlblMenu", avlblMenu);
        resultMap.put("beUseMenu", beUseMenu);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 메뉴권한복사
     * @param   hqMenuVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "authorExcept/copyAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyAuth(@RequestBody HqMenuVO hqMenuVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.copyAuth(hqMenuVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사용메뉴 추가
     * @param   hqMenuVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "authorExcept/addAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result addAuth(@RequestBody HqMenuVO[] hqMenuVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.addAuth(hqMenuVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사용메뉴 삭제
     * @param   hqMenuVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "authorExcept/removeAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result removeAuth(@RequestBody HqMenuVO[] hqMenuVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.removeAuth(hqMenuVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }


    /**
     * 환경설정 조회
     * @param   hqManageVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "config/getConfiglist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result configList(HqManageVO hqManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.getConfigList(hqManageVO);

        return returnListJson(Status.OK, list, hqManageVO);
    }

    /**
     * 환경설정 저장
     * @param   hqEnvstsVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "config/saveHqEnv.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqEnv(@RequestBody HqEnvstVO[] hqEnvstsVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.saveConfig(hqEnvstsVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
