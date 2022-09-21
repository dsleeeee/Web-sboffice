package kr.co.solbipos.membr.reportKwu.monthlyMembr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.reportKwu.monthlyMembr.service.MonthlyMembrService;
import kr.co.solbipos.membr.reportKwu.monthlyMembr.service.MonthlyMembrVO;
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
 * @Class Name : MonthlyMembrController.java
 * @Description : 광운대 > 리포트 > 월별회원등록현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.21
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/membr/reportKwu/monthlyMembr")
public class MonthlyMembrController {
    private final SessionService sessionService;
    private final MonthlyMembrService monthlyMembrService;

    public MonthlyMembrController(SessionService sessionService, MonthlyMembrService monthlyMembrService){
        this.sessionService = sessionService;
        this.monthlyMembrService = monthlyMembrService;
    }

    /**
     * 월별회원등록현황 - 페이지 이동
     * @param request
     * @param response
     * @param model
     * @param monthlyMembrVO
     * @return
     * @author  이다솜
     * @since   2022.09.21
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model, MonthlyMembrVO monthlyMembrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 본사 명칭 콤보조회 - 강습구분 : 140
        monthlyMembrVO.setNmcodeGrpCd("140");
        monthlyMembrVO.setCodeType("1");  // [1: comboBox용]
        model.addAttribute("ComboClassFg", convertToJson(monthlyMembrService.selectHqCodeList(monthlyMembrVO, sessionInfoVO)));

        // 컬럼명칭 - 강사명 : 139
        monthlyMembrVO.setNmcodeGrpCd("139");
        monthlyMembrVO.setCodeType("2");  // [2: column용]
        model.addAttribute("teacherCd", convertToJson(monthlyMembrService.selectHqCodeList(monthlyMembrVO, sessionInfoVO)));

        // 컬럼명칭 - 강습구분 : 140
        monthlyMembrVO.setNmcodeGrpCd("140");
        monthlyMembrVO.setCodeType("2");  // [2: column용]
        model.addAttribute("classFg", convertToJson(monthlyMembrService.selectHqCodeList(monthlyMembrVO, sessionInfoVO)));

        // 컬럼명칭 - 스케이트종류 : 144
        monthlyMembrVO.setNmcodeGrpCd("144");
        monthlyMembrVO.setCodeType("2");  // [2: column용]
        model.addAttribute("skateFg", convertToJson(monthlyMembrService.selectHqCodeList(monthlyMembrVO, sessionInfoVO)));

        return "membr/reportKwu/monthlyMembr/monthlyMembr";
    }

    /**
     * 월별회원등록현황 - 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param monthlyMembrVO
     * @return
     * @author  이다솜
     * @since   2022.09.21
     */
    @RequestMapping(value = "/getMonthlyMembrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthlyMembrList(HttpServletRequest request, HttpServletResponse response, Model model, MonthlyMembrVO monthlyMembrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = monthlyMembrService.getMonthlyMembrList(monthlyMembrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthlyMembrVO);
    }
}
