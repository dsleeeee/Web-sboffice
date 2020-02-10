package kr.co.solbipos.sale.status.pos.hour.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.hour.service.PosHourService;
import kr.co.solbipos.sale.status.pos.hour.service.PosHourVO;

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
 * @Class Name : TodayBillSaleDtlController.java
 * @Description : 매출관리 > 매출현황 > 포스별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.04  김진       최초생성
 *
 * @author 엠투엠글로벌 김진
 * @since 2020.02.04
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/pos")
public class PosHourController {
    private final SessionService sessionService;
    private final PosHourService posHourService;

    @Autowired
    public PosHourController(SessionService sessionService, PosHourService posHourService) {
        this.sessionService = sessionService;
        this.posHourService = posHourService;
    }


    /**
     * 포스별매출 시간대별별 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 02. 04.
     */
    @RequestMapping(value = "/hour/view.sb", method = RequestMethod.GET)
    public String posHourView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/pos/posSale";
    }


    /**
     * 포스별매출 시간대별별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posHourVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 04.
     */
    @RequestMapping(value = "/hour/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosHourVO posHourVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posHourVO.getPosNo() != null && !"".equals(posHourVO.getPosNo())) {
        	 String[] arrPosNo = posHourVO.getPosNo().split(",");
             posHourVO.setArrPosNo(arrPosNo);
             posHourVO.setArrStorePos(arrPosNo);
        } else {
        	String[] arrStoreCd = posHourVO.getStoreCd().split(",");

        	if (arrStoreCd.length > 0) {
        		if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
        			posHourVO.setArrStoreCd(arrStoreCd);
        		}
        	}

            List<DefaultMap<String>> list = posHourService.getPosNmList(posHourVO, sessionInfoVO);

            if (list.size() > 0) {

            	String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posHourVO.setArrStorePos(arrStorePos);

            }

        }

        List<DefaultMap<String>> list = posHourService.getPosDayList(posHourVO, sessionInfoVO);
        //System.out.println("list.size() :: "+posDayVO.getArrPosCd().length);
        return ReturnUtil.returnListJson(Status.OK, list, posHourVO);
    }


    /**
     * 포스별매출 시간대별 - 포스 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posHourVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 04.
     */
    @RequestMapping(value = "/hour/posNmList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosNmList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosHourVO posHourVO) {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

    	if (posHourVO.getPosNo() != null && !"".equals(posHourVO.getPosNo())) {
    		String[] arrPosNo = posHourVO.getPosNo().split(",");

    		if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				posHourVO.setArrPosNo(arrPosNo);
    			}
    		}
    	} else {
    		String[] arrStoreCd = posHourVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				posHourVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

        List<DefaultMap<String>> list = posHourService.getPosNmList(posHourVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, posHourVO);
    }

}
