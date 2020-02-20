package kr.co.solbipos.sale.status.appr.acquire.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.acquire.service.ApprAcquireService;
import kr.co.solbipos.sale.status.appr.acquire.service.ApprAcquireVO;

/**
 * @Class Name : ApprAcquireController.java
 * @Description : 매출관리 > 승인현황 > 카드매입사별  탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.31  조동훤      최초생성
 *
 * @author 
 * @since 2020.01.31
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/appr")
public class ApprAcquireController {
    private final SessionService sessionService;
    private final ApprAcquireService apprAcquireService;

    @Autowired
    public ApprAcquireController(SessionService sessionService, ApprAcquireService apprAcquireService) {
        this.sessionService = sessionService;
        this.apprAcquireService = apprAcquireService;
    }

    /**
     * 카드매입사별 탭 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   apprAcquireVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/acquireCard/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getApprAcquireList(HttpServletRequest request, HttpServletResponse response,
        Model model, ApprAcquireVO apprAcquireVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd()) && apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo())) {
    		String[] arrCornrCd = apprAcquireVO.getCornrCd().split(",");
    		String[] arrPosNo = apprAcquireVO.getPosNo().split(",");
    		if (arrCornrCd.length > 0) {
    			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
    				apprAcquireVO.setArrCornrCd(arrCornrCd);
    				apprAcquireVO.setArrStoreCornr(arrCornrCd);
    			}
    		}
    		if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprAcquireVO.setArrPosNo(arrPosNo);
    				apprAcquireVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
    		String[] arrStoreCd = apprAcquireVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprAcquireVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
        List<DefaultMap<String>> list = apprAcquireService.getApprAcquireList(apprAcquireVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, apprAcquireVO);
    }
    
    
    /**
     * 카드매입사별 탭 - 모바일쿠폰 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   apprAcquireVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/acquireMcoupon/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getApprAcquireMcouponList(HttpServletRequest request, HttpServletResponse response,
        Model model, ApprAcquireVO apprAcquireVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd()) && apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo())) {
    		String[] arrCornrCd = apprAcquireVO.getCornrCd().split(",");
    		String[] arrPosNo = apprAcquireVO.getPosNo().split(",");
    		if (arrCornrCd.length > 0) {
    			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
    				apprAcquireVO.setArrCornrCd(arrCornrCd);
    				apprAcquireVO.setArrStoreCornr(arrCornrCd);
    			}
    		}
    		if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprAcquireVO.setArrPosNo(arrPosNo);
    				apprAcquireVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
    		String[] arrStoreCd = apprAcquireVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprAcquireVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
        List<DefaultMap<String>> list = apprAcquireService.getApprAcquireMcouponList(apprAcquireVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, apprAcquireVO);
    }
    
    
    /**
     * 카드매입사별 탭 - 모바일쿠폰 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   apprAcquireVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/acquireMpay/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getApprAcquireMpayList(HttpServletRequest request, HttpServletResponse response,
        Model model, ApprAcquireVO apprAcquireVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd()) && apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo())) {
    		String[] arrCornrCd = apprAcquireVO.getCornrCd().split(",");
    		String[] arrPosNo = apprAcquireVO.getPosNo().split(",");
    		if (arrCornrCd.length > 0) {
    			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
    				apprAcquireVO.setArrCornrCd(arrCornrCd);
    				apprAcquireVO.setArrStoreCornr(arrCornrCd);
    			}
    		}
    		if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprAcquireVO.setArrPosNo(arrPosNo);
    				apprAcquireVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
    		String[] arrStoreCd = apprAcquireVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprAcquireVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
        List<DefaultMap<String>> list = apprAcquireService.getApprAcquireMpayList(apprAcquireVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, apprAcquireVO);
    }
    
    /**
     * 카드매입사별 탭 - 모바일쿠폰 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   apprAcquireVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/acquireNcard/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getApprAcquireNcardList(HttpServletRequest request, HttpServletResponse response,
        Model model, ApprAcquireVO apprAcquireVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd()) && apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo())) {
    		String[] arrCornrCd = apprAcquireVO.getCornrCd().split(",");
    		String[] arrPosNo = apprAcquireVO.getPosNo().split(",");
    		if (arrCornrCd.length > 0) {
    			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
    				apprAcquireVO.setArrCornrCd(arrCornrCd);
    				apprAcquireVO.setArrStoreCornr(arrCornrCd);
    			}
    		}
    		if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprAcquireVO.setArrPosNo(arrPosNo);
    				apprAcquireVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
    		String[] arrStoreCd = apprAcquireVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprAcquireVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
        List<DefaultMap<String>> list = apprAcquireService.getApprAcquireNcardList(apprAcquireVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, apprAcquireVO);
    }
}
