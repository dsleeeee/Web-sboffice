package kr.co.solbipos.adi.etc.cd.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.etc.cd.service.CdService;
import kr.co.solbipos.adi.etc.cd.service.CdVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : SystemCdController.java
 * @Description : 부가서비스 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.13  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/adi/etc/cd")
public class CdController {

    // Constructor Injection
    private final CdService cdService;
    private final SessionService sessionService;

    @Autowired
    public CdController(CdService cdService, SessionService sessionService) {
        this.cdService = cdService;
        this.sessionService = sessionService;
    }

    /**
     * 시스템 명칭관리 - 페이지 이동
     * @param   request - HttpServletRequest
     * @param   response - HttpServletResponse
     * @param   model - Model
     * @return  String
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/cd/view.sb", method = RequestMethod.GET)
    public String cdView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "adi/etc/cd/cd";
    }
    
    /**
     * 시스템 명칭관리 - 조회
     * @param   request - HttpServletRequest
     * @param   response - HttpServeltResponse
     * @param   cdVO - CdVO
     * @param   model - Model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/cd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNmcodeGrpCdList(HttpServletRequest request, HttpServletResponse response,
            CdVO cdVO, Model model) {

        // 세션정보 설정
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        cdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            cdVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            cdVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>(); 
        // Parameter 값으로 대표/세부 명칭 코드목록을 조회 분기처리
        if ( "000".equals(cdVO.getNmcodeGrpCd()) ) {
            // 대표명칭 코드목록 조회
            list = cdService.getNmcodeGrpCdList(cdVO);
        } else {
            // 세부명칭 코드목록 조회
            list = cdService.getNmcodeCdList(cdVO);
        }

        return ReturnUtil.returnListJson(Status.OK, list, cdVO);
        
    }
    
    /**
     * 시스템 명칭관리 - 저장
     * @param   cdVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/cd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody CdVO[] cdVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        int result = cdService.saveNmcodeCdList(cdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
    
    
}
