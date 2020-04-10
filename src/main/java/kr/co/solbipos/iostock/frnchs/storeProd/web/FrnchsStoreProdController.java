package kr.co.solbipos.iostock.frnchs.storeProd.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.frnchs.store.service.FrnchsStoreService;
import kr.co.solbipos.iostock.frnchs.storeProd.service.FrnchsStoreProdService;
import kr.co.solbipos.iostock.frnchs.storeProd.service.FrnchsStoreProdVO;

/**
 * @Class Name : TodayBillSaleDtlController.java
 * @Description : 수불관리 > 본사-매장간 입출고 내역 > 매장-상품별 입출고내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.08  정유경      최초생성
 *
 * @author 솔비포스
 * @since 2020.03.04
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/frnchs/storeprod")
public class FrnchsStoreProdController {
    private final SessionService sessionService;
    private final FrnchsStoreProdService frnchsStoreProdService;

    @Autowired
    public FrnchsStoreProdController(SessionService sessionService, FrnchsStoreProdService frnchsStoreProdService) {
        this.sessionService = sessionService;
        this.frnchsStoreProdService = frnchsStoreProdService;
    }


    /**매장별 입출고내역
     * 분류별상품현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author
     * @since   2020.03.04
     */
    @RequestMapping(value = "/storeprod/list.sb", method = RequestMethod.GET)
    public String frnchsStoreProdView(HttpServletRequest request, HttpServletResponse response, Model model) {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "iostock/frnchs/storeProd/storeProd";
    }


    /**
     * 매장-상품별 입출고내역 - 매장-상품별 입출고내역 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  정유경
     * @since   2020.03.04
     */
    @RequestMapping(value = "/storeprod/frnchsStoreProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreProdList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreProdVO frnchsStoreProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = frnchsStoreProdService.getFrnchsStoreProdList(frnchsStoreProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreProdVO);
    }

    /**
     * 매장-상품별 입출고내역 - 매장-상품별 입출고내역 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  정유경
     * @since   2020.03.04
     */
    @RequestMapping(value = "/storeprod/frnchsStoreProdDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreProdDtlList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreProdVO frnchsStoreProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = frnchsStoreProdService.getFrnchsStoreProdDtlList(frnchsStoreProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreProdVO);
    }
}
