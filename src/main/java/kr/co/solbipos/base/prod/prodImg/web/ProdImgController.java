package kr.co.solbipos.base.prod.prodImg.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodImg.service.ProdImgService;
import kr.co.solbipos.base.prod.prodImg.service.ProdImgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.common.service.message.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

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
    private final MessageService messageService;

    @Autowired
    public ProdImgController(SessionService sessionService, ProdImgService prodImgService, MessageService messageService) {

        this.sessionService = sessionService;
        this.prodImgService = prodImgService;
        this.messageService = messageService;
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



}
