package kr.co.solbipos.store.storeMoms.storeVerPatch.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreVerPatchService.java
 * @Description : 맘스터치 > 매장관리 > 버전패치현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.04  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.04
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface StoreVerPatchService {

    /** 조회 */
    List<DefaultMap<String>> getStoreVerPatchList(StoreVerPatchVO storeVerPatchVO, SessionInfoVO sessionInfoVO);

    /** 매장 정보 팝업 - 조회 */
    List<DefaultMap<String>> getPatchStoreDtlList(StoreVerPatchVO storeVerPatchVO, SessionInfoVO sessionInfoVO);
}
