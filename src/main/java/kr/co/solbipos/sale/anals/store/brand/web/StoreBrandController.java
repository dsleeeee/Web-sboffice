package kr.co.solbipos.sale.anals.store.brand.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.brand.service.StoreBrandService;
import kr.co.solbipos.sale.anals.store.brand.service.StoreBrandVO;

/**
 * @Class Name : StoreBrandController.java
 * @Description : 매출관리 > 매출분석 > 매장별매출분석 > 브랜드별 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.31  정유경      최초생성
 *
 * @author 솔비포스 
 * @since 2020.01.31
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/anals/store")
public class StoreBrandController {
    private final SessionService sessionService;
    private final StoreBrandService storeBrandService;

    @Autowired
    public StoreBrandController(SessionService sessionService, StoreBrandService storeBrandService) {
        this.sessionService = sessionService;
        this.storeBrandService = storeBrandService;
    }

    /**
     * 브랜드별 매출 - 브랜드별 매출 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   storeBrandVO
     * @return  String
     * @author  정유경
     * @since   2020.01.31.
     */
    @RequestMapping(value = "/brand/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreBrandList(HttpServletRequest request, HttpServletResponse response, Model model, StoreBrandVO storeBrandVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeBrandService.getStoreBrandList(storeBrandVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, storeBrandVO);
    }
    
    /**
     * 브랜드별 매출 - 브랜드별 매출 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   storeBrandVO
     * @return  String
     * @author  정유경
     * @since   2020.01.31.
     */
    @RequestMapping(value = "/brand/sortFgComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSortFgComboList(HttpServletRequest request, HttpServletResponse response, Model model, StoreBrandVO storeBrandVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeBrandService.getSortFgComboList(storeBrandVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, storeBrandVO);
    }
}
