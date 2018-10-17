package kr.co.solbipos.store.manage.terminalManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;
import kr.co.solbipos.store.manage.storemanage.service.StorePosVO;
import kr.co.solbipos.store.manage.terminalManage.service.StoreCornerVO;
import kr.co.solbipos.store.manage.terminalManage.service.StoreTerminalVO;
import kr.co.solbipos.store.manage.terminalManage.service.TerminalManageService;
import kr.co.solbipos.store.manage.terminalManage.service.enums.TerminalEnvFg;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : TerminalManageController.java
 * @Description : 가맹점관리 > 매장관리 > 매장터미널관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.06  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.06
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */


@Controller
@RequestMapping(value = "/store/manage/terminalManage/")
public class TerminalManageController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** service */
    @Autowired
    TerminalManageService service;

    @Autowired
    SessionService sessionService;

    /**
     * 매장터미널관리 - 화면 이동
     * @param request
     * @param response
     * @param model
     * @return String
     * @author 김지은
     * @since 2018.10.06
     */
    @RequestMapping(value = "terminalManage/terminalView.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "store/manage/terminalManage/terminalView";
    }

    /**
     * 포스목록 조회
     * @param storePosVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "terminalManage/getPosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosList(StorePosVO storePosVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        List<DefaultMap<String>> storeList = service.getPosList(storePosVO);

        return returnListJson(Status.OK, storeList, storePosVO);
    }


    /**
     * 매장 조회
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "terminalManage/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(StoreManageVO storeManageVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        List<DefaultMap<String>> storeList = service.getStoreList(storeManageVO);

        return returnListJson(Status.OK, storeList, storeManageVO);
    }

    /**
     * 매장터미널관리 - 포스/터미널 설정 환경변수 조회
     * @param storeEnvVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "terminalManage/getTerminalEnv.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTerminalEnv(StoreEnvVO storeEnvVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<DefaultMap<String>> posList = null;
        List<DefaultMap<String>> cornerList = null;

        TerminalEnvFg envstVal = TerminalEnvFg.getEnum(service.getTerminalEnv(storeEnvVO));

        // 포스 목록 조회
        StorePosVO storePosVO = new StorePosVO();
        storePosVO.setStoreCd(storeEnvVO.getStoreCd());

        posList  = service.getPosList(storePosVO);

        // 코너 목록 조회
        StoreCornerVO storeCornerVO = new StoreCornerVO();
        storeCornerVO.setStoreCd(storeEnvVO.getStoreCd());

        cornerList  = service.getCornerList(storeCornerVO);

        resultMap.put("envstVal", envstVal);
        resultMap.put("posList", posList);
        resultMap.put("cornerList", cornerList);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 매장터미널관리 - 포스 목록 조회
     * @param storeTerminalVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pos/getPosTerminalList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosTerminalList(StoreTerminalVO storeTerminalVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        List<DefaultMap<String>> posList = service.getPosTerminalList(storeTerminalVO);

        return returnListJson(Status.OK, posList, storeTerminalVO);
    }

    /**
     * POS VAN 터미널 정보 저장
     * @param storeTerminalVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pos/savePosTerminalInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePosTerminalInfo(@RequestBody StoreTerminalVO[] storeTerminalVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        try{
            StoreEnvVO storeEnvVO = new StoreEnvVO();
            storeEnvVO.setStoreCd(request.getParameter("storeCd"));
            storeEnvVO.setEnvstVal(request.getParameter("terminalFgVal"));

            // 환경변수 값 저장
            result += service.updateTerminalEnvst(storeEnvVO, sessionInfoVO);

            // 터미널정보 저장
            result += service.savePosTerminalInfo(storeTerminalVOs, sessionInfoVO);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return returnListJson(Status.OK, result);
    }

    /**
     * 매장터미널관리 - 코너 터미널 목록 조회
     * @param storeTerminalVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "corner/getCornerTerminalList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCornerTerminalList(StoreTerminalVO storeTerminalVO , HttpServletRequest request,
        HttpServletResponse response, Model model) {

        List<DefaultMap<String>> posList = service.getCornerTerminalList(storeTerminalVO);

        return returnListJson(Status.OK, posList, storeTerminalVO);
    }

    /**
     * 코너 정보 저장
     * @param storeTerminalVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "corner/saveCornerTerminalInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCornerInfo(@RequestBody StoreTerminalVO[] storeTerminalVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        try{
            StoreEnvVO storeEnvVO = new StoreEnvVO();
            storeEnvVO.setStoreCd(request.getParameter("storeCd"));
            storeEnvVO.setEnvstVal(request.getParameter("terminalFgVal"));

            // 환경변수 값 저장
            result += service.updateTerminalEnvst(storeEnvVO, sessionInfoVO);

            // 코너 터미널 정보 저장
            result = service.saveCornerTerminalInfo(storeTerminalVOs, sessionInfoVO);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return returnListJson(Status.OK, result);
    }
}
