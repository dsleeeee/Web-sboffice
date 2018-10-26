package kr.co.solbipos.pos.confg.vermanage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.vermanage.service.ApplcStoreVO;
import kr.co.solbipos.pos.confg.vermanage.service.VerInfoVO;
import kr.co.solbipos.pos.confg.vermanage.service.VerManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
* @Class Name : VerManageController.java
* @Description : 포스관리 > POS 설정관리 > POS 버전 관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/pos/confg/verManage")
public class VerManageController {

    private final VerManageService verManageService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public VerManageController(VerManageService verManageService, SessionService sessionService) {
        this.verManageService = verManageService;
        this.sessionService = sessionService;
    }

    /**
     * 버전정보 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/verInfo/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "pos/confg/vermanage/verManage";
    }

    /**
     * 버전정보 목록 조회
     *
     * @param verInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/verInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(VerInfoVO verInfo, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = verManageService.list(verInfo);

        return returnListJson(Status.OK, list, verInfo);
    }

    /**
     * 버전정보 상세 조회
     *
     * @param verInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/verInfo/dtlInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dtlInfo(VerInfoVO verInfo, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        DefaultMap<String> result = verManageService.dtlInfo(verInfo);

        return returnJson(Status.OK, result);
    }

    /**
     * 버전 삭제
     *
     * @param verInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/verInfo/remove.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(VerInfoVO verInfo, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        int result = verManageService.verDelete(verInfo);

        return returnJson(Status.OK, result);
    }

    /**
     * 버전 중복체크
     *
     * @param verInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/verInfo/chkVerSerNo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkSerNo(VerInfoVO verInfo, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        int cnt = verManageService.chkVerSerNo(verInfo);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 버전 등록
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/verInfo/regist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result regist(MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        if(verManageService.regist(request, sessionInfo)) {
            return returnJson(Status.OK);
        } else {
            return returnJson(Status.FAIL);
        }
    }


    /**
     * 버전 수정
     * @param request
     * @return
     */
    @RequestMapping(value = "/verInfo/modify.sb", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result modify(MultipartHttpServletRequest request){

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        if(verManageService.modify(request, sessionInfo)) {
            return returnJson(Status.OK);
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 매장 조회
     *
     * @param verInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/applcStore/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result storeList(VerInfoVO verInfo, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> result = verManageService.storeList(verInfo);

        return returnListJson(Status.OK, result);
    }

    /**
     * 매장추가 매장검색
     *
     * @param applcStore
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/applcStore/srchStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result srchStoreList(ApplcStoreVO applcStore, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        // 포스가 설치된 매장만 조회
        List<DefaultMap<String>> list = verManageService.srchStoreList(applcStore);

        return returnListJson(Status.OK, list, applcStore);
    }

    /**
     * 버전 적용 매장 등록
     *
     * @param applcStore
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/applcStore/regist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result registStore(@RequestBody ApplcStoreVO[] applcStore, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = verManageService.registStore(applcStore, sessionInfo);

        return returnJson(Status.OK, result);
    }

    /**
     * 버전 적용 매장 삭제
     *
     * @param applcStore
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/applcStore/removeStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result removeStore(@RequestBody ApplcStoreVO[] applcStore, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = verManageService.removeStore(applcStore, sessionInfo);

        return returnJson(Status.OK, result);
    }
}
