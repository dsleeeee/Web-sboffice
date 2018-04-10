package kr.co.solbipos.sys.service.auth.authgroup;

import static kr.co.solbipos.utils.DateUtil.currentDateTimeString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthGroup;
import kr.co.solbipos.sys.persistence.auth.authgroup.AuthGroupMapper;

/**
 * 시스템관리 - 권한관리 - 권한 그룹 관리
 * @author 조병준
 *
 */
@Service
public class AuthGroupServiceImpl implements AuthGroupService {

    @Autowired
    AuthGroupMapper mapper;

    @Autowired
    MessageService messageService;

    @Override
    public List<DefaultMap<String>> list(AuthGroup authGroup) {
        return mapper.select(authGroup);
    }

    @Override
    public int save(AuthGroup authGroup, SessionInfo sessionInfo) {

        // 기본값 세팅
        String insertDt = currentDateTimeString();
        authGroup.setRegDt(insertDt);
        authGroup.setModDt(insertDt);
        authGroup.setRegId(sessionInfo.getUserId());
        authGroup.setModId(sessionInfo.getUserId());
        return mapper.save(authGroup);
    }

}
