package kr.co.solbipos.base.prod.platformProdNmReg.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.platformProdNmReg.service.PlatformProdNmRegService;
import kr.co.solbipos.base.prod.platformProdNmReg.service.PlatformProdNmRegVO;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
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

/**
 * @Class Name : PlatformProdNmRegController.java
 * @Description : 기초관리 > 상품관리2 > 플랫폼 상품명 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.21  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.21
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/platformProdNmReg")
public class PlatformProdNmRegController {

    private final SessionService sessionService;
    private final DayProdService dayProdService;
    private final PlatformProdNmRegService platformProdNmRegService;

    @Autowired
    public PlatformProdNmRegController(SessionService sessionService, PlatformProdNmRegService platformProdNmRegService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.platformProdNmRegService = platformProdNmRegService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

        return "base/prod/platformProdNmReg/platformProdNmReg";
    }

    /**
     * 플랫폼 상품명 등록 리스트 조회
     *
     * @param platformProdNmRegVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPlatformProdNmRegList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPlatformProdNmRegList(PlatformProdNmRegVO platformProdNmRegVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = platformProdNmRegService.getPlatformProdNmRegList(platformProdNmRegVO, sessionInfoVO);

        return returnListJson(Status.OK, list, platformProdNmRegVO);
    }

    /**
     * 플랫폼 상품명 등록 저장
     *
     * @param platformProdNmRegVOs
     * @param request
     * @return
     */
    @RequestMapping(value = "/savePlatformProdNm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePlatformProdNm(@RequestBody PlatformProdNmRegVO[] platformProdNmRegVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = platformProdNmRegService.savePlatformProdNm(platformProdNmRegVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

}
