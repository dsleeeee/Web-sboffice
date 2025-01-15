package kr.co.solbipos.sale.status.dayProdSaleStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.dayProdSaleStore.service.DayProdSaleStoreService;
import kr.co.solbipos.sale.status.dayProdSaleStore.service.DayProdSaleStoreVO;
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
 * @Class Name : DayProdSaleStoreController.java
 * @Description : 매출관리 > 매출현황2 > 일별상품매출현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.01.09  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2025.01.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/status/dayProdSaleStore")
public class DayProdSaleStoreController {
    private final SessionService sessionService;
    private final DayProdSaleStoreService dayProdSaleStoreService;

    @Autowired
    public DayProdSaleStoreController(SessionService sessionService, DayProdSaleStoreService dayProdSaleStoreService){
        this.sessionService = sessionService;
        this.dayProdSaleStoreService = dayProdSaleStoreService;
    }

    /**
     * 일별상품매출현황(매장별) - 페이지 이동
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025.01.09
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/status/dayProdSaleStore/dayProdSaleStore";
    }

    /**
     * 일별상품매출현황(매장별) - 조회
     * @param dayProdSaleStoreVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2025.01.09
     */
    @RequestMapping(value = "/getDayProdSaleStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdSaleStoreList(DayProdSaleStoreVO dayProdSaleStoreVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = dayProdSaleStoreService.getDayProdSaleStoreList(dayProdSaleStoreVO, sessionInfoVO);

        return returnListJson(Status.OK, result, dayProdSaleStoreVO);
    }

    /**
     * 일별상품매출현황(매장별) - 엑셀다운로드 조회
     * @param dayProdSaleStoreVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2025.01.09
     */
    @RequestMapping(value = "/getDayProdSaleStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdSaleStoreExcelList(DayProdSaleStoreVO dayProdSaleStoreVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = dayProdSaleStoreService.getDayProdSaleStoreExcelList(dayProdSaleStoreVO, sessionInfoVO);

        return returnListJson(Status.OK, result, dayProdSaleStoreVO);
    }

    /**
     * 일별상품매출현황(매장별) - 상세 팝업 리스트 조회
     * @param dayProdSaleStoreVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2025.01.09
     */
    @RequestMapping(value = "/getDayProdSaleStoreDtl.sb", method = RequestMethod.POST)
        @ResponseBody
    public Result getDayProdSaleStoreDtl(DayProdSaleStoreVO dayProdSaleStoreVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = dayProdSaleStoreService.getDayProdSaleStoreDtl(dayProdSaleStoreVO, sessionInfoVO);

        return returnListJson(Status.OK, result, dayProdSaleStoreVO);
    }
}
