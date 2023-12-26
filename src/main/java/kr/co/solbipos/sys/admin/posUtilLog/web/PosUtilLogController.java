package kr.co.solbipos.sys.admin.posUtilLog.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.sys.admin.posUtilLog.service.PosUtilLogService;
import kr.co.solbipos.sys.admin.posUtilLog.service.PosUtilLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : PosUtilLogController.java
 * @Description : 시스템관리 > 관리자기능 > 포스유틸사용로그
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.21  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2023.12.21
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/admin/posUtilLog")
public class PosUtilLogController {

    private final PosUtilLogService posUtilLogService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PosUtilLogController(PosUtilLogService posUtilLogService){
        this.posUtilLogService = posUtilLogService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value="/posUtilLog/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model){
        return "sys/admin/posUtilLog/posUtilLog";
    }

    /**
     * 포스유틸사용로그 - 조회
     *
     * @param posUtilLogVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2023.12.21
     */
    @RequestMapping(value = "/posUtilLog/getPosUtilLogList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosUtilLogList(HttpServletRequest request, HttpServletResponse response,
                                          Model model, PosUtilLogVO posUtilLogVO) {

        List<DefaultMap<String>> list = posUtilLogService.getPosUtilLogList(posUtilLogVO);

        return ReturnUtil.returnListJson(Status.OK, list, posUtilLogVO);
    }
}
