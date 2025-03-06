package kr.co.solbipos.base.store.empTalk.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.empTalk.service.EmpTalkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : EmpTalkMapper.java
 * @Description : 기초관리 > 매장관리 > 키오스크 직원대화
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.02.12  김유승      최초생성
 *
 * @author 링크 WEB개발팀 김유승
 * @since 2025.02.12
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface EmpTalkMapper {

    /** 키오스크 직원대화 관리 - 조회 */
    List<DefaultMap<Object>> getEmpTalkList(EmpTalkVO empTalkVO);

    /** 키오스크 직원대화 관리 - 추가/수정 */
    int mergeEmpTalkList(EmpTalkVO empTalkVO);

    /** 키오스크 직원대화 관리 - 삭제 */
    int deleteEmpTalkList(EmpTalkVO empTalkVO);

    /** 키오스크 직원대화 관리 - 대화코드 자동채번 */
    String getMaxEmpTextNo(EmpTalkVO empTalkVO);

    /** 키오스크 직원대화 관리 - 표시순서 자동채번 */
    String getMaxDispSeq(EmpTalkVO empTalkVO);

    /** 키오스크 직원대화 관리 - 기준매장 상용구 적용 */
    int mergeEmpTalkRegStore(EmpTalkVO empTalkVO);
}
