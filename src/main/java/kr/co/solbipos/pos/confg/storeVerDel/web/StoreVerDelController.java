package kr.co.solbipos.pos.confg.storeVerDel.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.storeVerDel.service.StoreVerDelService;
import kr.co.solbipos.pos.confg.storeVerDel.service.StoreVerDelVO;
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

/**
* @Class Name : StoreVerDelController.java
* @Description : 포스관리 > POS 설정관리 > 매장별 POS 버전 삭제
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2023.10.12  이다솜      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 이다솜
* @since 2023.10.12
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/pos/confg/storeVerDel")
public class StoreVerDelController {

    private final StoreVerDelService storeVerDelService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public StoreVerDelController(StoreVerDelService storeVerDelService, SessionService sessionService) {
        this.storeVerDelService = storeVerDelService;
        this.sessionService = sessionService;
    }

    /**
     * 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023.10.12
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        return "pos/confg/storeVerDel/storeVerDel";
    }

    /**
     * 매장별 포스 버전 정보 조회
     * @param request
     * @param response
     * @param model
     * @param storeVerDelVO
     * @return
     */
    @RequestMapping(value = "/getStoreVerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreVerList(HttpServletRequest request, HttpServletResponse response,
                                 Model model, StoreVerDelVO storeVerDelVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeVerDelService.getStoreVerList(storeVerDelVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeVerDelVO);
    }

    /**
     * 매장별 포스 버전 정보 조회 엑셀다운로드
     * @param request
     * @param response
     * @param model
     * @param storeVerDelVO
     * @return
     */
    @RequestMapping(value = "/getStoreVerExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreVerExcelList(HttpServletRequest request, HttpServletResponse response,
                                 Model model, StoreVerDelVO storeVerDelVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeVerDelService.getStoreVerExcelList(storeVerDelVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeVerDelVO);
    }

    /**
     * 매장별 포스 버전 삭제
     * @param request
     * @param response
     * @param storeVerDelVOs
     * @return
     */
    @RequestMapping(value = "/deleteStoreVer.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteStoreVer(HttpServletRequest request, HttpServletResponse response, @RequestBody StoreVerDelVO[] storeVerDelVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeVerDelService.deleteStoreVer(storeVerDelVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
