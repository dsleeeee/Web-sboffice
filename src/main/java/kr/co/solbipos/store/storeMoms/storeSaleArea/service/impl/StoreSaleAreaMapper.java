package kr.co.solbipos.store.storeMoms.storeSaleArea.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.storeMoms.storeSaleArea.service.StoreSaleAreaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreSaleAreaMapper.java
 * @Description : 맘스터치 > 점포관리 > 점포 영업 지역 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.11.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.11.21
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface StoreSaleAreaMapper {

    /** 매장목록 조회 */
    List<DefaultMap<String>> getStoreList(StoreSaleAreaVO storeSaleAreaVO);

    /** 지사 조회(콤보박스용) */
    List<DefaultMap<String>> getBranchCombo(StoreSaleAreaVO storeSaleAreaVO);

    /** 매장 조회(콤보박스용) */
    List<DefaultMap<String>> getStoreCombo(StoreSaleAreaVO storeSaleAreaVO);

    /** 매장 영업지역 조회 */
    DefaultMap<String> getStoreSaleArea(StoreSaleAreaVO storeSaleAreaVO);

    /** 매장 영업지역 저장 */
    int saveStoreSaleArea(StoreSaleAreaVO storeSaleAreaVO);

    /** 서울, 경기 매장 영업지역 조회 */
    List<DefaultMap<String>> getMetropolitanSaleArea (StoreSaleAreaVO storeSaleAreaVO);
}
