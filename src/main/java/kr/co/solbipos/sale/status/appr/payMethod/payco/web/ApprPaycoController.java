package kr.co.solbipos.sale.status.appr.payMethod.payco.web;

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
import kr.co.solbipos.sale.status.appr.payMethod.payco.service.ApprPaycoService;
import kr.co.solbipos.sale.status.appr.payMethod.payco.service.ApprPaycoVO;

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
public class ApprPaycoController {
    private final SessionService sessionService;
    private final ApprPaycoService apprPaycoService;

    @Autowired
    public ApprPaycoController(SessionService sessionService, ApprPaycoService apprPaycoService) {
        this.sessionService = sessionService;
        this.apprPaycoService = apprPaycoService;
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
    @RequestMapping(value = "/payco/view.sb", method = RequestMethod.GET)
    public String apprPaycoView(HttpServletRequest request, HttpServletResponse response, Model model) {
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
    @RequestMapping(value = "/payco/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getApprPaycoList(HttpServletRequest request, HttpServletResponse response,
        Model model, ApprPaycoVO apprPaycoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (apprPaycoVO.getCornrCd() != null && !"".equals(apprPaycoVO.getCornrCd()) && apprPaycoVO.getPosNo() != null && !"".equals(apprPaycoVO.getPosNo())) {
    		String[] arrCornrCd = apprPaycoVO.getCornrCd().split(",");
    		String[] arrPosNo = apprPaycoVO.getPosNo().split(",");
    		if (arrCornrCd.length > 0) {
    			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
    				apprPaycoVO.setArrCornrCd(arrCornrCd);
    				apprPaycoVO.setArrStoreCornr(arrCornrCd);
    			}
    		}
    		if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprPaycoVO.setArrPosNo(arrPosNo);
    				apprPaycoVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
    		String[] arrStoreCd = apprPaycoVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprPaycoVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
        
        List<DefaultMap<String>> list = apprPaycoService.getApprPaycoList(apprPaycoVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, apprPaycoVO);
    }
    
}
