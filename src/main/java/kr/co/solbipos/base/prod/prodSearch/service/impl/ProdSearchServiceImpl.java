package kr.co.solbipos.base.prod.prodSearch.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodSearch.service.ProdSearchService;
import kr.co.solbipos.base.prod.prodSearch.service.ProdSearchVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Class Name : ProdSearchServiceImpl.java
 * @Description : 기초관리 - 상품관리2 - 상품정보조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.13   권지현       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.09.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodSearchService")
public class ProdSearchServiceImpl implements ProdSearchService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ProdSearchMapper prodSearchMapper;

    /** Constructor Injection */
    @Autowired
    public ProdSearchServiceImpl(ProdSearchMapper prodSearchMapper) {
        this.prodSearchMapper = prodSearchMapper;
    }

    /** 상품목록 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody ProdSearchVO prodSearchVO, SessionInfoVO sessionInfoVO) {

        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        prodSearchVO.setOrgnFg(orgnFg);
        prodSearchVO.setHqOfficeCd(hqOfficeCd);
        prodSearchVO.setStoreCd(storeCd);

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (prodSearchVO.getProdHqBrandCd() == "" || prodSearchVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (prodSearchVO.getUserProdBrands() != null && !"".equals(prodSearchVO.getUserProdBrands())) {
                    String[] userBrandList = prodSearchVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        prodSearchVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        prodSearchVO.setUserId(sessionInfoVO.getUserId());

        return prodSearchMapper.getProdList(prodSearchVO);
    }

    /** 상품목록 조회(엑셀다운로드용) */
    @Override
    public List<DefaultMap<String>> getProdExcelList(@RequestBody ProdSearchVO prodSearchVO, SessionInfoVO sessionInfoVO) {

        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        prodSearchVO.setOrgnFg(orgnFg);
        prodSearchVO.setHqOfficeCd(hqOfficeCd);
        prodSearchVO.setStoreCd(storeCd);

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (prodSearchVO.getProdHqBrandCd() == "" || prodSearchVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (prodSearchVO.getUserProdBrands() != null && !"".equals(prodSearchVO.getUserProdBrands())) {
                    String[] userBrandList = prodSearchVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        prodSearchVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        if (prodSearchVO.getExcelGubun().equals("T")) { // 전체 엑셀다운로드시(T) 조회조건 날림
            prodSearchVO.setProdCd(null);
            prodSearchVO.setProdNm(null);
            prodSearchVO.setProdClassCd(null);
            prodSearchVO.setBarCd(null);
            prodSearchVO.setUseYn(null);
            prodSearchVO.setHqBrandNm(null);
            prodSearchVO.setProdHqBrandCd(null);
            prodSearchVO.setUserProdBrands(null);
            prodSearchVO.setUserProdBrandList(null);
            prodSearchVO.setVatFg(null);
            prodSearchVO.setProdTypeFg(null);
            prodSearchVO.setPrcCtrlFg(null);
        }
        prodSearchVO.setUserId(sessionInfoVO.getUserId());

        return prodSearchMapper.getProdExcelList(prodSearchVO);
    }

    @Override
    public List<DefaultMap<String>> getProdList2(ProdSearchVO prodSearchVO, SessionInfoVO sessionInfoVO) {
        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        prodSearchVO.setOrgnFg(orgnFg);
        prodSearchVO.setHqOfficeCd(hqOfficeCd);
        prodSearchVO.setStoreCd(storeCd);

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (prodSearchVO.getProdHqBrandCd() == "" || prodSearchVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (prodSearchVO.getUserProdBrands() != null && !"".equals(prodSearchVO.getUserProdBrands())) {
                    String[] userBrandList = prodSearchVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        prodSearchVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        prodSearchVO.setUserId(sessionInfoVO.getUserId());

        return prodSearchMapper.getProdList2(prodSearchVO);
    }

    /** 상품목록 조회(엑셀다운로드용) */
    @Override
    public List<DefaultMap<String>> getProdExcelList2(@RequestBody ProdSearchVO prodSearchVO, SessionInfoVO sessionInfoVO) {

        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        prodSearchVO.setOrgnFg(orgnFg);
        prodSearchVO.setHqOfficeCd(hqOfficeCd);
        prodSearchVO.setStoreCd(storeCd);

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (prodSearchVO.getProdHqBrandCd() == "" || prodSearchVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (prodSearchVO.getUserProdBrands() != null && !"".equals(prodSearchVO.getUserProdBrands())) {
                    String[] userBrandList = prodSearchVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        prodSearchVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        if (prodSearchVO.getExcelGubun().equals("T")) { // 전체 엑셀다운로드시(T) 조회조건 날림
            prodSearchVO.setProdCd(null);
            prodSearchVO.setProdNm(null);
            prodSearchVO.setProdClassCd(null);
            prodSearchVO.setBarCd(null);
            prodSearchVO.setUseYn(null);
            prodSearchVO.setHqBrandNm(null);
            prodSearchVO.setProdHqBrandCd(null);
            prodSearchVO.setUserProdBrands(null);
            prodSearchVO.setUserProdBrandList(null);
            prodSearchVO.setVatFg(null);
            prodSearchVO.setProdTypeFg(null);
            prodSearchVO.setPrcCtrlFg(null);
            prodSearchVO.setTuKey(null);
            prodSearchVO.setKiosk(null);
            prodSearchVO.setSdsel(null);
            prodSearchVO.setBsImg(null);
            prodSearchVO.setKioskImg(null);
            prodSearchVO.setDidImg(null);
            prodSearchVO.setProImg(null);
            prodSearchVO.setToImg(null);
            prodSearchVO.setDlvrNm(null);
            prodSearchVO.setPrtCnt(null);
            prodSearchVO.setOrgNm(null);
            prodSearchVO.setAlgiNm(null);
            prodSearchVO.setSdyNm(null);
            prodSearchVO.setDisNm(null);
            prodSearchVO.setProNm(null);
        }
        prodSearchVO.setUserId(sessionInfoVO.getUserId());

        return prodSearchMapper.getProdExcelList2(prodSearchVO);
    }

}