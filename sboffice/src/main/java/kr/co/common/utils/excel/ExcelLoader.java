package kr.co.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.common.XMLChar;
import org.springframework.stereotype.Service;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.spring.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 엑셀 생성 Class
 * 
 * @author bjcho
 */

@Slf4j
@Service
public class ExcelLoader implements ResultHandler<T> {

    /* ---------------------------------------------------------------------- */
    /* external variable */
    /* ---------------------------------------------------------------------- */
    private ExlSheetMaker exSheetMaker;
    private XSSFWorkbook exWorkbook;
    private XSSFSheet exSheet;
    private Map<String, XSSFCellStyle> exmStyle;
    private LinkedHashMap<String, String> exmExl;
    private LinkedHashMap<String, String> exmColData;
    private LinkedHashMap<String, String> exmColSize;
    private LinkedHashMap<String, String> exmColMask;
    private LinkedList<String> exmColMerge;
    private int exnRowIdx;
    private int exnColIdx;
    private File exoXMLTempfile;
    private FileOutputStream oFOS;
    private Writer oWriter;
    private File tempDir;

    private String fullPath = "";
    private String fileName = "";
    private String tempFileName = "";

    private int totalDataCount;

    /* ---------------------------------------------------------------------- */
    /*
     * Use Example; /* /* try /* { /* ExcelLoader excelLoader = new ExcelLoader( filename,
     * sheetTitle ); /* excelLoader.addHeader("tax_no" , "사업자번호", excelLoader.CELL_TYPE_FORMULA, 20,
     * "" ); /* excelLoader.addHeader("term_name" , "가맹점명" , excelLoader.CELL_TYPE_FORMULA, 20, ""
     * ); /* excelLoader.addHeader("total_mony", "총금액" , excelLoader.CELL_TYPE_COMMA_NUMBER, 25, ""
     * ); /* /* exlDown.openExcel(); /* //DB Handle.... Dao.queryWithRowHandler( ...QueryId, ...Map,
     * excelLoader ); 이 부분은 개발자가 구현 /* exLDown.closeExcel(); /* } /* catch( Exception e ) /* { /* }
     * /* ----------------------------------------------------------------------
     */


    /**
     * 엑셀 생성자 초기화
     * 
     * @param fileName String
     * @param sheetTitle String
     * @return ExcelLoader
     */
    public ExcelLoader init(String fileName, String sheetTitle) {
        exmExl = new LinkedHashMap<String, String>();
        exmColData = new LinkedHashMap<String, String>();
        exmColSize = new LinkedHashMap<String, String>();
        exmColMask = new LinkedHashMap<String, String>();
        exmColMerge = new LinkedList<String>();
        exWorkbook = new XSSFWorkbook();
        exnRowIdx = 0;
        exnColIdx = 0;

        this.fullPath = StringUtil.removeTrailingSlash(BaseEnv.EXCEL_TEMP_DIR);
        this.fileName = fileName;
        this.tempFileName = "temp_" + fileName;

        exSheet = exWorkbook.createSheet(sheetTitle);
        exmStyle = createStyles(exWorkbook);

        tempDir = new File(fullPath);
        if (!tempDir.isDirectory())
            tempDir.mkdirs();

        totalDataCount = 0;

        return this;
    }

    /**
     * 임시 워크시크 생성 및 엑셀 생성 준비
     */
    public void openExcel() throws Exception {
        try {
            /* -------------------------------------------------------------- */
            /*
             * 1. save the template /*
             * --------------------------------------------------------------
             */
            oFOS = new FileOutputStream(this.fullPath + "/" + this.tempFileName + ".xlsx");
            exWorkbook.write(oFOS);

            /* -------------------------------------------------------------- */
            /*
             * 2. Generate XML file /*
             * --------------------------------------------------------------
             */
            exoXMLTempfile =
                    File.createTempFile(this.fullPath + "/" + this.tempFileName, ".xml", tempDir);
            oWriter = new OutputStreamWriter(new FileOutputStream(exoXMLTempfile), "UTF-8");
            exSheetMaker = new ExlSheetMaker(oWriter, exmColSize, exmColMerge);
            exSheetMaker.beginSheet();

        } catch (Exception e) {
            oFOS.close();
            oWriter.close();
            throw new RuntimeException(e);
        } finally {
        }
    }



    /**
     * 임시 워크시크 종료 및 Exl File Write
     */
    public void closeExcel() throws Exception {
        FileOutputStream out = null;

        try {
            /* -------------------------------------------------------------- */
            /*
             * 3. Substitute the template entry with the generated data /*
             * --------------------------------------------------------------
             */
            exSheetMaker.endSheet();
            oWriter.close();

            String sheetRef = exSheet.getPackagePart().getPartName().getName();

            File excelFile = new File(this.fullPath + "/" + this.fileName + ".xlsx");
            out = new FileOutputStream(excelFile);

            File tempFile = new File(this.fullPath + "/" + this.tempFileName + ".xlsx");
            substitute(tempFile, exoXMLTempfile, sheetRef.substring(1), out);

            tempFile.delete();
        } finally {
            exoXMLTempfile.delete();
            oFOS.close();
            oWriter.close();
            out.flush();
            out.close();
        }
    }


    /**
     * total data count getter
     * 
     * @return int
     */
    public int getTotalDataCount() {
        return totalDataCount;
    }

    /**
     * HEADER 컬럼 추가
     * 
     * @param key String
     * @param cellTitle String
     * @param cellType short
     * @param cellWidth int
     * @param masking String
     */
    public void addHeader(String key, String cellTitle, short cellType, int cellWidth,
            String masking) {
        // 헤더문구 내 줄바꿈 태그 제거
        if (!StringUtil.isEmpty(cellTitle) && ((cellTitle.toLowerCase()).indexOf("<br/>") > -1
                || (cellTitle.toLowerCase()).indexOf("<br>") > -1)) {
            cellTitle = (cellTitle.toLowerCase()).replace("<br>", "");
            cellTitle = (cellTitle.toLowerCase()).replace("<br/>", "");
        }

        exmExl.put(key, cellTitle);
        /* ------------------------------------------------------------------ */
        /*
         * cellWidth 255 글자 이상 입력 하면 에러 발생 함. 예외처리 안함. /*
         * ------------------------------------------------------------------
         */
        exmColSize.put(String.valueOf(exnColIdx), String.valueOf(cellWidth));
        exmColData.put(String.valueOf(exnColIdx), String.valueOf(cellType));
        exmColMask.put(String.valueOf(exnColIdx), String.valueOf(masking));
        exnColIdx++;
    }


    /**
     * 엑셀 CELL MERGE 추가
     * 
     * @param cell1 String
     * @param cell2 String
     */
    public void addMerge(String cell1, String cell2) {
        exmColMerge.add(cell1 + ":" + cell2);
    }


    /**
     * 엑셀 헤더를 추가
     * 
     * @param headerMap HashMap
     */
    @SuppressWarnings("rawtypes")
    public void addHeaderRow(HashMap headerMap) throws Exception {
        addRow((Map) headerMap, this.exmStyle.get("header"));
    }

    /**
     * Mybatis ResultHandler 실행 Method 상속받음
     * 
     * @param paramResultContext ResultContext
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void handleResult(ResultContext paramResultContext) {
        Map dataMap = (Map) paramResultContext.getResultObject();
        try {
            if (exnRowIdx == 0) {
                createSheetTitle();
            }
            addRow(dataMap);

            this.totalDataCount++;
            log.debug(this.totalDataCount + ":" + dataMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * title row 작성
     */
    private void createSheetTitle() throws Exception {
        addRow(exmExl, exmStyle.get("header"));
    }



    /**
     * row 처리
     * 
     * @param dataMap Map dataMap
     */
    @SuppressWarnings({"rawtypes"})
    private void addRow(Map dataMap) throws Exception {
        addRow(dataMap, null);
    }



    /**
     * row 생성
     * 
     * @param dataMap Map
     * @param cellStyle XSSFCellStyle
     */
    @SuppressWarnings("rawtypes")
    private void addRow(Map dataMap, XSSFCellStyle cellStyle) throws Exception {
        Iterator keyData = exmExl.keySet().iterator();
        int i = 0;

        exSheetMaker.insertRow(exnRowIdx);
        while (keyData.hasNext()) {
            String key = (String) keyData.next();
            Object oValue = dataMap.get(key);
            String value = (oValue == null) ? "" : oValue.toString();
            // value = UtlString.clean(value);

            if (cellStyle != null) {
                exSheetMaker.createCell(i, value, cellStyle.getIndex());
            } else {
                /* ------------------------------------------------------------------ */
                /*
                 * 신용카드 및 개인정보 엑셀 다운로드시 마스킹 처리 /*
                 * ------------------------------------------------------------------
                 */
                if ("Y".equals(exmColMask.get(String.valueOf(i)))) {
                    // ErrInfo errInfo = new ErrInfo();
                    // if(!StringUtil.isEmpty(value)){
                    //
                    // value = KMCCrypto.getMaskedData( value, errInfo );
                    // }
                }
                if (isStringDouble(value)) {
                    switch (exmColData.get(String.valueOf(i))) {
                        case "20":
                            exSheetMaker.createCell(i, Long.parseLong(value),
                                    exmStyle.get(exmColData.get(String.valueOf(i))).getIndex());
                            break;
                        case "40":
                            exSheetMaker.createCell(i, Double.parseDouble(value),
                                    exmStyle.get(exmColData.get(String.valueOf(i))).getIndex());
                            break;
                        case "130":
                            double date = DateUtil.getExcelDate(
                                    kr.co.common.utils.DateUtil.string2date(value, "yyyyMMdd"));
                            exSheetMaker.createCell(i, date,
                                    exmStyle.get(exmColData.get(String.valueOf(i))).getIndex());
                            break;
                        case "140":
                            double datetime = DateUtil.getExcelDate(kr.co.common.utils.DateUtil
                                    .string2date(value, "yyyyMMddHHmmss"));
                            exSheetMaker.createCell(i, datetime,
                                    exmStyle.get(exmColData.get(String.valueOf(i))).getIndex());
                            break;
                        default:
                            exSheetMaker.createCell(i, value,
                                    exmStyle.get(exmColData.get(String.valueOf(i))).getIndex());
                    }
                } else {
                    exSheetMaker.createCell(i, value,
                            exmStyle.get(exmColData.get(String.valueOf(i))).getIndex());
                }
            }
            i++;
        }
        exSheetMaker.endRow();
        exnRowIdx++;
    }

    /**
     * 문자열이 더블형인지 확인
     * 
     * @param s String
     * @return boolean
     */
    public static boolean isStringDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 엑셀로 변환
     * 
     * @param zipfile File
     * @param tmpfile File
     * @param entry String
     * @param out OutputStream
     */
    protected void substitute(File zipfile, File tmpfile, String entry, OutputStream out)
            throws Exception {
        InputStream is = null;
        ZipFile zip = new ZipFile(zipfile);
        ZipOutputStream zos = new ZipOutputStream(out);

        try {
            @SuppressWarnings("unchecked")
            Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();

            while (en.hasMoreElements()) {
                ZipEntry ze = en.nextElement();
                if (!ze.getName().equals(entry)) {
                    zos.putNextEntry(new ZipEntry(ze.getName()));
                    InputStream oIS = zip.getInputStream(ze);
                    copyStream(oIS, zos);
                    oIS.close();
                }
            }
            zos.putNextEntry(new ZipEntry(entry));
            is = new FileInputStream(tmpfile);
            copyStream(is, zos);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            is.close();
            zip.close();
            zos.finish();
            zos.close();
        }
    }



    /**
     * Stream 데이터 복사
     * 
     * @param in InputStream
     * @param out OutputStream
     */
    protected void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >= 0) {
            out.write(chunk, 0, count);
        }
    }



    /**
     * 셀 스타일 생성
     * 
     * @param wb XSSFWorkbook
     * @return Map&lt;String, XSSFCellStyle&gt;
     */
    protected Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb) {
        Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
        XSSFDataFormat fmt = wb.createDataFormat();


        /* ------------------------------------------------------------------- */
        /*
         * 타이틀 항목 포맷 /* -------------------------------------------------------------------
         */
        XSSFCellStyle style_title = wb.createCellStyle();
        XSSFFont font_title = wb.createFont();
        font_title.setBold(true);
        font_title.setFontHeight(9);
        font_title.setFontName(BaseEnv.EXCEL_FONT);
        style_title.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style_title.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style_title.setBorderRight(XSSFCellStyle.BORDER_THIN); // 테두리 설정
        style_title.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style_title.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style_title.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style_title.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style_title.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style_title.setFont(font_title);
        styles.put("header", style_title);



        /* ------------------------------------------------------------------- */
        /*
         * 데이터 폰트 사이즈 /* -------------------------------------------------------------------
         */
        XSSFFont font_data = wb.createFont();
        font_data.setFontHeight(9);
        font_data.setFontName(BaseEnv.EXCEL_FONT);


        // Cell Style 지정
        XSSFCellStyle style_data = null;



        /* ------------------------------------------------------------------- */
        /*
         * CELL_TYPE_STRING /* -------------------------------------------------------------------
         */
        style_data = wb.createCellStyle();
        style_data.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style_data.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style_data.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style_data.setFont(font_data);
        style_data.setDataFormat(fmt.getFormat("General"));
        style_data.setWrapText(true);
        styles.put("10", style_data);

        /* ------------------------------------------------------------------- */
        /*
         * CELL_TYPE_COMMA_NUMBER /*
         * -------------------------------------------------------------------
         */
        style_data = wb.createCellStyle();
        style_data.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style_data.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style_data.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style_data.setFont(font_data);
        style_data.setDataFormat(fmt.getFormat("#,##0"));
        styles.put("20", style_data);

        /* ------------------------------------------------------------------- */
        /*
         * CELL_TYPE_NUMBER /* -------------------------------------------------------------------
         */
        style_data = wb.createCellStyle();
        style_data.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style_data.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style_data.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style_data.setFont(font_data);
        style_data.setDataFormat(fmt.getFormat("0"));
        styles.put("30", style_data);

        /* ------------------------------------------------------------------- */
        /*
         * CELL_TYPE_DOUBLE /* -------------------------------------------------------------------
         */
        style_data = wb.createCellStyle();
        style_data.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style_data.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style_data.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style_data.setFont(font_data);
        style_data.setDataFormat(fmt.getFormat("#,##0.00"));
        styles.put("40", style_data);

        /* ------------------------------------------------------------------- */
        /*
         * CELL_TYPE_PERCENT /* -------------------------------------------------------------------
         */
        style_data = wb.createCellStyle();
        style_data.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style_data.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style_data.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style_data.setFont(font_data);
        style_data.setDataFormat(fmt.getFormat("0.0%"));
        styles.put("50", style_data);



        /* ------------------------------------------------------------------- */
        /*
         * CELL_TYPE_FORMULA /* -------------------------------------------------------------------
         */
        style_data = wb.createCellStyle();
        style_data.setBorderRight(XSSFCellStyle.BORDER_THIN); // 테두리 설정
        style_data.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style_data.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        style_data.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style_data.setFont(font_data);
        styles.put("60", style_data);



        /* ------------------------------------------------------------------- */
        /*
         * CELL_TYPE_FORMULA_RIGHT /*
         * -------------------------------------------------------------------
         */
        style_data = wb.createCellStyle();
        style_data.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style_data.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style_data.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style_data.setFont(font_data);
        style_data.setDataFormat(fmt.getFormat("General"));
        styles.put("90", style_data);

        /* ------------------------------------------------------------------- */
        /*
         * CELL_TYPE_DATE /* -------------------------------------------------------------------
         */
        style_data = wb.createCellStyle();
        style_data.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style_data.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style_data.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style_data.setFont(font_data);
        style_data.setDataFormat(fmt.getFormat("dd\"/\"mm\"/\"yyyy"));
        styles.put("130", style_data);

        /* ------------------------------------------------------------------- */
        /*
         * CELL_TYPE_DATETIME /* -------------------------------------------------------------------
         */
        style_data = wb.createCellStyle();
        style_data.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style_data.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style_data.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style_data.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style_data.setFont(font_data);
        style_data.setDataFormat(fmt.getFormat("dd\"/\"mm\"/\"yyyy hh:mm:ss"));
        styles.put("140", style_data);


        return styles;
    }
}


class ExlSheetMaker {

    private final Writer exoInnerWriter;
    private int exnIdx;
    private LinkedHashMap<String, String> exColSize;
    private LinkedList<String> exColMerge;


    public ExlSheetMaker(Writer writer, LinkedHashMap<String, String> colSize,
            LinkedList<String> colMerge) {
        exoInnerWriter = writer;
        exColSize = colSize;
        exColMerge = colMerge;
    }



    public void beginSheet() throws IOException {
        exoInnerWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");

        if (!exColSize.isEmpty()) {
            exoInnerWriter.write("<cols>");
            Set<String> keySet = exColSize.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                Integer col = Integer.parseInt((String) iterator.next());
                exoInnerWriter.write("<col min=\"" + (col + 1) + "\" max=\"" + (col + 1)
                        + "\" width=\"" + exColSize.get(col.toString()) + "\" customWidth=\"1\"/>");
            }
            exoInnerWriter.write("</cols>");
        }

        exoInnerWriter.write("<sheetData>\n");
    }



    public void endSheet() throws IOException {
        exoInnerWriter.write("</sheetData>");

        if (!exColMerge.isEmpty()) {
            exoInnerWriter.write("<mergeCells>");
            for (int i = 0, l = exColMerge.size(); i < l; i++) {
                exoInnerWriter.write("<mergeCell ref=\"" + exColMerge.get(i) + "\"/>");
            }
            exoInnerWriter.write("</mergeCells>");
        }

        exoInnerWriter.write("</worksheet>");
    }



    public void insertRow(int nIdx) throws IOException {
        exoInnerWriter.write("<row r=\"" + (nIdx + 1) + "\">\n");
        this.exnIdx = nIdx;
    }



    public void endRow() throws IOException {
        exoInnerWriter.write("</row>\n");
    }



    public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
        String ref = new CellReference(exnIdx, columnIndex).formatAsString();
        exoInnerWriter.write("<c r=\"" + ref + "\" t=\"inlineStr\"");
        if (styleIndex != -1)
            exoInnerWriter.write(" s=\"" + styleIndex + "\"");
        exoInnerWriter.write(">");
        exoInnerWriter.write("<is><t>" + santizeForXml(value) + "</t></is>");
        exoInnerWriter.write("</c>");
    }



    public void createCell(int columnIndex, String value) throws IOException {
        createCell(columnIndex, value, -1);
    }



    public void createCell(int columnIndex, double value, int styleIndex) throws IOException {
        String ref = new CellReference(exnIdx, columnIndex).formatAsString();
        exoInnerWriter.write("<c r=\"" + ref + "\" t=\"n\"");
        if (styleIndex != -1)
            exoInnerWriter.write(" s=\"" + styleIndex + "\"");
        exoInnerWriter.write(">");
        exoInnerWriter.write("<v>" + value + "</v>");
        exoInnerWriter.write("</c>");

    }



    public void createCell(int columnIndex, double value) throws IOException {
        createCell(columnIndex, value, -1);
    }



    public void createCell(int columnIndex, Calendar value, int styleIndex) throws IOException {
        createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
    }


    /* ---------------------------------------------------------------------- */
    /*
     * Xml 파일 생성시 예외처리 ( <![CDATA[]]> ) /*
     * ----------------------------------------------------------------------
     */
    private String santizeForXml(String str) {
        StringBuilder strBuilder = new StringBuilder();

        boolean stringHasSpecial = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!XMLChar.isInvalid(c)) {
                strBuilder.append(c);
                stringHasSpecial = stringHasSpecial || charIsSpecial(c);
            }

        }

        if (stringHasSpecial) {
            return cData(strBuilder.toString());
        }

        return strBuilder.toString();
    }

    private String cData(String str) {
        return "<![CDATA[" + str + "]]>";
    }

    private boolean charIsSpecial(char c) {
        if (c == '&' || c == '<' || c == '>')
            return true;
        return false;
    }

}
