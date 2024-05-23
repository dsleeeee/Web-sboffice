package kr.co.solbipos.base.prod.prodImgBarrierFree.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodImgBarrierFree.service.ProdImgBarrierFreeService;
import kr.co.solbipos.base.prod.prodImgBarrierFree.service.ProdImgBarrierFreeVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : ProdImgBarrierFreeController.java
 * @Description : 기초관리 > 상품관리2 > 베리어프리-이미지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.05.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/prodImgBarrierFree")
public class ProdImgBarrierFreeController {

    private final SessionService sessionService;
    private final ProdImgBarrierFreeService prodImgBarrierFreeService;
    private final DayProdService dayProdService;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdImgBarrierFreeController(SessionService sessionService, ProdImgBarrierFreeService prodImgBarrierFreeService, DayProdService dayProdService, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.prodImgBarrierFreeService = prodImgBarrierFreeService;
        this.dayProdService = dayProdService;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodImgBarrierFree/list.sb", method = RequestMethod.GET)
    public String prodImgBarrierFreeView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
            model.addAttribute("posLoginReconnect", request.getParameter("posLoginReconnect"));
        }

        /** 맘스터치 */
        // [1250 맘스터치] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
        }
        /** //맘스터치 */

        return "base/prod/prodImgBarrierFree/prodImgBarrierFree";
    }

    /**
     * 베리어프리-이미지관리 - 조회
     *
     * @param prodImgBarrierFreeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 17.
     */
    @RequestMapping(value = "/prodImgBarrierFree/getProdImgBarrierFreeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdImgBarrierFreeList(ProdImgBarrierFreeVO prodImgBarrierFreeVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodImgBarrierFreeService.getProdImgBarrierFreeList(prodImgBarrierFreeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodImgBarrierFreeVO);
    }

    /**
     * 베리어프리-이미지관리 - 이미지 조회
     *
     * @param prodImgBarrierFreeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 17.
     */
    @RequestMapping(value = "/prodImgBarrierFree/getProdImgBarrierFreeImageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdImgBarrierFreeImageList(ProdImgBarrierFreeVO prodImgBarrierFreeVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodImgBarrierFreeService.getProdImgBarrierFreeImageList(prodImgBarrierFreeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodImgBarrierFreeVO);
    }

    /**
     * 베리어프리-이미지관리 - 이미지 저장
     *
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 17.
     */
    @RequestMapping(value = "/prodImgBarrierFree/getProdImgBarrierFreeImageSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdImgBarrierFreeImageSave(ProdImgBarrierFreeVO prodImgBarrierFreeVO, MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = prodImgBarrierFreeService.getProdImgBarrierFreeImageSave(request, prodImgBarrierFreeVO, sessionInfoVO);

        if(result.equals("0")) {
            return returnJson(Status.OK);
        } else if(result.equals("1")) {
            return returnJson(Status.FAIL);
        } else if(result.equals("3")) {
            //return returnJson(Status.OK, );
            return returnJson(Status.FAIL, "msg", messageService.get("prodImg.fileExtensionChk.msg"));
        } else{
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 베리어프리-이미지관리 - 이미지 삭제
     *
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 17.
     */
    @RequestMapping(value = "/prodImgBarrierFree/getProdImgBarrierFreeImageDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdImgBarrierFreeImageDelete(ProdImgBarrierFreeVO prodImgBarrierFreeVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if(prodImgBarrierFreeService.getProdImgBarrierFreeImageDelete(prodImgBarrierFreeVO, sessionInfoVO)) {
            return returnJson(Status.OK);
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 베리어프리-이미지관리 매장적용 팝업 - 조회
     *
     * @param prodImgBarrierFreeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 17.
     */
    @RequestMapping(value = "/prodImgBarrierFreeStoreRegist/getProdImgBarrierFreeStoreRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdImgBarrierFreeStoreRegistList(ProdImgBarrierFreeVO prodImgBarrierFreeVO, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodImgBarrierFreeService.getProdImgBarrierFreeStoreRegistList(prodImgBarrierFreeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodImgBarrierFreeVO);
    }

    /**
     * 베리어프리-이미지관리 매장적용 팝업 - 저장
     *
     * @param prodImgBarrierFreeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 17.
     */
    @RequestMapping(value = "/prodImgBarrierFreeStoreRegist/getProdImgBarrierFreeStoreRegistSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdImgBarrierFreeStoreRegistSave(@RequestBody ProdImgBarrierFreeVO[] prodImgBarrierFreeVOs, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = prodImgBarrierFreeService.getProdImgBarrierFreeStoreRegistSave(prodImgBarrierFreeVOs, sessionInfo);

        return returnJson(Status.OK, result);
    }

    /**
     * 베리어프리-이미지관리 이미지복사 팝업 - 저장
     *
     * @param prodImgBarrierFreeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 17.
     */
    @RequestMapping(value = "/prodImgBarrierFreeCopy/getProdImgBarrierFreeCopySave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdImgBarrierFreeCopySave(ProdImgBarrierFreeVO prodImgBarrierFreeVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = prodImgBarrierFreeService.getProdImgBarrierFreeCopySave(prodImgBarrierFreeVO, sessionInfo);

        return returnJson(Status.OK, result);
    }

    /**
     * 베리어프리-이미지관리 이미지전체삭제 팝업 - 전체삭제
     *
     * @param prodImgBarrierFreeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 17.
     */
    @RequestMapping(value = "/prodImgBarrierFreeDelete/getProdImgBarrierFreeDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdImgBarrierFreeDeleteAll(ProdImgBarrierFreeVO prodImgBarrierFreeVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = prodImgBarrierFreeService.getProdImgBarrierFreeDeleteAll(prodImgBarrierFreeVO, sessionInfo);

        return returnJson(Status.OK, result);
    }
}