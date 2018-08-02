package kr.co.solbipos.sys.cd.envconfig.web;

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
import kr.co.solbipos.sys.cd.envconfig.service.EnvConfigService;
import kr.co.solbipos.sys.cd.envconfig.service.EnvstDtlVO;
import kr.co.solbipos.sys.cd.envconfig.service.EnvstVO;

/**
 * @Class Name : EnvConfigController.java
 * @Description : 시스템관리 > 코드관리 > 환경설정관리
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
@RequestMapping(value = "/sys/cd/envConfig")
public class EnvConfigController {
    
    @Autowired
    EnvConfigService envConfigService;
    @Autowired
    SessionService sessionService;
    
    /**
     * 환경설정관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/envConfig/view.sb", method = RequestMethod.GET)
    public String envConfigView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "sys/cd/envConfig/envConfig";
    }
    
    /**
     * 환경설정관리 - 대표명칭 조회
     * @param   request
     * @param   response
     * @param   envstVO
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/envConfig/envst/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvstList(HttpServletRequest request, HttpServletResponse response,
            EnvstVO envstVO, Model model) {
        
        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>(); 
        // 대표명칭 코드목록 조회
        list = envConfigService.getEnvstList(envstVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, envstVO);
        
    }
    
    /**
     * 환경설정관리 - 대표명칭 저장
     * @param   request
     * @param   response
     * @param   envstVOs
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/envConfig/envst/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveEnvstList(@RequestBody EnvstVO[] envstVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        int result = envConfigService.saveEnvstList(envstVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
    
    /**
     * 환경설정관리 - 세부명칭 조회
     * @param   request
     * @param   response
     * @param   envstDtlVO
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/envConfig/envstDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEnvstDtlList(HttpServletRequest request, HttpServletResponse response,
            EnvstDtlVO envstDtlVO, Model model) {
        
        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>(); 
        // 세부명칭 코드목록 조회
        list = envConfigService.getEnvstDtlList(envstDtlVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, envstDtlVO);
        
    }
    
    /**
     * 환경설정관리 - 세부명칭 저장
     * @param   request
     * @param   response
     * @param   envstVOs
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/envConfig/envstDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveEnvstDtlList(@RequestBody EnvstDtlVO[] envstDtlVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        int result = envConfigService.saveEnvstDtlList(envstDtlVOs, sessionInfoVO);
        
        return returnJson(Status.OK, result);
    }
    
    
}
