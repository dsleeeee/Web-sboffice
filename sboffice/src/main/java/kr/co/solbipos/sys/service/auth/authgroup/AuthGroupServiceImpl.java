package kr.co.solbipos.sys.service.auth.authgroup;

import static kr.co.solbipos.utils.DateUtil.currentDateTimeString;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.application.enums.grid.GridDataFg;
import kr.co.solbipos.application.persistence.login.LoginMapper;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.enums.UseYn;
import kr.co.solbipos.exception.JsonException;
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
    LoginMapper loginMapper;
    
    @Autowired
    MessageService messageService;

    @Override
    public List<DefaultMap<String>> list(AuthGroup authGroup) {
        return mapper.selectGroup(authGroup);
    }

    @Override
    public int save(AuthGroup[] authGroups, SessionInfo sessionInfo) {
        
        int procCnt = 0;
        String insertDt = currentDateTimeString();
        for(AuthGroup authGroup: authGroups) {
            
            // 기본값 세팅
            authGroup.setRegDt(insertDt);
            authGroup.setModDt(insertDt);
            authGroup.setRegId(sessionInfo.getUserId());
            authGroup.setModId(sessionInfo.getUserId());
            
            if(authGroup.getStatus() == GridDataFg.INSERT) {
                authGroup.setUseYn(UseYn.Y);
                procCnt += mapper.insertGroup(authGroup);
            }
            else if(authGroup.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateGroup(authGroup);
            }
            else if(authGroup.getStatus() == GridDataFg.DELETE) {
                authGroup.setUseYn(UseYn.N);
                procCnt += mapper.deleteGroup(authGroup);
            }
        }
        
        if(procCnt == authGroups.length) {
            return procCnt;
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    @Override
    public List<DefaultMap<String>> listResrce(AuthGroup authGroup, SessionInfo sessionInfo) {
        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        
        List<ResrceInfo> menu =loginMapper.selectAuthMenu(sessionInfo);
        
        return list;
    }

    @Override
    public int saveResrce(ResrceInfo[] resrceInfo, SessionInfo sessionInfo) {
        // TODO Auto-generated method stub
        return 0;
    }

}
