package kr.co.solbipos.sale.prod.prodSaleRateMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.prod.prodSaleRateMoms.service.ProdSaleRateMomsService;
import kr.co.solbipos.sale.prod.prodSaleRateMoms.service.ProdSaleRateMomsVO;
import kr.co.solbipos.sale.prod.saleProdRankMoms.service.SaleProdRankMomsVO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : ProdSaleRateMomsController.java
 * @Description : 맘스터치 > 상품매출분석 > 상품판매비율
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.13   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.12.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/prod/prodSaleRateMoms")
public class ProdSaleRateMomsController {

    private final SessionService sessionService;
    private final ProdSaleRateMomsService prodSaleRateMomsService;
    private final DayProdService dayProdService;
    private final StoreChannelService storeChannelService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleRateMomsController(SessionService sessionService, ProdSaleRateMomsService prodSaleRateMomsService, DayProdService dayProdService, StoreChannelService storeChannelService, CmmCodeUtil cmmCodeUtil){
        this.sessionService = sessionService;
        this.prodSaleRateMomsService = prodSaleRateMomsService;
        this.dayProdService = dayProdService;
        this.storeChannelService = storeChannelService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
    * 화면 이동
    *
    * @param request
    * @param response
    * @param model
    * @author  이다솜
    * @since   2022.12.13
    */
   @RequestMapping(value = "/prodSaleRateMoms/view.sb", method = RequestMethod.GET)
   public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

       // 사용자별 브랜드 콤보박스 조회
       DayProdVO dayProdVO = new DayProdVO();
       model.addAttribute("momsHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

       // 사용자별 지사 콤보박스 조회
       List branchCdComboList = dayProdService.getUserBranchComboList(sessionInfoVO);
       model.addAttribute("branchCdComboList", branchCdComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(branchCdComboList, "name", "value", UseYn.N));

       // 사용자별 코드별 공통코드 콤보박스 조회
       // - 팀별
       List momsTeamComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "151");
       model.addAttribute("momsTeamComboList", momsTeamComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.N));

       // - AC점포별
       List momsAcShopComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "152");
       model.addAttribute("momsAcShopComboList", momsAcShopComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(momsAcShopComboList, "name", "value", UseYn.N));

       // - 지역구분
       List momsAreaFgComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "153");
       model.addAttribute("momsAreaFgComboList", momsAreaFgComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.N));

       // - 상권
       List momsCommercialComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "154");
       model.addAttribute("momsCommercialComboList", momsCommercialComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.N));

       // - 점포유형
       List momsShopTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "155");
       model.addAttribute("momsShopTypeComboList", momsShopTypeComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.N));

       // - 매장관리타입
       List momsStoreManageTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "156");
       model.addAttribute("momsStoreManageTypeComboList", momsStoreManageTypeComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(momsStoreManageTypeComboList, "name", "value", UseYn.N));

       // 주문채널 구분자 조회
       StoreChannelVO storeChannelVO = new StoreChannelVO();
       List<DefaultMap<String>> dlvrInFgColList = storeChannelService.getDlvrInFgColList(storeChannelVO, sessionInfoVO);

       // 주문채널 코드를 , 로 연결하는 문자열 생성
       String dlvrInFgCol = "";
       String dlvrInFgColNm = "";
       for(int i=0; i < dlvrInFgColList.size(); i++) {
           dlvrInFgCol += (dlvrInFgCol.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFg");
           dlvrInFgColNm += (dlvrInFgColNm.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFgNm");
       }
       model.addAttribute("dlvrInFgColList", dlvrInFgColList);
       model.addAttribute("dlvrInFgCol", dlvrInFgCol);
       model.addAttribute("dlvrInFgColNm", dlvrInFgColNm);

       return "sale/prod/prodSaleRateMoms/prodSaleRateMoms";
   }

    /**
     * 상품판매비율 조회
     * @param request
     * @param response
     * @param model
     * @param prodSaleRateMomsVO
     * @return
     * @author  이다솜
     * @since   2022.12.13
     */
    @RequestMapping(value = "/prodSaleRateMoms/getProdSaleRateList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleRateList(HttpServletRequest request, HttpServletResponse response, Model model, ProdSaleRateMomsVO prodSaleRateMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodSaleRateMomsService.getProdSaleRateList(prodSaleRateMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodSaleRateMomsVO);
    }

    /**
     * 상품판매비율 조회(엑셀용)
     * @param request
     * @param response
     * @param model
     * @param prodSaleRateMomsVO
     * @return
     * @author  이다솜
     * @since   2022.12.13
     */
    @RequestMapping(value = "/prodSaleRateMoms/getProdSaleRateExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleRateExcelList(HttpServletRequest request, HttpServletResponse response, Model model, ProdSaleRateMomsVO prodSaleRateMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodSaleRateMomsService.getProdSaleRateExcelList(prodSaleRateMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodSaleRateMomsVO);
    }

    /**
     * 빈 콤보박스 셋팅
     * @return
     */
    public String comboListAll(){

        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> m = new HashMap<>();
        m.put("name", "전체");
        m.put("value", "");
        list.add(m);

        return convertToJson(list);
    }
}
