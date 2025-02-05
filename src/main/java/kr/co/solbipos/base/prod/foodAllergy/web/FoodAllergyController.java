package kr.co.solbipos.base.prod.foodAllergy.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.foodAllergy.service.FoodAllergyService;
import kr.co.solbipos.base.prod.foodAllergy.service.FoodAllergyVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : FoodAllergyController.java
 * @Description : 기초관리 > 상품관리 > 식품 알레르기 정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.10.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2020.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/foodAllergy")
public class FoodAllergyController {

    private final SessionService sessionService;
    private final FoodAllergyService foodAllergyService;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public FoodAllergyController(SessionService sessionService, FoodAllergyService foodAllergyService, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.foodAllergyService = foodAllergyService;
        this.dayProdService = dayProdService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/foodAllergy/list.sb", method = RequestMethod.GET)
    public String foodAllergyView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        return "base/prod/foodAllergy/foodAllergy";
    }

    /**
     * 식품 알레르기 정보관리 조회
     *
     * @param foodAllergyVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 10. 06.
     */
    @RequestMapping(value = "/foodAllergy/getFoodAllergyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFoodAllergyList(FoodAllergyVO foodAllergyVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = foodAllergyService.getFoodAllergyList(foodAllergyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, foodAllergyVO);
    }

    /**
     * 브랜드 콤보박스 리스트 조회
     *
     * @param foodAllergyVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021. 03. 22.
     */
    @RequestMapping(value = "/foodAllergy/getBrandComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBrandComboList(FoodAllergyVO foodAllergyVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        foodAllergyVO.setHqOfficeCd(request.getParameter("hqOfficeCd"));

        List<DefaultMap<Object>> result = foodAllergyService.getBrandComboList(foodAllergyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, foodAllergyVO);
    }

    /**
     * 식품 알레르기 정보관리 저장
     *
     * @param foodAllergyVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 10. 06.
     */
    @RequestMapping(value = "/foodAllergy/getFoodAllergySave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFoodAllergySave(@RequestBody FoodAllergyVO[] foodAllergyVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = foodAllergyService.getFoodAllergySave(foodAllergyVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 알레르기-상품 조회
     *
     * @param foodAllergyVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 10. 06.
     */
    @RequestMapping(value = "/foodAllergy/getFoodAllergyDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFoodAllergyDetailList(FoodAllergyVO foodAllergyVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = foodAllergyService.getFoodAllergyDetailList(foodAllergyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, foodAllergyVO);
    }

    /**
     * 알레르기-상품 등록 팝업 - 상품조회
     *
     * @param foodAllergyVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 10. 06.
     */
    @RequestMapping(value = "/foodAllergy/getFoodAllergyProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFoodAllergyProdList(FoodAllergyVO foodAllergyVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = foodAllergyService.getFoodAllergyProdList(foodAllergyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, foodAllergyVO);
    }

    /**
     * 알레르기-상품 등록 팝업 - 재료-상품 저장
     *
     * @param foodAllergyVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 10. 06.
     */
    @RequestMapping(value = "/foodAllergy/getFoodAllergyDetailSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFoodAllergyDetailSave(@RequestBody FoodAllergyVO[] foodAllergyVOs, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = foodAllergyService.getFoodAllergyDetailSave(foodAllergyVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}