package kr.co.solbipos.pos.confg.loginstatus.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.pos.confg.loginstatus.service.LoginStatusService;
import kr.co.solbipos.pos.confg.loginstatus.service.LoginStatusVO;

@Service
@Transactional
public class LoginStatusServiceImpl implements LoginStatusService {

    @Autowired
    LoginStatusMapper loginStatusMapper;

    @Override
    public <E> List<E> selectLoginStatus(LoginStatusVO loginStatusVO) {
        return loginStatusMapper.selectLoginStatus(loginStatusVO);
    }

}


