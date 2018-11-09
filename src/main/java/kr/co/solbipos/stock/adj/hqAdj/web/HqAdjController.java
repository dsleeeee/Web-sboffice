package kr.co.solbipos.stock.adj.hqAdj.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.adj.hqAdj.service.HqAdjService;
import kr.co.solbipos.stock.adj.hqAdj.service.HqAdjVO;
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
 * @Class Name : HqAdjController.java
 * @Description : 재고관리 > 실사/조정/폐기 > 조정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.08  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 11.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/adj/hqAdj")
public class HqAdjController {
    private final SessionService sessionService;
    private final HqAdjService hqAdjService;

    @Autowired
    public HqAdjController(SessionService sessionService, HqAdjService hqAdjService) {
        this.sessionService = sessionService;
        this.hqAdjService = hqAdjService;
    }

    /**
     * 조정관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/hqAdj/view.sb", method = RequestMethod.GET)
    public String hqAdjView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "stock/adj/hqAdj/hqAdj";
    }


    /**
     * 조정관리 - 조정관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAdjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/hqAdj/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqAdjList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqAdjVO hqAdjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqAdjService.getHqAdjList(hqAdjVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqAdjVO);
    }



    /**
     * 조정관리 - 조정 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAdjVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/hqAdj/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteHqAdj(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqAdjVO[] hqAdjVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqAdjService.deleteHqAdj(hqAdjVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 조정관리 - 조정 진행구분 및 제목 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAdjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/hqAdjRegist/procFgCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProcFgCheck(HttpServletRequest request, HttpServletResponse response,
        Model model, HqAdjVO hqAdjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = hqAdjService.getProcFgCheck(hqAdjVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 조정관리 - 조정등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAdjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/hqAdjRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqAdjRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqAdjVO hqAdjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqAdjService.getHqAdjRegistList(hqAdjVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqAdjVO);
    }


    /**
     * 조정관리 - 조정상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAdjVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/hqAdjRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqAdjRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqAdjVO[] hqAdjVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqAdjService.saveHqAdjRegist(hqAdjVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 조정관리 - 조정등록시 상품정보 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAdjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 06.
     */
    @RequestMapping(value = "/hqAdjRegist/getProdInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, HqAdjVO hqAdjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = hqAdjService.getProdInfo(hqAdjVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 조정관리 - 조정 상세 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAdjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/hqAdjDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqAdjDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqAdjVO hqAdjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqAdjService.getHqAdjDtlList(hqAdjVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqAdjVO);
    }


    /**
     * 조정관리 - 조정 상세 상품 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   hqAdjVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 08.
     */
    @RequestMapping(value = "/hqAdjDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqAdjDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqAdjVO[] hqAdjVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqAdjService.saveHqAdjDtl(hqAdjVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
