package kr.co.solbipos.base.prod.kioskKeyMap.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.prod.kioskOption.service.KioskOptionVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuSelClassVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuSelProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : KioskKeyMapMapper.java
 * @Description : 기초관리 - 상품관리 - 키오스크키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.03  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 09.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface KioskKeyMapMapper {

    /** 키오스크용 포스 조회 */
    List<DefaultMap<String>> getKioskPosList(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 조회 */
    List<DefaultMap<Object>> getKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 생성 시 카테고리 코드 생성 */
    String getKioskCategoryCode(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 생성 */
    int insertKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 수정 */
    int updateKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 삭제 */
    int deleteKioskCategory(KioskKeyMapVO kioskKeyMapVO);
}
