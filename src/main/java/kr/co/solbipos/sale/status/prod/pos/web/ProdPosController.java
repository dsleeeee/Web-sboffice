package kr.co.solbipos.sale.status.prod.pos.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.pos.service.ProdPosService;
import kr.co.solbipos.sale.status.prod.pos.service.ProdPosVO;

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
 * @ 2020.02.04  김진      최초생성
 *
 * @author 엠투엠글로벌 김진
 * @since 2020.02.04
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/prod")
public class ProdPosController {
    private final SessionService sessionService;
    private final ProdPosService prodPosService;

    @Autowired
    public ProdPosController(SessionService sessionService, ProdPosService prodPosService) {
        this.sessionService = sessionService;
        this.prodPosService = prodPosService;
    }


    /**
     * 상품별매출 포스별 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 02. 04.
     */
    @RequestMapping(value = "/prod/view.sb", method = RequestMethod.GET)
    public String posProdView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/prod/prodSale";
    }


    /**
     * 포스별탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodPosVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 04.
     */
    @RequestMapping(value = "/prodPos/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdPosList(HttpServletRequest request, HttpServletResponse response,
        Model model, ProdPosVO prodPosVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (prodPosVO.getPosNo() != null && !"".equals(prodPosVO.getPosNo())) {
        	 String[] arrPosNo = prodPosVO.getPosNo().split(",");
        	 prodPosVO.setArrPosNo(arrPosNo);
        	 prodPosVO.setArrStorePos(arrPosNo);
        } else {
        	String[] arrStoreCd = prodPosVO.getStoreCd().split(",");

        	if (arrStoreCd.length > 0) {
        		if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
        			prodPosVO.setArrStoreCd(arrStoreCd);
        		}
        	}

            List<DefaultMap<String>> list = prodPosService.getPosNmList(prodPosVO, sessionInfoVO);

            if (list.size() > 0) {

            	String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                prodPosVO.setArrStorePos(arrStorePos);
            }
        }

        List<DefaultMap<String>> list = prodPosService.getProdPosList(prodPosVO, sessionInfoVO);
        //System.out.println("list.size() :: "+posProdVO.getArrPosCd().length);

        return ReturnUtil.returnListJson(Status.OK, list, prodPosVO);
    }
    
    /**
     * 포스별탭 - 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodPosVO
     * @return  String
     * @author  서재식
     * @since   2020. 04. 22.
     */
    @RequestMapping(value = "/prodPos/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdPosExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, ProdPosVO prodPosVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (prodPosVO.getPosNo() != null && !"".equals(prodPosVO.getPosNo())) {
        	 String[] arrPosNo = prodPosVO.getPosNo().split(",");
        	 prodPosVO.setArrPosNo(arrPosNo);
        	 prodPosVO.setArrStorePos(arrPosNo);
        } else {
        	String[] arrStoreCd = prodPosVO.getStoreCd().split(",");

        	if (arrStoreCd.length > 0) {
        		if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
        			prodPosVO.setArrStoreCd(arrStoreCd);
        		}
        	}

            List<DefaultMap<String>> list = prodPosService.getPosNmList(prodPosVO, sessionInfoVO);

            if (list.size() > 0) {

            	String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                prodPosVO.setArrStorePos(arrStorePos);
            }
        }

        List<DefaultMap<String>> list = prodPosService.getProdPosExcelList(prodPosVO, sessionInfoVO);
        //System.out.println("list.size() :: "+posProdVO.getArrPosCd().length);

        return ReturnUtil.returnListJson(Status.OK, list, prodPosVO);
    }
    
}
