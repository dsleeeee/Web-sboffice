package kr.co.solbipos.sale.area.bizArea.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.area.bizArea.service.BizAreaService;
import kr.co.solbipos.sale.area.bizArea.service.BizAreaVO;
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
 * @Class Name : AreaController.java
 * @Description : 맘스터치 > 매출분석 > 상권별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/area/bizArea")
public class BizAreaController {

    private final SessionService sessionService;
    private final BizAreaService bizAreaService;

    /**
     * Constructor Injection
     */
    @Autowired
    public BizAreaController(SessionService sessionService, BizAreaService bizAreaService) {
        this.sessionService = sessionService;
        this.bizAreaService = bizAreaService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/bizArea/list.sb", method = RequestMethod.GET)
    public String bizAreaView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/area/bizArea/bizArea";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   bizAreaVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/bizArea/getBizAreaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBizAreaList(HttpServletRequest request, HttpServletResponse response, Model model, BizAreaVO bizAreaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = bizAreaService.getBizAreaList(bizAreaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, bizAreaVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   bizAreaVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/bizArea/getBizAreaExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBizAreaExcelList(HttpServletRequest request, HttpServletResponse response, Model model, BizAreaVO bizAreaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = bizAreaService.getBizAreaExcelList(bizAreaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, bizAreaVO);
    }

}