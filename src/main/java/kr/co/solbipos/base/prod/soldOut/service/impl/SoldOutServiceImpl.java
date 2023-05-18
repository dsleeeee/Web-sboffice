package kr.co.solbipos.base.prod.soldOut.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.soldOut.service.SoldOutService;
import kr.co.solbipos.base.prod.soldOut.service.SoldOutVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SoldOutServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 품절관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.02.28  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.02.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("SoldOutService")
public class SoldOutServiceImpl implements SoldOutService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SoldOutMapper soldOutMapper;

    /** Constructor Injection */
    @Autowired
    public SoldOutServiceImpl(SoldOutMapper soldOutMapper) {
        this.soldOutMapper = soldOutMapper;
    }

    @Override
    public List<DefaultMap<String>> getProdList(SoldOutVO soldOutVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            soldOutVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        soldOutVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        soldOutVO.setUserId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        String[] storeCds = soldOutVO.getStoreCds().split(",");
        soldOutVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (soldOutVO.getProdCds() != null && !"".equals(soldOutVO.getProdCds())) {
            String[] prodCdList = soldOutVO.getProdCds().split(",");
            soldOutVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (soldOutVO.getStoreHqBrandCd() == "" || soldOutVO.getStoreHqBrandCd() == null || soldOutVO.getProdHqBrandCd() == "" || soldOutVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (soldOutVO.getUserBrands() != null && !"".equals(soldOutVO.getUserBrands())) {
                    String[] userBrandList = soldOutVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        soldOutVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return soldOutMapper.getProdList(soldOutVO);
    }

    @Override
    public DefaultMap<String> getProdDetail(SoldOutVO soldOutVO, SessionInfoVO sessionInfoVO) {

        DefaultMap result = new DefaultMap<>();

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            soldOutVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 상품상세정보 조회
        result = soldOutMapper.getProdDetail(soldOutVO);

        // 연결상품목록 조회
        List<DefaultMap<String>> linkedProdList = soldOutMapper.getLinkedProdList(soldOutVO);
        result.put("linkedProdList", linkedProdList);

        return result;
    }

    // 상품 품절관리 업데이트
    @Override
    public int getProdSoldOutSave(SoldOutVO[] soldOutVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SoldOutVO soldOutVO : soldOutVOs) {

            soldOutVO.setModDt(currentDt);
            soldOutVO.setModId(sessionInfoVO.getUserId());

            procCnt = soldOutMapper.getProdSoldOutSave(soldOutVO);
        }

        return procCnt;
    }

    /** 품절관리 상품탭 - 품절여부전체저장 */
    @Override
    public int getProdSoldOutAllSave(SoldOutVO[] soldOutVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SoldOutVO soldOutVO : soldOutVOs) {

            soldOutVO.setModDt(currentDt);
            soldOutVO.setModId(sessionInfoVO.getUserId());

            // 상품 update
            procCnt = soldOutMapper.getProdSoldOutSave(soldOutVO);

            // 사이드상품 update
            procCnt = soldOutMapper.getSdselProdSoldOutSaveUpdate(soldOutVO);
        }

        return procCnt;
    }

    // 선택그룹
    @Override
    public List<DefaultMap<String>> getMenuGrpList(SoldOutVO soldOutVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            soldOutVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return soldOutMapper.getMenuGrpList(soldOutVO);
    }

    // 선택분류
    @Override
    public List<DefaultMap<String>> getMenuClassList(SoldOutVO soldOutVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            soldOutVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return soldOutMapper.getMenuClassList(soldOutVO);
    }

    /** 사이드메뉴-선택메뉴탭-선택상품 목록 조회 */
    @Override
    public List<DefaultMap<String>> getMenuProdList(SoldOutVO soldOutVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            soldOutVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        soldOutVO.setUserId(sessionInfoVO.getUserId());

        return soldOutMapper.getMenuProdList(soldOutVO);
    }

    // 상품 품절관리 업데이트
    @Override
    public int getSideMenuSoldOutSave(SoldOutVO[] soldOutVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SoldOutVO soldOutVO : soldOutVOs) {

            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
                soldOutVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            soldOutVO.setModDt(currentDt);
            soldOutVO.setModId(sessionInfoVO.getUserId());

            procCnt = soldOutMapper.getSideMenuSoldOutSave(soldOutVO);
        }

        return procCnt;
    }

    /** 사이드메뉴(상품) 목록 조회 */
    @Override
    public List<DefaultMap<String>> getSideMenuProdSoldOutList(SoldOutVO soldOutVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            soldOutVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        soldOutVO.setUserId(sessionInfoVO.getUserId());

        // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
        if (soldOutVO.getProdHqBrandCd() == "" || soldOutVO.getProdHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
            if (soldOutVO.getUserProdBrands() != null && !"".equals(soldOutVO.getUserProdBrands())) {
                String[] userBrandList = soldOutVO.getUserProdBrands().split(",");
                if (userBrandList.length > 0) {
                    soldOutVO.setUserProdBrandList(userBrandList);
                }
            }
        }

        return soldOutMapper.getSideMenuProdSoldOutList(soldOutVO);
    }

}