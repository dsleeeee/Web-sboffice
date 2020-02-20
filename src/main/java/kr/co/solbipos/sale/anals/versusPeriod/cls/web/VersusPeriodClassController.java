package kr.co.solbipos.sale.anals.versusPeriod.cls.web;

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
import kr.co.solbipos.sale.anals.versusPeriod.cls.service.VersusPeriodClassService;
import kr.co.solbipos.sale.anals.versusPeriod.cls.service.VersusPeriodClassVO;

/**
 * @Class Name : ProdHourController.java
 * @Description : 매출관리 > 매출분석 > 분류상품별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.31  정유경      최초생성
 *
 * @author 솔비포스
 * @since 2020.01.31
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/anals/versusPeriod")
public class VersusPeriodClassController {
    private final SessionService sessionService;
    private final VersusPeriodClassService versusPeriodClassService;

    @Autowired
    public VersusPeriodClassController(SessionService sessionService, VersusPeriodClassService versusPeriodClassService) {
        this.sessionService = sessionService;
        this.versusPeriodClassService = versusPeriodClassService;
    }

    
    @RequestMapping(value = "/class/list.sb", method = RequestMethod.GET)
    public String versusPeriodView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/anals/versusPeriod/versusPeriodSale";
    }
    
    /** 대비기간매출분석 - 분류상품별 리스트 조회 */
    @RequestMapping(value = "/class/versusPeriodClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVersusPeriodClassList(HttpServletRequest request, HttpServletResponse response, Model model, VersusPeriodClassVO versusPeriodClassVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = versusPeriodClassService.getVersusPeriodClassList(versusPeriodClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, versusPeriodClassVO);
    }

    /** 대비기간매출분석 - 분류상품별 리스트 상세 조회 */
    @RequestMapping(value = "/class/versusPeriodClassDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVersusPeriodClassDtlList(HttpServletRequest request, HttpServletResponse response, Model model, VersusPeriodClassVO versusPeriodClassVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = versusPeriodClassService.getVersusPeriodClassDtlList(versusPeriodClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, versusPeriodClassVO);
    }
    
    /** 대비기간매출분석 - 브랜드 코드 조회조건 */
    @RequestMapping(value = "/class/getBrandCdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBrandCdList(HttpServletRequest request, HttpServletResponse response, Model model, VersusPeriodClassVO versusPeriodClassVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = versusPeriodClassService.getBrandCdList(versusPeriodClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, versusPeriodClassVO);
    }
}
