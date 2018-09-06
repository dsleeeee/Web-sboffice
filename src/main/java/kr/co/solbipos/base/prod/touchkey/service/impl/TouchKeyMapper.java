package kr.co.solbipos.base.prod.touchkey.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.touchkey.service.TouchClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TouchkeyMapper.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TouchKeyMapper {

    /**
     * 상품 조회
     * @param touchVO
     * @return DefaultMap
     */
    List<DefaultMap<String>> selectProdByStore(TouchVO touchVO);

    /**
     * 상품 터치키 그룹 삭제
     * @param storeCd 매장코드
     * @return
     */
    int deleteTouchClassByStore(String storeCd);

    /**
     * 상품 터치키 그룹 등록
     * @param touchClassVO
     * @return
     */
    int insertTouchClassByStore(TouchClassVO touchClassVO);

    /**
     * 상품 터치키 삭제
     * @param storeCd 매장코드
     * @return
     */
    int deleteTouchByStore(String storeCd);

    /**
     * 상품 터치키 등록
     * @param touchVO
     * @return
     */
    int insertTouchByStore(TouchVO touchVO);

}
