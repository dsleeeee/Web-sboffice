package kr.co.solbipos.iostock.order.dstbCloseStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.DstbCloseStoreService;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.DstbCloseStoreVO;
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
 * @Class Name : DstbCloseStoreController.java
 * @Description : 수불관리 > 수주관리 > 분배마감(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.20  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 09.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/iostock/order/dstbCloseStore")
public class DstbCloseStoreController {
    private final SessionService sessionService;
    private final DstbCloseStoreService dstbCloseStoreService;

    @Autowired
    public DstbCloseStoreController(SessionService sessionService, DstbCloseStoreService dstbCloseStoreService) {
        this.sessionService = sessionService;
        this.dstbCloseStoreService = dstbCloseStoreService;
    }

    /**
     * 분배마감 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 09. 20.
     */
    @RequestMapping(value = "/dstbCloseStore/view.sb", method = RequestMethod.GET)
    public String dstbCloseStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/order/dstbCloseStore/dstbCloseStore";
    }

    /**
     * 분배마감 - 분배 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 20.
     */
    @RequestMapping(value = "/dstbCloseStore/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseStoreList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseStoreVO dstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseStoreService.getDstbCloseStoreList(dstbCloseStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseStoreVO);
    }

    /**
     * 분배마감 - 분배 리스트 확정
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 20.
     */
    @RequestMapping(value = "/dstbCloseStore/saveConfirm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbCloseStoreConfirm(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbCloseStoreVO[] dstbCloseStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseStoreService.saveDstbCloseStoreConfirm(dstbCloseStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 분배마감 - 분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 20.
     */
    @RequestMapping(value = "/dstbCloseStoreDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbCloseStoreDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseStoreVO dstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseStoreService.getDstbCloseStoreDtlList(dstbCloseStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseStoreVO);
    }

    /**
     * 분배마감 - 분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 20.
     */
    @RequestMapping(value = "/dstbCloseStoreDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbCloseStoreDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbCloseStoreVO[] dstbCloseStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseStoreService.saveDstbCloseStoreDtl(dstbCloseStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 분배마감 - 추가분배시 주문가능여부 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 21.
     */
    @RequestMapping(value = "/dstbAddProd/getOrderFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderFg(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseStoreVO dstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = dstbCloseStoreService.getOrderFg(dstbCloseStoreVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 분배마감 - 추가분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 21.
     */
    @RequestMapping(value = "/dstbAddProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbAddProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, DstbCloseStoreVO dstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = dstbCloseStoreService.getDstbAddProdList(dstbCloseStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, dstbCloseStoreVO);
    }

    /**
     * 분배마감 - 추가분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   dstbCloseStoreVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 21.
     */
    @RequestMapping(value = "/dstbAddProd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbAddProd(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody DstbCloseStoreVO[] dstbCloseStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dstbCloseStoreService.saveDstbAddProd(dstbCloseStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
