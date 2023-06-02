package kr.co.solbipos.base.prod.kioskDisplayResve.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.kioskDisplayResve.service.KioskDisplayResveVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : KioskDisplayResveMapper.java
 * @Description : 기초관리 - 상품관리2 - 비노출관리(예약)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.24  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.05.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface KioskDisplayResveMapper {

    // 예약 내역 조회
    List<DefaultMap<String>> getKioskDisplayResve(KioskDisplayResveVO kioskDisplayResveVO);

    // 상품 내역 조회
    List<DefaultMap<String>> getProdList(KioskDisplayResveVO kioskDisplayResveVO);

    // 비노출관리 예약 전 체크
    int getKioskDisplayResveCnt(KioskDisplayResveVO kioskDisplayResveVO);
    
    // 비노출관리 예약
    int saveKioskDisplayResve(KioskDisplayResveVO kioskDisplayResveVO);

    // 예약 삭제
    int deleteKioskDisplayResve(KioskDisplayResveVO kioskDisplayResveVO);

}