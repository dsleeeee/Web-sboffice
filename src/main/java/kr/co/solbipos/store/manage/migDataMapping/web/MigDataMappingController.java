package kr.co.solbipos.store.manage.migDataMapping.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.migDataMapping.service.MigDataMappingService;
import kr.co.solbipos.store.manage.migDataMapping.service.MigDataMappingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

@Controller
@RequestMapping("/store/manage/migDataMapping")
public class MigDataMappingController {

    private final SessionService sessionService;
    private final MigDataMappingService migDataMappingService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MigDataMappingController(SessionService sessionService, MigDataMappingService migDataMappingService) {
        this.sessionService = sessionService;
        this.migDataMappingService = migDataMappingService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/migDataMapping/list.sb", method = RequestMethod.GET)
    public String migDataMappingView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "store/manage/migDataMapping/migDataMapping";
    }

    /**
     * OKPOS-KCP 데이터 이관 조회
     *
     * @param migDataMappingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 16.
     */
    @RequestMapping(value = "/migDataMapping/getMigDataMappingList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMigDataMappingList(MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = migDataMappingService.getMigDataMappingList(migDataMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, migDataMappingVO);
    }

    /**
     * OKPOS-KCP 사용자정보 조회
     *
     * @param migDataMappingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 16.
     */
    @RequestMapping(value = "/migDataMappingInfo/getOkposUserInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOkposUserInfoList(MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

//        System.out.println("test1111");
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = migDataMappingService.getOkposUserInfoList(migDataMappingVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * OKPOS-KCP 매장 조회
     *
     * @param migDataMappingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 16.
     */
    @RequestMapping(value = "/migDataMappingInfo/getMigDataMappingInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMigDataMappingInfoList(MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = migDataMappingService.getMigDataMappingInfoList(migDataMappingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, migDataMappingVO);
    }

    /**
     * OKPOS-KCP 데이터 이관 저장
     *
     * @param migDataMappingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 17.
     */
    @RequestMapping(value = "/migDataMappingInfo/getMigDataMappingInfoSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMigDataMappingInfoSave(@RequestBody MigDataMappingVO migDataMappingVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = migDataMappingService.getMigDataMappingInfoSave(migDataMappingVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}