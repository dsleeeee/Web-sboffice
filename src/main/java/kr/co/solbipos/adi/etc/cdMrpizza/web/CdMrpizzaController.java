package kr.co.solbipos.adi.etc.cdMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.etc.cdMrpizza.service.CdMrpizzaService;
import kr.co.solbipos.adi.etc.cdMrpizza.service.CdMrpizzaVO;
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
 * @Class Name  : CdMrpizzaController.java
 * @Description : 미스터피자 > 기타관리 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.14  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.14
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/adi/etc/cdMrpizza")
public class CdMrpizzaController {

    private final CdMrpizzaService cdMrpizzaService;
    private final SessionService sessionService;

    @Autowired
    public CdMrpizzaController(CdMrpizzaService cdMrpizzaService, SessionService sessionService) {
        this.cdMrpizzaService = cdMrpizzaService;
        this.sessionService = sessionService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/cdMrpizza/view.sb", method = RequestMethod.GET)
    public String cdMrpizza(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/etc/cdMrpizza/cdMrpizza";
    }

    /**
     * 시스템 명칭관리 - 조회
     *
     * @param   cdMrpizzaVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 08. 14.
     */
    @RequestMapping(value = "/cdMrpizza/getNmcodeCdMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNmcodeCdMrpizzaList(HttpServletRequest request, HttpServletResponse response,
                                      CdMrpizzaVO cdMrpizzaVO, Model model) {

        // 세션정보 설정
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        cdMrpizzaVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            cdMrpizzaVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            cdMrpizzaVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // Parameter 값으로 대표/세부 명칭 코드목록을 조회 분기처리
        if ( "000".equals(cdMrpizzaVO.getNmcodeGrpCd()) ) {
            // 대표명칭 코드목록 조회
            //20201.01.05 999 의 내역 중 코드항목 1  C:공통(본사기준, 단독매장수정가능), H:본사전용, S:매장전용
            list = cdMrpizzaService.getNmcodeGrpCdMrpizzaList(cdMrpizzaVO);
        } else {
            // 세부명칭 코드목록 조회
            list = cdMrpizzaService.getNmcodeCdMrpizzaList(cdMrpizzaVO);
        }

        return ReturnUtil.returnListJson(Status.OK, list, cdMrpizzaVO);
    }

    /**
     * 시스템 명칭관리 - 저장
     *
     * @param   cdMrpizzaVOS
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 08. 14.
     */
    @RequestMapping(value = "/cdMrpizza/getNmcodeCdMrpizzaSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNmcodeCdMrpizzaSave(@RequestBody CdMrpizzaVO[] cdMrpizzaVOS, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = cdMrpizzaService.getNmcodeCdMrpizzaSave(cdMrpizzaVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
