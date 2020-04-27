package kr.co.solbipos.sale.anals.goal.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.goal.service.GoalVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoalMapper {
	
	/**매출목표관리 - 일자별 목표대비 매출 리스트 조회   */
	List<DefaultMap<String>> getSaleGoalDayColList(GoalVO goalVO);
	
	/**매출목표관리 - 일자별 목표대비 매출 리스트 조회   */
    List<DefaultMap<String>> getSaleGoalDayList(GoalVO goalVO);
    
	/**매출목표관리 - 월별 목표대비 매출 리스트 조회   */
    List<DefaultMap<String>> getSaleGoalMonthList(GoalVO goalVO);
    
	/**매출목표관리 - 매장 리스트 조회   */
    List<DefaultMap<String>> getSaleGoalStoreList(GoalVO goalVO);
    
	/**매출목표관리 - 매출목표 조회   */
    List<DefaultMap<String>> getSaleGoalList(GoalVO goalVO);
    
	/**매출목표관리 - 매출목표 상세조회   */
    List<DefaultMap<String>> getSaleGoalDtl1List(GoalVO goalVO);
    
	/**매출목표관리 - 매출목표 상세조회   */
    List<DefaultMap<String>> getSaleGoalDtl2List(GoalVO goalVO);
    
	/**매출목표관리 - 매출목표 등록(MONTH) 저장   */
    int saveSaleGoalSave(GoalVO goalVO);
    
	/**매출목표관리 - 매출목표 등록(WEIGHT) 저장   */
    int saveGoalWeight(GoalVO goalVO);
    
	/**매출목표관리 - 매출목표 등록(DAILY) 저장   */
    int saveGoalDaily(GoalVO goalVO);
    
	/**매출목표관리 - 매출목표 상세등록   */
    int saveSaleGoalgoalDeatilSave(GoalVO goalVO);
    
	/**매출목표관리 - 매출목표금액TOT 저장   */
    int saveSaleGoalAmtTotSave(GoalVO goalVO);

    /**매출목표관리 - 일자별 목표대비 매출 엑셀리스트 조회   */
	List<DefaultMap<String>> getSaleGoalDayExcelList(GoalVO goalVO);

	/**매출목표관리 - 월별 목표대비 매출 엑셀리스트 조회   */
	List<DefaultMap<String>> getSaleGoalMonthExcelList(GoalVO goalVO);
}
