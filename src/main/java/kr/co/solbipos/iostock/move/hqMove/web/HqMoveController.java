package kr.co.solbipos.iostock.move.hqMove.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.move.hqMove.service.HqMoveService;
import kr.co.solbipos.iostock.move.hqMove.service.HqMoveVO;
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
 * @Class Name : HqMoveController.java
 * @Description : 수불관리 > 이동관리 > 본사이동관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.24  정유경      최초생성
 *
 * @author 정유경
 * @since 2020.03.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/move/hqMove")
public class HqMoveController {
    private final SessionService sessionService;
    private final HqMoveService hqMoveService;
    private final MessageService messageService;

    @Autowired
    public HqMoveController(SessionService sessionService, HqMoveService hqMoveService, MessageService messageService) {
        this.sessionService = sessionService;
        this.hqMoveService = hqMoveService;
        this.messageService = messageService;
    }

    /**
     * 본사이동관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  정유경
     * @since   2020.03.26
     */
    @RequestMapping(value = "/move/view.sb", method = RequestMethod.GET)
    public String hqMoveView(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("selectStoreDisplayNmAll", messageService.get("cmm.all"));
        return "iostock/move/hqMove/hqMove";
    }

    /**
     * 본사이동관리 - 본사이동관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqMoveVO
     * @return  String
     * @author  정유경
     * @since   2020.03.26
     */
    @RequestMapping(value = "/hqMove/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqMoveList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqMoveVO hqMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqMoveService.getHqMoveList(hqMoveVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqMoveVO);
    }

    /**
     * 본사이동관리 - 전표상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqMoveVO
     * @return  String
     * @author  정유경
     * @since   2020.03.26
     */
    @RequestMapping(value = "/hqMoveDtl/getSlipNoInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipNoInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, HqMoveVO hqMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = hqMoveService.getSlipNoInfo(hqMoveVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 본사이동관리 - 본사이동관리 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqMoveVO
     * @return  String
     * @author  정유경
     * @since   2020.03.26
     */
    @RequestMapping(value = "/hqMoveDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqMoveDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqMoveVO hqMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqMoveService.getHqMoveDtlList(hqMoveVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqMoveVO);
    }

    /**
     * 본사이동관리 - 본사이동관리 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   hqMoveVOs
     * @return  String
     * @author  정유경
     * @since   2020.03.26
     */
    @RequestMapping(value = "/hqMoveDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqMoveDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqMoveVO[] hqMoveVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqMoveService.saveHqMoveDtl(hqMoveVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 본사이동관리 - 본사이동관리 상세 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   hqMoveVO
     * @return  String
     * @author  정유경
     * @since   2020.03.26
     */
    @RequestMapping(value = "/hqMoveDtl/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteHqMoveDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, HqMoveVO hqMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqMoveService.deleteHqMoveDtl(hqMoveVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 본사이동관리 - 본사이동관리 신규등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqMoveVO
     * @return  String
     * @author  정유경
     * @since   2020.03.26
     */
    @RequestMapping(value = "/hqMoveRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqMoveRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqMoveVO hqMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqMoveService.getHqMoveRegistList(hqMoveVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqMoveVO);
    }

    /**
     * 본사이동관리 - 본사이동관리 신규등록 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   hqMoveVOs
     * @return  String
     * @author  정유경
     * @since   2020.03.26
     */
    @RequestMapping(value = "/hqMoveRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqMoveRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqMoveVO[] hqMoveVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqMoveService.saveHqMoveRegist(hqMoveVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 본사이동관리 - 본사이동관리 상품추가 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   hqMoveVO
     * @return  String
     * @author  정유경
     * @since   2020.03.26
     */
    @RequestMapping(value = "/hqMoveAddProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqMoveAddProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, HqMoveVO hqMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        hqMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = hqMoveService.getHqMoveAddProdList(hqMoveVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, hqMoveVO);
    }

    /**
     * 본사이동관리 - 본사이동관리 상품추가 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   hqMoveVOs
     * @return  String
     * @author  정유경
     * @since   2020.03.26
     */
    @RequestMapping(value = "/hqMoveAddProd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqMoveAddProd(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody HqMoveVO[] hqMoveVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = hqMoveService.saveHqMoveAddProd(hqMoveVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
