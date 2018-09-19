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
 * @ 2018.05.01  조병준      최초생성
 * @ 2018.09.19  노현수      메소드정리/분리
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

    /** 상품목록 조회 : 판매터치키에서 사용 */
    List<DefaultMap<String>> getProductListForTouchKey(TouchVO touchVO);

    /** 판매터치키 XML 정보 조회 */
    String getTouchKeyXml(DefaultMap<String> param);

    /** 판매터치키 XML 정보 생성 */
    int insertTouchKeyConfgXml(DefaultMap<String> param);

    /** 판매터치키 XML 정보 수정 */
    int updateTouchKeyConfgXml(DefaultMap<String> param);

    /** 판매터치키 그룹 생성 */
    int insertTouchClass(TouchClassVO touchClassVO);

    /** 판매터치키 그룹 삭제 */
    int deleteTouchClass(TouchClassVO touchClassVO);

    /** 판매터치키 생성 */
    int insertTouchKey(TouchVO touchVO);

    /** 판매터치키 삭제 */
    int deleteTouchKey(TouchVO touchVO);


}
