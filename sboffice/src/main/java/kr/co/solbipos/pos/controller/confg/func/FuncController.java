package kr.co.solbipos.pos.controller.confg.func;

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
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.pos.domain.confg.func.FuncVO;
import kr.co.solbipos.pos.service.confg.func.FuncService;


/**
 * 포스관리 > POS 설정관리 > POS 기능정의
 *
 * @author 김지은
 */
@Controller
@RequestMapping(value = "/pos/confg/func/func")
public class FuncController {

    @Autowired
    FuncService service;

    @Autowired
    SessionService sessionService;

    @Autowired
    CmmCodeUtil cmmCodeUtil;

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        // 기능구분 조회
        model.addAttribute("fnkeyFgList", cmmCodeUtil.getCommCodeAll("034"));

        return "pos/confg/func/func";
    }

    /**
     * 기능구분 상세 조회
     *
     * @param funcVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "funcList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(FuncVO funcVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.list(funcVO);

        return returnListJson(Status.OK, list, funcVO);
    }

    /**
     * 기능구분상세 저장
     *
     * @param funcVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody FuncVO[] funcVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.save(funcVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }


}
