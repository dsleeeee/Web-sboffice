package kr.co.solbipos.store.manage.storemanage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import kr.co.solbipos.store.manage.storemanage.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreManageController.java
 * @Description : 가맹점관리 > 매장관리 > 매장정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.08  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */


@Controller
@RequestMapping(value = "/store/manage/storeManage/")
public class StoreManageController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** service */
    private final StoreManageService service;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public StoreManageController(StoreManageService service, SessionService sessionService) {
        this.service = service;
        this.sessionService = sessionService;
    }

    /**
     * 매장정보관리 - 화면 이동
     * @param request
     * @param response
     * @param model
     * @return String
     * @author 김지은
     * @since 2018.06.08
     */
    @RequestMapping(value = "storeManage/view.sb", method = RequestMethod.GET)
    public String list(StoreManageVO storeManageVO, HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 메뉴권한 복사할 본사목록 조회
        List<DefaultMap<String>> authHqList = service.authHqList(storeManageVO, sessionInfoVO);
        model.addAttribute("authHqList", convertToJson(authHqList));

        return "store/manage/storeManage/storeManage";
    }

    /**
     * 매장 목록 - 조회
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 김지은
     * @since 2018.06.08
     */
    @RequestMapping(value = "storeManage/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(StoreManageVO storeManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = service.getStoreList(storeManageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeManageVO);
    }

    /**
     * 매장 목록 - 엑셀조회
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 김지은
     * @since 2020.04.23
     */
    @RequestMapping(value = "storeManage/getStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreExcelList(StoreManageVO storeManageVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = service.getStoreExcelList(storeManageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeManageVO);
    }

    /**
     * 매장 정보 상세 - 조회
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 김지은
     * @since 2018.06.08
     */
    @RequestMapping(value = "storeManage/getStoreDetail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreDetail(StoreManageVO storeManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        Map<String, Object> result = service.getStoreDetail(storeManageVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장 선택 콤보리스트
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "storeManage/getStoreComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreComboList(StoreManageVO storeManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = service.getStoreComboList(storeManageVO, sessionInfoVO);

        return returnListJson(Status.OK, list);
    }

    /**
     * 매장환경조회 팝업 데이터 조회
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "storeManage/getStoreEnvInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreEnvInfo(StoreManageVO storeManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        Map<String, Object> result = service.getStoreEnvInfo(storeManageVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장 정보 신규 등록
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "storeManage/saveStoreInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreInfo(@RequestBody StoreManageVO storeManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 사업자번호 정리
        String bizNo = storeManageVO.getBizNo1() + storeManageVO.getBizNo2() + storeManageVO.getBizNo3();
        storeManageVO.setBizNo(bizNo);

        // 오픈일자
        String sysOpenDate = storeManageVO.getSysOpenDate();
        storeManageVO.setSysOpenDate(StringUtils.remove(sysOpenDate, "-"));

        // 폐점일자
        String sysClosureDate = storeManageVO.getSysClosureDate();
        storeManageVO.setSysClosureDate(StringUtils.remove(sysClosureDate, "-"));

        String storeCd = service.saveStoreInfo(storeManageVO, sessionInfoVO);

//        String storeCd = "";

        return returnJson(Status.OK, storeCd);
    }

    /**
     * 매장 정보 수정
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "storeManage/updateStoreInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateStoreInfo(@RequestBody StoreManageVO storeManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 사업자번호 정리
        String bizNo = storeManageVO.getBizNo1() + storeManageVO.getBizNo2() + storeManageVO.getBizNo3();
        storeManageVO.setBizNo(bizNo);

        // 오픈일자
        String sysOpenDate = storeManageVO.getSysOpenDate();
        storeManageVO.setSysOpenDate(StringUtils.remove(sysOpenDate, "-"));

        // 폐점일자
        String sysClosureDate = storeManageVO.getSysClosureDate();
        storeManageVO.setSysClosureDate(StringUtils.remove(sysClosureDate, "-"));

        int cnt = service.updateStoreInfo(storeManageVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
    * 매장 환경정보 조회
    * @param storeEnvVO
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.06.18
    */
   @RequestMapping(value = "storeManage/getStoreConfigList.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result getStoreConfigList(StoreEnvVO storeEnvVO, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       List<DefaultMap<String>> envGroupList = service.getEnvGroupList(storeEnvVO);

       return returnListJson(Status.OK, envGroupList);
   }

   /**
    * 매장 환경정보 저장
    * @param storeEnvVOs
    * @param request
    * @param response
    * @param model
    * @return
    */
   @RequestMapping(value = "storeManage/saveStoreConfig.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result saveStoreConfig(@RequestBody StoreEnvVO[] storeEnvVOs, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

       int result = service.saveStoreConfig(storeEnvVOs, sessionInfoVO);

       return returnJson(Status.OK, result);
   }

   /**
    * 매장 포스 환경정보 저장
    * @param storePosEnvVOs
    * @param request
    * @param response
    * @param model
    * @return
    */
   @RequestMapping(value = "storeManage/savePosConfig.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result savePosConfig(@RequestBody StorePosEnvVO[] storePosEnvVOs, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

       int result = service.savePosConfig(storePosEnvVOs, sessionInfoVO);

       return returnJson(Status.OK, result);
   }

   /**
    * 포스 환경정보 조회
    * @param storePosEnvVO
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.06.18
    */
   @RequestMapping(value = "storeManage/getPosConfigList.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result getPosConfigList(StorePosEnvVO storePosEnvVO, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       Map<String, Object> resultMap = new HashMap<String, Object>();

       // 포스 환경정보 조회
       List<DefaultMap<String>> list = service.getPosEnvGroupList(storePosEnvVO);

       // 테이블 그룹
       List<DefaultMap<String>> groupList = service.getGroupList(storePosEnvVO);

       resultMap.put("list", list);
       resultMap.put("groupList", groupList);

       return returnJson(Status.OK, resultMap);
   }

   /**
    * 테이블 그룹설정정보 저장
    * @param storePosEnvVOs
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.06.29
    */
   @RequestMapping(value = "storeManage/savePosTabGrp.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result savePosTabGrp(@RequestBody StorePosEnvVO[] storePosEnvVOs, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

       int result = service.savePosTabGrp(storePosEnvVOs, sessionInfoVO);

       return returnJson(Status.OK, result);
   }


   /**
    * 포스 목록 조회
    * @param storePosEnvVO
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.06.18
    */
   @RequestMapping(value = "storeManage/getPosList.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result getPosList(StorePosEnvVO storePosEnvVO, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       // 포스 번호 목록 조회
       List<DefaultMap<String>> posList = service.getPosList(storePosEnvVO);

       return returnListJson(Status.OK, posList);
   }

   /**
    * 포스 테이블 명칭 저장
    * @param storePosEnvVOs
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.06.29
    */
   @RequestMapping(value = "storeManage/savePosNm.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result savePosNm(@RequestBody StorePosEnvVO[] storePosEnvVOs, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

       int result = service.savePosTabNm(storePosEnvVOs, sessionInfoVO);

       return returnJson(Status.OK, result);
   }

   /**
    * 포스 셋팅 복사
    * @param storePosEnvVO
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.06.29
    */
   @RequestMapping(value = "storeManage/copyPosSetting.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result copyPosSetting(@RequestBody StorePosEnvVO storePosEnvVO, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

       int result = service.copyPosSetting(storePosEnvVO, sessionInfoVO);

       return returnJson(Status.OK, result);
   }


   /**
    * 포스 삭제
    * @param storePosEnvVO
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.06.29
    */
   @RequestMapping(value = "storeManage/deletePos.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result deletePos(StorePosEnvVO storePosEnvVO, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

       int result = service.deletePos(storePosEnvVO, sessionInfoVO);

       return returnJson(Status.OK, result);
   }

   /**
    * 주방프린터 조회
    * @param storeEnvVO
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.07.05
    */
   @RequestMapping(value = "storeManage/getKitchenPrintInfo.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result getKitchenPrintInfo(StoreEnvVO storeEnvVO, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       // 주방프린터 목록 조회
       List<DefaultMap<String>> printList = service.getKitchenPrintInfo(storeEnvVO);

       return returnListJson(Status.OK, printList);
   }


   /**
    * 주방프린터 저장
    * @param kitchenPrintVOs
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.07.11
    */
   @RequestMapping(value = "storeManage/saveKitchenPrintInfo.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result saveKitchenPrintInfo(@RequestBody KitchenPrintVO[] kitchenPrintVOs, HttpServletRequest request,
           HttpServletResponse response , Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

       int result = service.saveKitchenPrintInfo(kitchenPrintVOs, sessionInfoVO);

       return returnJson(Status.OK, result);
   }

    /**
     * 매장권하에서 주방프린터 상품연결 화면 이동
     * @param request
     * @param response
     * @param model
     * @return String
     * @author 김지은
     * @since 2018.06.08
     */
    @RequestMapping(value = "storeManage/storeKitchenPrintProductView.sb", method = RequestMethod.GET)
    public String kitchenPrintProductView(HttpServletRequest request, HttpServletResponse response,
        Model model) {
        return "store/manage/storeManage/storeKitchenPrintProductView";
    }

    /**
    * 주방프린터 연결상품 조회
    * @param storeProductVO
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.07.05
    */
   @RequestMapping(value = "storeManage/getKitchenPrintProductInfo.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result getKitchenPrintProductInfo(StoreProductVO storeProductVO, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       Map<String, Object> resultMap = new HashMap<String, Object>();

       // 출력상품 목록 조회
       List<StoreProductVO> printProductList = service.getPrintProductInfo(storeProductVO, UseYn.Y);

       // 미출력상품 목록 조회
       List<StoreProductVO> noPrintProductList = service.getPrintProductInfo(storeProductVO, UseYn.N);

       resultMap.put("printProductList", printProductList);
       resultMap.put("noPrintProductList", noPrintProductList);

       return returnListJson(Status.OK, resultMap);
   }

   /**
    * 주방프린터 연결상품 등록 및 삭제
    * @param storeProductVOs
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.07.05
    */
   @RequestMapping(value = "storeManage/saveKitchenPrintProduct.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result saveKitchenPrintProduct(@RequestBody StoreProductVO[] storeProductVOs, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

       int result = service.saveKitchenPrintProduct(storeProductVOs, sessionInfoVO);

       return returnJson(Status.OK, result);
   }

   /**
    * 터치키 복사할 본사 목록 조회
    * @param
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.07.17
    */
   @RequestMapping(value = "storeManage/getHqList.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result getHqList(HttpServletRequest request,
           HttpServletResponse response, Model model) {

       // 터치키 복사할 본사 목록 조회
       List<DefaultMap<String>> hqList = service.getHqList();

       return returnListJson(Status.OK, hqList);
   }

   /**
    * 터치키 복사할 매장 목록 조회
    * @param hqManageVO
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.07.17
    */
   @RequestMapping(value = "storeManage/getTouchKeyStoreList.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result getTouchKeyStoreList(HqManageVO hqManageVO, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       // 터치키 복사할 브랜드 조회
       List<DefaultMap<String>> hqList = service.getTouchKeyStoreList(hqManageVO);

       return returnListJson(Status.OK, hqList);
   }

    /**
     * 설치포스 수 추가
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2019.11.06
     */
    @RequestMapping(value = "storeManage/savePosCnt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePosCnt(@RequestBody StoreManageVO storeManageVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.savePosCnt(storeManageVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장코드 중복체크
     * @param storeManageVO
     * @author 이다솜
     * @since 2019.12.03
     */
    @ResponseBody
    @RequestMapping(value = "storeManage/getStoreCdCnt.sb", method = RequestMethod.POST)
    public Result getStoreCdCnt(StoreManageVO storeManageVO) {

        int storeCdCnt= service.getStoreCdCnt(storeManageVO);

        return returnJson(Status.OK, storeCdCnt);
    }

    /**
     * 권한그룹복사를 위한 매장목록 조회
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2020.05.12
     */
    @RequestMapping(value = "storeManage/getAuthStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAuthStoreList(StoreManageVO storeManageVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 메뉴권한 복사할 본사목록 조회
        List<DefaultMap<String>> getAuthStoreList = service.authStoreList(storeManageVO, sessionInfoVO);

        return returnListJson(Status.OK, getAuthStoreList);
    }

    /**
     * 사용메뉴 조회
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2020.05.12
     */
    @RequestMapping(value = "storeManage/avlblMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result avlblMenu(StoreManageVO storeManageVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {
        // 사용메뉴 조회
        List<DefaultMap<String>> avlblMenu = service.avlblMenu(storeManageVO);

        return returnListJson(Status.OK, avlblMenu);
    }

    /**
     * 미사용메뉴 조회
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2020.05.12
     */
    @RequestMapping(value = "storeManage/beUseMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result beUseMenu (StoreManageVO storeManageVO, HttpServletRequest request,
                      HttpServletResponse response, Model model) {

        // 미사용메뉴 조회
        List<DefaultMap<String>> beUseMenu  = service.beUseMenu (storeManageVO);

        return returnListJson(Status.OK, beUseMenu );
    }

    /**
     * 메뉴권한복사
     * @param   storeMenuVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2020. 05. 14.
     */
    @RequestMapping(value = "storeManage/copyAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyAuth(@RequestBody StoreMenuVO storeMenuVO, HttpServletRequest request,
                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.copyAuth(storeMenuVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사용메뉴 추가
     * @param   storeMenus
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "storeManage/addAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result addAuth(@RequestBody StoreMenuVO[] storeMenus, HttpServletRequest request,
                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.addAuth(storeMenus, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사용메뉴 삭제
     * @param   storeMenus
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "storeManage/removeAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result removeAuth(@RequestBody StoreMenuVO[] storeMenus, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.removeAuth(storeMenus, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

}
