package kr.co.solbipos.store.storeMrpizza.storeMrpizzaBatchChange.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.store.storeMrpizza.storeMrpizzaBatchChange.service.StoreMrpizzaBatchChangeService;
import kr.co.solbipos.store.storeMrpizza.storeMrpizzaBatchChange.service.StoreMrpizzaBatchChangeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreMrpizzaBatchChangeController.java
 * @Description : 미스터피자 > 매장관리 > 매장정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.18  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value="/store/storeMrpizza/storeMrpizzaBatchChange/")
public class StoreMrpizzaBatchChangeController {

    private final StoreMrpizzaBatchChangeService storeMrpizzaBatchChangeService;
    private final SessionService sessionService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public StoreMrpizzaBatchChangeController(StoreMrpizzaBatchChangeService storeMrpizzaBatchChangeService, SessionService sessionService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.storeMrpizzaBatchChangeService = storeMrpizzaBatchChangeService;
        this.sessionService = sessionService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 매장정보일괄변경 -  화면 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2025.08.18
     */
    @RequestMapping(value = "storeMrpizzaBatchChange/view.sb", method = RequestMethod.GET)
    public String view(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        // 사용자별 코드별 공통코드 콤보박스 조회
        // 팀별
        List momsTeamComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "151");
        String momsTeamComboListAll = "";
        if (momsTeamComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsTeamComboListAll = convertToJson(list);
        } else {
            momsTeamComboListAll = cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsTeamComboList", momsTeamComboListAll);
        // 지역구분
        List momsAreaFgComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "153");
        String momsAreaFgComboListAll = "";
        if (momsAreaFgComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsAreaFgComboListAll = convertToJson(list);
        } else {
            momsAreaFgComboListAll = cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsAreaFgComboList", momsAreaFgComboListAll);
        // 상권
        List momsCommercialComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "154");
        String momsCommercialComboListAll = "";
        if (momsCommercialComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsCommercialComboListAll = convertToJson(list);
        } else {
            momsCommercialComboListAll = cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsCommercialComboList", momsCommercialComboListAll);
        // 점포유형
        List momsShopTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "155");
        String momsShopTypeComboListAll = "";
        if (momsShopTypeComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsShopTypeComboListAll = convertToJson(list);
        } else {
            momsShopTypeComboListAll = cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsShopTypeComboList", momsShopTypeComboListAll);

        // 전체 없는 콤보박스 데이터
        // 사용자별 코드별 공통코드 콤보박스 조회
        // 팀별
        if (momsTeamComboList.size() > 1) {
            momsTeamComboList.remove(0);
            String momsTeamComboListAll2 = cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.N);
            model.addAttribute("momsTeamComboList2", momsTeamComboListAll2);
            String momsTeamComboListAll3 = cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.SELECT);
            model.addAttribute("momsTeamComboList3", momsTeamComboListAll3);
        } else {
            model.addAttribute("momsTeamComboList2", momsTeamComboListAll);
            model.addAttribute("momsTeamComboList3", momsTeamComboListAll);
        }
        // 지역구분
        if (momsAreaFgComboList.size() > 1) {
            momsAreaFgComboList.remove(0);
            String momsAreaFgComboListAll2 = cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.N);
            model.addAttribute("momsAreaFgComboList2", momsAreaFgComboListAll2);
            String momsAreaFgComboListAll3 = cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.SELECT);
            model.addAttribute("momsAreaFgComboList3", momsAreaFgComboListAll3);
        } else {
            model.addAttribute("momsAreaFgComboList2", momsAreaFgComboListAll);
            model.addAttribute("momsAreaFgComboList3", momsAreaFgComboListAll);
        }
        // 상권
        if(momsCommercialComboList.size() > 1) {
            momsCommercialComboList.remove(0);
            String momsCommercialComboListAll2 = cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.N);
            model.addAttribute("momsCommercialComboList2", momsCommercialComboListAll2);
            String momsCommercialComboListAll3 = cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.SELECT);
            model.addAttribute("momsCommercialComboList3", momsCommercialComboListAll3);
        } else {
            model.addAttribute("momsCommercialComboList2", momsCommercialComboListAll);
            model.addAttribute("momsCommercialComboList3", momsCommercialComboListAll);
        }
        // 점포유형
        if(momsShopTypeComboList.size() > 1) {
            momsShopTypeComboList.remove(0);
            String momsShopTypeComboListAll2 = cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.N);
            model.addAttribute("momsShopTypeComboList2", momsShopTypeComboListAll2);
            String momsShopTypeComboListAll3 = cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.SELECT);
            model.addAttribute("momsShopTypeComboList3", momsShopTypeComboListAll3);
        } else {
            model.addAttribute("momsShopTypeComboList2", momsShopTypeComboListAll);
            model.addAttribute("momsShopTypeComboList3", momsShopTypeComboListAll);
        }

        return "store/storeMrpizza/storeMrpizzaBatchChange/storeMrpizzaBatchChangeTab";
    }

    /**
     * 매장목록 조회
     * @param   storeMrpizzaBatchChangeVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2025.08.18
     */
    @RequestMapping(value = "storeMrpizzaBatchChange/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = storeMrpizzaBatchChangeService.getStoreList(storeMrpizzaBatchChangeVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeMrpizzaBatchChangeVO);
    }

    /**
     * 매장정보 저장
     * @param   storeMrpizzaBatchChangeVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2025.08.18
     */
    @RequestMapping(value = "storeMrpizzaBatchChange/getStoreBatchChangeSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreBatchChangeSave(@RequestBody StoreMrpizzaBatchChangeVO[] storeMrpizzaBatchChangeVOs, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeMrpizzaBatchChangeService.getStoreBatchChangeSave(storeMrpizzaBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
    /**
     * 업로드시 임시테이블 삭제
     * @param   storeMrpizzaBatchChangeVO
     * @param   request
     * @return  Result
     * @author  김유승
     * @since   2025.08.18
     */
    @RequestMapping(value = "storeMrpizzaBatchChange/getStoreExcelUploadCheckDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreExcelUploadCheckDeleteAll(@RequestBody StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeMrpizzaBatchChangeService.getStoreExcelUploadCheckDeleteAll(storeMrpizzaBatchChangeVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 검증결과 삭제
     * @param   storeMrpizzaBatchChangeVOs
     * @param   request
     * @return  Result
     * @author  김유승
     * @since   2025.08.18
     */
    @RequestMapping(value = "storeMrpizzaBatchChange/getStoreExcelUploadCheckDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreExcelUploadCheckDelete(@RequestBody StoreMrpizzaBatchChangeVO[] storeMrpizzaBatchChangeVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeMrpizzaBatchChangeService.getStoreExcelUploadCheckDelete(storeMrpizzaBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /** 업로드시 임시테이블 저장 */
    @RequestMapping(value = "storeMrpizzaBatchChange/getStoreExcelUploadCheckSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreExcelUploadCheckSave(@RequestBody StoreMrpizzaBatchChangeVO[] storeMrpizzaBatchChangeVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeMrpizzaBatchChangeService.getStoreExcelUploadCheckSave(storeMrpizzaBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /** 검증결과 조회 */
    @RequestMapping(value = "storeMrpizzaBatchChange/getStoreExcelUploadCheckList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExcelList(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeMrpizzaBatchChangeService.getStoreExcelUploadCheckList(storeMrpizzaBatchChangeVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeMrpizzaBatchChangeVO);
    }


    /** 엑셀 저장 */
    @RequestMapping(value = "storeMrpizzaBatchChange/getSimpleSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSimpleSave(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeMrpizzaBatchChangeService.getSimpleSave(storeMrpizzaBatchChangeVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /** 검증결과 조회 */
    @RequestMapping(value = "storeMrpizzaBatchChange/getTmpStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTmpStoreList(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeMrpizzaBatchChangeService.getTmpStoreList(storeMrpizzaBatchChangeVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeMrpizzaBatchChangeVO);
    }

    /** 변경된 값만 임시테이블 저장 */
    @RequestMapping(value = "storeMrpizzaBatchChange/getDiffValSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDiffValSave(@RequestBody StoreMrpizzaBatchChangeVO[] storeMrpizzaBatchChangeVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeMrpizzaBatchChangeService.getDiffValSave(storeMrpizzaBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }
}
