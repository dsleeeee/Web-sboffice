package kr.co.solbipos.iostock.orderReturn.rtnInstockConfmStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.orderReturn.rtnInstockConfmStore.service.RtnInstockConfmStoreService;
import kr.co.solbipos.iostock.orderReturn.rtnInstockConfmStore.service.RtnInstockConfmStoreVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : RtnInstockConfmStoreController.java
 * @Description : 수불관리 > 반품관리 > 반품본사입고현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.09  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2024.07.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/orderReturn/rtnInstockConfmStore")
public class RtnInstockConfmStoreController {
    private final SessionService sessionService;
    private final RtnInstockConfmStoreService rtnInstockConfmStoreService;
    private final CmmEnvService cmmEnvService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
        public RtnInstockConfmStoreController(SessionService sessionService, RtnInstockConfmStoreService rtnInstockConfmStoreService, CmmEnvService cmmEnvService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.rtnInstockConfmStoreService = rtnInstockConfmStoreService;
        this.cmmEnvService = cmmEnvService;
        this.cmmEnvUtil = cmmEnvUtil;

    }

    /**
     * 반품본사입고현황 - 페이지 이동
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2024.07.09
     */
    @RequestMapping(value = "/rtnInstockConfmStore/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 매장입고 환경변수 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1043");
        String envst1043 = CmmUtil.nvl(cmmEnvService.getHqEnvst(hqEnvstVO), "Y");
        model.addAttribute("envst1043", envst1043);

        return "iostock/orderReturn/rtnInstockConfmStore/rtnInstockConfmStore";
    }

    /**
     * 반품본사입고현황 - 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param rtnInstockConfmStoreVO
     * @return
     * @author 이다솜
     * @since 2024.07.09
     */
    @RequestMapping(value = "/rtnInstockConfmStore/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(HttpServletRequest request, HttpServletResponse response, Model model, RtnInstockConfmStoreVO rtnInstockConfmStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = rtnInstockConfmStoreService.getRtnInstockConfmStoreList(rtnInstockConfmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnInstockConfmStoreVO);
    }

    /**
     * 반품본사입고현황 - 전표 상세 조회
     * @param request
     * @param response
     * @param rtnInstockConfmStoreVO
     * @return
     * @author 이다솜
     * @since 2024.07.09
     */
    @RequestMapping(value = "/rtnInstockConfmStore/getSlipNoInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipNoInfo(HttpServletRequest request, HttpServletResponse response, RtnInstockConfmStoreVO rtnInstockConfmStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = rtnInstockConfmStoreService.getSlipNoInfo(rtnInstockConfmStoreVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품본사입고현황 - 전표 상세 리스트 조회
     * @param request
     * @param response
     * @param rtnInstockConfmStoreVO
     * @return
     * @author 이다솜
     * @since 2024.07.09
     */
    @RequestMapping(value = "/rtnInstockConfmStore/dtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dtlList(HttpServletRequest request, HttpServletResponse response, RtnInstockConfmStoreVO rtnInstockConfmStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = rtnInstockConfmStoreService.getRtnInstockConfmStoreDtlList(rtnInstockConfmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnInstockConfmStoreVO);
    }

}
