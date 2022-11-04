package kr.co.solbipos.sale.prod.prodSaleRate2.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import kr.co.solbipos.sale.prod.prodSaleRate2.service.ProdSaleRate2Service;
import kr.co.solbipos.sale.prod.prodSaleRate2.service.ProdSaleRate2VO;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelService;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;


/**
 * @Class Name : ProdSaleRate2Controller.java
 * @Description : 맘스터치 > 상품매출분석 > 상품 판매 비율
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.05  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/prod/prodSaleRate2")
public class ProdSaleRate2Controller {

    private final SessionService sessionService;
    private final ProdSaleRate2Service prodSaleRate2Service;
    private final StoreTypeService storeTypeService;
    private final StoreChannelService storeChannelService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleRate2Controller(SessionService sessionService, ProdSaleRate2Service prodSaleRate2Service, StoreTypeService storeTypeService, StoreChannelService storeChannelService) {
        this.sessionService = sessionService;
        this.prodSaleRate2Service = prodSaleRate2Service;
        this.storeTypeService = storeTypeService;
        this.storeChannelService = storeChannelService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodSaleRate2/list.sb", method = RequestMethod.GET)
    public String prodRankMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        StoreChannelVO storeChannelVO = new StoreChannelVO();

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("hqBrandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        // 주문채널 구분자 조회
        List<DefaultMap<String>> dlvrInFgColList = storeChannelService.getDlvrInFgColList(storeChannelVO, sessionInfoVO);

        // 주문채널 코드를 , 로 연결하는 문자열 생성
        String dlvrInFgCol = "";
        for(int i=0; i < dlvrInFgColList.size(); i++) {
            dlvrInFgCol += (dlvrInFgCol.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFg");
        }
        model.addAttribute("dlvrInFgColList", dlvrInFgColList);
        model.addAttribute("dlvrInFgCol", dlvrInFgCol);

        return "sale/prod/prodSaleRate2/prodSaleRate2";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodSaleRate2VO
     * @return  String
     * @author  권지현
     * @since   2022.10.05
     */
    @RequestMapping(value = "/prodSaleRate2/getProdSaleRate2List.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleRate2List(HttpServletRequest request, HttpServletResponse response, Model model, ProdSaleRate2VO prodSaleRate2VO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = prodSaleRate2Service.getProdSaleRate2List(prodSaleRate2VO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodSaleRate2VO);
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodSaleRate2VO
     * @return  String
     * @author  권지현
     * @since   2022.10.05
     */
    @RequestMapping(value = "/prodSaleRate2/getProdSaleRate2ExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleRate2ExcelList(HttpServletRequest request, HttpServletResponse response, Model model, ProdSaleRate2VO prodSaleRate2VO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = prodSaleRate2Service.getProdSaleRate2ExcelList(prodSaleRate2VO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodSaleRate2VO);
    }

}