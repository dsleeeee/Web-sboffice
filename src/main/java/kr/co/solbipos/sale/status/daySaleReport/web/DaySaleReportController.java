package kr.co.solbipos.sale.status.daySaleReport.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.daySaleReport.service.DaySaleReportService;
import kr.co.solbipos.sale.status.daySaleReport.service.DaySaleReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : DaySaleReportController.java
 * @Description : 매출관리 > 매출현황2 > 일별매출내역 다운로드(제너시스올떡 분식대장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.12.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.12.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/status/daySaleReport")
public class DaySaleReportController {

    private final SessionService sessionService;
    private final DaySaleReportService daySaleReportService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DaySaleReportController(SessionService sessionService, DaySaleReportService daySaleReportService) {
        this.sessionService = sessionService;
        this.daySaleReportService = daySaleReportService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/daySaleReport/list.sb", method = RequestMethod.GET)
    public String daySaleReportView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/status/daySaleReport/daySaleReport";
    }

    /**
     * 일별매출내역 다운로드 - 조회
     *
     * @param daySaleReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 08.
     */
    @RequestMapping(value = "/daySaleReport/getDaySaleReportList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDaySaleReportList(DaySaleReportVO daySaleReportVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = daySaleReportService.getDaySaleReportList(daySaleReportVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, daySaleReportVO);
    }

    /**
     * 일별매출내역 다운로드 - 자료생성 저장
     *
     * @param daySaleReportVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 08.
     */
    @RequestMapping(value = "/daySaleReport/getDaySaleReportSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDaySaleReportSave(@RequestBody DaySaleReportVO[] daySaleReportVOs, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = daySaleReportService.getDaySaleReportSave(daySaleReportVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 일별매출내역 다운로드 - 삭제
     *
     * @param daySaleReportVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 08.
     */
    @RequestMapping(value = "/daySaleReport/getDaySaleReportDel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDaySaleReportDel(@RequestBody DaySaleReportVO[] daySaleReportVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = daySaleReportService.getDaySaleReportDel(daySaleReportVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 일별매출내역 다운로드 - 자료생성 요청건 존재여부 확인
     *
     * @param daySaleReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 08.
     */
    @RequestMapping(value = "/daySaleReport/getDaySaleReportChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDaySaleReportChk(DaySaleReportVO daySaleReportVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = daySaleReportService.getDaySaleReportChk(daySaleReportVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 일별매출내역 다운로드 - 첨부파일 다운로드
     *
     * @param daySaleReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 08.
     */
    @RequestMapping(value="/daySaleReport/getDaySaleReportDownload.sb")
    @ResponseBody
    public void getDaySaleReportDownload(DaySaleReportVO daySaleReportVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) throws Exception {

//        System.out.println("test1111 : " + daySaleReportVO.getFileName());

//        File file = new File("D:\\Workspace\\javaWeb\\testSaleReport\\", daySaleReportVO.getFileName());
        File file = new File(BaseEnv.FILE_UPLOAD_DIR + "/MediaBase/SaleReport/", daySaleReportVO.getFileName());

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        //User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
        String header = request.getHeader("User-Agent");
        String fileName;
        if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
            //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
            fileName = URLEncoder.encode(daySaleReportVO.getFileName(), "UTF-8");
        } else {
            //나머지 브라우저에서 인코딩
            fileName = new String(daySaleReportVO.getFileName().getBytes("UTF-8"), "iso-8859-1");
        }

        //형식을 모르는 파일첨부용 contentType
        response.setContentType("application/octet-stream");
        //다운로드와 다운로드될 파일이름
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
        //파일복사
        FileCopyUtils.copy(in, response.getOutputStream());
        in.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}