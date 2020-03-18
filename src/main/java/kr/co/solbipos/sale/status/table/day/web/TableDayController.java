package kr.co.solbipos.sale.status.table.day.web;

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
import kr.co.solbipos.sale.status.table.day.service.TableDayService;
import kr.co.solbipos.sale.status.table.day.service.TableDayVO;

@Controller
@RequestMapping("sale/status/table")
public class TableDayController {

	private final SessionService sessionService;
	private final TableDayService tableDayService;

	public TableDayController(SessionService sessionService, TableDayService tableDayService) {
		super();
		this.sessionService = sessionService;
		this.tableDayService = tableDayService;
	}

	@RequestMapping(value = "/day/view.sb", method = RequestMethod.GET)
	public String dayView(HttpServletRequest request, HttpServletResponse response, TableDayVO tableDayVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		return "sale/status/table/tableSale";
	}

	/** 테이블별 - 일자별 리스트 조회 */
	@RequestMapping(value = "/day/list.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getTableDayList(HttpServletRequest request, HttpServletResponse response, TableDayVO tableDayVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		// 테이블 리스트 조회
        List<DefaultMap<String>> tblColList = tableDayService.getStoreTableList(tableDayVO, sessionInfoVO);

        // 테이블 목록을 , 로 연결하는 문자열 생성
        String tblCol = "";
        if(tableDayVO.getTableCd() == "" ) {
        	for(int i=0; i < tblColList.size(); i++) {
        		tblCol += (tblCol.equals("") ? "" : ",") + tblColList.get(i).getStr("nmcodeCd");
        	}
        }
        else {
        	tblCol += tableDayVO.getTableCd() + ",";
        }
        tableDayVO.setTableCd(tblCol);

		// 테이블 목록 컬럼 쿼리 추가
		tableDayVO = setCol(tableDayVO);
		List<DefaultMap<String>> list = tableDayService.getTableDayList(tableDayVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, tableDayVO);
	}

	/** 테이블별 = 매장코드로 해당 매장의 테이블 목록 조회, 콤보박스 데이터 */
	@RequestMapping(value = "/day/tableNmList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getStoreTableList(HttpServletRequest request, HttpServletResponse response, Model model, TableDayVO tableDayVO) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		if (tableDayVO.getTableCd() != null && !"".equals(tableDayVO.getTableCd())) {
    		String[] arrTableCd = tableDayVO.getTableCd().split(",");

    		if (arrTableCd.length > 0) {
    			if (arrTableCd[0] != null && !"".equals(arrTableCd[0])) {
    				tableDayVO.setArrTableCd(arrTableCd);
    			}
    		}
    	} else {
    		String[] arrStoreCd = tableDayVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				tableDayVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		// 테이블 목록 조회
		List<DefaultMap<String>> list = tableDayService.getStoreTableList(tableDayVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, tableDayVO);
	}

	/** 테이블 컬럼 세팅 */
	public TableDayVO setCol(TableDayVO tableDayVO) {

		// 테이블 컬럼 array 값 세팅
		tableDayVO.setArrTableCd(tableDayVO.getTableCd().split(","));


		// 테이블 번호 기준, 동적 컬럼 생성을 위한 쿼리 변수
		String sQuery1 = "";

		// 테이블 '전체' 선택 시
		for (int i = 0; i < tableDayVO.getArrTableCd().length; i++) {
			sQuery1 += " ,NVL(SUM(CASE STORE_CD||'||'||TBL_CD WHEN '" + tableDayVO.getArrTableCd()[i]
					+ "' THEN REAL_SALE_AMT END), '0') AS REAL_SALE_AMT_T" + i + "\n";
			sQuery1 += " ,NVL(SUM(CASE STORE_CD||'||'||TBL_CD WHEN '" + tableDayVO.getArrTableCd()[i]
					+ "' THEN SALE_CNT END), '0') AS SALE_CNT_T" + i + "\n";
			sQuery1 += " ,NVL(SUM(CASE STORE_CD||'||'||TBL_CD WHEN '" + tableDayVO.getArrTableCd()[i]
					+ "' THEN GUEST_CNT_1 END), '0') AS GUEST_CNT_1_T" + i + "\n";
		}

		tableDayVO.setsQuery1(sQuery1);

		return tableDayVO;
	}

}
