package kr.co.solbipos.iostock.order.dstmn.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.dstmn.service.DstmnService;
import kr.co.solbipos.iostock.order.dstmn.service.DstmnVO;
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
 * @Class Name : DstmnController.java
 * @Description : 수불관리 > 수주관리 > 거래명세표
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.11  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018.10.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/order/dstmn")
public class DstmnController {
    private final SessionService sessionService;
    private final DstmnService dstmnService;

    @Autowired
    public DstmnController(SessionService sessionService, DstmnService dstmnService) {
        this.sessionService = sessionService;
        this.dstmnService = dstmnService;
    }

    /**
     * 거래명세표 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 11.
     */
    @RequestMapping(value = "/dstmn/view.sb", method = RequestMethod.GET)
    public String outstockDataView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/order/dstmn/dstmn";
    }

    /**
     * 거래명세표 - 매장요청 미확정건, 출고자료 미생성건 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstmnVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 11.
     */
    @RequestMapping(value = "/dstmn/reqNoConfirmCnt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getReqNoConfirmCnt(HttpServletRequest request, HttpServletResponse response,
        Model model, DstmnVO dstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstmnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = dstmnService.getReqNoConfirmCnt(dstmnVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 거래명세표 - 거래명세표 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstmnVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 11.
     */
    @RequestMapping(value = "/dstmn/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstmnList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstmnVO dstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstmnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstmnService.getDstmnList(dstmnVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstmnVO);
    }

    /**
     * 거래명세표 - 전표상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstmnVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 11.
     */
    @RequestMapping(value = "/dstmnDtl/getSlipNoInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipNoInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, DstmnVO dstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstmnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = dstmnService.getSlipNoInfo(dstmnVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 거래명세표 - 거래명세표 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstmnVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 11.
     */
    @RequestMapping(value = "/dstmnDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstmnDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstmnVO dstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstmnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//        List<DefaultMap<String>> list = dstmnService.getDstmnDtlList(dstmnVO);
        List<DefaultMap<String>> list = dstmnService.getDstmnDtlList(dstmnVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstmnVO);
    }

    /**
     * 거래명세표 - 거래명세표 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   dstmnVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 11.
     */
    @RequestMapping(value = "/dstmnDtl/save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstmnDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstmnVO[] dstmnVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstmnService.saveDstmnDtl(dstmnVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 거래명세표 - 출고확정 이후 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   dstmnVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 11.
     */
    @RequestMapping(value = "/dstmnDtl/saveOutstockAfter.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveOutstockAfter(HttpServletRequest request, HttpServletResponse response,
        Model model, DstmnVO dstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstmnService.saveOutstockAfter(dstmnVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래명세표 - 세금계산서 공급자 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstmnVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 02.
     */
    @RequestMapping(value = "/taxReport/supplierInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSupplierInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, DstmnVO dstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = dstmnService.getSupplierInfo(dstmnVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래명세표 - 세금계산서 전표 내역 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstmnVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 02.
     */
    @RequestMapping(value = "/taxReport/taxReportInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTaxReportInfoList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstmnVO dstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dstmnService.getTaxReportInfoList(dstmnVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstmnVO);
    }


    /**
     * 거래명세표 - 거래명세표 전표 내역 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstmnVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 03.
     */
    @RequestMapping(value = "/transReport/transReportInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTransReportInfoList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstmnVO dstmnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dstmnService.getTransReportInfoList(dstmnVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstmnVO);
    }
}
