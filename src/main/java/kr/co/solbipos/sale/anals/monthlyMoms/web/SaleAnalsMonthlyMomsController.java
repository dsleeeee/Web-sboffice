package kr.co.solbipos.sale.anals.monthlyMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.monthlyMoms.service.SaleAnalsMonthlyMomsService;
import kr.co.solbipos.sale.anals.monthlyMoms.service.SaleAnalsMonthlyMomsVO;
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
 * @Class Name : SaleAnalsMonthlyMomsController.java
 * @Description : 맘스터치 > 매출분석 > 월력판매분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.04.13
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/anals/monthlyMoms")
public class SaleAnalsMonthlyMomsController {
    private final SessionService sessionService;
    private final SaleAnalsMonthlyMomsService SaleAnalsMonthlyMomsService;

    @Autowired
    public SaleAnalsMonthlyMomsController(SessionService sessionService, SaleAnalsMonthlyMomsService SaleAnalsMonthlyMomsService) {
        this.sessionService = sessionService;
        this.SaleAnalsMonthlyMomsService = SaleAnalsMonthlyMomsService;
    }

    /**
     * 월력판매분석 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2023.04.13
     */
    @RequestMapping(value = "/monthlyMoms/list.sb", method = RequestMethod.GET)
    public String SaleAnalsMonthlyMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/anals/monthlyMoms/saleAnalsMonthlyMoms";
    }


    /**
     * 월력판매분석 - 월력판매분석 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleAnalsMonthlyMomsVO
     * @return  String
     * @author  권지현
     * @since   2023.04.13
     */
    @RequestMapping(value = "/SaleAnalsMonthlyMoms/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleAnalsMonthlyMomsList(HttpServletRequest request, HttpServletResponse response,
        Model model, SaleAnalsMonthlyMomsVO SaleAnalsMonthlyMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = SaleAnalsMonthlyMomsService.getSaleAnalsMonthlyMomsList(SaleAnalsMonthlyMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, SaleAnalsMonthlyMomsVO);
    }
    
    /**
     * 월력 판매분석- 팝업 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleAnalsMonthlyMomsVO
     * @return  String
     * @author  권지현
     * @since   2023.04.13
     */
    @RequestMapping(value = "/monthlyMoms/getSaleAnalsMonthlyMomsStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleAnalsMonthlyMomsStoreList(HttpServletRequest request, HttpServletResponse response,
        Model model, SaleAnalsMonthlyMomsVO SaleAnalsMonthlyMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = SaleAnalsMonthlyMomsService.getSaleAnalsMonthlyMomsStoreList(SaleAnalsMonthlyMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, SaleAnalsMonthlyMomsVO);
    }

    /**
     * 월력 판매분석- 팝업팝업 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleAnalsMonthlyMomsVO
     * @return  String
     * @author  권지현
     * @since   2023.04.13
     */
    @RequestMapping(value = "/monthlyMoms/getSaleAnalsMonthlyMomsStoreDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleAnalsMonthlyMomsStoreDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, SaleAnalsMonthlyMomsVO SaleAnalsMonthlyMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = SaleAnalsMonthlyMomsService.getSaleAnalsMonthlyMomsStoreDtlList(SaleAnalsMonthlyMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, SaleAnalsMonthlyMomsVO);
    }

}
