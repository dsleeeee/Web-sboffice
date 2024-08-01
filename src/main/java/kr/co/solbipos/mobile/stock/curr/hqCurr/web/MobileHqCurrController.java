package kr.co.solbipos.mobile.stock.curr.hqCurr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.curr.hqCurr.service.MobileHqCurrService;
import kr.co.solbipos.mobile.stock.curr.hqCurr.service.MobileHqCurrVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
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
 * @Class Name : MobileHqCurrController.java
 * @Description : (모바일)재고현황 > 현재고현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.19  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/stock/curr/hqCurr")
public class MobileHqCurrController {

    private final SessionService sessionService;
    private final MobileHqCurrService mobileHqCurrService;
    private final CmmEnvService cmmEnvService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public MobileHqCurrController(SessionService sessionService, MobileHqCurrService mobileHqCurrService, CmmEnvService cmmEnvService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.mobileHqCurrService = mobileHqCurrService;
        this.cmmEnvService = cmmEnvService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 현재고현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobileHqCurr/view.sb", method = RequestMethod.GET)
    public String hqCurrView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 본사 환경설정 0003(레시피사용여부) 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("0003");
        String envst0003 = cmmEnvService.getHqEnvst(hqEnvstVO);

        // 본사 환경설정 0008(저울바코드구성) 조회
        hqEnvstVO.setEnvstCd("0008");
        String envst0008 = cmmEnvService.getHqEnvst(hqEnvstVO);

        model.addAttribute("envst0003", envst0003);
        model.addAttribute("envst0008", envst0008);

        // [1241 창고사용여부] 환경설정값 조회
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("storageEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1241"), "0"));
            System.out.println("storageEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1241"), "0"));
        } else {
            model.addAttribute("storageEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1241"), "0"));
            System.out.println("storageEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1241"), "0"));
        }

        return "mobile/stock/curr/hqCurr/mobileHqCurr";
    }

    /**
     * 현재고현황 - 현재고현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobileHqCurr/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqCurrList(HttpServletRequest request, HttpServletResponse response,
                                Model model, MobileHqCurrVO mobileHqCurrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        mobileHqCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = mobileHqCurrService.getHqCurrList(mobileHqCurrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileHqCurrVO);
    }

    /**
     * 현재고현황 - 재고현황 팝업 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobileHqCurr/getHqCurrDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCmmStockStatusList(HttpServletRequest request, HttpServletResponse response,
                                        MobileHqCurrVO mobileHqCurrVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileHqCurrService.gethqCurrDtlList(mobileHqCurrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileHqCurrVO);
    }

    /**
     * 현재고현황 - 현재고현황 엑셀 전체 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 19.
     */
    @RequestMapping(value = "/mobileHqCurr/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqCurrExcelList(HttpServletRequest request, HttpServletResponse response,
                                     Model model, MobileHqCurrVO mobileHqCurrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        mobileHqCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = mobileHqCurrService.getHqCurrExcelList(mobileHqCurrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileHqCurrVO);
    }
}
