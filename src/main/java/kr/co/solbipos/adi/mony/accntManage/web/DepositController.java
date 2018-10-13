package kr.co.solbipos.adi.mony.accntManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.mony.accntManage.service.DepositService;
import kr.co.solbipos.adi.mony.accntManage.service.impl.AccntVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : DepositController.java
 * @Description : 부가서비스 > 금전처리 > 계정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.12  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.12
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/adi/mony/accntManage/")
public class DepositController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** service */
    @Autowired
    DepositService service;

    @Autowired
    SessionService sessionService;

    /**
     * 계정관리 - 화면 이동
     * @param request
     * @param response
     * @param model
     * @return String
     * @author 김지은
     * @since 2018.10.12
     */
    @RequestMapping(value = "deposit/depositView.sb", method = RequestMethod.GET)
    public String depositView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "adi/mony/accntManage/depositView";
    }

    /**
     * 계정 조회
     * @param accntVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deposit/getDepositAccntList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDepositAccntList(AccntVO accntVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> storeList = service.getDepositAccntList(accntVO, sessionInfoVO);

        return returnListJson(Status.OK, storeList, accntVO);
    }
//
//    /**
//     * 매장터미널관리 - 포스/터미널 설정 환경변수 조회
//     * @param storeEnvVO
//     * @param request
//     * @param response
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "terminalManage/getTerminalEnv.sb", method = RequestMethod.POST)
//    @ResponseBody
//    public Result getTerminalEnv(StoreEnvVO storeEnvVO, HttpServletRequest request,
//        HttpServletResponse response, Model model) {
//
//        String envstVal = service.getTerminalEnv(storeEnvVO);
//
//        return returnJson(Status.OK, envstVal);
//    }
//
//    /**
//     * 매장터미널관리 - 포스 목록 조회
//     * @param storePosVO
//     * @param request
//     * @param response
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "pos/getPosList.sb", method = RequestMethod.POST)
//    @ResponseBody
//    public Result getPosList(StorePosVO storePosVO, HttpServletRequest request,
//        HttpServletResponse response, Model model) {
//
//        List<DefaultMap<String>> posList = service.getPosList(storePosVO);
//
//        return returnListJson(Status.OK, posList, storePosVO);
//    }
//
//    /**
//     * POS VAN 정보 저장
//     * @param storePosVOs
//     * @param request
//     * @param response
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "pos/savePosInfo.sb", method = RequestMethod.POST)
//    @ResponseBody
//    public Result savePosInfo(@RequestBody StorePosVO[] storePosVOs, HttpServletRequest request,
//        HttpServletResponse response, Model model) {
//
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//
//        int result = 0;
//
//        try{
//            StoreEnvVO storeEnvVO = new StoreEnvVO();
//            storeEnvVO.setStoreCd(request.getParameter("storeCd"));
//            storeEnvVO.setEnvstVal(request.getParameter("terminalFgVal"));
//
//            int envstSaveResult = service.updateTerminalEnvst(storeEnvVO, sessionInfoVO);
//
//            result = service.savePosInfo(storePosVOs, sessionInfoVO);
//
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        return returnListJson(Status.OK, result);
//    }
//
//    /**
//     * 매장터미널관리 - 코너 목록 조회
//     * @param storeCornerVO
//     * @param request
//     * @param response
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "corner/getCornerList.sb", method = RequestMethod.POST)
//    @ResponseBody
//    public Result getCornerList(StoreCornerVO storeCornerVO, HttpServletRequest request,
//        HttpServletResponse response, Model model) {
//
//        List<DefaultMap<String>> posList = service.getCornerList(storeCornerVO);
//
//        return returnListJson(Status.OK, posList, storeCornerVO);
//    }
//
//
//    /**
//     * 코너 정보 저장
//     * @param storeCornerVOs
//     * @param request
//     * @param response
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "corner/saveCornerInfo.sb", method = RequestMethod.POST)
//    @ResponseBody
//    public Result saveCornerInfo(@RequestBody StoreCornerVO[] storeCornerVOs, HttpServletRequest request,
//        HttpServletResponse response, Model model) {
//
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//
//        int result = 0;
//
//        try{
//            StoreEnvVO storeEnvVO = new StoreEnvVO();
//            storeEnvVO.setStoreCd(request.getParameter("storeCd"));
//            storeEnvVO.setEnvstVal(request.getParameter("terminalFgVal"));
//
//            int envstSaveResult = service.updateTerminalEnvst(storeEnvVO, sessionInfoVO);
//
//            result = service.saveCornerInfo(storeCornerVOs, sessionInfoVO);
//
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        return returnListJson(Status.OK, result);
//    }


}
