package kr.co.solbipos.stock.acins.hqAcins.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.acins.hqAcins.service.HqAcinsService;
import kr.co.solbipos.stock.acins.hqAcins.service.HqAcinsVO;
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
 * @Class Name : HqAcinsController.java
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
@RequestMapping("/stock/acins/hqAcins")
public class HqAcinsController {
    private final SessionService sessionService;
    private final HqAcinsService hqAcinsService;

    @Autowired
    public HqAcinsController(SessionService sessionService, HqAcinsService hqAcinsService) {
        this.sessionService = sessionService;
        this.hqAcinsService = hqAcinsService;
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
    @RequestMapping(value = "/hqAcins/view.sb", method = RequestMethod.GET)
    public String hqAcinsView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "stock/acins/hqAcins/hqAcins";
    }


    /**
     * 실사관리 - 실사관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAcinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 02.
     */
    @RequestMapping(value = "/hqAcins/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqAcinsList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqAcinsVO hqAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqAcinsService.getHqAcinsList(hqAcinsVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqAcinsVO);
    }



    /**
     * 실사관리 - 실사 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAcinsVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/hqAcins/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteHqAcins(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqAcinsVO[] hqAcinsVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqAcinsService.deleteHqAcins(hqAcinsVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 실사 진행구분 및 제목 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAcinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/hqAcinsRegist/procFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, HqAcinsVO hqAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = hqAcinsService.getProcFgCheck(hqAcinsVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 실사등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAcinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/hqAcinsRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqAcinsRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqAcinsVO hqAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqAcinsService.getHqAcinsRegistList(hqAcinsVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqAcinsVO);
    }


    /**
     * 실사관리 - 실사상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAcinsVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/hqAcinsRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqAcinsRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqAcinsVO[] hqAcinsVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqAcinsService.saveHqAcinsRegist(hqAcinsVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 실사등록시 상품정보 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAcinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 06.
     */
    @RequestMapping(value = "/hqAcinsRegist/getProdInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, HqAcinsVO hqAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = hqAcinsService.getProdInfo(hqAcinsVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 실사관리 - 실사 상세 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAcinsVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/hqAcinsDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqAcinsDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqAcinsVO hqAcinsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqAcinsService.getHqAcinsDtlList(hqAcinsVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqAcinsVO);
    }


    /**
     * 실사관리 - 실사 상세 상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAcinsVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 05.
     */
    @RequestMapping(value = "/hqAcinsDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqAcinsDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqAcinsVO[] hqAcinsVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqAcinsService.saveHqAcinsDtl(hqAcinsVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
