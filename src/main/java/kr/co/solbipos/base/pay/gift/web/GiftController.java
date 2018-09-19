package kr.co.solbipos.base.pay.gift.web;


import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.pay.coupon.service.*;
import kr.co.solbipos.base.pay.gift.service.GiftService;
import kr.co.solbipos.base.pay.gift.service.GiftVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : GiftController.java
 * @Description : 기초관리 > 결제수단 > 상품권등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.18  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.09.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/pay/gift")
public class GiftController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** service */
    @Autowired
    GiftService service;
    @Autowired
    SessionService sessionService;

    /** util */
    @Autowired
    CmmCodeUtil cmmCodeUtil;
    @Autowired
    CmmEnvUtil cmmEnvUtil;

    /**
     * 상품권 등록 화면
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.09
     */
    @RequestMapping(value = "/class/giftView.sb", method = RequestMethod.GET)
    public String prodClassView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "base/pay/gift/giftView";
    }

    /**
     * 상품권 분류 조회
     * @param   payMethodClassVO
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.09
     */
    @RequestMapping(value = "/class/getGiftClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getGiftClassList(PayMethodClassVO payMethodClassVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = service.getGiftClassList(payMethodClassVO, sessionInfoVO);

        return returnListJson(Status.OK, list, payMethodClassVO);
    }

    /**
     * 상품권분류 저장 (본사/매장)
     * @param   payMethodClassVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.10
     */
    @RequestMapping(value = "/class/saveGiftClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveGiftClassList(@RequestBody PayMethodClassVO[] payMethodClassVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        try{
            result = service.saveGiftClassList(payMethodClassVOs, sessionInfoVO);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return returnJson(Status.OK, result);
    }

    /**
     * 상품권 조회
     * @param   giftVO
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.09
     */
    @RequestMapping(value = "/class/getGiftList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getGiftList(GiftVO giftVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = service.getGiftList( giftVO, sessionInfoVO);

        return returnListJson(Status.OK, list, giftVO);
    }

    /**
     * 상품권 저장 (본사/매장)
     * @param   giftVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.10
     */
    @RequestMapping(value = "/class/saveGiftList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveGiftList(@RequestBody GiftVO[] giftVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        try{
            result = service.saveGiftList(giftVOs, sessionInfoVO);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return returnJson(Status.OK, result);
    }

}
