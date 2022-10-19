package kr.co.solbipos.sale.prod.monthSalesVolume.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import kr.co.solbipos.sale.prod.monthSalesVolume.service.MonthSalesVolumeService;
import kr.co.solbipos.sale.prod.monthSalesVolume.service.MonthSalesVolumeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;


/**
 * @Class Name : MonthSalesVolumeController.java
 * @Description : 맘스터치 > 상품매출분석 > 월별 상품 판매량
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/prod/monthSalesVolume")
public class MonthSalesVolumeController {

    private final SessionService sessionService;
    private final MonthSalesVolumeService monthSalesVolumeService;
    private final StoreTypeService storeTypeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MonthSalesVolumeController(SessionService sessionService, MonthSalesVolumeService monthSalesVolumeService, StoreTypeService storeTypeService) {
        this.sessionService = sessionService;
        this.monthSalesVolumeService = monthSalesVolumeService;
        this.storeTypeService = storeTypeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/monthSalesVolume/list.sb", method = RequestMethod.GET)
    public String prodRankMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("hqBrandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        return "sale/prod/monthSalesVolume/monthSalesVolume";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   monthSalesVolumeVO
     * @return  String
     * @author  권지현
     * @since   2022.10.04
     */
    @RequestMapping(value = "/monthSalesVolume/getMonthSalesVolumeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthSalesVolumeList(HttpServletRequest request, HttpServletResponse response, Model model, MonthSalesVolumeVO monthSalesVolumeVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthSalesVolumeService.getMonthSalesVolumeList(monthSalesVolumeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthSalesVolumeVO);
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   monthSalesVolumeVO
     * @return  String
     * @author  권지현
     * @since   2022.10.04
     */
    @RequestMapping(value = "/monthSalesVolume/getMonthSalesVolumeExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthSalesVolumeExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MonthSalesVolumeVO monthSalesVolumeVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthSalesVolumeService.getMonthSalesVolumeExcelList(monthSalesVolumeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthSalesVolumeVO);
    }

}