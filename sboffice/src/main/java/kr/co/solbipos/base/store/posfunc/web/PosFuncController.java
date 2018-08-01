package kr.co.solbipos.base.store.posfunc.web;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;
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
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.posfunc.service.PosFuncService;
import kr.co.solbipos.base.store.posfunc.service.PosFuncVO;

/**
 * @Class Name : PosFuncController.java
 * @Description : 기초관리 > 매장관리 > 포스기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.07.26  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.26
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/store/posfunc")
public class PosFuncController {

    /** service */
    @Autowired
    PosFuncService service;
    @Autowired
    SessionService sessionService;
    @Autowired
    CmmCodeUtil cmmCodeUtil;

    /**
     * 포스기능정의 화면
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018. 07. 26.
     */
    @RequestMapping(value = "/use/view.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, 
            Model model) {
        
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        
        PosFuncVO posFuncVO = new PosFuncVO();
        posFuncVO.setStoreCd(sessionInfoVO.getOrgnCd());
        posFuncVO.setPosNo("01");

        // 포스 목록 조회
        List<DefaultMap<String>> posList = service.getPosList(posFuncVO);
        // 포스 기능목록 조회
        List<DefaultMap<String>> funcList = service.getPosFuncList(posFuncVO);
        
        model.addAttribute("posList", cmmCodeUtil.assmblObj(posList,"posNm", "posNo", UseYn.Y));
        model.addAttribute("posTotList", cmmCodeUtil.assmblObj(posList,"posTotNm", "posNo", UseYn.N));
        model.addAttribute("funcList", convertToJson(funcList));
        
        return "base/store/posFunc/posFunc";
    }
    
    /**
     * 포스기능정의 상세
     * @param   posFuncVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/use/getPosConfDetail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBrandlist(PosFuncVO posFuncVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        System.out.println("======== FnkeyFg : " + posFuncVO.getFnkeyFg());
        
        List<DefaultMap<String>> list = service.getPosConfDetail(posFuncVO);
        
        return returnListJson(Status.OK, list, posFuncVO);
    }
    
    /**
     * 포스기능정의 저장
     * @param   hqBrand
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/use/savePosConf.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePosConf(@RequestBody PosFuncVO[] posFuncVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        int result = service.savePosConf(posFuncVOs, sessionInfoVO);
        
        return returnListJson(Status.OK, result);
    }
}
