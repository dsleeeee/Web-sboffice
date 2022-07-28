package kr.co.solbipos.sys.bill.kind.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.bill.kind.service.KindService;
import kr.co.solbipos.sys.bill.kind.service.KindVO;
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
 * @Class Name : KindController.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력물 종류
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sys/bill/kind")
public class KindController {

    private final KindService kindService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public KindController(KindService kindService, SessionService sessionService) {
        this.kindService = kindService;
        this.sessionService = sessionService;
    }

    /**
     * 출력물 종류 - 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return String
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/bill/view.sb", method = RequestMethod.GET)
    public String kindView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sys/bill/kind/kind";
    }

    /**
     * 출력물 종류 - 출력물종류 목록 조회
     * 
     * @param request
     * @param response
     * @param kindVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/bill/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrintList(HttpServletRequest request, HttpServletResponse response,
            KindVO kindVO, Model model) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 출력물종류 목록 조회
        list = kindService.getPrintList(kindVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, kindVO);

    }

    /**
     * 출력물 종류 - 출력물종류 목록 저장
     * 
     * @param request
     * @param response
     * @param kindVOs
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/bill/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePrintList(@RequestBody KindVO[] kindVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kindService.savePrintList(kindVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
        
    }

    /**
     * 출력물 종류 - 출력물매핑 목록 조회
     * 
     * @param request
     * @param response
     * @param kindVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/mapng/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrintMapngList(HttpServletRequest request, HttpServletResponse response,
            KindVO kindVO, Model model) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 출력물매핑 목록 조회
        list = kindService.getPrintMapngList(kindVO);

        return ReturnUtil.returnListJson(Status.OK, list, kindVO);

    }

    /**
     * 출력물 종류 - 출력물매핑 목록 팝업 조회
     *
     * @param request
     * @param response
     * @param kindVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/mapng/unUsedList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrintMapngUnUsedList(HttpServletRequest request, HttpServletResponse response,
        KindVO kindVO, Model model) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 출력물매핑 목록 팝업 조회
        list = kindService.getPrintMapngUnUsedList(kindVO);
        // 데이터 없는 경우 커스텀 메시지 처리
        Result result = ReturnUtil.returnListJson(Status.OK, list, kindVO);
        if ( list.isEmpty() ) {
            result.setMessage("추가할 출력코드가 없습니다.");
        }

        return result;

    }

    /**
     * 출력물 종류 - 출력물매핑 목록 저장
     * 
     * @param request
     * @param response
     * @param kindVOs
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/mapng/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePrintMapngList(@RequestBody KindVO[] kindVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kindService.savePrintMapngList(kindVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
        
    }

    /**
     * 출력물 종류 삭제 전 체크
     *
     * @param kindVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 07. 27.
     */
    @RequestMapping(value = "/bill/getKindDeleteChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKindDeleteChk(KindVO kindVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = kindService.getKindDeleteChk(kindVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 출력물 종류 삭제
     *
     * @param kindVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 07. 27.
     */
    @RequestMapping(value = "/bill/getKindDeleteSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKindDeleteSave(@RequestBody KindVO[] kindVOs, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kindService.getKindDeleteSave(kindVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
