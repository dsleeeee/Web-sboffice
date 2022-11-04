package kr.co.solbipos.sale.appr.cashBill.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import kr.co.solbipos.sale.appr.cashBill.service.CashBillService;
import kr.co.solbipos.sale.appr.cashBill.service.CashBillVO;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;


/**
 * @Class Name : CashBillController.java
 * @Description : 맘스터치 > 승인관리2 > 현금영수증 승인 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.29  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/appr/cashBill")
public class CashBillController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final CashBillService cashBillService;
    private final StoreTypeService storeTypeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public CashBillController(SessionService sessionService, CashBillService cashBillService, StoreTypeService storeTypeService) {
        this.sessionService = sessionService;
        this.cashBillService = cashBillService;
        this.storeTypeService = storeTypeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/cashBill/list.sb", method = RequestMethod.GET)
    public String cashBillView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("hqBrandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        return "sale/appr/cashBill/cashBill";
    }

    /**
     * 현금영수증 승인 조회
     *
     * @param cashBillVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 권지현
     * @since 2022. 09. 29.
     */
    @RequestMapping(value = "/cashBill/getCashBillList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCashBillList(CashBillVO cashBillVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = cashBillService.getCashBillList(cashBillVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, cashBillVO);
    }

    /**
     * 현금영수증 승인 조회_엑셀
     *
     * @param cashBillVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 권지현
     * @since 2022. 09. 29.
     */
    @RequestMapping(value = "/cashBill/getCashBillExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCashBillExcelList(CashBillVO cashBillVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = cashBillService.getCashBillExcelList(cashBillVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, cashBillVO);
    }

    /**
     * spring poi를 이용한 엑셀 다운로드
     * @param cashBillVO
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/cashBill/poiExcelDown.sb", method = RequestMethod.POST)
    @ResponseBody
    public void poiExcelDown(CashBillVO cashBillVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = cashBillService.getCashBillExcelList(cashBillVO, sessionInfoVO);

        if(result != null && result.size() > 0) {

            final String fileName = "엑셀다운로드_테스트"; // 파일명
            final String[] header = {"No", "매장코드", "매장명", "포스번호", "영수증번호", "판매여부"}; // 헤더명
            final int[] colWidths = {1000, 3000, 5000, 1000, 2000, 2000}; // 컬럼 사이즈

            Workbook workbook = new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1"); // 시트명
            Cell cell = null;
            Row row = null;

            // region + 엑셀 츨력 스타일 정의
            // font style (header)
            Font fontHeader = workbook.createFont();
            fontHeader.setFontName("맑은 고딕");                //글씨체
            fontHeader.setFontHeight((short) (9 * 20));        //사이즈
            fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);    //볼드(굵게)

            // font style
            Font font9 = workbook.createFont();
            font9.setFontName("맑은 고딕");
            font9.setFontHeight((short) (9 * 20));

            // cell style (header)
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
            headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            headerStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headerStyle.setFont(fontHeader);

            // cell style
            CellStyle bodyStyle = workbook.createCellStyle();
            bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
            bodyStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            bodyStyle.setFont(font9);

            // cell style (left)
            CellStyle leftStyle = workbook.createCellStyle();
            leftStyle.setAlignment(CellStyle.ALIGN_LEFT);
            leftStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            leftStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            leftStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            leftStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            leftStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            leftStyle.setFont(font9);

            // cell style (number format)
            CellStyle numberCellStyle = workbook.createCellStyle();
            numberCellStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
            numberCellStyle.setFont(font9);
            // endregion

            // 엑셀 데이터 만들기
            int rowCnt = 0;
            int cellCnt = 0;
            int listCount = result.size();

            // header data set
            row = sheet.createRow(rowCnt++);
            for (int i = 0; i < header.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(header[i]);
                sheet.setColumnWidth(i, colWidths[i]);
            }

            // body data set
            for (DefaultMap<Object> rowResult : result) {
                cellCnt = 0;
                row = sheet.createRow(rowCnt++);

                // No
                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(listCount--);

                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(rowResult.getStr("storeCd"));

                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(rowResult.getStr("storeNm"));

                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(rowResult.getStr("posNo"));

                cell = row.createCell(cellCnt++);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(rowResult.getStr("billNo"));

                cell = row.createCell(cellCnt++);
                cell.setCellStyle(numberCellStyle);
                cell.setCellValue(rowResult.getStr("saleFg"));
            }

            // 엑셀 파일 만들기
            try {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
                workbook.write(response.getOutputStream());
                //workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            LOGGER.info("다운로드할 데이터 없음 - 데이터 있는 쿼리문작성해서 테스트 할 것");
        }
    }
}