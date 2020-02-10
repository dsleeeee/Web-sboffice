package kr.co.solbipos.sale.status.corner.dayOfWeek.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.corner.dayOfWeek.service.CornerDayOfWeekService;
import kr.co.solbipos.sale.status.corner.dayOfWeek.service.CornerDayOfWeekVO;

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
@RequestMapping("/sale/status/corner")
public class CornerDayOfWeekController {
    private final SessionService sessionService;
    private final CornerDayOfWeekService cornerDayOfWeekService;

    @Autowired
    public CornerDayOfWeekController(SessionService sessionService, CornerDayOfWeekService cornerDayOfWeekService) {
        this.sessionService = sessionService;
        this.cornerDayOfWeekService = cornerDayOfWeekService;
    }


    /**
     * 코너별매출 일자별 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/dayOfWeek/view.sb", method = RequestMethod.GET)
    public String cornerDayOfWeekView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/corner/dayOfWeek/dayOfWeek";
    }


    /**
     * 코너별매출 일자별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDayOfWeeklVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/dayOfWeek/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCornerDayOfWeekList(HttpServletRequest request, HttpServletResponse response,
        Model model, CornerDayOfWeekVO cornerDayOfWeekVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        if (cornerDayOfWeekVO.getCornrCd() != null && !"".equals(cornerDayOfWeekVO.getCornrCd())) {
    		String[] arrCornrCd = cornerDayOfWeekVO.getCornrCd().split(",");
    		if (arrCornrCd.length > 0) {
    			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
    				cornerDayOfWeekVO.setArrCornrCd(arrCornrCd);
    				cornerDayOfWeekVO.setArrStoreCornr(arrCornrCd);
    			}
    		}
    	} else {
    		String[] arrStoreCd = cornerDayOfWeekVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				cornerDayOfWeekVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

        List<DefaultMap<String>> list = cornerDayOfWeekService.getCornerDayOfWeekList(cornerDayOfWeekVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, cornerDayOfWeekVO);
    }
}
