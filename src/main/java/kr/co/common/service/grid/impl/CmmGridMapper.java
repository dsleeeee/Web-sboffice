package kr.co.common.service.grid.impl;

import kr.co.solbipos.application.common.service.GridDispItemVO;

/**
 * 샘플
 *
 * @author 정용길
 *
 */
public interface CmmGridMapper {

    /**
      * 그리드의 보여주는 컬럼 내역 저장
      * @param gridDispItemVO
      * @return
      */
    int insertGridItem(GridDispItemVO gridDispItemVO);

    /**
      *
      * @param gridDispItemVO
      * @return
      */
    int updateGridItem(GridDispItemVO gridDispItemVO);

    /**
      *
      * @param gridDispItemVO
      * @return
      */
    GridDispItemVO selectGridItem(GridDispItemVO gridDispItemVO);


}
