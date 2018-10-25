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
     * 근태 관리 리스트 조회
     *
     * @param dclzManageVO
     * @return
     */
    List<DefaultMap<String>> selectDclzManage(DclzManageVO dclzManageVO);

    /**
     * 근태 등록
     *
     * @param dclzManageVO
     * @return
     */
    int insertDclzManage(DclzManageVO dclzManageVO);

    /**
     * 근태 수정
     *
     * @param dclzManageVO
     * @return
     */
    int updateDclzManage(DclzManageVO dclzManageVO);

    /**
     * 근태 삭제
     *
     * @param dclzManageVO
     * @return
     */
    int deleteDclzManage(DclzManageVO dclzManageVO);

    /**
     * 임직원 조회 > 근태 등록시에 해당되는 매장의 근태 등록 가능한 임직원 목록을 조회
     *
     * @param dclzManageVO
     * @return
     */
    List<DefaultMap<String>> selectStoreEmployee(DclzManageVO dclzManageVO);

    /**
     * 해당 근무일에 근태가 있는지 확인
     *
     * @param dclzManageVO
     * @return
     */
    int selectWorkCheck(DclzManageVO dclzManageVO);
}
