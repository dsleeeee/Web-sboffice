package kr.co.solbipos.iostock.move.standMove.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.move.standMove.service.StandMoveService;
import kr.co.solbipos.iostock.move.standMove.service.StandMoveVO;
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
 * @Class Name : standMoveController.java
 * @Description : 수불관리 > 이동관리 > 매대이동관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.04.09  김진      최초생성
 *
 * @author 솔비포스 
 * @since  2020.04.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/move/standMove")
public class StandMoveController {
    private final SessionService sessionService;
    private final StandMoveService standMoveService;
    private final MessageService messageService;

    @Autowired
    public StandMoveController(SessionService sessionService, StandMoveService standMoveService, MessageService messageService) {
        this.sessionService = sessionService;
        this.standMoveService = standMoveService;
        this.messageService = messageService;
    }


    /**
     * 매대이동관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 04. 09.
     */
    @RequestMapping(value = "/move/view.sb", method = RequestMethod.GET)
    public String standMoveView(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("selectStandDisplayNmAll", messageService.get("cmm.all"));
        return "iostock/move/standMove/standMove";
    }

    /**
     * 매대이동관리 - 매대이동관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   standMoveVO
     * @return  String
     * @author  김진
     * @since   2020. 04. 09.
     */
    @RequestMapping(value = "/standMove/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStandMoveList(HttpServletRequest request, HttpServletResponse response, Model model, StandMoveVO standMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        standMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = standMoveService.getStandMoveList(standMoveVO);

        return ReturnUtil.returnListJson(Status.OK, list, standMoveVO);
    }

    /**
     * 매대이동관리 - 매대이동관리 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   standMoveVO
     * @return  String
     * @author  김진
     * @since   2020. 04. 09.
     */
    @RequestMapping(value = "/standMoveDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getstandMoveDtlList(HttpServletRequest request, HttpServletResponse response, Model model, StandMoveVO standMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        standMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = standMoveService.getStandMoveDtlList(standMoveVO);

        return ReturnUtil.returnListJson(Status.OK, list, standMoveVO);
    }

    /**
     * 매대이동관리 - 매대이동관리 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   standMoveVOs
     * @return  String
     * @author  김진
     * @since   2020. 04. 09.
     */
    @RequestMapping(value = "/standMoveDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savestandMoveDtl(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody StandMoveVO[] standMoveVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = standMoveService.saveStandMoveDtl(standMoveVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
      
    /**
     * 매대이동관리 - 매대이동관리 신규등록 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   standMoveVO
     * @return  String
     * @author  김진
     * @since   2020. 04. 09.
     */
    @RequestMapping(value = "/standMoveRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getstandMoveRegistList(HttpServletRequest request, HttpServletResponse response, Model model, StandMoveVO standMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        standMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        
        List<DefaultMap<String>> list = standMoveService.getStandMoveRegistList(standMoveVO);

        return ReturnUtil.returnListJson(Status.OK, list, standMoveVO);
    }

    /**
     * 매대이동관리 - 매대이동관리 신규등록 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   standMoveVOs
     * @return  String
     * @author  김진
     * @since   2020. 04. 09.
     */
    @RequestMapping(value = "/standMoveRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savestandMoveRegist(HttpServletRequest request, HttpServletResponse response,  Model model, @RequestBody StandMoveVO[] standMoveVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = standMoveService.saveStandMoveRegist(standMoveVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매대이동관리 - 매대이동관리 상품추가 상품 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   standMoveVO
     * @return  String
     * @author  김진
     * @since   2020. 04. 09.
     */
    @RequestMapping(value = "/standMoveAddProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getstandMoveAddProdList(HttpServletRequest request, HttpServletResponse response, Model model, StandMoveVO standMoveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        standMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = standMoveService.getStandMoveAddProdList(standMoveVO);

        return ReturnUtil.returnListJson(Status.OK, list, standMoveVO);
    }

    /**
     * 매대이동관리 - 매대이동관리 상품추가 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   standMoveVOs
     * @return  String
     * @author  김진
     * @since   2020. 04. 09.
     */
    @RequestMapping(value = "/standMoveAddProd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savestandMoveAddProd(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody StandMoveVO[] standMoveVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = standMoveService.saveStandMoveAddProd(standMoveVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

}
