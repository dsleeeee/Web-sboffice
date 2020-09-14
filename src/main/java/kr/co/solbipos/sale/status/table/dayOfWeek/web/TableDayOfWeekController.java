package kr.co.solbipos.sale.status.table.dayOfWeek.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.table.dayOfWeek.service.TableDayOfWeekService;
import kr.co.solbipos.sale.status.table.dayOfWeek.service.TableDayOfWeekVO;

@Controller
@RequestMapping("sale/status/table")
public class TableDayOfWeekController {

	private final SessionService sessionService;
	private final TableDayOfWeekService tableDayOfWeekService;

	public TableDayOfWeekController(SessionService sessionService, TableDayOfWeekService tableDayService) {
		super();
		this.sessionService = sessionService;
		this.tableDayOfWeekService = tableDayService;
	}

	/** 테이블별 - 요일별 리스트 조회 */
	@RequestMapping(value = "/dayofweek/list.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getTableDayList(HttpServletRequest request, HttpServletResponse response, TableDayOfWeekVO tableDayOfWeekVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		// 테이블 리스트 조회
        List<DefaultMap<String>> tblColList = tableDayOfWeekService.getStoreTableList(tableDayOfWeekVO, sessionInfoVO);

        // 테이블 목록을 , 로 연결하는 문자열 생성
        String tblCol = "";
        if(tableDayOfWeekVO.getTableCd() == "" ) {
        	for(int i=0; i < tblColList.size(); i++) {
        		tblCol += (tblCol.equals("") ? "" : ",") + tblColList.get(i).getStr("nmcodeCd");
        	}
        }
        else {
        	tblCol += tableDayOfWeekVO.getTableCd() + ",";
        }
        tableDayOfWeekVO.setTblCol(tblCol);

		// 테이블 목록 컬럼 쿼리 추가
		tableDayOfWeekVO = setCol(tableDayOfWeekVO);
		List<DefaultMap<String>> list = tableDayOfWeekService.getTableDayOfWeekList(tableDayOfWeekVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, tableDayOfWeekVO);
	}

	/** 테이블별 = 매장코드로 해당 매장의 테이블 목록 조회, 콤보박스 데이터 */
	@RequestMapping(value = "/dayofweek/storeTableList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getStoreTableList(HttpServletRequest request, HttpServletResponse response, Model model, TableDayOfWeekVO tableDayOfWeekVO) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		if (tableDayOfWeekVO.getTableCd() != null && !"".equals(tableDayOfWeekVO.getTableCd())) {
    		String[] arrTableCd = tableDayOfWeekVO.getTableCd().split(",");

    		if (arrTableCd.length > 0) {
    			if (arrTableCd[0] != null && !"".equals(arrTableCd[0])) {
    				tableDayOfWeekVO.setArrTableCd(arrTableCd);
    			}
    		}
    	} else {
    		String[] arrStoreCd = tableDayOfWeekVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				tableDayOfWeekVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
		// 테이블 목록 조회
		List<DefaultMap<String>> list = tableDayOfWeekService.getStoreTableList(tableDayOfWeekVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, tableDayOfWeekVO);
	}

	/** 테이블 컬럼 세팅 */
	public TableDayOfWeekVO setCol(TableDayOfWeekVO tableDayOfWeekVO) {

		// 테이블 컬럼 array 값 세팅
		tableDayOfWeekVO.setArrTableCd(tableDayOfWeekVO.getTblCol().split(","));

		// 테이블 번호 기준, 동적 컬럼 생성을 위한 쿼리 변수
		String sQuery1 = "";

		// 테이블 '전체' 선택 시
		for (int i = 0; i < tableDayOfWeekVO.getArrTableCd().length; i++) {
			sQuery1 += " ,NVL(SUM(CASE STORE_CD||'||'||TBL_CD WHEN '" + tableDayOfWeekVO.getArrTableCd()[i]
					+ "' THEN REAL_SALE_AMT END), 0) AS REAL_SALE_AMT_T" + i + "\n";
			sQuery1 += " ,NVL(SUM(CASE STORE_CD||'||'||TBL_CD WHEN '" + tableDayOfWeekVO.getArrTableCd()[i]
					+ "' THEN SALE_CNT END),0) AS SALE_CNT_T" + i + "\n";
			sQuery1 += " ,NVL(SUM(CASE STORE_CD||'||'||TBL_CD WHEN '" + tableDayOfWeekVO.getArrTableCd()[i]
					+ "' THEN TOT_GUEST_CNT END),0) AS GUEST_CNT_1_T" + i + "\n";
		}

		tableDayOfWeekVO.setsQuery1(sQuery1);

		return tableDayOfWeekVO;
	}

}
