package kr.co.solbipos.base.prod.tableOrderKeyMap.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.prod.tableOrderKeyMap.service.TableOrderKeyMapVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TableOrderKeyMapMapper.java
 * @Description : 기초관리 > 상품관리2 > 테이블오더키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.24  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.07.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TableOrderKeyMapMapper {

    /** 테이블오더키맵 매장적용 팝업 - 조회 */
    List<DefaultMap<Object>> getTableOrderKeyMapStoreRegistList(TableOrderKeyMapVO tableOrderKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 생성 시 카테고리 코드 생성 */
    String getKioskCategoryCode(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 생성 */
    int insertKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 수정 */
    int updateKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 삭제 */
    int deleteKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치) */
    String updateKioskClsMomsLsm(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 수정 */
    int updateKioskKeyMap(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 삭제 */
    int deleteKioskKeyMap(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 관련 코드 조회 */
    DefaultMap<String> getKioskKeyMapCode(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 등록 */
    int saveKioskKeyMap(KioskKeyMapVO kioskKeyMapVO);

    /** 해당 카테고리(분류)에 해당하는 상품도 삭제 */
    int deleteAllKioskKeyMap(KioskKeyMapVO kioskKeyMapVO);
}