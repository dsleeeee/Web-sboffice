package kr.co.solbipos.sale.cmmSalePopup.dcInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.dcInfo.service.DcInfoService;
import kr.co.solbipos.sale.cmmSalePopup.dcInfo.service.DcInfoVO;
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
 * @Class Name : DcInfoController.java
 * @Description : 매출공통팝업 - 할인 상세 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.02.13  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2019.02.13
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/cmmSalePopup/dcInfo")
public class DcInfoController {
    private final SessionService sessionService;
    private final DcInfoService dcInfoService;

    @Autowired
    public DcInfoController(SessionService sessionService, DcInfoService dcInfoService) {
        this.sessionService = sessionService;
        this.dcInfoService = dcInfoService;
    }


    /**
     * 당일매출상세현황 - 일반할인 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dcInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 13.
     */
    @RequestMapping(value = "/generalDc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getGeneralDcList(HttpServletRequest request, HttpServletResponse response,
        Model model, DcInfoVO dcInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcInfoService.getGeneralDcList(dcInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcInfoVO);
    }


    /**
     * 당일매출상세현황 - 쿠폰할인 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dcInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 13.
     */
    @RequestMapping(value = "/coupnDc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCoupnDcList(HttpServletRequest request, HttpServletResponse response,
        Model model, DcInfoVO dcInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcInfoService.getCoupnDcList(dcInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcInfoVO);
    }


    /**
     * 당일매출상세현황 - 회원할인 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dcInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 13.
     */
    @RequestMapping(value = "/membrDc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMembrDcList(HttpServletRequest request, HttpServletResponse response,
        Model model, DcInfoVO dcInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcInfoService.getMembrDcList(dcInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcInfoVO);
    }


    /**
     * 당일매출상세현황 - 제휴할인 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dcInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 13.
     */
    @RequestMapping(value = "/partnerDc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPartnerDcList(HttpServletRequest request, HttpServletResponse response,
        Model model, DcInfoVO dcInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcInfoService.getPartnerDcList(dcInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcInfoVO);
    }

    /**
     * 당일매출상세현황 - 서비스할인 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dcInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 13.
     */
    @RequestMapping(value = "/serviceDc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getServiceDcList(HttpServletRequest request, HttpServletResponse response,
        Model model, DcInfoVO dcInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcInfoService.getServiceDcList(dcInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcInfoVO);
    }


    /**
     * 당일매출상세현황 - 프로모션할인 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dcInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 13.
     */
    @RequestMapping(value = "/promtnDc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromtnDcList(HttpServletRequest request, HttpServletResponse response,
        Model model, DcInfoVO dcInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcInfoService.getPromtnDcList(dcInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcInfoVO);
    }


    /**
     * 당일매출상세현황 - 포장할인 상세 리스트 조회 (할인수단에 컬럼이 있어 매핑은 만들어두긴 하지만 현재(2019.02.14) 사용하지는 않고 있음. 나중에 사용하게 되면 페이지만 만들면 될듯..)
     * @param   request
     * @param   response
     * @param   model
     * @param   dcInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 13.
     */
    @RequestMapping(value = "/packDc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPackDcList(HttpServletRequest request, HttpServletResponse response,
        Model model, DcInfoVO dcInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcInfoService.getPackDcList(dcInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcInfoVO);
    }


    /**
     * 당일매출상세현황 - 현장할인 상세 리스트 조회 (할인수단에 컬럼이 있어 매핑은 만들어두긴 하지만 현재(2019.02.14) 사용하지는 않고 있음. 나중에 사용하게 되면 페이지만 만들면 될듯..)
     * @param   request
     * @param   response
     * @param   model
     * @param   dcInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 13.
     */
    @RequestMapping(value = "/siteDc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSiteDcList(HttpServletRequest request, HttpServletResponse response,
        Model model, DcInfoVO dcInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcInfoService.getSiteDcList(dcInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcInfoVO);
    }


    /**
     * 당일매출상세현황 - VMEM 쿠폰할인 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dcInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 13.
     */
    @RequestMapping(value = "/vcoupnDc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVcoupnDcList(HttpServletRequest request, HttpServletResponse response,
        Model model, DcInfoVO dcInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcInfoService.getVcoupnDcList(dcInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcInfoVO);
    }

    /**
     * 당일매출상세현황 - 스마트오더 할인 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dcInfoVO
     * @return  String
     * @author  권지현
     * @since   2021.08.11
     */
    @RequestMapping(value = "/vorderDc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmartorderDcList(HttpServletRequest request, HttpServletResponse response,
                                  Model model, DcInfoVO dcInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcInfoService.getSmartorderDcList(dcInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcInfoVO);
    }
}
