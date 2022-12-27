package kr.co.solbipos.base.prod.prodInfoSearch.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodInfoSearch.service.ProdInfoSearchService;
import kr.co.solbipos.base.prod.prodInfoSearch.service.ProdInfoSearchVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Class Name : ProdInfoSearchServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 상품정보조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.23  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodInfoSearchService")
@Transactional
public class ProdInfoSearchServiceImpl implements ProdInfoSearchService {
    private final ProdInfoSearchMapper prodInfoSearchMapper;

    public ProdInfoSearchServiceImpl(ProdInfoSearchMapper prodInfoSearchMapper) {
        this.prodInfoSearchMapper = prodInfoSearchMapper;
    }

    /** 상품분류 조회 */
    @Override
    public List<DefaultMap<String>> getProdClassList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodInfoSearchMapper.getProdClassList(prodInfoSearchVO);
    }

    /** 상품분류 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getProdClassExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodInfoSearchMapper.getProdClassExcelList(prodInfoSearchVO);
    }

    /** 사이드-속성 조회 */
    @Override
    public List<DefaultMap<String>> getSideAttrList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodInfoSearchMapper.getSideAttrList(prodInfoSearchVO);
    }

    /** 사이드-속성 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getSideAttrExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodInfoSearchMapper.getSideAttrExcelList(prodInfoSearchVO);
    }

    /** 사이드-선택메뉴 조회 */
    @Override
    public List<DefaultMap<String>> getSideMenuList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodInfoSearchMapper.getSideMenuList(prodInfoSearchVO);
    }

    /** 사이드-선택메뉴 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getSideMenuExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodInfoSearchMapper.getSideMenuExcelList(prodInfoSearchVO);
    }

    /** 옵션 조회 */
    @Override
    public List<DefaultMap<String>> getOptionList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodInfoSearchMapper.getOptionList(prodInfoSearchVO);
    }

    /** 옵션 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getOptionExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodInfoSearchMapper.getOptionExcelList(prodInfoSearchVO);
    }

    /** 상품-속성/선택메뉴/옵션 조회 */
    @Override
    public List<DefaultMap<String>> getProdInfoList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());
        
        // 상품 array 값 세팅
        if (prodInfoSearchVO.getProdCds() != null && !"".equals(prodInfoSearchVO.getProdCds())) {
            String[] prodCdList = prodInfoSearchVO.getProdCds().split(",");
            prodInfoSearchVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodInfoSearchVO.getProdHqBrandCd() == "" || prodInfoSearchVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodInfoSearchVO.getUserBrands().split(",");
                prodInfoSearchVO.setUserBrandList(userBrandList);
            }
        }

        return prodInfoSearchMapper.getProdInfoList(prodInfoSearchVO);
    }

    /** 상품-속성/선택메뉴/옵션 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getProdInfoExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        // 상품 array 값 세팅
        if (prodInfoSearchVO.getProdCds() != null && !"".equals(prodInfoSearchVO.getProdCds())) {
            String[] prodCdList = prodInfoSearchVO.getProdCds().split(",");
            prodInfoSearchVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodInfoSearchVO.getProdHqBrandCd() == "" || prodInfoSearchVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodInfoSearchVO.getUserBrands().split(",");
                prodInfoSearchVO.setUserBrandList(userBrandList);
            }
        }

        return prodInfoSearchMapper.getProdInfoExcelList(prodInfoSearchVO);
    }

    /** 원산지 조회 */
    @Override
    public List<DefaultMap<String>> getOrgplceList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        // 상품 array 값 세팅
        if (prodInfoSearchVO.getProdCds() != null && !"".equals(prodInfoSearchVO.getProdCds())) {
            String[] prodCdList = prodInfoSearchVO.getProdCds().split(",");
            prodInfoSearchVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodInfoSearchVO.getProdHqBrandCd() == "" || prodInfoSearchVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodInfoSearchVO.getUserBrands().split(",");
                prodInfoSearchVO.setUserBrandList(userBrandList);
            }
        }

        return prodInfoSearchMapper.getOrgplceList(prodInfoSearchVO);
    }

    /** 원산지 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getOrgplceExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        // 상품 array 값 세팅
        if (prodInfoSearchVO.getProdCds() != null && !"".equals(prodInfoSearchVO.getProdCds())) {
            String[] prodCdList = prodInfoSearchVO.getProdCds().split(",");
            prodInfoSearchVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodInfoSearchVO.getProdHqBrandCd() == "" || prodInfoSearchVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodInfoSearchVO.getUserBrands().split(",");
                prodInfoSearchVO.setUserBrandList(userBrandList);
            }
        }

        return prodInfoSearchMapper.getOrgplceExcelList(prodInfoSearchVO);
    }

    /** 알레르기 조회 */
    @Override
    public List<DefaultMap<String>> getAllergyList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        // 상품 array 값 세팅
        if (prodInfoSearchVO.getProdCds() != null && !"".equals(prodInfoSearchVO.getProdCds())) {
            String[] prodCdList = prodInfoSearchVO.getProdCds().split(",");
            prodInfoSearchVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodInfoSearchVO.getProdHqBrandCd() == "" || prodInfoSearchVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodInfoSearchVO.getUserBrands().split(",");
                prodInfoSearchVO.setUserBrandList(userBrandList);
            }
        }

        return prodInfoSearchMapper.getAllergyList(prodInfoSearchVO);
    }

    /** 알레르기 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getAllergyExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO) {

        prodInfoSearchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoSearchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoSearchVO.setStoreCd(sessionInfoVO.getStoreCd());

        // 상품 array 값 세팅
        if (prodInfoSearchVO.getProdCds() != null && !"".equals(prodInfoSearchVO.getProdCds())) {
            String[] prodCdList = prodInfoSearchVO.getProdCds().split(",");
            prodInfoSearchVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodInfoSearchVO.getProdHqBrandCd() == "" || prodInfoSearchVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodInfoSearchVO.getUserBrands().split(",");
                prodInfoSearchVO.setUserBrandList(userBrandList);
            }
        }

        return prodInfoSearchMapper.getAllergyExcelList(prodInfoSearchVO);
    }

}