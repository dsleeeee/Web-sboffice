package kr.co.solbipos.sale.status.prod.rank.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.dayOfWeek.service.PosDayOfWeekVO;
import kr.co.solbipos.sale.status.prod.rank.service.ProdRankService;
import kr.co.solbipos.sale.status.prod.rank.service.ProdRankVO;
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
 * @Class Name : TodayBillSaleDtlController.java
 * @Description : 매출관리 > 매출현황 > 영수증별매출상세현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.08  김진      최초생성
 *
 * @author 솔비포스
 * @since 2020.01.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/prod")
public class ProdRankController {
    private final SessionService sessionService;
    private final ProdRankService prodRankService;

    @Autowired
    public ProdRankController(SessionService sessionService, ProdRankService prodRankService) {
        this.sessionService = sessionService;
        this.prodRankService = prodRankService;
    }


    /**
     * 상품매출순위현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 01. 08.
     */
    @RequestMapping(value = "/rank/view.sb", method = RequestMethod.GET)
    public String prodRankView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/prod/rank/pordRank";
    }


    /**
     * 상품별 매출 - 상품매출순위 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRankVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 08.
     */
    @RequestMapping(value = "/rank/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRankList(HttpServletRequest request, HttpServletResponse response, Model model, ProdRankVO prodRankVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodRankService.getProdRankList(prodRankVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodRankVO);
    }

    /**
     * 상품별매출 - 상품매출순위 차트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRankVO
     * @return  String
     * @author  서재식
     * @since   2020. 04. 14.
     */
    @RequestMapping(value = "/rank/chartList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRankChartList(HttpServletRequest request, HttpServletResponse response,
        Model model, ProdRankVO prodRankVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodRankService.getProdRankChartList(prodRankVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, prodRankVO);
    }

}
