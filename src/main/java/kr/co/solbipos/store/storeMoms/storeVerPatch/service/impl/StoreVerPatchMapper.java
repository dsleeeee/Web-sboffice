package kr.co.solbipos.store.storeMoms.storeVerPatch.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.storeMoms.storeVerPatch.service.StoreVerPatchVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreVerPatchMapper {

    /** 조회 */
    List<DefaultMap<String>> getStoreVerPatchList(StoreVerPatchVO storeVerPatchVO);

    /** 매장 정보 팝업 - 조회 */
    List<DefaultMap<String>> getPatchStoreDtlList(StoreVerPatchVO storeVerPatchVO);
}
