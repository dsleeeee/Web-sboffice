package kr.co.solbipos.stock.curr.storeCurr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.curr.storeCurr.service.StoreCurrService;
import kr.co.solbipos.stock.curr.storeCurr.service.StoreCurrVO;
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
 * @Class Name : StoreCurrController.java
 * @Description : 재고관리 > 재고현황 > 현재고현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.31  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 10.31
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/curr/storeCurr")
public class StoreCurrController {
    private final SessionService sessionService;
    private final StoreCurrService storeCurrService;
    private final CmmEnvService cmmEnvService;

    @Autowired
    public StoreCurrController(SessionService sessionService, StoreCurrService storeCurrService, CmmEnvService cmmEnvService) {
        this.sessionService = sessionService;
        this.storeCurrService = storeCurrService;
        this.cmmEnvService = cmmEnvService;
    }

    /**
     * 현재고현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 30.
     */
    @RequestMapping(value = "/storeCurr/view.sb", method = RequestMethod.GET)
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

        return "stock/curr/storeCurr/storeCurr";
    }

    /**
     * 현재고현황 - 현재고현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeCurrVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 30.
     */
    @RequestMapping(value = "/storeCurr/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreCurrList(HttpServletRequest request, HttpServletResponse response,
        Model model, StoreCurrVO storeCurrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        //storeCurrVO.setStoreCd(sessionInfoVO.getStoreCd());

        List<DefaultMap<String>> list = storeCurrService.getStoreCurrList(storeCurrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeCurrVO);
    }
}
