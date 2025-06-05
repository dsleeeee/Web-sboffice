package kr.co.solbipos.store.storeMoms.storeVerPatch.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.storeMoms.storeVerPatch.service.StoreVerPatchVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreVerPatchMapper.java
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
@Mapper
@Repository
public interface StoreVerPatchMapper {

    /** 조회 */
    List<DefaultMap<String>> getStoreVerPatchList(StoreVerPatchVO storeVerPatchVO);

    /** 매장 정보 팝업 - 조회 */
    List<DefaultMap<String>> getPatchStoreDtlList(StoreVerPatchVO storeVerPatchVO);
}
