package kr.co.solbipos.sys.link.naverPlaceEasyLink.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.link.naverPlaceEasyLink.service.NaverPlaceEasyLinkService;
import kr.co.solbipos.sys.link.naverPlaceEasyLink.service.NaverPlaceEasyLinkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : NaverPlaceEasyLinkServiceImpl.java
 * @Description : 시스템관리 > 연동 > 네이버플레이스 간편연동 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("naverPlaceEasyLinkService")
@Transactional
public class NaverPlaceEasyLinkServiceImpl implements NaverPlaceEasyLinkService {

    private final NaverPlaceEasyLinkMapper naverPlaceEasyLinkMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public NaverPlaceEasyLinkServiceImpl(NaverPlaceEasyLinkMapper naverPlaceEasyLinkMapper) {
        this.naverPlaceEasyLinkMapper = naverPlaceEasyLinkMapper;
    }

    /** 사용자현황 조회 */
    @Override
    public List<DefaultMap<Object>> getNaverPlaceUserList(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO, SessionInfoVO sessionInfoVO) {
        return naverPlaceEasyLinkMapper.getNaverPlaceUserList(naverPlaceEasyLinkVO);
    }

    /** 간편연동 업로드 검증 및 임시데이터 저장 */
    @Override
    public int saveNaverPlaceEasyLinkExcelCheck(NaverPlaceEasyLinkVO[] naverPlaceEasyLinkVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;

        for (NaverPlaceEasyLinkVO naverPlaceEasyLinkVO : naverPlaceEasyLinkVOs) {

            naverPlaceEasyLinkVO.setSessionId(sessionInfoVO.getSessionId());
            naverPlaceEasyLinkVO.setSeq(naverPlaceEasyLinkVO.getProgressCnt() + i); // seq 중복방지를 위해 (진행 갯수 + i)로 계산

            // 필수값 체크
            if (naverPlaceEasyLinkVO.getPlaceId() == null || "".equals(naverPlaceEasyLinkVO.getPlaceId()) || naverPlaceEasyLinkVO.getPlaceId().trim().isEmpty()) {
                naverPlaceEasyLinkVO.setResult("네이버 플레이스ID가 없습니다.");
            }
            /*else if (naverPlaceEasyLinkVO.getBusinessName() == null || "".equals(naverPlaceEasyLinkVO.getBusinessName()) || naverPlaceEasyLinkVO.getBusinessName().trim().isEmpty()) {
                naverPlaceEasyLinkVO.setResult("업체명이 없습니다.");
            }*/
            else if (naverPlaceEasyLinkVO.getBusinessNumber() == null || "".equals(naverPlaceEasyLinkVO.getBusinessNumber()) || naverPlaceEasyLinkVO.getBusinessNumber().trim().isEmpty()) {
                naverPlaceEasyLinkVO.setResult("사업자번호가 없습니다.");
            } else {
                // 사업자번호로 매장 매칭
                DefaultMap<Object> storeMap = naverPlaceEasyLinkMapper.getStoreByBizNo(naverPlaceEasyLinkVO);
                if (storeMap != null && storeMap.get("hqOfficeCd") != null) {
                    naverPlaceEasyLinkVO.setHqOfficeCd(String.valueOf(storeMap.get("hqOfficeCd")));
                    naverPlaceEasyLinkVO.setStoreCd(String.valueOf(storeMap.get("storeCd")));

                    // 기등록 매장여부 조회
                    int naverLinkCnt = naverPlaceEasyLinkMapper.getNaverLinkRegCnt(naverPlaceEasyLinkVO);
                    if (naverLinkCnt > 0) {
                        naverPlaceEasyLinkVO.setResult("기등록 매장");
                    } else {
                        naverPlaceEasyLinkVO.setResult("정상");
                    }
                } else {
                    naverPlaceEasyLinkVO.setResult("대상 매장 없음");
                }
            }

            procCnt = naverPlaceEasyLinkMapper.saveNaverPlaceEasyLinkExcelCheck(naverPlaceEasyLinkVO);
            i++;
        }

        return procCnt;
    }

    /** 간편연동 업로드 검증결과 조회 */
    @Override
    public List<DefaultMap<Object>> getNaverPlaceEasyLinkExcelList(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO, SessionInfoVO sessionInfoVO) {

        naverPlaceEasyLinkVO.setSessionId(sessionInfoVO.getSessionId());
        return naverPlaceEasyLinkMapper.getNaverPlaceEasyLinkExcelList(naverPlaceEasyLinkVO);
    }

    /** 간편연동 업로드 임시데이터 전체 삭제(세션 기준) */
    @Override
    public int deleteNaverPlaceEasyLinkExcelAll(SessionInfoVO sessionInfoVO) {

        NaverPlaceEasyLinkVO naverPlaceEasyLinkVO = new NaverPlaceEasyLinkVO();
        naverPlaceEasyLinkVO.setSessionId(sessionInfoVO.getSessionId());
        return naverPlaceEasyLinkMapper.deleteNaverPlaceEasyLinkExcelAll(naverPlaceEasyLinkVO);
    }

    /** 간편연동 업로드 임시데이터 개별 삭제 */
    @Override
    public int deleteNaverPlaceEasyLinkExcel(NaverPlaceEasyLinkVO[] naverPlaceEasyLinkVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for (NaverPlaceEasyLinkVO naverPlaceEasyLinkVO : naverPlaceEasyLinkVOs) {
            naverPlaceEasyLinkVO.setSessionId(sessionInfoVO.getSessionId());
            procCnt = naverPlaceEasyLinkMapper.deleteNaverPlaceEasyLinkExcel(naverPlaceEasyLinkVO);
        }

        return procCnt;
    }

    /** 간편연동 업로드 정상(검증성공) 데이터 저장 */
    @Override
    public int saveNaverPlaceEasyLinkExcel(NaverPlaceEasyLinkVO[] naverPlaceEasyLinkVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for (NaverPlaceEasyLinkVO naverPlaceEasyLinkVO : naverPlaceEasyLinkVOs) {

            naverPlaceEasyLinkVO.setRegDt(dt);
            naverPlaceEasyLinkVO.setRegId(sessionInfoVO.getUserId());
            naverPlaceEasyLinkVO.setModDt(dt);
            naverPlaceEasyLinkVO.setModId(sessionInfoVO.getUserId());

            // 정상 건만 실제 테이블에 반영
            if (!"정상".equals(naverPlaceEasyLinkVO.getResult())) {
                continue;
            }

            // 업체명이 없으면 매장명으로 대체
            if (naverPlaceEasyLinkVO.getBusinessName() == null || naverPlaceEasyLinkVO.getBusinessName().trim().isEmpty()) {
                naverPlaceEasyLinkVO.setBusinessName(naverPlaceEasyLinkMapper.getStoreNm(naverPlaceEasyLinkVO));
            }

            procCnt += naverPlaceEasyLinkMapper.saveNaverPlaceEasyLinkExcel(naverPlaceEasyLinkVO);
        }

        return procCnt;
    }
}
