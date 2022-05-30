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

    /** 판매터치키 XML 정보 삭제 */
    int deleteTouchKeyConfgXml(DefaultMap<String> param);

    /** 판매터치키 XML 기본값 세팅 */
    int defaultTouchKeyConfgXml(DefaultMap<String> param);

    /** 터치키 매장적용 본사 라인수 적용(HQ: 0041, MS:1041) */
    int mergeStoreEnvst(TouchKeyVO touchKeyVO);

    /** 판매터치키 분류 생성 */
    int insertTouchKeyClass(TouchKeyClassVO touchKeyClassVO);

    /** 판매터치키 분류 삭제 */
    int deleteTouchKeyClass(TouchKeyClassVO touchKeyClassVO);

    /** 판매터치키 분류 기본값 세팅 */
    int defaultTouchKeyClass(TouchKeyClassVO touchKeyClassVO);

    /** 판매터치키 생성 */
    int insertTouchKey(TouchKeyVO touchKeyVO);

    /** 판매터치키 삭제 */
    int deleteTouchKey(TouchKeyVO touchKeyVO);

    /** 터치키미적용상품 조회 */
    List<DefaultMap<String>> getNoTouchKey(TouchKeyVO touchKeyVO);

    /** 매장목록 조회 */
    List<DefaultMap<String>> getStoreList(TouchKeyVO touchKeyVO);

    /** 매장 터치키분류 업데이트 */
    int saveStoreConfgXml(TouchKeyVO touchKeyVO);

    /** 터치키 분류 매장삭제 */
    int deleteTouchKeyClassToStore(TouchKeyVO touchKeyVO);

    /** 터치키 매장삭제 */
    int deleteTouchKeyToStore(TouchKeyVO touchKeyVO);

    /** 터치키 분류 매장생성 */
    int insertTouchKeyClassToStore(TouchKeyVO touchKeyVO);

    /** 터치키 매장생성 */
    int insertTouchKeyToStore(TouchKeyVO touchKeyVO);

    /** 판매터치키 그룹 조회 */
    List<DefaultMap<String>> getTouchKeyGrp(TouchKeyVO touchKeyVO);

    /** 터치키 그룹코드 생성 */
    String getTouchKeyGrpCd(DefaultMap<String> param);

    /** 터치키 XML 데이터 복사 */
    int copyTouchKeyGrpXml(TouchKeyVO touchKeyVO);

    /** 터치키 CLASS 데이터 복사 */
    int copyTouchKeyGrpClass(TouchKeyVO touchKeyVO);

    /** 터치키 데이터 복사 */
    int copyTouchKeyGrp(TouchKeyVO touchKeyVO);

    /** 판매터치키 조회 - 데이터 기준으로 XML 만들기 */
    String getTouchKeyXmlByData(DefaultMap<String> param);

    /** 터치키 분류코드 생성 */
    String getTouchKeyClassCd(DefaultMap<String> param);

}
