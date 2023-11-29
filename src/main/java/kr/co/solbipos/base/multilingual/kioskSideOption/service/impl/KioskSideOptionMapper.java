package kr.co.solbipos.base.multilingual.kioskSideOption.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.multilingual.kioskSideOption.service.KioskSideOptionVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : KioskSideOptionMapper.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(키오스크/사이드/옵션)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.11.20  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.11.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface KioskSideOptionMapper {

    /** 키오스크(카테고리명) 탭 리스트 조회 */
    List<DefaultMap<String>> getKioskCategoryList(KioskSideOptionVO kioskSideOptionVO);

    /** 키오스크(카테고리명) 영문, 중문, 일문 저장 */
    int saveKioskCategory(KioskSideOptionVO kioskSideOptionVO);

    /** 사이드(선택그룹명) 탭 리스트 조회 */
    List<DefaultMap<String>> getSideSdselGrpList(KioskSideOptionVO kioskSideOptionVO);

    /** 사이드(선택그룹명) 영문, 중문, 일문 저장 */
    int saveSideSdselGrp(KioskSideOptionVO kioskSideOptionVO);

    /** 사이드(선택분류명) 탭 리스트 조회 */
    List<DefaultMap<String>> getSideSdselClassList(KioskSideOptionVO kioskSideOptionVO);

    /** 사이드(선택분류명) 영문, 중문, 일문 저장 */
    int saveSideSdselClass(KioskSideOptionVO kioskSideOptionVO);

    /** 옵션(그룹명) 탭 리스트 조회 */
    List<DefaultMap<String>> getOptionGrpList(KioskSideOptionVO kioskSideOptionVO);

    /** 옵션(그룹명) 영문, 중문, 일문 저장 */
    int saveOptionGrp(KioskSideOptionVO kioskSideOptionVO);

    /** 옵션(옵션명) 탭 리스트 조회 */
    List<DefaultMap<String>> getOptionValList(KioskSideOptionVO kioskSideOptionVO);

    /** 옵션(옵션명) 영문, 중문, 일문 저장 */
    int saveOptionVal(KioskSideOptionVO kioskSideOptionVO);
}
