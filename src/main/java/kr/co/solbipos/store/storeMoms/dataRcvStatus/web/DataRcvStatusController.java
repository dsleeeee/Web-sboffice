package kr.co.solbipos.store.storeMoms.dataRcvStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.store.storeMoms.dataRcvStatus.service.DataRcvStatusService;
import kr.co.solbipos.store.storeMoms.dataRcvStatus.service.DataRcvStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : DataRcvStatusController.java
 * @Description : 맘스터치 > 매장관리 > 자료수신현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.03
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/store/storeMoms/dataRcvStatus")
public class DataRcvStatusController {

    private final SessionService sessionService;
    private final DataRcvStatusService dataRcvStatusService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    @Autowired
    public DataRcvStatusController(SessionService sessionService, DataRcvStatusService dataRcvStatusService,
                                   DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.dataRcvStatusService = dataRcvStatusService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     */
    @RequestMapping(value = "/dataRcvStatus/view.sb", method = RequestMethod.GET)
    public String dataRcvStatusView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        // 팀별
        List momsTeamComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "151");
        String momsTeamComboListAll = buildComboListAll(momsTeamComboList);
        model.addAttribute("momsTeamComboList", momsTeamComboListAll);

        // AC점포별
        List momsAcShopComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "152");
        String momsAcShopComboListAll = buildComboListAll(momsAcShopComboList);
        model.addAttribute("momsAcShopComboList", momsAcShopComboListAll);

        // 지역구분
        List momsAreaFgComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "153");
        String momsAreaFgComboListAll = buildComboListAll(momsAreaFgComboList);
        model.addAttribute("momsAreaFgComboList", momsAreaFgComboListAll);

        // 상권
        List momsCommercialComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "154");
        String momsCommercialComboListAll = buildComboListAll(momsCommercialComboList);
        model.addAttribute("momsCommercialComboList", momsCommercialComboListAll);

        // 점포유형
        List momsShopTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "155");
        String momsShopTypeComboListAll = buildComboListAll(momsShopTypeComboList);
        model.addAttribute("momsShopTypeComboList", momsShopTypeComboListAll);

        // 매장관리타입
        List momsStoreManageTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "156");
        String momsStoreManageTypeComboListAll = buildComboListAll(momsStoreManageTypeComboList);
        model.addAttribute("momsStoreManageTypeComboList", momsStoreManageTypeComboListAll);

        // 그룹
        List branchCdComboList = dayProdService.getUserBranchComboList(sessionInfoVO);
        String branchCdComboListAll = buildComboListAll(branchCdComboList);
        model.addAttribute("branchCdComboList", branchCdComboListAll);

        // 매장그룹
        List momsStoreFg01ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "167");
        model.addAttribute("momsStoreFg01ComboList", buildComboListAll(momsStoreFg01ComboList));

        // 매장그룹2
        List momsStoreFg02ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "169");
        model.addAttribute("momsStoreFg02ComboList", buildComboListAll(momsStoreFg02ComboList));

        // 매장그룹3
        List momsStoreFg03ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "170");
        model.addAttribute("momsStoreFg03ComboList", buildComboListAll(momsStoreFg03ComboList));

        // 매장그룹4
        List momsStoreFg04ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "171");
        model.addAttribute("momsStoreFg04ComboList", buildComboListAll(momsStoreFg04ComboList));

        // 매장그룹5
        List momsStoreFg05ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "172");
        model.addAttribute("momsStoreFg05ComboList", buildComboListAll(momsStoreFg05ComboList));

        return "store/storeMoms/dataRcvStatus/dataRcvStatus";
    }

    /**
     * 자료수신현황 헤더 조회
     */
    @RequestMapping(value = "/dataRcvStatus/getDataRcvStatusHdrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDataRcvStatusHdrList(HttpServletRequest request, HttpServletResponse response,
                                          Model model, DataRcvStatusVO dataRcvStatusVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dataRcvStatusService.getDataRcvStatusHdrList(dataRcvStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dataRcvStatusVO);
    }

    /**
     * 자료수신현황 상세 조회
     */
    @RequestMapping(value = "/dataRcvStatus/getDataRcvStatusDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDataRcvStatusDtlList(HttpServletRequest request, HttpServletResponse response,
                                          Model model, DataRcvStatusVO dataRcvStatusVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dataRcvStatusService.getDataRcvStatusDtlList(dataRcvStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dataRcvStatusVO);
    }

    /** 콤보박스 빈 리스트 처리 공통 */
    private String buildComboListAll(List comboList) {
        if (comboList == null || comboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            return convertToJson(list);
        }
        return cmmCodeUtil.assmblObj(comboList, "name", "value", UseYn.N);
    }
}
