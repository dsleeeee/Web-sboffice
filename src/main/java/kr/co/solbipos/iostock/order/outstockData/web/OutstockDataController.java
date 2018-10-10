package kr.co.solbipos.iostock.order.outstockData.web;
/**
 * @Class Name : OutstockDataController.java
 * @Description : 수불관리 > 수주관리 > 출고자료생성
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.04  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018.10.04
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
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataService;
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataVO;
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
@RequestMapping("/iostock/order/outstockData")
public class OutstockDataController {
    private final SessionService sessionService;
    private final OutstockDataService outstockDataService;

    @Autowired
    public OutstockDataController(SessionService sessionService, OutstockDataService outstockDataService) {
        this.sessionService = sessionService;
        this.outstockDataService = outstockDataService;
    }

    /**
     * 출고자료생성 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 04.
     */
    @RequestMapping(value = "/outstockData/view.sb", method = RequestMethod.GET)
    public String outstockDataView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/order/outstockData/outstockData";
    }

    /**
     * 출고자료생성 - 출고자료생성 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockDataVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 04.
     */
    @RequestMapping(value = "/outstockData/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOutstockDataList(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockDataVO outstockDataVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        outstockDataVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = outstockDataService.getOutstockDataList(outstockDataVO);

        return ReturnUtil.returnListJson(Status.OK, list, outstockDataVO);
    }

    /**
     * 출고자료생성 - 출고자료생성
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockDataVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 04.
     */
    @RequestMapping(value = "/outstockData/saveDataCreate", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDataCreate(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody OutstockDataVO[] outstockDataVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = outstockDataService.saveDataCreate(outstockDataVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 출고자료생성 - 출고자료생성 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockDataVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 04.
     */
    @RequestMapping(value = "/outstockDataDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOutstockDataDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockDataVO outstockDataVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        outstockDataVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = outstockDataService.getOutstockDataDtlList(outstockDataVO);

        return ReturnUtil.returnListJson(Status.OK, list, outstockDataVO);
    }
}
