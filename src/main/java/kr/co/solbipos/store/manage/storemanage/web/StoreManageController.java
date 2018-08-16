package kr.co.solbipos.store.manage.storemanage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.brand.service.HqBrandVO;
import kr.co.solbipos.store.manage.storemanage.service.*;
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

    /** service */
    @Autowired
    StoreManageService service;

    @Autowired
    SessionService sessionService;

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
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {
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

        List<DefaultMap<String>> list = service.getStoreList(storeManageVO);

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

        List<DefaultMap<String>> list = service.getStoreComboList(storeManageVO);

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

        int cnt = service.saveStoreInfo(storeManageVO, sessionInfoVO);

        return returnJson(Status.OK, String.valueOf(cnt), storeManageVO);
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
       List<DefaultMap<String>> envGroupList = service.getPosEnvGroupList(storePosEnvVO);

       // 그룹설정 selectBox
       List<DefaultMap<String>> groupList = service.getGroupList(storePosEnvVO);

       resultMap.put("envGroupList", envGroupList);
       resultMap.put("groupList", groupList);

       return returnListJson(Status.OK, resultMap);
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

       Map<String, Object> resultMap = new HashMap<String, Object>();

       // 포스 번호 목록 조회
       List<DefaultMap<String>> posList = service.getPosList(storePosEnvVO);

       resultMap.put("posList", posList);

       return returnListJson(Status.OK, resultMap);
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
   public Result copyPosSetting(StorePosEnvVO storePosEnvVO, HttpServletRequest request,
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
    * 터치키 복사할 브랜드 목록 조회
    * @param hqBrandVO
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.07.17
    */
   @RequestMapping(value = "storeManage/getHqBrandList.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result getHqBrandList(HqBrandVO hqBrandVO, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       // 터치키 복사할 브랜드 조회
       List<DefaultMap<String>> hqList = service.getHqBrandList(hqBrandVO);

       return returnListJson(Status.OK, hqList);
   }
   // getTouchKeyStoreList

   /**
    * 터치키 복사할 매장 목록 조회
    * @param hqBrandVO
    * @param request
    * @param response
    * @param model
    * @return
    * @author 김지은
    * @since 2018.07.17
    */
   @RequestMapping(value = "storeManage/getTouchKeyStoreList.sb", method = RequestMethod.POST)
   @ResponseBody
   public Result getTouchKeyStoreList(HqBrandVO hqBrandVO, HttpServletRequest request,
           HttpServletResponse response, Model model) {

       // 터치키 복사할 브랜드 조회
       List<DefaultMap<String>> hqList = service.getTouchKeyStoreList(hqBrandVO);

       return returnListJson(Status.OK, hqList);
   }
}
