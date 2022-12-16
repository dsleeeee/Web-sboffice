package kr.co.solbipos.store.storeMoms.storeInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.storeMoms.storeInfo.service.StoreInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreInfoMapper.java
 * @Description : 맘스터치 > 매장관리 > 매장정보조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.14  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2022.12.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface StoreInfoMapper {

    /** 조회 */
    List<DefaultMap<String>> getStoreInfoList(StoreInfoVO storeInfoVO);

}
