package kr.co.solbipos.stock.curr.storeCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.stock.curr.storeCurr.service.StoreCurrService;
import kr.co.solbipos.stock.curr.storeCurr.service.StoreCurrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("storeCurrService")
public class StoreCurrServiceImpl implements StoreCurrService {
    private final StoreCurrMapper storeCurrMapper;
    private final MessageService messageService;

    @Autowired
    public StoreCurrServiceImpl(StoreCurrMapper storeCurrMapper, MessageService messageService) {
        this.storeCurrMapper = storeCurrMapper;
        this.messageService = messageService;
    }

    /** 현재고현황 - 현재고현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreCurrList(StoreCurrVO storeCurrVO) {
        return storeCurrMapper.getStoreCurrList(storeCurrVO);
    }
}
