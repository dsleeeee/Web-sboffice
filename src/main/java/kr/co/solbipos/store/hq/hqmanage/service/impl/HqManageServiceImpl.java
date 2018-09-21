package kr.co.solbipos.store.hq.hqmanage.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.security.EncUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.loginstatus.enums.SysStatFg;
import kr.co.solbipos.store.hq.brand.enums.TargtFg;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import kr.co.solbipos.store.hq.hqmanage.service.*;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : HqManageServiceImpl.java
 * @Description : 가맹점관리 > 본사정보 > 본사정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service
public class HqManageServiceImpl implements HqManageService{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final String DEFAULT_POS_EMPNO = "0000";
    private final String DEFAULT_POS_PASSWORD = "1234";
    private final String SYS_CLOSURE_DATE = "99991231";


    @Autowired
    HqManageMapper mapper;

    @Autowired
    MessageService messageService;

    /** 본사 목록 조회 */
    @Override
    public List<DefaultMap<String>> list(HqManageVO hqManage) {
        return mapper.list(hqManage);
    }

    /** 본사 상세정보 조회 */
    @Override
    public DefaultMap<String> dtlInfo(HqManageVO hqManage) {
        return mapper.dtlInfo(hqManage);
    }

    /** 사업자번호 중복 체크 */
    @Override
    public int chkBizNo(HqManageVO hqManage) {
        return mapper.chkBizNo(hqManage);
    }

    /** 사업자번호 사용현황 목록 */
    @Override
    public List<DefaultMap<String>> getBizUseList(HqManageVO hqManage) {
        return mapper.getBizUseList(hqManage);
    }

    /** 사업자번호 사용현황 상세 */
    @Override
    public DefaultMap<String> getBizInfoDtl(HqManageVO hqManage) {
        return mapper.getBizInfoDtl(hqManage);
    }

    /** 본사 신규 등록 */
    @Override
    public int regist(HqManageVO hqManage, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        hqManage.setRegDt(dt);
        hqManage.setModDt(dt);
        hqManage.setRegId(sessionInfoVO.getUserId());
        hqManage.setModId(sessionInfoVO.getUserId());

        if(SysStatFg.CLOSE == hqManage.getSysStatFg() ) {
            hqManage.setSysClosureDate(SYS_CLOSURE_DATE);
        } else  if(SysStatFg.DEMO == hqManage.getSysStatFg() ) {
            hqManage.setSysClosureDate(SYS_CLOSURE_DATE);
        } else {
            hqManage.setSysClosureDate(currentDateString());
        }

        // 본사 코드 조회
        String hqOfficeCd = mapper.getHqOfficeCd(hqManage);

        String wUuserId = hqOfficeCd.toLowerCase(); // 웹 사용자 아이디
        String wUserPwd = EncUtil.setEncSHA256(wUuserId + DEFAULT_POS_EMPNO);    // 웹 패스워드
        String pEmpNo = DEFAULT_POS_EMPNO; // 포스 기본 사용자 사원번호
        String pUserPwd = EncUtil.setEncSHA256(pEmpNo + DEFAULT_POS_PASSWORD);  // 포스 패스워드

        hqManage.setHqOfficeCd(hqOfficeCd);
        hqManage.setUserId(wUuserId);
        hqManage.setUserPwd(wUserPwd);
        hqManage.setPosEmpNo(pEmpNo);
        hqManage.setPosUserPwd(pUserPwd);

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

        // 코드 등록 (본사 코드 마스터 - TB_HQ_NMCODE)
        // TB_CM_NMCODE와 별개로 본사에서 사용하는 본사전용코드
        int cmmCodeReg = 0;

        HqNmcodeVO nmcodeVO = new HqNmcodeVO();

        nmcodeVO.setHqOfficeCd(hqOfficeCd);
        nmcodeVO.setUseYn(UseYn.Y);
        nmcodeVO.setRegDt(dt);
        nmcodeVO.setRegId(sessionInfoVO.getUserId());
        nmcodeVO.setModDt(dt);
        nmcodeVO.setModId(sessionInfoVO.getUserId());

        // 본사 등록시, 초기 등록 공통 코드
        String registCmmCode[][] = {
            {"000", "097", "주문단위"}, // 주문단위
            {"097", "0", "낱개"},
            {"097", "1", "세트"},
            {"000", "098", "판매형태"}, // 판매형태
            {"098", "0", "정상"},
            {"000", "099", "입금계정"}, // 입금계정
            {"099", "00", "입금"},
            {"000", "100", "매장형태"}, // 매장형태
            {"100", "1", "직영"},
            {"100", "2", "가맹"},
            {"000", "101", "매장그룹"}, // 매장그룹
            {"101", "100", "일반"},
            {"000", "102", "고객분류"}, // 고객분류
            {"102", "01", "남"},
            {"102", "02", "여"},
            {"000", "096", "기본매출시간대"} // 기본 매출 시간대
        };

        for(int i=0; i<registCmmCode.length; i++) {
            String cmmCode[] = registCmmCode[i];
            nmcodeVO.setNmcodeGrpCd(cmmCode[0]);
            nmcodeVO.setNmcodeCd(cmmCode[1]);
            nmcodeVO.setNmcodeNm(cmmCode[2]);

            cmmCodeReg += mapper.cmmCodeReg(nmcodeVO);
        }

        // 기본 매출 시간대
        for(int i=0; i<24; i++){

            String nmcodeNm = "";

            if(i<=5)                nmcodeNm = "심야";
            else if(i>=6  && i<=10) nmcodeNm = "아침";
            else if(i>=11 && i<=15) nmcodeNm = "점심";
            else if(i>=16 && i<=23) nmcodeNm = "저녁";

            nmcodeVO.setNmcodeGrpCd("096");
            nmcodeVO.setNmcodeCd(StringUtil.lpad(String.valueOf(i), 2));
            nmcodeVO.setNmcodeNm(nmcodeNm);

            int saleTimeReg = mapper.cmmCodeReg(nmcodeVO);

            cmmCodeReg += saleTimeReg;
        }

        // 필요 공통코드 복사
        String copyCmmCode[] = {"062"};

        for(int i=0; i<copyCmmCode.length; i++) {
            String copyCode = copyCmmCode[i];
            nmcodeVO.setNmcodeGrpCd(copyCode);
            cmmCodeReg += mapper.copyCmmCode(nmcodeVO);
        }

        // 공통코드 테이블에서 Tid 복사
        HqNmcodeVO tidResult = new HqNmcodeVO();
        tidResult.setResult(mapper.copyTid(nmcodeVO));

        // 포스 출력물 등록
        HqPrintTemplVO printTempVO = new HqPrintTemplVO();

        printTempVO.setHqOfficeCd(hqOfficeCd);
        printTempVO.setRegDt(dt);
        printTempVO.setRegId(sessionInfoVO.getUserId());
        printTempVO.setModDt(dt);
        printTempVO.setModId(sessionInfoVO.getUserId());

        //TODO
        //int printTempReg = mapper.hqPrintTempReg(printTempVO);
        //procCnt += printTempReg;

        procCnt += cmmCodeReg;

        return procCnt;
    }

    /** 본사 수정 */
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

    /** 권한그룹 목록 조회 */
    @Override
    public List<DefaultMap<String>> authHqList(HqManageVO hqManage) {
        return mapper.authHqList(hqManage);
    }

    /** 사용가능한 메뉴 */
    @Override
    public List<DefaultMap<String>> avlblMenu(HqManageVO hqManage) {
        return mapper.avlblMenu(hqManage);
    }

    /** 사용중인 메뉴 */
    @Override
    public List<DefaultMap<String>> beUseMenu(HqManageVO hqManage) {
        return mapper.beUseMenu(hqManage);
    }

    /** 메뉴권한복사 */
    @Override
    public int copyAuth(HqMenuVO hqMenuVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        hqMenuVO.setRegDt(dt);
        hqMenuVO.setRegId(sessionInfoVO.getUserId());
        hqMenuVO.setModDt(dt);
        hqMenuVO.setModId(sessionInfoVO.getUserId());

        // hqOfficeCd : 복사 대상이 되는 본사
        // copyHqOfficeCd : 복사할 기준이 되는 본사

        // 권한 복사
        int authGrpCopy = mapper.copyAuth(hqMenuVO);
        int authExpCopy = mapper.copyAuthExcp(hqMenuVO);

        if(authGrpCopy <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else if(authExpCopy <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return (authGrpCopy+authExpCopy);
    }

    /** 메뉴 권한 추가 */
    @Override
    public int addAuth(HqMenuVO[] hqMenus, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String insertDt = currentDateTimeString();

        for(HqMenuVO hqMenu : hqMenus){

            hqMenu.setIncldExcldFg(IncldExcldFg.INCLUDE);
            hqMenu.setRegDt(insertDt);
            hqMenu.setRegId(sessionInfoVO.getUserId());
            hqMenu.setModDt(insertDt);
            hqMenu.setModId(sessionInfoVO.getUserId());

            procCnt = mapper.addAuth(hqMenu);
        }
        return procCnt;
    }

    /** 메뉴 권한 삭제 */
    @Override
    public int removeAuth(HqMenuVO[] hqMenus, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String insertDt = currentDateTimeString();

        for(HqMenuVO hqMenu : hqMenus){
            hqMenu.setIncldExcldFg(IncldExcldFg.INCLUDE);
            hqMenu.setRegDt(insertDt);
            hqMenu.setRegId(sessionInfoVO.getUserId());
            hqMenu.setModDt(insertDt);
            hqMenu.setModId(sessionInfoVO.getUserId());

            // 권한 예외 테이블에 있는지 조회 후, 예외로 들어간 권한이 있으면 삭제
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

    /** 환경설정 조회 */
    @Override
    public List<DefaultMap<String>> getConfigList(HqManageVO hqManageVO) {
        return mapper.getConfigList(hqManageVO);
    }

    /** 환경설정 저장 */
    @Override
    public int saveConfig(HqEnvstVO[] hqEnvsts, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(HqEnvstVO hqEnvst: hqEnvsts) {

            hqEnvst.setRegDt(dt);
            hqEnvst.setRegId(sessionInfoVO.getUserId());
            hqEnvst.setModDt(dt);
            hqEnvst.setModId(sessionInfoVO.getUserId());

            if(hqEnvst.getStatus() == GridDataFg.INSERT) {
                hqEnvst.setUseYn(UseYn.Y);
                procCnt += mapper.insertConfig(hqEnvst);
            }
            else if(hqEnvst.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateConfig(hqEnvst);
            }

            // 적용 타겟이 본사기준 매장까지인 경우, 매장까지 적용
            // 단독매장 제외(프렌차이즈만 해당)
            if("00000".equals(hqEnvst.getHqOfficeCd()) && hqEnvst.getTargtFg() == TargtFg.BOTH ) {
                procCnt += mapper.updateConfigStore(hqEnvst);
            }
        }
        return procCnt;
    }


}
