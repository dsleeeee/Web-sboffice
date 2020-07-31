package kr.co.solbipos.iostock.order.outstockConfm.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmService;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmService;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmVO;
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
 * @Class Name : OutstockConfmController.java
 * @Description : 수불관리 > 수주관리 > 출고확정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.04  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018.10.05
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/order/outstockConfm")
public class OutstockConfmController {
    private final SessionService sessionService;
    private final OutstockConfmService outstockConfmService;
    private final IostockCmmService iostockCmmService;

    @Autowired
    public OutstockConfmController(SessionService sessionService, OutstockConfmService outstockConfmService, IostockCmmService iostockCmmService) {
        this.sessionService = sessionService;
        this.outstockConfmService = outstockConfmService;
        this.iostockCmmService = iostockCmmService;
    }

    /**
     * 출고확정 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 05.
     */
    @RequestMapping(value = "/outstockConfm/view.sb", method = RequestMethod.GET)
    public String outstockConfmView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/order/outstockConfm/outstockConfm";
    }

    /**
     * 출고확정 - 매장요청 미확정건, 출고자료 미생성건 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 05.
     */
    @RequestMapping(value = "/outstockConfm/reqNoConfirmCnt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getReqNoConfirmCnt(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockConfmVO outstockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        outstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = outstockConfmService.getReqNoConfirmCnt(outstockConfmVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 출고확정 - 출고확정 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 05.
     */
    @RequestMapping(value = "/outstockConfm/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOutstockConfmList(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockConfmVO outstockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        outstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = outstockConfmService.getOutstockConfmList(outstockConfmVO);

        return ReturnUtil.returnListJson(Status.OK, list, outstockConfmVO);
    }

    /**
     * 출고확정 - 출고확정
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockConfmVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 08.
     */
    @RequestMapping(value = "/outstockConfm/saveOutstockConfirm", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDataCreate(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody OutstockConfmVO[] outstockConfmVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = outstockConfmService.saveOutstockConfirm(outstockConfmVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 출고확정 - 전표상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 08.
     */
    @RequestMapping(value = "/outstockConfmDtl/getSlipNoInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSlipNoInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockConfmVO outstockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        outstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        DefaultMap<String> result = outstockConfmService.getSlipNoInfo(outstockConfmVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 출고확정 - 출고확정 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 10.
     */
    @RequestMapping(value = "/outstockConfmDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOutstockConfmDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockConfmVO outstockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        outstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = outstockConfmService.getOutstockConfmDtlList(outstockConfmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, outstockConfmVO);
    }

    /**
     * 출고확정 - 출고확정 상세 리스트 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockConfmVOs
     * @return  String
     * @author  안동관
     * @since   2018. 10. 10.
     */
    @RequestMapping(value = "/outstockConfmDtl/save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveOutstockConfmDtl(HttpServletRequest request, HttpServletResponse response,
        Model model, @RequestBody OutstockConfmVO[] outstockConfmVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = outstockConfmService.saveOutstockConfmDtl(outstockConfmVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 출고확정 - 출고확정 이후 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   outstockConfmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 10.
     */
    @RequestMapping(value = "/outstockConfmDtl/saveOutstockAfter.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveOutstockAfter(HttpServletRequest request, HttpServletResponse response,
        Model model, OutstockConfmVO outstockConfmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = outstockConfmService.saveOutstockAfter(outstockConfmVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }





    /**
     * 다이나믹 콤보조회 - 배송기사 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 30.
     */
    @RequestMapping(value = "/outstockConfm/getDlvrCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();

//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
        if (iostockCmmVO.getStorageCd() != null) {
        	iostockCmmVO.setSelectTable("TB_PO_HQ_DELIVERY_CHARGER tphdc, TB_PO_HQ_DELIVERY_CHARGER_STORAGE tphdcs");
            iostockCmmVO.setSelectCd("tphdc.DLVR_CD");
            iostockCmmVO.setSelectNm("tphdc.DLVR_NM");
            iostockCmmVO.setSelectWhere("tphdc.HQ_OFFICE_CD='"+sessionInfoVO.getHqOfficeCd()+"' "
//            iostockCmmVO.setSelectWhere("tphdc.HQ_OFFICE_CD='"+iostockCmmVO.getHqOfficeCd()+"' "
            		
            						+ " AND tphdcs.STORAGE_CD='"+ iostockCmmVO.getStorageCd() +"' "

            						+ "	AND tphdcs.HQ_OFFICE_CD(+)= tphdc.HQ_OFFICE_CD"
            						+ " AND tphdcs.DLVR_CD     (+)= tphdc.DLVR_CD");
		} else {
			iostockCmmVO.setSelectTable("TB_PO_HQ_DELIVERY_CHARGER tphdc");
            iostockCmmVO.setSelectCd("tphdc.DLVR_CD");
            iostockCmmVO.setSelectNm("tphdc.DLVR_NM");
			iostockCmmVO.setSelectWhere("HQ_OFFICE_CD='"+sessionInfoVO.getHqOfficeCd()+"'");
		}
        
        
//        iostockCmmVO.setSelectWhere("HQ_OFFICE_CD='"+iostockCmmVO.getHqOfficeCd()+"'");
            list = iostockCmmService.selectDynamicCodeList(iostockCmmVO, sessionInfoVO);
//        }
//        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//            iostockCmmVO.setSelectTable("TB_MS_STORE_NMCODE");
//            iostockCmmVO.setSelectCd("NMCODE_CD");
//            iostockCmmVO.setSelectNm("NMCODE_NM");
//            iostockCmmVO.setSelectWhere("STORE_CD='"+sessionInfoVO.getStoreCd()+"' AND NMCODE_GRP_CD = 'AA1'");
//            list = volmErrService.selectDynamicCodeList(iostockCmmVO);
//        }


        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }
    
    /**
     * 다이나믹 콤보조회 - 출고창고 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  M2M
     * @since   2020. 07. 22.
     */
    @RequestMapping(value = "/outstockConfm/getOutStorageCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOutStorageCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();

//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
        iostockCmmVO.setSelectTable("TB_HQ_STORAGE");
        iostockCmmVO.setSelectCd("STORAGE_CD");
        iostockCmmVO.setSelectNm("STORAGE_NM");
        iostockCmmVO.setSelectWhere("HQ_OFFICE_CD='"+sessionInfoVO.getHqOfficeCd()+"'");
//        iostockCmmVO.setSelectWhere("HQ_OFFICE_CD='"+iostockCmmVO.getHqOfficeCd()+"'");
            list = iostockCmmService.selectDynamicCodeList(iostockCmmVO, sessionInfoVO);
//        }
//        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//            iostockCmmVO.setSelectTable("TB_MS_STORE_NMCODE");
//            iostockCmmVO.setSelectCd("NMCODE_CD");
//            iostockCmmVO.setSelectNm("NMCODE_NM");
//            iostockCmmVO.setSelectWhere("STORE_CD='"+sessionInfoVO.getStoreCd()+"' AND NMCODE_GRP_CD = 'AA1'");
//            list = volmErrService.selectDynamicCodeList(iostockCmmVO);
//        }


        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }
}
