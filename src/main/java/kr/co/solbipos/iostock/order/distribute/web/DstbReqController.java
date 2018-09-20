package kr.co.solbipos.iostock.order.distribute.web;
/**
 * @Class Name : dstbReqController.java
 * @Description : 수불관리 > 수주관리 > 분배등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.17  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 09.17
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.distribute.service.DstbReqService;
import kr.co.solbipos.iostock.order.distribute.service.DstbReqVO;
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

@Controller
@RequestMapping("/iostock/order/distribute")
public class DstbReqController {
    private final SessionService sessionService;
    private final DstbReqService dstbReqService;

    @Autowired
    public DstbReqController(SessionService sessionService, DstbReqService dstbReqService) {
        this.sessionService = sessionService;
        this.dstbReqService = dstbReqService;
    }

    /**
     * 분배등록 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 09. 17.
     */
    @RequestMapping(value = "/dstbReq/view.sb", method = RequestMethod.GET)
    public String storeCloseView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/order/distribute/dstbReq";
    }


    /**
     * 분배등록 - 분배 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbReqVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 18.
     */
    @RequestMapping(value = "/dstbReq/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbReqList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbReqVO dstbReqVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbReqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dstbReqVO.setStoreCd(sessionInfoVO.getStoreCd());

        List<DefaultMap<String>> list = dstbReqService.getDstbReqList(dstbReqVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbReqVO);
    }

    /**
     * 분배등록 - 분배완료
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbReqVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 19.
     */
    @RequestMapping(value = "/dstbReq/saveDstbConfirm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbConfirm(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbReqVO[] dstbReqVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbReqService.saveDstbConfirm(dstbReqVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 분배등록 - 분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbReqVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 18.
     */
    @RequestMapping(value = "/dstbReqDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbReqDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbReqVO dstbReqVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbReqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbReqService.getDstbReqDtlList(dstbReqVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbReqVO);
    }

    /**
     * 분배등록 - 분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbReqVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 18.
     */
    @RequestMapping(value = "/dstbReqDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbReqDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbReqVO[] dstbReqVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbReqService.saveDstbReqDtl(dstbReqVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
