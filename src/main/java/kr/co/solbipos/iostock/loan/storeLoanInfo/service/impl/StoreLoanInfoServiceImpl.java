package kr.co.solbipos.iostock.loan.storeLoanInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.iostock.loan.storeLoanInfo.service.StoreLoanInfoService;
import kr.co.solbipos.iostock.loan.storeLoanInfo.service.StoreLoanInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("storeLoanInfoService")
public class StoreLoanInfoServiceImpl implements StoreLoanInfoService {

    private final StoreLoanInfoMapper storeLoanInfoMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    public StoreLoanInfoServiceImpl(StoreLoanInfoMapper storeLoanInfoMapper, PopupMapper popupMapper, MessageService messageService) {
        this.storeLoanInfoMapper = storeLoanInfoMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 매장여신관리 목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreLoanInfoList(StoreLoanInfoVO storeLoanInfoVO) {
        if(!StringUtil.getOrBlank(storeLoanInfoVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeLoanInfoVO.getStoreCd(), 3900));
            storeLoanInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return storeLoanInfoMapper.getStoreLoanInfoList(storeLoanInfoVO);
    }
}
