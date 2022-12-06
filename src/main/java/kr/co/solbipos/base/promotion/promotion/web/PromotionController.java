package kr.co.solbipos.base.promotion.promotion.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.promotion.promotion.service.PromotionService;
import kr.co.solbipos.base.promotion.promotion.service.PromotionVO;
import kr.co.solbipos.base.store.media.service.MediaVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmService;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @Class Name : PromotionController.java
 * @Description : 기초관리 - 프로모션관리 - 프로모션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .04. 13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/promotion/promotion")
public class PromotionController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final PromotionService promotionService;
    private final StoreTypeService storeTypeService;
    private final IostockCmmService iostockCmmService;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public PromotionController(SessionService sessionService, PromotionService promotionService, StoreTypeService storeTypeService, IostockCmmService iostockCmmService, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.promotionService = promotionService;
        this.storeTypeService = storeTypeService;
        this.iostockCmmService = iostockCmmService;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 프로모션관리 화면이동
     * @param model
     * @author 이다솜
     * @since 2021.04.13
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 프로모션 특이사항 환경변수 값(본사의 환경변수 1095 사용)
        // 본사,매장 모두 본사 환경설정값을 따른다.
        String promotionEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1095"));
        model.addAttribute("promotionEnvstVal", promotionEnvstVal);

        // 진행중인 프로모션 수정여부 환경변수 값(본사 또는 매장의 환경변수 1097 사용)
        String modPromotionEnvstVal = "";
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            modPromotionEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1097"));
        }else{
            modPromotionEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1097"));
        }

        model.addAttribute("modPromotionEnvstVal", modPromotionEnvstVal);

        // [1250 맘스터치] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
        }

        // [1253 매장프로모션생성] 환경설정값 조회
        model.addAttribute("storePromoRegYnVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1253"), "0"));

        // 맘스터치 적용매장추가 조회조건 콤보박스 데이터
        IostockCmmVO iostockCmmVO = new IostockCmmVO();
        model.addAttribute("momsStoreHqBrand", convertToJson(iostockCmmService.selectBrandMomsList(iostockCmmVO, sessionInfoVO)));         // 매장브랜드
        model.addAttribute("momsBranch", convertToJson(iostockCmmService.selectBranchMomsList(iostockCmmVO, sessionInfoVO)));               // 지사

        iostockCmmVO.setNmcodeGrpCd("151");
        model.addAttribute("momsTeam", convertToJson(iostockCmmService.selectHqNmcodeMomsList(iostockCmmVO, sessionInfoVO)));               // 팀별

        iostockCmmVO.setNmcodeGrpCd("152");
        model.addAttribute("momsAcShop", convertToJson(iostockCmmService.selectHqNmcodeMomsList(iostockCmmVO, sessionInfoVO)));             // AC점포별

        iostockCmmVO.setNmcodeGrpCd("153");
        model.addAttribute("momsAreaFg", convertToJson(iostockCmmService.selectHqNmcodeMomsList(iostockCmmVO, sessionInfoVO)));             // 지역구분

        iostockCmmVO.setNmcodeGrpCd("154");
        model.addAttribute("momsCommercial", convertToJson(iostockCmmService.selectHqNmcodeMomsList(iostockCmmVO, sessionInfoVO)));         // 상권

        iostockCmmVO.setNmcodeGrpCd("155");
        model.addAttribute("momsShopType", convertToJson(iostockCmmService.selectHqNmcodeMomsList(iostockCmmVO, sessionInfoVO)));           // 점포유형

        iostockCmmVO.setNmcodeGrpCd("156");
        model.addAttribute("momsStoreManageType", convertToJson(iostockCmmService.selectHqNmcodeMomsList(iostockCmmVO, sessionInfoVO)));    // 매장관리타입


        StoreTypeVO storeTypeVO = new StoreTypeVO();

        // 브랜드조회(콤보박스용)
        model.addAttribute("brandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        // 매장타입조회(콤보박스용)
        model.addAttribute("storeTypeList", convertToJson(storeTypeService.getStoreTypeCombo(storeTypeVO, sessionInfoVO)));

        // 메뉴그룹조회(콤보박스용)
        model.addAttribute("storeGroupList", convertToJson(storeTypeService.getStoreGroupCombo(storeTypeVO, sessionInfoVO)));

        // 프로모션 종류 조회(콤보박스용)
        model.addAttribute("promotionTypeList", convertToJson(promotionService.getPromotionTypeList()));

        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
         model.addAttribute("posLoginReconnect", request.getParameter("posLoginReconnect"));
        }

        return "base/promotion/promotion/promotion";
    }

    /**
     * 프로모션관리 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.13
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result gerPromotionList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getPromotionList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 등록/수정
     * @param promotionVO
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.13
     * @return
     */
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotion(@RequestBody PromotionVO promotionVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = promotionService.savePromotion(promotionVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 프로모션 상세 조회
     * @param promotionVO
     * @param request
     * @author 이다솜
     * @since 2021.04.21
     * @return
     */
    @RequestMapping(value = "/detail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionDetail(PromotionVO promotionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = promotionService.getPromotionDetail(promotionVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 프로모션 적용상품 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.22
     * @return
     */
    @RequestMapping(value = "/getPromotionProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionProdList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getPromotionProdList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 적용상품, 혜택상품 선택팝업 상품리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.22
     * @return
     */
    @RequestMapping(value = "/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getProdList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 적용상품 선택팝업 분류리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.23
     * @return
     */
    @RequestMapping(value = "/getClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getClassList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getClassList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 적용상품 선택팝업 상품추가/수정/삭제
     *
     * @param promotionVOs
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.22
     * @return
     */
    @RequestMapping(value = "/savePromotionProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotionProd(@RequestBody PromotionVO[] promotionVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = promotionService.savePromotionProd(promotionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 프로모션 적용매장 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.23
     * @return
     */
    @RequestMapping(value = "/getPromotionStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionStoreList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getPromotionStoreList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 적용매장 선택팝업 매장리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.23
     * @return
     */
    @RequestMapping(value = "/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getStoreList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 적용매장 선택팝업 전매장적용
     *
     * @param promotionVO
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.23
     * @return
     */
    @RequestMapping(value = "/insertPromotionStoreAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result insertPromotionStoreAll(@RequestBody PromotionVO promotionVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result= promotionService.insertPromotionStoreAll(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 프로모션 적용매장 선택팝업 매장추가/삭제
     *
     * @param promotionVOs
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.23
     * @return
     */
    @RequestMapping(value = "/savePromotionStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotionStore(@RequestBody PromotionVO[] promotionVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = promotionService.savePromotionStore(promotionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 프로모션 혜택상품 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.26
     * @return
     */
    @RequestMapping(value = "/getPromotionPresentList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionPresentList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getPromotionPresentList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 혜택상품 선택팝업 상품리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.22
     * @return
     */
    @RequestMapping(value = "/getPresentProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPresentProdList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getPresentProdList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 혜택상품 선택팝업 상품추가/수정/삭제
     *
     * @param promotionVOs
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.26
     * @return
     */
    @RequestMapping(value = "/savePromotionPresent.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotionPresent(@RequestBody PromotionVO[] promotionVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = promotionService.savePromotionPresent(promotionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 프로모션 종류 변경에 따른 필수값 저장
     * @param promotionVO
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.12.10
     * @return
     */
    @RequestMapping(value = "/savePromotionDefaultSet.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotionDefaultSet(@RequestBody PromotionVO promotionVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = promotionService.savePromotionDefaultSet(promotionVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 프로모션 적용매장 전체삭제
     * @param promotionVO
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2022.11.01
     * @return
     */
    @RequestMapping(value = "/deletePromotionStoreAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deletePromotionStoreAll(@RequestBody PromotionVO promotionVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = promotionService.deletePromotionStoreAll(promotionVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 프로모션 적용매장 매장 엑셀업로드
     * @param promotionVOs
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2022.11.01
     * @return
     */
    @RequestMapping(value = "/excelUploadPromotionStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUploadPromotionStore(@RequestBody PromotionVO[] promotionVOs, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = promotionService.excelUploadPromotionStore(promotionVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 프로모션 키오스크 배너 조회
     * @param mediaVO
     * @param request
     * @author 이다솜
     * @since 2022.11.08
     * @return
     */
    @RequestMapping(value = "/getPromotionBanner.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionBanner(MediaVO mediaVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getPromotionBanner(mediaVO, sessionInfoVO);

        return returnListJson(Status.OK, list, mediaVO);
    }

    /**
     * 프로모션 키오스크 배너 등록
     * @param mediaVO
     * @param request
     * @author 이다솜
     * @since 2022.11.08
     * @return
     */
    @RequestMapping(value = "/savePromotionBanner.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotionBanner(MediaVO mediaVO, MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        String result = promotionService.savePromotionBanner(request, mediaVO, sessionInfo);

        if(result.equals("0")) {
            return returnJson(Status.OK);
        } else if(result.equals("1")) {
            return returnJson(Status.FAIL);
        } else if(result.equals("2")) {
            return returnJson(Status.FAIL, "msg", messageService.get("promotion.fileExtensionChk.msg"));
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 프로모션 키오스크 배너 삭제
     * @param mediaVO
     * @param request
     * @author 이다솜
     * @since 2022.11.08
     * @return
     */
    @RequestMapping(value = "/delPromotionBanner.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result delPromotionBanner(MediaVO mediaVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if(promotionService.delPromotionBanner(mediaVO, sessionInfoVO)) {
            return returnJson(Status.OK);
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 프로모션 키오스크 배너 수정(프로모션관련 정보만 수정)
     * @param mediaVO
     * @param request
     * @author 이다솜
     * @since 2022.11.08
     * @return
     */
    @RequestMapping(value = "/modPromotionBanner.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result modPromotionBanner(@RequestBody MediaVO mediaVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = promotionService.modPromotionBanner(mediaVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 맘스터치 프로모션 적용매장 선택팝업 매장리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.12.02
     * @return
     */
    @RequestMapping(value = "/getMomsStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMomsStoreList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getMomsStoreList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }
}
