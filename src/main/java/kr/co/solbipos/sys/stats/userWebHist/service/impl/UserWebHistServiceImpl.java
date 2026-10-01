package kr.co.solbipos.sys.stats.userWebHist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.stats.userWebHist.service.UserWebHistService;
import kr.co.solbipos.sys.stats.userWebHist.service.UserWebHistVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : UserWebHistServiceImpl.java
 * @Description : 시스템관리 > 통계 > 사용자웹사용이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.15  김유승      최초생성
 * @ 2026.09.22  김유승      조회정보(조회건수) 팝업 조회 추가
 * @ 2026.09.23  김유승      ③ 메뉴(URL)별 건수 조회 추가
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.15
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("UserWebHistServiceImpl")
@Transactional
public class UserWebHistServiceImpl implements UserWebHistService {
    private final UserWebHistMapper userWebHistMapper;

    /**
     * Constructor Injection
     */
    public UserWebHistServiceImpl(UserWebHistMapper userWebHistMapper) {
        this.userWebHistMapper = userWebHistMapper;
    }

    /** 사용자웹사용이력 조회 */
    @Override
    public List<DefaultMap<Object>> getUserWebHistList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO) {
        return userWebHistMapper.getUserWebHistList(userWebHistVO);
    }

    /** 사용자 아이디 일시정지 */
    @Override
    public int getPauseUserId(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();

        userWebHistVO.setModId(sessionInfoVO.getUserId());
        userWebHistVO.setModDt(currentDt);

        int result = userWebHistMapper.getPauseUserId(userWebHistVO);

        return result;
    }

    /** 사용자 아이디 일시정지 해제 */
    @Override
    public int getResumeUserId(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();

        userWebHistVO.setModId(sessionInfoVO.getUserId());
        userWebHistVO.setModDt(currentDt);

        int result = userWebHistMapper.getResumeUserId(userWebHistVO);

        return result;
    }

    /** [조회정보 팝업] 조회건수 USER_ID 종합 조회 */
    @Override
    public List<DefaultMap<Object>> getUserWebChkCntList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO) {
        setChkCntPeriodDefaults(userWebHistVO);
        return userWebHistMapper.getUserWebChkCntList(userWebHistVO);
    }

    /** [조회정보 팝업] 조회건수 USER_ID + IP별 상세 조회 */
    @Override
    public List<DefaultMap<Object>> getUserWebChkCntIpList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO) {
        setChkCntPeriodDefaults(userWebHistVO);
        return userWebHistMapper.getUserWebChkCntIpList(userWebHistVO);
    }

    /** [조회정보 팝업] ③ 메뉴(URL)별 건수 조회 */
    @Override
    public List<DefaultMap<Object>> getUserWebChkCntMenuList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO) {
        setChkCntPeriodDefaults(userWebHistVO);
        return userWebHistMapper.getUserWebChkCntMenuList(userWebHistVO);
    }

    /**
     * 조회건수 팝업 조회기간 보정
     *   - '-' 제거 후 YYYYMMDD 8자리가 아니면 오늘로 기본 처리 (ETC050_01.JSP 동일 규칙)
     *   - from > to 이면 교환
     */
    private void setChkCntPeriodDefaults(UserWebHistVO userWebHistVO) {
        String today = currentDateTimeString().substring(0, 8);

        String day1 = userWebHistVO.getChkDay1() == null ? "" : userWebHistVO.getChkDay1().replaceAll("-", "").trim();
        String day2 = userWebHistVO.getChkDay2() == null ? "" : userWebHistVO.getChkDay2().replaceAll("-", "").trim();

        if (!day1.matches("[0-9]{8}")) day1 = today;
        if (!day2.matches("[0-9]{8}")) day2 = today;

        if (day1.compareTo(day2) > 0) {
            String tmp = day1;
            day1 = day2;
            day2 = tmp;
        }

        userWebHistVO.setChkDay1(day1);
        userWebHistVO.setChkDay2(day2);
    }

    /** [조회정보 팝업] ② IP별 상세 0건 시 원천 데이터 진단 */
    @Override
    public DefaultMap<Object> getUserWebChkCntIpDiag(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO) {
        setChkCntPeriodDefaults(userWebHistVO);
        return userWebHistMapper.getUserWebChkCntIpDiag(userWebHistVO);
    }

    /** [조회정보 팝업] 일시정지 이력 조회 */
    @Override
    public List<DefaultMap<Object>> getUserPauseHistList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO) {
        setChkCntPeriodDefaults(userWebHistVO);
        return userWebHistMapper.getUserPauseHistList(userWebHistVO);
    }

    /** [조회정보 팝업] ⑤ 웹사용자정보변경이력 조회 */
    @Override
    public List<DefaultMap<Object>> getUserInfoLogList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO) {
        setChkCntPeriodDefaults(userWebHistVO);
        if (userWebHistVO.getUserId() != null) {
            userWebHistVO.setUserId(userWebHistVO.getUserId().trim());
        }
        return userWebHistMapper.getUserInfoLogList(userWebHistVO);
    }

    /** [조회정보 팝업] 일시정지 + 이력 등록 */
    @Override
    public int getPauseUserIdWithHist(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();
        setChkCntPeriodDefaults(userWebHistVO);

        // 1) 처리 전 상태
        String befStatFg = userWebHistMapper.getUserStatFg(userWebHistVO);

        // 2) 기존 일시정지 (USER_STAT_FG '00' → '50')
        userWebHistVO.setModId(sessionInfoVO.getUserId());
        userWebHistVO.setModDt(currentDt);
        int result = userWebHistMapper.getPauseUserId(userWebHistVO);

        // 3) 이력 등록 — 반영 실패(이미 정지 등)여도 남긴다
        insertPauseHist(userWebHistVO, sessionInfoVO, "P", currentDt, befStatFg, "50", result);

        return result;
    }

    /** [조회정보 팝업] 일시정지 해제 + 이력 등록 */
    @Override
    public int getResumeUserIdWithHist(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();
        setChkCntPeriodDefaults(userWebHistVO);

        // 1) 처리 전 상태 — 사용자 없으면 이력 없이 종료
        String befStatFg = userWebHistMapper.getUserStatFg(userWebHistVO);
        if (befStatFg == null) {
            return -2;
        }

        // 2) 기존 일시정지 해제 (USER_STAT_FG '50' → '00')
        userWebHistVO.setModId(sessionInfoVO.getUserId());
        userWebHistVO.setModDt(currentDt);
        int result = userWebHistMapper.getResumeUserId(userWebHistVO);

        // 3) 이력 등록 (PROC_FG='R') — 반영 실패여도 남긴다
        insertPauseHist(userWebHistVO, sessionInfoVO, "R", currentDt, befStatFg, "00", result);

        return result;
    }

    /** [일시정지 이력] 공통 등록 — 성공 시 aftStatFg = 목표상태, 실패 시 처리 전 상태 유지 */
    private void insertPauseHist(UserWebHistVO vo, SessionInfoVO sessionInfoVO, String procFg,
                                 String currentDt, String befStatFg, String targetStatFg, int result) {
        vo.setProcFg(procFg);
        vo.setProcDt(currentDt);
        vo.setProcId(sessionInfoVO.getUserId());
        vo.setBefStatFg(befStatFg);
        vo.setAftStatFg(result > 0 ? targetStatFg : befStatFg);
        vo.setResultFg(result > 0 ? "Y" : "N");
        vo.setRegDt(currentDt);
        vo.setRegId(sessionInfoVO.getUserId());
        userWebHistMapper.insertUserPauseHist(vo);
    }
}