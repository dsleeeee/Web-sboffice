package kr.co.solbipos.base.store.empTalk.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.empTalk.service.EmpTalkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
