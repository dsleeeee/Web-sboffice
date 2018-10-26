package kr.co.solbipos.iostock.order.storeClose.web;

/**
 * @Class Name : StoreCloseController.java
 * @Description : 수불관리 > 수주관리 > 매장요청마감
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.07  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 09.07
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
import kr.co.solbipos.iostock.order.storeClose.service.StoreCloseService;
import kr.co.solbipos.iostock.order.storeClose.service.StoreCloseVO;
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
@RequestMapping("/iostock/order/storeClose")
public class StoreCloseController {

    private final SessionService sessionService;
    private final StoreCloseService storeCloseService;

    /** Constructor Injection */
    @Autowired
    public StoreCloseController(SessionService sessionService, StoreCloseService storeCloseService) {
        this.sessionService = sessionService;
        this.storeCloseService = storeCloseService;
    }

    /**
     * 매장요청마감 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 09. 07.
     */
    @RequestMapping(value = "/storeClose/view.sb", method = RequestMethod.GET)
    public String storeCloseView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/order/storeClose/storeClose";

    }

    /**
     * 매장요청마감 - 마감월 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeCloseVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 07.
     */
    @RequestMapping(value = "/storeClose/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreCloseList(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreCloseVO storeCloseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeCloseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = storeCloseService.getStoreCloseList(storeCloseVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeCloseVO);
    }

    /**
     * 매장요청마감 - 마감월 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeCloseVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 07.
     */
    @RequestMapping(value = "/storeClose/dtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreCloseDtlList(HttpServletRequest request, HttpServletResponse response, Model model, StoreCloseVO storeCloseVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeCloseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = storeCloseService.getStoreCloseDtlList(storeCloseVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeCloseVO);
    }


    /**
     * 매장요청마감 - 마감일 마감여부 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   storeCloseVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 07.
     */
    @RequestMapping(value = "/storeClose/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreClose(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody StoreCloseVO[] storeCloseVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeCloseService.saveStoreClose(storeCloseVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

}
