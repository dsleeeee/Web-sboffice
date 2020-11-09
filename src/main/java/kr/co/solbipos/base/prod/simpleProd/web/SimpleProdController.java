package kr.co.solbipos.base.prod.simpleProd.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.simpleProd.service.SimpleProdService;
import kr.co.solbipos.base.prod.simpleProd.service.SimpleProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.PriceEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

@Controller
@RequestMapping("/base/prod/simpleProd")
public class SimpleProdController {

    private final SessionService sessionService;
    private final SimpleProdService simpleProdService;
    private final CmmEnvUtil cmmEnvUtil;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public SimpleProdController(SessionService sessionService, SimpleProdService simpleProdService, CmmEnvUtil cmmEnvUtil, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.simpleProdService = simpleProdService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/simpleProd/list.sb", method = RequestMethod.GET)
    public String simpleProdView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 상품등록 본사 통제여부
        ProdEnvFg prodEnvstVal = ProdEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0020"));

        // 상품코드 채번방식
        ProdNoEnvFg prodNoEnvFg;
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0028"));
        }else{
            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0028"));
        }

        model.addAttribute("prodEnvstVal", prodEnvstVal);
        model.addAttribute("prodNoEnvFg", prodNoEnvFg);


        // 거래처 콤보 조회
        List vendrComboList = simpleProdService.vendrComboList(sessionInfoVO);
        String vendrComboListAll = "";
        if (vendrComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> m = new HashMap<>();
                m.put("name", "선택");
                m.put("value", "");
                list.add(m);
            vendrComboListAll = convertToJson(list);
        } else {
            // 거래처 선택 포함
            vendrComboListAll = cmmCodeUtil.assmblObj(vendrComboList, "name", "value", UseYn.SELECT);
        }
        model.addAttribute("vendrComboList", vendrComboListAll);
//        System.out.println("vendrComboList : "+vendrComboListAll);

        return "base/prod/simpleProd/simpleProd";
    }

    /**
     * 검증결과 전체 삭제
     *
     * @param simpleProdVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 08. 26.
     */
    @RequestMapping(value = "/simpleProd/getSimpleProdCheckDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSimpleProdCheckDeleteAll(@RequestBody SimpleProdVO simpleProdVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = simpleProdService.getSimpleProdCheckDeleteAll(simpleProdVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 검증결과 저장
     *
     * @param simpleProdVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 08. 26.
     */
    @RequestMapping(value = "/simpleProd/getSimpleProdCheckSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSimpleProdCheckSave(@RequestBody SimpleProdVO[] simpleProdVOs, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = simpleProdService.getSimpleProdCheckSave(simpleProdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 검증결과 조회
     *
     * @param simpleProdVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 08. 26.
     */
    @RequestMapping(value = "/simpleProd/getSimpleProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSimpleProdList(SimpleProdVO simpleProdVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = simpleProdService.getSimpleProdList(simpleProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, simpleProdVO);
    }

    /**
     * 상품 저장
     *
     * @param simpleProdVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 09. 01.
     */
    @RequestMapping(value = "/simpleProd/getSimpleProdSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSimpleProdSave(@RequestBody SimpleProdVO[] simpleProdVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = simpleProdService.getSimpleProdSave(simpleProdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}