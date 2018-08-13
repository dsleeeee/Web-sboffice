package kr.co.solbipos.sys.cd.systemcd.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import java.util.ArrayList;
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
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.cd.systemcd.service.SystemCdService;
import kr.co.solbipos.sys.cd.systemcd.service.SystemCdVO;

/**
 * @Class Name : SystemCdController.java
 * @Description : 시스템관리 > 코드관리 > 시스템 명칭관리
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
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sys/cd/systemCd")
public class SystemCdController {
    
    @Autowired
    SystemCdService systemCdService;
    @Autowired
    SessionService sessionService;
    
    /**
     * 시스템 명칭관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/systemCd/view.sb", method = RequestMethod.GET)
    public String systemCdView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "sys/cd/systemCd/systemCd";
    }
    
    /**
     * 시스템 명칭관리 - 조회
     * @param   request
     * @param   response
     * @param   systemCdVO
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/systemCd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNmcodeGrpCdList(HttpServletRequest request, HttpServletResponse response,
            SystemCdVO systemCdVO, Model model) {
        
        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>(); 
        // Parameter 값으로 대표/세부 명칭 코드목록을 조회 분기처리
        if ( "000".equals(systemCdVO.getNmcodeGrpCd()) ) {
            // 대표명칭 코드목록 조회
            list = systemCdService.getNmcodeGrpCdList(systemCdVO);
        } else {
            // 세부명칭 코드목록 조회
            list = systemCdService.getNmcodeCdList(systemCdVO);
        }
        
        
        return ReturnUtil.returnListJson(Status.OK, list, systemCdVO);
        
    }
    
    /**
     * 시스템 명칭관리 - 저장
     * @param   request
     * @param   response
     * @param   systemCdVO
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/systemCd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody SystemCdVO[] systemCdVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        int result = systemCdService.saveNmcodeCdList(systemCdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
    
    
}
