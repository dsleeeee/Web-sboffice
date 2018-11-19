package kr.co.solbipos.stock.acins.acins.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.acins.acins.service.AcinsService;
import kr.co.solbipos.stock.acins.acins.service.AcinsVO;
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
 * @Class Name : AcinsController.java
 * @Description : 재고관리 > 실사/조정/폐기 > 실사관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.02  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 11.02
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/acins/acins")
public class AcinsController {
    private final SessionService sessionService;
    private final AcinsService acinsService;

    @Autowired
    public AcinsController(SessionService sessionService, AcinsService acinsService) {
        this.sessionService = sessionService;
        this.acinsService = acinsService;
    }

    /**
     * 실사관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 11. 02.
     */
    @RequestMapping(value = "/acins/view.sb", method = RequestMethod.GET)
    public String acinsView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "stock/acins/acins/acins";
    }


    /**
     * 실사관리 - 실사관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 02.
     */
    @RequestMapping(value = "/acins/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcinsList(HttpServletRequest request, HttpServletResponse response,
        Model model, AcinsVO acinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = acinsService.getAcinsList(acinsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, acinsVO);
    }



    /**
     * 실사관리 - 실사 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/acins/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteAcins(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody AcinsVO[] acinsVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acinsService.deleteAcins(acinsVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 실사 진행구분 및 제목 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/acinsRegist/procFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, AcinsVO acinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = acinsService.getProcFgCheck(acinsVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 실사등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/acinsRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcinsRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, AcinsVO acinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = acinsService.getAcinsRegistList(acinsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, acinsVO);
    }


    /**
     * 실사관리 - 실사상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/acinsRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAcinsRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody AcinsVO[] acinsVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acinsService.saveAcinsRegist(acinsVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 실사등록시 상품정보 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 06.
     */
    @RequestMapping(value = "/acinsRegist/getProdInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, AcinsVO acinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = acinsService.getProdInfo(acinsVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 실사 상세 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/acinsDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcinsDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, AcinsVO acinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = acinsService.getAcinsDtlList(acinsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, acinsVO);
    }


    /**
     * 실사관리 - 실사 상세 상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   acinsVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/acinsDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAcinsDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody AcinsVO[] acinsVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = acinsService.saveAcinsDtl(acinsVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
