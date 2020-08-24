package kr.co.solbipos.base.prod.kioskOption.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.kioskOption.service.KioskOptionVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : KioskOptionMapper.java
 * @Description : 기초관리 > 상품관리 > 키오스크옵션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.08.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface KioskOptionMapper {

    /** 상품목록 조회 */
    List<DefaultMap<Object>> getKioskOptionList(KioskOptionVO kioskOptionVO);

    /** 키오스크옵션 조회 */
    List<DefaultMap<Object>> getKioskOptionDetailList(KioskOptionVO kioskOptionVO);

    /** 키오스크옵션 삭제 */
    int getKioskOptionSaveDelete(KioskOptionVO kioskOptionVO);

    /** 키오스크옵션 저장 */
    int getKioskOptionSaveUpdate(KioskOptionVO kioskOptionVO);

    /** 키오스크옵션 상품등록 팝업 - 상품목록 조회 */
    List<DefaultMap<Object>> getKioskOptionProdList(KioskOptionVO kioskOptionVO);

    /** 키오스크옵션 상품등록 팝업 - 표기순번 조회 */
    DefaultMap<String> getKioskOptionProdDispSeq(KioskOptionVO KioskOptionVO);

    /** 키오스크옵션 상품등록 팝업 - 키오스크옵션 저장 */
    int getKioskOptionProdSave(KioskOptionVO KioskOptionVO);
}