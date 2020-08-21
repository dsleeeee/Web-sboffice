package kr.co.solbipos.kds.anals.chart.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.kds.anals.chart.service.KdsService;
import kr.co.solbipos.kds.anals.chart.service.KdsVO;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * KdsDayController.java
 */
@Controller
@RequestMapping(value = "/kds/anals/chart/")
public class KdsController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KdsService kdsService;
    private final RegistService registService;
    private final SessionService sessionService;
    private final MessageService messageService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public KdsController(KdsService kdsService, RegistService registService, SessionService sessionService,
                         MessageService messageService,
                         CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.kdsService = kdsService;
        this.registService = registService;
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "day/list.sb", method = RequestMethod.GET)
    public String kdsDayList(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 등록 매장 조회
        List regstrStoreList = registService.getRegistStore(sessionInfoVO);
        // 등록 매장 전체 포함
        String regstrStoreListAll = cmmCodeUtil.assmblObj(regstrStoreList, "name", "value", UseYn.SELECT);

        // 본사일 경우 해당 본사의 기본매장(코드)을 조회 해야 함.
        // [보나비]의 경우 기본매장코드를 사용하여
        // 회원등록 매장이 기본매장일 경우 후불회원 적용매장을 등록한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        model.addAttribute("regstrStoreList", regstrStoreListAll);
        return "kds/anals/view/kdsDay";
    }

    /**
     * 조회
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "day/getKdsDay.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKdsDay(KdsVO kdsVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kdsService.getKdsDay(kdsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "dayTime/list.sb", method = RequestMethod.GET)
    public String kdsDayTimeList(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 등록 매장 조회
        List regstrStoreList = registService.getRegistStore(sessionInfoVO);
        // 등록 매장 전체 포함
        String regstrStoreListAll = cmmCodeUtil.assmblObj(regstrStoreList, "name", "value", UseYn.SELECT);

        // 본사일 경우 해당 본사의 기본매장(코드)을 조회 해야 함.
        // [보나비]의 경우 기본매장코드를 사용하여
        // 회원등록 매장이 기본매장일 경우 후불회원 적용매장을 등록한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        model.addAttribute("regstrStoreList", regstrStoreListAll);
        return "kds/anals/view/kdsDayTime";
    }

    /**
     * 조회
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "dayTime/getKdsDayTime.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKdsDayTime(KdsVO kdsVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kdsService.getKdsDayTime(kdsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 차트조회
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "dayTime/getKdsDayTimeChart.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKdsDayTimeChart(KdsVO kdsVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kdsService.getKdsDayTimeChart(kdsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "dayProd/list.sb", method = RequestMethod.GET)
    public String kdsDayProdList(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 등록 매장 조회
        List regstrStoreList = registService.getRegistStore(sessionInfoVO);
        // 등록 매장 전체 포함
        String regstrStoreListAll = cmmCodeUtil.assmblObj(regstrStoreList, "name", "value", UseYn.SELECT);

        // 본사일 경우 해당 본사의 기본매장(코드)을 조회 해야 함.
        // [보나비]의 경우 기본매장코드를 사용하여
        // 회원등록 매장이 기본매장일 경우 후불회원 적용매장을 등록한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        model.addAttribute("regstrStoreList", regstrStoreListAll);
        return "kds/anals/view/kdsDayProd";
    }

    /**
     * 조회
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "dayProd/getKdsDayProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKdsDayProd(KdsVO kdsVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kdsService.getKdsDayProd(kdsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "month/list.sb", method = RequestMethod.GET)
    public String kdsMonthList(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 등록 매장 조회
        List regstrStoreList = registService.getRegistStore(sessionInfoVO);
        // 등록 매장 전체 포함
        String regstrStoreListAll = cmmCodeUtil.assmblObj(regstrStoreList, "name", "value", UseYn.SELECT);

        // 본사일 경우 해당 본사의 기본매장(코드)을 조회 해야 함.
        // [보나비]의 경우 기본매장코드를 사용하여
        // 회원등록 매장이 기본매장일 경우 후불회원 적용매장을 등록한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        model.addAttribute("regstrStoreList", regstrStoreListAll);
        return "kds/anals/view/kdsMonth";
    }

    /**
     * 조회
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "month/getKdsMonth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKdsMonth(KdsVO kdsVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kdsService.getKdsMonth(kdsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "dayProdTime/list.sb", method = RequestMethod.GET)
    public String kdsDayProdTimeList(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 등록 매장 조회
        List regstrStoreList = registService.getRegistStore(sessionInfoVO);
        // 등록 매장 전체 포함
        String regstrStoreListAll = cmmCodeUtil.assmblObj(regstrStoreList, "name", "value", UseYn.SELECT);

        // 본사일 경우 해당 본사의 기본매장(코드)을 조회 해야 함.
        // [보나비]의 경우 기본매장코드를 사용하여
        // 회원등록 매장이 기본매장일 경우 후불회원 적용매장을 등록한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        model.addAttribute("regstrStoreList", regstrStoreListAll);
        return "kds/anals/view/kdsDayProdTime";
    }

    /**
     * 조회
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "dayProdTime/getKdsDayProdTime.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKdsDayProdTime(KdsVO kdsVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kdsService.getKdsDayProdTime(kdsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 차트조회
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "dayProdTime/getKdsDayProdTimeChart.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKdsDayProdTimeChart(KdsVO kdsVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kdsService.getKdsDayProdTimeChart(kdsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "store/list.sb", method = RequestMethod.GET)
    public String kdsStoreList(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 등록 매장 조회
        List regstrStoreList = registService.getRegistStore(sessionInfoVO);
        // 등록 매장 전체 포함
        String regstrStoreListAll = cmmCodeUtil.assmblObj(regstrStoreList, "name", "value", UseYn.SELECT);

        // 본사일 경우 해당 본사의 기본매장(코드)을 조회 해야 함.
        // [보나비]의 경우 기본매장코드를 사용하여
        // 회원등록 매장이 기본매장일 경우 후불회원 적용매장을 등록한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        model.addAttribute("regstrStoreList", regstrStoreListAll);
        return "kds/anals/view/kdsDayStore";
    }

    /**
     * 조회
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "store/getKdsStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKdsStore(KdsVO kdsVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kdsService.getKdsStore(kdsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "service/list.sb", method = RequestMethod.GET)
    public String kdsServiceTimeList(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 등록 매장 조회
        List regstrStoreList = registService.getRegistStore(sessionInfoVO);
        // 등록 매장 전체 포함
        String regstrStoreListAll = cmmCodeUtil.assmblObj(regstrStoreList, "name", "value", UseYn.SELECT);

        // 본사일 경우 해당 본사의 기본매장(코드)을 조회 해야 함.
        // [보나비]의 경우 기본매장코드를 사용하여
        // 회원등록 매장이 기본매장일 경우 후불회원 적용매장을 등록한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        model.addAttribute("regstrStoreList", regstrStoreListAll);
        return "kds/anals/view/kdsServiceTime";
    }

    /**
     * 조회
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "service/getKdsServiceTime.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKdsServiceTime(KdsVO kdsVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kdsService.getKdsServiceTime(kdsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
}

