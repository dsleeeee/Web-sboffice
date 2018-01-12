package kr.co.solbipos.application.service.user;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.persistance.user.UserMapper;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.system.Prop;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    Prop prop;
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    UserMapper userMapper;

    @Override
    public String selectUserCheck(HashMap<String, String> param) {
        return userMapper.selectUserCheck(param);
    }

}


