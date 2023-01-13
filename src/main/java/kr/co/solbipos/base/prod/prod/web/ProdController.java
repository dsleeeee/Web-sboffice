package kr.co.solbipos.base.prod.prod.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.ProdAuthEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : ProdController.java
 * @Description : 기초관리 - 상품관리 - 상품조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  장혁수       최초생성
 * @ 2018.10.19  노현수       생성자 주입, 상품조회 관련 변경
 *
 * @author NHN한국사이버결제 KCP 장혁수
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/prod/prod")
public class ProdController {

    private final SessionService sessionService;
    private final ProdService prodService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public ProdController(SessionService sessionService, ProdService prodService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.prodService = prodService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 상품조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 상품등록 본사 통제여부
//        ProdEnvFg prodEnvstVal = ProdEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0020"));
        // 판매가 본사 통제여부
//        PriceEnvFg priceEnvstVal = PriceEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

        // 상품생성설정
        ProdAuthEnvFg prodAuthEnvstVal = ProdAuthEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0042"));

        // 상품코드 채번방식
        ProdNoEnvFg prodNoEnvFg;
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0028"));
        }else{
            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0028"));
        }

        // 내점/배달/포장 가격관리 사용여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));
        }else{
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));
        }

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0043"),"0").equals("0")){ // 0043 본사신규상품 매장생성기준
                model.addAttribute("kitchenprintLink", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1110") , "0")); // 1110상품생성시주방프린터연결여부
            }
        } else {
            model.addAttribute("kitchenprintLink", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1110") , "0")); // 1110상품생성시주방프린터연결여부
        }

        // (상품관리)브랜드사용여부
//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
//        }else{
//            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1114") , "0"));
//        }

        // 매장상품제한구분 사용여부(매장 세트구성상품 등록시 사용, 매장에서 사용하지만 본사환경설정값으로 여부파악)
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            model.addAttribute("storeProdUseFg", "0");
        } else {
            model.addAttribute("storeProdUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1100"), "0"));
        }

        // 브랜드 리스트 조회(선택 콤보박스용)
        ProdVO prodVO = new ProdVO();
        model.addAttribute("brandList", convertToJson(prodService.getBrandList(prodVO, sessionInfoVO)));

//        model.addAttribute("prodEnvstVal", prodEnvstVal);
//        model.addAttribute("priceEnvstVal", priceEnvstVal);
        model.addAttribute("prodAuthEnvstVal", prodAuthEnvstVal);
        model.addAttribute("prodNoEnvFg", prodNoEnvFg);


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


        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
            model.addAttribute("posLoginReconnect", request.getParameter("posLoginReconnect"));
        }

        return "base/prod/prod/prod";
    }

    /**
     * 상품조회
     *
     * @param prodVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(ProdVO prodVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodService.getProdList(prodVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodVO);
    }

    /**
     * 상품명조회
     *
     * @param prodVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/getProdNmList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdNmList(ProdVO prodVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodService.getProdNmList(prodVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodVO);
    }

    /**
     * 상품전체조회(엑셀다운로드용)
     *
     * @param prodVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/getProdExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdExcelList(ProdVO prodVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodService.getProdExcelList(prodVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodVO);
    }

    /**
     * 상품상세조회
     * @param prodVO ProdVO
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/detail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdDetail(ProdVO prodVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return returnJson(Status.OK, "list", prodService.getProdDetail(prodVO, sessionInfoVO));
    }

    /**
     * 사이드메뉴 체크(매장구성세트상품화면에서 신규상품을 등록할 때 1100옵션이 BBQ전용이면 사이드상품만 입력가능)
     * @param prodVO ProdVO
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/chkSide.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkSide(ProdVO prodVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 매장상품제한구분 환경변수 값(환경변수 1100 사용)
        String sideEnvstVal;
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) { // 본사는 해당옵션의 제약X
            sideEnvstVal = "0";
        } else {
            sideEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1100"));
        }

        if(sideEnvstVal.equals("1")){
            if(!prodVO.getSideProdYn().equals("Y")){
                return returnJson(Status.FAIL);
            } else if(StringUtil.getOrBlank(prodVO.getSdselGrpCd()).equals("")){
                return returnJson(Status.FAIL);
            }
        }
        return returnJson(Status.OK);
    }

    /**
     * 바코드 중복 체크
     * @param prodVO ProdVO
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/chkBarCd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkBarCd(ProdVO prodVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = prodService.chkBarCd(prodVO, sessionInfoVO);
        if(result.size() == 0){
            return returnJson(Status.OK);
        } else {
            return returnJson(Status.FAIL, result);
        }
    }

    /**
     * 상품정보 저장
     * @param prodVO ProdVO
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProductInfo(@RequestBody ProdVO prodVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

//        int result = prodService.saveProductInfo(prodVO, sessionInfoVO);
//        long result = prodService.saveProductInfo(prodVO, sessionInfoVO);
        String result = prodService.saveProductInfo(prodVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /***
     * 상품 적용매장/미적용 매장 조회
     * @param prodVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRegStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRegStoreList(ProdVO prodVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> storeList = prodService.getStoreList(prodVO, sessionInfoVO);

        return returnListJson(Status.OK, storeList, prodVO);
    }

    /***
     * 상품적용매장 등록
     * @param prodVOs
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertProdStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result insertProdStore(@RequestBody ProdVO[] prodVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodService.insertProdStore(prodVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /***
     * 상품적용매장 삭제
     * @param prodVOs
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteProdStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteProdStore(@RequestBody ProdVO[] prodVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodService.deleteProdStore(prodVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }


    /***
     * 등록매장 판매가 변경
     * @param prodVOs
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateStoreSaleUprc.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateStoreSaleUprc(@RequestBody ProdVO[] prodVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodService.updateStoreSaleUprc(prodVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 상품코드 중복체크
     * @param prodVO
     * @author 이다솜
     * @since 2019.12.06
     */
    @ResponseBody
    @RequestMapping(value = "/getProdCdCnt.sb", method = RequestMethod.POST)
    public Result getProdCdCnt(ProdVO prodVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int prodCdCnt= prodService.getProdCdCnt(prodVO, sessionInfoVO);

        return returnJson(Status.OK, prodCdCnt);
    }

    /***
     * 매장 적용/미적용 상품 조회
     * @param prodVO
     * @param request
     * @author 이다솜
     * @since 2020.02.13
     * @return
     */
    @RequestMapping(value = "/getStoreProdBatchList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreProdBatchList(ProdVO prodVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> storeList = prodService.getStoreProdBatchList(prodVO, sessionInfoVO);

        return returnListJson(Status.OK, storeList, prodVO);
    }

    /***
     * 매장 적용상품 등록
     * @param prodVOs
     * @param request
     * @author 이다솜
     * @since 2020.02.13
     * @return
     */
    @RequestMapping(value = "/insertStoreProdBatch.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result insertStoreProdBatch(@RequestBody ProdVO[] prodVOs, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodService.insertStoreProdBatch(prodVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 상품 신규등록,수정 팝업 - 상품 이미지 저장
     *
     * @return  Object
     * @author  김설아
     * @since   2020. 08. 04.
     */
    @RequestMapping(value = "/getProdImageFileSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdImageFileSave(MultipartHttpServletRequest request) {

//        System.out.println("test1111");
        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        if(prodService.getProdImageFileSave(request, sessionInfo)) {
            return returnJson(Status.OK);
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 미적용 상품 거래처 조회 팝업 - 조회
     *
     * @param prodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 12. 16.
     */
    @RequestMapping(value = "/getSearchNoProdVendrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchNoProdVendrList(ProdVO prodVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = prodService.getSearchNoProdVendrList(prodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodVO);
    }

    /**
     * 브랜드 콤보박스 리스트 조회
     *
     * @param prodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2020. 12. 16.
     */
    @RequestMapping(value = "/getBrandComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBrandComboList(ProdVO prodVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = prodService.getBrandComboList(prodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodVO);
    }

    /**
     * 사이드메뉴관리의 선택상품에 등록된 상품인지 조회
     *
     * @param prodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 07. 08.
     */
    @RequestMapping(value = "/getSideProdChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideProdChk(ProdVO prodVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodService.getSideProdChk(prodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodVO);
    }

    /**
     * 프린터 조회
     *
     * @param prodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.07.15
     */
    @RequestMapping(value = "/getKitchenprintList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKitchenprintList(ProdVO prodVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = prodService.getKitchenprintList(prodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodVO);
    }


    /**
     * 프린터 연결
     *
     * @param prodVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.07.16
     */
    @RequestMapping(value = "/kitchenprintLink.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result kitchenprintLink(@RequestBody ProdVO[] prodVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodService.kitchenprintLink(prodVOs, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 브랜드 리스트 조회(선택 콤보박스용)
     * @param prodVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBrandList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBrandList(ProdVO prodVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = prodService.getBrandList(prodVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodVO);
    }

    /**
     * 브랜드 리스트 조회(선택 콤보박스용, 선택한 상품에서 현재 사용중인 브랜드 + 사용여부 'Y'인 브랜드)
     * @param prodVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBrandList2.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBrandList2(ProdVO prodVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = prodService.getBrandList2(prodVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodVO);
    }

    /**
     * 세트구성상품 팝업 - 구성내역 리스트 조회
     *
     * @param prodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2021. 08. 21.
     */
    @RequestMapping(value = "/getSetConfigProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSetConfigProdList(ProdVO prodVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = prodService.getSetConfigProdList(prodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodVO);
    }

    /**
     * 세트구성상품 팝업 - 상품 리스트 조회
     *
     * @param prodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2021. 08. 21.
     */
    @RequestMapping(value = "/getSrchSetConfigProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSrchSetConfigProdList(ProdVO prodVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = prodService.getSrchSetConfigProdList(prodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodVO);
    }

    /**
     * 세트구성상품 팝업 - 상품 등록/수정/삭제
     *
     * @param prodVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2021. 08. 23.
     */
    @RequestMapping(value = "/saveSetConfigProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSetConfigProd(@RequestBody ProdVO[] prodVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodService.saveSetConfigProd(prodVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 선택메뉴 조회 팝업
     * @param prodVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 09. 13.
     */
    @RequestMapping(value = "/getSearchSdselGrpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchSdselGrpList(ProdVO prodVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = prodService.getSearchSdselGrpList(prodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodVO);
    }

    /**
     * 선택상품삭제
     *
     * @param prodVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2022. 03. 08.
     */
    @RequestMapping(value = "/selectProdDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectProdDelete(@RequestBody ProdVO[] prodVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodService.selectProdDelete(prodVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 전체상품삭제
     * @param prodVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/allProdDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result allProdDelete(@RequestBody ProdVO prodVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodService.allProdDelete(prodVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * KIOSK 판매시간 시간설정 조회
     * @param prodVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2022. 10. 11.
     */
    @RequestMapping(value = "/getProdSaleTime.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleTime(ProdVO prodVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return returnJson(Status.OK, "list", prodService.getProdSaleTime(prodVO, sessionInfoVO));
    }

    /**
     * 상품옵션그룹 조회 팝업
     * @param prodVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2022. 12. 19.
     */
    @RequestMapping(value = "/getSearchOptionGrpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchOptionGrpList(ProdVO prodVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = prodService.getSearchOptionGrpList(prodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodVO);
    }
}
