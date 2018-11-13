package kr.co.solbipos.stock.disuse.hqDisuse.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.disuse.hqDisuse.service.HqDisuseService;
import kr.co.solbipos.stock.disuse.hqDisuse.service.HqDisuseVO;
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
 * @Class Name : HqDisuseController.java
 * @Description : 재고관리 > 실사/조정/폐기 > 폐기관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.12  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 11.12
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/disuse/hqDisuse")
public class HqDisuseController {
    private final SessionService sessionService;
    private final HqDisuseService hqDisuseService;

    @Autowired
    public HqDisuseController(SessionService sessionService, HqDisuseService hqDisuseService) {
        this.sessionService = sessionService;
        this.hqDisuseService = hqDisuseService;
    }

    /**
     * 폐기관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/hqDisuse/view.sb", method = RequestMethod.GET)
    public String hqDisuseView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "stock/disuse/hqDisuse/hqDisuse";
    }


    /**
     * 폐기관리 - 폐기관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqDisuseVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/hqDisuse/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqDisuseList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqDisuseVO hqDisuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqDisuseService.getHqDisuseList(hqDisuseVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqDisuseVO);
    }


    /**
     * 폐기관리 - 폐기 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   hqDisuseVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/hqDisuse/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteHqDisuse(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqDisuseVO[] hqDisuseVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqDisuseService.deleteHqDisuse(hqDisuseVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 폐기관리 - 폐기 진행구분 및 제목 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqDisuseVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/hqDisuseRegist/procFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, HqDisuseVO hqDisuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = hqDisuseService.getProcFgCheck(hqDisuseVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 폐기관리 - 폐기등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqDisuseVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/hqDisuseRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqDisuseRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqDisuseVO hqDisuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqDisuseService.getHqDisuseRegistList(hqDisuseVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqDisuseVO);
    }


    /**
     * 폐기관리 - 폐기상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   hqDisuseVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/hqDisuseRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqDisuseRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqDisuseVO[] hqDisuseVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqDisuseService.saveHqDisuseRegist(hqDisuseVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 폐기관리 - 폐기등록시 상품정보 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqDisuseVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 06.
     */
    @RequestMapping(value = "/hqDisuseRegist/getProdInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, HqDisuseVO hqDisuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = hqDisuseService.getProdInfo(hqDisuseVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 폐기관리 - 폐기 상세 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqDisuseVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/hqDisuseDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqDisuseDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqDisuseVO hqDisuseVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqDisuseService.getHqDisuseDtlList(hqDisuseVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqDisuseVO);
    }


    /**
     * 폐기관리 - 폐기 상세 상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   hqDisuseVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/hqDisuseDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqDisuseDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqDisuseVO[] hqDisuseVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqDisuseService.saveHqDisuseDtl(hqDisuseVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
