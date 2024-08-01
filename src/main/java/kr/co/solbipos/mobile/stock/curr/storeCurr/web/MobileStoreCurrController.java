package kr.co.solbipos.mobile.stock.curr.storeCurr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.curr.storeCurr.service.MobileStoreCurrService;
import kr.co.solbipos.mobile.stock.curr.storeCurr.service.MobileStoreCurrVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * @Class Name : MobileStoreCurrController.java
 * @Description : (모바일)재고현황 > 매장현재고
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.23  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/stock/curr/storeCurr")
public class MobileStoreCurrController {

    private final SessionService sessionService;
    private final CmmEnvService cmmEnvService;
    private final MobileStoreCurrService mobileStoreCurrService;

    @Autowired
    public MobileStoreCurrController(SessionService sessionService, CmmEnvService cmmEnvService, MobileStoreCurrService mobileStoreCurrService) {
        this.sessionService = sessionService;
        this.cmmEnvService = cmmEnvService;
        this.mobileStoreCurrService = mobileStoreCurrService;
    }

    /**
     * 현재고현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStoreCurr/view.sb", method = RequestMethod.GET)
    public String storeCurrView(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 매장 환경설정 0003(레시피사용여부) 조회
        StoreEnvVO storeEnvVO = new StoreEnvVO();
        storeEnvVO.setStoreCd(sessionInfoVO.getStoreCd());
        storeEnvVO.setEnvstCd("0003");
        String envst0003 = cmmEnvService.getStoreEnvst(storeEnvVO);

        // 매장 환경설정 0008(저울바코드구성) 조회
        storeEnvVO.setEnvstCd("0008");
        String envst0008 = cmmEnvService.getStoreEnvst(storeEnvVO);

        model.addAttribute("envst0003", envst0003);
        model.addAttribute("envst0008", envst0008);

        return "mobile/stock/curr/storeCurr/mobileStoreCurr";
    }

    /**
     * 현재고현황 - 현재고현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   mobileStoreCurrVO
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStoreCurr/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreCurrList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, MobileStoreCurrVO mobileStoreCurrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        //storeCurrVO.setStoreCd(sessionInfoVO.getStoreCd());

        List<DefaultMap<String>> list = mobileStoreCurrService.getStoreCurrList(mobileStoreCurrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileStoreCurrVO);
    }
}
