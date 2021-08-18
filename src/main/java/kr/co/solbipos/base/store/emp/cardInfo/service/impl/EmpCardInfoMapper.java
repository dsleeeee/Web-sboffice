package kr.co.solbipos.base.store.emp.cardInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.emp.cardInfo.service.EmpCardInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : EmpCardInfoMapper.java
 * @Description : 기초관리 - 사원관리 - 사원카드정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface EmpCardInfoMapper {

    /** 사원카드정보 조회 */
    List<DefaultMap<Object>> getEmpCardInfo(EmpCardInfoVO empCardInfoVO);

    /** 사원카드정보 삭제 */
    int deleteEmpCardInfo(EmpCardInfoVO empCardInfoVO);

    /** 사원카드정보 등록 */
    int insertEmpCardInfo(EmpCardInfoVO empCardInfoVO);

}
