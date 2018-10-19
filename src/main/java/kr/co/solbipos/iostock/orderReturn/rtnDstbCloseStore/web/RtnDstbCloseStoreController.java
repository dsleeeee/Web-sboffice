package kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.RtnDstbCloseStoreService;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.RtnDstbCloseStoreVO;
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
 * @Class Name : RtnDstbCloseStoreController.java
 * @Description : 수불관리 > 매장반품관리 > 반품마감(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.16  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 10.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/iostock/orderReturn/rtnDstbCloseStore")
public class RtnDstbCloseStoreController {
    private final SessionService sessionService;
    private final RtnDstbCloseStoreService rtnDstbCloseStoreService;

    @Autowired
    public RtnDstbCloseStoreController(SessionService sessionService, RtnDstbCloseStoreService rtnDstbCloseStoreService) {
        this.sessionService = sessionService;
        this.rtnDstbCloseStoreService = rtnDstbCloseStoreService;
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
    @RequestMapping(value = "/rtnDstbCloseStore/view.sb", method = RequestMethod.GET)
    public String rtnDstbCloseStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStore";
    }

    /**
     * 반품마감 - 분배 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStore/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnDstbCloseStoreList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstbCloseStoreVO rtnDstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstbCloseStoreService.getRtnDstbCloseStoreList(rtnDstbCloseStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstbCloseStoreVO);
    }

    /**
     * 반품마감 - 분배 리스트 확정
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseStoreVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStore/saveConfirm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnDstbCloseStoreConfirm(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstbCloseStoreService.saveRtnDstbCloseStoreConfirm(rtnDstbCloseStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품마감 - 분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStoreDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnDstbCloseStoreDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstbCloseStoreVO rtnDstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstbCloseStoreService.getRtnDstbCloseStoreDtlList(rtnDstbCloseStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstbCloseStoreVO);
    }

    /**
     * 반품마감 - 분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseStoreVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStoreDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnDstbCloseStoreDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstbCloseStoreService.saveRtnDstbCloseStoreDtl(rtnDstbCloseStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품마감 - 추가분배 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseStoreVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStoreAdd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDstbAddList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnDstbCloseStoreVO rtnDstbCloseStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnDstbCloseStoreService.getDstbAddList(rtnDstbCloseStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnDstbCloseStoreVO);
    }

    /**
     * 반품마감 - 추가분배 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnDstbCloseStoreVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 16.
     */
    @RequestMapping(value = "/rtnDstbCloseStoreAdd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDstbAdd(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnDstbCloseStoreService.saveDstbAdd(rtnDstbCloseStoreVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
