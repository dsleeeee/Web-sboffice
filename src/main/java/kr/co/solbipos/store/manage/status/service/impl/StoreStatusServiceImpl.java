package kr.co.solbipos.store.manage.status.service.impl;

import kr.co.common.service.message.MessageService;
import kr.co.solbipos.sale.day.day.service.impl.DayMapper;
import kr.co.solbipos.store.manage.status.service.StoreStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("storeStatusService")
public class StoreStatusServiceImpl implements StoreStatusService {
    private final StoreStatusMapper storeStatusMapper;

    @Autowired
    public StoreStatusServiceImpl(StoreStatusMapper storeStatusMapper) {
        this.storeStatusMapper = storeStatusMapper;
    }

}
