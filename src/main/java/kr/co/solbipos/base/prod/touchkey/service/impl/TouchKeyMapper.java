package kr.co.solbipos.base.prod.touchkey.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyStyleVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
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
    List<DefaultMap<String>> getProductListForTouchKey(TouchKeyVO touchKeyVO);

    /** 터치키 스타일코드 목록 조회 */
    List<DefaultMap<String>> getTouchKeyStyleCdList();

    /** 터치키 스타일 목록 조회 */
    List<DefaultMap<String>> getTouchKeyStyleList(TouchKeyStyleVO touchKeyStyleVO);

    /** 터치키 분류 페이지별 스타일 코드 조회 */
    String getTouchKeyPageStyleCd(TouchKeyClassVO touchKeyClassVO);

    /** 터치키 스타일 비교값 조회 */
    DefaultMap<String> getTouchKeyStyleForCompare(TouchKeyStyleVO touchKeyStyleVO);

    /** 판매터치키 XML 정보 조회 */
    String getTouchKeyXml(DefaultMap<String> param);

    /** 판매터치키 저장 상품정보 조회 */
    List<DefaultMap<String>> getTouchKeyProdInfoList(TouchKeyVO touchKeyVO);

    /** 판매터치키 XML 정보 생성 */
    int insertTouchKeyConfgXml(DefaultMap<String> param);

    /** 판매터치키 XML 정보 수정 */
    int updateTouchKeyConfgXml(DefaultMap<String> param);

    /** 판매터치키 그룹 생성 */
    int insertTouchKeyClass(TouchKeyClassVO touchKeyClassVO);

    /** 판매터치키 그룹 삭제 */
    int deleteTouchKeyClass(TouchKeyClassVO touchKeyClassVO);

    /** 판매터치키 생성 */
    int insertTouchKey(TouchKeyVO touchKeyVO);

    /** 판매터치키 삭제 */
    int deleteTouchKey(TouchKeyVO touchKeyVO);


}
