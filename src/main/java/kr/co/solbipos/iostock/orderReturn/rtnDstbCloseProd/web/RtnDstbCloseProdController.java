package kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service.RtnDstbCloseProdService;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service.RtnDstbCloseProdVO;
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
 * @Class Name : RtnDstbCloseProdController.java
 * @Description : 수불관리 > 반품관리 > 반품마감(상품별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.16  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018.10.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/iostock/orderReturn/rtnDstbCloseProd")
public class RtnDstbCloseProdController {
    private final SessionService sessionService;
    private final RtnDstbCloseProdService rtnDstbCloseProdService;

    @Autowired
    public RtnDstbCloseProdController(SessionService sessionService, RtnDstbCloseProdService rtnDstbCloseProdService) {
        this.sessionService = sessionService;
        this.rtnDstbCloseProdService = rtnDstbCloseProdService;
    }

    /**
     * 반품마감 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseProd/view.sb", method = RequestMethod.GET)
    public String rtnDstbCloseProdView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/order/rtnDstbCloseProd/rtnDstbCloseProd";
    }

    /**
     * 반품마감 - 분배 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseProdVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnDstbCloseProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstbCloseProdVO rtnDstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstbCloseProdService.getRtnDstbCloseProdList(rtnDstbCloseProdVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstbCloseProdVO);
    }

    /**
     * 반품마감 - 분배 리스트 확정
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseProdVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseProd/saveConfirm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnDstbCloseProdConfirm(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnDstbCloseProdVO[] rtnDstbCloseProdVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstbCloseProdService.saveRtnDstbCloseProdConfirm(rtnDstbCloseProdVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품마감 - 분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseProdVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseProdDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnDstbCloseProdDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstbCloseProdVO rtnDstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstbCloseProdService.getRtnDstbCloseProdDtlList(rtnDstbCloseProdVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstbCloseProdVO);
    }

    /**
     * 반품마감 - 분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseProdVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseProdDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnDstbCloseProdDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnDstbCloseProdVO[] rtnDstbCloseProdVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstbCloseProdService.saveRtnDstbCloseProdDtl(rtnDstbCloseProdVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품마감 - 추가등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseProdVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseProdAddProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnDstbCloseProdAddProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstbCloseProdVO rtnDstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstbCloseProdService.getRtnDstbCloseProdAddProdList(rtnDstbCloseProdVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstbCloseProdVO);
    }

    /**
     * 반품마감 - 추가등록 분배등록 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseProdVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseProdAddRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnDstbCloseProdAddRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstbCloseProdVO rtnDstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstbCloseProdService.getRtnDstbCloseProdAddRegistList(rtnDstbCloseProdVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstbCloseProdVO);
    }

    /**
     * 반품마감 - 추가분배 분배등록 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseProdVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseProdAddRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnDstbCloseProdAddRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnDstbCloseProdVO[] rtnDstbCloseProdVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstbCloseProdService.saveRtnDstbCloseProdAddRegist(rtnDstbCloseProdVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
