package kr.co.solbipos.application.persistence.cmm;

import kr.co.solbipos.application.domain.cmm.GridDispItemVO;

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
