package kr.co.solbipos.adi.etc.cdKwu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.etc.cdKwu.service.CdKwuService;
import kr.co.solbipos.adi.etc.cdKwu.service.CdKwuVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
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
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : CdKwuController.java
 * @Description : 광운대 > 공통코드 > 공통코드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.09.07
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/adi/etc/cdKwu")
public class CdKwuController {

    private final CdKwuService cdKwuService;
    private final SessionService sessionService;

    /**
     * Constructor Injection
     */
    @Autowired
    public CdKwuController(CdKwuService cdKwuService, SessionService sessionService) {
        this.cdKwuService = cdKwuService;
        this.sessionService = sessionService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/cdKwu/view.sb", method = RequestMethod.GET)
    public String cdKwuView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/etc/cdKwu/cdKwu";
    }

    /**
     * 시스템 명칭관리 - 조회
     *
     * @param cdKwuVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 09. 07.
     */
    @RequestMapping(value = "/cdKwu/getNmcodeCdKwuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNmcodeCdKwuList(HttpServletRequest request, HttpServletResponse response,
                                     CdKwuVO cdKwuVO, Model model) {

        // 세션정보 설정
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        cdKwuVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            cdKwuVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            cdKwuVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // Parameter 값으로 대표/세부 명칭 코드목록을 조회 분기처리
        if ( "000".equals(cdKwuVO.getNmcodeGrpCd()) ) {
            // 대표명칭 코드목록 조회
            //20201.01.05 999 의 내역 중 코드항목 1  C:공통(본사기준, 단독매장수정가능), H:본사전용, S:매장전용
            list = cdKwuService.getNmcodeGrpCdKwuList(cdKwuVO);
        } else {
            // 세부명칭 코드목록 조회
            list = cdKwuService.getNmcodeCdKwuList(cdKwuVO);
        }

        return ReturnUtil.returnListJson(Status.OK, list, cdKwuVO);
    }

    /**
     * 시스템 명칭관리 - 저장
     *
     * @param cdKwuVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 09. 07.
     */
    @RequestMapping(value = "/cdKwu/getNmcodeCdKwuSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNmcodeCdKwuSave(@RequestBody CdKwuVO[] cdKwuVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = cdKwuService.getNmcodeCdKwuSave(cdKwuVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}