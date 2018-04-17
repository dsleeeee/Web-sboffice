package kr.co.solbipos.sys.service.auth.authgroup;

import static kr.co.solbipos.utils.DateUtil.currentDateTimeString;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.Resrce;
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
import lombok.extern.slf4j.Slf4j;

/**
 * 시스템관리 - 권한관리 - 권한 그룹 관리
 * @author 조병준
 *
 */
@Slf4j
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
    public List<Resrce> listResrce(AuthGroup authGroup, SessionInfo sessionInfo) {
        
        authGroup.setUserId(sessionInfo.getUserId());
        List<DefaultMap<String>> list = mapper.selectResrce(authGroup);
        
        return makeTreeData(list);
    }

    @Override
    public int saveResrce(ResrceInfo[] resrceInfo, SessionInfo sessionInfo) {
        // TODO Auto-generated method stub
        return 0;
    }

    private List<Resrce> makeTreeData(List<DefaultMap<String>> list) {
        List<Resrce> input = new ArrayList<Resrce>();
        Resrce resrce = new Resrce();
        for(DefaultMap<String> rm : list) {
            resrce = new Resrce();
            resrce.setResrceCd(rm.getStr("resrceCd"));
            resrce.setPResrce(rm.getStr("pResrce"));
            resrce.setResrceNm(rm.getStr("resrceNm"));
            resrce.setAuthFg(rm.getLong("authFg"));
            resrce.setItems(new ArrayList<Resrce>());
            input.add(resrce);
        }

        Map<String, Resrce> hm = new LinkedHashMap<String, Resrce>();
        Resrce child;
        Resrce mmdParent;
        
        for(Resrce item : input) {
            if( !hm.containsKey(item.getResrceCd()) ) {
                hm.put(item.getResrceCd(), item);
            }
            child = hm.get(item.getResrceCd());
            
            if(!item.getPResrce().equals("") && !item.getPResrce().equals("000000")) {
                if(hm.containsKey(item.getPResrce())) {
                    mmdParent = hm.get(item.getPResrce());
                    mmdParent.getItems().add(child);
                }
            }
            
        }
        //log.debug( hm.toString() );
        
        List<Resrce> retrunData = new ArrayList<Resrce>();
        for (Resrce mmd : hm.values()) {
            if (mmd.getPResrce() == null || mmd.getPResrce().equals("")
                    || mmd.getPResrce().equals("000000")) {
                retrunData.add(mmd);
            }
        }
        log.debug( retrunData.toString() );
        return retrunData;
    }

}
