package kr.co.solbipos.base.prod.vendr.web;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.vendr.service.VendrService;
import kr.co.solbipos.base.prod.vendr.service.VendrVO;

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
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/vendr/vendr/")
public class VendrController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    private final String RESULT_URI = "base/prod/vendr";

    @Autowired
    SessionService sessionService;
    @Autowired
    MessageService messageService;
    @Autowired
    VendrService vendrService;

    /**
     * 거래처 조회 페이지 이동
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model Model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String loginstatusList(HttpServletRequest request, HttpServletResponse response, Model model) {
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
    @RequestMapping(value = "list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result loginstatusListPost(VendrVO vendrVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        LOGGER.debug(vendrVO.toString());

        SessionInfoVO si = sessionService.getSessionInfo(request);
        
        List<DefaultMap<Object>> result = null;

        // 본사 또는 매장에 따라 분기 처리
        if(si.getOrgnFg() == OrgnFg.HQ) {
            result = vendrService.getHqVendrList(vendrVO);
        }else if(si.getOrgnFg() == OrgnFg.STORE) {
            result = vendrService.getMsVendrList(vendrVO);
        }
        
        return returnListJson(Status.OK, result, vendrVO);
    }

}
