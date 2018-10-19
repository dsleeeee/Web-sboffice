package kr.co.solbipos.iostock.orderReturn.rtnDstmn.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.dstmn.service.DstmnVO;
import kr.co.solbipos.iostock.orderReturn.rtnDstmn.service.RtnDstmnService;
import kr.co.solbipos.iostock.orderReturn.rtnDstmn.service.RtnDstmnVO;
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

/**
 * @Class Name : RtnDstmnController.java
 * @Description : 수불관리 > 매장반품관리 > 반품명세표
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.17  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018.10.17
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/orderReturn/rtnDstmn")
public class RtnDstmnController {
    private final SessionService sessionService;
    private final RtnDstmnService rtnDstmnService;

    @Autowired
    public RtnDstmnController(SessionService sessionService, RtnDstmnService rtnDstmnService) {
        this.sessionService = sessionService;
        this.rtnDstmnService = rtnDstmnService;
    }

    /**
     * 반품명세표 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnDstmn/view.sb", method = RequestMethod.GET)
    public String outstockDataView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/orderReturn/rtnDstmn/rtnDstmn";
    }

    /**
     * 반품명세표 - 매장요청 미확정건, 출고자료 미생성건 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstmnVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnDstmn/reqNoConfirmCnt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getReqNoConfirmCnt(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstmnVO rtnDstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstmnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = rtnDstmnService.getReqNoConfirmCnt(rtnDstmnVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품명세표 - 반품명세표 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstmnVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnDstmn/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnDstmnList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstmnVO rtnDstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstmnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstmnService.getRtnDstmnList(rtnDstmnVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstmnVO);
    }

    /**
     * 반품명세표 - 전표상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstmnVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnDstmnDtl/getSlipNoInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipNoInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstmnVO rtnDstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstmnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = rtnDstmnService.getSlipNoInfo(rtnDstmnVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품명세표 - 반품명세표 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstmnVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnDstmnDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnDstmnDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstmnVO rtnDstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstmnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstmnService.getRtnDstmnDtlList(rtnDstmnVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstmnVO);
    }

    /**
     * 반품명세표 - 반품명세표 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstmnVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnDstmnDtl/save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnDstmnDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnDstmnVO[] rtnDstmnVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstmnService.saveRtnDstmnDtl(rtnDstmnVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품명세표 - 반품매장출고 이후 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstmnVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnDstmnDtl/saveOutstockAfter.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveOutstockAfter(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstmnVO rtnDstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstmnService.saveOutstockAfter(rtnDstmnVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
