package kr.co.solbipos.store.storeMoms.lsmStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.dlvrProdMulti.service.DlvrProdMultiVO;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
import kr.co.solbipos.store.storeMoms.lsmStore.service.LsmStoreService;
import kr.co.solbipos.store.storeMoms.lsmStore.service.LsmStoreVO;
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
 * @Class Name : LsmStoreController.java
 * @Description : 맘스터치 > 매장관리 > LSM사용매장조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.26  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.04.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/store/storeMoms/lsmStore")
public class LsmStoreController {
    private final SessionService sessionService;
    private final LsmStoreService lsmStoreService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    @Autowired
    public LsmStoreController(SessionService sessionService, LsmStoreService lsmStoreService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.lsmStoreService = lsmStoreService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }


    /**
     * 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2023.04.26
     */
    @RequestMapping(value = "/lsmStore/view.sb", method = RequestMethod.GET)
    public String empMonthView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        return "store/storeMoms/lsmStore/lsmStoreTab";
    }

    /**
     * 터치키 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  권지현
     * @since   2023.04.26
     */
    @RequestMapping(value = "/lsmStore/getLsmStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLsmStoreList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, LsmStoreVO lsmStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = lsmStoreService.getLsmStoreList(lsmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, lsmStoreVO);
    }

    /**
     * 터치키 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  권지현
     * @since   2023.04.26
     */
    @RequestMapping(value = "/lsmStore/getLsmStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLsmStoreExcelList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, LsmStoreVO lsmStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = lsmStoreService.getLsmStoreExcelList(lsmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, lsmStoreVO);
    }

    /**
     * 키오스크 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.03.18
     */
    @RequestMapping(value = "/lsmStore/getLsmKioskStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLsmKioskStoreList(HttpServletRequest request, HttpServletResponse response,
                                  Model model, LsmStoreVO lsmStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = lsmStoreService.getLsmKioskStoreList(lsmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, lsmStoreVO);
    }

    /**
     * 키오스크 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.03.18
     */
    @RequestMapping(value = "/lsmStore/getLsmKioskStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLsmKioskStoreExcelList(HttpServletRequest request, HttpServletResponse response,
                                       Model model, LsmStoreVO lsmStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = lsmStoreService.getLsmKioskStoreExcelList(lsmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, lsmStoreVO);
    }

    /**
     * 키오스크 탭 엑셀 업로드 - 데이터 임시 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVOs
     * @return  String
     * @author  김유승
     * @since   2024.06.11
     */
    @RequestMapping(value = "/lsmStore/getKioskKeyTempInsert.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskKeyTempInsert(@RequestBody LsmStoreVO[] lsmStoreVOs, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = lsmStoreService.getKioskKeyTempInsert(lsmStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 탭 엑셀 업로드 - 키오스크키맵 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.06.11
     */
    @RequestMapping(value = "/lsmStore/getDeleteKioskKey.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDeleteKioskKey(@RequestBody LsmStoreVO lsmStoreVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = lsmStoreService.getDeleteKioskKey(lsmStoreVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 탭 엑셀 업로드 - 데이터 중 LSM사용인 데이터 수 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.06.12
     */
    @ResponseBody
    @RequestMapping(value = "/lsmStore/getKioskKeyCnt.sb", method = RequestMethod.POST)
    public Result getKioskKeyCnt(@RequestBody LsmStoreVO lsmStoreVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int kioskKeyCnt= lsmStoreService.getKioskKeyCnt(lsmStoreVO, sessionInfoVO);

        return returnJson(Status.OK, kioskKeyCnt);
    }

    /**
     * 키오스크 탭 엑셀 업로드 - 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.06.12
     */
    @RequestMapping(value = "/lsmStore/getInsertKioskKey.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInsertKioskKey(@RequestBody LsmStoreVO lsmStoreVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int regCnt = lsmStoreService.getInsertKioskKey(lsmStoreVO, sessionInfoVO);

        return returnListJson(Status.OK, regCnt);
    }

    /**
     * 터치키 탭 엑셀 업로드 - 데이터 임시 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVOs
     * @return  String
     * @author  김유승
     * @since   2024.06.11
     */
    @RequestMapping(value = "/lsmStore/getTukeyTempInsert.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTukeyTempInsert(@RequestBody LsmStoreVO[] lsmStoreVOs, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = lsmStoreService.getTukeyTempInsert(lsmStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 터치키 탭 엑셀 업로드 - 키오스크키맵 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.06.11
     */
    @RequestMapping(value = "/lsmStore/getDeleteTukey.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDeleteTukey(@RequestBody LsmStoreVO lsmStoreVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = lsmStoreService.getDeleteTukey(lsmStoreVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 터치키 탭 엑셀 업로드 - 데이터 중 LSM사용인 데이터 수 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.06.12
     */
    @ResponseBody
    @RequestMapping(value = "/lsmStore/getTukeyCnt.sb", method = RequestMethod.POST)
    public Result getTukeyCnt(@RequestBody LsmStoreVO lsmStoreVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int kioskKeyCnt= lsmStoreService.getTukeyCnt(lsmStoreVO, sessionInfoVO);

        return returnJson(Status.OK, kioskKeyCnt);
    }

    /**
     * 터치키 탭 엑셀 업로드 - 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.06.12
     */
    @RequestMapping(value = "/lsmStore/getInsertTukey.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInsertTukey(@RequestBody LsmStoreVO lsmStoreVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int regCnt = lsmStoreService.getInsertTukey(lsmStoreVO, sessionInfoVO);

        return returnListJson(Status.OK, regCnt);
    }

    /**
     * 키오스크 탭 엑셀 업로드 - 단종/미사용 상품 유무 확인
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.06.12
     */
    @ResponseBody
    @RequestMapping(value = "/lsmStore/getKioskChkUseYn.sb", method = RequestMethod.POST)
    public Result getKioskChkUseYn(LsmStoreVO lsmStoreVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = lsmStoreService.getKioskChkUseYn(lsmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 키오스크 탭 엑셀 업로드 - 카테고리별 상품수 확인
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.06.12
     */
    @ResponseBody
    @RequestMapping(value = "/lsmStore/getKioskChkProdCnt.sb", method = RequestMethod.POST)
    public Result getKioskChkProdCnt(LsmStoreVO lsmStoreVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = lsmStoreService.getKioskChkProdCnt(lsmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 터치키 탭 엑셀 업로드 - 단종/미사용 상품 유무 확인
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.06.12
     */
    @ResponseBody
    @RequestMapping(value = "/lsmStore/getTukeyChkUseYn.sb", method = RequestMethod.POST)
    public Result getTukeyChkUseYn(LsmStoreVO lsmStoreVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = lsmStoreService.getTukeyChkUseYn(lsmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 터치키 탭 엑셀 업로드 - 분류별 상품수 확인
     * @param   request
     * @param   response
     * @param   model
     * @param   lsmStoreVO
     * @return  String
     * @author  김유승
     * @since   2024.06.12
     */
    @ResponseBody
    @RequestMapping(value = "/lsmStore/getTukeyChkProdCnt.sb", method = RequestMethod.POST)
    public Result getTukeyChkProdCnt(LsmStoreVO lsmStoreVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = lsmStoreService.getTukeyChkProdCnt(lsmStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

}
