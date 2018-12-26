package kr.co.solbipos.iostock.order.outstockReqDate.web;

/**
 * @Class Name : OutstockReqDateController.java
 * @Description : 수불관리 > 수주관리 > 출고요청일관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.03  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 09.03
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
import kr.co.solbipos.iostock.order.outstockReqDate.service.OutstockReqDateService;
import kr.co.solbipos.iostock.order.outstockReqDate.service.OutstockReqDateVO;
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
@RequestMapping("/iostock/order/outstockReqDate")
public class OutstockReqDateController {

    @Autowired
    SessionService sessionService;

    @Autowired
    OutstockReqDateService outstockReqDateService;


    /**
     * 출고요청일관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 09. 03.
     */
    @RequestMapping(value = "/outstockReqDate/view.sb", method = RequestMethod.GET)
    public String daysView(HttpServletRequest request, HttpServletResponse response,
        Model model) {
        return "iostock/order/outstockReqDate/outstockReqDate";
    }


    /**
     * 출고요청일관리 - 요일별 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockReqDateVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 03.
     */
    @RequestMapping(value = "/days/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result daysList(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockReqDateVO outstockReqDateVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        outstockReqDateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = outstockReqDateService.getDaysList(outstockReqDateVO);

        return ReturnUtil.returnListJson(Status.OK, list, outstockReqDateVO);
    }


    /**
     * 출고요청일관리 - 요일별 출고가능일 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockReqDateVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 04.
     */
    @RequestMapping(value = "/days/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveReqDateDays(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody OutstockReqDateVO[] outstockReqDateVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = outstockReqDateService.saveReqDateDays(outstockReqDateVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 출고요청일관리 - 특정일 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockReqDateVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 04.
     */
    @RequestMapping(value = "specificDate/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSpecificDateList(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockReqDateVO outstockReqDateVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        outstockReqDateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = outstockReqDateService.getSpecificDateList(outstockReqDateVO);

        return ReturnUtil.returnListJson(Status.OK, list, outstockReqDateVO);
    }


    /**
     * 출고요청일관리 - 특정일 신규저장
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockReqDateVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 04.
     */
    @RequestMapping(value = "/specificDate/saveNew.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveNewSpecificDate(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockReqDateVO outstockReqDateVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = outstockReqDateService.saveNewSpecificDate(outstockReqDateVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 출고요청일관리 - 특정일 수정
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockReqDateVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 04.
     */
    @RequestMapping(value = "/specificDate/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSpecificDate(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody OutstockReqDateVO[] outstockReqDateVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = outstockReqDateService.saveSpecificDate(outstockReqDateVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 출고요청일관리 - 특정일 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockReqDateVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 04.
     */
    @RequestMapping(value = "/specificDate/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteSpecificDate(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody OutstockReqDateVO[] outstockReqDateVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = outstockReqDateService.deleteSpecificDate(outstockReqDateVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 출고요청일관리 - 특정일 복사
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockReqDateVOs
     * @return  String
     * @author  안동관
     * @since   2018. 09. 06.
     */
    @RequestMapping(value = "/specificDate/copy.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copySpecificDate(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody OutstockReqDateVO[] outstockReqDateVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = outstockReqDateService.copySpecificDate(outstockReqDateVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 출고요청일관리 - 요일 복사
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockReqDateVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 06.
     */
    @RequestMapping(value = "/days/copy.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyDays(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockReqDateVO outstockReqDateVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = outstockReqDateService.copyDays(outstockReqDateVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

}
