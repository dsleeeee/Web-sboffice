package kr.co.solbipos.iostock.loan.storeLoanInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.iostock.loan.storeLoanInfo.service.StoreLoanInfoService;
import kr.co.solbipos.iostock.loan.storeLoanInfo.service.StoreLoanInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StoreLoanInfoService")
public class StoreLoanInfoServiceImpl implements StoreLoanInfoService {

    @Autowired StoreLoanInfoMapper storeLoanInfoMapper;
    @Autowired
    MessageService messageService;

    /** 매장여신관리 목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreLoanInfoList(StoreLoanInfoVO storeLoanInfoVO) {
        return storeLoanInfoMapper.getStoreLoanInfoList(storeLoanInfoVO);
    }
}
