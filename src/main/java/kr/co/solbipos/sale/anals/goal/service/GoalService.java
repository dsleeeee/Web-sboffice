package kr.co.solbipos.sale.anals.goal.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.goal.service.GoalVO;

import java.util.List;

public interface GoalService {

	/**매출목표관리 - 일자별 목표대비 매출 리스트 조회   */
	List<DefaultMap<String>> getSaleGoalDayColList(GoalVO goalVO, SessionInfoVO sessionInfoVO);
	
	/**매출목표관리 - 일자별 목표대비 매출 리스트 조회   */
    List<DefaultMap<String>> getSaleGoalDayList(GoalVO goalVO, SessionInfoVO sessionInfoVO);
    
	/**매출목표관리 - 월별 목표대비 매출 리스트 조회   */
    List<DefaultMap<String>> getSaleGoalMonthList(GoalVO goalVO, SessionInfoVO sessionInfoVO);
    
	/**매출목표관리 - 매장 리스트 조회    */
    List<DefaultMap<String>> getSaleGoalStoreList(GoalVO goalVO, SessionInfoVO sessionInfoVO);
    
	/**매출목표관리 - 매출목표 조회    */
    List<DefaultMap<String>> getSaleGoalList(GoalVO goalVO, SessionInfoVO sessionInfoVO);
    
	/**매출목표관리 - 매출목표 상세조회    */
    List<DefaultMap<String>> getSaleGoalDtl1List(GoalVO goalVO, SessionInfoVO sessionInfoVO);
    
	/**매출목표관리 - 매출목표 상세조회    */
    List<DefaultMap<String>> getSaleGoalDtl2List(GoalVO goalVO, SessionInfoVO sessionInfoVO);
    
	/**매출목표관리 - 매출목표 등록    */
    int saveSaleGoalSave(GoalVO goalVO, SessionInfoVO sessionInfoVO);
    
	/**매출목표관리 - 매출목표 상세등록    */
    int saveSaleGoalgoalDeatilSave(GoalVO[] goalVOs, SessionInfoVO sessionInfoVO);
    
	/**매출목표관리 - 매출목표금액TOT 저장    */
    int saveSaleGoalAmtTotSave(GoalVO goalVO, SessionInfoVO sessionInfoVO);

    /**매출목표관리 - 일자별 목표대비 매출 엑셀리스트 조회   */
	List<DefaultMap<String>> getSaleGoalDayExcelList(GoalVO goalVO, SessionInfoVO sessionInfoVO);

	/**매출목표관리 - 월별 목표대비 매출 엑셀리스트 조회   */
	List<DefaultMap<String>> getSaleGoalMonthExcelList(GoalVO goalVO, SessionInfoVO sessionInfoVO);
}
