package kr.co.solbipos.sys.admin.adminMedia.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapService;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.store.media.service.MediaApplcStoreVO;
import kr.co.solbipos.base.store.media.service.MediaVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sys.admin.adminMedia.service.AdminMediaService;
import kr.co.solbipos.sys.admin.adminMedia.service.AdminMediaVO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

@Controller
@RequestMapping("/sys/admin/adminMedia")
public class AdminMediaController {

    private final SessionService sessionService;
    private final AdminMediaService adminMediaService;
    private final DayProdService dayProdService;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;
    private final KioskKeyMapService kioskKeyMapService;
    private final CmmCodeUtil cmmCodeUtil;

    public AdminMediaController(SessionService sessionService, AdminMediaService adminMediaService, DayProdService dayProdService, MessageService messageService, CmmEnvUtil cmmEnvUtil, KioskKeyMapService kioskKeyMapService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.adminMediaService = adminMediaService;
        this.dayProdService = dayProdService;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.kioskKeyMapService = kioskKeyMapService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 미디어관리 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/adminMedia/list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
                       Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
            model.addAttribute("posLoginReconnect", request.getParameter("posLoginReconnect"));
        }


        /** 파일타입 콤보박스 조회 */
        AdminMediaVO adminMediaVO = new AdminMediaVO();
        List<DefaultMap<String>> fileTypeComboList = adminMediaService.getFileTypeComboList(adminMediaVO, sessionInfoVO);
        String fileTypeComboList2 = cmmCodeUtil.assmblObj(fileTypeComboList, "name", "value", UseYn.N);
        model.addAttribute("fileTypeComboList", fileTypeComboList2);
        System.out.println("fileTypeComboList : " + fileTypeComboList2);
        String fileTypeComboListAll = cmmCodeUtil.assmblObj(fileTypeComboList, "name", "value", UseYn.ALL);
        model.addAttribute("fileTypeComboListAll", fileTypeComboListAll);
        System.out.println("fileTypeComboListAll : " + fileTypeComboListAll);


        return "sys/admin/adminMedia/adminMediaTab";
    }

    /**
     * 듀얼모니터영상관리 탭 - 조회
     *
     * @param   adminMediaVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 07.
     */
    @RequestMapping(value = "/adminMedia/getMediaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(AdminMediaVO adminMediaVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = adminMediaService.getMediaList(sessionInfoVO, adminMediaVO);

        return returnListJson(Status.OK, list, adminMediaVO);
    }

    /**
     * 듀얼모니터영상관리 탭 - 삭제
     *
     * @param   adminMediaVOS
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 07.
     */
    @RequestMapping(value = "/adminMedia/getMediaDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMediaDelete(@RequestBody AdminMediaVO[] adminMediaVOS, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = adminMediaService.getMediaDelete(adminMediaVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 파일등록 팝업 - 첨부파일 확장자 체크
     *
     * @param   adminMediaVO
     * @param   request
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 07.
     */
    @RequestMapping(value = "/getFileType.sb", method = RequestMethod.POST)
    @ResponseBody
    public String getFileType(AdminMediaVO adminMediaVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        return adminMediaService.getFileType(sessionInfoVO, adminMediaVO);
    }

    /**
     * 듀얼모니터영상관리 탭 - 파일등록
     *
     * @param   request
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 07.
     */
    @RequestMapping(value = "/verInfo/getRegistMedia.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRegistMedia(MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        String result = adminMediaService.getRegistMedia(request, sessionInfo);

        if(result.equals("0")) {
            return returnJson(Status.OK);
        } else if(result.equals("1")) {
            return returnJson(Status.FAIL);
        } else if(result.equals("2")) {
            return returnJson(Status.FAIL, "msg", messageService.get("prodImg.fileExtensionChk.msg"));
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 듀얼모니터영상관리 탭 - 파일수정
     *
     * @param   request
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 07.
     */
    @RequestMapping(value = "/verInfo/getModifyMedia.sb", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result getModifyMedia(MultipartHttpServletRequest request){

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        String result = adminMediaService.getModifyMedia(request, sessionInfo);
        if(result.equals("0")) {
            return returnJson(Status.OK);
        } else if(result.equals("1")) {
            return returnJson(Status.FAIL);
        } else if(result.equals("2")) {
            return returnJson(Status.FAIL, "msg", messageService.get("prodImg.fileExtensionChk.msg"));
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 듀얼모니터영상관리 탭 - 파일정보 상세 조회
     *
     * @param   adminMediaVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 07.
     */
    @RequestMapping(value = "/verInfo/dtlInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dtlInfo(AdminMediaVO adminMediaVO, HttpServletRequest request,
                          HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = adminMediaService.dtlInfo(sessionInfoVO, adminMediaVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 날짜 체크
     *
     * @param   adminMediaVO
     * @param   request
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 07.
     */
    @RequestMapping(value = "/chkDate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkDate(AdminMediaVO adminMediaVO, HttpServletRequest request) {

        if(!adminMediaVO.getUseYn().getCode().equals("N")) {
            SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
            if (!adminMediaService.chkFileType(sessionInfoVO, adminMediaVO).equals("0")) {  // 공통코드 303의 NMCODE_ITEM_1이 날짜중복불가이면 날짜체크
                String response = adminMediaService.chkDate(sessionInfoVO, adminMediaVO);
                if (!response.equals("0")) {
                    return returnJson(Status.FAIL);
                }
            }
        }
        return returnJson(Status.OK);
    }

    /**
     * 듀얼모니터영상관리 탭 - 파일등록 중복체크
     *
     * @param   adminMediaVO
     * @param   request
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 06.
     */
    @RequestMapping(value = "/chkDup.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkDup(AdminMediaVO adminMediaVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String dupCnt = adminMediaService.chkDupCnt(sessionInfoVO, adminMediaVO);

        if(!adminMediaVO.getUseYn().getCode().equals("N")) {
            if (dupCnt != null) {
                int chkFileTypeCnt = adminMediaService.chkFileTypeCnt(sessionInfoVO, adminMediaVO);
                if (chkFileTypeCnt >= Integer.parseInt(dupCnt)) {
                    return returnJson(Status.FAIL, dupCnt);
                }
            }
        }

        return returnJson(Status.OK, dupCnt);
    }


    /**
     * 재생순서관리 탭 - 조회
     *
     * @param   adminMediaVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 07.
     */
    @RequestMapping(value = "/adminMediaPlaySeq/getMediaPlaySeqList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMediaPlaySeqList(AdminMediaVO adminMediaVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = adminMediaService.getMediaPlaySeqList(adminMediaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, adminMediaVO);
    }

    /**
     * 재생순서관리 탭 - 저장
     *
     * @param   adminMediaVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 11.
     */
    @RequestMapping(value = "/adminMediaPlaySeq/getMediaPlaySeqSaveUpdate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMediaPlaySeqSaveUpdate(@RequestBody AdminMediaVO[] adminMediaVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = adminMediaService.getMediaPlaySeqSaveUpdate(adminMediaVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장등록 - 조회
     *
     * @param   adminMediaVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 20.
     */
    @RequestMapping(value = "/adminMedia/srchStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result srchStoreList(AdminMediaVO adminMediaVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        System.out.println(adminMediaVO.getHqOfficeCd() + "본사코드 컨트롤러");
        // 포스가 설치된 매장만 조회
        List<DefaultMap<String>> list = adminMediaService.srchStoreList(adminMediaVO, sessionInfoVO);

        return returnListJson(Status.OK, list, adminMediaVO);
    }

    /**
     * 매장 적용 - 파일 등록
     *
     * @param   applcStores
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 20.
     */
    @RequestMapping(value = "/adminMedia/getRegistStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRegistStore(@RequestBody MediaApplcStoreVO[] applcStores, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = adminMediaService.getRegistStore(applcStores, sessionInfo);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장 적용 - 파일 삭제
     *
     * @param   applcStores
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 03. 20.
     */
    @RequestMapping(value = "/adminMedia/getRemoveStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRemoveStore(@RequestBody MediaApplcStoreVO[] applcStores, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = adminMediaService.getRemoveStore(applcStores, sessionInfo);

        return returnJson(Status.OK, result);
    }
}
