package kr.co.solbipos.base.prod.kioskOption.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.kioskOption.service.KioskOptionService;
import kr.co.solbipos.base.prod.kioskOption.service.KioskOptionVO;
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

@Controller
@RequestMapping("/base/prod/kioskOption")
public class KioskOptionController {

    private final SessionService sessionService;
    private final KioskOptionService kioskOptionService;

    /**
     * Constructor Injection
     */
    @Autowired
    public KioskOptionController(SessionService sessionService, KioskOptionService kioskOptionService) {
        this.sessionService = sessionService;
        this.kioskOptionService = kioskOptionService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/kioskOption/list.sb", method = RequestMethod.GET)
    public String kioskOptionView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/prod/kioskOption/kioskOption";
    }

    /**
     * 상품목록 조회
     *
     * @param kioskOptionVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 08. 19.
     */
    @RequestMapping(value = "/kioskOption/getKioskOptionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskOptionList(KioskOptionVO kioskOptionVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskOptionService.getKioskOptionList(kioskOptionVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskOptionVO);
    }

    /**
     * 키오스크옵션 조회
     *
     * @param kioskOptionVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 08. 19.
     */
    @RequestMapping(value = "/kioskOption/getKioskOptionDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskOptionDetailList(KioskOptionVO kioskOptionVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskOptionService.getKioskOptionDetailList(kioskOptionVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskOptionVO);
    }

    /**
     * 키오스크옵션 삭제
     *
     * @param kioskOptionVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 08. 19.
     */
    @RequestMapping(value = "/kioskOption/getKioskOptionSaveDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskOptionSaveDelete(@RequestBody KioskOptionVO[] kioskOptionVOs, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskOptionService.getKioskOptionSaveDelete(kioskOptionVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크옵션 저장
     *
     * @param kioskOptionVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 08. 19.
     */
    @RequestMapping(value = "/kioskOption/getKioskOptionSaveUpdate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskOptionSaveUpdate(@RequestBody KioskOptionVO[] kioskOptionVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskOptionService.getKioskOptionSaveUpdate(kioskOptionVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크옵션 상품등록 팝업 - 상품목록 조회
     *
     * @param kioskOptionVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 08. 19.
     */
    @RequestMapping(value = "/kioskOptionProd/getKioskOptionProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskOptionProdList(KioskOptionVO kioskOptionVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskOptionService.getKioskOptionProdList(kioskOptionVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskOptionVO);
    }

    /**
     * 키오스크옵션 상품등록 팝업 - 표기순번 조회
     *
     * @param kioskOptionVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 08. 19.
     */
    @RequestMapping(value = "/kioskOptionProd/getKioskOptionProdDispSeq.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskOptionProdDispSeq(KioskOptionVO kioskOptionVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = kioskOptionService.getKioskOptionProdDispSeq(kioskOptionVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 키오스크옵션 상품등록 팝업 - 키오스크옵션 저장
     *
     * @param kioskOptionVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 08. 19.
     */
    @RequestMapping(value = "/kioskOptionProd/getKioskOptionProdSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskOptionProdSave(@RequestBody KioskOptionVO[] kioskOptionVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskOptionService.getKioskOptionProdSave(kioskOptionVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크옵션 옵션상품 매장적용
     *
     * @param kioskOptionVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2022. 01. 19.
     */
    @RequestMapping(value = "/kioskOption/saveStoreOptionProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreOptionProd(@RequestBody KioskOptionVO[] kioskOptionVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskOptionService.saveStoreOptionProd(kioskOptionVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}