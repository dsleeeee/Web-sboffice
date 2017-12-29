package kr.co.solbipos.application.persistance.cmm;

import kr.co.solbipos.application.domain.cmm.GridDispItem;

/**
 * 샘플
 * 
 * @author 정용길
 *
 */
public interface CmmGridMapper {
    
    /**
      * 그리드의 보여주는 컬럼 내역 저장
      * @param gridDispItem
      * @return
      */
    int insertGridItem(GridDispItem gridDispItem);
    
    /**
      * 
      * @param gridDispItem
      * @return
      */
    int updateGridItem(GridDispItem gridDispItem);
    
    /**
      * 
      * @param gridDispItem
      * @return
      */
    GridDispItem selectGridItem(GridDispItem gridDispItem);
    
    
}
