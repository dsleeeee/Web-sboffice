package kr.co.solbipos.sale.status.pos.dayOfWeek.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.dayOfWeek.service.PosDayOfWeekService;
import kr.co.solbipos.sale.status.pos.dayOfWeek.service.PosDayOfWeekVO;

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
 * @Class Name : PosDayOfWeekController.java
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
public class PosDayOfWeekController {
    private final SessionService sessionService;
    private final PosDayOfWeekService posDayOfWeekService;

    @Autowired
    public PosDayOfWeekController(SessionService sessionService, PosDayOfWeekService posDayOfWeekService) {
        this.sessionService = sessionService;
        this.posDayOfWeekService = posDayOfWeekService;
    }


    /**
     * 포스별매출 요일별 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/dayOfWeek/view.sb", method = RequestMethod.GET)
    public String posDayOfWeekView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/pos/posSale";
    }


    /**
     * 포스별매출 요일별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posDayOfWeekVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/dayOfWeek/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayOfWeekList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosDayOfWeekVO posDayOfWeekVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posDayOfWeekVO.getPosNo() != null && !"".equals(posDayOfWeekVO.getPosNo())) {
        	 String[] arrPosNo = posDayOfWeekVO.getPosNo().split(",");
             posDayOfWeekVO.setArrPosNo(arrPosNo);
             posDayOfWeekVO.setArrStorePos(arrPosNo);
        } else {
        	String[] arrStoreCd = posDayOfWeekVO.getStoreCd().split(",");

        	if (arrStoreCd.length > 0) {
        		if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
        			posDayOfWeekVO.setArrStoreCd(arrStoreCd);
        		}
        	}

            List<DefaultMap<String>> list = posDayOfWeekService.getPosNmList(posDayOfWeekVO, sessionInfoVO);

            if (list.size() > 0) {

            	String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posDayOfWeekVO.setArrStorePos(arrStorePos);

            }

        }

        List<DefaultMap<String>> list = posDayOfWeekService.getPosDayOfWeekList(posDayOfWeekVO, sessionInfoVO);
        //System.out.println("list.size() :: "+posDayOfWeekVO.getArrPosCd().length);
        return ReturnUtil.returnListJson(Status.OK, list, posDayOfWeekVO);
    }

}
