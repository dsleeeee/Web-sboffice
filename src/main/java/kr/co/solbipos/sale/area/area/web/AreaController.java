package kr.co.solbipos.sale.area.area.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.area.area.service.AreaService;
import kr.co.solbipos.sale.area.area.service.AreaVO;
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
 * @Description : 맘스터치 > 매출분석 > 지역별매출
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
@RequestMapping("/sale/area/area")
public class AreaController {

    private final SessionService sessionService;
    private final AreaService areaService;

    /**
     * Constructor Injection
     */
    @Autowired
    public AreaController(SessionService sessionService, AreaService areaService) {
        this.sessionService = sessionService;
        this.areaService = areaService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/area/list.sb", method = RequestMethod.GET)
    public String areaView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/area/area/area";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   areaVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/area/getAreaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAreaList(HttpServletRequest request, HttpServletResponse response, Model model, AreaVO areaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = areaService.getAreaList(areaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, areaVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   areaVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/area/getAreaExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAreaExcelList(HttpServletRequest request, HttpServletResponse response, Model model, AreaVO areaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = areaService.getAreaExcelList(areaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, areaVO);
    }

}