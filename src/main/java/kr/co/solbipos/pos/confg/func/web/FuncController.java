package kr.co.solbipos.pos.confg.func.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.solbipos.pos.confg.func.service.FuncStoreVO;
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
import kr.co.solbipos.pos.confg.func.service.FuncService;
import kr.co.solbipos.pos.confg.func.service.FuncVO;

/**
 * @Class Name : FuncController.java
 * @Description : 포스관리 > POS 설정관리 > POS 기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.01  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
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
        model.addAttribute("fnkeyFgList", cmmCodeUtil.getCommCodeAll("026"));

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
     * @param funcVOs
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

    /**
     * 포스기능 등록/미등록 매장 조회
     *
     * @param funcStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getFuncStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFuncStoreList(FuncStoreVO funcStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        Map<String, Object> result = service.getFunStoreList(funcStoreVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 포스기능 등록매장 등록 및 삭제
     *
     * @param funcStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "saveFuncStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveFuncStore(@RequestBody FuncStoreVO[] funcStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int regCnt = service.saveFuncStore(funcStoreVO, sessionInfoVO);

        return returnListJson(Status.OK, regCnt);
    }

}
