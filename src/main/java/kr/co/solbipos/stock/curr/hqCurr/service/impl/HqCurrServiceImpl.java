package kr.co.solbipos.stock.curr.hqCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrService;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("hqCurrService")
public class HqCurrServiceImpl implements HqCurrService {
    private final HqCurrMapper hqCurrMapper;
    private final MessageService messageService;

    @Autowired
    public HqCurrServiceImpl(HqCurrMapper hqCurrMapper, MessageService messageService) {
        this.hqCurrMapper = hqCurrMapper;
        this.messageService = messageService;
    }

    /** 현재고현황 - 현재고현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqCurrList(HqCurrVO hqCurrVO) {
        return hqCurrMapper.getHqCurrList(hqCurrVO);
    }

}
