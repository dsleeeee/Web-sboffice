package kr.co.solbipos.sys.etc.vancard.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.etc.vancard.service.CardCmpnyVO;
import kr.co.solbipos.sys.etc.vancard.service.VanCardService;
import kr.co.solbipos.sys.etc.vancard.service.VanCardVO;
import kr.co.solbipos.sys.etc.vancard.service.VanCmpnyVO;

/**
 * @Class Name : VanCardController.java
 * @Description : 시스템관리 > VAN/CARD사 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sys/etc/vanCard")
public class VanCardController {

    @Autowired
    VanCardService vanCardService;
    @Autowired
    SessionService sessionService;

    /**
     * VAN/CARD사 관리 - 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return String
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/vanCard/view.sb", method = RequestMethod.GET)
    public String vanCardView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        
        List<DefaultMap<String>> listCardCmpny = new ArrayList<DefaultMap<String>>();
        // CARD사 목록 조회
        listCardCmpny = vanCardService.getCardCmpnyList(new CardCmpnyVO());
        // 콤보박스용 데이터 생성
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> m = new HashMap<>();
        for ( HashMap<String, String> cardCmpnyList : listCardCmpny ) {
            m = new HashMap<>();
            m.put("name", cardCmpnyList.get("cardcoNm"));
            m.put("value", cardCmpnyList.get("cardcoCd"));
            list.add(m);
        }
        
        model.addAttribute("cardCmpnyList", convertToJson(list));
        
        return "sys/etc/vanCard/vanCard";
    }

    /**
     * VAN/CARD사 관리 - VAN사 목록 조회
     * 
     * @param request
     * @param response
     * @param vanCmpnyVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/vanCard/van/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVanCmpnyList(HttpServletRequest request, HttpServletResponse response,
            VanCmpnyVO vanCmpnyVO, Model model) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // VAN사 목록 조회
        list = vanCardService.getVanCmpnyList(vanCmpnyVO);

        return ReturnUtil.returnListJson(Status.OK, list, vanCmpnyVO);

    }

    /**
     * VAN/CARD사 관리 - VAN사 목록 저장
     * 
     * @param request
     * @param response
     * @param vanCmpnyVOs
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/vanCard/van/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVanCmpnyList(@RequestBody VanCmpnyVO[] vanCmpnyVOs,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vanCardService.saveVanCmpnyList(vanCmpnyVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * VAN/CARD사 관리 - CARD사 목록 조회
     * 
     * @param request
     * @param response
     * @param cardCmpnyVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/vanCard/card/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardCmpnyList(HttpServletRequest request, HttpServletResponse response,
            CardCmpnyVO cardCmpnyVO, Model model) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // VAN사 목록 조회
        list = vanCardService.getCardCmpnyList(cardCmpnyVO);

        return ReturnUtil.returnListJson(Status.OK, list, cardCmpnyVO);

    }

    /**
     * VAN/CARD사 관리 - CARD사 목록 저장
     * 
     * @param request
     * @param response
     * @param cardCmpnyVOs
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/vanCard/card/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCardCmpnyList(@RequestBody CardCmpnyVO[] cardCmpnyVOs,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vanCardService.saveCardCmpnyList(cardCmpnyVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * VAN/CARD사 관리 - VAN/CARD사 매핑 목록 조회
     * 
     * @param request
     * @param response
     * @param vanCardVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/vanCard/mapng/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMapngList(HttpServletRequest request, HttpServletResponse response,
            VanCardVO vanCardVO, Model model) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // VAN사 목록 조회
        list = vanCardService.getMapngList(vanCardVO);

        return ReturnUtil.returnListJson(Status.OK, list, vanCardVO);

    }

    /**
     * VAN/CARD사 관리 - VAN/CARD사 매핑 목록 저장
     * 
     * @param request
     * @param response
     * @param vanCardVOs
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/vanCard/mapng/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMapngList(@RequestBody VanCardVO[] vanCardVOs,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vanCardService.saveMapngList(vanCardVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

}
