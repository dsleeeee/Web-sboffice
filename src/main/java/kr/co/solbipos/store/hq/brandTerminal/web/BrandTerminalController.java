package kr.co.solbipos.store.hq.brandTerminal.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.brandTerminal.service.BrandTerminalService;
import kr.co.solbipos.store.hq.brandTerminal.service.BrandTerminalVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : BrandTerminalController.java
 * @Description : 기초관리 > 본사정보관리 > 브랜드별 브랜드터미널관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.06  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.07.06
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */


@Controller
@RequestMapping(value = "/store/hq/brandTerminal/")
public class BrandTerminalController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** service */
    private final BrandTerminalService brandTerminalService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public BrandTerminalController(BrandTerminalService brandTerminalService, SessionService sessionService) {
        this.brandTerminalService = brandTerminalService;
        this.sessionService = sessionService;
    }

    /**
     * 브랜드터미널관리 - 화면 이동
     * @param request
     * @param response
     * @param model
     * @return String
     * @author 권지현
     * @since 2023.07.06
     */
    @RequestMapping(value = "brandTerminal/terminalView.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {

        List<DefaultMap<String>> vendorList = brandTerminalService.getVendorList();

        model.addAttribute("vendorList", convertToJson(vendorList) );

        return "store/hq/brandTerminal/brandTerminal";
    }

    /**
     * 브랜드 조회
     * @param brandTerminalVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "brandTerminal/getBrandList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBrandList(BrandTerminalVO brandTerminalVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> storeList = brandTerminalService.getBrandList(brandTerminalVO, sessionInfoVO);

        return returnListJson(Status.OK, storeList, brandTerminalVO);
    }

    /**
     * 브랜드터미널관리 
     * @param brandTerminalVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "brandTerminal/getTerminalList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTerminalList(BrandTerminalVO brandTerminalVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> posList = brandTerminalService.getTerminalList(brandTerminalVO, sessionInfoVO);

        return returnListJson(Status.OK, posList, brandTerminalVO);
    }

    /**
     * 터미널 정보 저장
     * @param brandTerminalVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "brandTerminal/saveTerminalInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTerminalInfo(@RequestBody BrandTerminalVO[] brandTerminalVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        try{
            result += brandTerminalService.saveTerminalInfo(brandTerminalVOs, sessionInfoVO);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return returnListJson(Status.OK, result);
    }

}
