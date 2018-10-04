package kr.co.solbipos.iostock.order.dstbCloseProd.web;
/**
 * @Class Name : DstbCloseProdController.java
 * @Description : 수불관리 > 수주관리 > 분배마감(상품별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.01  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018.10.01
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
import kr.co.solbipos.iostock.order.dstbCloseProd.service.DstbCloseProdService;
import kr.co.solbipos.iostock.order.dstbCloseProd.service.DstbCloseProdVO;
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
@RequestMapping("/iostock/order/dstbCloseProd")
public class DstbCloseProdController {
    private final SessionService sessionService;
    private final DstbCloseProdService dstbCloseProdService;

    @Autowired
    public DstbCloseProdController(SessionService sessionService, DstbCloseProdService dstbCloseProdService) {
        this.sessionService = sessionService;
        this.dstbCloseProdService = dstbCloseProdService;
    }

    /**
     * 분배마감 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 01.
     */
    @RequestMapping(value = "/dstbCloseProd/view.sb", method = RequestMethod.GET)
    public String dstbCloseProdView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/order/dstbCloseProd/dstbCloseProd";
    }

    /**
     * 분배마감 - 분배 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 01.
     */
    @RequestMapping(value = "/dstbCloseProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseProdVO dstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseProdService.getDstbCloseProdList(dstbCloseProdVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseProdVO);
    }

    /**
     * 분배마감 - 분배 리스트 확정
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 01.
     */
    @RequestMapping(value = "/dstbCloseProd/saveConfirm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbCloseProdConfirm(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbCloseProdVO[] dstbCloseProdVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseProdService.saveDstbCloseProdConfirm(dstbCloseProdVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 분배마감 - 분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 01.
     */
    @RequestMapping(value = "/dstbCloseProdDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseProdDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseProdVO dstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseProdService.getDstbCloseProdDtlList(dstbCloseProdVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseProdVO);
    }

    /**
     * 분배마감 - 분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 01.
     */
    @RequestMapping(value = "/dstbCloseProdDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbCloseProdDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbCloseProdVO[] dstbCloseProdVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseProdService.saveDstbCloseProdDtl(dstbCloseProdVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 분배마감 - 추가등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 02.
     */
    @RequestMapping(value = "/dstbCloseProdAddProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseProdAddProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseProdVO dstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseProdService.getDstbCloseProdAddProdList(dstbCloseProdVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseProdVO);
    }

    /**
     * 분배마감 - 추가등록 분배등록 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 02.
     */
    @RequestMapping(value = "/dstbCloseProdAddRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseProdAddRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseProdVO dstbCloseProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseProdService.getDstbCloseProdAddRegistList(dstbCloseProdVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseProdVO);
    }

    /**
     * 분배마감 - 추가분배 분배등록 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseProdVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 02.
     */
    @RequestMapping(value = "/dstbCloseProdAddRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbCloseProdAddRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbCloseProdVO[] dstbCloseProdVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseProdService.saveDstbCloseProdAddRegist(dstbCloseProdVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
