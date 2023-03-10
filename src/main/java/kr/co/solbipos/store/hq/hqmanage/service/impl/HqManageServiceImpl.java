package kr.co.solbipos.store.hq.hqmanage.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.security.EncUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.pos.loginstatus.enums.SysStatFg;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageService;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqMenuVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqNmcodeVO;
import kr.co.solbipos.store.manage.storemanage.service.MemberClassVO;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private final String DEFAULT_POS_EMPNO = "0000";    // 기본 포스 직원번호
    private final String HQ_AUTH_GRP_CD = "000007"; // TODO 보나비용 사용자 그룹코드 (화면에서 사용자 그룹 선택 필요)
    private final String DEFAULT_POS_PASSWORD = "1234"; // 기본 포스 패스워드
    private final String SYS_CLOSURE_DATE = "99991231"; // 시스템 종료일

    private final HqManageMapper mapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public HqManageServiceImpl(HqManageMapper mapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.mapper = mapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 본사 목록 조회 */
    @Override
    public List<DefaultMap<String>> list(HqManageVO hqManage, SessionInfoVO sessionInfoVO) {

        // 소속구분, 총판의 부모총판 코드
        hqManage.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        hqManage.setpAgencyCd(sessionInfoVO.getpAgencyCd());

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            hqManage.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return mapper.getHqOfficeList(hqManage);
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
        hqManage.setAuthGrpCd(HQ_AUTH_GRP_CD);   //본사사용자 기본값 "000007"

        int result = 0;

        // 본사 등록
        result = mapper.regist(hqManage);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 기본 사원 등록
        result = mapper.registEmployee(hqManage);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 웹 사용자 등록
        result = mapper.registWebUser(hqManage);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 코드 등록 (본사 코드 마스터 - TB_HQ_NMCODE)
        // TB_CM_NMCODE와 별개로 본사에서 사용하는 본사전용코드
        // todo TB_CM_NMCODE 에서 CMM 본사용으로 복사해야함.
        HqNmcodeVO nmcodeVO = new HqNmcodeVO();

        nmcodeVO.setHqOfficeCd(hqOfficeCd);
        nmcodeVO.setUseYn(UseYn.Y);
        nmcodeVO.setRegDt(dt);
        nmcodeVO.setRegId(sessionInfoVO.getUserId());
        nmcodeVO.setModDt(dt);
        nmcodeVO.setModId(sessionInfoVO.getUserId());

        // 본사 등록시, 초기 등록 공통 코드
        String registCmmCode[][] = {
            {"000", "093", "주문단위"}, // 주문단위
            {"093", "1", "낱개"},
            {"093", "2", "박스"},
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

            result = mapper.cmmCodeReg(nmcodeVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
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

            result = mapper.cmmCodeReg(nmcodeVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        // 기본 매출 시간대(001)
        result = mapper.registTimeSlot(nmcodeVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 공통코드 복사
        HqNmcodeVO hqNmcodeVO = new HqNmcodeVO();
        hqNmcodeVO.setHqOfficeCd(hqOfficeCd);
        String copyNmcodeResult = mapper.copyCmmNameCode(nmcodeVO);

        // 회원등급 생성
        MemberClassVO memberClassVO = new MemberClassVO();
        memberClassVO.setMembrOrgnCd(hqOfficeCd);
        memberClassVO.setRegDt(dt);
        memberClassVO.setRegId(sessionInfoVO.getUserId());
        memberClassVO.setModDt(dt);
        memberClassVO.setModId(sessionInfoVO.getUserId());

        result += mapper.insertMemberClass(memberClassVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 기본 창고 등록 (본사 001 기본창고)
        result += mapper.insertStorage(hqManage);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 포스 템플릿 등록
        result += mapper.hqPrintTempReg(hqManage);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
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
    public List<DefaultMap<String>> authHqList(HqManageVO hqManage, SessionInfoVO sessionInfoVO) {

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY){
            hqManage.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            hqManage.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return mapper.authHqList(hqManage);
    }

    /** 사용 메뉴 */
    @Override
    public List<DefaultMap<String>> avlblMenu(HqManageVO hqManage) {
        return mapper.avlblMenu(hqManage);
    }

    /** 미사용 메뉴 */
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

        // 1. 메뉴 권한 복사
        int authGrpCopy = mapper.copyAuth(hqMenuVO);
        if(authGrpCopy <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        // 2. 기존 메뉴권한 예외값 삭제
        mapper.removeAuthAll(hqMenuVO);

        // 3. 메뉴 권한 예외값이 있는지 확인 후, 복사
        int authExpCopy = 0;
        List<DefaultMap<String>> excepList = mapper.exceptMenu(hqMenuVO);

        if(excepList != null && excepList.size() > 0){

            for (int i = 0; i < excepList.size(); i++) {

                hqMenuVO.setResrceCd(excepList.get(i).getStr("resrceCd"));

                if("E".equals(excepList.get(i).getStr("incldExcldFg"))){
                    hqMenuVO.setIncldExcldFg(IncldExcldFg.EXCLUDE);
                }else{
                    hqMenuVO.setIncldExcldFg(IncldExcldFg.INCLUDE);
                }
                hqMenuVO.setUseYn(excepList.get(i).getStr("useYn"));

                int result = mapper.copyAuthExcp(hqMenuVO);
                if(result <= 0){
                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                } else {
                    authExpCopy ++;
                }
            }
        }

        return (authGrpCopy+authExpCopy);
    }

    /** 사용메뉴 등록 */
    @Override
    public int addAuth(HqMenuVO[] hqMenus, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String insertDt = currentDateTimeString();

        for(HqMenuVO hqMenu : hqMenus){

            hqMenu.setIncldExcldFg(IncldExcldFg.EXCLUDE);
            hqMenu.setRegDt(insertDt);
            hqMenu.setRegId(sessionInfoVO.getUserId());
            hqMenu.setModDt(insertDt);
            hqMenu.setModId(sessionInfoVO.getUserId());

            // 권한 추가 테이블에 있는지 조회 후, 사용중인 권한이 있으면 삭제
            int isAuth = mapper.isAuth(hqMenu);

            if(isAuth > 0) {
                procCnt = mapper.removeAuth(hqMenu);
            }

            /*// 권한 추가 처리
            hqMenu.setIncldExcldFg(IncldExcldFg.INCLUDE);
            procCnt = mapper.addAuth(hqMenu);*/
        }
        return procCnt;
    }

    /** 미사용메뉴 등록 */
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
            }

            // 권한 삭제 처리
            hqMenu.setIncldExcldFg(IncldExcldFg.EXCLUDE);
            procCnt = mapper.addAuth(hqMenu);

        }
        return procCnt;
    }

    /** 환경설정 조회 */
    @Override
    public List<DefaultMap<String>> getConfigList(HqManageVO hqManageVO, SessionInfoVO sessionInfoVO) {
        sessionInfoVO.setHqOfficeCd(hqManageVO.getHqOfficeCd());
        // [1266 환경설정사용등록 사용여부] 환경설정값 조회
        hqManageVO.setEnvst1266(CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1266"), "0"));

        return mapper.getConfigList(hqManageVO);
    }

    /** 환경설정 저장 */
    @Override
    public int saveConfig(HqEnvstVO[] hqEnvsts, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();
        String chkEnvstCd   = "";
        String procResult   = "";

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
                chkEnvstCd = hqEnvst.getEnvstCd();
                if(chkEnvstCd.equals("0041"))
                {
                    procResult = mapper.updateTouchKeyMng(hqEnvst);

                    // 본사의 터치키분류 메뉴 라인수 변경에 따른 터치키 재정렬(바둑판형식)
                    chgHqTouchKeySort(hqEnvst, sessionInfoVO);
                }
            }

            // 적용 타겟이 본사기준 매장까지인 경우, 매장까지 적용
            // 단독매장 제외(프렌차이즈만 해당)
            if(!"00000".equals(hqEnvst.getHqOfficeCd()) && "X".equals(hqEnvst.getTargtFg())) {
                procCnt += mapper.updateConfigStore(hqEnvst);
            }
        }
        return procCnt;
    }

    /** 업체 목록 조회 */
    @Override
    public List<DefaultMap<String>> getAgencyCd(HqManageVO hqManage, SessionInfoVO sessionInfoVO) {

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY){
            hqManage.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            hqManage.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return mapper.getAgencyCd(hqManage);
    }

    /** 본사의 터치키분류 메뉴 라인수 변경에 따른 터치키 재정렬(바둑판형식) */
    public void chgHqTouchKeySort(HqEnvstVO hqEnvstVO, SessionInfoVO sessionInfoVO){

        String dt = currentDateTimeString();
        HqEnvstVO hqEnvstVO2 = new HqEnvstVO();

        hqEnvstVO2.setHqOfficeCd(hqEnvstVO.getHqOfficeCd());
        hqEnvstVO2.setEnvstVal(hqEnvstVO.getEnvstVal());
        hqEnvstVO2.setSessionId(sessionInfoVO.getUserId());
        hqEnvstVO2.setRegDt(dt);
        hqEnvstVO2.setRegId(sessionInfoVO.getUserId());
        hqEnvstVO2.setModDt(dt);
        hqEnvstVO2.setModId(sessionInfoVO.getUserId());

        try{
            // 0. 임시테이블 초기화
            mapper.deleteAllTmpTouchKeyClass(hqEnvstVO2);
            mapper.deleteAllTmpTouchKey(hqEnvstVO2);

            // 1. 임시테이블에 매장 판매터치키 정보 입력
            mapper.insertTmpHqTouchKeyClass(hqEnvstVO2);
            mapper.insertTmpHqTouchKey(hqEnvstVO2);

            // 2. 매장 판매터치키 기존정보 삭제
            mapper.deleteOrgHqTouchKeyClass(hqEnvstVO2);
            mapper.deleteOrgHqTouchKey(hqEnvstVO2);

            // 3. 매장 판매터치키 재정렬 및 저장
            mapper.chgSortHqTouchKeyClass(hqEnvstVO2);
            mapper.chgSortHqTouchKey01(hqEnvstVO2); // 매장 판매터치키 재정렬 (01: 셀 사이즈)
            mapper.chgSortHqTouchKey02(hqEnvstVO2); // 매장 판매터치키 재정렬 (02: 상품명)
            mapper.chgSortHqTouchKey03(hqEnvstVO2); // 매장 판매터치키 재정렬 (03: 가격)

            // 다시 임시테이블 초기화
            mapper.deleteAllTmpTouchKeyClass(hqEnvstVO2);
            mapper.deleteAllTmpTouchKey(hqEnvstVO2);

        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("판매터치키_재정렬_오류 : " + e.getMessage());
        }
    }


}
