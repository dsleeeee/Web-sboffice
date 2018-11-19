package kr.co.solbipos.stock.setProdAdj.hqSetProdAdj.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.setProdAdj.hqSetProdAdj.service.HqSetProdAdjService;
import kr.co.solbipos.stock.setProdAdj.hqSetProdAdj.service.HqSetProdAdjVO;
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
 * @Class Name : HqSetProdAdjController.java
 * @Description : 재고관리 > 세트재고조정 > 세트재고조정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.13  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 11.13
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/setProdAdj/hqSetProdAdj")
public class HqSetProdAdjController {
    private final SessionService sessionService;
    private final HqSetProdAdjService hqSetProdAdjService;

    @Autowired
    public HqSetProdAdjController(SessionService sessionService, HqSetProdAdjService hqSetProdAdjService) {
        this.sessionService = sessionService;
        this.hqSetProdAdjService = hqSetProdAdjService;
    }

    /**
     * 세트재고조정 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 11. 13.
     */
    @RequestMapping(value = "/hqSetProdAdj/view.sb", method = RequestMethod.GET)
    public String hqSetProdAdjView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "stock/setProdAdj/hqSetProdAdj/hqSetProdAdj";
    }


    /**
     * 세트재고조정 - 세트재고조정 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqSetProdAdjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 13.
     */
    @RequestMapping(value = "/hqSetProdAdj/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSetProdAdjList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqSetProdAdjVO hqSetProdAdjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqSetProdAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqSetProdAdjService.getHqSetProdAdjList(hqSetProdAdjVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqSetProdAdjVO);
    }


    /**
     * 세트재고조정 - 세트재고 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   hqSetProdAdjVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 13.
     */
    @RequestMapping(value = "/hqSetProdAdj/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteHqSetProdAdj(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqSetProdAdjVO[] hqSetProdAdjVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqSetProdAdjService.deleteHqSetProdAdj(hqSetProdAdjVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 세트재고조정 - 세트재고조정 세트상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqSetProdAdjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 14.
     */
    @RequestMapping(value = "/hqSetProdAdjRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSetProdAdjRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqSetProdAdjVO hqSetProdAdjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqSetProdAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqSetProdAdjService.getHqSetProdAdjRegistList(hqSetProdAdjVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqSetProdAdjVO);
    }


    /**
     * 세트재고조정 - 세트재고조정 세트구성상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqSetProdAdjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 14.
     */
    @RequestMapping(value = "/hqSetProdAdjRegistCompst/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSetProdAdjRegistCompstList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqSetProdAdjVO hqSetProdAdjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqSetProdAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqSetProdAdjService.getHqSetProdAdjRegistCompstList(hqSetProdAdjVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqSetProdAdjVO);
    }


    /**
     * 세트재고조정 - 세트재고조정 등록
     * @param   request
     * @param   response
     * @param   model
     * @param   hqSetProdAdjVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 14.
     */
    @RequestMapping(value = "/hqSetProdAdjRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqSetProdAdjRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqSetProdAdjVO[] hqSetProdAdjVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqSetProdAdjService.saveHqSetProdAdjRegist(hqSetProdAdjVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
