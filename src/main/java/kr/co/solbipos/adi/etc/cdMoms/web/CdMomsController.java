package kr.co.solbipos.adi.etc.cdMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.etc.cdMoms.service.CdMomsService;
import kr.co.solbipos.adi.etc.cdMoms.service.CdMomsVO;
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
 * @Class Name : CdMomsController.java
 * @Description : 맘스터치 > 기타관리 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.10.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/adi/etc/cdMoms")
public class CdMomsController {

    private final CdMomsService cdMomsService;
    private final SessionService sessionService;

    /**
     * Constructor Injection
     */
    @Autowired
    public CdMomsController(CdMomsService cdMomsService, SessionService sessionService) {
        this.cdMomsService = cdMomsService;
        this.sessionService = sessionService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/cdMoms/view.sb", method = RequestMethod.GET)
    public String cdMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/etc/cdMoms/cdMoms";
    }

    /**
     * 시스템 명칭관리 - 조회
     *
     * @param cdMomsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 10. 27.
     */
    @RequestMapping(value = "/cdMoms/getNmcodeCdMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNmcodeCdMomsList(HttpServletRequest request, HttpServletResponse response,
                                        CdMomsVO cdMomsVO, Model model) {

        // 세션정보 설정
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        cdMomsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            cdMomsVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            cdMomsVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // Parameter 값으로 대표/세부 명칭 코드목록을 조회 분기처리
        if ( "000".equals(cdMomsVO.getNmcodeGrpCd()) ) {
            // 대표명칭 코드목록 조회
            //20201.01.05 999 의 내역 중 코드항목 1  C:공통(본사기준, 단독매장수정가능), H:본사전용, S:매장전용
            list = cdMomsService.getNmcodeGrpCdMomsList(cdMomsVO);
        } else {
            // 세부명칭 코드목록 조회
            list = cdMomsService.getNmcodeCdMomsList(cdMomsVO);
        }

        return ReturnUtil.returnListJson(Status.OK, list, cdMomsVO);
    }

    /**
     * 시스템 명칭관리 - 저장
     *
     * @param cdMomsVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 10. 27.
     */
    @RequestMapping(value = "/cdMoms/getNmcodeCdMomsSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNmcodeCdMomsSave(@RequestBody CdMomsVO[] cdMomsVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = cdMomsService.getNmcodeCdMomsSave(cdMomsVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}