package kr.co.solbipos.store.manage.virtuallogin.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginService;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginVO;

@Service
public class VirtualLoginServiceImpl implements VirtualLoginService {

    @Override
    public List<DefaultMap<String>> selectVirtualLogin(VirtualLoginVO virtualLoginVO) {
        return null;
    }


}