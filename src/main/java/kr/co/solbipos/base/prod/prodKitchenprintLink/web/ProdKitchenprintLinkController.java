package kr.co.solbipos.base.prod.prodKitchenprintLink.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodKitchenprintLink.service.ProdKitchenprintLinkService;
import kr.co.solbipos.base.prod.prodKitchenprintLink.service.ProdKitchenprintLinkVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/base/prod/prodKitchenprintLink")
public class ProdKitchenprintLinkController {

    private final SessionService sessionService;
    private final ProdKitchenprintLinkService prodKitchenprintLinkService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdKitchenprintLinkController(SessionService sessionService, ProdKitchenprintLinkService prodKitchenprintLinkService) {
        this.sessionService = sessionService;
        this.prodKitchenprintLinkService = prodKitchenprintLinkService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String prodKitchenprintLinkView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/prod/prodKitchenprintLink/prodKitchenprintLink";
    }

    /**
     * 상품조회
     *
     * @param prodKitchenprintLinkVO HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/prodKitchenprintLink/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodKitchenprintLinkService.getProdList(prodKitchenprintLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodKitchenprintLinkVO);
    }

    /**
     * 연결된 프린터 조회
     *
     * @param prodKitchenprintLinkVO HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/prodKitchenprintLinked/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLinkedList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodKitchenprintLinkService.getLinkedList(prodKitchenprintLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodKitchenprintLinkVO);
    }

    /**
     * 연결된 프린터 연결 해제
     *
     * @param prodKitchenprintLinkVO HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/prodKitchenprintLinked/unlink.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result unlinkPrter(@RequestBody ProdKitchenprintLinkVO[] prodKitchenprintLinkVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodKitchenprintLinkService.unlinkPrter(prodKitchenprintLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 안연결된 프린터 조회
     *
     * @param prodKitchenprintLinkVO HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/prodKitchenprintUnlink/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUnlinkList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodKitchenprintLinkService.getUnlinkList(prodKitchenprintLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodKitchenprintLinkVO);
    }


    /**
     * 안연결된 프린터 연결
     *
     * @param prodKitchenprintLinkVO HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/prodKitchenprintUnlink/linked.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result linkedPrter(@RequestBody ProdKitchenprintLinkVO[] prodKitchenprintLinkVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodKitchenprintLinkService.linkedPrter(prodKitchenprintLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

}