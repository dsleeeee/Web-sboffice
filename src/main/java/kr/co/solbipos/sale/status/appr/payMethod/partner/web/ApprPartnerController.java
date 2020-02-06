package kr.co.solbipos.sale.status.appr.payMethod.partner.web;

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
import kr.co.solbipos.sale.status.appr.payMethod.partner.service.ApprPartnerService;
import kr.co.solbipos.sale.status.appr.payMethod.partner.service.ApprPartnerVO;

/**
 * @Class Name : ApprCardController.java
 * @Description : 매출관리 > 승인현황 > 신용카드 승인현황 탭
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
public class ApprPartnerController {
    private final SessionService sessionService;
    private final ApprPartnerService apprPartnerService;

    @Autowired
    public ApprPartnerController(SessionService sessionService, ApprPartnerService apprPartnerService) {
        this.sessionService = sessionService;
        this.apprPartnerService = apprPartnerService;
    }


    /**
     * 신용카드 승인현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/partner/view.sb", method = RequestMethod.GET)
    public String apprPartnerView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/appr/apprSale";
    }


    /**
     * 신용카드 승인현황 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   apprCardVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/partner/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getApprPartnerList(HttpServletRequest request, HttpServletResponse response,
        Model model, ApprPartnerVO apprPartnerVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (apprPartnerVO.getCornrCd() != null && !"".equals(apprPartnerVO.getCornrCd()) && apprPartnerVO.getPosNo() != null && !"".equals(apprPartnerVO.getPosNo())) {
    		String[] arrCornrCd = apprPartnerVO.getCornrCd().split(",");
    		String[] arrPosNo = apprPartnerVO.getPosNo().split(",");
    		if (arrCornrCd.length > 0) {
    			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
    				apprPartnerVO.setArrCornrCd(arrCornrCd);
    				apprPartnerVO.setArrStoreCornr(arrCornrCd);
    			}
    		}
    		if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprPartnerVO.setArrPosNo(arrPosNo);
    				apprPartnerVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
    		String[] arrStoreCd = apprPartnerVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprPartnerVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
        
        List<DefaultMap<String>> list = apprPartnerService.getApprPartnerList(apprPartnerVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, apprPartnerVO);
    }
    
}
