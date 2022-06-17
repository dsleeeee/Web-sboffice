package kr.co.solbipos.sale.status.weight.weight.web;

        import kr.co.common.data.enums.Status;
        import kr.co.common.data.structure.DefaultMap;
        import kr.co.common.data.structure.Result;
        import kr.co.common.service.session.SessionService;
        import kr.co.common.utils.grid.ReturnUtil;
        import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
        import kr.co.solbipos.sale.status.weight.weight.service.WeightService;
        import kr.co.solbipos.sale.status.weight.weight.service.WeightVO;
        import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlService;
        import kr.co.solbipos.sale.today.todayGnrlz.service.TodayGnrlzService;
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
 * @Class Name : WeightController.java
 * @Description : 매출관리 > 매출현황2 > 중량별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.11.08  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.11.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/weight/weight")
public class WeightController {
    private final SessionService sessionService;
    private final WeightService weightService;

    @Autowired
    public WeightController(SessionService sessionService, WeightService weightService) {
        this.sessionService = sessionService;
        this.weightService = weightService;
    }


    /**
     * 중량별 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2021.11.08
     */
    @RequestMapping(value = "/weight/view.sb", method = RequestMethod.GET)
    public String WeightView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/weight/weightTab";
    }

    /**
     * 중량별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   weightVO
     * @return  String
     * @author  권지현
     * @since   2021.11.08
     */
    @RequestMapping(value = "/weight/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWeightList(HttpServletRequest request, HttpServletResponse response,
                                Model model, WeightVO weightVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = weightService.getWeightList(weightVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  weightVO);
    }

    /**
     * 일자별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   weightVO
     * @return  String
     * @author  권지현
     * @since   2021.11.08
     */
    @RequestMapping(value = "/weightDay/getWeightDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWeightDayList(HttpServletRequest request, HttpServletResponse response,
                                Model model, WeightVO weightVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = weightService.getWeightDayList(weightVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  weightVO);
    }

    /**
     * 상품별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   weightVO
     * @return  String
     * @author  권지현
     * @since   2022.06.09
     */
    @RequestMapping(value = "/weightProd/getWeightProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWeightProdList(HttpServletRequest request, HttpServletResponse response,
                                Model model, WeightVO weightVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = weightService.getWeightProdList(weightVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  weightVO);
    }

    /**
     * 상품별 - 엑셀리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   weightVO
     * @return  String
     * @author  권지현
     * @since   2022.06.09
     */
    @RequestMapping(value = "/weightProd/getWeightProdExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWeightProdExcelList(HttpServletRequest request, HttpServletResponse response,
                                Model model, WeightVO weightVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = weightService.getWeightProdExcelList(weightVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  weightVO);
    }
}
