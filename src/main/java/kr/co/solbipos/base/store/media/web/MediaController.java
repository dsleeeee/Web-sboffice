package kr.co.solbipos.base.store.media.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapService;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.store.media.service.MediaApplcStoreVO;
import kr.co.solbipos.base.store.media.service.MediaService;
import kr.co.solbipos.base.store.media.service.MediaVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : mediaController.java
 * @Description : 기초관리 > 매장관리 > 미디어관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.09  권지현      최초생성
 *
 *
 * @author 솔비포스 web개발팀 권지현
 * @since 2021.06.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/base/store/media")
public class MediaController {

    private final SessionService sessionService;
    private final MediaService mediaService;
    private final DayProdService dayProdService;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;
    private final KioskKeyMapService kioskKeyMapService;
    private final CmmCodeUtil cmmCodeUtil;

    public MediaController(SessionService sessionService, MediaService mediaService, DayProdService dayProdService, MessageService messageService, CmmEnvUtil cmmEnvUtil, KioskKeyMapService kioskKeyMapService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.mediaService = mediaService;
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
    @RequestMapping(value = "/media/list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
                       Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
            model.addAttribute("posLoginReconnect", request.getParameter("posLoginReconnect"));
        }

        KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
        // 키오스크 키맵그룹 조회
        List<DefaultMap<String>> kioskTuClsTypeList = kioskKeyMapService.getKioskTuClsTypeList(kioskKeyMapVO, sessionInfoVO);
        String kioskTuClsTypeListAll = "";
        if (kioskTuClsTypeList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "선택");
            m.put("value", "");
            list.add(m);
            kioskTuClsTypeListAll = convertToJson(list);
        } else {
            kioskTuClsTypeListAll = cmmCodeUtil.assmblObj(kioskTuClsTypeList, "name", "value", UseYn.SELECT);
        }
        model.addAttribute("kioskTuClsTypeListAll", kioskTuClsTypeListAll);


        /** 파일타입 콤보박스 조회 */
        MediaVO mediaVO = new MediaVO();
        List<DefaultMap<String>> fileTypeComboList = mediaService.getFileTypeComboList(mediaVO, sessionInfoVO);
        String fileTypeComboList2 = cmmCodeUtil.assmblObj(fileTypeComboList, "name", "value", UseYn.N);
        model.addAttribute("fileTypeComboList", fileTypeComboList2);
        System.out.println("fileTypeComboList : " + fileTypeComboList2);
        String fileTypeComboListAll = cmmCodeUtil.assmblObj(fileTypeComboList, "name", "value", UseYn.ALL);
        model.addAttribute("fileTypeComboListAll", fileTypeComboListAll);
        System.out.println("fileTypeComboListAll : " + fileTypeComboListAll);


        return "base/store/media/mediaTab";
    }

    /**
     * 미디어 목록 조회
     *
     * @param mediaVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/media/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(MediaVO mediaVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mediaService.list(sessionInfoVO, mediaVO);

        return returnListJson(Status.OK, list, mediaVO);
    }

    /**
     * 버전정보 상세 조회
     *
     * @param
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/verInfo/dtlInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dtlInfo(MediaVO mediaVO, HttpServletRequest request,
                          HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = mediaService.dtlInfo(sessionInfoVO, mediaVO);

        return returnJson(Status.OK, result);
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

        String result = mediaService.regist(request, sessionInfo);

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
     * 첨부파일 확장자
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getFileType.sb", method = RequestMethod.POST)
    @ResponseBody
    public String getFileType(MediaVO mediaVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        return mediaService.getFileType(sessionInfoVO, mediaVO);
    }

    /**
     * 날짜 체크
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/chkDate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkDate(MediaVO mediaVO, HttpServletRequest request) {

        if(!mediaVO.getUseYn().getCode().equals("N")) {
            SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
            if (!mediaService.chkFileType(sessionInfoVO, mediaVO).equals("0")) {  // 공통코드 303의 NMCODE_ITEM_1이 날짜중복불가이면 날짜체크
                String response = mediaService.chkDate(sessionInfoVO, mediaVO);
                if (!response.equals("0")) {
                    return returnJson(Status.FAIL);
                }
            }
        }
        return returnJson(Status.OK);
    }


    /**
     * 버전 수정
     * @param request
     * @return
     */
    @RequestMapping(value = "/verInfo/modify.sb", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result modify(MultipartHttpServletRequest request){

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        String result = mediaService.modify(request, sessionInfo);
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
    public Result srchStoreList(MediaApplcStoreVO applcStore, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        // 포스가 설치된 매장만 조회
        List<DefaultMap<String>> list = mediaService.srchStoreList(applcStore, sessionInfo);

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
    public Result registStore(@RequestBody MediaApplcStoreVO[] applcStore, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = mediaService.registStore(applcStore, sessionInfo);

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
    public Result removeStore(@RequestBody MediaApplcStoreVO[] applcStore, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = mediaService.removeStore(applcStore, sessionInfo);

        return returnJson(Status.OK, result);
    }

    /**
     * 듀얼모니터영상관리 탭 - 삭제
     *
     * @param mediaVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 10. 17.
     */
    @RequestMapping(value = "/media/getMediaDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMediaDelete(@RequestBody MediaVO[] mediaVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = mediaService.getMediaDelete(mediaVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 재생순서관리 탭 - 조회
     *
     * @param mediaVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 10. 17.
     */
    @RequestMapping(value = "/mediaPlaySeq/getMediaPlaySeqList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMediaPlaySeqList(MediaVO mediaVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mediaService.getMediaPlaySeqList(mediaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mediaVO);
    }

    /**
     * 재생순서관리 탭 - 저장
     *
     * @param mediaVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 10. 17.
     */
    @RequestMapping(value = "/mediaPlaySeq/getMediaPlaySeqSaveUpdate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMediaPlaySeqSaveUpdate(@RequestBody MediaVO[] mediaVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = mediaService.getMediaPlaySeqSaveUpdate(mediaVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 첨부파일 다운로드
     *
     * @param mediaVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2023. 08. 07.
     */
    @RequestMapping(value="/media/download.sb")
    @ResponseBody
    public void download(MediaVO mediaVO, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

//System.out.println("kjs: request.getParameter(fileNm): "  + request.getParameter("fileNm"));
System.out.println("kjs: boardVO.getFileNm() : "  + mediaVO.getFileNm());
//        File file = new File("D:\\Workspace\\javaWeb\\testBoardAtch\\", boardVO.getFileNm());
        String reFileNM = mediaVO.getFileNm().replaceAll("../", "").replaceAll("/", "");
System.out.println("kjs: reFileNM : " + reFileNM);

        File file = new File(BaseEnv.FILE_UPLOAD_DIR + "Media/", reFileNM);

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        //User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
        String header = request.getHeader("User-Agent");
        String fileName;
        String orginlFileNm;
        String fileExt;
        if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
            //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
            fileName = URLEncoder.encode(reFileNM, "UTF-8");
            orginlFileNm = URLEncoder.encode(mediaVO.getOrginlFileNm(), "UTF-8");
            fileExt = URLEncoder.encode(mediaVO.getFileExt(), "UTF-8");
        } else {
            //나머지 브라우저에서 인코딩
            fileName = new String(reFileNM.getBytes("UTF-8"), "iso-8859-1");
            orginlFileNm = new String(mediaVO.getOrginlFileNm().getBytes("UTF-8"), "iso-8859-1");
            fileExt = new String(mediaVO.getFileExt().getBytes("UTF-8"), "iso-8859-1");
        }

        //형식을 모르는 파일첨부용 contentType
        response.setContentType("application/octet-stream");
        //다운로드와 다운로드될 파일이름
//        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ orginlFileNm + "." + fileExt + "\"");
        //파일복사
        FileCopyUtils.copy(in, response.getOutputStream());
        in.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
