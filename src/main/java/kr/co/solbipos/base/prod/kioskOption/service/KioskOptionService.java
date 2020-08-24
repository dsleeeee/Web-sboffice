package kr.co.solbipos.base.prod.kioskOption.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : KioskOptionService.java
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
public interface KioskOptionService {

    /** 상품목록 조회 */
    List<DefaultMap<Object>> getKioskOptionList(KioskOptionVO kioskOptionVO, SessionInfoVO sessionInfoVO);

    /** 키오스크옵션 조회 */
    List<DefaultMap<Object>> getKioskOptionDetailList(KioskOptionVO kioskOptionVO, SessionInfoVO sessionInfoVO);

    /** 키오스크옵션 삭제 */
    int getKioskOptionSaveDelete(KioskOptionVO[] kioskOptionVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크옵션 저장 */
    int getKioskOptionSaveUpdate(KioskOptionVO[] kioskOptionVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크옵션 상품등록 팝업 - 상품목록 조회 */
    List<DefaultMap<Object>> getKioskOptionProdList(KioskOptionVO kioskOptionVO, SessionInfoVO sessionInfoVO);

    /** 키오스크옵션 상품등록 팝업 - 표기순번 조회 */
    DefaultMap<String> getKioskOptionProdDispSeq(KioskOptionVO kioskOptionVO, SessionInfoVO sessionInfoVO);

    /** 키오스크옵션 상품등록 팝업 - 키오스크옵션 저장 */
    int getKioskOptionProdSave(KioskOptionVO[] kioskOptionVOs, SessionInfoVO sessionInfoVO);
}