package kr.co.solbipos.base.prod.prodImg.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodImg.service.ProdImgService;
import kr.co.solbipos.base.prod.prodImg.service.ProdImgVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
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
 * @Class Name : ProdImgController.java
 * @Description : 기초관리 - 상품관리 - 상품이미지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/prodImg")
public class ProdImgController {

    private final SessionService sessionService;
    private final ProdImgService prodImgService;
    private final DayProdService dayProdService;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public ProdImgController(SessionService sessionService, ProdImgService prodImgService, DayProdService dayProdService, MessageService messageService, CmmEnvUtil cmmEnvUtil) {

        this.sessionService = sessionService;
        this.prodImgService = prodImgService;
        this.dayProdService = dayProdService;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 상품이미지관리 메인화면
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2020.08.13
     * @return
     */
    @RequestMapping(value = "/prodImg/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "base/prod/prodImg/prodImgRegistView";
    }

    /**
     * 상품이미지관리 - 상품목록조회
     *
     * @param prodImgVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodImg/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(ProdImgVO prodImgVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodImgService.getProdList(prodImgVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodImgVO);
    }

    /**
     * 상품이미지관리 - 상품이미지조회
     *
     * @param prodImgVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodImg/getProdImg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdImg(ProdImgVO prodImgVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodImgService.getProdImg(prodImgVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodImgVO);
    }

    /**
     * 상품이미지관리 - 상품이미지저장
     *
     * @param prodImgVO HttpServletRequest
     * @param request MultipartHttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodImg/saveProdImg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProdImg(ProdImgVO prodImgVO, MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = prodImgService.saveProdImg(request, prodImgVO, sessionInfoVO);

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
     * 상품이미지관리 - 상품이미지삭제
     *
     * @param prodImgVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodImg/delProdImg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result delProdImg(ProdImgVO prodImgVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if(prodImgService.delProdImg(prodImgVO, sessionInfoVO)) {
            return returnJson(Status.OK);
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 상품이미지관리 - 이미지매장적용 매장리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.06.16
     * @return
     */
    @RequestMapping(value = "/prodImg/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(ProdImgVO prodImgVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodImgService.getStoreList(prodImgVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodImgVO);
    }

    /**
     * 상품이미지관리 -  본사상품이미지 매장적용
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.06.17
     * @return
     */
    @RequestMapping(value = "/prodImg/prodImgToStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result prodImgToStore(@RequestBody ProdImgVO[] prodImgVOs, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = prodImgService.prodImgToStore(prodImgVOs, sessionInfo);

        return returnJson(Status.OK, result);
    }

    /**
     * 상품이미지관리 -  상품이미지 복사
     *
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2022.01.13
     * @return
     */
    @RequestMapping(value = "/prodImg/prodImgCopy.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result prodImgCopy(ProdImgVO prodImgVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = prodImgService.prodImgCopy(prodImgVO, sessionInfo);

        return returnJson(Status.OK, result);
    }
    /**
     * 상품이미지관리 -  상품이미지 복사
     *
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2022.01.13
     * @return
     */
    @RequestMapping(value = "/prodImg/prodImgDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result prodImgDeleteAll(ProdImgVO prodImgVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = prodImgService.prodImgDeleteAll(prodImgVO, sessionInfo);

        return returnJson(Status.OK, result);
    }

}
