package kr.co.solbipos.sale.status.pos.prod.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.prod.service.PosProdService;
import kr.co.solbipos.sale.status.pos.prod.service.PosProdVO;

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
 * @Class Name : PosProdController.java
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
public class PosProdController {
    private final SessionService sessionService;
    private final PosProdService posProdService;

    @Autowired
    public PosProdController(SessionService sessionService, PosProdService posProdService) {
        this.sessionService = sessionService;
        this.posProdService = posProdService;
    }


    /**
     * 포스별매출 상품별 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/prod/view.sb", method = RequestMethod.GET)
    public String posProdView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/pos/posSale";
    }


    /**
     * 포스별매출 상품별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posProdVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/prod/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosProdVO posProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posProdVO.getPosNo() != null && !"".equals(posProdVO.getPosNo())) {
        	 String[] arrPosNo = posProdVO.getPosNo().split(",");
             posProdVO.setArrPosNo(arrPosNo);
             posProdVO.setArrStorePos(arrPosNo);
        } else {
        	String[] arrStoreCd = posProdVO.getStoreCd().split(",");

        	if (arrStoreCd.length > 0) {
        		if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
        			posProdVO.setArrStoreCd(arrStoreCd);
        		}
        	}

            List<DefaultMap<String>> list = posProdService.getPosNmList(posProdVO, sessionInfoVO);

            if (list.size() > 0) {

            	String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posProdVO.setArrStorePos(arrStorePos);

            }

        }

        if (posProdVO.getArrStorePos() == null) {
        	return ReturnUtil.returnListJson(Status.OK, null, posProdVO);
        } else {
        	List<DefaultMap<String>> list = posProdService.getPosProdList(posProdVO, sessionInfoVO);
            //System.out.println("list.size() :: "+posProdVO.getArrPosCd().length);
            return ReturnUtil.returnListJson(Status.OK, list, posProdVO);
        }
    }

    /**
     * 포스별매출 상품별 - 리스트 조회 (엑셀)
     * @param   request
     * @param   response
     * @param   model
     * @param   posProdVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/prod/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosProdExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosProdVO posProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posProdVO.getPosNo() != null && !"".equals(posProdVO.getPosNo())) {
        	 String[] arrPosNo = posProdVO.getPosNo().split(",");
             posProdVO.setArrPosNo(arrPosNo);
             posProdVO.setArrStorePos(arrPosNo);
        } else {
        	String[] arrStoreCd = posProdVO.getStoreCd().split(",");

        	if (arrStoreCd.length > 0) {
        		if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
        			posProdVO.setArrStoreCd(arrStoreCd);
        		}
        	}

            List<DefaultMap<String>> list = posProdService.getPosNmList(posProdVO, sessionInfoVO);

            if (list.size() > 0) {

            	String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posProdVO.setArrStorePos(arrStorePos);

            }

        }

        if (posProdVO.getArrStorePos() == null) {
        	return ReturnUtil.returnListJson(Status.OK, null, posProdVO);
        } else {
        	List<DefaultMap<String>> list = posProdService.getPosProdExcelList(posProdVO, sessionInfoVO);
            //System.out.println("list.size() :: "+posProdVO.getArrPosCd().length);
            return ReturnUtil.returnListJson(Status.OK, list, posProdVO);
        }
    }

}
