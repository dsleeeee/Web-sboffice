package kr.co.solbipos.base.persistence.store.tablelayout;

import kr.co.solbipos.base.domain.store.tablelayout.Table;
import kr.co.solbipos.base.domain.store.tablelayout.TableGroup;
import kr.co.solbipos.structure.DefaultMap;

/**
 * 기초관리 - 매장관리 - 테이블구성
 * 
 * @author 조병준
 *
 */
public interface TableLayoutMapper {

    /** 
     * 매장 테이블 조회
     * @param param
     * @return
     */
    String selectTableGroupByStore(DefaultMap<String> param);
    
    /** 
     * 매장 테이블 그룹 조회
     * @param param
     * @return
     */
    String selectTableByStore(DefaultMap<String> param);

    /** 
     * 매장 테이블그룹 삭제
     * @param storeCd 매장코드
     * @return
     */
    int deleteTableGroupByStore(String storeCd);

    /** 
     * 매장 테이블그룹 저장(Merge)
     * @param tableGroup
     * @return
     */
    int mergeTableGroupByStore(TableGroup tableGroup);

    /** 
     * 매장 테이블 삭제
     * @param storeCd 매장코드
     * @return
     */
    int deleteTableByStore(String storeCd);

    /** 
     * 매장 테이블 저장(Merge)
     * @param table
     * @return
     */
    int mergeTableByStore(Table table);

}
