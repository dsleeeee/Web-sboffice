package kr.co.solbipos.store.storeMoms.storeSaleArea.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.storeMoms.storeSaleArea.service.StoreSaleAreaService;
import kr.co.solbipos.store.storeMoms.storeSaleArea.service.StoreSaleAreaVO;
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
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreSaleAreaController.java
 * @Description : 맘스터치 > 점포관리 > 점포 영업 지역 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.11.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.11.21
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value="/store/storeMoms/storeSaleArea/")
public class StoreSaleAreaController {

    private final StoreSaleAreaService storeSaleAreaService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public StoreSaleAreaController(StoreSaleAreaService storeSaleAreaService, SessionService sessionService) {
        this.storeSaleAreaService = storeSaleAreaService;
        this.sessionService = sessionService;
    }

    /**
     * 점포 영업 지역 관리 화면 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이다솜
     * @since   2022.11.21
     */
    @RequestMapping(value = "view.sb", method = RequestMethod.GET)
    public String view(StoreSaleAreaVO storeSaleAreaVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 지사 조회(콤보박스용)
        model.addAttribute("branchCombo", convertToJson(storeSaleAreaService.getBranchCombo(storeSaleAreaVO, sessionInfoVO)));

        return "store/storeMoms/storeSaleArea/storeSaleArea";
    }

    /**
     * 매장목록 조회
     * @param   storeSaleAreaVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2022.11.21
     */
    @RequestMapping(value = "getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(StoreSaleAreaVO storeSaleAreaVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = storeSaleAreaService.getStoreList(storeSaleAreaVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeSaleAreaVO);
    }

    /**
     * 매장 조회(콤보박스용)
     * @param storeSaleAreaVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2022.11.22
     */
    @RequestMapping(value = "getStoreCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreCombo(StoreSaleAreaVO storeSaleAreaVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> storeCombo = storeSaleAreaService.getStoreCombo(storeSaleAreaVO, sessionInfoVO);

        return returnListJson(Status.OK, storeCombo);
    }

    /**
     * 매장 영업지역 조회
     * @param storeSaleAreaVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2022.11.22
     */
    @RequestMapping(value = "getStoreSaleArea.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSaleArea(StoreSaleAreaVO storeSaleAreaVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        DefaultMap<String> result = storeSaleAreaService.getStoreSaleArea(storeSaleAreaVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매장 영업지역 저장
     * @param storeSaleAreaVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2022.11.22
     */
    @RequestMapping(value = "saveStoreSaleArea.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreSaleArea(@RequestBody StoreSaleAreaVO storeSaleAreaVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int result = storeSaleAreaService.saveStoreSaleArea(storeSaleAreaVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 서울, 경기 매장 영업지역 조회
     * @param storeSaleAreaVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2022.11.22
     */
    @RequestMapping(value = "getMetropolitanSaleArea.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMetropolitanSaleArea(StoreSaleAreaVO storeSaleAreaVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = storeSaleAreaService.getMetropolitanSaleArea(storeSaleAreaVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeSaleAreaVO);
    }
}
