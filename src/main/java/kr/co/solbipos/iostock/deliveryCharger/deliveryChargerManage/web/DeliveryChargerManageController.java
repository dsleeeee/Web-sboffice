package kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.web;

/**
 * @Class Name : deliveryChargerManageController.java
 * @Description : 수불관리 > 수주관리 > 배송기사관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.29  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 08.29
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
import kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.service.DeliveryChargerManageService;
import kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.service.DeliveryChargerManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value="/iostock/deliveryCharger/deliveryChargerManage")
public class DeliveryChargerManageController {

    @Autowired
    DeliveryChargerManageService deliveryChargerManageService;

    @Autowired
    SessionService sessionService;


    /**
     * 배송기사관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 08. 29.
     */
    @RequestMapping(value = "/deliveryChargerList/view.sb", method = RequestMethod.GET)
    public String deliveryChargerManageView(HttpServletRequest request, HttpServletResponse response,
        Model model) {
        return "iostock/deliveryCharger/deliveryChargerManage/deliveryChargerList";
    }

    /**
     * 배송기사관리 - 배송기사 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   deliveryChargerManageVO
     * @return  String
     * @author  안동관
     * @since   2018. 08. 29.
     */
    @RequestMapping(value = "/deliveryChargerList/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deliveryChargerList(HttpServletRequest request, HttpServletResponse response,
        Model model, DeliveryChargerManageVO deliveryChargerManageVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        deliveryChargerManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = deliveryChargerManageService.getDeliveryChargerList(deliveryChargerManageVO);

        return ReturnUtil.returnListJson(Status.OK, list, deliveryChargerManageVO);
    }

    /**
     * 배송기사관리 - 배송기사 상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   deliveryChargerManageVO
     * @return  String
     * @author  안동관
     * @since   2018. 08. 29.
     */
    @RequestMapping(value = "/deliveryChargerRegist/dlvrInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deliveryChargerRegistDlvrInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, DeliveryChargerManageVO deliveryChargerManageVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        deliveryChargerManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = deliveryChargerManageService.getDlvrInfo(deliveryChargerManageVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 배송기사관리 - 배송기사 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   deliveryChargerManageVO
     * @return  String
     * @author  안동관
     * @since   2018. 08. 29.
     */
    @RequestMapping(value = "/deliveryChargerRegist/dlvrSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deliveryChargerRegistDlvrSave(HttpServletRequest request, HttpServletResponse response,
        Model model, DeliveryChargerManageVO deliveryChargerManageVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        deliveryChargerManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        deliveryChargerManageVO.setRegId(sessionInfoVO.getUserId());
        deliveryChargerManageVO.setModId(sessionInfoVO.getUserId());

        int result = deliveryChargerManageService.saveDeliveryCharger(deliveryChargerManageVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 배송기사관리 - 배송기사 담당 창고 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   deliveryChargerManageVO
     * @return  String
     * @author  안동관
     * @since   2018. 08. 30.
     */
    @RequestMapping(value = "/deliveryChargerRegist/storageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deliveryChargerStorageList(HttpServletRequest request, HttpServletResponse response,
        Model model, DeliveryChargerManageVO deliveryChargerManageVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        deliveryChargerManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = deliveryChargerManageService.getDeliveryChargerStorageList(deliveryChargerManageVO);

        return ReturnUtil.returnListJson(Status.OK, list, deliveryChargerManageVO);
    }
}
