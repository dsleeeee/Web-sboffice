package kr.co.solbipos.base.store.storage.popup.service;


import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface StoragePopupService {

    /** 창고관리 - 창고정보 등록 */
    int RegStorageInfo(StoragePopupVO storagePopupVO, SessionInfoVO sessionInfoVO);
    /** 창고관리 - 창고정보 수정 */
    int ModStorageInfo(StoragePopupVO storagePopupVO, SessionInfoVO sessionInfoVO);
    /** 창고관리 - 창고 재고 확인 */
    int StockCnt(StoragePopupVO storagePopupVO, SessionInfoVO sessionInfoVO);
}
