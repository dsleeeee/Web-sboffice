package kr.co.solbipos.iostock.loan.loanInfo.service;

import kr.co.common.data.structure.DefaultMap;

import java.util.List;

public interface LoanInfoService {
    /** 여신현황 목록 조회 */
    List<DefaultMap<String>> getLoanInfoList(LoanInfoVO loanInfoVO);

    /** 매장여신정보 조회 */
    List<DefaultMap<String>> getStoreLoanManageList(LoanInfoVO loanInfoVO);
}
