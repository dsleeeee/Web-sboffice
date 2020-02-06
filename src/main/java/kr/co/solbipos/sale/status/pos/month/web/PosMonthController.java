package kr.co.solbipos.sale.status.pos.month.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.month.service.PosMonthService;
import kr.co.solbipos.sale.status.pos.month.service.PosMonthVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static kr.co.common.utils.spring.StringUtil.toCamelCaseName;

import java.util.List;

/**
 * @Class Name : PosMonthController.java
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
@RequestMapping("/sale/status/pos")
public class PosMonthController {
    private final SessionService sessionService;
    private final PosMonthService posMonthService;

    @Autowired
    public PosMonthController(SessionService sessionService, PosMonthService posMonthService) {
        this.sessionService = sessionService;
        this.posMonthService = posMonthService;
    }


    /**
     * 포스별매출 월별 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/month/view.sb", method = RequestMethod.GET)
    public String posMonthView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/pos/posSale";
    }


    /**
     * 포스별매출 월별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posMonthVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/month/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosMonthList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosMonthVO posMonthVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posMonthVO.getPosNo() != null && !"".equals(posMonthVO.getPosNo())) {
        	 String[] arrPosNo = posMonthVO.getPosNo().split(",");
             posMonthVO.setArrPosNo(arrPosNo);
             posMonthVO.setArrStorePos(arrPosNo);
        } else {
        	String[] arrStoreCd = posMonthVO.getStoreCd().split(",");

        	if (arrStoreCd.length > 0) {
        		if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
        			posMonthVO.setArrStoreCd(arrStoreCd);
        		}
        	}

            List<DefaultMap<String>> list = posMonthService.getPosNmList(posMonthVO, sessionInfoVO);

            if (list.size() > 0) {

            	String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posMonthVO.setArrStorePos(arrStorePos);

            }

        }

        List<DefaultMap<String>> list = posMonthService.getPosMonthList(posMonthVO, sessionInfoVO);
        //System.out.println("list.size() :: "+posMonthVO.getArrPosCd().length);
        return ReturnUtil.returnListJson(Status.OK, list, posMonthVO);
    }

}
