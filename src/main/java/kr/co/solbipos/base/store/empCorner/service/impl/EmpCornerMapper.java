package kr.co.solbipos.base.store.empCorner.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.empCorner.service.EmpCornerVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : EmpCornerMapper.java
 * @Description : 기초관리 > 매장관리 > 사원별코너관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.05.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.05.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface EmpCornerMapper {

    /** 사원별탭 - 사원정보 조회 */
    List<DefaultMap<Object>> getEmpCornerEmpList(EmpCornerVO empCornerVO);

    /** 사원별탭 - 관리코너 조회 */
    List<DefaultMap<Object>> getEmpManageCornerList(EmpCornerVO empCornerVO);

    /** 사원별탭 - 미관리코너 조회 */
    List<DefaultMap<Object>> getEmpNoManageCornerList(EmpCornerVO empCornerVO);

    /** 사원별탭 - 관리코너 추가 */
    int getEmpManageCornerSave(EmpCornerVO empCornerVO);

    /** 사원별탭 - 관리코너 삭제 */
    int getEmpManageCornerDelete(EmpCornerVO empCornerVO);

    /** 코너별탭 - 코너정보 조회 */
    List<DefaultMap<Object>> getEmpCornerCornerList(EmpCornerVO empCornerVO);

    /** 코너별탭 - 관리사원 조회 */
    List<DefaultMap<Object>> getCornerManageEmpList(EmpCornerVO empCornerVO);

    /** 코너별탭 - 미관리사원 조회 */
    List<DefaultMap<Object>> getCornerNoManageEmpList(EmpCornerVO empCornerVO);

    /** 코너별탭 - 관리사원 추가 */
    int getCornerManageEmpSave(EmpCornerVO empCornerVO);

    /** 코너별탭 - 관리사원 삭제 */
    int getCornerManageEmpDelete(EmpCornerVO empCornerVO);
}