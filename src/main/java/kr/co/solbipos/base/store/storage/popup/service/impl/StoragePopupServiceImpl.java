package kr.co.solbipos.base.store.storage.popup.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.common.data.enums.Status;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.storage.popup.service.StoragePopupService;
import kr.co.solbipos.base.store.storage.popup.service.StoragePopupVO;

@Service("StoragePopupService")
@Transactional
public class StoragePopupServiceImpl implements StoragePopupService {
    private final StoragePopupMapper storagePopupMapper;
    private final MessageService messageService;

    @Autowired
    public StoragePopupServiceImpl(StoragePopupMapper storagePopupMapper, MessageService messageService) {
    	this.storagePopupMapper = storagePopupMapper;
        this.messageService = messageService;
    }


	/** 창고관리 팝업 - 창고관리 팝업 리스트 조회 */
	@Override
	public int RegStorageInfo(StoragePopupVO storagePopupVO, SessionInfoVO sessionInfoVO) {
		storagePopupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		storagePopupVO.setStoreCd(sessionInfoVO.getStoreCd());
		storagePopupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		
		//창고 코드
		int storageCdCnt = storagePopupMapper.StorageCdCnt(storagePopupVO);
		
        if(storageCdCnt >= 999) throw new JsonException(Status.SERVER_ERROR, messageService.get("storageManage.overStorage"));
		
		String cdCnt = String.format("%03d", storageCdCnt);
		storagePopupVO.setStorageCd(cdCnt);
		
		String dt = currentDateTimeString();
		String userId = sessionInfoVO.getUserId();
		storagePopupVO.setRegDt(dt);
		storagePopupVO.setRegId(userId);
		storagePopupVO.setModDt(dt);
		storagePopupVO.setModId(userId);
		
		int result = 0;

		if(storagePopupVO.getOrgnFg() == "S") {
			result = storagePopupMapper.RegStoreStorageInfo(storagePopupVO);
		}else {
			result = storagePopupMapper.RegHqStorageInfo(storagePopupVO);
		}
		
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        
		return result;
	}
	
	/** 창고관리 팝업 - 창고관리 팝업 리스트 조회 */
	@Override
	public int ModStorageInfo(StoragePopupVO storagePopupVO, SessionInfoVO sessionInfoVO) {
		storagePopupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		storagePopupVO.setStoreCd(sessionInfoVO.getStoreCd());
		storagePopupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		
		String dt = currentDateTimeString();
		String userId = sessionInfoVO.getUserId();
		storagePopupVO.setModDt(dt);
		storagePopupVO.setModId(userId);
		
		int result = 0;

		if(storagePopupVO.getOrgnFg() == "S") {
			result = storagePopupMapper.ModStoreStorageInfo(storagePopupVO);
		}else {
			result = storagePopupMapper.ModHqStorageInfo(storagePopupVO);
		}
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        
		return result;
		
	}
	
	/** 창고관리 팝업 - 창고관리 팝업 리스트 조회 */
	@Override
	public int StockCnt(StoragePopupVO storagePopupVO, SessionInfoVO sessionInfoVO) {

		storagePopupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		storagePopupVO.setStoreCd(sessionInfoVO.getStoreCd());
		storagePopupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

		int stock = storagePopupMapper.StockCnt(storagePopupVO);
		
		return stock;
		
	}
}
