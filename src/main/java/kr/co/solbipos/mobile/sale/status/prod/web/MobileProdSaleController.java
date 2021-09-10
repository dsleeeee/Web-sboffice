package kr.co.solbipos.mobile.sale.status.prod.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleService;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleVO;
import kr.co.solbipos.sale.anals.nonSaleCharge.service.NonSaleChargeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : MobileProdSaleController.java
 * @Description : 모바일 매출현황 > 상품별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.03.31  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2021.03.31
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("mobile/sale/status/prod/prodSale")
public class MobileProdSaleController {
    private final SessionService sessionService;
    private final MobileProdSaleService mobileProdSaleService;

    @Autowired
    public MobileProdSaleController(SessionService sessionService, MobileProdSaleService mobileProdSaleService){
        this.sessionService = sessionService;
        this.mobileProdSaleService = mobileProdSaleService;
    }

    /**
     * 모바일 매출현황 - 상품별매출현황 페이지 이동
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  이다솜
     * @since   2021.04.01
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);

        model.addAttribute("multiStoreFg", list.size());
        return "mobile/sale/status/prod/mobileProdSale";
    }

    /**
     * 모바일 매출현황 - 당일매출 Best3, 상품별매출현황 조회
     * @param mobileProdSaleVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  이다솜
     * @since   2021.04.01
     */
    @RequestMapping(value = "/prodSaleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result prodSaleList(MobileProdSaleVO mobileProdSaleVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = mobileProdSaleService.getProdSaleList(mobileProdSaleVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

    /**
     * 모바일 매출현황 - 다중매장조회
     * @param mobileProdSaleVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  이다솜
     * @since   2021.04.01
     */
    @RequestMapping(value = "/getMultiStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMultiStoreList(MobileProdSaleVO mobileProdSaleVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

    /**
     * 모바일 매출현황 - 매장조회
     * @param mobileProdSaleVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2021.09.07
     */
    @RequestMapping(value = "/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(MobileProdSaleVO mobileProdSaleVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileProdSaleService.getStoreList(mobileProdSaleVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }
}
