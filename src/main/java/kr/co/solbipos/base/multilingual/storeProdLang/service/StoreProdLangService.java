package kr.co.solbipos.base.multilingual.storeProdLang.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreProdLangService.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(상품)(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.25  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.09.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface StoreProdLangService {

    /** 상품명 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreProdNmList(StoreProdLangVO storeProdLangVO, SessionInfoVO sessionInfoVO);

    /** 상품명 영문, 중문, 일문 저장(매장) */
    int saveStoreProdNm(StoreProdLangVO[] storeProdLangVOs, SessionInfoVO sessionInfoVO);

    /** 상품설명 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreProdInfoList(StoreProdLangVO storeProdLangVO, SessionInfoVO sessionInfoVO);

    /** 상품설명 영문, 중문, 일문 저장(매장) */
    int saveStoreProdInfo(StoreProdLangVO[] storeProdLangVOs, SessionInfoVO sessionInfoVO);
}
