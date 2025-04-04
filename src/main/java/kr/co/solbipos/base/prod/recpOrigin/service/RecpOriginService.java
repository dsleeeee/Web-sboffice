package kr.co.solbipos.base.prod.recpOrigin.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreVO;

import java.util.List;

/**
 * @Class Name : RecpOriginService.java
 * @Description : 기초관리 > 상품관리 > 원산지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2020.07.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface RecpOriginService {

    /** 원산지관리 조회 */
    List<DefaultMap<Object>> getRecpOriginList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 브랜드 콤보박스 리스트 조회 */
    List<DefaultMap<Object>> getBrandComboList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 원산지관리 저장 */
    int getRecpOriginSave(RecpOriginVO[] recpOriginVOs, SessionInfoVO sessionInfoVO);

    /** 재료-상품 조회 */
    List<DefaultMap<Object>> getRecpOriginDetailList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 재료-상품 등록 팝업 - 상품조회 */
    List<DefaultMap<Object>> getRecpProdList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 재료-상품 등록 팝업 - 재료-상품 저장 */
    int getRecpOriginDetailSave(RecpOriginVO[] recpOriginVOs, SessionInfoVO sessionInfoVO);

    /** 상품-원산지관리탭 - 조회 */
    List<DefaultMap<Object>> getProdRecpOriginList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 상품-원산지관리탭 - 재료 및 원산지 등록 조회 */
    List<DefaultMap<Object>> getProdRecpOriginDetailList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 재료 및 원산지 등록 팝업 - 조회 */
    List<DefaultMap<Object>> getProdRecpOriginAddList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 재료 및 원산지 등록 팝업 - 저장 */
    int getProdRecpOriginAddSave(RecpOriginVO[] recpOriginVOs, SessionInfoVO sessionInfoVO);

    /** 상품-원산지관리탭 - 저장 */
    int getProdRecpOriginSave(RecpOriginVO[] recpOriginVOs, SessionInfoVO sessionInfoVO);

    /** 원산지관리-정보입력 조회 */
    List<DefaultMap<Object>> getRecpOriginInfoList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 원산지관리-정보입력 - TEXT 조회 */
    List<DefaultMap<Object>> getRecpOriginInfoDetailList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 원산지관리-정보입력 원산지관리 저장 */
    int getRecpOriginInfoSave(RecpOriginVO[] recpOriginVOs, SessionInfoVO sessionInfoVO);

    /** 원산지관리-정보입력 원산지정보 TEXT 저장 */
    int getRecpOriginInfoDetailSave(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 원산지관리-정보입력 매장적용 팝업 원산지 코드 조회 */
    List<DefaultMap<String>> getSelectOriginCdList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 원산지관리-정보입력 매장적용 팝업 매장리스트 조회 */
    List<DefaultMap<String>> getOriginInfoStoreList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO);

    /** 원산지관리-정보입력 매장적용 팝업 매장적용 */
    int getOriginInfoRegStore(RecpOriginVO[] recpOriginVOs, SessionInfoVO sessionInfoVO);
}