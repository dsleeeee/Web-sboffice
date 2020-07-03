package kr.co.solbipos.pos.confg.func.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.pos.confg.func.service.FuncStoreVO;
import kr.co.solbipos.pos.confg.func.service.FuncVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : FuncStoreVO.java
 * @Description : 포스관리 > POS 설정관리 > POS 기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.01  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.06.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface FuncMapper {
    /**
     * 기능구분 상세 조회
     *
     * @param funcVO
     * @return
     */
    List<DefaultMap<String>> getFuncList(FuncVO funcVO);

    /**
     * 기능구분 상세 등록
     *
     * @param funcVO
     * @return
     */
    int insertFunc(FuncVO funcVO);

    /**
     * 기능구분 상세 수정
     *
     * @param funcVO
     * @return
     */
    int updateFunc(FuncVO funcVO);

    /**
     * 기능구분 상세 삭제
     * @param funcVO
     * @return
     */
    int deleteFunc(FuncVO funcVO);

    /**
     * 기능키 등록매장 조회
     * @param funcStoreVO
     * @return
     */
    List<DefaultMap<String>> getStoreList(FuncStoreVO funcStoreVO);

    /**
     * 기능키 적용매장 등록
     * @param funcStoreVO
     * @return
     */
    int insertFuncStore(FuncStoreVO funcStoreVO);

    /**
     * 기능키 적용매장 삭제
     * @param funcStoreVO
     * @return
     */
    int deleteFuncStore(FuncStoreVO funcStoreVO);

    /**
     * 매장 기능키에 해당 기능키 등록 프로시져 호출
     * @param funcStoreVO
     * @return
     */
    String insertStoreFuncKey(FuncStoreVO funcStoreVO);

    /**
     * 포스 기능키에 해당 기능키 등록 프로시져 호출
     * @param funcStoreVO
     * @return
     */
    String insertPosFuncKey(FuncStoreVO funcStoreVO);

    /**
     * 매장 기능키 삭제 프로시져 호출
     * @param funcStoreVO
     * @return
     */
    String deleteStoreFuncKey(FuncStoreVO funcStoreVO);

    /**
     * 포스 기능키 삭제 프로시져 호출
     * @param funcStoreVO
     * @return
     */
    String deletePosFuncKey(FuncStoreVO funcStoreVO);

    /**
     * 포스 기능키 적용 매장 삭제
     * @param funcVO
     * @return
     */
    int deleteCmmStoreFunc(FuncVO funcVO);

    /**
     * 포스 기능 적용 매장 삭제(매장 기능키 테이블)
     * @param funcVO
     * @return
     */
    int deleteAllStoreFunc(FuncVO funcVO);

    /**
     * 포스 기능키 적용 포스목록 삭제(매장 포스 기능키 테이블)
     * @param funcVO
     * @return
     */
    int deleteAllPosFunc(FuncVO funcVO);

    /***
     * 기능키 상세정보 수정 프로시져 호출
     * @param funcVO
     * @return
     */
    String updateStoreFuncKey(FuncVO funcVO);

    /***
     * 매장리스트
     * @param funcStoreVO
     * @return
     */
    List<DefaultMap<String>> selectStoreList(FuncStoreVO funcStoreVO);

    /**
     * 포스기능키 매장정보 삭제 (적용매장 정보)
     * @param funcStoreVO
     * @return
     */
    int delPosFnkeyStore(FuncStoreVO funcStoreVO);

    /**
     * 매장별 포스기능키 삭제
     * @param funcStoreVO
     * @return
     */
    int delStoreFnkey(FuncStoreVO funcStoreVO);

    /**
     * 포스별 포스기능키 삭제
     * @param funcStoreVO
     * @return
     */
    int delPosFnkey(FuncStoreVO funcStoreVO);

    /**
     * 기본 포스기능키 등록 (적용매장 정보 등록)
     * @param funcStoreVO
     * @return
     */
    int regDefaultFuncStore(FuncStoreVO funcStoreVO);

    /**
     * 기본 포스기능키 등록 (매장별 포스기능키 등록)
     * @param funcStoreVO
     * @return
     */
    int regDefaultFuncStoreFnkey(FuncStoreVO funcStoreVO);

    /**
     * 기본 포스기능키 등록 포스별 포스기능키 등록)
     * @param funcStoreVO
     * @return
     */
    int regDefaultFuncPosFnkey(FuncStoreVO funcStoreVO);
}
