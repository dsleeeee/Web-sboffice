package kr.co.common.controller;

import kr.co.common.data.domain.VanVO;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.data.domain.AgencyVO;
import kr.co.common.service.popup.PopupService;
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
 * @Class Name : PopupController.java
 * @Description : 공통 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.24  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.24
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/popup/")
public class PopupController {

    /** service */
    private final PopupService service;

    /** Constructor Injection */
    @Autowired
    public PopupController(PopupService service) {
        this.service = service;
    }

    /**
     * 벤사 목록 조회
     * @param vanVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 김지은
     * @since 2018.06.08
     */
    @RequestMapping(value = "getVanList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVanList(VanVO vanVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.getVanList(vanVO);

        return returnListJson(Status.OK, list, vanVO);
    }

    /**
     * 대리점 목록 조회
     * @param agencyVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 김지은
     * @since 2018.06.08
     */
    @RequestMapping(value = "getAgencyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgencyList(AgencyVO agencyVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.getAgencyList(agencyVO);

        return returnListJson(Status.OK, list, agencyVO);
    }



}
