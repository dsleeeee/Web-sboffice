package kr.co.solbipos.store.hq.brand.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.brand.service.HqBrandService;
import kr.co.solbipos.store.hq.brand.service.HqBrandVO;
import kr.co.solbipos.store.hq.brand.service.HqClsVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;

/**
 * @Class Name : HqBrandManageController.java
 * @Description : 가맹점관리 > 본사정보 > 브랜드정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/store/hq/hqBrand/")
public class HqBrandManageController {

    @Autowired
    HqBrandService service;
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    CmmCodeUtil cmmCodeUtil;

    /**
     * 브랜드정보관리 화면 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "hqBrandManage/list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, 
            Model model) {
        return "store/hq/hqBrand/hqBrandManage";
    }
    
    /**
     * 브랜드 목록 조회
     * @param   hqBrand
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "hqBrandManage/getBrandlist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBrandlist(HqBrandVO hqBrand, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        List<DefaultMap<String>> list = service.getBrandlist(hqBrand);
        
        return returnListJson(Status.OK, list, hqBrand);
    }
    
    /**
     * 브랜드 등록, 수정
     * @param   hqBrandVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "hqBrandManage/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody HqBrandVO[] hqBrandVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.save(hqBrandVOs, sessionInfoVO);
        
        return returnJson(Status.OK, result);
    }
    
    /**
     * 환경설정 조회
     * @param   hqBrand
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "config/getConfiglist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result configList(HqBrandVO hqBrand, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        List<DefaultMap<String>> list = service.getConfigList(hqBrand);
        
        return returnListJson(Status.OK, list, hqBrand);
    }
    
    /**
     * 환경설정 저장
     * @param   hqBrands
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "config/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveConfig(@RequestBody HqEnvstVO[] hqEnvsts, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.saveConfig(hqEnvsts, sessionInfoVO);
        
        return returnJson(Status.OK, result);
    }
    
    /**
     * 분류 조회
     * @param   hqBrand
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "productClass/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result clsList(HqBrandVO hqBrand, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        List<HqClsVO> list = service.getClsList(hqBrand);
        
        return returnListJson(Status.OK, list, hqBrand);
    }
    
    /**
     * 분류 등록
     * @param   hqBrand
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "productClass/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result clsSave(@RequestBody HqClsVO[] HqClsVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.clsSave(HqClsVOs, sessionInfoVO);
        
        return returnJson(Status.OK, result);
    }
    
}
