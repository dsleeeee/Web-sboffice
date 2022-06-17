package kr.co.solbipos.dlvr.info.deliveryTelNo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.info.deliveryTelNo.service.DeliveryTelNoService;
import kr.co.solbipos.dlvr.info.deliveryTelNo.service.DeliveryTelNoVO;
import kr.co.solbipos.dlvr.info.dlvrExcelUpload.service.DlvrExcelUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : DeliveryTelNoController.java
 * @Description : 배달관리 > CID 배달정보 > 배달지정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.16  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.06.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/dlvr/manage/info")
public class DeliveryTelNoController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DeliveryTelNoService dlvrDeliveryTelNoService;
    private final SessionService sessionService;

    @Autowired
    public DeliveryTelNoController(DeliveryTelNoService dlvrDeliveryTelNoService, SessionService sessionService) {
        this.dlvrDeliveryTelNoService = dlvrDeliveryTelNoService;
        this.sessionService = sessionService;
    }

    /**
     * 배달지정보관리 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.16
     */
    @RequestMapping(value = "/deliveryTelNo/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "dlvr/info/deliveryTelNo/deliveryTelNo";
    }

    /**
     * 배달지정보관리 조회
     *
     * @param deliveryTelNoVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.16
     */
    @RequestMapping(value = "/deliveryTelNo/getDeliveryTelNoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDeliveryTelNoList(DeliveryTelNoVO deliveryTelNoVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dlvrDeliveryTelNoService.getDeliveryTelNoList(deliveryTelNoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, deliveryTelNoVO);
    }

    /**
     * 배달지정보관리 수정
     *
     * @param deliveryTelNoVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.06.16
     */
    @RequestMapping(value = "/deliveryTelNo/updateDeliveryTelNo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateDeliveryTelNo(@RequestBody DeliveryTelNoVO[] deliveryTelNoVOs, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dlvrDeliveryTelNoService.updateDeliveryTelNo(deliveryTelNoVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 배달지정보관리 전체삭제
     *
     * @param deliveryTelNoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.06.16
     */
    @RequestMapping(value = "/deliveryTelNo/deleteAllDeliveryTelNo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteAllDeliveryTelNo(DeliveryTelNoVO deliveryTelNoVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dlvrDeliveryTelNoService.deleteAllDeliveryTelNo(deliveryTelNoVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
