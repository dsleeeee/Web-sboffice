package kr.co.solbipos.base.store.storage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.storage.service.StorageService;
import kr.co.solbipos.base.store.storage.service.StorageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* @Class Name : StorageServiceImpl.java
* @Description : 기초관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
 * @ 2018.08.14  김영근      최초생성
 * @ 2018.11.20  김지은      angular 방식으로 변경
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.14
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("storageService")
public class StorageServiceImpl implements StorageService {

    private final StorageMapper storageMapper;
    private final MessageService messageService;


    /** Constructor Injection */
    @Autowired
    public StorageServiceImpl(StorageMapper storageMapper, MessageService messageService) {
        this.storageMapper = storageMapper;
        this.messageService = messageService;
    }

    /** 창고정보 리스트조회 */
    @Override
    public List<DefaultMap<String>> getStorageList(StorageVO storageVO, SessionInfoVO sessionInfoVO){

    	storageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	storageVO.setStoreCd(sessionInfoVO.getStoreCd());
    	storageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	List<DefaultMap<String>> list = null;
    	if(storageVO.getOrgnFg() == "H") { // 본사일때
    		list = storageMapper.getHqStorageList(storageVO);
    	}else if(storageVO.getOrgnFg() == "S") { // 매장일때
    		list = storageMapper.getStoreStorageList(storageVO);
    	}
        return list;
    }
}
