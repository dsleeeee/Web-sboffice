package kr.co.solbipos.base.prod.artiseeProdSpec.web;

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
import kr.co.solbipos.base.prod.artiseeProdSpec.service.ArtiseeProdSpecService;
import kr.co.solbipos.base.prod.artiseeProdSpec.service.ArtiseeProdSpecVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : ArtiseeProdSpecController.java
 * @Description : 보나비 > 상품관리 > 아티제-상품특성관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.10.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/artiseeProdSpec")
public class ArtiseeProdSpecController {

    private final SessionService sessionService;
    private final ArtiseeProdSpecService artiseeProdSpecService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ArtiseeProdSpecController(SessionService sessionService, ArtiseeProdSpecService artiseeProdSpecService) {
        this.sessionService = sessionService;
        this.artiseeProdSpecService = artiseeProdSpecService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/artiseeProdSpec/list.sb", method = RequestMethod.GET)
    public String artiseeProdSpecView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/prod/artiseeProdSpec/artiseeProdSpec";
    }

    /**
     * 아티제-상품특성관리 - 조회
     *
     * @param artiseeProdSpecVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 10. 04.
     */
    @RequestMapping(value = "/artiseeProdSpec/getArtiseeProdSpecList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getArtiseeProdSpecList(ArtiseeProdSpecVO artiseeProdSpecVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = artiseeProdSpecService.getArtiseeProdSpecList(artiseeProdSpecVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, artiseeProdSpecVO);
    }

    /**
     * 아티제-상품특성관리 - 적용 상품 조회
     *
     * @param artiseeProdSpecVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 10. 04.
     */
    @RequestMapping(value = "/artiseeProdSpec/getArtiseeProdSpecProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getArtiseeProdSpecProdList(ArtiseeProdSpecVO artiseeProdSpecVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = artiseeProdSpecService.getArtiseeProdSpecProdList(artiseeProdSpecVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, artiseeProdSpecVO);
    }


    /**
     * 아티제-상품특성관리 - 미적용 상품 조회
     *
     * @param artiseeProdSpecVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 10. 04.
     */
    @RequestMapping(value = "/artiseeProdSpec/getArtiseeProdSpecNoProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getArtiseeProdSpecNoProdList(ArtiseeProdSpecVO artiseeProdSpecVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = artiseeProdSpecService.getArtiseeProdSpecNoProdList(artiseeProdSpecVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, artiseeProdSpecVO);
    }

    /**
     * 아티제-상품특성관리 - 상품 저장
     *
     * @param artiseeProdSpecVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 10. 04.
     */
    @RequestMapping(value = "/artiseeProdSpec/getArtiseeProdSpecProdSaveInsert.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getArtiseeProdSpecProdSaveInsert(@RequestBody ArtiseeProdSpecVO[] artiseeProdSpecVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = artiseeProdSpecService.getArtiseeProdSpecProdSaveInsert(artiseeProdSpecVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 아티제-상품특성관리 - 상품 삭제
     *
     * @param artiseeProdSpecVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 10. 04.
     */
    @RequestMapping(value = "/artiseeProdSpec/getArtiseeProdSpecProdSaveDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getArtiseeProdSpecProdSaveDelete(@RequestBody ArtiseeProdSpecVO[] artiseeProdSpecVOs, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = artiseeProdSpecService.getArtiseeProdSpecProdSaveDelete(artiseeProdSpecVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}