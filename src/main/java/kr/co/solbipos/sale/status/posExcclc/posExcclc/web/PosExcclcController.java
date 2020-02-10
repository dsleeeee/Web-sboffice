package kr.co.solbipos.sale.status.posExcclc.posExcclc.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.posExcclc.posExcclc.service.PosExcclcService;
import kr.co.solbipos.sale.status.posExcclc.posExcclc.service.PosExcclcVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.toCamelCaseName;

import java.util.List;

/**
 * @Class Name : TodayBillSaleDtlController.java
 * @Description : 매출관리 > 매출현황 > 포스별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.21  이승규      최초생성
 *
 * @author 엠투엠글로벌 이승규
 * @since 2020.01.21
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/posExcclc")
public class PosExcclcController {
    private final SessionService sessionService;
    private final PosExcclcService posExcclcService;

    @Autowired
    public PosExcclcController(SessionService sessionService, PosExcclcService posExcclcService) {
        this.sessionService = sessionService;
        this.posExcclcService = posExcclcService;
    }


    /**
     * POS 정산내역 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/posExcclc/view.sb", method = RequestMethod.GET)
    public String posExcclcView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/posExcclc/posExcclc";
    }


    /**
     * POS 정산내역 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posExcclclVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/posExcclc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosExcclcList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosExcclcVO posExcclcVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = posExcclcService.getPosExcclcList(posExcclcVO, sessionInfoVO);
        //System.out.println("list.size() :: "+posExcclcVO.getArrPosCd().length);
        return ReturnUtil.returnListJson(Status.OK, list, posExcclcVO);
    }


    /**
     * POS 정산내역 - 세부 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posExcclclVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/posExcclc/dtlInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dtlInfo(PosExcclcVO posExcclcVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        DefaultMap<String> result = posExcclcService.getPosExcclcDetailInfo(posExcclcVO);

        return returnJson(Status.OK, result);
    }

}
