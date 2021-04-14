package kr.co.common.service.cmm.impl;

import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.redis.RedisConnService;
import kr.co.common.system.BaseEnv;
import kr.co.common.template.RedisCustomTemplate;
import kr.co.solbipos.application.common.service.*;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentTimeMsString;

/**
 * @Class Name : CmmMenuServiceImpl.java
 * @Description : 공통메뉴관련
 * @Modification Information
 * @
 * @ 수정일       수정자      수정내용
 * @ ----------  ---------  -------------------------------
 * @ 2018.10.23  노현수      주석추가, 로직정리...
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("cmmMenuService")
public class CmmMenuServiceImpl implements CmmMenuService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RedisConnService redisConnService;
    private final CmmMenuMapper cmmMenuMapper;
    private final RedisCustomTemplate<String, SessionInfoVO> redisCustomTemplate;

    /** Constructor Injection */
    @Autowired
    public CmmMenuServiceImpl(RedisConnService redisConnService, CmmMenuMapper cmmMenuMapper,
        RedisCustomTemplate<String, SessionInfoVO> redisCustomTemplate) {
        this.redisConnService = redisConnService;
        this.cmmMenuMapper = cmmMenuMapper;
        this.redisCustomTemplate = redisCustomTemplate;
    }

    /** 본사에 속한 가맹점 코드 목록을 조회 */
    @Override
    public List<String> getStoreCdList(String hqOfficeCd) {
        return cmmMenuMapper.getStoreCdList(hqOfficeCd);
    }

    /** 레이어 팝업 매장 조회 */
    @Override
    public List<StoreVO> getStoreInfo(StoreVO storeVO) {
        return cmmMenuMapper.getStoreInfo(storeVO);
    }

    /** 레이어 팝업 본사 조회 */
    @Override
    public List<HqVO> getHqInfo(HqVO hqVO) {
        return cmmMenuMapper.getHqInfo(hqVO);
    }
    
    /** 레이어 팝업 업체 조회 */
    public List<CmAgencyVO> getCmAgencyInfo(CmAgencyVO cmAgencyVO) {
    	return cmmMenuMapper.getCmAgencyInfo(cmAgencyVO);
    }

    /** 리소스 정보로 메뉴 사용 내역 저장 */
    @Override
    public int insertMenuUseHist(MenuUseHistVO menuUseHistVO) {
        return cmmMenuMapper.insertMenuUseHist(menuUseHistVO);
    }

    /** 메뉴목록 조회 : 사용자의 권한있는 메뉴 목록 */
    @Override
    public List<ResrceInfoBaseVO> getUserMenuList(SessionInfoVO sessionInfoVO) {
        return cmmMenuMapper.getUserMenuList(sessionInfoVO);
    }

    /** 즐겨찾기 메뉴 조회 */
    @Override
    public List<ResrceInfoBaseVO> getBkmkMenuList(SessionInfoVO sessionInfoVO) {
        return cmmMenuMapper.getBkmkMenuList(sessionInfoVO);
    }

    /** 고정 메뉴 조회 */
    @Override
    public List<ResrceInfoBaseVO> getFixedMenuList(SessionInfoVO sessionInfoVO) {
        return cmmMenuMapper.getFixedMenuList(sessionInfoVO);
    }

    /** 메뉴 사용 내역 저장 */
    @Override
    public int insertMenuUseHist(ResrceInfoBaseVO resrceInfoBaseVO, SessionInfoVO sessionInfoVO) {
        MenuUseHistVO menuUseHistVO = new MenuUseHistVO();
        menuUseHistVO.setUserId(sessionInfoVO.getUserId());
        menuUseHistVO.setResrceCd(resrceInfoBaseVO.getResrceCd());
        menuUseHistVO.setUseDate(currentDateString());
        menuUseHistVO.setUseDt(currentTimeMsString());

        return cmmMenuMapper.insertMenuUseHist(menuUseHistVO);
    }

    /** 세션에 메뉴 히스토리 추가 */
    @Override
    public void addHistMenu(ResrceInfoVO resrceInfoVO, SessionInfoVO sessionInfoVO) {
        // 추가 할 리소스 생성 및 세팅
        ResrceInfoBaseVO resrceInfoBaseVO = new ResrceInfoBaseVO();
        resrceInfoBaseVO.setResrceCd(resrceInfoVO.getResrceCd());
        resrceInfoBaseVO.setpResrce(resrceInfoVO.getpResrce());
        resrceInfoBaseVO.setResrceNm(resrceInfoVO.getResrceNm());
        resrceInfoBaseVO.setUrl(resrceInfoVO.getUrl());
        addHistMenu(resrceInfoBaseVO, sessionInfoVO);
    }

    /** 세션에 사용 메뉴 추가 : interceptor 에서 호출 */
    @Override
    public SessionInfoVO addHistMenu(ResrceInfoBaseVO resrceInfoBaseVO,
            SessionInfoVO sessionInfoVO) {

        // 추가 할려는 리소스
        String resrceCd = resrceInfoBaseVO.getResrceCd();
        // 히스토리 메뉴
        List<ResrceInfoBaseVO> historyMenuData = sessionInfoVO.getHistoryMenuData();
        // 히스토리메뉴 초기화
        if ( historyMenuData == null ) {
            historyMenuData = new ArrayList<>();
        }
        // 고정 메뉴
        List<ResrceInfoBaseVO> fixedMenuData = sessionInfoVO.getFixedMenuData();
        // 고정 메뉴에 선택한 메뉴가 있는지 체크 > 추가하지 않고 고정메뉴 활성화
        if (fixedMenuData.size() > 0) {
            for(ResrceInfoBaseVO fixedMenuVO : fixedMenuData) {
                // 같은게 있으면 히스토리에 추가하지 않고 해당 고정 메뉴 활성화 하고 세션 저장
                if ( fixedMenuVO.getResrceCd().equals(resrceCd) ) {
                    sessionInfoVO.setFixedMenuData(fixedMenuData);
                    return checkActivation(resrceCd, sessionInfoVO);
                }
            }
        }

        // 기존 히스토리 메뉴 리스트에 있는지 체크 > 지우고 젤 뒤에 추가
        int hSize = historyMenuData.size();
        if (hSize == 0) {
            historyMenuData.add(resrceInfoBaseVO);
            sessionInfoVO.setHistoryMenuData(historyMenuData);
            return checkActivation(resrceCd, sessionInfoVO);
        } else {
            for (int i = 0; i < hSize; i++) {
                ResrceInfoBaseVO historyMenuVO = historyMenuData.get(i);
                // 히스토리 메뉴에 같은게 있으면 지우고 젤 뒤에 추가
                if (historyMenuVO.getResrceCd().equals(resrceCd)) {
                    historyMenuData.remove(i);
                    historyMenuData.add(resrceInfoBaseVO);
                    sessionInfoVO.setHistoryMenuData(historyMenuData);
                    return checkActivation(resrceCd, sessionInfoVO);
                }
                // 마지막 까지 같은게 없으면
                if (i == hSize - 1) {
                    // 히스토리 리소스 갯수가 세팅된 갯수를 넘어가면 지우고 추가함
                    if (hSize > BaseEnv.SESSION_HIST_MENU_SIZE) {
                        // 첫번째를 지운다.
                        historyMenuData.remove(0);
                        historyMenuData.add(resrceInfoBaseVO);
                    // 세팅된 갯수를 넘어가지 않으면 바로 추가
                    } else {
                        historyMenuData.add(resrceInfoBaseVO);
                    }
                    // 히스토리메뉴 세션에 다시 Set
                    sessionInfoVO.setHistoryMenuData(historyMenuData);
                    return checkActivation(resrceCd, sessionInfoVO);
                }
            }
        }
        return sessionInfoVO;
    }

    /** 세션 고정메뉴, 히스토리 메뉴 의 활성화 여부 하나만 체크 */
    @Override
    public SessionInfoVO checkActivation(String resrceCd, SessionInfoVO sessionInfoVO) {

        // 고정 메뉴
        List<ResrceInfoBaseVO> fixedMenuData = sessionInfoVO.getFixedMenuData();
        if ( fixedMenuData != null && fixedMenuData.size() > 0) {
            for (ResrceInfoBaseVO resrceInfoBaseVO : fixedMenuData) {
                if (resrceInfoBaseVO.getResrceCd().equals(resrceCd)) {
                    resrceInfoBaseVO.setActivation(true);
                    // 현재 메뉴 정보 등록
                    sessionInfoVO.setCurrentMenu(resrceInfoBaseVO);
                } else {
                    resrceInfoBaseVO.setActivation(false);
                }
            }
        }
        // 히스토리 메뉴
        List<ResrceInfoBaseVO> historyMenuData = sessionInfoVO.getHistoryMenuData();
        if ( historyMenuData != null && historyMenuData.size() > 0) {
            for (ResrceInfoBaseVO resrceInfoBaseVO : historyMenuData) {
                if (resrceInfoBaseVO.getResrceCd().equals(resrceCd)) {
                    resrceInfoBaseVO.setActivation(true);
                    // 현재 메뉴 정보 등록
                    sessionInfoVO.setCurrentMenu(resrceInfoBaseVO);
                } else {
                    resrceInfoBaseVO.setActivation(false);
                }
            }
        }

        sessionInfoVO.setFixedMenuData(fixedMenuData);
        sessionInfoVO.setHistoryMenuData(historyMenuData);

        // 레디스에 수정한 세션정보를 저장
        if ( redisConnService.isAvailable() ) {
            try {
                redisCustomTemplate.set( redisCustomTemplate.makeKey(sessionInfoVO.getSessionId()), sessionInfoVO,
                    BaseEnv.SESSION_TIMEOUT_MIN, TimeUnit.MINUTES );
            } catch ( Exception e ) {
                LOGGER.error( "Redis server not available!! setSessionInfo {}", e );
                redisConnService.disable();
            }
        }

        return sessionInfoVO;
    }

    /** 히스토리 메뉴 내역을 세션에서 삭제 */
    @Override
    public void deleteHistMenu(String resrceCd, SessionInfoVO sessionInfoVO) {
        // 히스토리 메뉴
        List<ResrceInfoBaseVO> historyMenuData = sessionInfoVO.getHistoryMenuData();
        if ( historyMenuData != null ) {
            int size = historyMenuData.size();
            for (int i = 0; i < size; i++) {
                ResrceInfoBaseVO r = historyMenuData.get(i);
                if (r.getResrceCd().equals(resrceCd)) {
                    historyMenuData.remove(i);
                    break;
                }
            }
            sessionInfoVO.setHistoryMenuData(historyMenuData);
            checkActivation(resrceCd, sessionInfoVO);
        }
    }

    /** 고정 메뉴를 세션에서 삭제. 디비에서 삭제 하는건 아님 */
    @Override
    public void deleteFixMenu(String resrceCd, SessionInfoVO sessionInfoVO) {
        List<ResrceInfoBaseVO> fixedMenuData = sessionInfoVO.getFixedMenuData();
        if ( fixedMenuData != null ) {
            int size = fixedMenuData.size();
            for (int i = 0; i < size; i++) {
                ResrceInfoBaseVO r = fixedMenuData.get(i);
                if (r.getResrceCd().equals(resrceCd)) {
                    fixedMenuData.remove(i);
                    break;
                }
            }
            sessionInfoVO.setFixedMenuData(fixedMenuData);
            checkActivation(resrceCd, sessionInfoVO);
        }
    }

    /** 유효 메뉴 여부 확인 */
    @Override
    public int menuResrceChk(ResrceInfoVO resrceInfoVO) {
        return cmmMenuMapper.menuResrceChk(resrceInfoVO);
    }

}


