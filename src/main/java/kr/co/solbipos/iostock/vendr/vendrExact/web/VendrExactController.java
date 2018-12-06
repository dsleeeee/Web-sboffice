package kr.co.solbipos.iostock.vendr.vendrExact.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.vendr.vendrExact.service.VendrExactService;
import kr.co.solbipos.iostock.vendr.vendrExact.service.VendrExactVO;
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
 * @Class Name : VendrExactController.java
 * @Description : 수불관리 > 거래처(매입)입출고관리 > 거래처정산관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.03  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 12.03
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/vendr/vendrExact")
public class VendrExactController {
    private final SessionService sessionService;
    private final VendrExactService vendrExactService;

    @Autowired
    public VendrExactController(SessionService sessionService, VendrExactService vendrExactService) {
        this.sessionService = sessionService;
        this.vendrExactService = vendrExactService;
    }

    /**
     * 거래처 정산관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 12. 03.
     */
    @RequestMapping(value = "/vendrExact/view.sb", method = RequestMethod.GET)
    public String vendrExactView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/vendr/vendrExact/vendrExact";
    }


    /**
     * 거래처 정산관리 - 거래처별 정산 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrExactVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 03.
     */
    @RequestMapping(value = "/vendrExact/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrExactList(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrExactVO vendrExactVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = vendrExactService.getVendrExactList(vendrExactVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vendrExactVO);
    }


    /**
     * 거래처 정산관리 - 거래처 정산 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrExactVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 03.
     */
    @RequestMapping(value = "/vendrExactDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrExactDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrExactVO vendrExactVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>>
            list = vendrExactService.getVendrExactDtlList(vendrExactVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, vendrExactVO);
    }


    /**
     * 거래처 정산관리 - 지급액 상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrExactVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 03.
     */
    @RequestMapping(value = "/vendrExactRegist/getExactInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExactInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrExactVO vendrExactVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = vendrExactService.getExactInfo(vendrExactVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 정산관리 - 지급액 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrExactVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 03.
     */
    @RequestMapping(value = "/vendrExactRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVendrExactRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrExactVO vendrExactVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vendrExactService.saveVendrExactRegist(vendrExactVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 거래처 정산관리 - 지급액 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrExactVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 03.
     */
    @RequestMapping(value = "/vendrExactRegist/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteVendrExactRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, VendrExactVO vendrExactVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vendrExactService.deleteVendrExactRegist(vendrExactVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
