package kr.co.solbipos.sale.moms.posRcvSaleMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.moms.posRcvSaleMoms.service.PosRcvSaleMomsService;
import kr.co.solbipos.sale.moms.posRcvSaleMoms.service.PosRcvSaleMomsVO;

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
 * @Class Name : PosRcvSaleMomsController.java
 * @Description : 맘스터치 > 정산 > POS내역수신(매출)
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
@RequestMapping("/sale/moms/posRcvSaleMoms")
public class PosRcvSaleMomsController {

    private final SessionService sessionService;
    private final PosRcvSaleMomsService posRcvSaleMomsService;

    @Autowired
    public PosRcvSaleMomsController(SessionService sessionService, PosRcvSaleMomsService posRcvSaleMomsService) {
        this.sessionService = sessionService;
        this.posRcvSaleMomsService = posRcvSaleMomsService;
    }

    /**
     * POS내역수신(매출) - 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String posRcvSaleMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/moms/posRcvSaleMoms/posRcvSaleMoms";
    }

    /**
     * POS내역수신(매출) - 리스트 조회
     *
     * @param posRcvSaleMomsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2026. 09. 18.
     */
    @RequestMapping(value = "/posRcvSaleMoms/getPosRcvSaleMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosRcvSaleMomsList(PosRcvSaleMomsVO posRcvSaleMomsVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = posRcvSaleMomsService.getPosRcvSaleMomsList(posRcvSaleMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, posRcvSaleMomsVO);
    }
}
