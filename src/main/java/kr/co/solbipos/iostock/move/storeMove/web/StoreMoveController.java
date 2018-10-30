package kr.co.solbipos.iostock.move.storeMove.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.move.hqStoreMove.service.HqStoreMoveVO;
import kr.co.solbipos.iostock.move.storeMove.service.StoreMoveService;
import kr.co.solbipos.iostock.move.storeMove.service.StoreMoveVO;
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
 * @Class Name : StoreMoveController.java
 * @Description : 수불관리 > 이동관리 > 매장이동관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.26  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 10.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/move/storeMove")
public class StoreMoveController {
    private final SessionService sessionService;
    private final StoreMoveService storeMoveService;
    private final MessageService messageService;

    @Autowired
    public StoreMoveController(SessionService sessionService, StoreMoveService storeMoveService, MessageService messageService) {
        this.sessionService = sessionService;
        this.storeMoveService = storeMoveService;
        this.messageService = messageService;
    }


    /**
     * 매장이동관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 26.
     */
    @RequestMapping(value = "/storeMove/view.sb", method = RequestMethod.GET)
    public String storeMoveView(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("selectStoreDisplayNmAll", messageService.get("cmm.all"));
        return "iostock/move/storeMove/storeMove";
    }

    /**
     * 매장이동관리 - 매장이동관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMoveVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 26.
     */
    @RequestMapping(value = "/storeMove/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreMoveList(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreMoveVO storeMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = storeMoveService.getStoreMoveList(storeMoveVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeMoveVO);
    }

    /**
     * 매장이동관리 - 전표상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMoveVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 26.
     */
    @RequestMapping(value = "/storeMoveDtl/getSlipNoInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipNoInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreMoveVO storeMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = storeMoveService.getSlipNoInfo(storeMoveVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매장이동관리 - 매장이동관리 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMoveVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 26.
     */
    @RequestMapping(value = "/storeMoveDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreMoveDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreMoveVO storeMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = storeMoveService.getStoreMoveDtlList(storeMoveVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeMoveVO);
    }

    /**
     * 매장이동관리 - 매장이동관리 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMoveVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 26.
     */
    @RequestMapping(value = "/storeMoveDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreMoveDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody StoreMoveVO[] storeMoveVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeMoveService.saveStoreMoveDtl(storeMoveVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 매장이동관리 - 매장이동관리 상세 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMoveVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 26.
     */
    @RequestMapping(value = "/storeMoveDtl/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteStoreMoveDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreMoveVO storeMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeMoveService.deleteStoreMoveDtl(storeMoveVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 매장이동관리 - 매장이동관리 신규등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMoveVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 26.
     */
    @RequestMapping(value = "/storeMoveRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreMoveRegistList(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreMoveVO storeMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = storeMoveService.getStoreMoveRegistList(storeMoveVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeMoveVO);
    }

    /**
     * 매장이동관리 - 매장이동관리 신규등록 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMoveVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 26.
     */
    @RequestMapping(value = "/storeMoveRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreMoveRegist(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody StoreMoveVO[] storeMoveVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeMoveService.saveStoreMoveRegist(storeMoveVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매장이동관리 - 매장이동관리 상품추가 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMoveVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 26.
     */
    @RequestMapping(value = "/storeMoveAddProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreMoveAddProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreMoveVO storeMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = storeMoveService.getStoreMoveAddProdList(storeMoveVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeMoveVO);
    }

    /**
     * 매장이동관리 - 매장이동관리 상품추가 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMoveVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 26.
     */
    @RequestMapping(value = "/storeMoveAddProd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreMoveAddProd(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody StoreMoveVO[] storeMoveVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeMoveService.saveStoreMoveAddProd(storeMoveVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

}
