package kr.co.solbipos.sale.status.table.month.web;

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
import kr.co.solbipos.sale.status.table.month.service.TableMonthService;
import kr.co.solbipos.sale.status.table.month.service.TableMonthVO;

@Controller
@RequestMapping("sale/status/table")
public class TableMonthController {

	private final SessionService sessionService;
	private final TableMonthService tableMonthService;

	public TableMonthController(SessionService sessionService, TableMonthService tableMonthService) {
		super();
		this.sessionService = sessionService;
		this.tableMonthService = tableMonthService;
	}

	@RequestMapping(value = "/month/view.sb", method = RequestMethod.GET)
	public String dayView(HttpServletRequest request, HttpServletResponse response, TableMonthVO tableMonthVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		return "sale/status/table/month";
	}

	/** 테이블별 - 월별 리스트 조회 */
	@RequestMapping(value = "/month/list.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getTableMonthList(HttpServletRequest request, HttpServletResponse response, TableMonthVO tableMonthVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		// 테이블 리스트 조회
        List<DefaultMap<String>> tblColList = tableMonthService.getStoreTableList(tableMonthVO, sessionInfoVO);

        // 테이블 목록을 , 로 연결하는 문자열 생성
        String tblCol = "";
        if(tableMonthVO.getTableCd() == "" ) {
        	for(int i=0; i < tblColList.size(); i++) {
        		tblCol += (tblCol.equals("") ? "" : ",") + tblColList.get(i).getStr("nmcodeCd");
        	}
        }
        else {
        	tblCol += tableMonthVO.getTableCd() + ",";
        }
        tableMonthVO.setTblCol(tblCol);

		// 테이블 목록 컬럼 쿼리 추가
		tableMonthVO = setCol(tableMonthVO);
		List<DefaultMap<String>> list = tableMonthService.getTableMonthList(tableMonthVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, tableMonthVO);
	}

	/** 테이블별 = 매장코드로 해당 매장의 테이블 목록 조회, 콤보박스 데이터 */
	@RequestMapping(value = "/month//tableNmList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getStoreTableList(HttpServletRequest request, HttpServletResponse response, Model model, TableMonthVO tableMonthVO) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		if (tableMonthVO.getTableCd() != null && !"".equals(tableMonthVO.getTableCd())) {
    		String[] arrTableCd = tableMonthVO.getTableCd().split(",");

    		if (arrTableCd.length > 0) {
    			if (arrTableCd[0] != null && !"".equals(arrTableCd[0])) {
    				tableMonthVO.setArrTableCd(arrTableCd);
    			}
    		}
    	} else {
    		String[] arrStoreCd = tableMonthVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				tableMonthVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		// 테이블 목록 조회
		List<DefaultMap<String>> list = tableMonthService.getStoreTableList(tableMonthVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, tableMonthVO);
	}

	/** 테이블 컬럼 세팅 */
	public TableMonthVO setCol(TableMonthVO tableMonthVO) {

		// 테이블 컬럼 array 값 세팅
		tableMonthVO.setArrTableCd(tableMonthVO.getTblCol().split(","));

		// 테이블 번호 기준, 동적 컬럼 생성을 위한 쿼리 변수
		String sQuery1 = "";

		// 테이블 '전체' 선택 시
		for (int i = 0; i < tableMonthVO.getArrTableCd().length; i++) {
			sQuery1 += " ,SUM(CASE STORE_CD||'||'||TBL_CD WHEN '" + tableMonthVO.getArrTableCd()[i]
					+ "' THEN REAL_SALE_AMT END) AS REAL_SALE_AMT_T" + i + "\n";
			sQuery1 += " ,SUM(CASE STORE_CD||'||'||TBL_CD WHEN '" + tableMonthVO.getArrTableCd()[i]
					+ "' THEN REAL_SALE_CNT END) AS REAL_SALE_CNT_T" + i + "\n";
			sQuery1 += " ,SUM(CASE STORE_CD||'||'||TBL_CD WHEN '" + tableMonthVO.getArrTableCd()[i]
					+ "' THEN GUEST_CNT_1 END) AS GUEST_CNT_1_T" + i + "\n";
		}

		tableMonthVO.setsQuery1(sQuery1);

		return tableMonthVO;
	}

}
