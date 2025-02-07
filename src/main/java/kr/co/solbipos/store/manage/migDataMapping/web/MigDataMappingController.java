package kr.co.solbipos.store.manage.migDataMapping.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.store.manage.migDataMapping.service.MigDataMappingService;
import kr.co.solbipos.store.manage.migDataMapping.service.MigDataMappingVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageService;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;
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
 * @Class Name : MigDataMappingController.java
 * @Description : 기초관리 > 매장정보관리 > OKPOS-KCP 데이터 이관
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.16  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2020.07.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/store/manage/migDataMapping")
public class MigDataMappingController {

    private final SessionService sessionService;
    private final MigDataMappingService migDataMappingService;
    private final StoreManageService storeManageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MigDataMappingController(SessionService sessionService, MigDataMappingService migDataMappingService, StoreManageService storeManageService) {
        this.sessionService = sessionService;
        this.migDataMappingService = migDataMappingService;
        this.storeManageService = storeManageService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/migDataMapping/list.sb", method = RequestMethod.GET)
    public String migDataMappingView(HttpServletRequest request, HttpServletResponse response, Model model, StoreManageVO storeManageVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 메뉴권한 복사할 본사목록 조회
        List<DefaultMap<String>> authHqList = storeManageService.authHqList(storeManageVO, sessionInfoVO);
        model.addAttribute("authHqList", convertToJson(authHqList));

        return "store/manage/migDataMapping/migDataMappingTab";
    }

    /**
     * OKPOS-KCP 데이터 이관 조회
     *
     * @param migDataMappingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 16.
     */
    @RequestMapping(value = "/migDataMapping/getMigDataMappingList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMigDataMappingList(MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = migDataMappingService.getMigDataMappingList(migDataMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, migDataMappingVO);
    }

    /**
     * OKPOS-KCP 사용자정보 조회
     *
     * @param migDataMappingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 16.
     */
    @RequestMapping(value = "/migDataMappingInfo/getOkposUserInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOkposUserInfoList(MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

//        System.out.println("test1111");
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = migDataMappingService.getOkposUserInfoList(migDataMappingVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * OKPOS-KCP 매장 조회
     *
     * @param migDataMappingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 16.
     */
    @RequestMapping(value = "/migDataMappingInfo/getMigDataMappingInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMigDataMappingInfoList(MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = migDataMappingService.getMigDataMappingInfoList(migDataMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, migDataMappingVO);
    }

    /**
     * OKPOS-KCP 데이터 이관 저장
     *
     * @param migDataMappingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 17.
     */
    @RequestMapping(value = "/migDataMappingInfo/getMigDataMappingInfoSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMigDataMappingInfoSave(@RequestBody MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = migDataMappingService.getMigDataMappingInfoSave(migDataMappingVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * SOLBI 매장코드 조회
     *
     * @param migDataMappingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 22.
     */
    @RequestMapping(value = "/migDataMappingInfo/getMigDataMappingSolbiStoreCdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMigDataMappingSolbiStoreCdList(MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = migDataMappingService.getMigDataMappingSolbiStoreCdList(migDataMappingVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);
//        System.out.println("test1111" + result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 매출재이관
     *
     * @param migDataMappingVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 08. 14.
     */
    @RequestMapping(value = "/migDataMapping/getMigDataMappingSaleAgainSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMigDataMappingSaleAgainSave(@RequestBody MigDataMappingVO[] migDataMappingVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = migDataMappingService.getMigDataMappingSaleAgainSave(migDataMappingVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * NXPOS1 탭 - 조회
     *
     * @param   migDataMappingVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 11. 04.
     */
    @RequestMapping(value = "/migDataMapping/getNxMigDataMappingList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNxMigDataMappingList(MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = migDataMappingService.getNxMigDataMappingList(migDataMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, migDataMappingVO);
    }

    /**
     * NXPOS1 탭 - 매장 조회
     * @param   migDataMappingVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 11. 05.
     */
    @RequestMapping(value = "/migDataMappingInfo/getNxMigDataMappingInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNxMigDataMappingInfoList(MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = migDataMappingService.getNxMigDataMappingInfoList(migDataMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, migDataMappingVO);
    }

    /**
     * NXPOS1 탭 - 이관 등록
     * @param   migDataMappingVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 11. 05.
     */
    @RequestMapping(value = "/migDataMappingInfo/getNxMigDataMappingInfoSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNxMigDataMappingInfoSave(@RequestBody MigDataMappingVO[] migDataMappingVOs, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = migDataMappingService.getNxMigDataMappingInfoSave(migDataMappingVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 제우스 탭 - 매장 조회
     * @param   migDataMappingVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 02. 04.
     */
    @RequestMapping(value = "/migDataMapping/getZeusDataMappingList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getZeusDataMappingList(MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = migDataMappingService.getZeusDataMappingList(migDataMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, migDataMappingVO);
    }

    /***
     * 제우스 탭 - 연동 매장 삭제
     * @param   migDataMappingVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 11. 05.
     */
    @RequestMapping(value = "/migDataMapping/getDeleteStoreMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDeleteStoreMapping(@RequestBody MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = migDataMappingService.getDeleteStoreMapping(migDataMappingVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 제우스 탭 - 매장연동신청팝업 매장 조회
     * @param   migDataMappingVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 02. 05.
     */
    @RequestMapping(value = "/migDataMapping/getSearchZeusStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchZeusStoreList(MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = migDataMappingService.getSearchZeusStoreList(migDataMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, migDataMappingVO);
    }

    /***
     * 제우스 탭 - 매장연동신청팝업 매장 등록
     * @param   migDataMappingVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 11. 05.
     */
    @RequestMapping(value = "/migDataMapping/getStoreMappingReg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreMappingReg(@RequestBody MigDataMappingVO[] migDataMappingVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = migDataMappingService.getStoreMappingReg(migDataMappingVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}