package kr.co.solbipos.sys.link.naverPlaceEasyLink.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.link.naverPlaceEasyLink.service.NaverPlaceEasyLinkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : NaverPlaceEasyLinkMapper.java
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
@Mapper
@Repository
public interface NaverPlaceEasyLinkMapper {

    /** 사용자현황 조회 */
    List<DefaultMap<Object>> getNaverPlaceUserList(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO);

    /** 간편연동 업로드 검증 및 임시데이터 저장 */
    int saveNaverPlaceEasyLinkExcelCheck(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO);

    /** 간편연동 업로드 검증결과 조회 */
    List<DefaultMap<Object>> getNaverPlaceEasyLinkExcelList(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO);

    /** 간편연동 업로드 임시데이터 전체 삭제(세션 기준) */
    int deleteNaverPlaceEasyLinkExcelAll(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO);

    /** 간편연동 업로드 임시데이터 개별 삭제 */
    int deleteNaverPlaceEasyLinkExcel(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO);

    /** 사업자번호로 매장 조회(검증용) */
    DefaultMap<Object> getStoreByBizNo(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO);

    /** 기등록 매장여부 조회 */
    int getNaverLinkRegCnt(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO);

    /** 매장코드로 매장명 조회 */
    String getStoreNm(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO);

    /** 간편연동 업로드 검증성공 데이터 저장 */
    int saveNaverPlaceEasyLinkExcel(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO);
}
