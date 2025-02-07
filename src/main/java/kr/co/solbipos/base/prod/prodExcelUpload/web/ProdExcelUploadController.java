package kr.co.solbipos.base.prod.prodExcelUpload.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.ProdAuthEnvFg;
import kr.co.solbipos.base.prod.prodExcelUpload.service.ProdExcelUploadService;
import kr.co.solbipos.base.prod.prodExcelUpload.service.ProdExcelUploadVO;
import kr.co.solbipos.base.prod.simpleProd.service.SimpleProdService;
import kr.co.solbipos.base.prod.prod.service.enums.PriceEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.vendr.service.VendrVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmService;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.RtnDstbCloseStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

@Controller
@RequestMapping("/base/prod/prodExcelUpload")
public class ProdExcelUploadController {

    private final SessionService sessionService;
    private final ProdExcelUploadService prodExcelUploadService;
    private final SimpleProdService simpleProdService;
    private final ProdService prodService;
    private final CmmEnvUtil cmmEnvUtil;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdExcelUploadController(SessionService sessionService, ProdExcelUploadService prodExcelUploadService, SimpleProdService simpleProdService, ProdService prodService, CmmEnvUtil cmmEnvUtil, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.prodExcelUploadService = prodExcelUploadService;
        this.simpleProdService = simpleProdService;
        this.prodService = prodService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodExcelUpload/list.sb", method = RequestMethod.GET)
    public String prodExcelUploadView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 상품생성설정
        ProdAuthEnvFg prodAuthEnvstVal = ProdAuthEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0042"));

        // 상품코드 채번방식
        ProdNoEnvFg prodNoEnvFg;
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0028"));
        }else{
            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0028"));
        }

        model.addAttribute("prodAuthEnvstVal", prodAuthEnvstVal);
        model.addAttribute("prodNoEnvFg", prodNoEnvFg);


        // 거래처 콤보 조회
        List vendrComboList = simpleProdService.vendrComboList(sessionInfoVO);
        String vendrComboListAll = "";
        if (vendrComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> m = new HashMap<>();
                m.put("name", "선택");
                m.put("value", "");
                list.add(m);
            vendrComboListAll = convertToJson(list);
        } else {
            // 거래처 선택 포함
            vendrComboListAll = cmmCodeUtil.assmblObj(vendrComboList, "name", "value", UseYn.SELECT);
        }
        model.addAttribute("vendrComboList", vendrComboListAll);
//        System.out.println("vendrComboList : "+vendrComboListAll);


        // 상품분류 콤보 조회
        List prodClassComboList = prodExcelUploadService.prodClassComboList(sessionInfoVO);
        String prodClassComboListAll = "";
        if (prodClassComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> m = new HashMap<>();
                m.put("name", "선택");
                m.put("value", "");
                list.add(m);
            prodClassComboListAll = convertToJson(list);
        } else {
            // 상품분류 전체 포함
            prodClassComboListAll = cmmCodeUtil.assmblObj(prodClassComboList, "name", "value", UseYn.SELECT);
        }
        model.addAttribute("prodClassComboList", prodClassComboListAll);
//        System.out.println("prodClassComboList : "+prodClassComboListAll);

        // 내점/배달/포장 가격관리 사용여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));
        }else{
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));
        }

        // (상품관리)브랜드사용여부
//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
//        }else{
//            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1114") , "0"));
//        }

        // 브랜드 리스트 조회(선택 콤보박스용)
        ProdVO prodVO = new ProdVO();
        model.addAttribute("brandList", convertToJson(prodService.getBrandList(prodVO, sessionInfoVO)));

        // 발주 단위 구분 조회
        prodVO.setNmcodeGrpCd("093");
        List poUnitFgDataList = prodService.getPoUnitFgData(prodVO, sessionInfoVO);
        String poUnitFgData = cmmCodeUtil.assmblObj(poUnitFgDataList, "name", "value", UseYn.N);
        model.addAttribute("poUnitFgData", poUnitFgData);

        // 코너 리스트 조회(선택 콤보박스용)
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            List cornerList = prodService.getCornerList(prodVO, sessionInfoVO);
            model.addAttribute("cornerList", cornerList.isEmpty() ? CmmUtil.comboListAll2("기본코너","00") : cmmCodeUtil.assmblObj(cornerList, "name", "value", UseYn.N));
        }else {
            model.addAttribute("cornerList", CmmUtil.comboListAll2("기본코너","00"));
        }

        return "base/prod/prodExcelUpload/prodExcelUpload";
    }

    /**
     * 검증결과 전체 삭제
     *
     * @param prodExcelUploadVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 09. 09.
     */
    @RequestMapping(value = "/prodExcelUpload/getProdExcelUploadCheckDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdExcelUploadCheckDeleteAll(@RequestBody ProdExcelUploadVO prodExcelUploadVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodExcelUploadService.getProdExcelUploadCheckDeleteAll(prodExcelUploadVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 검증결과 조회
     *
     * @param prodExcelUploadVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 09. 09.
     */
    @RequestMapping(value = "/prodExcelUpload/getProdExcelUploadCheckList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdExcelUploadCheckList(ProdExcelUploadVO prodExcelUploadVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodExcelUploadService.getProdExcelUploadCheckList(prodExcelUploadVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodExcelUploadVO);
    }

    /**
     * 업로드시 임시테이블 저장
     *
     * @param prodExcelUploadVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 10. 14.
     */
    @RequestMapping(value = "/prodExcelUpload/getProdExcelUploadCheckSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdExcelUploadCheckSave(@RequestBody ProdExcelUploadVO[] prodExcelUploadVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodExcelUploadService.getProdExcelUploadCheckSave(prodExcelUploadVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 검증결과 저장
     *
     * @param prodExcelUploadVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 10. 14.
     */
    @RequestMapping(value = "/prodExcelUpload/getProdExcelUploadCheckSaveAdd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdExcelUploadCheckSaveAdd(@RequestBody ProdExcelUploadVO[] prodExcelUploadVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodExcelUploadService.getProdExcelUploadCheckSaveAdd(prodExcelUploadVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 검증결과 삭제
     *
     * @param prodExcelUploadVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 10. 14.
     */
    @RequestMapping(value = "/prodExcelUpload/getProdExcelUploadCheckDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdExcelUploadCheckDelete(@RequestBody ProdExcelUploadVO[] prodExcelUploadVOs, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodExcelUploadService.getProdExcelUploadCheckDelete(prodExcelUploadVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 기초 마스터 체크
     *
     * @param prodExcelUploadVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.03.23
     */
    @RequestMapping(value = "/prodExcelUpload/getMasterChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMasterChk(ProdExcelUploadVO prodExcelUploadVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = prodExcelUploadService.getMasterChk(prodExcelUploadVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 기초 마스터 등록 - 상품분류
     *
     * @param prodExcelUploadVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.03.23
     */
    @RequestMapping(value = "/prodExcelUpload/getProdClassCdInsertList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassCdInsertList(ProdExcelUploadVO prodExcelUploadVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result  = prodExcelUploadService.getProdClassCdInsertList(prodExcelUploadVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodExcelUploadVO);
    }

    /**
     * 기초 마스터 등록 - 상품분류 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   productClassVOs
     * @return  String
     * @author  권지현
     * @since   2022.03.23
     */
    @RequestMapping(value = "/prodExcelUpload/prodClassCdSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRtnDstbCloseStoreDtl(HttpServletRequest request, HttpServletResponse response,
                                           Model model, @RequestBody ProductClassVO[] productClassVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodExcelUploadService.prodClassCdSave(productClassVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 기초 마스터 등록 - 거래처
     *
     * @param prodExcelUploadVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.03.23
     */
    @RequestMapping(value = "/prodExcelUpload/getVendrCdInsertList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrCdInsertList(ProdExcelUploadVO prodExcelUploadVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result  = prodExcelUploadService.getVendrCdInsertList(prodExcelUploadVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodExcelUploadVO);
    }

    /**
     * 기초 마스터 등록 - 거래처 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   vendrVOs
     * @return  String
     * @author  권지현
     * @since   2022.03.23
     */
    @RequestMapping(value = "/prodExcelUpload/vendrCdSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result prodClassCdSave(HttpServletRequest request, HttpServletResponse response,
                                           Model model, @RequestBody VendrVO[] vendrVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodExcelUploadService.vendrCdSave(vendrVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

}