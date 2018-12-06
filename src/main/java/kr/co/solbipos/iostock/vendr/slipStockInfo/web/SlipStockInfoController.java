package kr.co.solbipos.iostock.vendr.slipStockInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.vendr.slipStockInfo.service.SlipStockInfoService;
import kr.co.solbipos.iostock.vendr.slipStockInfo.service.SlipStockInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : SlipStockInfoController.java
 * @Description : 수불관리 > 거래처(매입)입출고관리 > 전표별 입출고내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.05  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 12.05
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/vendr/slipStockInfo")
public class SlipStockInfoController {
    private final SessionService sessionService;
    private final SlipStockInfoService slipStockInfoService;

    @Autowired
    public SlipStockInfoController(SessionService sessionService, SlipStockInfoService slipStockInfoService) {
        this.sessionService = sessionService;
        this.slipStockInfoService = slipStockInfoService;
    }

    /**
     * 거래처 전표별 입출고내역 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/slipStockInfo/view.sb", method = RequestMethod.GET)
    public String slipStockInfoView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/vendr/slipStockInfo/slipStockInfo";
    }


    /**
     * 거래처 전표별 입출고내역 - 전표별 입출고내역 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   slipStockInfoVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/slipStockInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipStockInfoList(HttpServletRequest request, HttpServletResponse response,
        Model model, SlipStockInfoVO slipStockInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = slipStockInfoService.getSlipStockInfoList(slipStockInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, slipStockInfoVO);
    }


    /**
     * 거래처 전표별 입출고내역 - 전표별 입출고내역 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   slipStockInfoVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/slipStockInfoDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipStockInfoDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, SlipStockInfoVO slipStockInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>>
            list = slipStockInfoService.getSlipStockInfoDtlList(slipStockInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, slipStockInfoVO);
    }
}
