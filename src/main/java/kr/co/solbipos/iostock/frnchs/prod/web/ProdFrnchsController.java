package kr.co.solbipos.iostock.frnchs.prod.web;

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
import kr.co.solbipos.iostock.frnchs.prod.service.ProdFrnchsService;
import kr.co.solbipos.iostock.frnchs.prod.service.ProdFrnchsVO;
import kr.co.solbipos.iostock.vendr.orderStockInfo.service.OrderStockInfoVO;

/**
 * @Class Name : ProdController.java
 * @Description : 수불관리 > 본사-매장간 입출고관리 > 상품별 입출고내역
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
@RequestMapping("/iostock/frnchs/prod")
public class ProdFrnchsController {
    private final SessionService sessionService;
    private final ProdFrnchsService prodFrnchsService;

    @Autowired
    public ProdFrnchsController(SessionService sessionService, ProdFrnchsService prodFrnchsService) {
        this.sessionService = sessionService;
        this.prodFrnchsService = prodFrnchsService;
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
    @RequestMapping(value = "/prod/view.sb", method = RequestMethod.GET)
    public String prodFrnchsView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/frnchs/prod/prod";
    }


    /**
     * 본사 상품별 입출고내역 - 상품별 입출고내역 엑셀리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/prod/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdFrnchsList(HttpServletRequest request, HttpServletResponse response,
        Model model, ProdFrnchsVO prodFrnchsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>>
            list = prodFrnchsService.getProdFrnchsList(prodFrnchsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodFrnchsVO);
    }
    
    
    /**
     * 거래처 상품별 입출고내역 - 상품별 입출고내역 엑셀리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodVO
     * @return  String
     * @author  조동훤
     * @since   2020. 04. 21.
     */
    @RequestMapping(value = "/prod/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdFrnchsExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, ProdFrnchsVO prodFrnchsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>>
            list = prodFrnchsService.getProdFrnchsExcelList(prodFrnchsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodFrnchsVO);
    }
    
    
    /**
     * 거래처 발주대비 입고현황 - 상품 입고현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderStockInfoVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/prodInOutstockInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInOutstockInfoList(HttpServletRequest request, HttpServletResponse response,
        Model model, ProdFrnchsVO prodFrnchsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodFrnchsService.getProdInOutstockInfoList(prodFrnchsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodFrnchsVO);
    }
}
