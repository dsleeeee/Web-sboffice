package kr.co.solbipos.base.store.storage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.storemanage.service.StorePosEnvVO;

import java.util.List;
import java.util.Map;

/**
* @Class Name : StorageService.java
* @Description : 
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2020.03.24  조동훤      최초생성
*
* @author 조동훤
* @since 2020.03.24
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface StorageService {

    /** 창고정보 리스트조회 */
    List<DefaultMap<String>> getStorageList(StorageVO storageVO, SessionInfoVO sessionInfoVO);
}
