package kr.co.solbipos.sale.today.todayBillSaleDtl.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.today.todayBillSaleDtl.service.TodayBillSaleDtlService;
import kr.co.solbipos.sale.today.todayBillSaleDtl.service.TodayBillSaleDtlVO;
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
 * @ 2019.02.15  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2019.02.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/today/todayBillSaleDtl")
public class TodayBillSaleDtlController {
    private final SessionService sessionService;
    private final TodayBillSaleDtlService todayBillSaleDtlService;

    @Autowired
    public TodayBillSaleDtlController(SessionService sessionService, TodayBillSaleDtlService todayBillSaleDtlService) {
        this.sessionService = sessionService;
        this.todayBillSaleDtlService = todayBillSaleDtlService;
    }


    /**
     * 영수증별매출상세현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2019. 01. 25.
     */
    @RequestMapping(value = "/todayBillSaleDtl/view.sb", method = RequestMethod.GET)
    public String todayBillSaleDtlView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/today/todayBillSaleDtl/todayBillSaleDtl";
    }


    /**
     * 영수증별매출상세현황 - 영수증별매출상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   todayBillSaleDtlVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 15.
     */
    @RequestMapping(value = "/todayBillSaleDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodayBillSaleDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, TodayBillSaleDtlVO todayBillSaleDtlVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = todayBillSaleDtlService.getTodayBillSaleDtlList(todayBillSaleDtlVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, todayBillSaleDtlVO);
    }
}
