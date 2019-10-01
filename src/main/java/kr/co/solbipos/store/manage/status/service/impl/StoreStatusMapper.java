package kr.co.solbipos.store.manage.status.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.status.service.StoreStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreStatusMapper.java
 * @Description : 기초관리 > 매장정보관리 > 매장현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.09.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreStatusMapper {

    /** 매장탭 - 매장정보조회*/
    List<DefaultMap<Object>> getStatusStoreList(StoreStatusVO storeStatusVO);

    /** 매장탭 - 코너 상세조회*/
    List<DefaultMap<Object>> getStatusStoreCornerList(StoreStatusVO storeStatusVO);

    /** 관리업체탭 - 관리업체 조회*/
    List<DefaultMap<Object>> getStatusAgencyList(StoreStatusVO storeStatusVO);

    /** 관리업체탭 - 관리업체 상세조회*/
    List<DefaultMap<Object>> getStatusAgencyDetailList(StoreStatusVO storeStatusVO);

    /** VAN사탭 - VAN사 조회*/
    List<DefaultMap<Object>> getStatusVanList(StoreStatusVO storeStatusVO);

    /** VAN사탭 - VAN사 상세조회*/
    List<DefaultMap<Object>> getStatusVanDetailList(StoreStatusVO storeStatusVO);

    /** POS설치현황탭 - POS설치현황 조회*/
    List<DefaultMap<Object>> getStatusPosInstallList(StoreStatusVO storeStatusVO);
}
