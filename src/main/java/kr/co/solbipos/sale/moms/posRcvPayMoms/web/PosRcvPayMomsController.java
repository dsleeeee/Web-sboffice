package kr.co.solbipos.sale.moms.posRcvPayMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.moms.posRcvPayMoms.service.PosRcvPayMomsService;
import kr.co.solbipos.sale.moms.posRcvPayMoms.service.PosRcvPayMomsVO;

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
 * @Class Name : PosRcvPayMomsController.java
 * @Description : 맘스터치 > 정산 > POS내역수신(결제)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.18  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.09.18
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/moms/posRcvPayMoms")
public class PosRcvPayMomsController {

    private final SessionService sessionService;
    private final PosRcvPayMomsService posRcvPayMomsService;
    private final DayService dayService;

    @Autowired
    public PosRcvPayMomsController(SessionService sessionService, PosRcvPayMomsService posRcvPayMomsService, DayService dayService) {
        this.sessionService = sessionService;
        this.posRcvPayMomsService = posRcvPayMomsService;
        this.dayService = dayService;
    }

    /**
     * POS내역수신(결제) - 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String posRcvPayMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/moms/posRcvPayMoms/posRcvPayMoms";
    }

    /**
     * POS내역수신(결제) - 리스트 조회
     * TODO: 조회일자 / 매장선택(싱글) 필수값 검증(CLASS 단 처리) 및 실제 조회 로직 구현
     *
     * @param posRcvPayMomsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2026. 09. 18.
     */
    @RequestMapping(value = "/posRcvPayMoms/getPosRcvPayMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosRcvPayMomsList(PosRcvPayMomsVO posRcvPayMomsVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = posRcvPayMomsService.getPosRcvPayMomsList(posRcvPayMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, posRcvPayMomsVO);
    }
}
