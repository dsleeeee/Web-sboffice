package kr.co.solbipos.iostock.order.instockConfm.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmService;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.iostock.order.instockConfm.service.InstockConfmService;
import kr.co.solbipos.iostock.order.instockConfm.service.InstockConfmVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : InstockConfmController.java
 * @Description : 수불관리 > 주문관리 > 입고확정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.15  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018.10.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/order/instockConfm")
public class InstockConfmController {
	private final Logger	LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final InstockConfmService instockConfmService;
    private final CmmEnvService cmmEnvService;
    private final IostockCmmService iostockCmmService;

    @Autowired
    public InstockConfmController(SessionService sessionService, InstockConfmService instockConfmService, CmmEnvService cmmEnvService, IostockCmmService iostockCmmService) {
        this.sessionService = sessionService;
        this.instockConfmService = instockConfmService;
        this.cmmEnvService = cmmEnvService;
        this.iostockCmmService = iostockCmmService;
    }

    /**
     * 입고확정 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/instockConfm/view.sb", method = RequestMethod.GET)
    public String instockConfmView(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 매장입고 환경변수 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1043");
        String envst1043 = cmmEnvService.getHqEnvst(hqEnvstVO);
        model.addAttribute("envst1043", envst1043);

        return "iostock/order/instockConfm/instockConfm";
    }

    /**
     * 입고확정 - 입고확정 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   instockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/instockConfm/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInstockConfmList(HttpServletRequest request, HttpServletResponse response,
        Model model, InstockConfmVO instockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        instockConfmVO.setOrgnFg	(sessionInfoVO.getOrgnFg().getCode());	//소속구분(M:시스템, A:대리점, H:본사, S:매장,가맹점
        instockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd()		);	//본사코드
        instockConfmVO.setStoreCd	(sessionInfoVO.getStoreCd()			);	//매장코드

        LOGGER.debug("### OrgnFg        : " + instockConfmVO.getOrgnFg		());
        LOGGER.debug("### hqOfficeCd    : " + instockConfmVO.getHqOfficeCd	());
        LOGGER.debug("### storeCd       : " + instockConfmVO.getStoreCd		());

        List<DefaultMap<String>> list = instockConfmService.getInstockConfmList(instockConfmVO);

        return ReturnUtil.returnListJson(Status.OK, list, instockConfmVO);
    }

    /**
     * 입고확정 - 전표상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   instockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/instockConfmDtl/getSlipNoInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipNoInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, InstockConfmVO instockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        instockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = instockConfmService.getSlipNoInfo(instockConfmVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 입고확정 - 입고확정 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   instockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/instockConfmDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInstockConfmDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, InstockConfmVO instockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        instockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        instockConfmVO.setStoreCd   (sessionInfoVO.getStoreCd   ());

        List<DefaultMap<String>> list = instockConfmService.getInstockConfmDtlList(instockConfmVO);

        return ReturnUtil.returnListJson(Status.OK, list, instockConfmVO);
    }

    /**
     * 입고확정 - 입고확정 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   instockConfmVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/instockConfmDtl/save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveInstockConfmDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody InstockConfmVO[] instockConfmVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = instockConfmService.saveInstockConfmDtl(instockConfmVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
    
    /**
     * 다이나믹 콤보조회 - 출고창고 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  M2M
     * @since   2020. 08. 03.
     */
    @RequestMapping(value = "/instockConfm/getInStorageCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOutStorageCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        
        String storeCd	=	(sessionInfoVO.getStoreCd() == null ?  iostockCmmVO.getStoreCd() : sessionInfoVO.getStoreCd());
        iostockCmmVO.setSelectTable("TB_MS_STORAGE");
        iostockCmmVO.setSelectCd("STORAGE_CD");
        iostockCmmVO.setSelectNm("STORAGE_NM");
        iostockCmmVO.setSelectWhere("HQ_OFFICE_CD='"+sessionInfoVO.getHqOfficeCd()+"'"
            						+ " AND STORE_CD='"+ storeCd +"' "        		
        		);
            list = iostockCmmService.selectDynamicCodeList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }    
}
