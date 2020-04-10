package kr.co.solbipos.base.store.storage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.storage.service.StorageVO;
import kr.co.solbipos.base.store.view.service.CopyStoreEnvVO;
import kr.co.solbipos.base.store.view.service.VanConfigVO;
import kr.co.solbipos.base.store.view.service.ViewVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import kr.co.solbipos.store.manage.storemanage.service.StorePosEnvVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Class Name : StorageMapper.java
* @Description : 기초관리 > 매장관리 > 창고관리
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
@Mapper
@Repository
public interface StorageMapper {

    /** 창고정보 리스트 조회 */
//    List<DefaultMap<String>> getStorageList(StorageVO storageVO);

    /** 본사 창고정보 리스트 조회 */
    List<DefaultMap<String>> getHqStorageList(StorageVO storageVO);
    
    /** 매장 창고정보 리스트 조회 */
    List<DefaultMap<String>> getStoreStorageList(StorageVO storageVO);
}
