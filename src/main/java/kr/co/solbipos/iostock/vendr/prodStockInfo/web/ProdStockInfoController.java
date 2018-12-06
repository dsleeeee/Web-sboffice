package kr.co.solbipos.iostock.vendr.prodStockInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.vendr.prodStockInfo.service.ProdStockInfoService;
import kr.co.solbipos.iostock.vendr.prodStockInfo.service.ProdStockInfoVO;
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
 * @Class Name : ProdStockInfoController.java
 * @Description : 수불관리 > 거래처(매입)입출고관리 > 상품별 입출고내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.05  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 12.05
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/vendr/prodStockInfo")
public class ProdStockInfoController {
    private final SessionService sessionService;
    private final ProdStockInfoService prodStockInfoService;

    @Autowired
    public ProdStockInfoController(SessionService sessionService, ProdStockInfoService prodStockInfoService) {
        this.sessionService = sessionService;
        this.prodStockInfoService = prodStockInfoService;
    }

    /**
     * 거래처 상품별 입출고내역 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/prodStockInfo/view.sb", method = RequestMethod.GET)
    public String prodStockInfoView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/vendr/prodStockInfo/prodStockInfo";
    }


    /**
     * 거래처 상품별 입출고내역 - 상품별 입출고내역 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodStockInfoVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/prodStockInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdStockInfoList(HttpServletRequest request, HttpServletResponse response,
        Model model, ProdStockInfoVO prodStockInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>>
            list = prodStockInfoService.getProdStockInfoList(prodStockInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodStockInfoVO);
    }
}
