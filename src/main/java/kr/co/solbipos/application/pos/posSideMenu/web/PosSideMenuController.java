package kr.co.solbipos.application.pos.posSideMenu.web;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdAuthEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyService;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyStyleVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : PosTouchKeyController.java
 * @Description : POS 화면에서 판매터치키 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.28  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.28
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/application/pos/posSideMenu/")
public class PosSideMenuController {


    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;
    private final TouchKeyService touchkeyService;

    public PosSideMenuController(SessionService sessionService, CmmEnvUtil cmmEnvUtil, TouchKeyService touchkeyService) {
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.touchkeyService = touchkeyService;
    }


    /**
     * 조회 화면 전,
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "posSideMenuTest.sb", method = RequestMethod.GET)
    public String posSideMenuTestView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "application/pos/posSideMenu/posSideMenuTest";
    }

    /**
     * 게시판 조회 화면
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "posSideMenu.sb", method = RequestMethod.GET)
    public String posSideMenuView(HttpServletRequest request, HttpServletResponse response, Model model) {

        /**
         *  판매터치키등록
         * */

//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//
//        TouchKeyVO params = new TouchKeyVO();
//        params.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
//        params.setStoreCd(sessionInfoVO.getStoreCd());
//        params.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
//
//        TouchKeyClassVO touchKeyClassVO = new TouchKeyClassVO();
//        touchKeyClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
//        touchKeyClassVO.setStoreCd(sessionInfoVO.getStoreCd());
//        touchKeyClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
//        touchKeyClassVO.setPageNo(1);
//
//        TouchKeyStyleVO touchKeyStyleVO = new TouchKeyStyleVO();
//        touchKeyStyleVO.setStyleCd("");
//
//        // 본사or매장의 터치키 환경 설정 값을 조회해서 셋팅
//        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
//            model.addAttribute("maxClassRow", cmmEnvUtil.getHqEnvst(sessionInfoVO, "0041"));
//        } else {
//            model.addAttribute("maxClassRow", cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1041"));
//        }
//
//        // 터치키 관련 권한정보 가져오기 : 2019-08-08 이다솜
//        String envstCd = "0017";
//        String touchKeyEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, envstCd));
//        model.addAttribute("touchKeyEnvstVal", touchKeyEnvstVal);
//
//        // 터치키 그룹 가져오기
//        List<DefaultMap<String>> touchKeyGrpList = touchkeyService.getTouchKeyGrp(params, sessionInfoVO);
//        model.addAttribute("touchKeyGrp", convertToJson(touchKeyGrpList));
//
//        /**
//         *  상품정보관리
//         * */
//
//        // 상품생성설정
//        ProdAuthEnvFg prodAuthEnvstVal = ProdAuthEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0042"));
//
//        // 상품코드 채번방식
//        ProdNoEnvFg prodNoEnvFg;
//        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
//            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0028"));
//        }else{
//            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0028"));
//        }
//
//        model.addAttribute("prodAuthEnvstVal", prodAuthEnvstVal);
//        model.addAttribute("prodNoEnvFg", prodNoEnvFg);
//
//        // 매장상품제한구분 환경변수 값(환경변수 1100 사용)
//        String sideEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1100"));
//        model.addAttribute("sideEnvstVal", sideEnvstVal);

        return "application/pos/posSideMenu/posSideMenu";
    }


}