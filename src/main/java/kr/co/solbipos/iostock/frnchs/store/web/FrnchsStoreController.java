package kr.co.solbipos.iostock.frnchs.store.web;

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
import kr.co.solbipos.iostock.frnchs.store.service.FrnchsStoreVO;

/**
 * @Class Name : TodayBillSaleDtlController.java
 * @Description : 매출관리 > 매출현황 > 분류별상품 상세현황
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
@RequestMapping("/iostock/frnchs/store")
public class FrnchsStoreController {
    private final SessionService sessionService;
    private final FrnchsStoreService frnchsStoreService;

    @Autowired
    public FrnchsStoreController(SessionService sessionService, FrnchsStoreService frnchsStoreService) {
        this.sessionService = sessionService;
        this.frnchsStoreService = frnchsStoreService;
    }


    /**매장별 입출고내역
     * 분류별상품현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author	정유경
     * @since   2020.03.04
     */
    @RequestMapping(value = "/store/list.sb", method = RequestMethod.GET)
    public String frnchsStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "iostock/frnchs/store/store";
    }


    /**
     * 매장별 입출고내역 - 매장별 입출고내역 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  정유경
     * @since   2020.03.04
     */
    @RequestMapping(value = "/store/frnchsStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreVO frnchsStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = frnchsStoreService.getFrnchsStoreList(frnchsStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreVO);
    }

    /**
     * 매장별 입출고내역 - 매장별 입출고내역 엑셀리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  조동훤
     * @since   2020.04.22
     */
    @RequestMapping(value = "/store/frnchsStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreExcelList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreVO frnchsStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = frnchsStoreService.getFrnchsStoreExcelList(frnchsStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreVO);
    }

    
    /**
     * 매장별 입출고내역 상세 레이어- 매장별 입출고내역 매장상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  정유경
     * @since   2020.03.04
     */
    @RequestMapping(value = "/store/frnchsStoreInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreInfoList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreVO frnchsStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = frnchsStoreService.getFrnchsStoreInfoList(frnchsStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreVO);
    }

    /**
     * 매장별 입출고내역 상세 레이어- 매장별 입출고내역 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRnakVO
     * @return  String
     * @author  정유경
     * @since   2020.03.04
     */
    @RequestMapping(value = "/store/frnchsStoreDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFrnchsStoreDtlList(HttpServletRequest request, HttpServletResponse response, Model model, FrnchsStoreVO frnchsStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = frnchsStoreService.getFrnchsStoreDtlList(frnchsStoreVO);

        return ReturnUtil.returnListJson(Status.OK, list, frnchsStoreVO);
    }
}
