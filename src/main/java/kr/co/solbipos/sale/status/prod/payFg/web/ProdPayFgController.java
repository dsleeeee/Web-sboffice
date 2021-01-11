package kr.co.solbipos.sale.status.prod.payFg.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgService;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : ProdPayFgController.java
 * @Description : 매출관리 > 매출현황 > 상품별 > 결제수단별탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.08  김진      최초생성
 *
 * @author 솔비포스 
 * @since 2020.01.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/prod")
public class ProdPayFgController {
    private final SessionService sessionService;
    private final ProdPayFgService prodPayFgService;

    @Autowired
    public ProdPayFgController(SessionService sessionService, ProdPayFgService prodPayFgService) {
        this.sessionService = sessionService;
        this.prodPayFgService = prodPayFgService;
    }

    /**
     * 결제수단별탭 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 01. 08.
     */
    @RequestMapping(value = "/PayFg/view.sb", method = RequestMethod.GET)
    public String prodPayFgView(HttpServletRequest request, HttpServletResponse response, Model model) {

    	ProdPayFgVO prodPayFgVO = new ProdPayFgVO();
    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = prodPayFgService.getPayColList(prodPayFgVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        return "sale/status/prod/payFg/prodPayFg";
    }

    /**
     * 결제수단별탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodPayFgVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 08.
     */
    @RequestMapping(value = "/payFg/getProdPayFgList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdPayFgList(HttpServletRequest request, HttpServletResponse response, Model model, ProdPayFgVO prodPayFgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodPayFgService.getProdPayFgList(prodPayFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodPayFgVO);
    }
    
    /**
     * 결제수단별탭 - 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodPayFgVO
     * @return  String
     * @author  서재식
     * @since   2020. 04. 21.
     */
    @RequestMapping(value = "/payFg/getProdPayFgExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdPayFgExcelList(HttpServletRequest request, HttpServletResponse response, Model model, ProdPayFgVO prodPayFgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodPayFgService.getProdPayFgExcelList(prodPayFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodPayFgVO);
    }
    
}