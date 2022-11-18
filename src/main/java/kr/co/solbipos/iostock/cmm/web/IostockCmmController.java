package kr.co.solbipos.iostock.cmm.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmService;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : IostockCmmController.java
 * @Description : 수불&재고관련 공통
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.20  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 12.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/cmm/iostockCmm")
public class IostockCmmController {
    private final SessionService sessionService;
    private final IostockCmmService iostockCmmService;

    @Autowired
    public IostockCmmController(SessionService sessionService, IostockCmmService iostockCmmService) {
        this.sessionService = sessionService;
        this.iostockCmmService = iostockCmmService;
    }


    /**
     * 수불&재고관련 공통 - 매장선택 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  안동관
     * @since   2018. 09. 03.
     */
    @RequestMapping(value = "/selectStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectStoreList(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = iostockCmmService.selectStoreList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }


    /**
     * 수불&재고관련 공통 - 매장선택 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  권지현
     * @since   2022. 10. 19.
     */
    @RequestMapping(value = "/selectStoreMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectStoreMomsList(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = iostockCmmService.selectStoreMomsList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }

    /**
     * 수불&재고관련 공통 - 상품선택 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  권지현
     * @since   2022. 10. 19.
     */
    @RequestMapping(value = "/selectProdMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectProdMomsList(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = iostockCmmService.selectProdMomsList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }

    /**
     * 수불&재고관련 공통 - 매장선택 팝업 브랜드 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  권지현
     * @since   2022. 10. 19.
     */
    @RequestMapping(value = "/selectBrandMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectBrandMomsList(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = iostockCmmService.selectBrandMomsList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }

    /**
     * 수불&재고관련 공통 - 거래처 선택모듈 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 21.
     */
    @RequestMapping(value = "/selectVendrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVendrList(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = iostockCmmService.getVendrList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }

    /**
     * 수불&재고관련 공통 - 창고 선택모듈 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  정유경
     * @since   2020.03.26
     */
    @RequestMapping(value = "/selectStorageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectStorageList(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = iostockCmmService.selectStorageList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }


    /**
     * 공통 명칭 콤보조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 22.
     */
    @RequestMapping(value = "/getCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = iostockCmmService.selectCmmCodeList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }


    /**
     * 본사 명칭 콤보조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 19.
     */
    @RequestMapping(value = "/getHqCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = iostockCmmService.selectHqCodeList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }


    /**
     * 매장 명칭 콤보조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 19.
     */
    @RequestMapping(value = "/getStoreCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = iostockCmmService.selectStoreCodeList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }


    /**
     * 본사/매장 명칭 콤보조회 - 본사인 경우 본사, 매장인 경우 매장의 명칭 콤보조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 19.
     */
    @RequestMapping(value = "/getOrgnCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrgnCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = iostockCmmService.selectOrgnCodeList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }


    /**
     * 다이나믹 콤보조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  안동관
     * @since   2018. 10. 22.
     */
    @RequestMapping(value = "/getDynamicCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDynamicCombo(HttpServletRequest request, HttpServletResponse response,
        Model model, IostockCmmVO iostockCmmVO) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        iostockCmmVO.setSelectTable("TB_MS_STORE");
        iostockCmmVO.setSelectCd("STORE_CD");
        iostockCmmVO.setSelectNm("STORE_NM");
        iostockCmmVO.setSelectWhere("HQ_OFFICE_CD='"+sessionInfoVO.getHqOfficeCd()+"'");
        List<DefaultMap<String>> list = iostockCmmService.selectDynamicCodeList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }

    /**
     * 사용자별 브랜드 콤보박스 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  김설아
     * @since   2022. 11. 17.
     */
    @RequestMapping(value = "/selectHqNmcodeMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectHqNmcodeMomsList(HttpServletRequest request, HttpServletResponse response,
                                       Model model, IostockCmmVO iostockCmmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = iostockCmmService.selectHqNmcodeMomsList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }

    /**
     * 사용자별 지사 콤보박스 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   iostockCmmVO
     * @return  String
     * @author  김설아
     * @since   2022. 11. 17.
     */
    @RequestMapping(value = "/selectBranchMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectBranchMomsList(HttpServletRequest request, HttpServletResponse response,
                                      Model model, IostockCmmVO iostockCmmVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = iostockCmmService.selectBranchMomsList(iostockCmmVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, iostockCmmVO);
    }
}
