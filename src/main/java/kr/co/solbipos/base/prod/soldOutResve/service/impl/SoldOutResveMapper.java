package kr.co.solbipos.base.prod.soldOutResve.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.soldOutResve.service.SoldOutResveVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SoldOutResveMapper.java
 * @Description : 기초관리 - 상품관리2 - 품절관리(예약)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.30  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.05.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SoldOutResveMapper {

    // 예약 내역 조회
    List<DefaultMap<String>> getSoldOutResve(SoldOutResveVO soldOutResveVO);

    // 상품 내역 조회
    List<DefaultMap<String>> getProdList(SoldOutResveVO soldOutResveVO);

    // 품절관리 예약 전 체크
    int getSoldOutResveCnt(SoldOutResveVO soldOutResveVO);
    
    // 품절관리 예약
    int saveSoldOutResve(SoldOutResveVO soldOutResveVO);

    // 예약 삭제
    int deleteSoldOutResve(SoldOutResveVO soldOutResveVO);

    // 사이드 예약 내역 조회
    List<DefaultMap<String>> getSdselSoldOutResve(SoldOutResveVO soldOutResveVO);

    // 사이드 상품 내역 조회
    List<DefaultMap<String>> getSdselProdList(SoldOutResveVO soldOutResveVO);

    // 사이드 품절관리 예약 전 체크
    int getSdselSoldOutResveCnt(SoldOutResveVO soldOutResveVO);

    // 사이드 품절관리 예약
    int saveSdselSoldOutResve(SoldOutResveVO soldOutResveVO);

    // 사이드 예약 삭제
    int deleteSdselSoldOutResve(SoldOutResveVO soldOutResveVO);
}