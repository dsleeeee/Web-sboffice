package kr.co.solbipos.adi.dclz.dclzmanage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DclzManageMapper.java
 * @Description : 부가서비스 > 근태 관리 > 근태 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DclzManageMapper {

    /**
     * 근태관리 리스트 조회
     *
     * @param dclzManageVO
     * @return
     */
    List<DefaultMap<String>> selectDclzManage(DclzManageVO dclzManageVO);

    /**
     * 매장 사원 조회
     *
     * @param dclzManageVO
     * @return
     */
    List<DefaultMap<String>> selectStoreEmployee(DclzManageVO dclzManageVO);

    /**
     * 근태등록
     *
     * @param dclzManageVO
     * @return
     */
    int insertDclzManage(DclzManageVO dclzManageVO);

    /**
     * 근태수정
     *
     * @param dclzManageVO
     * @return
     */
    int updateDclzManage(DclzManageVO dclzManageVO);

    /**
     * 근태삭제
     *
     * @param dclzManageVO
     * @return
     */
    int deleteDclzManage(DclzManageVO dclzManageVO);

    /**
     * 근태 등록여부 확인
     *
     * @param dclzManageVO
     * @return
     */
    int selectWorkCheck(DclzManageVO dclzManageVO);

    /**
     * 근태상세정보 조회
     *
     * @param dclzManageVO
     * @return
     */
    DefaultMap<String> selectDclzManageDtl(DclzManageVO dclzManageVO);
}
