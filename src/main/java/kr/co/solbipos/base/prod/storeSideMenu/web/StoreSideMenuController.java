package kr.co.solbipos.base.prod.storeSideMenu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.ProdAuthEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyService;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyStyleVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
* @Class Name : storeSideMenuController.java
* @Description : 기초관리 > 상품관리 > 매장구성세트상품
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2021.05.26  권지현      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 권지현
* @since 2021. 05.26
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/base/prod/storeSideMenu")
public class StoreSideMenuController {

    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;
    private final TouchKeyService touchkeyService;
    private final ProdService prodService;

    public StoreSideMenuController(SessionService sessionService, CmmEnvUtil cmmEnvUtil, TouchKeyService touchkeyService, ProdService prodService) {
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.touchkeyService = touchkeyService;
        this.prodService = prodService;
    }


    /**
     * 매장구성세트상품 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        /** 판매터치키등록  */
        TouchKeyVO params = new TouchKeyVO();
        params.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        params.setStoreCd(sessionInfoVO.getStoreCd());
        params.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        TouchKeyClassVO touchKeyClassVO = new TouchKeyClassVO();
        touchKeyClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyClassVO.setStoreCd(sessionInfoVO.getStoreCd());
        touchKeyClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        touchKeyClassVO.setPageNo(1);

        TouchKeyStyleVO touchKeyStyleVO = new TouchKeyStyleVO();
        touchKeyStyleVO.setStyleCd("");

        // 본사or매장의 터치키 환경 설정 값을 조회해서 셋팅
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("maxClassRow", cmmEnvUtil.getHqEnvst(sessionInfoVO, "0041"));
        } else {
            model.addAttribute("maxClassRow", cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1041"));
        }

        // 터치키 관련 권한정보 가져오기 : 2019-08-08 이다솜
        String envstCd = "0017";
        String touchKeyEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, envstCd));
        model.addAttribute("touchKeyEnvstVal", touchKeyEnvstVal);

        // 터치키 그룹 가져오기
        List<DefaultMap<String>> touchKeyGrpList = touchkeyService.getTouchKeyGrp(params, sessionInfoVO);
        model.addAttribute("touchKeyGrp", convertToJson(touchKeyGrpList));

        /**  상품정보관리  */

        ProdVO prodVO = new ProdVO();

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

        model.addAttribute("brandList", convertToJson(prodService.getBrandList(prodVO, sessionInfoVO)));

        return "base/prod/storeSideMenu/storeSideMenu";
    }

}
