package kr.co.common.service.popup.impl;

import kr.co.common.data.domain.AgencyVO;
import kr.co.common.data.domain.HqOfficeVO;
import kr.co.common.data.domain.VanVO;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.PopupService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class Name : PopupServiceImpl.java
 * @Description : 공통 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018. 10.24  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.24
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service
public class PopupServiceImpl implements PopupService{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PopupMapper popupMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public PopupServiceImpl(PopupMapper popupMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.popupMapper = popupMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 벤사 목록 조회 */
    @Override
    public List<DefaultMap<String>> getVanList(VanVO vanVO) {
        return popupMapper.getVanList(vanVO);
    }

    /** 대리점 목록 조회 */
    @Override
    public List<DefaultMap<String>> getAgencyList(AgencyVO agencyVO, SessionInfoVO sessionInfoVO) {

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY){
            agencyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            agencyVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return popupMapper.getAgencyList(agencyVO);
    }

    /** 본사 목록 조회 */
    @Override
    public List<DefaultMap<String>> getHqList(HqOfficeVO hqOfficeVO, SessionInfoVO sessionInfoVO) {

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY){
            hqOfficeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            hqOfficeVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return popupMapper.getHqList(hqOfficeVO);
    }

    /** 매장 목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {
        storeManageVO.setEmpNo(sessionInfoVO.getEmpNo());

        // 본사인 경우, 브랜드 체크
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (storeManageVO.getStoreHqBrandCd() == "" || storeManageVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeManageVO.getUserBrands() != null && !"".equals(storeManageVO.getUserBrands())) {
                    String[] userBrandList = storeManageVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeManageVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return popupMapper.getStoreList(storeManageVO);
    }

    /** 매장 목록 조회(가맹점 로직 추가) */
    @Override
    public List<DefaultMap<String>> getSearchStoreList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY){
            storeManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            storeManageVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return popupMapper.getSearchStoreList(storeManageVO);
    }

    /** 상품분류 트리 조회 */
    @Override
    public List<ProductClassVO> getProdClassTree(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        if(prodVO.getPageFg() != null && prodVO.getPageFg().equals("1")) {
            prodVO.setOrgnFg("S");
        }else{
            prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return popupMapper.getProdClassTree(prodVO);
    }

    /** 상품분류 트리 조회 */
    @Override
    public List<ProductClassVO> getProdClassTree3(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(prodVO.getPageFg() != null && prodVO.getPageFg().equals("1")) {
            prodVO.setOrgnFg("S");
        }else{
            prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = null;

        if (sessionInfoVO.getHqOfficeCd().equals("A0001") || sessionInfoVO.getHqOfficeCd().equals("A0007")) {
            // (A0001, A0007 본사/하위매장은 기존 상품분류 테이블 사용)
            list = popupMapper.getProdClassTreeArtisee(prodVO);
        } else {
            list = popupMapper.getProdClassTree3(prodVO);
        }

        return makeTreeData(list);
    }

    /** 상품분류 플랫 조회 */
    @Override
    public String getProdClassCdNm(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        if(prodVO.getPageFg() != null && prodVO.getPageFg().equals("1")) {
            prodVO.setOrgnFg("S");
        }else{
            prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return popupMapper.getProdClassCdNm(prodVO);
    }

    /** 상품 조회 */
    @Override
    public List<DefaultMap<String>> getProductList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        if(StringUtil.isEmpty(storeCd)) prodVO.setOrgnFg(OrgnFg.HQ.getCode());
        else                            prodVO.setOrgnFg(OrgnFg.STORE.getCode());

        // 소속구분 설정
        if(prodVO.getPageFg() != null && prodVO.getPageFg().equals("1")) {
            prodVO.setOrgnFg("S");
            storeCd = prodVO.getStoreCd();
        }

        prodVO.setHqOfficeCd(hqOfficeCd);
        prodVO.setStoreCd(storeCd);

        prodVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (prodVO.getProdHqBrandCd() == "" || prodVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (prodVO.getUserProdBrands() != null && !"".equals(prodVO.getUserProdBrands())) {
                    String[] userBrandList = prodVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        prodVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return popupMapper.getProductList(prodVO);
    }

    /** 본사 + 매장목록 조회 */
    @Override
    public List<DefaultMap<String>> getHqStoreList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        storeManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeManageVO.setStoreCd(sessionInfoVO.getStoreCd());

        return popupMapper.getHqStoreList(storeManageVO);
    }

    /** 트리 데이터 생성 */
    public List<ProductClassVO> makeTreeData(List<DefaultMap<String>> lists) {

        List<ProductClassVO> prodClsVOs = new ArrayList<ProductClassVO>();

        for(DefaultMap<String> list : lists){
            ProductClassVO prodClsVO = new ProductClassVO();
            prodClsVO.setProdClassCd(list.getStr("prodClassCd"));
            prodClsVO.setpProdClassCd(list.getStr("pProdClassCd"));
            prodClsVO.setProdClassNm(list.getStr("prodClassNm"));
            prodClsVO.setItems(new ArrayList<ProductClassVO>());
            prodClsVOs.add(prodClsVO);
        }

        Map<String, ProductClassVO> hm = new LinkedHashMap<String, ProductClassVO>();

        ProductClassVO child;
        ProductClassVO parent;

        for(ProductClassVO prodClsVO : prodClsVOs){
            if(!hm.containsKey(prodClsVO.getProdClassCd())) {
                hm.put(prodClsVO.getProdClassCd(), prodClsVO);
            }

            child = hm.get(prodClsVO.getProdClassCd());

            if (child != null && !"".equals(prodClsVO.getpProdClassCd()) && !prodClsVO.getpProdClassCd().equals("00000")) {
                if (hm.containsKey(prodClsVO.getpProdClassCd())) {
                    parent = hm.get(prodClsVO.getpProdClassCd());
                    parent.getItems().add(child);
                    child.setParent(parent);
                }
            }
        }

        List<ProductClassVO> returnData = new ArrayList<ProductClassVO>();
        for (ProductClassVO prodClsVO : hm.values()) {
            if (prodClsVO.getpProdClassCd() == null || "".equals(prodClsVO.getpProdClassCd()) || prodClsVO.getpProdClassCd().equals("00000")) {
                returnData.add(prodClsVO);
            }
        }

        return returnData;
    }
}
