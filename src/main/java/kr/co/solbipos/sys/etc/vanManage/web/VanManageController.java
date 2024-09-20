package kr.co.solbipos.sys.etc.vanManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.etc.vanManage.service.VanManageService;
import kr.co.solbipos.sys.etc.vanManage.service.VanManageVO;
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
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
/**
 * @Class Name : VanManageController.java
 * @Description : 시스템관리 > Van/Card사 관리 > 밴사정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.09.13  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.09.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sys/etc/vanManage")
public class VanManageController {

    private final VanManageService vanManageService;
    private final SessionService sessionService;

    @Autowired
    public VanManageController(VanManageService vanManageService, SessionService sessionService) {
        this.vanManageService = vanManageService;
        this.sessionService = sessionService;
    }

    /**
     * 밴사정보관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2024. 09. 13.
     */
    @RequestMapping(value = "/vanManage/view.sb", method = RequestMethod.GET)
    public String vanManageView(HttpServletRequest request, HttpServletResponse response,
                              Model model) {
        return "sys/etc/vanManage/vanManage";
    }

    /**
     * 밴사정보관리 - 조회
     * @param   request
     * @param   response
     * @param   vanManageVo
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2024. 09. 13.
     */
    @RequestMapping(value = "/vanManage/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNmcodeGrpCdList(HttpServletRequest request, HttpServletResponse response,
                                     VanManageVO vanManageVo, Model model) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        list = vanManageService.getNmcodeGrpCdList(vanManageVo);


        return ReturnUtil.returnListJson(Status.OK, list, vanManageVo);
    }

    /**
     * 밴사정보관리 - 상세 조회
     * @param   request
     * @param   response
     * @param   vanManageVo
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2024. 09. 13.
     */
    @RequestMapping(value = "/vanManage/getVanManageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVanManageList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, VanManageVO vanManageVo) {

        List<DefaultMap<String>> list = vanManageService.getVanManageList(vanManageVo);

        return ReturnUtil.returnListJson(Status.OK, list, vanManageVo);
    }

    /**
     * 밴사정보관리 - 밴사정보 저장
     *
     * @param vanManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2024. 09. 13.
     */
    @RequestMapping(value = "/vanManage/getVanSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVanSave(@RequestBody VanManageVO[] vanManageVOs, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vanManageService.getVanSave(vanManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 밴사정보관리 - 밴사정보 삭제
     * @param   vanManageVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.09.13
     */
    @RequestMapping(value = "/vanManage/getVanDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVanDelete(@RequestBody VanManageVO[] vanManageVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = vanManageService.getVanDelete(vanManageVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
