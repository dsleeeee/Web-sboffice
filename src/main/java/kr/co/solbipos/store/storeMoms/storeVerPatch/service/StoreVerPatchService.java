package kr.co.solbipos.store.storeMoms.storeVerPatch.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface StoreVerPatchService {

    /** 조회 */
    List<DefaultMap<String>> getStoreVerPatchList(StoreVerPatchVO storeVerPatchVO, SessionInfoVO sessionInfoVO);

    /** 매장 정보 팝업 - 조회 */
    List<DefaultMap<String>> getPatchStoreDtlList(StoreVerPatchVO storeVerPatchVO, SessionInfoVO sessionInfoVO);
}
