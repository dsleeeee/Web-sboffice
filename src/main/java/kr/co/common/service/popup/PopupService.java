package kr.co.common.service.popup;

import kr.co.common.data.domain.AgencyVO;
import kr.co.common.data.domain.HqOfficeVO;
import kr.co.common.data.domain.VanVO;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;

import java.util.List;

/**
 * @Class Name : PopupService.java
 * @Description : 공통 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018. 10.24  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PopupService {

    /** 벤사 목록 조회 */
    List<DefaultMap<String>> getVanList(VanVO vanVO);

    /** 대리점 목록 조회 */
    List<DefaultMap<String>> getAgencyList(AgencyVO agencyVO, SessionInfoVO sessionInfoVO);

    /** 본사 목록 조회 */
    List<DefaultMap<String>> getHqList(HqOfficeVO hqOfficeVO, SessionInfoVO sessionInfoVO);

    /** 매장 목록 조회 */
    List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 매장 목록 조회(가맹점 로직 추가) */
    List<DefaultMap<String>> getSearchStoreList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO);

    /** 상품분류 트리 조회 */
    List<ProductClassVO> getProdClassTree(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 상품분류 트리 조회3 */
    List<ProductClassVO> getProdClassTree3(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 상품분류 플랫 조회 */
    String getProdClassCdNm(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 상품 목록 조회 */
    List<DefaultMap<String>> getProductList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 본사 + 매장 목록 조회 */
    List<DefaultMap<String>> getHqStoreList(StoreManageVO storeManageVO , SessionInfoVO sessionInfoVO);
}
