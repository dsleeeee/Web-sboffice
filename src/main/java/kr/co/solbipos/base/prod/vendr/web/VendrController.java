package kr.co.solbipos.base.prod.vendr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.vendr.service.VendrService;
import kr.co.solbipos.base.prod.vendr.service.VendrVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : VendrController.java
 * @Description : 기초관리 - 상품관리 - 거래처 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  노해민      최초생성
 *
 * @author NHN한국사이버결제 KCP 노해민
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/vendr")
public class VendrController {

    private final String RESULT_URI = "base/prod/vendr";

    private final VendrService vendrService;
    private final SessionService sessionService;
    private final CmmEnvService cmmEnvService;

    /** Constructor Injection */
    @Autowired
    public VendrController(VendrService vendrService, SessionService sessionService, CmmEnvService cmmEnvService) {
        this.vendrService = vendrService;
        this.sessionService = sessionService;
        this.cmmEnvService = cmmEnvService;
    }

    /**
     * 거래처 조회 페이지 이동
     *
     * @param request HttpServletRequest
     * @param response HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/vendr//list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model)
    {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 본사 환경설정 1242(거래처출고구분) 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1242");
        model.addAttribute("envst1242", CmmUtil.nvl(cmmEnvService.getHqEnvst(hqEnvstVO), "0"));

        // 거래처구분
        model.addAttribute("urlVendorFg", "0");

        return RESULT_URI + "/vendr";
    }

    /**
     * 거래처 조회 페이지 이동
     *
     * @param request HttpServletRequest
     * @param response HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/vendr/{urlVendorFg}/list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String urlVendorFg)
    {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 본사 환경설정 1242(거래처출고구분) 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1242");
        model.addAttribute("envst1242", CmmUtil.nvl(cmmEnvService.getHqEnvst(hqEnvstVO), "0"));

        // 메뉴 분리해서 사용하려고 추가
        // 거래처구분
        // 0: [기초관리] - [상품관리] - [거래처관리]
        // 2: [국민대] - [매출처관리] - [매출처등록]
        model.addAttribute("urlVendorFg", urlVendorFg);

        return RESULT_URI + "/vendr";
    }

    /**
     * 거래처 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/vendr/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(VendrVO vendrVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> result =  vendrService.list(vendrVO, sessionInfoVO);

        return returnListJson(Status.OK, result, vendrVO);
    }

    /**
     * 거래처 등록 - 거래처 저장
     *
     * @param request
     * @param response
     * @param vendrVO
     * @param model
     * @return Result
     * @author 신유나
     * @since 2018. 08. 29
     */
    @RequestMapping(value = "/regist/regist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody VendrVO vendrVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int result = vendrService.save(vendrVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 거래처 상세조회
     *
     * @param   vendrVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  신유나
     * @since   2018. 09. 02
     */
    @RequestMapping(value = "/regist/view.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dtlInfo(VendrVO vendrVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        DefaultMap<String> result = vendrService.dtlInfo(vendrVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 본사 수정
     *
     * @param   vendrVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  신유나
     * @since   2018. 09. 03.
     */
    @RequestMapping(value = "/regist/modify.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result modify(@RequestBody VendrVO vendrVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = vendrService.modify(vendrVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 취급상품 조회 ( 취급상품, 미취급상품 )
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/trtMnt/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result listTrtMnt(VendrVO vendrVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        //Map<String, Object> resultMap = new HashMap<String, Object>();
        List<DefaultMap<String>> dateSelList;

        /** 취급/미취급 상품 리스트 */
        if(vendrVO.getTrtmntYn() != null && "Y".equals(vendrVO.getTrtmntYn())){
            dateSelList = vendrService.vendrProdList(vendrVO, sessionInfoVO);
        }else{
            dateSelList = vendrService.prodList(vendrVO, sessionInfoVO);
        }

        return returnListJson(Status.OK, dateSelList, vendrVO);

        //resultMap.put("dateSelList1", dateSelList1);
        //resultMap.put("dateSelList2", dateSelList2);

        //return returnJson(Status.OK, dateSelList1, vendrVO);
    }

    /**
     * 미취급상품 수정
     *
     * @param   vendrVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  신유나
     * @since   2018. 09. 03.
     */
    @RequestMapping(value = "/trtMnt/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyProd(@RequestBody VendrVO[] vendrVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = vendrService.modifyProd(vendrVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
    * 취급상품 삭제
    *
    * @param   vendrVO
    * @param   request
    * @param   response
    * @param   model
    * @return  Result
    * @author  신유나
    * @since   2018. 09. 07.
    */
   @RequestMapping(value = "/trtMnt/delete.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result deleteProd(@RequestBody VendrVO[] vendrVO, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

       int cnt = vendrService.deleteProd(vendrVO, sessionInfoVO);

       return returnJson(Status.OK, cnt);
   }
}
