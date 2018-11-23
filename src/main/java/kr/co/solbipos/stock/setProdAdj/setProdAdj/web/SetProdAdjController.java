package kr.co.solbipos.stock.setProdAdj.setProdAdj.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.setProdAdj.setProdAdj.service.SetProdAdjService;
import kr.co.solbipos.stock.setProdAdj.setProdAdj.service.SetProdAdjVO;
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
 * @Class Name : SetProdAdjController.java
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
@RequestMapping("/stock/setProdAdj/setProdAdj")
public class SetProdAdjController {
    private final SessionService sessionService;
    private final SetProdAdjService setProdAdjService;

    @Autowired
    public SetProdAdjController(SessionService sessionService, SetProdAdjService setProdAdjService) {
        this.sessionService = sessionService;
        this.setProdAdjService = setProdAdjService;
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
    @RequestMapping(value = "/setProdAdj/view.sb", method = RequestMethod.GET)
    public String setProdAdjView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "stock/setProdAdj/setProdAdj/setProdAdj";
    }


    /**
     * 세트재고조정 - 세트재고조정 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   setProdAdjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 13.
     */
    @RequestMapping(value = "/setProdAdj/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSetProdAdjList(HttpServletRequest request, HttpServletResponse response,
        Model model, SetProdAdjVO setProdAdjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = setProdAdjService.getSetProdAdjList(setProdAdjVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, setProdAdjVO);
    }


    /**
     * 세트재고조정 - 세트재고 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   setProdAdjVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 13.
     */
    @RequestMapping(value = "/setProdAdj/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteSetProdAdj(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody SetProdAdjVO[] setProdAdjVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = setProdAdjService.deleteSetProdAdj(setProdAdjVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 세트재고조정 - 세트재고조정 세트상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   setProdAdjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 14.
     */
    @RequestMapping(value = "/setProdAdjRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSetProdAdjRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, SetProdAdjVO setProdAdjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = setProdAdjService.getSetProdAdjRegistList(setProdAdjVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, setProdAdjVO);
    }


    /**
     * 세트재고조정 - 세트재고조정 세트구성상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   setProdAdjVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 14.
     */
    @RequestMapping(value = "/setProdAdjRegistCompst/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSetProdAdjRegistCompstList(HttpServletRequest request, HttpServletResponse response,
        Model model, SetProdAdjVO setProdAdjVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = setProdAdjService.getSetProdAdjRegistCompstList(setProdAdjVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, setProdAdjVO);
    }


    /**
     * 세트재고조정 - 세트재고조정 등록
     * @param   request
     * @param   response
     * @param   model
     * @param   setProdAdjVOs
     * @return  String
     * @author  안동관
     * @since   2018. 11. 14.
     */
    @RequestMapping(value = "/setProdAdjRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSetProdAdjRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody SetProdAdjVO[] setProdAdjVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = setProdAdjService.saveSetProdAdjRegist(setProdAdjVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
