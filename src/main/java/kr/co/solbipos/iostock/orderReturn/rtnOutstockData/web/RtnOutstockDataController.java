package kr.co.solbipos.iostock.orderReturn.rtnOutstockData.web;
/**
 * @Class Name : RtnOutstockDataController.java
 * @Description : 수불관리 > 매장반품관리 > 반품자료생성
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.17  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018.10.17
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
import kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.RtnOutstockDataService;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.RtnOutstockDataVO;
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
@RequestMapping("/iostock/orderReturn/rtnOutstockData")
public class RtnOutstockDataController {
    private final SessionService sessionService;
    private final RtnOutstockDataService rtnOutstockDataService;

    @Autowired
    public RtnOutstockDataController(SessionService sessionService, RtnOutstockDataService rtnOutstockDataService) {
        this.sessionService = sessionService;
        this.rtnOutstockDataService = rtnOutstockDataService;
    }

    /**
     * 반품자료생성 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockData/view.sb", method = RequestMethod.GET)
    public String rtnOutstockDataView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/orderReturn/rtnOutstockData/rtnOutstockData";
    }

    /**
     * 반품자료생성 - 반품자료생성 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockDataVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockData/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnOutstockDataList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnOutstockDataVO rtnOutstockDataVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnOutstockDataVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnOutstockDataService.getRtnOutstockDataList(rtnOutstockDataVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnOutstockDataVO);
    }

    /**
     * 반품자료생성 - 반품자료생성
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockDataVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockData/saveDataCreate", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDataCreate(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody RtnOutstockDataVO[] rtnOutstockDataVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = rtnOutstockDataService.saveDataCreate(rtnOutstockDataVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 반품자료생성 - 반품자료생성 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   rtnOutstockDataVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 17.
     */
    @RequestMapping(value = "/rtnOutstockDataDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnOutstockDataDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnOutstockDataVO rtnOutstockDataVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        rtnOutstockDataVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = rtnOutstockDataService.getRtnOutstockDataDtlList(rtnOutstockDataVO);

        return ReturnUtil.returnListJson(Status.OK, list, rtnOutstockDataVO);
    }
}
