package kr.co.solbipos.base.store.tablelayout.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.tablelayout.service.TableGroupVO;
import kr.co.solbipos.base.store.tablelayout.service.TableVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Class Name : TableLayoutMapper.java
 * @Description : 기초관리 > 매장관리 > 테이블관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
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
     * @param tableGroupVO
     * @return
     */
    int mergeTableGroupByStore(TableGroupVO tableGroupVO);

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
    int mergeTableByStore(TableVO tableVO);

}
