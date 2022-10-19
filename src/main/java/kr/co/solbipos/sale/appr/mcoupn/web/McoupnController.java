package kr.co.solbipos.sale.appr.mcoupn.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import kr.co.solbipos.sale.appr.mcoupn.service.McoupnService;
import kr.co.solbipos.sale.appr.mcoupn.service.McoupnVO;
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
 * @Class Name : McoupnController.java
 * @Description : 맘스터치 > 승인관리2 > 모바일쿠폰 승인 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.30  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/appr/mcoupn")
public class McoupnController {

    private final SessionService sessionService;
    private final McoupnService mcoupnService;
    private final StoreTypeService storeTypeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public McoupnController(SessionService sessionService, McoupnService mcoupnService, StoreTypeService storeTypeService) {
        this.sessionService = sessionService;
        this.mcoupnService = mcoupnService;
        this.storeTypeService = storeTypeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mcoupn/list.sb", method = RequestMethod.GET)
    public String mcoupnView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("hqBrandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        return "sale/appr/mcoupn/mcoupn";
    }

    /**
     * 모바일쿠폰 승인 조회
     *
     * @param mcoupnVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022. 09. 30.
     */
    @RequestMapping(value = "/mcoupn/getMcoupnList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMcoupnList(McoupnVO mcoupnVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mcoupnService.getMcoupnList(mcoupnVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mcoupnVO);
    }

    /**
     * 모바일쿠폰 승인 조회_엑셀
     *
     * @param mcoupnVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022. 09. 30.
     */
    @RequestMapping(value = "/mcoupn/getMcoupnExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMcoupnExcelList(McoupnVO mcoupnVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mcoupnService.getMcoupnExcelList(mcoupnVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mcoupnVO);
    }


}