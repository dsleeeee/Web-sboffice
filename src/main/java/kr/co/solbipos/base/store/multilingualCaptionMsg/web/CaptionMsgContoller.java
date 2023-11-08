package kr.co.solbipos.base.store.multilingualCaptionMsg.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.media.service.MediaVO;
import kr.co.solbipos.base.store.multilingualCaptionMsg.service.CaptionMsgService;
import kr.co.solbipos.base.store.multilingualCaptionMsg.service.CaptionMsgVO;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : CaptionMsgContoller.java
 * @Description : 기초관리 - 매장관리 - 다국어관리(기능키/메시지)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.11.03  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.11.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/store/multilingualCaptionMsg")
public class CaptionMsgContoller {

    private final SessionService sessionService;
    private final CaptionMsgService captionMsgService;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public CaptionMsgContoller(SessionService sessionService, CaptionMsgService captionMsgService, MessageService messageService) {
        this.sessionService = sessionService;
        this.captionMsgService = captionMsgService;
        this.messageService = messageService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 11. 03.
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/store/multilingualCaptionMsg/captionMsgTab";
    }

    /**
     * 화면구분등록 탭 리스트 조회
     *
     * @param captionMsgVO
     * @param request
     * @author  이다솜
     * @since   2023. 11. 03.
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCaptionMsgGrpList(CaptionMsgVO captionMsgVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = captionMsgService.getCaptionMsgGrpList(captionMsgVO, sessionInfoVO);

        return returnListJson(Status.OK, list, captionMsgVO);
    }

    /**
     * 화면구분 상세 조회
     * @param captionMsgVO
     * @param request
     * @author  이다솜
     * @since   2023. 11. 03.
     */
    @RequestMapping(value = "/dtlInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCaptionMsgGrpDtl(CaptionMsgVO captionMsgVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = captionMsgService.getCaptionMsgGrpDtl(captionMsgVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 화면구분 신규 등록
     * @param request
     * @author  이다솜
     * @since   2023. 11. 03.
     */
    @RequestMapping(value = "/regist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result regist(MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        String result = captionMsgService.regist(request, sessionInfo);

        if(result.equals("0")) {
            return returnJson(Status.OK);
        } else if(result.equals("1")) {
            return returnJson(Status.FAIL);
        } else if(result.equals("2")) {
            return returnJson(Status.FAIL, "msg", messageService.get("multilingualCaptionMsg.fileExtensionChk.msg"));
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 화면구분 수정
     * @param request
     * @author  이다솜
     * @since   2023. 11. 03.
     */
    @RequestMapping(value = "/modify.sb", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result modify(MultipartHttpServletRequest request){

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        String result = captionMsgService.modify(request, sessionInfo);

        if(result.equals("0")) {
            return returnJson(Status.OK);
        } else if(result.equals("1")) {
            return returnJson(Status.FAIL);
        } else if(result.equals("2")) {
            return returnJson(Status.FAIL, "msg", messageService.get("multilingualCaptionMsg.fileExtensionChk.msg"));
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 화면구분 삭제
     * @param captionMsgVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 11. 03.
     */
    @RequestMapping(value = "/delCaptionMsgGrp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result delCaptionMsgGrp(@RequestBody CaptionMsgVO[] captionMsgVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = captionMsgService.delCaptionMsgGrp(captionMsgVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     *
     * @param captionMsgVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 11. 03.
     */
    @RequestMapping(value="/download.sb")
    @ResponseBody
    public void download(CaptionMsgVO captionMsgVO, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        String reFileNM = captionMsgVO.getFileNm().replaceAll("../", "").replaceAll("/", "");

        //File file = new File("D:\\" + "Media/", reFileNM);
        File file = new File(BaseEnv.FILE_UPLOAD_DIR + "Media/", reFileNM);

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        // User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
        String header = request.getHeader("User-Agent");
        String orginlFileNm;
        String fileExt;

        if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
            // 인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
            orginlFileNm = URLEncoder.encode(captionMsgVO.getFileOrgNm(), "UTF-8");
            fileExt = URLEncoder.encode(captionMsgVO.getFileExt(), "UTF-8");
        } else {
            // 나머지 브라우저에서 인코딩
            orginlFileNm = new String(captionMsgVO.getFileOrgNm().getBytes("UTF-8"), "iso-8859-1");
            fileExt = new String(captionMsgVO.getFileExt().getBytes("UTF-8"), "iso-8859-1");
        }

        // 형식을 모르는 파일첨부용 contentType
        response.setContentType("application/octet-stream");

        // 다운로드와 다운로드될 파일이름
        response.setHeader("Content-Disposition", "attachment; filename=\""+ orginlFileNm + "." + fileExt + "\"");

        // 파일복사
        FileCopyUtils.copy(in, response.getOutputStream());
        in.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
