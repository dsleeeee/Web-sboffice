package kr.co.solbipos.sale.status.pos.day.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.day.service.PosDayService;
import kr.co.solbipos.sale.status.pos.day.service.PosDayVO;

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
public class PosDayController {
    private final SessionService sessionService;
    private final PosDayService posDayService;

    @Autowired
    public PosDayController(SessionService sessionService, PosDayService posDayService) {
        this.sessionService = sessionService;
        this.posDayService = posDayService;
    }


    /**
     * 포스별매출 일자별 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/day/view.sb", method = RequestMethod.GET)
    public String posDayView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/pos/posSale";
    }


    /**
     * 포스별매출 일자별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posDaylVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/day/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosDayVO posDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posDayVO.getPosNo() != null && !"".equals(posDayVO.getPosNo())) {
        	 String[] arrPosNo = posDayVO.getPosNo().split(",");
             posDayVO.setArrPosNo(arrPosNo);
             posDayVO.setArrStorePos(arrPosNo);
        } else {
        	String[] arrStoreCd = posDayVO.getStoreCd().split(",");

        	if (arrStoreCd.length > 0) {
        		if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
        			posDayVO.setArrStoreCd(arrStoreCd);
        		}
        	}

            List<DefaultMap<String>> list = posDayService.getPosNmList(posDayVO, sessionInfoVO);

            if (list.size() > 0) {

            	String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posDayVO.setArrStorePos(arrStorePos);

            }

        }

        List<DefaultMap<String>> list = posDayService.getPosDayList(posDayVO, sessionInfoVO);
        //System.out.println("list.size() :: "+posDayVO.getArrPosCd().length);
        return ReturnUtil.returnListJson(Status.OK, list, posDayVO);
    }

    /**
     * 포스별매출 일자별 - 리스트 조회(엑셀)
     * @param   request
     * @param   response
     * @param   model
     * @param   posDaylVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/day/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosDayVO posDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posDayVO.getPosNo() != null && !"".equals(posDayVO.getPosNo())) {
        	 String[] arrPosNo = posDayVO.getPosNo().split(",");
             posDayVO.setArrPosNo(arrPosNo);
             posDayVO.setArrStorePos(arrPosNo);
        } else {
        	String[] arrStoreCd = posDayVO.getStoreCd().split(",");

        	if (arrStoreCd.length > 0) {
        		if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
        			posDayVO.setArrStoreCd(arrStoreCd);
        		}
        	}

            List<DefaultMap<String>> list = posDayService.getPosNmList(posDayVO, sessionInfoVO);

            if (list.size() > 0) {

            	String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posDayVO.setArrStorePos(arrStorePos);

            }

        }

        List<DefaultMap<String>> list = posDayService.getPosDayExcelList(posDayVO, sessionInfoVO);
        //System.out.println("list.size() :: "+posDayVO.getArrPosCd().length);
        return ReturnUtil.returnListJson(Status.OK, list, posDayVO);
    }


    /**
     * 포스별매출 일자별 - 포스 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posDaylVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "pos/posNmList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosNmList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosDayVO posDayVO) {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

    	if (posDayVO.getPosNo() != null && !"".equals(posDayVO.getPosNo())) {
    		String[] arrPosNo = posDayVO.getPosNo().split(",");

    		if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				posDayVO.setArrPosNo(arrPosNo);
    			}
    		}
    	} else {
    		String[] arrStoreCd = posDayVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				posDayVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

        List<DefaultMap<String>> list = posDayService.getPosNmList(posDayVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, posDayVO);
    }

}
