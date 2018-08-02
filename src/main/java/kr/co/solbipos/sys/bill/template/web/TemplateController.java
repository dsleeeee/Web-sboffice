package kr.co.solbipos.sys.bill.template.web;

import static kr.co.common.utils.spring.StringUtil.convertToJson;
import java.util.ArrayList;
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
import kr.co.solbipos.sys.bill.template.service.TemplateService;
import kr.co.solbipos.sys.bill.template.service.TemplateVO;

/**
 * @Class Name : TemplateController.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력물 샘플
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sys/bill/template")
public class TemplateController {
    
    @Autowired
    TemplateService templateService;
    @Autowired
    SessionService sessionService;
    
    /**
     * 출력물 샘플 - 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return String
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/template/view.sb", method = RequestMethod.GET)
    public String templateView(HttpServletRequest request, HttpServletResponse response, Model model) {
        
        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        TemplateVO params = new TemplateVO();
        // 출력물종류 목록 조회
        list = templateService.getPrintTypeList(params);
        model.addAttribute("listPrintType", convertToJson(list));
        
        return "sys/bill/template/template";
    }
    
    /**
     * 출력물 샘플 - 출력물코드 목록 조회
     * 
     * @param request
     * @param response
     * @param templateVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/code/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrintCodeList(HttpServletRequest request, HttpServletResponse response,
            TemplateVO templateVO, Model model) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 출력물코드 목록 조회
        list = templateService.getPrintCodeList(templateVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, templateVO);

    }
    
    /**
     * 출력물 샘플 - 출력물템플릿 목록 조회
     * 
     * @param request
     * @param response
     * @param templateVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/item/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTemplateList(HttpServletRequest request, HttpServletResponse response,
            TemplateVO templateVO, Model model) {
        
        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 출력물코드 목록 조회
        list = templateService.getTemplateList(templateVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, templateVO);
        
    }
    
}
