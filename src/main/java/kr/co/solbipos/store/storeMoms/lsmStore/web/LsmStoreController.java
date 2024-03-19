package kr.co.solbipos.store.storeMoms.lsmStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
import kr.co.solbipos.store.storeMoms.lsmStore.service.LsmStoreService;
import kr.co.solbipos.store.storeMoms.lsmStore.service.LsmStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : LsmStoreController.java
 * @Description : 맘스터치 > 매장관리 > LSM사용매장조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.26  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.04.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/store/storeMoms/lsmStore")
public class LsmStoreController {
    private final SessionService sessionService;
    private final LsmStoreService lsmStoreService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    @Autowired
    public LsmStoreController(SessionService sessionService, LsmStoreService lsmStoreService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.lsmStoreService = lsmStoreService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }


    /**
     * 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2023.04.26
     */
    @RequestMapping(value = "/lsmStore/view.sb", method = RequestMethod.GET)
    public String empMonthView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "store/storeMoms/lsmStore/lsmStoreTab";
    }

    /**
     * 터치키 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  권지현
     * @since   2023.04.26
     */
    @RequestMapping(value = "/lsmStore/getLsmStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLsmStoreList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, LsmStoreVO lsmStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = lsmStoreService.getLsmStoreList(lsmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, lsmStoreVO);
    }

    /**
     * 터치키 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  권지현
     * @since   2023.04.26
     */
    @RequestMapping(value = "/lsmStore/getLsmStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLsmStoreExcelList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, LsmStoreVO lsmStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = lsmStoreService.getLsmStoreExcelList(lsmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, lsmStoreVO);
    }

    /**
     * 키오스크 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.03.18
     */
    @RequestMapping(value = "/lsmStore/getLsmKioskStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLsmKioskStoreList(HttpServletRequest request, HttpServletResponse response,
                                  Model model, LsmStoreVO lsmStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = lsmStoreService.getLsmKioskStoreList(lsmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, lsmStoreVO);
    }

    /**
     * 키오스크 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.03.18
     */
    @RequestMapping(value = "/lsmStore/getLsmKioskStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLsmKioskStoreExcelList(HttpServletRequest request, HttpServletResponse response,
                                       Model model, LsmStoreVO lsmStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = lsmStoreService.getLsmKioskStoreExcelList(lsmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, lsmStoreVO);
    }

}
