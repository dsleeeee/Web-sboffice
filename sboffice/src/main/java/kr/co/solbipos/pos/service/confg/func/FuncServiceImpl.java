package kr.co.solbipos.pos.service.confg.func;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.pos.persistence.confg.func.FuncMapper;
import kr.co.solbipos.structure.DefaultMap;

@Service
public class FuncServiceImpl implements FuncService{

    
    @Autowired
    FuncMapper funcMapper;

    @Override
    public List<DefaultMap<String>> getFuncList(SessionInfo sessionInfo) {
        return funcMapper.getFuncList(sessionInfo);
    }
}
