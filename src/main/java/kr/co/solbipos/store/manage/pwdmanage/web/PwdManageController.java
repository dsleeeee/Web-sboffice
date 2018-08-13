package kr.co.solbipos.store.manage.pwdmanage.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
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
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageService;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageVO;

/**
 * @Class Name : PwdManageController.java
 * @Description : 가맹점관리 > 매장관리 > 비밀번호 임의변경
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
@RequestMapping(value = "/store/manage/pwdManage")
public class PwdManageController {
    
    /** service */
    @Autowired
    PwdManageService pwdManageService;
    @Autowired
    MessageService messageService;
    @Autowired
    SessionService sessionService;
    
    /**
     * 비밀번호 임의변경 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/pwdManage/view.sb", method = RequestMethod.GET)
    public String pwdManageView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "store/manage/pwdManage/pwdManage";
    }
    
    /**
     * 비밀번호 임의변경 - 조회
     * @param   request
     * @param   response
     * @param   pwdManageVO
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/pwdManage/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPwdManageList(HttpServletRequest request, HttpServletResponse response,
            PwdManageVO pwdManageVO, Model model) {

        List<DefaultMap<String>> list = pwdManageService.getPwdManageList(pwdManageVO);

        return ReturnUtil.returnListJson(Status.OK, list, pwdManageVO);

    }
    
    /**
     * 비밀번호 임의변경 - 변경
     * @param   request
     * @param   response
     * @param   pwdManageVO
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/pwdManage/modify.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePassword(HttpServletRequest request,
            HttpServletResponse response, PwdManageVO pwdManageVO, Model model) {
        
        pwdManageVO.setRegId(sessionService.getSessionInfo().getUserId());
        pwdManageVO.setModId(sessionService.getSessionInfo().getUserId());
        
        // 패스워드 변경
        PwChgResult result = pwdManageService.modifyPwd(pwdManageVO);
        
        if (result == PwChgResult.NEW_PASSWORD_NOT_MATCH) {
            /** 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인 */
            return returnJson(Status.FAIL, "msg", messageService.get("login.pw.find.not.match"));
        } else if (result == PwChgResult.PASSWORD_NEW_OLD_MATH) {
            /** 변경 패스워드가 기존 비밀번호가 같은지 체크 */
            return returnJson(Status.FAIL, "msg", messageService.get("login.layer.pwchg.current"));
        } else if (result == PwChgResult.PASSWORD_REGEXP) {
            /** 패스워드 정책 체크 */
            return returnJson(Status.FAIL, "msg", messageService.get("login.pw.chg.regexp"));
        }
        
        return returnJson(Status.OK, result);
        
    }
    
}
