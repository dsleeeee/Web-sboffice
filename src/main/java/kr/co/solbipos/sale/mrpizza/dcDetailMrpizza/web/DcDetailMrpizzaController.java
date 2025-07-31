package kr.co.solbipos.sale.mrpizza.dcDetailMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.mrpizza.dcDetailMrpizza.service.DcDetailMrpizzaService;
import kr.co.solbipos.sale.mrpizza.dcDetailMrpizza.service.DcDetailMrpizzaVO;
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
 * @Class Name : DcDetailMrpizzaController.java
 * @Description : 미스터피자 > 마케팅조회 > 할인세부내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.30  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/mrpizza/dcDetailMrpizza")
public class DcDetailMrpizzaController {

    private final SessionService sessionService;
    private final DcDetailMrpizzaService dcDetailMrpizzaService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DcDetailMrpizzaController(SessionService sessionService, DcDetailMrpizzaService dcDetailMrpizzaService) {
        this.sessionService = sessionService;
        this.dcDetailMrpizzaService = dcDetailMrpizzaService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/mrpizza/dcDetailMrpizza/dcDetailMrpizzaTab";
    }

    /**
     * 할인세부내역 - 전체점포 탭 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param dcDetailMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.07.30
     */
    @RequestMapping(value = "/getDcDetailMrpizzaAllStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDcDetailMrpizzaAllStoreList(HttpServletRequest request, HttpServletResponse response, Model model, DcDetailMrpizzaVO dcDetailMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dcDetailMrpizzaService.getDcDetailMrpizzaAllStoreList(dcDetailMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcDetailMrpizzaVO);
    }
}
