package kr.co.solbipos.adi.etc.kitchenmemo.web;

import kr.co.common.data.enums.CodeType;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.CodeException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.adi.etc.kitchenmemo.service.KitchenMemoService;
import kr.co.solbipos.adi.etc.kitchenmemo.service.KitchenMemoVO;
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
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;


/**
* @Class Name : KitchenMemoController.java
* @Description : 부가서비스 > 주방메모관리
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
@RequestMapping(value = "/adi/etc/kitchenMemo/kitchenMemo/")
public class KitchenMemoController {

    private final KitchenMemoService service;
    private final SessionService sessionService;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public KitchenMemoController(KitchenMemoService service, SessionService sessionService,
        MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.service = service;
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 부가서비스 > 주방메모관리
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String kitchenmemoList(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        String envstCd = "0024";
        String envstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, envstCd));
        String orgnFg = String.valueOf(sessionInfoVO.getOrgnFg());

        // 환경설정값 체크 : [0024] 본사통제구분-주방메모 확인 ( 1: 본사통제, 2:매장통제 )
        // 매장통제, 본사통제 둘다 메뉴 사용 가능하지만 null인 경우는 사용 불가능.
        // 해당 환경설정값이 없는 경우, 본사환경설정에서 환경설정 필요.
        // 본사통제인데 매장에서 접속시, 권한 오류
        // 본사통제/매장통제 모두 화면 보이도록 수정(본사통제인데 매장에서 접속해도 화면은 보임, 등록/수정은 불가) 2020.02.03_이다솜
        /*if("".equals(envstVal) || ("1".equals(envstVal) && sessionInfoVO.getOrgnFg() == OrgnFg.STORE )) {*/
        if("".equals(envstVal)) {
            throw new CodeException(CodeType.HQ_ENV, envstCd, "/error/envError.sb");
        } else{
            model.addAttribute("envstVal", envstVal);
            return "adi/etc/kitchenmemo/kitchenMemo";
        }
    }

    /**
     * 주방메모 조회
     * @param   kitchenMemoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.09
     */
    @RequestMapping(value = "getKitchenMemoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKitchenMemoList(KitchenMemoVO kitchenMemoVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = service.getKitchenMemoList(kitchenMemoVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kitchenMemoVO);
    }

    /**
     * 주방메모 저장
     * @param kitchenMemoVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "saveKitchenMemo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result kitchenmemoSave(@RequestBody KitchenMemoVO[] kitchenMemoVOs, HttpServletRequest request,
            HttpServletResponse response , Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.save(kitchenMemoVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
