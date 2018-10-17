package kr.co.solbipos.iostock.loan.loanInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.loan.loanInfo.service.LoanInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LoanInfoMapper {
    /** 여신현황 목록 조회 */
    List<DefaultMap<String>> getLoanInfoList(LoanInfoVO loanInfoVO);

}
