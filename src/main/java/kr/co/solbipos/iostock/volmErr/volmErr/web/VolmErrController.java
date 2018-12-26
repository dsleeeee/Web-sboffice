package kr.co.solbipos.iostock.volmErr.volmErr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.volmErr.volmErr.service.VolmErrService;
import kr.co.solbipos.iostock.volmErr.volmErr.service.VolmErrVO;
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
 * @Class Name : VolmErrController.java
 * @Description : 수불관리 > 물량오류 > 물량오류관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.22  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 10.22
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/volmErr/volmErr")
public class VolmErrController {
    private final SessionService sessionService;
    private final VolmErrService volmErrService;

    @Autowired
    public VolmErrController(SessionService sessionService, VolmErrService volmErrService) {
        this.sessionService = sessionService;
        this.volmErrService = volmErrService;
    }


    /**
     * 물량오류관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 22.
     */
    @RequestMapping(value = "/volmErr/view.sb", method = RequestMethod.GET)
    public String volmErrView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/volmErr/volmErr/volmErr";
    }

    /**
     * 물량오류관리 - 물량오류관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   volmErrVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 22.
     */
    @RequestMapping(value = "/volmErr/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVolmErrList(HttpServletRequest request, HttpServletResponse response,
        Model model, VolmErrVO volmErrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        volmErrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = volmErrService.getVolmErrList(volmErrVO);

        return ReturnUtil.returnListJson(Status.OK, list, volmErrVO);
    }

    /**
     * 물량오류관리 - 물량오류관리 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   volmErrVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 22.
     */
    @RequestMapping(value = "/volmErrDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVolmErrDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, VolmErrVO volmErrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        volmErrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = volmErrService.getVolmErrDtlList(volmErrVO);

        return ReturnUtil.returnListJson(Status.OK, list, volmErrVO);
    }


    /**
     * 물량오류관리 - 물량오류관리 상세 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   volmErrVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 22.
     */
    @RequestMapping(value = "/volmErrDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVolmErrDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody VolmErrVO[] volmErrVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = volmErrService.saveVolmErrDtl(volmErrVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

}
