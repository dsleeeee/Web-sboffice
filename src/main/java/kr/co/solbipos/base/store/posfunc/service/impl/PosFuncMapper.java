package kr.co.solbipos.base.store.posfunc.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.posfunc.service.PosFuncVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PosFuncMapper.java
 * @Description : 기초관리 > 매장관리 > 포스기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.07.26  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.26
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PosFuncMapper {

    /** 포스목록 조회 */
    List<DefaultMap<String>> getPosList(PosFuncVO posFuncVO);

    /** 포스기능목록 조회 */
    List<DefaultMap<String>> getPosFuncList(PosFuncVO posFuncVO);

    /** 포스기능상세 조회 */
    List<DefaultMap<String>> getPosConfDetail(PosFuncVO posFuncVO);

    /** 포스기능상세 저장 */
    int savePosConf(PosFuncVO posFuncVO);

    /** 포스기능 삭제 */
    int deletePosFunc(PosFuncVO posFuncVO);

    /** 포스기능 복사 */
    int copyPosFunc(PosFuncVO posFuncVO);

    /** 포스기능키 목록 조회 */
    List<DefaultMap<String>> getPosFuncKeyList(PosFuncVO posFuncVO);

    /** 포스기능키 기존설정 조회 */
    String getFuncKeyXml(DefaultMap<String> param);

    /** 포스기능키 XML 정보 생성 */
    int insertFuncKeyConfgXml(DefaultMap<String> param);

    /** 포스기능키 XML 정보 수정 */
    int updateFuncKeyConfgXml(DefaultMap<String> param);

    /** 포스기능키 생성 */
    int insertPosFuncKey(PosFuncVO posFuncVO);

    /** 포스기능키 삭제 */
    int deletePosFuncKey(PosFuncVO posFuncVO);

    /** 포스기능 인증목록 조회 */
    List<DefaultMap<String>> getPosConfAuthDetail(PosFuncVO posFuncVO);

    /** 포스기능 인증허용대상 조회 */
    List<DefaultMap<String>> getAuthEmpList(PosFuncVO posFuncVO);

    /** 포스기능 인증허용대상 저장 */
    int saveAuthEmp(PosFuncVO posFuncVO);
}
