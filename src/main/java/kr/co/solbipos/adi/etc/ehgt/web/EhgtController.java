package kr.co.solbipos.adi.etc.ehgt.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.etc.ehgt.service.EhgtService;
import kr.co.solbipos.adi.etc.ehgt.service.EhgtVO;
import kr.co.solbipos.adi.etc.ehgt.service.HqCdVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : EhgtController.java
 * @Description : 부가서비스 > 환율관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.09  조병준      최초생성
 *
 * @author NHN한국사이버결제 조병준
 * @since 2018. 08.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/adi/etc/ehgt/")
public class EhgtController {

    @Autowired
    EhgtService ehgtService;

    @Autowired
    SessionService sessionService;

    /**
     * 환율 관리 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "regist/list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        //본사 통화구분 코드 조회
        HqCdVO hqCdVO = new HqCdVO();

        hqCdVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        hqCdVO.setNmcodeGrpCd("052");
        hqCdVO.setUseYn(UseYn.Y);
        List<DefaultMap<String>> hqCrncys = ehgtService.getHqCdListByGrpCd(hqCdVO);
        
        //입력 table과 grid의 헤더를 위한 데이터
        model.addAttribute("hqCrncys", hqCrncys);
        
        return "adi/etc/ehgt/regist";
    }

    /**
     * 환율 관리 리스트 조회
     *
     * @param ehgtVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "regist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(EhgtVO ehgtVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<Object>> list = ehgtService.getEhgtListBySaleDt(ehgtVO, sessionInfoVO);
        
        return returnListJson(Status.OK, list);
    }

    /**
     * 환율 관리 해당일의 환율 조회
     *
     * @param ehgtVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "regist/detail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result detail(EhgtVO ehgtVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = ehgtService.getEhgtDetailBySaleDt(ehgtVO, sessionInfoVO);
        
        return returnListJson(Status.OK, list);
    }

    /**
     * 환율 등록
     *
     * @param ehgtVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "regist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody EhgtVO[] ehgtVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = ehgtService.saveEhgts(ehgtVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }


    /**
     * 통화구분 팝업 조회
     *
     * @param ehgtVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "crncy/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result listCrncy(HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        //본사 통화구분 코드 조회
        HqCdVO hqCdVO = new HqCdVO();
        
        hqCdVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        hqCdVO.setNmcodeGrpCd("052");
        hqCdVO.setUseYn(UseYn.ALL);
        List<DefaultMap<String>> list = ehgtService.getHqCdListByGrpCd(hqCdVO);
        return returnListJson(Status.OK, list);
    }

    /**
     * 통화구분 저장
     *
     * @param ehgt
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "crncy/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCrncy(@RequestBody HqCdVO[] hqCdVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        int result = ehgtService.updateHqCd(hqCdVOs, si);

        return returnJson(Status.OK, result);
    }
}
