package kr.co.solbipos.iostock.loan.loanInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.iostock.loan.loanInfo.service.LoanInfoService;
import kr.co.solbipos.iostock.loan.loanInfo.service.LoanInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("LoanInfoService")
public class LoanInfoServiceImpl implements LoanInfoService {
    private final LoanInfoMapper loanInfoMapper;
    private final MessageService messageService;

    public LoanInfoServiceImpl(LoanInfoMapper loanInfoMapper, MessageService messageService) {
        this.loanInfoMapper = loanInfoMapper;
        this.messageService = messageService;
    }

    /** 여신현황 목록 조회 */
    @Override
    public List<DefaultMap<String>> getLoanInfoList(LoanInfoVO loanInfoVO) {
        return loanInfoMapper.getLoanInfoList(loanInfoVO);
    }

}
