package kr.co.solbipos.store.service.hq.hqmanage;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.pos.enums.SysStatFg;
import kr.co.solbipos.store.domain.hq.hqmanage.HqManageVO;
import kr.co.solbipos.store.domain.hq.hqmanage.HqMenuVO;
import kr.co.solbipos.store.persistence.hq.hqmanage.HqManageMapper;
import kr.co.solbipos.sys.enums.IncldExcldFg;

@Service
public class HqManageServiceImpl implements HqManageService{

    @Autowired
    HqManageMapper mapper;

    @Autowired
    MessageService messageService;
    
    @Override
    public List<DefaultMap<String>> list(HqManageVO hqManage) {
        return mapper.list(hqManage);
    }

    @Override
    public DefaultMap<String> dtlInfo(HqManageVO hqManage) {
        return mapper.dtlInfo(hqManage);
    }

    @Override
    public int chkBizNo(HqManageVO hqManage) {
        return mapper.chkBizNo(hqManage);
    }
    
    @Override
    public List<DefaultMap<String>> getBizUseList(HqManageVO hqManage) {
        return mapper.getBizUseList(hqManage);
    }

    @Override
    public DefaultMap<String> getBizInfoDtl(HqManageVO hqManage) {
        return mapper.getBizInfoDtl(hqManage);
    }
    
    @Override
    public int regist(HqManageVO hqManage, SessionInfoVO sessionInfoVO) {
        
        String dt = currentDateTimeString();
        
        hqManage.setRegDt(dt);
        hqManage.setModDt(dt);
        hqManage.setRegId(sessionInfoVO.getUserId());
        hqManage.setModId(sessionInfoVO.getUserId());
        
        if(SysStatFg.CLOSE == hqManage.getSysStatFg()) {
            hqManage.setSysClosureDate("99991231");
        } else {
            hqManage.setSysClosureDate(currentDateString());
        }
        
        // 본사 코드
        String hqOfficeCd = mapper.getHqOfficeCd(hqManage);
        
        hqManage.setHqOfficeCd(hqOfficeCd);
        
        // 본사 등록
        int procCnt = mapper.regist(hqManage);
        
        if(procCnt > 0) {
            
            // 기본 사원 등록
            int employeeReg = mapper.registEmployee(hqManage);
            // 웹 사용자 등록
            int webUserReg = mapper.registWebUser(hqManage);
            
            return (employeeReg + webUserReg);
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    @Override
    public int modify(HqManageVO hqManage, SessionInfoVO sessionInfoVO) {
        
        String dt = currentDateTimeString();
        
        hqManage.setModDt(dt);
        hqManage.setModId(sessionInfoVO.getUserId());
        
        // 상태가 폐점일 경우, 폐점일 추가
        if(SysStatFg.CLOSE == hqManage.getSysStatFg()) {
            hqManage.setSysClosureDate(currentDateString());
        } else {
            hqManage.setSysClosureDate("99991231");
        }

        int procCnt = mapper.modify(hqManage);
        
        if(procCnt > 0) {
            return procCnt;
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    @Override
    public List<DefaultMap<String>> authHqList(HqManageVO hqManage) {
        return mapper.authHqList(hqManage);
    }

    @Override
    public List<DefaultMap<String>> avlblMenu(HqManageVO hqManage) {
        return mapper.avlblMenu(hqManage);
    }

    @Override
    public List<DefaultMap<String>> beUseMenu(HqManageVO hqManage) {
        return mapper.beUseMenu(hqManage);
    }

    @Override
    public int copyAuth(HqMenuVO hqMenu, SessionInfoVO sessionInfoVO) {
        
        String dt = currentDateTimeString();
        
        hqMenu.setRegDt(dt);
        hqMenu.setRegId(sessionInfoVO.getUserId());
        hqMenu.setModDt(dt);
        hqMenu.setModId(sessionInfoVO.getUserId());
        
        // hqOfficeCd : 복사 대상이 되는 본사
        // copyHqOfficeCd : 복사할 기준이 되는 본사
        System.out.println("hqOfficeCd : "+ hqMenu.getHqOfficeCd());
        System.out.println("CopyHqOfficeCd : "+ hqMenu.getCopyHqOfficeCd());
        
        int authGrpCopy = mapper.copyAuth(hqMenu, sessionInfoVO);
        int authExpCopy = mapper.copyAuthExcp(hqMenu, sessionInfoVO);
        
        if(authGrpCopy <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else if(authExpCopy <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return (authGrpCopy+authExpCopy);
    }
    
    @Override
    public int addAuth(HqMenuVO[] hqMenus, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String insertDt = currentDateTimeString();

        for(HqMenuVO hqMenu : hqMenus){
            
            //hqManage.setIncldExcldFg("I");
            hqMenu.setIncldExcldFg(IncldExcldFg.INCLUDE);
            hqMenu.setRegDt(insertDt);
            hqMenu.setRegId(sessionInfoVO.getUserId());
            hqMenu.setModDt(insertDt);
            hqMenu.setModId(sessionInfoVO.getUserId());
            
            procCnt = mapper.addAuth(hqMenu);
        }
        return procCnt;
    }

    @Override
    public int removeAuth(HqMenuVO[] hqMenus, SessionInfoVO sessionInfoVO) {

        // 삭제시에는 예외권한 테이블에 i로 있는지 확인하고 있으면 지우고
        // 그 테이블에 없으면 예외권한 테이블에 e로 넣어
        // enum insert 는  incldExcld보고 참고해
        
        int procCnt = 0;
        String insertDt = currentDateTimeString();

        for(HqMenuVO hqMenu : hqMenus){
            hqMenu.setIncldExcldFg(IncldExcldFg.INCLUDE);
            hqMenu.setRegDt(insertDt);
            hqMenu.setRegId(sessionInfoVO.getUserId());
            hqMenu.setModDt(insertDt);
            hqMenu.setModId(sessionInfoVO.getUserId());
            
            // 예외 테이블에 있는지 조회
            int isAuth = mapper.isAuth(hqMenu);
            
            if(isAuth > 0) {
                procCnt = mapper.removeAuth(hqMenu);
            }else {
                
                hqMenu.setIncldExcldFg(IncldExcldFg.EXCLUDE);
                hqMenu.setRegDt(insertDt);
                hqMenu.setRegId(sessionInfoVO.getUserId());
                hqMenu.setModDt(insertDt);
                hqMenu.setModId(sessionInfoVO.getUserId());
                
                procCnt = mapper.addAuth(hqMenu);
            }
        }
        
        return procCnt;
    }

}
