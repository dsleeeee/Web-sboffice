package kr.co.solbipos.store.service.hq.hqmanage;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.pos.enums.SysStatFg;
import kr.co.solbipos.store.domain.hq.hqbrand.HqBrandVO;
import kr.co.solbipos.store.domain.hq.hqmanage.HqManageVO;
import kr.co.solbipos.store.domain.hq.hqmanage.HqMenuVO;
import kr.co.solbipos.store.domain.hq.hqmanage.HqNmcodeVO;
import kr.co.solbipos.store.domain.hq.hqmanage.HqPrintTemplVO;
import kr.co.solbipos.store.persistence.hq.hqbrand.HqBrandMapper;
import kr.co.solbipos.store.persistence.hq.hqmanage.HqManageMapper;
import kr.co.solbipos.sys.enums.IncldExcldFg;

@Service
public class HqManageServiceImpl implements HqManageService{

    @Autowired
    HqManageMapper mapper;

    @Autowired
    HqBrandMapper brandMapper;

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
        
        // 본사 코드 조회
        String hqOfficeCd = mapper.getHqOfficeCd(hqManage);
        
        hqManage.setHqOfficeCd(hqOfficeCd);
        
        // 본사 등록
        int procCnt = mapper.regist(hqManage);
        
        if(procCnt > 0) {
            
            // 기본 사원 등록
            int employeeReg = mapper.registEmployee(hqManage);
            // 웹 사용자 등록
            int webUserReg = mapper.registWebUser(hqManage);
            
            procCnt += employeeReg + webUserReg;
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
        // 기본 브랜드 등록
        HqBrandVO hqBrandVO = new HqBrandVO();
        
        hqBrandVO.setHqOfficeCd(hqOfficeCd);

        // 브랜드 코드 조회
        String hqBrandCd = brandMapper.getHqBrandCd(hqBrandVO);
        
        hqBrandVO.setHqBrandCd(hqBrandCd);
        hqBrandVO.setHqBrandNm(hqManage.getHqOfficeNm());
        hqBrandVO.setUseYn(UseYn.Y);
        hqBrandVO.setRegDt(dt);
        hqBrandVO.setRegId(sessionInfoVO.getUserId());
        hqBrandVO.setModDt(dt);
        hqBrandVO.setModId(sessionInfoVO.getUserId());
        
        // 브랜드 등록
        int brandReg = brandMapper.insertBrand(hqBrandVO);
        
        procCnt += brandReg;
        
        // 코드 등록 (본사 코드 마스터)
        int cmmCodeReg = 0;
        
        HqNmcodeVO nmcodeVO = new HqNmcodeVO();
        
        nmcodeVO.setHqBrandCd(hqBrandCd);
        nmcodeVO.setUseYn(UseYn.Y);
        nmcodeVO.setRegDt(dt);
        nmcodeVO.setRegId(sessionInfoVO.getUserId());
        nmcodeVO.setModDt(dt);
        nmcodeVO.setModId(sessionInfoVO.getUserId());
        
        // 주문단위 등록
        nmcodeVO.setNmcodeGrpCd("097");
        nmcodeVO.setNmcodeCd("0");
        nmcodeVO.setNmcodeNm("낱개");

        cmmCodeReg += mapper.cmmCodeReg(nmcodeVO);
        
        nmcodeVO.setNmcodeGrpCd("097");
        nmcodeVO.setNmcodeCd("1");
        nmcodeVO.setNmcodeNm("낱개");
        
        cmmCodeReg += mapper.cmmCodeReg(nmcodeVO);
        
        // 판매형태
        nmcodeVO.setNmcodeGrpCd("098");
        nmcodeVO.setNmcodeCd("0");
        nmcodeVO.setNmcodeNm("정상");
        
        cmmCodeReg += mapper.cmmCodeReg(nmcodeVO);

        // 입금계정 기본 등록
        nmcodeVO.setNmcodeGrpCd("099");
        nmcodeVO.setNmcodeCd("00");
        nmcodeVO.setNmcodeNm("입금");
        
        cmmCodeReg += mapper.cmmCodeReg(nmcodeVO);
        
        // 매장형태 - 직영
        nmcodeVO.setNmcodeGrpCd("100");
        nmcodeVO.setNmcodeCd("1");
        nmcodeVO.setNmcodeNm("직영");
        
        cmmCodeReg += mapper.cmmCodeReg(nmcodeVO);

        // 매장형태 - 가맹
        nmcodeVO.setNmcodeGrpCd("100");
        nmcodeVO.setNmcodeCd("2");
        nmcodeVO.setNmcodeNm("가맹");
        
        cmmCodeReg += mapper.cmmCodeReg(nmcodeVO);

        // 매장그룹
        nmcodeVO.setNmcodeGrpCd("101");
        nmcodeVO.setNmcodeCd("100");
        nmcodeVO.setNmcodeNm("일반");
        
        cmmCodeReg += mapper.cmmCodeReg(nmcodeVO);

        // 고객분류
        nmcodeVO.setNmcodeGrpCd("102");
        nmcodeVO.setNmcodeCd("01");
        nmcodeVO.setNmcodeNm("남");
        
        cmmCodeReg += mapper.cmmCodeReg(nmcodeVO);
        
        nmcodeVO.setNmcodeGrpCd("102");
        nmcodeVO.setNmcodeCd("02");
        nmcodeVO.setNmcodeNm("여");
        
        cmmCodeReg += mapper.cmmCodeReg(nmcodeVO);
        
        // 포스 출력물 등록
        HqPrintTemplVO printTempVO = new HqPrintTemplVO();
        
        printTempVO.setHqBrandCd(hqBrandCd);
        printTempVO.setRegDt(dt);
        printTempVO.setRegId(sessionInfoVO.getUserId());
        printTempVO.setModDt(dt);
        printTempVO.setModId(sessionInfoVO.getUserId());
        
        int printTempReg = mapper.hqPrintTempReg(printTempVO);
        
        procCnt += printTempReg;
        
        // 기본 매출 시간대 등록 (심야:00시-05시, 아침:06시-10시, 점심:11시-15시, 저녁:16시-23시)
        for(int i=0; i<24; i++){
            
            String nmcodeNm = "";
            
            if(i<=5)                nmcodeNm = "심야";
            else if(i>=6  && i<=10) nmcodeNm = "아침";
            else if(i>=11 && i<=15) nmcodeNm = "점심";
            else if(i>=16 && i<=23) nmcodeNm = "저녁";

            nmcodeVO.setNmcodeGrpCd("096"); // 기본 매출 시간대
            nmcodeVO.setNmcodeCd(StringUtil.lpad(String.valueOf(i), 2));
            nmcodeVO.setNmcodeNm(nmcodeNm);
            
            int saleTimeReg = mapper.cmmCodeReg(nmcodeVO);
            
            cmmCodeReg += saleTimeReg;
        }
        
        procCnt += cmmCodeReg;
        
        return procCnt;
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
