package kr.co.solbipos.sale.status.storeProdSaleReport.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.storeProdSaleReport.service.StoreProdSaleReportService;
import kr.co.solbipos.sale.status.storeProdSaleReport.service.StoreProdSaleReportVO;
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

@Controller
@RequestMapping("/sale/status/storeProdSaleReport")
public class StoreProdSaleReportController {

    private final SessionService sessionService;
    private final StoreProdSaleReportService storeProdSaleReportService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreProdSaleReportController(SessionService sessionService, StoreProdSaleReportService storeProdSaleReportService) {
        this.sessionService = sessionService;
        this.storeProdSaleReportService = storeProdSaleReportService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/storeProdSaleReport/list.sb", method = RequestMethod.GET)
    public String storeProdSaleReportView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/status/storeProdSaleReport/storeProdSaleReportTab";
    }

    /**
     * 기간별 매장-상품 매출 다운로드 탭 - 조회
     *
     * @param storeProdSaleReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 01.
     */
    @RequestMapping(value = "/storeProdSaleReport/getStoreProdSaleReportList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreProdSaleReportList(StoreProdSaleReportVO storeProdSaleReportVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeProdSaleReportService.getStoreProdSaleReportList(storeProdSaleReportVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeProdSaleReportVO);
    }

    /**
     * 기간별 매장-상품 매출 다운로드 탭 - 자료생성 저장
     *
     * @param storeProdSaleReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 01.
     */
    @RequestMapping(value = "/storeProdSaleReport/getStoreProdSaleReportSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreProdSaleReportSave(@RequestBody StoreProdSaleReportVO storeProdSaleReportVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeProdSaleReportService.getStoreProdSaleReportSave(storeProdSaleReportVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 기간별 매장-상품 매출 다운로드 탭 - 삭제
     *
     * @param storeProdSaleReportVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 01.
     */
    @RequestMapping(value = "/storeProdSaleReport/getStoreProdSaleReportDel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreProdSaleReportDel(@RequestBody StoreProdSaleReportVO[] storeProdSaleReportVOs, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeProdSaleReportService.getStoreProdSaleReportDel(storeProdSaleReportVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 기간별 매장-상품 매출 다운로드 탭 - 자료생성 요청건 존재여부 확인
     *
     * @param storeProdSaleReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 01.
     */
    @RequestMapping(value = "/storeProdSaleReport/getStoreProdSaleReportChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileDaySaleTotalList(StoreProdSaleReportVO storeProdSaleReportVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = storeProdSaleReportService.getStoreProdSaleReportChk(storeProdSaleReportVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 기간별 매장-상품 매출 다운로드 탭 - 첨부파일 다운로드
     *
     * @param storeProdSaleReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 01.
     */
    @RequestMapping(value="/storeProdSaleReport/getStoreProdSaleReportDownload.sb")
    @ResponseBody
    public void getStoreProdSaleReportDownload(StoreProdSaleReportVO storeProdSaleReportVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) throws Exception {

//        System.out.println("test1111 : " + storeProdSaleReportVO.getFileName());

//        File file = new File("D:\\Workspace\\javaWeb\\testSaleReport\\", storeProdSaleReportVO.getFileName());
        File file = new File(BaseEnv.FILE_UPLOAD_DIR + "/MediaBase/SaleReport/", storeProdSaleReportVO.getFileName());

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        //User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
        String header = request.getHeader("User-Agent");
        String fileName;
        if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
            //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
            fileName = URLEncoder.encode(storeProdSaleReportVO.getFileName(), "UTF-8");
        } else {
            //나머지 브라우저에서 인코딩
            fileName = new String(storeProdSaleReportVO.getFileName().getBytes("UTF-8"), "iso-8859-1");
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

    /**
     * 지사-지역관리 탭 - 지사 조회
     *
     * @param storeProdSaleReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 02.
     */
    @RequestMapping(value = "/branchArea/getBranchAreaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBranchAreaList(StoreProdSaleReportVO storeProdSaleReportVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeProdSaleReportService.getBranchAreaList(storeProdSaleReportVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeProdSaleReportVO);
    }

    /**
     * 지사-지역관리 탭 - 지사 저장
     *
     * @param storeProdSaleReportVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 02.
     */
    @RequestMapping(value = "/branchArea/getBranchAreaSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBranchAreaSave(@RequestBody StoreProdSaleReportVO[] storeProdSaleReportVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeProdSaleReportService.getBranchAreaSave(storeProdSaleReportVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 지사-지역관리 탭 - 지역 조회
     *
     * @param storeProdSaleReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 02.
     */
    @RequestMapping(value = "/branchArea/getBranchAreaDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBranchAreaDetailList(StoreProdSaleReportVO storeProdSaleReportVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeProdSaleReportService.getBranchAreaDetailList(storeProdSaleReportVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeProdSaleReportVO);
    }

    /**
     * 지사-지역관리 탭 - 지역 저장
     *
     * @param storeProdSaleReportVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 02.
     */
    @RequestMapping(value = "/branchArea/getBranchAreaDetailSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBranchAreaDetailSave(@RequestBody StoreProdSaleReportVO[] storeProdSaleReportVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeProdSaleReportService.getBranchAreaDetailSave(storeProdSaleReportVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 지역-매장관리 탭 - 지역 조회
     *
     * @param storeProdSaleReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 03.
     */
    @RequestMapping(value = "/areaStoreMapping/getAreaStoreMappingList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAreaStoreMappingList(StoreProdSaleReportVO storeProdSaleReportVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeProdSaleReportService.getAreaStoreMappingList(storeProdSaleReportVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeProdSaleReportVO);
    }

    /**
     * 지역-매장관리 탭 - 지역-매장 조회
     *
     * @param storeProdSaleReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 03.
     */
    @RequestMapping(value = "/areaStoreMapping/getAreaStoreMappingDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAreaStoreMappingDetailList(StoreProdSaleReportVO storeProdSaleReportVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeProdSaleReportService.getAreaStoreMappingDetailList(storeProdSaleReportVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeProdSaleReportVO);
    }

    /**
     * 지역-매장관리 탭 - 매장 조회
     *
     * @param storeProdSaleReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 03.
     */
    @RequestMapping(value = "/areaStoreMapping/getAreaStoreMappingStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAreaStoreMappingStoreList(StoreProdSaleReportVO storeProdSaleReportVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeProdSaleReportService.getAreaStoreMappingStoreList(storeProdSaleReportVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeProdSaleReportVO);
    }

    /**
     * 지사-지역관리 탭 - 지역-매장 저장 delete
     *
     * @param storeProdSaleReportVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 03.
     */
    @RequestMapping(value = "/areaStoreMapping/getAreaStoreMappingDetailSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAreaStoreMappingDetailSave(@RequestBody StoreProdSaleReportVO[] storeProdSaleReportVOs, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeProdSaleReportService.getAreaStoreMappingDetailSave(storeProdSaleReportVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 지사-지역관리 탭 - 지역-매장 저장
     *
     * @param storeProdSaleReportVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 03.
     */
    @RequestMapping(value = "/areaStoreMapping/getAreaStoreMappingStoreSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAreaStoreMappingStoreSave(@RequestBody StoreProdSaleReportVO[] storeProdSaleReportVOs, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeProdSaleReportService.getAreaStoreMappingStoreSave(storeProdSaleReportVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}