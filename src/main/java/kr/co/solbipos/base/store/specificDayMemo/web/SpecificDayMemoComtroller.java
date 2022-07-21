package kr.co.solbipos.base.store.specificDayMemo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.specificDayMemo.service.SpecificDayMemoService;
import kr.co.solbipos.base.store.specificDayMemo.service.SpecificDayMemoVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : SpecificDayMemoComtroller.java
 * @Description : 기초관리 > 매장관리 > 이벤트등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.20  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.07.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/store/specificDayMemo")
public class SpecificDayMemoComtroller {

    private final SessionService sessionService;
    private final SpecificDayMemoService specificDayMemoService;

    public SpecificDayMemoComtroller(SessionService sessionService, SpecificDayMemoService specificDayMemoService) {
        this.sessionService = sessionService;
        this.specificDayMemoService = specificDayMemoService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/specificDayMemo/view.sb", method = RequestMethod.GET)
    public String getSpecificDayMemoView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/store/specificDayMemo/specificDayMemo";
    }

    /**
     * 이벤트등록 - 조회
     *
     * @param specificDayMemoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.07.20
     */
    @RequestMapping(value = "/specificDayMemo/getSpecificDayMemoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSpecificDayMemoList(SpecificDayMemoVO specificDayMemoVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = specificDayMemoService.getSpecificDayMemoList(specificDayMemoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, specificDayMemoVO);
    }

    /**
     * 이벤트등록 - 신규 등록
     *
     * @param specificDayMemoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.07.20
     */
    @RequestMapping(value = "/specificDayMemo/getSpecificDayMemoRegist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSpecificDayMemoRegist(@RequestBody SpecificDayMemoVO specificDayMemoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = specificDayMemoService.getSpecificDayMemoRegist(specificDayMemoVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 이벤트등록 - 저장
     *
     * @param specificDayMemoVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.07.20
     */
    @RequestMapping(value = "/specificDayMemo/getSpecificDayMemoSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSpecificDayMemoSave(@RequestBody SpecificDayMemoVO[] specificDayMemoVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = specificDayMemoService.getSpecificDayMemoSave(specificDayMemoVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 이벤트등록 - 삭제
     *
     * @param specificDayMemoVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.07.20
     */
    @RequestMapping(value = "/specificDayMemo/getSpecificDayMemoDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSpecificDayMemoDelete(@RequestBody SpecificDayMemoVO[] specificDayMemoVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = specificDayMemoService.getSpecificDayMemoDelete(specificDayMemoVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}