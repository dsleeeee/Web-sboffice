package kr.co.solbipos.store.manage.storemanage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageService;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;

@Service
public class StoreManageServiceImpl implements StoreManageService{

    @Autowired
    StoreManageMapper mapper;
    
    @Override
    public List<DefaultMap<String>> list(StoreManageVO storeManageVO) {
        return mapper.list(storeManageVO);
    }
}
