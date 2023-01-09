package kr.co.solbipos.base.store.posfunc.web;

import com.nhncorp.lucy.security.xss.XssPreventer;
import kr.co.common.data.domain.CommonCodeVO;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.posfunc.service.PosFuncService;
import kr.co.solbipos.base.store.posfunc.service.PosFuncVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageService;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

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
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/store/posfunc")
public class PosFuncController {

    /** service */
    private final PosFuncService service;
    private final StoreManageService storeService;
    private final DayProdService dayProdService;
    private final SessionService sessionService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection*/
    @Autowired
    public PosFuncController(PosFuncService service, StoreManageService storeService, DayProdService dayProdService, SessionService sessionService, CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.service = service;
        this.storeService = storeService;
        this.dayProdService = dayProdService;
        this.sessionService = sessionService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

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
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 브랜드사용여부
            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
            // 사용자별 브랜드 콤보박스 조회
            DayProdVO dayProdVO = new DayProdVO();
            model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));
        } else {
            // 관리자 또는 총판은 매장브랜드 값이 없으므로 사용자별 브랜드 빈 콤보박스 셋팅
            model.addAttribute("userHqBrandCdComboList", CmmUtil.comboListAll());
        }

        return "base/store/posFunc/posFunc";
    }


    /**
     * 포스기능정의 화면 (매장포스기능정의)
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018. 07. 26.
     */
    @RequestMapping( value = "/use/storeView.sb", method = RequestMethod.GET)
    public String storeView(HttpServletRequest request, HttpServletResponse response, Model model) {

        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
            model.addAttribute("posLoginReconnect", request.getParameter("posLoginReconnect"));
        }

        return "base/store/posFunc/posFuncStore";
    }


    /**
     * 매장목록 조회
     * @param   storeManageVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/use/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(StoreManageVO storeManageVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 매장 목록 조회
        List<DefaultMap<String>> list = storeService.getStoreList(storeManageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeManageVO);
    }

    /**
     * 포스목록 조회
     * @param   storeManageVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/use/getPosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosList(StoreManageVO storeManageVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        PosFuncVO posFuncVO = new PosFuncVO();
        posFuncVO.setStoreCd(storeManageVO.getStoreCd());
        posFuncVO.setPosNo("01");

        // 포스 목록 조회
        List<DefaultMap<String>> posList = service.getPosList(posFuncVO);

        return returnListJson(Status.OK, posList);
    }

    /**
     * 포스기능목록 조회
     * @param   posFuncVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/use/getPosFuncList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosFuncList(PosFuncVO posFuncVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        // 포스 기능목록 조회
        List<DefaultMap<String>> list = service.getPosFuncList(posFuncVO);

        return returnListJson(Status.OK, list);
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
    public Result getPosConfDetail(PosFuncVO posFuncVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.getPosConfDetail(posFuncVO);

        return returnListJson(Status.OK, list, posFuncVO);
    }

    /**
     * 포스기능정의 저장
     * @param   posFuncVOs
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

    /**
     * 포스기능 복사
     * @param   posFuncVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/use/copyPosFunc.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyPosFunc(@RequestBody PosFuncVO posFuncVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.copyPosFunc(posFuncVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 포스기능키 목록 조회
     * @param   posFuncVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 10. 01.
     */
    @RequestMapping(value = "/use/getPosFuncKeyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosFuncKeyList(PosFuncVO posFuncVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 포스 기능키 목록 조회
        List<DefaultMap<String>> list = service.getPosFuncKeyList(posFuncVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, posFuncVO);
    }

    /**
     * 포스기능키 기존 설정 조회
     *
     * @param posFuncVO PosFuncVO
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/use/getFuncKeyXml.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFuncKeyXml(PosFuncVO posFuncVO, HttpServletRequest request,
        HttpSession session, Model model) {
        String xml = service.getFuncKeyXml(posFuncVO);
        return new Result(Status.OK, xml);
    }

    /**
     * 포스기능키 저장
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/use/saveFuncKey.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveFuncKey(HttpServletRequest request, HttpSession session, Model model) {

        Result result = new Result(Status.FAIL);
        try {

            SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
            // xml 파일 처리
            String xml =
                URLDecoder.decode(request.getParameter("xml"), "UTF-8").replace("\n", "&#xa;");
            // 매장코드 설정
            String storeCd = request.getParameter("storeCd");
            // 포스번호 설정
            String posNo = request.getParameter("posNo");
            // 설정구분 설정
            String fnKeyFg = request.getParameter("fnkeyFg");
            // 설정구분 설정
            String confgFg = request.getParameter("fnkeyFg");
            // 파라미터 셋팅
            PosFuncVO posFuncVO = new PosFuncVO();
            posFuncVO.setStoreCd(storeCd);
            posFuncVO.setPosNo(posNo);
            posFuncVO.setFnkeyFg(fnKeyFg);
            posFuncVO.setConfgFg(confgFg);
            posFuncVO.setXml(XssPreventer.unescape(xml));
            // 저장 수행
            result = service.saveFunckey(posFuncVO, sessionInfoVO);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 포스기능인증관리 화면
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018. 07. 26.
     */
    @RequestMapping(value = "/auth/posAuthView.sb", method = RequestMethod.GET)
    public String posFuncAuth(HttpServletRequest request, HttpServletResponse response,
        Model model) {

        return "base/store/posFunc/posFuncAuth";
    }

    /**
     * 포스기능 목록 조회
     * @param   posFuncVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/auth/getPosFuncList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBrandlist(PosFuncVO posFuncVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        // 포스기능구분 조회
        CommonCodeVO commonCodeVO = cmmCodeUtil.getCommCodeData("026");

        List<DefaultMap<String>> codeList = commonCodeVO.getCodeList();

        return returnListJson(Status.OK, codeList, posFuncVO);
    }


    /**
     * 포스기능 인증 목록 조회
     * @param   posFuncVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 08. 02
     */
    @RequestMapping(value = "/auth/getPosConfAuthDetail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosConfAuthDetail(PosFuncVO posFuncVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.getPosConfAuthDetail(posFuncVO);

        return returnListJson(Status.OK, list, posFuncVO);
    }

    /**
     * 포스기능 인증허용대상 조회
     * @param   posFuncVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 08. 02
     */
    @RequestMapping(value = "/auth/getAuthEmpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAuthEmpList(PosFuncVO posFuncVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.getAuthEmpList(posFuncVO);

        return returnListJson(Status.OK, list);
    }


    /**
     * 포스기능 인증관리 인증여부 저장
     * @param   posFuncVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 12. 10.
     */
    @RequestMapping(value = "/auth/savePosAuthConf.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePosAuthConf(@RequestBody PosFuncVO[] posFuncVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.savePosAuthConf(posFuncVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }


    /**
     * 포스기능 인증허용대상 저장
     * @param   posFuncVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/auth/saveAuthEmp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAuthEmp(@RequestBody PosFuncVO[] posFuncVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.saveAuthEmp(posFuncVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
