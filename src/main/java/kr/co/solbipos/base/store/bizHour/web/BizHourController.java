package kr.co.solbipos.base.store.bizHour.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.bizHour.service.BizHourService;
import kr.co.solbipos.base.store.bizHour.service.BizHourVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : BizHourController.java
 * @Description : 기초관리 > 매장관리 > 매장영업시간관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.10.01  김유승      최초생성
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/store/bizHour")
public class BizHourController {

    @Autowired
    SessionService sessionService;

    @Autowired
    BizHourService bizHourService;

    /** 매장영업시간관리 - 페이지 이동 */
    @RequestMapping(value = "/bizHour/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "base/store/bizHour/bizHour";
    }

    /** 요일별 리스트 조회 */
    @RequestMapping(value = "/days/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result daysList(HttpServletRequest request, HttpServletResponse response,
        Model model, BizHourVO bizHourVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        bizHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 매장 사용자는 자기 매장만 조회 가능하도록 세션 매장으로 고정(본사는 매장 선택 조회 허용)
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            bizHourVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        List<DefaultMap<String>> list = bizHourService.getDaysList(bizHourVO);

        return ReturnUtil.returnListJson(Status.OK, list, bizHourVO);
    }

    /** 요일별 신규 등록 */
    @RequestMapping(value = "/days/saveNew.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result daysSaveNew(HttpServletRequest request, HttpServletResponse response,
        Model model, BizHourVO bizHourVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = bizHourService.saveNewDays(bizHourVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /** 요일별 수정 */
    @RequestMapping(value = "/days/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result daysSave(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody BizHourVO[] bizHourVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = bizHourService.saveDays(bizHourVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /** 요일별 삭제 */
    @RequestMapping(value = "/days/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result daysDelete(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody BizHourVO[] bizHourVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = bizHourService.deleteDays(bizHourVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /** 특정일 리스트 조회 */
    @RequestMapping(value = "/specificDate/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result specificList(HttpServletRequest request, HttpServletResponse response,
        Model model, BizHourVO bizHourVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        bizHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 매장 사용자는 자기 매장만 조회 가능하도록 세션 매장으로 고정(본사는 매장 선택 조회 허용)
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            bizHourVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        List<DefaultMap<String>> list = bizHourService.getSpecificList(bizHourVO);

        return ReturnUtil.returnListJson(Status.OK, list, bizHourVO);
    }

    /** 특정일 신규 등록 */
    @RequestMapping(value = "/specificDate/saveNew.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result specificSaveNew(HttpServletRequest request, HttpServletResponse response,
        Model model, BizHourVO bizHourVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = bizHourService.saveNewSpecific(bizHourVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /** 특정일 수정 */
    @RequestMapping(value = "/specificDate/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result specificSave(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody BizHourVO[] bizHourVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = bizHourService.saveSpecific(bizHourVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /** 특정일 삭제 */
    @RequestMapping(value = "/specificDate/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result specificDelete(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody BizHourVO[] bizHourVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = bizHourService.deleteSpecific(bizHourVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
