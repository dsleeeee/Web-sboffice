package kr.co.solbipos.sys.link.naverPlaceEasyLink.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
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
            else {
                // 사업자번호 우선순위 : place > receipt > connect
                String[] bizNos = {
                    naverPlaceEasyLinkVO.getBusinessNumberPlace(),
                    naverPlaceEasyLinkVO.getBusinessNumberReceipt(),
                    naverPlaceEasyLinkVO.getBusinessNumberConnect()
                };

                DefaultMap<Object> storeMap = null;
                String firstBizNo = null;   // 매칭 실패 시 화면에 표시할 번호(처음으로 값이 있는 번호)

                for (String bizNo : bizNos) {
                    if (bizNo == null || bizNo.trim().isEmpty()) {
                        continue;
                    }
                    if (firstBizNo == null) {
                        firstBizNo = bizNo.trim();
                    }

                    // 사업자번호로 매장 매칭 : 먼저 매칭된 번호를 사용
                    naverPlaceEasyLinkVO.setBusinessNumber(bizNo.trim());
                    storeMap = naverPlaceEasyLinkMapper.getStoreByBizNo(naverPlaceEasyLinkVO);
                    if (storeMap != null && storeMap.get("hqOfficeCd") != null) {
                        break;
                    }
                    storeMap = null;
                }

                if (firstBizNo == null) {
                    naverPlaceEasyLinkVO.setBusinessNumber(null);
                    naverPlaceEasyLinkVO.setResult("사업자번호가 없습니다.");
                } else if (storeMap != null) {
                    // businessNumber 에는 매칭된 사업자번호가 세팅된 상태
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
                    naverPlaceEasyLinkVO.setBusinessNumber(firstBizNo);
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

    /** 수기등록 매장 검색 */
    @Override
    public List<DefaultMap<Object>> getNaverPlaceManualStoreList(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO, SessionInfoVO sessionInfoVO) {

        // 검색조건이 하나도 없으면 전체 매장이 조회되므로 조회하지 않는다.
        if (isEmpty(naverPlaceEasyLinkVO.getBizNo()) && isEmpty(naverPlaceEasyLinkVO.getAddr())
                && isEmpty(naverPlaceEasyLinkVO.getStoreCd()) && isEmpty(naverPlaceEasyLinkVO.getStoreNm())) {
            return new ArrayList<>();
        }

        return naverPlaceEasyLinkMapper.getNaverPlaceManualStoreList(naverPlaceEasyLinkVO);
    }

    /** 수기등록 저장 */
    @Override
    public int saveNaverPlaceManual(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO, SessionInfoVO sessionInfoVO) {

        // 필수값 체크
        if (isEmpty(naverPlaceEasyLinkVO.getPlaceId())) {
            throw new JsonException(Status.FAIL, "네이버 플레이스ID가 없습니다.");
        }
        if (isEmpty(naverPlaceEasyLinkVO.getHqOfficeCd()) || isEmpty(naverPlaceEasyLinkVO.getStoreCd())) {
            throw new JsonException(Status.FAIL, "매장을 선택해주세요.");
        }

        // 선택한 매장이 실제 존재하는지 확인
        if (naverPlaceEasyLinkMapper.getStoreExistCnt(naverPlaceEasyLinkVO) <= 0) {
            throw new JsonException(Status.FAIL, "대상 매장 없음");
        }

        // 기등록 매장은 등록할 수 없음
        if (naverPlaceEasyLinkMapper.getNaverLinkRegCnt(naverPlaceEasyLinkVO) > 0) {
            throw new JsonException(Status.FAIL, "기등록 매장은 등록할 수 없습니다.");
        }

        String dt = currentDateTimeString();
        naverPlaceEasyLinkVO.setPlaceId(naverPlaceEasyLinkVO.getPlaceId().trim());
        naverPlaceEasyLinkVO.setRegDt(dt);
        naverPlaceEasyLinkVO.setRegId(sessionInfoVO.getUserId());
        naverPlaceEasyLinkVO.setModDt(dt);
        naverPlaceEasyLinkVO.setModId(sessionInfoVO.getUserId());

        // 업체명이 없으면 매장명으로 대체
        if (isEmpty(naverPlaceEasyLinkVO.getBusinessName())) {
            naverPlaceEasyLinkVO.setBusinessName(naverPlaceEasyLinkMapper.getStoreNm(naverPlaceEasyLinkVO));
        }

        return naverPlaceEasyLinkMapper.saveNaverPlaceEasyLinkExcel(naverPlaceEasyLinkVO);
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
