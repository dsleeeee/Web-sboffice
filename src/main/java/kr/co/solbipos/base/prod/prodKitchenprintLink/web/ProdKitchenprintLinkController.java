package kr.co.solbipos.base.prod.prodKitchenprintLink.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodKitchenprintLink.service.ProdKitchenprintLinkService;
import kr.co.solbipos.base.prod.prodKitchenprintLink.service.ProdKitchenprintLinkVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
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
import static kr.co.common.utils.spring.StringUtil.convertToJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/base/prod/prodKitchenprintLink")
public class ProdKitchenprintLinkController {

    private final SessionService sessionService;
    private final StoreTypeService storeTypeService;
    private final ProdKitchenprintLinkService prodKitchenprintLinkService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdKitchenprintLinkController(SessionService sessionService, StoreTypeService storeTypeService, ProdKitchenprintLinkService prodKitchenprintLinkService) {
        this.sessionService = sessionService;
        this.storeTypeService = storeTypeService;
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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("brandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        // 메뉴그룹조회(콤보박스용)
        model.addAttribute("storeGroupList", convertToJson(storeTypeService.getStoreGroupCombo(storeTypeVO, sessionInfoVO)));

        return "base/prod/prodKitchenprintLink/kitchenprintTab";
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


    /**
     * 프린터그룹 조회
     *
     * @param prodKitchenprintLinkVO HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/printerGroup/getPrinterGroupList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrinterGroupList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodKitchenprintLinkService.getPrinterGroupList(prodKitchenprintLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodKitchenprintLinkVO);
    }

    /**
     * 프린터그룹 저장
     *
     * @param prodKitchenprintLinkVOs HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/printerGroup/savePrinterGroup.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePrinterGroup(@RequestBody ProdKitchenprintLinkVO[] prodKitchenprintLinkVOs, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodKitchenprintLinkService.savePrinterGroup(prodKitchenprintLinkVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 매핑상품 조회
     *
     * @param prodKitchenprintLinkVO HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/printerGroup/getProdMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdMapping(ProdKitchenprintLinkVO prodKitchenprintLinkVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodKitchenprintLinkService.getProdMapping(prodKitchenprintLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodKitchenprintLinkVO);
    }


    /**
     * 상품 조회
     *
     * @param prodKitchenprintLinkVO HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/printerGroup/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getGroupProdList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodKitchenprintLinkService.getGroupProdList(prodKitchenprintLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodKitchenprintLinkVO);
    }


    /**
     * 상품 매핑 저장
     *
     * @param prodKitchenprintLinkVOs HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/printerGroup/saveProdMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProdMapping(@RequestBody ProdKitchenprintLinkVO[] prodKitchenprintLinkVOs, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodKitchenprintLinkService.saveProdMapping(prodKitchenprintLinkVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }


    /**
     * 매핑프린터 조회
     *
     * @param prodKitchenprintLinkVO HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/printerGroup/getPrinterMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrinterMapping(ProdKitchenprintLinkVO prodKitchenprintLinkVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodKitchenprintLinkService.getPrinterMapping(prodKitchenprintLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodKitchenprintLinkVO);
    }


    /**
     * 프린터 조회
     *
     * @param prodKitchenprintLinkVO HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/printerGroup/getPrinterList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrinterList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodKitchenprintLinkService.getPrinterList(prodKitchenprintLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodKitchenprintLinkVO);
    }


    /**
     * 프린터 매핑 저장
     *
     * @param prodKitchenprintLinkVOs HttpServletRequest
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/printerGroup/savePrinterMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePrinterMapping(@RequestBody ProdKitchenprintLinkVO[] prodKitchenprintLinkVOs, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodKitchenprintLinkService.savePrinterMapping(prodKitchenprintLinkVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

}