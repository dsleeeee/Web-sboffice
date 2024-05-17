package kr.co.solbipos.sale.status.table.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.table.service.TableSaleService;
import kr.co.solbipos.sale.status.table.service.TableSaleVO;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlService;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlVO;

@Controller
@RequestMapping("sale/status/table")
public class TableSaleController {

    private final SessionService sessionService;
    private final TableSaleService tableSaleService;
    private final TodayDtlService todayDtlService; // 당일매출현황상세

    public TableSaleController(SessionService sessionService, TableSaleService tableSaleService, TodayDtlService todayDtlService) {
        super();
        this.sessionService = sessionService;
        this.tableSaleService = tableSaleService;
        this.todayDtlService = todayDtlService; // 당일매출현황상세
    }

    @RequestMapping(value = "/day/view.sb", method = RequestMethod.GET)
    public String dayView(HttpServletRequest request, HttpServletResponse response, TableSaleVO tableSaleVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        TodayDtlVO todayDtlVO = new TodayDtlVO();

        // 객수 조회
        List<DefaultMap<String>> guestColList = todayDtlService.getGuestColList(todayDtlVO, sessionInfoVO);

        // 객수 코드를 , 로 연결하는 문자열 생성
        String guestCol = "";
        for(int i=0; i < guestColList.size(); i++) {
            guestCol += (guestCol.equals("") ? "" : ",") + guestColList.get(i).getStr("guestCd");
        }
        model.addAttribute("guestColList", guestColList);
        model.addAttribute("guestCol", guestCol);

        return "sale/status/table/tableSale";
    }

    /** 테이블별 - 일자별 리스트 조회 */
    @RequestMapping(value = "/day/getTableDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTableDayList(HttpServletRequest request, HttpServletResponse response, TableSaleVO tableSaleVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 테이블 리스트 조회
        List<DefaultMap<String>> tblColList = tableSaleService.getStoreTableList(tableSaleVO, sessionInfoVO);

        // 테이블 목록을 , 로 연결하는 문자열 생성
        String tblCol = "";
        if(tableSaleVO.getTableCd() == "" ) {
            for(int i=0; i < tblColList.size(); i++) {
                tblCol += (tblCol.equals("") ? "" : ",") + tblColList.get(i).getStr("nmcodeCd");
            }
        }
        else {
            tblCol += tableSaleVO.getTableCd() + ",";
        }
        tableSaleVO.setTableCd(tblCol);

        // 테이블 목록 컬럼 쿼리 추가
        tableSaleVO = setCol(tableSaleVO);
        List<DefaultMap<Object>> list = tableSaleService.getTableDayList(tableSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, tableSaleVO);
    }

    /** 테이블별 - 일자별 엑셀 리스트 조회 */
    @RequestMapping(value = "/day/getTableDayExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTableDayExcelList(HttpServletRequest request, HttpServletResponse response, TableSaleVO tableSaleVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 테이블 리스트 조회
        List<DefaultMap<String>> tblColList = tableSaleService.getStoreTableList(tableSaleVO, sessionInfoVO);

        // 테이블 목록을 , 로 연결하는 문자열 생성
        String tblCol = "";
        if(tableSaleVO.getTableCd() == "" ) {
            for(int i=0; i < tblColList.size(); i++) {
                tblCol += (tblCol.equals("") ? "" : ",") + tblColList.get(i).getStr("nmcodeCd");
            }
        }
        else {
            tblCol += tableSaleVO.getTableCd() + ",";
        }
        tableSaleVO.setTableCd(tblCol);

        // 테이블 목록 컬럼 쿼리 추가
        tableSaleVO = setCol(tableSaleVO);
        List<DefaultMap<Object>> list = tableSaleService.getTableDayExcelList(tableSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, tableSaleVO);
    }

    /** 테이블별 = 매장코드로 해당 매장의 테이블 목록 조회, 콤보박스 데이터 */
    @RequestMapping(value = "/day/tableNmList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreTableList(HttpServletRequest request, HttpServletResponse response, Model model, TableSaleVO tableSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (tableSaleVO.getTableCd() != null && !"".equals(tableSaleVO.getTableCd())) {
            String[] arrTableCd = tableSaleVO.getTableCd().split(",");

            if (arrTableCd.length > 0) {
                if (arrTableCd[0] != null && !"".equals(arrTableCd[0])) {
                    tableSaleVO.setArrTableCd(arrTableCd);
                }
            }
        } else {
            String[] arrStoreCd = tableSaleVO.getStoreCd().split(",");
            if (arrStoreCd.length > 0) {
                if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    tableSaleVO.setArrStoreCd(arrStoreCd);
                }
            }
        }

        // 테이블 목록 조회
        List<DefaultMap<String>> list = tableSaleService.getStoreTableList(tableSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, tableSaleVO);
    }

    /** 테이블 컬럼 세팅 */
    public TableSaleVO setCol(TableSaleVO tableSaleVO) {

        String sQuery1 = "";

        // 테이블 컬럼 array 값 세팅
        if(tableSaleVO.getTableCd() != "" && tableSaleVO.getTableCd() != null) {
            tableSaleVO.setArrTableCd(tableSaleVO.getTableCd().split(","));

            // 테이블 번호 기준, 동적 컬럼 생성을 위한 쿼리 변수

            // 테이블 '전체' 선택 시
            for (int i = 0; i < tableSaleVO.getArrTableCd().length; i++) {
                sQuery1 += " ,NVL(SUM(CASE STORE_CD||'||'||TBL_CD WHEN '" + tableSaleVO.getArrTableCd()[i]
                        + "' THEN REAL_SALE_AMT END), '0') AS REAL_SALE_AMT_T" + i + "\n";
                sQuery1 += " ,NVL(SUM(CASE STORE_CD||'||'||TBL_CD WHEN '" + tableSaleVO.getArrTableCd()[i]
                        + "' THEN SALE_CNT END), '0') AS SALE_CNT_T" + i + "\n";
                sQuery1 += " ,NVL(SUM(CASE STORE_CD||'||'||TBL_CD WHEN '" + tableSaleVO.getArrTableCd()[i]
                        + "' THEN TOT_GUEST_CNT END), '0') AS GUEST_CNT_1_T" + i + "\n";
            }
        }

        tableSaleVO.setsQuery1(sQuery1);

        return tableSaleVO;
    }

    /** 테이블별 - 요일별 리스트 조회 */
	@RequestMapping(value = "/dayofweek/getTableDayOfWeekList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getTableDayOfWeekList(HttpServletRequest request, HttpServletResponse response, TableSaleVO tableSaleVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		// 테이블 리스트 조회
        List<DefaultMap<String>> tblColList = tableSaleService.getStoreTableList(tableSaleVO, sessionInfoVO);

        // 테이블 목록을 , 로 연결하는 문자열 생성
        String tblCol = "";
        if(tableSaleVO.getTableCd() == "" ) {
        	for(int i=0; i < tblColList.size(); i++) {
        		tblCol += (tblCol.equals("") ? "" : ",") + tblColList.get(i).getStr("nmcodeCd");
        	}
        }
        else {
        	tblCol += tableSaleVO.getTableCd() + ",";
        }
        tableSaleVO.setTblCol(tblCol);

		// 테이블 목록 컬럼 쿼리 추가
        tableSaleVO = setCol(tableSaleVO);
		List<DefaultMap<Object>> list = tableSaleService.getTableDayOfWeekList(tableSaleVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, tableSaleVO);
	}

    /** 테이블별 - 월별 리스트 조회 */
    @RequestMapping(value = "/month/getTableMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTableMonthList(HttpServletRequest request, HttpServletResponse response, TableSaleVO tableSaleVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 테이블 리스트 조회
        List<DefaultMap<String>> tblColList = tableSaleService.getStoreTableList(tableSaleVO, sessionInfoVO);

        // 테이블 목록을 , 로 연결하는 문자열 생성
        String tblCol = "";
        if(tableSaleVO.getTableCd() == "" ) {
            for(int i=0; i < tblColList.size(); i++) {
                tblCol += (tblCol.equals("") ? "" : ",") + tblColList.get(i).getStr("nmcodeCd");
            }
        }
        else {
            tblCol += tableSaleVO.getTableCd() + ",";
        }
        tableSaleVO.setTblCol(tblCol);

        // 테이블 목록 컬럼 쿼리 추가
        tableSaleVO = setCol(tableSaleVO);
        List<DefaultMap<Object>> list = tableSaleService.getTableMonthList(tableSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, tableSaleVO);
    }

    /** 테이블별 - 월별 엑셀리스트 조회 */
    @RequestMapping(value = "/month/getTableMonthExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTableMonthExcelList(HttpServletRequest request, HttpServletResponse response, TableSaleVO tableSaleVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 테이블 리스트 조회
        List<DefaultMap<String>> tblColList = tableSaleService.getStoreTableList(tableSaleVO, sessionInfoVO);

        // 테이블 목록을 , 로 연결하는 문자열 생성
        String tblCol = "";
        if(tableSaleVO.getTableCd() == "" ) {
            for(int i=0; i < tblColList.size(); i++) {
                tblCol += (tblCol.equals("") ? "" : ",") + tblColList.get(i).getStr("nmcodeCd");
            }
        }
        else {
            tblCol += tableSaleVO.getTableCd() + ",";
        }
        tableSaleVO.setTblCol(tblCol);

        // 테이블 목록 컬럼 쿼리 추가
        tableSaleVO = setCol(tableSaleVO);
        List<DefaultMap<Object>> list = tableSaleService.getTableMonthExcelList(tableSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, tableSaleVO);
    }

    /** 설정기간별 매출 - 조회일자별 리스트 조회 */
    @RequestMapping(value = "/dayperiod/getTableDayPeriodList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTableDayPeriodList(HttpServletRequest request, HttpServletResponse response, Model model, TableSaleVO tableSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = tableSaleService.getTableDayPeriodList(tableSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, tableSaleVO);
    }

    /** 설정기간별 매출 - 조회일자별 엑셀 리스트 조회 */
    @RequestMapping(value = "/dayperiod/getTableDayPeriodExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTableDayPeriodExcelList(HttpServletRequest request, HttpServletResponse response, Model model, TableSaleVO tableSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = tableSaleService.getTableDayPeriodExcelList(tableSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, tableSaleVO);
    }

    /**
     * 일자별 - 리스트 총 수량 조회
     * @param tableSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2024. 05. 16.
     */
    @RequestMapping(value = "/day/getDayListCnt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayListCnt(HttpServletRequest request, HttpServletResponse response, TableSaleVO tableSaleVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 테이블 리스트 조회
        List<DefaultMap<String>> tblColList = tableSaleService.getStoreTableList(tableSaleVO, sessionInfoVO);

        // 테이블 목록을 , 로 연결하는 문자열 생성
        String tblCol = "";
        if(tableSaleVO.getTableCd() == "" ) {
            for(int i=0; i < tblColList.size(); i++) {
                tblCol += (tblCol.equals("") ? "" : ",") + tblColList.get(i).getStr("nmcodeCd");
            }
        }
        else {
            tblCol += tableSaleVO.getTableCd() + ",";
        }
        tableSaleVO.setTableCd(tblCol);

        // 테이블 목록 컬럼 쿼리 추가
        tableSaleVO = setCol(tableSaleVO);

        List<DefaultMap<Object>> list = tableSaleService.getDayListCnt(tableSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, tableSaleVO);
    }

    /**
     * 월별 - 리스트 총 수량 조회
     * @param tableSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2024. 05. 16.
     */
    @RequestMapping(value = "/day/getMonthListCnt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthListCnt(HttpServletRequest request, HttpServletResponse response, TableSaleVO tableSaleVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = tableSaleService.getMonthListCnt(tableSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, tableSaleVO);
    }

}
