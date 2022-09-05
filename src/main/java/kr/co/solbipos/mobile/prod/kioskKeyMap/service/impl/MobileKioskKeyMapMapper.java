package kr.co.solbipos.mobile.prod.kioskKeyMap.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.prod.kioskKeyMap.service.MobileKioskKeyMapVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileKioskKeyMapMapper.java
 * @Description : 상품관리 > 키오스크키맵
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.03  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.08.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface MobileKioskKeyMapMapper {

    /** 매장키맵 조회 */
    List<DefaultMap<String>> getMobileKioskKeyMapStoreList(MobileKioskKeyMapVO mobileKioskKeyMapVO);
    /** 포장키맵 조회 */
    List<DefaultMap<String>> getMobileKioskKeyMapPackList(MobileKioskKeyMapVO mobileKioskKeyMapVO);

    /** 키맵 저장 */
    int getMobileKioskKeyMapGrpSave(MobileKioskKeyMapVO mobileKioskKeyMapVO);

    /** 중분류 조회 */
    List<DefaultMap<String>> getMobileKioskKeyMapMList(MobileKioskKeyMapVO mobileKioskKeyMapVO);

    /** 중분류 저장 */
    int getMobileKioskKeyMapMGrpSave(MobileKioskKeyMapVO mobileKioskKeyMapVO);

    /** 키맵 상품 조회 */
    List<DefaultMap<String>> getMobileKioskKeyMapProdList(MobileKioskKeyMapVO mobileKioskKeyMapVO);

    /** 키맵 상품 저장 */
    int getMobileKioskKeyMapProdSave(MobileKioskKeyMapVO mobileKioskKeyMapVO);
}
