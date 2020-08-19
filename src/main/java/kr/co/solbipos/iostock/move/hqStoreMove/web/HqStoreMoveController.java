package kr.co.solbipos.iostock.move.hqStoreMove.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.move.hqStoreMove.service.HqStoreMoveService;
import kr.co.solbipos.iostock.move.hqStoreMove.service.HqStoreMoveVO;
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
 * @Class Name : HqStoreMoveController.java
 * @Description : 수불관리 > 이동관리 > 매장이동관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.24  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 10.24
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/move/hqStoreMove")
public class HqStoreMoveController {
    private final SessionService sessionService;
    private final HqStoreMoveService hqStoreMoveService;
    private final MessageService messageService;

    @Autowired
    public HqStoreMoveController(SessionService sessionService, HqStoreMoveService hqStoreMoveService, MessageService messageService) {
        this.sessionService = sessionService;
        this.hqStoreMoveService = hqStoreMoveService;
        this.messageService = messageService;
    }

    /**
     * 매장이동관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 24.
     */
    @RequestMapping(value = "/move/view.sb", method = RequestMethod.GET)
    public String hqStoreMoveView(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("selectStoreDisplayNmAll", messageService.get("cmm.all"));
        return "iostock/move/move";
    }

    /**
     * 매장이동관리 - 매장이동관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqStoreMoveVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 24.
     */
    @RequestMapping(value = "/hqStoreMove/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqStoreMoveList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqStoreMoveVO hqStoreMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqStoreMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqStoreMoveService.getHqStoreMoveList(hqStoreMoveVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqStoreMoveVO);
    }

    /**
     * 매장이동관리 - 전표상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqStoreMoveVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 25.
     */
    @RequestMapping(value = "/hqStoreMoveDtl/getSlipNoInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipNoInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, HqStoreMoveVO hqStoreMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqStoreMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = hqStoreMoveService.getSlipNoInfo(hqStoreMoveVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매장이동관리 - 매장이동관리 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqStoreMoveVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 24.
     */
    @RequestMapping(value = "/hqStoreMoveDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqStoreMoveDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqStoreMoveVO hqStoreMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqStoreMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqStoreMoveService.getHqStoreMoveDtlList(hqStoreMoveVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqStoreMoveVO);
    }

    /**
     * 매장이동관리 - 매장이동관리 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   hqStoreMoveVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 24.
     */
    @RequestMapping(value = "/hqStoreMoveDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqStoreMoveDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqStoreMoveVO[] hqStoreMoveVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqStoreMoveService.saveHqStoreMoveDtl(hqStoreMoveVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 매장이동관리 - 매장이동관리 상세 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   hqStoreMoveVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 26.
     */
    @RequestMapping(value = "/hqStoreMoveDtl/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteHqStoreMoveDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, HqStoreMoveVO hqStoreMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqStoreMoveService.deleteHqStoreMoveDtl(hqStoreMoveVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 매장이동관리 - 매장이동관리 신규등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqStoreMoveVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 24.
     */
    @RequestMapping(value = "/hqStoreMoveRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqStoreMoveRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqStoreMoveVO hqStoreMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqStoreMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqStoreMoveService.getHqStoreMoveRegistList(hqStoreMoveVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqStoreMoveVO);
    }

    /**
     * 매장이동관리 - 매장이동관리 신규등록 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   hqStoreMoveVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 25.
     */
    @RequestMapping(value = "/hqStoreMoveRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqStoreMoveRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqStoreMoveVO[] hqStoreMoveVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqStoreMoveService.saveHqStoreMoveRegist(hqStoreMoveVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매장이동관리 - 매장이동관리 상품추가 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqStoreMoveVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 25.
     */
    @RequestMapping(value = "/hqStoreMoveAddProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqStoreMoveAddProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqStoreMoveVO hqStoreMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqStoreMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqStoreMoveService.getHqStoreMoveAddProdList(hqStoreMoveVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqStoreMoveVO);
    }

    /**
     * 매장이동관리 - 매장이동관리 상품추가 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   hqStoreMoveVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 25.
     */
    @RequestMapping(value = "/hqStoreMoveAddProd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqStoreMoveAddProd(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqStoreMoveVO[] hqStoreMoveVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqStoreMoveService.saveHqStoreMoveAddProd(hqStoreMoveVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
