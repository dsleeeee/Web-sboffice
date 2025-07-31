package kr.co.solbipos.sale.marketing.saleByChannel.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.marketing.saleByChannel.service.SaleByChannelService;
import kr.co.solbipos.sale.marketing.saleByChannel.service.SaleByChannelVO;
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
 * @Class Name  : SaleByChannelController.java
 * @Description : 미스터피자 > 마케팅조회 > 채널별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/marketing/saleByChannel")
public class SaleByChannelController {

    private final SessionService sessionService;
    private final SaleByChannelService saleByChannelService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleByChannelController(SessionService sessionService, SaleByChannelService saleByChannelService) {
        this.sessionService = sessionService;
        this.saleByChannelService = saleByChannelService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/saleByChannel/list.sb", method = RequestMethod.GET)
    public String saleByChannelView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SaleByChannelVO saleByChannelVO = new SaleByChannelVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 그리드 컬럼 조회
        List<DefaultMap<String>> dlvrColList = saleByChannelService.getDlvrColList(saleByChannelVO,sessionInfoVO);

        model.addAttribute("dlvrColList", dlvrColList);

        return "sale/marketing/saleByChannel/saleByChannel";
    }

    /**
     * 채널별매출 - 조회
     *
     * @param   saleByChannelVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 07. 25.
     */
    @RequestMapping(value = "/saleByChannel/getSaleByChannelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleByChannelList(SaleByChannelVO saleByChannelVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleByChannelService.getSaleByChannelList(saleByChannelVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleByChannelVO);
    }
}
