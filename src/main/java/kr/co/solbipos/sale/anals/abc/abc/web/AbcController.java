package kr.co.solbipos.sale.anals.abc.abc.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.abc.abc.service.AbcService;
import kr.co.solbipos.sale.anals.abc.abc.service.AbcVO;

/**
 * @Class Name : StoreFgController.java
 * @Description : 매출관리 > 매출분석 > 매장별매출분석 > 매장형태별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.25  정유경      최초생성
 *
 * @author 솔비포스
 * @since 2020.02.25
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/anals/abc")
public class AbcController {
    private final SessionService sessionService;
    private final AbcService abcService;

    @Autowired
    public AbcController(SessionService sessionService, AbcService abcService) {
        this.sessionService = sessionService;
        this.abcService = abcService;
    }

    @RequestMapping(value = "/abc/list.sb", method = RequestMethod.GET)
    public String dcDcfgView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/anals/abc/abcSale";
    }

    /**
     * 상픔ABC분석 - 상픔ABC분석 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   abcVO
     * @return  String
     * @author  정유경
     * @since   2020.02.25
     */
    @RequestMapping(value = "/abc/abcList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAbcList(HttpServletRequest request, HttpServletResponse response, Model model, AbcVO abcVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = abcService.getAbcList(abcVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, abcVO);
    }

    /**
     * 매장형태별 - 매장형태별 매장구분 콤보 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   abcVO
     * @return  String
     * @author  정유경
     * @since   2020.02.25
     */
/*    @RequestMapping(value = "/fg/storeFgComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreFgComboList(HttpServletRequest request, HttpServletResponse response, Model model, AbcVO abcVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = abcService.getStoreFgComboList(abcVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, abcVO);
    }*/

}
