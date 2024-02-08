package kr.co.solbipos.base.prod.dlvrProdMulti.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.dlvrProdMulti.service.DlvrProdMultiService;
import kr.co.solbipos.base.prod.dlvrProdMulti.service.DlvrProdMultiVO;
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
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : DlvrProdMultiController.java
 * @Description : 기초관리 - 상품관리 - 배달시스템 상품 명칭 맵핑2
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.25  김유승       최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.25
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/dlvrProdMulti")
public class DlvrProdMultiController {

    private final SessionService sessionService;
    private final DlvrProdMultiService dlvrProdMultiService;

    public DlvrProdMultiController(SessionService sessionService, DlvrProdMultiService dlvrProdMultiService) {
        this.sessionService = sessionService;
        this.dlvrProdMultiService = dlvrProdMultiService;
    }

    /**
     * 배달시스템 상품 명칭 매핑2 - 메인화면
     *
     * @param request
     * @param response
     * @param model
     * @author 김유승
     * @since 2024.01.25
     * @return
     */
    @RequestMapping(value = "/dlvrProdMulti/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        DlvrProdMultiVO dlvrProdMultiVO = new DlvrProdMultiVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 배달앱 구분코드 조회
        List<DefaultMap<String>> dlvrColList = dlvrProdMultiService.getDlvrColList(dlvrProdMultiVO, sessionInfoVO);

        // 배달앱 구분코드를 , 로 연결하는 문자열 생성
        String dlvrCol = "";
        for(int i=0; i < dlvrColList.size(); i++) {
            dlvrCol += (dlvrCol.equals("") ? "" : ",") + dlvrColList.get(i).getStr("dlvrCd");
        }
        model.addAttribute("dlvrColList", dlvrColList);
        model.addAttribute("dlvrCol", dlvrCol);

        return "base/prod/dlvrProdMulti/dlvrProdMultiNmMapping";
    }

    /**
     * 배달시스템 상품 명칭 매핑2 - 상품목록조회
     *
     * @param dlvrProdMultiVO
     * @param request
     * @author 김유승
     * @since 2024.01.25
     * @return
     */
    @RequestMapping(value = "/dlvrProdMulti/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(DlvrProdMultiVO dlvrProdMultiVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dlvrProdMultiService.getProdList(dlvrProdMultiVO, sessionInfoVO);

        return returnListJson(Status.OK, list, dlvrProdMultiVO);
    }

    /**
     * 배달시스템 상품 명칭 매핑2  - 전체 엑셀다운로드
     *
     * @param dlvrProdMultiVO
     * @param request
     * @return  Object
     * @author  김유승
     * @since   2024.01.26
     */
    @RequestMapping(value = "/dlvrProdMulti/getDlvrProdMultiExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrProdMultiExcelList(DlvrProdMultiVO dlvrProdMultiVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dlvrProdMultiService.getDlvrProdMultiExcelList(dlvrProdMultiVO, sessionInfoVO);

        return returnListJson(Status.OK, list, dlvrProdMultiVO);
    }

    /**
     * 배달시스템 상품 명칭 매핑2 - 배달상품명칭 저장
     *
     * @param dlvrProdMultiVOs
     * @param request
     * @author 김유승
     * @since 2024.01.26
     * @return
     */
    @RequestMapping(value = "/dlvrProdMulti/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody DlvrProdMultiVO[] dlvrProdMultiVOs, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int regCnt = dlvrProdMultiService.saveDlvrProdMultiNm(dlvrProdMultiVOs, sessionInfoVO);

        return returnListJson(Status.OK, regCnt);
    }

    /**
     * 배달시스템 상품 명칭 매핑2 - 배달상품명칭 복사
     *
     * @param dlvrProdMultiVO
     * @param request
     * @author 김유승
     * @since   2024.01.30
     * @return
     */
    @RequestMapping(value = "/dlvrProdMulti/copyDlvrProdMultiNm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyDlvrProdMultiNm(@RequestBody DlvrProdMultiVO dlvrProdMultiVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int regCnt = dlvrProdMultiService.copyDlvrProdMultiNm(dlvrProdMultiVO, sessionInfoVO);

        return returnListJson(Status.OK, regCnt);
    }

    /**
     * 배달시스템 상품 명칭 매핑2 - 상품명칭 매장적용 팝업 조회
     *
     * @param dlvrProdMultiVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author 김유승
     * @since   2024.01.30
     */
    @RequestMapping(value = "/dlvrProdMulti/getDlvrProdMultiNmStoreRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrProdMultiNmStoreRegistList(DlvrProdMultiVO dlvrProdMultiVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dlvrProdMultiService.getDlvrProdMultiNmStoreRegistList(dlvrProdMultiVO, sessionInfoVO);

        return returnListJson(Status.OK, result, dlvrProdMultiVO);
    }

    /**
     * 배달시스템 상품 명칭 매핑2 - 상품명칭 매장적용 저장
     *
     * @param dlvrProdMultiVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2024.01.30
     */
    @RequestMapping(value = "/dlvrProdMulti/getDlvrProdMultiNmStoreRegistSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrProdMultiNmStoreRegistSave(@RequestBody DlvrProdMultiVO[] dlvrProdMultiVOs, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dlvrProdMultiService.getDlvrProdMultiNmStoreRegistSave(dlvrProdMultiVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 배달시스템 상품 명칭 매핑2 - 상품명칭 엑셀 업로드 전 상품코드 유효여부 체크
     *
     * @param dlvrProdMultiVO
     * @param request
     * @author 김유승
     * @since 2024.01.30
     * @return
     */
    @RequestMapping(value = "/dlvrProdMulti/chkDlvrProdMulti.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkDlvrProdMulti(DlvrProdMultiVO dlvrProdMultiVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int getCnt = dlvrProdMultiService.chkDlvrProdMulti(dlvrProdMultiVO, sessionInfoVO);

        return returnJson(Status.OK, getCnt);
    }

    /**
     * 배달시스템 상품 명칭 매핑2 - 데이터 임시 저장
     *
     * @param dlvrProdMultiVOs
     * @param request
     * @author 김유승
     * @since 2024.02.02
     * @return
     */
    @RequestMapping(value = "/dlvrProdMulti/getDlvrProdCdSaveInsert.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrProdNmMappingChk(@RequestBody DlvrProdMultiVO[] dlvrProdMultiVOs, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dlvrProdMultiService.getDlvrProdCdSaveInsert(dlvrProdMultiVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 배달시스템 상품 명칭 매핑2 - 배달상품명칭 중복 체크
     *
     * @param dlvrProdMultiVOs
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김유승
     * @since  2024.01.26.
     */
    @RequestMapping(value = "/dlvrProdMulti/getDlvrProdMultiNmMappingChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrProdMultiNmMappingChk(@RequestBody DlvrProdMultiVO[] dlvrProdMultiVOs, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = dlvrProdMultiService.getDlvrProdMultiNmMappingChk(dlvrProdMultiVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 배달시스템 상품 명칭 매핑2 - 입력값 확인
     *
     * @param dlvrProdMultiVO
     * @param request
     * @author 김유승
     * @since 2024.02.02
     * @return
     */
    @RequestMapping(value = "/dlvrProdMulti/getChkProdCdChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getChkProdCdChk(DlvrProdMultiVO dlvrProdMultiVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int getCnt = dlvrProdMultiService.getChkProdCdChk(dlvrProdMultiVO, sessionInfoVO);

        return returnJson(Status.OK, getCnt);
    }

    /**
     * 배달시스템 상품 명칭 매핑2 - 엑셀 업로드(기존값 삭제)
     *
     * @param dlvrProdMultiVOs
     * @param request
     * @author 김유승
     * @since 2024.02.05
     * @return
     */
    @RequestMapping(value = "/dlvrProdMulti/excelDeleteDlvrProdNm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelDeleteDlvrProdNm(@RequestBody DlvrProdMultiVO[] dlvrProdMultiVOs, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int regCnt = dlvrProdMultiService.excelDeleteDlvrProdNm(dlvrProdMultiVOs, sessionInfoVO);

        return returnListJson(Status.OK, regCnt);
    }

    /**
     * 배달시스템 상품 명칭 매핑2 - 엑셀업로드(저장)
     *
     * @param dlvrProdMultiVOs
     * @param request
     * @author 김유승
     * @since 2024.02.05
     * @return
     */
    @RequestMapping(value = "/dlvrProdMulti/excelUploadsave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result excelUploadsave(@RequestBody DlvrProdMultiVO[] dlvrProdMultiVOs, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int regCnt = dlvrProdMultiService.excelUploadsave(dlvrProdMultiVOs, sessionInfoVO);

        return returnListJson(Status.OK, regCnt);
    }

}
