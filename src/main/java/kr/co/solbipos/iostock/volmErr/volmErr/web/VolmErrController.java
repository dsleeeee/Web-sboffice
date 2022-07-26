package kr.co.solbipos.iostock.volmErr.volmErr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqService;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import kr.co.solbipos.iostock.volmErr.volmErr.service.VolmErrService;
import kr.co.solbipos.iostock.volmErr.volmErr.service.VolmErrVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
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

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : VolmErrController.java
 * @Description : 수불관리 > 물량오류 > 물량오류관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.22  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 10.22
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/volmErr/volmErr")
public class VolmErrController {
    private final SessionService sessionService;
    private final VolmErrService volmErrService;
    private final DstbReqService dstbReqService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;

    @Autowired
    public VolmErrController(SessionService sessionService, VolmErrService volmErrService, DstbReqService dstbReqService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService) {
        this.sessionService = sessionService;
        this.volmErrService = volmErrService;
        this.dstbReqService = dstbReqService;
        this.storeOrderService = storeOrderService;
        this.cmmEnvService = cmmEnvService;
    }


    /**
     * 물량오류관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 22.
     */
    @RequestMapping(value = "/volmErr/view.sb", method = RequestMethod.GET)
    public String volmErrView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 본사 환경설정 1242(거래처출고사용여부) 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1242");
        model.addAttribute("envst1242", CmmUtil.nvl(cmmEnvService.getHqEnvst(hqEnvstVO), "0"));

        // 본사 거래처 콤보박스
        StoreOrderVO storeOrderVO = new StoreOrderVO();
        model.addAttribute("vendrList", convertToJson(storeOrderService.getHqVendrCombo(storeOrderVO, sessionInfoVO)));

        // 현재 로그인 사원에 맵핑된 거래처코드 조회
        DstbReqVO dstbReqVO = new DstbReqVO();
        model.addAttribute("empVendrCd", dstbReqService.getEmployeeVendr(dstbReqVO, sessionInfoVO));

        return "iostock/volmErr/volmErr/volmErr";
    }

    /**
     * 물량오류관리 - 물량오류관리 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   volmErrVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 22.
     */
    @RequestMapping(value = "/volmErr/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVolmErrList(HttpServletRequest request, HttpServletResponse response,
        Model model, VolmErrVO volmErrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        volmErrVO.setOrgnFg		(sessionInfoVO.getOrgnFg().getCode());	//소속구분(M:시스템, A:대리점, H:본사, S:매장,가맹점
        volmErrVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd()		);	//본사코드
        volmErrVO.setStoreCd	(sessionInfoVO.getStoreCd()			);	//매장코드

        List<DefaultMap<String>> list = volmErrService.getVolmErrList(volmErrVO);

        return ReturnUtil.returnListJson(Status.OK, list, volmErrVO);
    }

    /**
     * 물량오류관리 - 물량오류관리 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   volmErrVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 22.
     */
    @RequestMapping(value = "/volmErrDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVolmErrDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, VolmErrVO volmErrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        volmErrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = volmErrService.getVolmErrDtlList(volmErrVO);

        return ReturnUtil.returnListJson(Status.OK, list, volmErrVO);
    }


    /**
     * 물량오류관리 - 물량오류관리 상세 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   volmErrVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 22.
     */
    @RequestMapping(value = "/volmErrDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVolmErrDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody VolmErrVO[] volmErrVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = volmErrService.saveVolmErrDtl(volmErrVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

}
