package kr.co.common.service.popup.impl;

import kr.co.common.data.domain.AgencyVO;
import kr.co.common.data.domain.HqOfficeVO;
import kr.co.common.data.domain.VanVO;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PopupMapper.java
 * @Description : 공통 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.24  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.24
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PopupMapper {

    /** 벤사장 목록 조회 */
    List<DefaultMap<String>> getVanList(VanVO vanVO);

    /** 대리점 목록 조회 */
    List<DefaultMap<String>> getAgencyList(AgencyVO agencyVO);

    /** 본사 목록 조회 */
    List<DefaultMap<String>> getHqList(HqOfficeVO hqOfficeVO);

    /** 매장 목록 조회 */
    List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO);

    /** 상품분류 트리 조회 */
    List<ProductClassVO> getProdClassTree(ProdVO prodVO);

    /** 상품분류 플랫 조회 */
    String getProdClassCdNm(ProdVO prodVO);

    /** 상품 조회 */
    List<DefaultMap<String>> getProductList(ProdVO prodVO);
}

