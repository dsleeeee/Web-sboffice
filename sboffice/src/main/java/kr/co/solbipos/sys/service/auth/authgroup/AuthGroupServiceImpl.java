package kr.co.solbipos.sys.service.auth.authgroup;

import static kr.co.solbipos.utils.DateUtil.currentDateTimeString;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.enums.grid.GridDataFg;
import kr.co.solbipos.application.enums.user.OrgnFg;
import kr.co.solbipos.application.persistence.login.LoginMapper;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.enums.UseYn;
import kr.co.solbipos.exception.JsonException;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthGroup;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthorExcept;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthorGrpResrce;
import kr.co.solbipos.sys.enums.IncldExcldFg;
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
    public List<DefaultMap<String>> listAvailGroup(SessionInfo sessionInfo) {
        return mapper.selectAvailGroup(sessionInfo.getOrgnCd());
    }

    @Override
    public List<DefaultMap<String>> list(AuthGroup authGroup, SessionInfo sessionInfo) {
        if(sessionInfo.getOrgnFg() != OrgnFg.MASTER) {
            authGroup.setTargetOrgn(sessionInfo.getOrgnCd());
        }
        return mapper.selectGroup(authGroup);
    }

    @Override
    public int save(AuthGroup[] authGroups, SessionInfo sessionInfo) {
        
        int procCnt = 0;
        String dt = currentDateTimeString();
        for(AuthGroup authGroup: authGroups) {
            
            // 기본값 세팅
            authGroup.setRegDt(dt);
            authGroup.setModDt(dt);
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
    public List<AuthorGrpResrce> listResrce(AuthGroup authGroup, SessionInfo sessionInfo) {
        
        //Tree 생성을 위한 그룹 기준 권한이 있는 모든! 리소스(Menu 포함) 조회 - 기준
        authGroup.setGrpCd(authGroup.getGrpCd());
        List<DefaultMap<String>> list = mapper.selectResrceByGroup(authGroup);
        
        //로그인 아이디 기준 권한이 있는 기능의 탐색 경로 조회
        //Tree의 기준 데이터에서 권한이 없는 리소스를 빼기 위해 사용
        List<DefaultMap<String>> authedList = new ArrayList<DefaultMap<String>>();
        if(sessionInfo.getOrgnFg() != OrgnFg.MASTER) {
            authGroup.setGrpCd(sessionInfo.getGrpCd());
            authGroup.setUserId(sessionInfo.getUserId());
            authedList = mapper.selectResrceById(authGroup);
        }
        return makeTreeData(list, authedList);
    }

    @Override
    public int saveResrce(AuthorGrpResrce[] authorGrpResrces, SessionInfo sessionInfo) {
        int procCnt = 0;
        String insertDt = currentDateTimeString();
        for(AuthorGrpResrce authorGrpResrce: authorGrpResrces) {
            
            // 기본값 세팅
            authorGrpResrce.setRegDt(insertDt);
            authorGrpResrce.setModDt(insertDt);
            authorGrpResrce.setRegId(sessionInfo.getUserId());
            authorGrpResrce.setModId(sessionInfo.getUserId());
            
            if(authorGrpResrce.getStatus() == GridDataFg.INSERT) {
                authorGrpResrce.setUseYn(UseYn.Y);
                procCnt += mapper.mergeAuthorGrpResrce(authorGrpResrce);
            }
            else if(authorGrpResrce.getStatus() == GridDataFg.DELETE) {
                authorGrpResrce.setUseYn(UseYn.N);
                procCnt += mapper.mergeAuthorGrpResrce(authorGrpResrce);
            }
        }
        
        if(procCnt == authorGrpResrces.length) {
            return procCnt;
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
    
    @Override
    public List<AuthorGrpResrce> makeTreeData(List<DefaultMap<String>> list, 
            List<DefaultMap<String>> authedList) {
        
        List<AuthorGrpResrce> input = new ArrayList<AuthorGrpResrce>();
        AuthorGrpResrce resrce = new AuthorGrpResrce();
        for(DefaultMap<String> rm : list) {
            resrce = new AuthorGrpResrce();
            resrce.setResrceCd(rm.getStr("resrceCd"));
            resrce.setPResrce(rm.getStr("pResrce"));
            resrce.setResrceNm(rm.getStr("resrceNm"));
            resrce.setResrceDisp(rm.getStr("resrceDisp"));
            resrce.setAuthFg(rm.getLong("authFg") == 1 ? true : false);
            resrce.setItems(new ArrayList<AuthorGrpResrce>());
            input.add(resrce);
        }
        //권한이 있는 기능에서 상위 리소스 정보 생성
        //상위 리소스 맵에 없는 리소스는 권한이 없는 것으로 판단.
        DefaultMap<String> authedResrce = new DefaultMap<String>(); 
        if(authedList != null && authedList.size() > 0) {
            String resrcePath = "";
            String[] arrayRes;
            for(DefaultMap<String> item : authedList) {
                resrcePath = item.getStr("resrcePath").substring(1);
                arrayRes = resrcePath.split("\\/");
                for(String res : arrayRes) {
                    authedResrce.put(res, "");
                }
            }
            log.debug(authedResrce.toString());
        }

        Map<String, AuthorGrpResrce> hm = new LinkedHashMap<String, AuthorGrpResrce>();
        AuthorGrpResrce child;
        AuthorGrpResrce mmdParent;
        
        for(AuthorGrpResrce item : input) {
            
            if(!hm.containsKey(item.getResrceCd())) {
                if(authedResrce.size() > 0) {
                    if(authedResrce.containsKey(item.getResrceCd())) {
                        hm.put(item.getResrceCd(), item);
                    }
                }
                else {
                    hm.put(item.getResrceCd(), item);
                }
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
        
        List<AuthorGrpResrce> retrunData = new ArrayList<AuthorGrpResrce>();
        for (AuthorGrpResrce mmd : hm.values()) {
            if (mmd.getPResrce() == null || mmd.getPResrce().equals("")
                    || mmd.getPResrce().equals("000000")) {
                retrunData.add(mmd);
            }
        }
        log.debug( retrunData.toString() );
        return retrunData;
    }

    @Override
    public List<AuthorGrpResrce> listResrceById(String userId, SessionInfo sessionInfo) {

        AuthGroup authGroup = new AuthGroup();

        //Tree 생성을 위한 모든 리소스(Menu 포함) 조회
        //로그인 아이디의 그룹 기준 권한이 있는 것 체크 
        authGroup.setGrpCd(mapper.selectGrpCdById(userId));
        authGroup.setUserId(userId);
        List<DefaultMap<String>> list = mapper.selectResrceByGroupAndId(authGroup);
        
        //로그인 아이디 기준 권한이 있는 기능의 탐색 경로 조회
        //Tree의 기준 데이터에서 권한이 없는 리소스를 빼기 위해 사용
        List<DefaultMap<String>> authedList = new ArrayList<DefaultMap<String>>();
        if(sessionInfo.getOrgnFg() != OrgnFg.MASTER) {
            authGroup.setGrpCd(sessionInfo.getGrpCd());
            authGroup.setUserId(sessionInfo.getUserId());
            authedList = mapper.selectResrceById(authGroup);
        }
        
        return makeTreeData(list, authedList);
    }

    @Override
    public int saveResrceById(AuthorExcept[] authorExcepts, SessionInfo sessionInfo) {
        
        //1.화면에서 체크한 경우
        //1.1 그룹에 있을 경우 : 제외 삭제
        //1.2 그룹에 없을 경우 : 포함으로 등록 
        //2.화면에서 체크를 해제한 경우
        //2.1 그룹에 있을 경우 : 제외로 등록
        //2.2 그룹에 없을 경우 : 포함으로 삭제
        
        //로그인 아이디 그룹에 리소스를 모두 조회
        DefaultMap<String> resMap = new DefaultMap<String>();
        if(authorExcepts.length > 0 ) {
            List<DefaultMap<String>> list = mapper.selectResrceByGrp(authorExcepts[0].getUserId());
            for(DefaultMap<String> item : list) {
                resMap.put(item.getStr("resrceCd"), "");
            }
        }

        int procCnt = 0;
        String insertDt = currentDateTimeString();
        for(AuthorExcept authorExcept: authorExcepts) {
            
            // 기본값 세팅
            authorExcept.setRegDt(insertDt);
            authorExcept.setModDt(insertDt);
            authorExcept.setRegId(sessionInfo.getUserId());
            authorExcept.setModId(sessionInfo.getUserId());
            
            //1.화면에서 체크한 경우
            if(authorExcept.getStatus() == GridDataFg.INSERT) {
                //1.1 그룹에 있을 경우 : (기존)제외 삭제,
                if(resMap.containsKey(authorExcept.getResrceCd())) {
                    authorExcept.setIncldExcldFg(IncldExcldFg.EXCLUDE);
                    authorExcept.setUseYn(UseYn.N);
                    procCnt += mapper.mergeAuthorExcept(authorExcept);
                }
                //1.2 그룹에 없을 경우 : 포함으로 등록 
                else {
                    authorExcept.setIncldExcldFg(IncldExcldFg.INCLUDE);
                    authorExcept.setUseYn(UseYn.Y);
                    procCnt += mapper.mergeAuthorExcept(authorExcept);
                }
            }
            //2.화면에서 체크를 해제한 경우
            else if(authorExcept.getStatus() == GridDataFg.DELETE) {
                //2.1 그룹에 있을 경우 : 제외로 등록
                if(resMap.containsKey(authorExcept.getResrceCd())) {
                    authorExcept.setIncldExcldFg(IncldExcldFg.EXCLUDE);
                    authorExcept.setUseYn(UseYn.Y);
                    procCnt += mapper.mergeAuthorExcept(authorExcept);
                }
                //2.2 그룹에 없을 경우 : 포함으로 삭제
                else {
                    authorExcept.setIncldExcldFg(IncldExcldFg.INCLUDE);
                    authorExcept.setUseYn(UseYn.N);
                    procCnt += mapper.mergeAuthorExcept(authorExcept);
                }
            }
        }
        
        if(procCnt == authorExcepts.length) {
            return procCnt;
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

}
