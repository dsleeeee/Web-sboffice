package kr.co.solbipos.base.prod.prod.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.PriceEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdEnvFg;
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
        ProdEnvFg prodEnvstVal = ProdEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0020"));
        // 판매가 본사 통제여부
        PriceEnvFg priceEnvstVal = PriceEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

        // 상품코드 채번방식
        ProdNoEnvFg prodNoEnvFg;
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0028"));
        }else{
            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0028"));
        }

        model.addAttribute("prodEnvstVal", prodEnvstVal);
        model.addAttribute("priceEnvstVal", priceEnvstVal);
        model.addAttribute("prodNoEnvFg", prodNoEnvFg);

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
}
