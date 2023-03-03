package kr.co.solbipos.base.price.salePriceResve.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.salePriceResve.service.SalePriceResveService;
import kr.co.solbipos.base.price.salePriceResve.service.SalePriceResveVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : SalePriceResveController.java
 * @Description : 기초관리 - 가격관리 - 가격예약
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.05  이다솜       최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.04.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/salePriceResve")
public class SalePriceResveController {

    private final SessionService sessionService;
    private final SalePriceResveService salePriceResveService;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public SalePriceResveController(SessionService sessionService, SalePriceResveService salePriceResveService, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.salePriceResveService = salePriceResveService;
        this.dayProdService = dayProdService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 가격예약(본사판매가) 화면 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2022.04.05
     */
    @RequestMapping(value = "/hqSalePriceResve/view.sb", method = RequestMethod.GET)
    public String hqSalePriceResveView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));
            // 매장판매가관리본사강제수정
            model.addAttribute("coercionFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1113"), "0"));

        }else{
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));
            model.addAttribute("coercionFg", "0");
        }

        // 내일날짜
        Calendar cal = Calendar.getInstance();
        cal.add(cal.DATE, +1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(cal.getTime());
        model.addAttribute("tomorrowDate", dateStr);

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        /** 맘스터치 */
        // [1250 맘스터치] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
            System.out.println("momsEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
            System.out.println("momsEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
        }
        /** //맘스터치 */

        return "base/price/salePriceResve/hqSalePriceResveTab";
    }

    /**
     * 가격예약(본사판매가) 리스트 조회
     * @param salePriceResveVO
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.05
     */
    @RequestMapping(value = "/hqSalePriceResve/getHqSalePriceResveList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSalePriceResveList(SalePriceResveVO salePriceResveVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceResveService.getHqSalePriceResveList(salePriceResveVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceResveVO);
    }

    /**
     * 가격예약(본사판매가) 추가
     * @param salePriceResveVOs
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.05
     */
    @RequestMapping(value = "/hqSalePriceResve/saveHqSalePriceResve.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqSalePriceResve(@RequestBody SalePriceResveVO[] salePriceResveVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceResveService.saveHqSalePriceResve(salePriceResveVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 가격예약(본사판매가) 수정
     * @param salePriceResveVOs
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.05
     */
    @RequestMapping(value = "/hqSalePriceResve/modHqSalePriceResve.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result modHqSalePriceResve(@RequestBody SalePriceResveVO[] salePriceResveVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceResveService.modHqSalePriceResve(salePriceResveVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 가격예약(본사판매가) 상품가격정보 조회
     * @param salePriceResveVO
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.05
     */
    @RequestMapping(value = "/hqSalePriceResve/getHqSalePriceInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSalePriceInfo(SalePriceResveVO salePriceResveVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceResveService.getHqSalePriceInfo(salePriceResveVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceResveVO);
    }

    /**
     * 가격예약(매장판매가) 화면 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2022.04.12
     */
    @RequestMapping(value = "/storeSalePriceResve/view.sb", method = RequestMethod.GET)
    public String storeSalePriceResveView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));
            // 매장판매가관리본사강제수정
            model.addAttribute("coercionFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1113"), "0"));

        } else {
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044"), "0"));
            model.addAttribute("coercionFg", "0");
        }

        // 내일날짜
        Calendar cal = Calendar.getInstance();
        cal.add(cal.DATE, +1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(cal.getTime());
        model.addAttribute("tomorrowDate", dateStr);

        return "base/price/salePriceResve/storeSalePriceResve";
    }

    /**
     * 가격예약(매장판매가) [상품별 판매가관리] 리스트 조회
     * @param salePriceResveVO
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.13
     */
    @RequestMapping(value = "/storeSalePriceResve/getStoreProdSalePriceResveList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreProdSalePriceResveList(SalePriceResveVO salePriceResveVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceResveService.getStoreProdSalePriceResveList(salePriceResveVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceResveVO);
    }

    /**
     * 가격예약(매장판매가) [매장별 판매가관리] 리스트 조회
     * @param salePriceResveVO
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.15
     */
    @RequestMapping(value = "/storeSalePriceResve/getStoreStoreSalePriceResveList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreStoreSalePriceResveList(SalePriceResveVO salePriceResveVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceResveService.getStoreStoreSalePriceResveList(salePriceResveVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceResveVO);
    }

    /**
     * 가격예약(매장판매가) 추가
     * @param salePriceResveVOs
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.13
     */
    @RequestMapping(value = "/storeSalePriceResve/saveStoreProdSalePriceResve.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreProdSalePriceResve(@RequestBody SalePriceResveVO[] salePriceResveVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceResveService.saveStoreProdSalePriceResve(salePriceResveVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 가격예약(매장판매가) 수정
     * @param salePriceResveVOs
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.13
     */
    @RequestMapping(value = "/storeSalePriceResve/modStoreProdSalePriceResve.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result modStoreProdSalePriceResve(@RequestBody SalePriceResveVO[] salePriceResveVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceResveService.modStoreProdSalePriceResve(salePriceResveVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 가격예약(매장판매가) 상품가격정보 조회
     * @param salePriceResveVO
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.13
     */
    @RequestMapping(value = "/storeSalePriceResve/getStoreSalePriceInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSalePriceInfo(SalePriceResveVO salePriceResveVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceResveService.getStoreSalePriceInfo(salePriceResveVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceResveVO);
    }

    /**
     * 가격예약(판매가관리) 화면 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2022.04.25
     */
    @RequestMapping(value = "/salePriceResve/view.sb", method = RequestMethod.GET)
    public String salePriceResveView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 내점/배달/포장 가격관리 사용여부
        model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));

        // 본사통제구분-판매가
        model.addAttribute("salePriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0045") , "1"));

        // 내일날짜
        Calendar cal = Calendar.getInstance();
        cal.add(cal.DATE, +1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(cal.getTime());
        model.addAttribute("tomorrowDate", dateStr);

        return "base/price/salePriceResve/salePriceResve";
    }

    /**
     * 가격예약(판매가관리) 리스트 조회
     * @param salePriceResveVO
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.25
     */
    @RequestMapping(value = "/salePriceResve/getSalePriceResveList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceResveList(SalePriceResveVO salePriceResveVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceResveService.getSalePriceResveList(salePriceResveVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceResveVO);
    }

    /**
     * 가격예약(판매가관리) 추가
     * @param salePriceResveVOs
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.25
     */
    @RequestMapping(value = "/salePriceResve/saveSalePriceResve.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSalePriceResve(@RequestBody SalePriceResveVO[] salePriceResveVOs, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceResveService.saveSalePriceResve(salePriceResveVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 가격예약(판매가관리) 수정
     * @param salePriceResveVOs
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.25
     */
    @RequestMapping(value = "/salePriceResve/modSalePriceResve.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result modSalePriceResve(@RequestBody SalePriceResveVO[] salePriceResveVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceResveService.modSalePriceResve(salePriceResveVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 가격예약(판매가관리) 상품가격정보 조회
     * @param salePriceResveVO
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.25
     */
    @RequestMapping(value = "/salePriceResve/getSalePriceInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceInfo(SalePriceResveVO salePriceResveVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceResveService.getSalePriceInfo(salePriceResveVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceResveVO);
    }

    /**
     * 가격예약(본사판매가) 엑셀업로드 탭 - 판매가 저장
     *
     * @param salePriceResveVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 02. 21.
     */
    @RequestMapping(value = "/salePriceResveExcelUpload/getHqSalePriceResveExcelUploadSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSalePriceResveExcelUploadSave(@RequestBody SalePriceResveVO[] salePriceResveVOs, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceResveService.getHqSalePriceResveExcelUploadSave(salePriceResveVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
